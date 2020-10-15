package org.loocsij.j2se.lamda;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.loocsij.logger.*;

public class LamdaExample1{
    private static Logger log = Log.getLogger(LamdaExample1.class);
    class Person{
        int age = 0;
        String name = "";
        Person(final int age, final String name){
            this.age = age;
            this.name = name;
        }
    }
    interface Filter{
        boolean accept(Person p);
    }
    void checkPerson(final List<Person> persons, final Filter filter){
        for(Person person : persons){
            if(filter.accept(person)){
                log.info(person.name);
            }
        }
    }
    void check(){
        List<Person> persons = new ArrayList<>();
        persons.add(new Person(28, "Bruce"));
        persons.add(new Person(23, "Alice"));
        persons.add(new Person(5, "Rain"));
        checkPerson(persons, p->p.age > 10);
    }
}
