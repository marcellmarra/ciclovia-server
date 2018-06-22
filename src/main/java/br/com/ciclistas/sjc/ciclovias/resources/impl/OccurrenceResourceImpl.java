package br.com.ciclistas.sjc.ciclovias.resources.impl;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
	
	private static final String CONTENT_PATH = System.getProperty("jboss.home.dir");
	private static final String PATH_OCCURRENCES_IMAGES = System.getProperty("path_images_ciclovias");
	private static final String URL_CONTEXT = System.getProperty("url_context_ciclovias");

	@Inject
	private Occurrences occurrences;

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
		
		String occurrenceJson = multipart.getFormDataPart("occurrence", String.class, null);

		ObjectMapper mapper = new ObjectMapper();
		Occurrence occurrence = mapper.readValue(occurrenceJson, Occurrence.class);

		occurrence.setPathPhoto(saveFile(multipart.getFormDataMap().get("uploads")));
		
		occurrences.save(occurrence);
		
		return Response.created(URI.create("/occurrences/" + occurrence.getId())).entity(occurrence).build();
	}
	
	private List<String> saveFile(final List<InputPart> imageParts) {

		logger.info("Salvando Arquivos da Ocorrencia");

		String pathOccurrenceImages = CONTENT_PATH + PATH_OCCURRENCES_IMAGES;
		
		System.out.println("È feio, eu sei .... " + pathOccurrenceImages);
		
		List<String> paths = new ArrayList<>();
		
		imageParts.forEach(part -> {
			String imageName = "IMG-" + Calendar.getInstance().getTimeInMillis() + ".jpg";
			Path path = Paths.get(pathOccurrenceImages + imageName);
			createFile(path, part);
			paths.add(URL_CONTEXT.concat("/").concat(PATH_OCCURRENCES_IMAGES).concat(imageName));
		});
		
		return paths;

	}

	private void createFile(Path path, InputPart part) {

		try {

			Files.write(path, part.getBody(byte[].class, null), StandardOpenOption.CREATE);

		} catch (IOException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
		}
	}

}
