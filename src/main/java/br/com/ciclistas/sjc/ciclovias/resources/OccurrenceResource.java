package br.com.ciclistas.sjc.ciclovias.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import br.com.ciclistas.sjc.ciclovias.model.entities.Occurrence;

/**
 * @author Pedro Hos
 *
 */

@Path("occurrences")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OccurrenceResource {
	
	@POST
	Response newOccurrence(Occurrence occurrence);
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path(value = "/upload")
	Response newOccurrenceWithUploads(MultipartFormDataInput multipart) throws IOException;
	
	@GET
	Response allOccurrence();
	
	@GET
	@Path("/{id}")
	Response findById(@PathParam("id") final Long id);

}
