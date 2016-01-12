package controllers;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.opensirf.audit.AuditLogReference;
import org.opensirf.audit.PreservationObjectAuditLog;
import org.opensirf.catalog.SIRFCatalog;
import org.opensirf.jaxrs.config.SIRFConfiguration;
import org.opensirf.jaxrs.config.SwiftConfiguration;
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
import org.opensirf.storage.SwiftStrategy;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import model.MyConfiguration;
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
		System.out.println("" + category + " " + index + " " + subIndex);
		switch(category) {
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

	public Result addPreservationObjectInformation() {
		Form<PreservationObjectInformation> formPreservationObjectInformation = Form.form(PreservationObjectInformation.class).bindFromRequest();
		PreservationObjectInformation poi = formPreservationObjectInformation.get();
		return ok(preservationObjectInformationTemplate.render(Form.form(PreservationObjectInformation.class), poi));
	}
	
	public Result saveSetup(String endpoint) {
		try {
			Files.write(Paths.get(SIRFConfiguration.SIRF_DEFAULT_DIRECTORY + "endpoint"), endpoint.getBytes());
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return ok();
	}
	
	public Result addCatalog() {
		Form<SIRFCatalog> formCatalog = Form.form(SIRFCatalog.class).bindFromRequest();
		SIRFCatalog c = formCatalog.get();
		return ok(catalogTemplate.render(Form.form(SIRFCatalog.class), c));
	}
	
	public Result catalog() {
		SIRFCatalog c = new SIRFCatalog("myId", "myContainer");
		c.getSirfObjects().add(po());
		return ok(catalogTemplate.render(Form.form(SIRFCatalog.class), c));
	}
	
	private PreservationObjectInformation po() {
		PreservationObjectInformation poi = new PreservationObjectInformation();
		poi.setObjectRetention(new Retention("retType", "retValue"));
		poi.setPackagingFormat(new PackagingFormat("packFormat"));
		
		DigestInformation di = new DigestInformation("do1", "da1", "dv1");
		poi.setObjectFixity(new FixityInformation(di));
		
		Extension e1 = new Extension("org1", "desc1");
		Extension e2 = new Extension("org2", "desc2");

		e1.getObjectExtensionPairs().add(new ExtensionPair("key1", "value1"));
		e1.getObjectExtensionPairs().add(new ExtensionPair("key2", "value2"));
		e1.getObjectExtensionPairs().add(new ExtensionPair("key3", "value3"));

		e2.getObjectExtensionPairs().add(new ExtensionPair("key4", "value4"));
		e2.getObjectExtensionPairs().add(new ExtensionPair("key5", "value5"));
		e2.getObjectExtensionPairs().add(new ExtensionPair("key6", "value6"));
		e2.getObjectExtensionPairs().add(new ExtensionPair("key7", "value7"));
		
		poi.getObjectExtension().add(e1);
		poi.getObjectExtension().add(e2);
		
		PreservationObjectIdentifier poid = new PreservationObjectIdentifier();
		poid.setObjectLogicalIdentifier(new PreservationObjectLogicalIdentifier("logicalType1", "logicalLocale1", "logicalValue1"));
		poid.setObjectVersionIdentifier(new PreservationObjectVersionIdentifier("versionType1", "versionLocale1", "versionValue1"));
		poid.setObjectParentIdentifier(new PreservationObjectParentIdentifier("parentType1", "parentLocale1", "parentValue1"));
		poid.putObjectName(new PreservationObjectName("nameType1", "nameLocale1", "nameValue1"));
		poid.putObjectName(new PreservationObjectName("nameType2", "nameLocale2", "nameValue2"));
		poid.putObjectName(new PreservationObjectName("nameType3", "nameLocale3", "nameValue3"));
		poi.addObjectIdentifier(poid);
		
		poid = new PreservationObjectIdentifier();
		poid.setObjectLogicalIdentifier(new PreservationObjectLogicalIdentifier("logicalType2", "logicalLocale2", "logicalValue2"));
		poid.setObjectVersionIdentifier(new PreservationObjectVersionIdentifier("versionType2", "versionLocale2", "versionValue2"));
		poid.setObjectParentIdentifier(new PreservationObjectParentIdentifier("parentType2", "parentLocale2", "parentValue2"));
		poid.putObjectName(new PreservationObjectName("nameType4", "nameLocale4", "nameValue4"));
		poid.putObjectName(new PreservationObjectName("nameType5", "nameLocale5", "nameValue5"));
		poi.addObjectIdentifier(poid);

		ArrayList<RelatedObjects> relatedObjects = new ArrayList<RelatedObjects>();
		RelatedObjects relatedObject = new RelatedObjects();
		relatedObject.setObjectRelatedObjectReference(new RelatedObjectReference("type", "role", "value"));
		relatedObjects.add(relatedObject);
		poi.setObjectRelatedObjects(relatedObjects);
		
		ArrayList<PreservationObjectAuditLog> auditLogs = new ArrayList<PreservationObjectAuditLog>();
		PreservationObjectAuditLog auditLog = new PreservationObjectAuditLog();
		auditLog.setObjectAuditLogReference(new AuditLogReference("type", "role", "value"));
		auditLogs.add(auditLog);
		poi.setObjectAuditLog(auditLogs);
		
		return poi;
	}
	
	public Result poiUnitTest() {
		PreservationObjectInformation poi = po();
		
		return ok(preservationObjectInformationTemplate.render(Form.form(PreservationObjectInformation.class), poi));
	}

	public Result setup() {
		String s;
		
		try {
			s = new String(Files.readAllBytes(
					Paths.get(SIRFConfiguration.SIRF_DEFAULT_DIRECTORY + "endpoint")));
		} catch(IOException e) {
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
