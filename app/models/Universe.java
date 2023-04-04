package models;

import lombok.ToString;

import java.util.List;

@ToString
public class Universe {

    private Planet planet;
    private List<Person> persons;

    public Universe(Planet planet, List<Person> persons) {
        this.planet = planet;
        this.persons = persons;
    }
}
