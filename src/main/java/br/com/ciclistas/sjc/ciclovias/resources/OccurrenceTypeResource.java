package br.com.ciclistas.sjc.ciclovias.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;

/**
 * @author Pedro Hos
 *
 */

@Path("type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value="/type", tags = "type")
public interface OccurrenceTypeResource {
	
	@GET
	Response getAllStatus();

}
