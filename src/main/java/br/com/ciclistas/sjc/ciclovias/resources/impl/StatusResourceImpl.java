package br.com.ciclistas.sjc.ciclovias.resources.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.com.ciclistas.sjc.ciclovias.model.repositories.StatusRepository;
import br.com.ciclistas.sjc.ciclovias.resources.StatusResource;
import br.com.ciclistas.sjc.ciclovias.resources.utils.JaxrsUtils;

/**
 * @author Pedro Hos
 *
 */
@Stateless
public class StatusResourceImpl implements StatusResource {
	
	@Inject
	private StatusRepository stausRepository;
	
	@Override
	public Response getAllStatus() {
		return Response.ok(JaxrsUtils.throw404IfNull(stausRepository.listAll())).build();
	}
	
	

}
