package controllers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.RequestBody;

import org.opensirf.audit.AuditLogReference;
import org.opensirf.audit.PreservationObjectAuditLog;
import org.opensirf.catalog.SIRFCatalog;
import org.opensirf.jaxrs.api.ObjectApi;
import org.opensirf.jaxrs.config.SIRFConfiguration;
import org.opensirf.jaxrs.config.SIRFConfigurationMarshaller;
import org.opensirf.jaxrs.config.SIRFConfigurationUnmarshaller;
import org.opensirf.obj.DigestInformation;
import org.opensirf.obj.Extension;
import org.opensirf.obj.ExtensionPair;
import org.opensirf.obj.FixityInformation;
import org.opensirf.obj.PackagingFormat;
import org.opensirf.obj.PreservationObjectIdentifier;
import org.opensirf.obj.PreservationObjectInformation;
import org.opensirf.obj.PreservationObjectLogicalIdentifier;
import org.opensirf.obj.PreservationObjectName;
import org.opensirf.obj.PreservationObjectParentIdentifier;
import org.opensirf.obj.PreservationObjectVersionIdentifier;
import org.opensirf.obj.RelatedObjectReference;
import org.opensirf.obj.RelatedObjects;
import org.opensirf.obj.Retention;
import org.opensirf.jaxrs.api.StorageContainerStrategy;
import org.opensirf.jaxrs.api.StrategyFactory;

import play.data.Form;
import play.mvc.Controller;
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
					
					System.out.println("OpenSIRF JAX-RS Response:");
					System.out.println(output);
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
		
		System.out.println("updating poi = " + poi.getVersionIdentifierUUID());
		
		SIRFConfiguration conf = getConfig();
		StorageContainerStrategy strat = StrategyFactory.createStrategy(conf);
		SIRFCatalog c = strat.getCatalog();
		c.getSirfObjects().remove(poi.getVersionIdentifierUUID());
		c.getSirfObjects().add(poi);
		strat.pushCatalog(c);
		
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
		System.out.println("UUID=" + uuid);
		SIRFConfiguration conf = getConfig();
		StorageContainerStrategy strat = StrategyFactory.createStrategy(conf);
		PreservationObjectInformation poi = strat.getCatalog().getSirfObjects().get(uuid);
		
		return ok(preservationObjectInformationTemplate.render(Form.form(
				PreservationObjectInformation.class), poi));
	}

	public Result downloadPreservationObject(String uuid) {
		try {
			SIRFConfiguration conf = getConfig();
			StorageContainerStrategy strat = StrategyFactory.createStrategy(conf);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			strat.getPreservationObjectStreamingOutput(uuid).write(baos);
			
			response().setHeader("Content-disposition","attachment; filename=" + uuid);
			
			return ok(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Result saveCatalog() {
		Form<SIRFCatalog> formCatalog = Form.form(SIRFCatalog.class).bindFromRequest();
		SIRFCatalog c = formCatalog.get();
		
		SIRFConfiguration conf = getConfig();
		StorageContainerStrategy strat = StrategyFactory.createStrategy(conf);
		strat.pushCatalog(c);
		
		return catalog();
	}

	public Result catalog() {
		SIRFConfiguration conf = getConfig();
		StorageContainerStrategy strat = StrategyFactory.createStrategy(conf);
		SIRFCatalog c = strat.getCatalog();
		return ok(catalogTemplate.render(Form.form(SIRFCatalog.class), c));
	}
	
	private SIRFConfiguration getConfig() {
		try {
			String endpoint = new String(Files.readAllBytes(Paths.get(
					SIRFConfiguration.SIRF_DEFAULT_DIRECTORY + "endpoint")));
			Client client = ClientBuilder.newClient();
			WebTarget resource = client.target("http://" + endpoint + "/sirf/config");
			Builder request = resource.request();
			request.accept(MediaType.APPLICATION_JSON);
			Response response = request.get();
			String output = response.readEntity(String.class);
			return new SIRFConfigurationUnmarshaller().unmarshalConfig(output);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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

}
