package br.com.ciclistas.sjc.ciclovias.resources.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ciclistas.sjc.ciclovias.model.entities.Occurrence;
import br.com.ciclistas.sjc.ciclovias.model.repositories.Occurrences;
import br.com.ciclistas.sjc.ciclovias.resources.OccurrenceResource;

/**
 * @author Pedro Hos
 *
 */
@Stateless
public class OccurrenceResourceImpl implements OccurrenceResource {
	
	@Inject
	private Logger logger;
	
	@Inject
	private Occurrences occurrences;
	
	//@Context 
	//private SecurityContext securityContext;

	@Override
	public Response newOccurrence(final Occurrence occurrence) {
		occurrences.save(occurrence);
		return Response.created(URI.create("/occurrences/" + occurrence.getId())).build();
	}
	
	@Override
	public Response allOccurrence() {
		return Response.ok(occurrences.listAll()).build();
	}

	@Override
	public Response findById(final Long id) {
		return Response.ok(occurrences.findById(id)).build();
	}

	@Override
	public Response newOccurrenceWithUploads(MultipartFormDataInput multipart) throws IOException {
		
		logger.info("Saving new occurrence!");
		
		//logger.info("Is admin? " + securityContext.isUserInRole("admin"));
		
		String occurrenceJson = multipart.getFormDataPart("occurrence", String.class, null);

		ObjectMapper mapper = new ObjectMapper();
		Occurrence occurrence = mapper.readValue(occurrenceJson, Occurrence.class);
		
		List<InputPart> photos = multipart.getFormDataMap().get("uploads");
		
		List<String> urlImages = savePhoto(photos);
		
		occurrence.setPathPhoto(urlImages);
		occurrences.save(occurrence);
		
		return Response.created(URI.create("/occurrences/" + occurrence.getId()))
					   .entity(occurrence).build();
	}
	
	private List<String> savePhoto(List<InputPart> photos) {
		
		String localPath = System.getProperty("path.image.dir");
		List<String> paths = new ArrayList<>();
		
		for (InputPart photo : photos) {
			
			String fileName = UUID.randomUUID() + ".jpeg";
			
			try {
				
				InputStream is = photo.getBody(InputStream.class,null);
				
				String pathAndFileName = localPath + "/" + fileName;
				Files.write(Paths.get(pathAndFileName), IOUtils.toByteArray(is));
				
				paths.add(System.getProperty("web.context") + "/images/" + fileName);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return paths;
	}
	
}
