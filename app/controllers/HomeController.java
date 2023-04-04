package controllers;

import models.Person;
import models.Planet;
import models.Universe;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.StartWarsClient;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private final StartWarsClient client;

    @Inject
    public HomeController(StartWarsClient client) {
        this.client = client;
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("Hello!");
    }

    public CompletionStage<Result> firstPlanet() {
        return this.client.getFirstPlanet().thenApply(jsonPlanet -> {
            Planet planet = Json.fromJson(jsonPlanet, Planet.class);
            return ok(planet.toString());
        });
    }

    public Result incorrectParameter(String text) {
        return ok("Incorrect parameter, use integer!");
    }

    public CompletionStage<Result> selectPlanet(int id) {
        List<Person> personList = new ArrayList<Person>();

        return this.client.getSelectPlanet(id).thenApply(jsonPlanet -> {
            Planet planet = Json.fromJson(jsonPlanet, Planet.class);
            if (planet.getName() == null) {
                return ok("Planet with this id does not exist");
            }

            List<String> residentsUrls = planet.getResidents();

            List<CompletableFuture<?>> futures = new ArrayList<>();
            if (residentsUrls != null)
                for (String url : residentsUrls) {
                    futures.add(this.client.getPerson(url).thenApply(jsonPerson -> {
                        Person person = Json.fromJson(jsonPerson, Person.class);
                        personList.add(person);
                        return ok(person.toString());
                    }).toCompletableFuture());
                }

            CompletableFuture<?> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            try {
                combinedFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Universe universe = new Universe(planet, personList);

            return ok(universe.toString());
        });
    }
}
