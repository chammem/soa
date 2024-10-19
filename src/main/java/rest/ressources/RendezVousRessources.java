package rest.ressources;

import rest.entities.Logement;
import rest.entities.RendezVous;
import rest.metiers.LogementBusiness;
import rest.metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rendezvous")
public class RendezVousRessources {
    private static RendezVousBusiness r = new RendezVousBusiness();
    private static LogementBusiness l = new LogementBusiness();
    @POST
    @Path("/ajouter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rendezVous(RendezVous rendezVous) {
        boolean isCreated = r.addRendezVous(rendezVous);

        if (isCreated) {
            return Response.status(Response.Status.CREATED)
                    .entity(rendezVous)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Erreur lors de la création du rendez-vous, peut-être que le logement n'existe pas.")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRendezvous() {
        List<RendezVous> rendezVousList = r.getListeRendezVous();

        if (rendezVousList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Aucun rendez-vous n'a été trouvé.")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity(rendezVousList)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getr")
    public Response getRendezvousByLogementReference(@QueryParam("refLogement") int refLogement) {
        List<RendezVous> rendezVousList = r.getListeRendezVousByLogementReference(refLogement);

        if (rendezVousList.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Aucun rendez-vous trouvé pour le logement avec la référence " + refLogement)
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity(rendezVousList)
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getr/{id}")
    public Response getRendezvousById(@PathParam("id") int id) {
        RendezVous rendezVous = r.getRendezVousById(id);

        if (rendezVous == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Le rendez-vous avec l'ID " + id + " n'existe pas.")
                    .build();
        } else {
            return Response.status(Response.Status.OK)
                    .entity(rendezVous)
                    .build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/supprimer/{id}")
    public Response deleteRendezVous(@PathParam("id") int id) {
        boolean isDeleted = r.deleteRendezVous(id);

        if (isDeleted) {
            return Response.status(Response.Status.OK)
                    .entity("Le rendez-vous avec l'ID " + id + " a été supprimé avec succès.")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Le rendez-vous avec l'ID " + id + " n'existe pas.")
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modifier/{id}")
    public Response updateRendezVous(@PathParam("id") int id, RendezVous updatedRendezVous) {
        // Associer l'identifiant à l'objet à mettre à jour
        updatedRendezVous.setId(id);

        boolean isUpdated = r.updateRendezVous(id, updatedRendezVous);

        if (isUpdated) {
            return Response.status(Response.Status.OK)
                    .entity(updatedRendezVous)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Le rendez-vous avec l'ID " + id + " n'existe pas.")
                    .build();
        }
    }


}

