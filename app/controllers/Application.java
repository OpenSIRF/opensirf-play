package controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.opensirf.catalog.SIRFCatalog;
import org.opensirf.client.SirfClient;
import org.opensirf.jaxrs.config.SIRFConfiguration;
import org.opensirf.obj.PreservationObjectInformation;

import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import views.html.aboutTemplate;
import views.html.catalogTemplate;
import views.html.containerAuditLogTemplate;
import views.html.digestInformationTemplate;
import views.html.documentationTemplate;
import views.html.extensionPairTemplate;
import views.html.extensionTemplate;
import views.html.identifierTemplate;
import views.html.objectNameTemplate;
import views.html.preservationObjectAuditLogTemplate;
import views.html.preservationObjectInformationTemplate;
import views.html.relatedObjectsTemplate;
import views.html.setupTemplate;

public class Application extends Controller {
	public Application() {		
		try {
			endpoint = new String(Files.readAllBytes(Paths.get(
					SIRFConfiguration.SIRF_DEFAULT_DIRECTORY + "endpoint")));
			
			config = new SirfClient(endpoint).getConfiguration();
			
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public Result dynamicFormElements(String category, int index, int subIndex) {
		switch (category) {
		case "Extension":
			return ok(extensionTemplate.render(null, index));
		case "ExtensionPair":
			return ok(extensionPairTemplate.render(null, index, subIndex));
		case "DigestInformation":
			return ok(digestInformationTemplate.render(null, index));
		case "Identifier":
			return ok(identifierTemplate.render(null, index));
		case "ObjectName":
			return ok(objectNameTemplate.render(null, index, subIndex));
		case "RelatedObjects":
			return ok(relatedObjectsTemplate.render(null, index));
		case "PreservationObjectAuditLog":
			return ok(preservationObjectAuditLogTemplate.render(null, index));
		case "ContainerAuditLog":
			return ok(containerAuditLogTemplate.render(null, index));
		default:
			throw new RuntimeException("Category " + category + " not found.");
		}
	}

	@BodyParser.Of(BodyParser.MultipartFormData.class)
	public Result addPreservationObjectInformation() {
		Form<PreservationObjectInformation> formPreservationObjectInformation = Form.form(
				PreservationObjectInformation.class).bindFromRequest();

		// TODO: convert to SirfClient
		PreservationObjectInformation poi = formPreservationObjectInformation.get();
		
		RequestBody body = request().body();
		
		for(FilePart fp: body.asMultipartFormData().getFiles()) {
			if(fp.getKey().equals("inputStream")) {
				try {
					SIRFConfiguration conf = getConfig();
					
				    FileDataBodyPart filePart = new FileDataBodyPart("inputstream", fp.getFile());
				    FormDataBodyPart poiPart = new FormDataBodyPart("poi", poi,
				    		MediaType.APPLICATION_JSON_TYPE);
					
					String endpoint = new String(Files.readAllBytes(Paths.get(SIRFConfiguration.
							SIRF_DEFAULT_DIRECTORY + "endpoint")));
					Client client = ClientBuilder.newBuilder().
							register(MultiPartFeature.class).
							build();

					WebTarget resource = client.target("http://" + endpoint + "/sirf/container/" +
							conf.getContainerConfiguration().getContainerName() + "/po");
					
					MultiPart multipartEntity = new FormDataMultiPart()
				    		.bodyPart(poiPart).bodyPart(filePart);
					
					Response response = resource.request().post(
							Entity.entity(multipartEntity, multipartEntity.getMediaType()));
					
					String output = response.readEntity(String.class);
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}

		return ok(preservationObjectInformationTemplate.render(Form.form(
				PreservationObjectInformation.class), poi));
	}
	
	public Result savePreservationObjectInformation() {
		Form<PreservationObjectInformation> formPreservationObjectInformation = Form.form(
				PreservationObjectInformation.class).bindFromRequest();

		PreservationObjectInformation poi = formPreservationObjectInformation.get();
		
		poi.setVersionIdentifierUUID(poi.getObjectIdentifiers().get(0).getObjectVersionIdentifier().
				getObjectIdentifierValue());
		
		SirfClient cli = new SirfClient(endpoint);
		SIRFCatalog catalog = cli.getCatalog(config.getContainerConfiguration().getContainerName());
		catalog.getSirfObjects().remove(poi.getVersionIdentifierUUID());
		catalog.getSirfObjects().add(poi);
		cli.pushCatalog(config.getContainerConfiguration().getContainerName(), catalog);

		return catalog();
	}
	
	public Result createPreservationObjectInformation() {
		return ok(preservationObjectInformationTemplate.render(
				Form.form(PreservationObjectInformation.class), null));
	}

	public Result saveSetup(String endpoint) {
		try {
			Files.write(Paths.get(SIRFConfiguration.SIRF_DEFAULT_DIRECTORY + "endpoint"),
					endpoint.getBytes());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return ok();
	}

	public Result editPreservationObject(String uuid) {
		SirfClient c = new SirfClient(endpoint);
		PreservationObjectInformation poi = c.getPreservationObjectInformation(config.
				getContainerConfiguration().getContainerName(), uuid);
		
		return ok(preservationObjectInformationTemplate.render(Form.form(
				PreservationObjectInformation.class), poi));
	}

	public Result downloadPreservationObject(String uuid) {
		SirfClient c = new SirfClient(endpoint, "application/octet-stream");
		byte[] po = c.getPreservationObject(config.getContainerConfiguration().getContainerName(),
				uuid);
		
		return ok(po);
	}

	public Result saveCatalog() {
		Form<SIRFCatalog> formCatalog = Form.form(SIRFCatalog.class).bindFromRequest();
		SIRFCatalog c = formCatalog.get();
		
		SirfClient cli = new SirfClient(endpoint);
		SIRFConfiguration config = cli.getConfiguration();
		String containerName = config.getContainerConfiguration().getContainerName();

		System.out.println("CONTAINER NAME2 = " + containerName);
		System.out.println("CATALOG NULL2 = " + (c==null));
		
		cli.pushCatalog(containerName, c);
		return catalog();
	}	
	
	public Result catalog() {
		try {
			String endpoint = new String(Files.readAllBytes(Paths.get(
				SIRFConfiguration.SIRF_DEFAULT_DIRECTORY + "endpoint")));
	
			SirfClient cli = new SirfClient(endpoint);
			SIRFConfiguration config = cli.getConfiguration();
			String containerName = config.getContainerConfiguration().getContainerName();
			
			System.out.println("CONTAINER NAME = " + containerName);
			
			SIRFCatalog catalog = cli.getCatalog(containerName);

			System.out.println("CATALOG NULL = " + (catalog==null));
			
			return ok(catalogTemplate.render(Form.form(SIRFCatalog.class),
					cli.getCatalog(containerName)));
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private SIRFConfiguration getConfig() {
		config = new SirfClient(endpoint).getConfiguration();
		return config;
	}

	public Result setup() {
		String s;

		try {
			s = new String(Files.readAllBytes(Paths.get(SIRFConfiguration.SIRF_DEFAULT_DIRECTORY +
					"endpoint")));
		} catch (IOException e) {
			s = "";
		}
		return ok(setupTemplate.render(s));
	}

	public Result documentation() {
		return ok(documentationTemplate.render());
	}

	public Result about() {
		return ok(aboutTemplate.render());
	}
	
	private String endpoint;
	
	private SIRFConfiguration config;

}
