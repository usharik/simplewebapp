package ru.geekbrains.rest;

import ru.geekbrains.jsf.UserRepr;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/users")
public interface UserServiceRest {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    List<UserRepr> getAllUsers();

    @GET
    @Path("/{id}/id")
    @Produces(MediaType.APPLICATION_JSON)
    UserRepr findById(@PathParam("id") int id);

}
