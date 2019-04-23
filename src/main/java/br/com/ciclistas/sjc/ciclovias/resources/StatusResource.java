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

@Path("status")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value="/status", tags = "status")
public interface StatusResource {
	
	@GET
	Response getAllStatus();
	
}
