package rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;

import operations.Logic;
import operations.Response;

@Path("loginapi")
public class LoginApi {

    Gson gson = new Gson();
    Logic logic;
    Response response = new Response();

    // returns boolean value stored in a JSon true or false depending whether
    // user pass matches or not
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public String login(String jsonString) throws JOSEException, ClassNotFoundException, SQLException {
        logic = new Logic();

        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        if (logic.getUserByUsername(username) == null) {
            return gson.toJson(logic.notAuthenticated());
        } else {
            logic.authenticate(password);
            return gson.toJson(logic.isAuthenticated());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("signup")
    public void signup(String jsonString) throws JOSEException, ClassNotFoundException, SQLException {
        logic = new Logic();

        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        logic.prepareNewUser(username, password);

    }

}
