package rest.ressources;

import rest.entities.Logement;
import rest.metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("logements")
public class LogementRessources {
    private static LogementBusiness lgb = new LogementBusiness();

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getall")
    public Response getAll() {
        if (lgb.getLogements().isEmpty())
            return Response.status(Response.Status.NOT_FOUND).entity("la liste est vide").build();
        else
            return Response.status(Response.Status.OK).entity(lgb).build();
    }*/

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement l) {
        if (lgb.getLogements().add(l))
            return Response.status(Response.Status.CREATED)
                    .entity(lgb.getLogements())
                    .build();

        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementbydelegation(@QueryParam("delegation") String del) {
        if (del != null) {
            if (lgb.getLogementsByDeleguation(del).isEmpty())
                return Response.status(Response.Status.NOT_FOUND).entity("Aucun logement appartient à cette delegation").build();
            else
                return Response.status(Response.Status.OK).entity(lgb.getLogementsByDeleguation(del)).build();
        } else
            return Response.status(Response.Status.OK).entity(lgb.getLogements()).build();
    }


    @DELETE
    @Path("{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLogement(@PathParam("reference") int ref) {
        if (lgb.deleteLogement(ref))
            return Response.status(Response.Status.OK).entity(lgb.getLogements()).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{r}")
    public Response updateLogement(@PathParam("r") int ref, Logement l) {
        if (lgb.updateLogement(ref, l))
            return Response.status(Response.Status.OK)
                    .entity(lgb.getLogements())
                    .build();
        return Response.status(Response.Status.NOT_FOUND)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getref")
    public Response getLogementbyreff(@QueryParam("ref") int reference)
    {
        if(lgb.getLogementsByReference(reference)==null)
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        return Response.status(Response.Status.FOUND)
                .entity(lgb.getLogementsByReference(reference))
                .build();
    }
}