package controllers;

import java.util.ArrayList;

import org.opensirf.audit.AuditLogReference;
import org.opensirf.audit.PreservationObjectAuditLog;
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

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.preservationObjectAuditLogTemplate;
import views.html.preservationObjectInformationTemplate;

public class Application extends Controller {

	public Result addPreservationObjectInformation() {
		Form<PreservationObjectInformation> formPreservationObjectInformation = Form.form(PreservationObjectInformation.class).bindFromRequest();
		PreservationObjectInformation poi = formPreservationObjectInformation.get();
		return ok(preservationObjectInformationTemplate.render(Form.form(PreservationObjectInformation.class), poi));
	}
	
	public Result poiUnitTest() {
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
		
		return ok(preservationObjectInformationTemplate.render(Form.form(PreservationObjectInformation.class), poi));
	}
	
}
