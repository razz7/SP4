package rest;

import dto.PersonDTO;
import entities.Address;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonRessourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    private Person p1;
    private Person p2;
    private Address a1;
    private Address a2;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.createNativeQuery("ALTER TABLE `PERSON` AUTO_INCREMENT = 1").executeUpdate();
        em.getTransaction().commit();
        em.close();
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Henning", "Henningsen", "12345678", new Date(), new Date());
        p2 = new Person("Rasmus", "Rasmussen", "87654321", new Date(), new Date());
        Address a1 = new Address("Cph", 2323, "Nørre voldgade 1");
        Address a2 = new Address("Århus", 2454, "Nørre voldgade 2");
        p1.setAddress(a1);
        p2.setAddress(a2);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testAll() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("allPersons", hasSize(2));

    }

    @Test
    public void testGetPersonByID() {
        given()
                .contentType("application/json")
                .get("/person/{id}", p1.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstname", equalTo("Henning"))
                .body("id", equalTo(p1.getId()));

    }
    
    
//    @Test 
//    public void testAddPerson() {
//        given()
//                .contentType("application/json")
//                .body(new PersonDTO("Peter", "Konrad", "12312323"))
//                .when()
//                .post("/person/add")
//                .then()
//                .body("firstname", equalTo("Peter"))
//                .body("lastname", equalTo("Konrad"));
//                
//    }
//
//    @Test
//    public void testGetPersonByIDError() {
//        given().
//                contentType("application/json")
//                .get("/person/2222")
//                .then()
//                .assertThat()
//                .statusCode(200)
//                .body("msg", equalTo("Could not delete, provided id does not exist"));
//    }

//    @Test
//    public void testCount() throws Exception {
//        given()
//        .contentType("application/json")
//        .get("/person/count").then()
//        .assertThat()
//        .statusCode(HttpStatus.OK_200.getStatusCode())
//        .body("count", equalTo(2));   
//    }
}
