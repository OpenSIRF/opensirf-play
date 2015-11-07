package controllers;

import org.opensirf.audit.AuditLogReference;
import org.opensirf.audit.PreservationObjectAuditLog;
import org.opensirf.obj.DigestInformation;
import org.opensirf.obj.Extension;
import org.opensirf.obj.ExtensionPair;
import org.opensirf.obj.FixityInformation;
import org.opensirf.obj.PackagingFormat;
import org.opensirf.obj.PreservationObjectInformation;
import org.opensirf.obj.Retention;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.preservationObjectInformationTemplate;
import views.html.preservationObjectAuditLogTemplate;

public class Application extends Controller {

	public Result index() {
		return ok(index.render());
	}

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
		
		return ok(preservationObjectInformationTemplate.render(Form.form(PreservationObjectInformation.class), poi));
	}
	
	public Result addPreservationObjectAuditLog() {
		Form<PreservationObjectAuditLog> formPreservationObjectAuditLog = Form.form(PreservationObjectAuditLog.class).bindFromRequest();
		PreservationObjectAuditLog poal = formPreservationObjectAuditLog.get();
		return ok(preservationObjectAuditLogTemplate.render(Form.form(PreservationObjectAuditLog.class), poal));
	}
	
	public Result preservationObjectAuditLogForm() {
		return ok(preservationObjectAuditLogTemplate.render(Form.form(PreservationObjectAuditLog.class), null));
	}
	
	public Result preservationObjectAuditLogUnitTest() {
		PreservationObjectAuditLog poal = new PreservationObjectAuditLog();

		poal.setObjectAuditLogReference(new AuditLogReference("type", "role", "value"));

		return ok(preservationObjectAuditLogTemplate.render(Form.form(PreservationObjectAuditLog.class), poal));
	}
}
