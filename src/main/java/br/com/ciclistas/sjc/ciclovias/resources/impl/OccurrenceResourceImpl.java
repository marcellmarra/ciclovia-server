package br.com.ciclistas.sjc.ciclovias.resources.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
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
	
	private static final String BUCKET_NAME = System.getProperty("amazon.s3.bucket-name");

	@Inject
	private Logger logger;
	
	@Inject
	private Occurrences occurrences;

	@Override
	public Response newOccurrence(final Occurrence occurrence) {
		occurrences.save(occurrence);
		return Response.created(URI.create("/occurrences/" + occurrence.getId())).build();
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
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
		List<String> urlImages = saveS3File(multipart.getFormDataMap().get("uploads"));
		occurrence.setPathPhoto(urlImages);
		occurrences.save(occurrence);
		
		return Response.created(URI.create("/occurrences/" + occurrence.getId())).entity(occurrence).build();
	}
	
	private List<String> saveS3File(List<InputPart> files) {
		
		logger.info("Saving files on Amazon S3");
		
		List<String> paths = new ArrayList<>();
		
        try {
        	
        	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        			.withRegion(Region.getRegion(Regions.US_EAST_1).getName())
        			.withCredentials(new ProfileCredentialsProvider())
        			.build();
        	
        	ObjectMetadata metadata = new ObjectMetadata();
        	metadata.setContentType("image/jpeg");
        	
        	for(InputPart file : files) {
        		InputStream is = file.getBody(InputStream.class, null);
        		String fileName = UUID.randomUUID() + ".jpeg";
        		s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, is, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
        		paths.add(s3Client.getUrl(BUCKET_NAME, fileName).toString());
        	}
			
        } catch (IOException e) {
			logger.severe(e.getMessage());
		}
		
        return paths;
	}
}
