package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPersons() {
        PersonsDTO persons = FACADE.getAllPersons();

        return GSON.toJson(persons);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) throws PersonNotFoundException {
        try {
        return Response.ok().entity(GSON.toJson(FACADE.getPerson(id))).build();
        } catch (PersonNotFoundException e) {
            return Response.ok().entity("{\"code\": 404, \"message\":\"CCould not delete, provided id does not exist\"}").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(String person) {

        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newP = FACADE.addPerson(p.getFirstname(), p.getLastname(), p.getPhone());
        return GSON.toJson(newP);
    }

    @PUT
    @Path("/edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPerson(@PathParam("id") int id, String person) throws PersonNotFoundException {

        
        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        pDTO.setId(id);
        PersonDTO newp = FACADE.editPerson(pDTO);
        if(newp == null) {
            return Response.status(404).entity("{\"Code\": 404, \"msg\":\"cant find that id \"}").build();
        }
        

        return Response.ok(newp).build();
    }

    @Path("delete/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        try {
            return Response.ok().entity(GSON.toJson(FACADE.deletePerson(id))).build();
        } catch (RuntimeException ex) {
            return Response.ok().entity("{\"code\": 404, \"message\":\"Could not delete, provided id does not exist\"}").build();
        }
    }

}
