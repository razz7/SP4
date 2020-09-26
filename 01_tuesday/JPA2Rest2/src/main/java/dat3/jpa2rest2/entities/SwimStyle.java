/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpa2rest2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author rh
 */
@Entity
public class SwimStyle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ss_id;
    private String styleName;
    
    @ManyToMany
    private List<Person> persons;

    public SwimStyle() {
    }

    public SwimStyle(String swimStyle) {
        this.styleName = swimStyle;
        this.persons = new ArrayList<>();
    }

    public Long getSs_id() {
        return ss_id;
    }

    public void setSs_id(Long ss_id) {
        this.ss_id = ss_id;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    
    
    

   
    
}
