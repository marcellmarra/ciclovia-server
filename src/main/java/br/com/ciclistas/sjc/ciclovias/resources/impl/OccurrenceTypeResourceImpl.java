package br.com.ciclistas.sjc.ciclovias.resources.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.com.ciclistas.sjc.ciclovias.model.repositories.OccurenceTypes;
import br.com.ciclistas.sjc.ciclovias.resources.OccurrenceTypeResource;
import br.com.ciclistas.sjc.ciclovias.resources.utils.JaxrsUtils;

/**
 * @author Pedro Hos
 *
 */
@Stateless
public class OccurrenceTypeResourceImpl implements OccurrenceTypeResource {
	
	@Inject
	private OccurenceTypes types;
	
	@Override
	public Response getAllStatus() {
		return Response.ok(JaxrsUtils.throw404IfNull(types.listAll())).build();
	}
	
	

}
