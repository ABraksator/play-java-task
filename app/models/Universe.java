package models;

import java.util.List;

public class Universe {

    private Planet planet;
    private List<Person> persons;

    public Universe(Planet planet, List<Person> persons) {
        this.planet = planet;
        this.persons = persons;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public void setPerson(List<Person> personsList){
        this.persons = personsList;
    }

    @Override
    public String toString() {
        return "Universe{" +
                planet +
                "," + persons +
                '}';
    }
}
