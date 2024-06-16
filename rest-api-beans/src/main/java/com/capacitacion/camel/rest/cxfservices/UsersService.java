package com.capacitacion.camel.rest.cxfservices;

import com.capacitacion.camel.rest.pojos.User;
import org.apache.camel.Header;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/cxf-example/v1")
public class UsersService {

    @POST @Path("/users")
    public Response createUser(User user){
        return null;
    }

    @POST @Path("/another/{varX}")
    public Response inputDataExample(Map<String, Object> body, @Header("varX") String id, @QueryParam("page") int page) {
        return null;
    }

}
