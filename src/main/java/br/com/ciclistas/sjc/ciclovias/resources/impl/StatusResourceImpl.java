package br.com.ciclistas.sjc.ciclovias.resources.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import br.com.ciclistas.sjc.ciclovias.model.repositories.StatusRepositoty;
import br.com.ciclistas.sjc.ciclovias.resources.StatusResource;

/**
 * @author Pedro Hos
 *
 */
@Stateless
public class StatusResourceImpl implements StatusResource {
	
	@Inject
	private StatusRepositoty stausRepository;
	
	@Override
	public Response getAllStatus() {
		return Response.ok(stausRepository.listAll()).build();
	}
	
	

}
