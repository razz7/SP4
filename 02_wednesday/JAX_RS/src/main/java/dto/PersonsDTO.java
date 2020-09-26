/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.List;
import entities.Person;
import java.util.ArrayList;

/**
 *
 * @author rh
 */
public class PersonsDTO {
    
    List<PersonDTO> allPersons = new ArrayList();
    
    public PersonsDTO(List<Person> persons) {
        persons.forEach((p) -> {
            allPersons.add(new PersonDTO(p));
        });
    }
}
