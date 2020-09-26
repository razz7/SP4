package dto;

import entities.Address;
import entities.Person;
import java.io.Serializable;
import java.util.Date;

public class PersonDTO implements Serializable {

    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private Date created;
    private Date lastEdited;
    private String street;
    private int zip;
    private String city;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstname = person.getFirstname();
        this.lastname = person.getLastname();
        this.phone = person.getPhone();
        this.created = person.getCreated();
        this.lastEdited = person.getLastEdited();
        this.street = person.getAddress().getStreet();
        this.city = person.getAddress().getCity();
        this.zip = person.getAddress().getZip();
        
    }

    public PersonDTO(String fn,String ln, String phone) {
        this.firstname = fn;
        this.lastname= ln;
        this.phone = phone;        
    }


    public PersonDTO() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

}
