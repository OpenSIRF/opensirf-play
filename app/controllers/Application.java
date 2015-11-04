package controllers;

import org.opensirf.audit.AuditLogReference;
import org.opensirf.audit.PreservationObjectAuditLog;
import org.opensirf.obj.Extension;
import org.opensirf.obj.ExtensionPair;
import org.opensirf.obj.PackagingFormat;
import org.opensirf.obj.PreservationObjectInformation;
import org.opensirf.obj.Retention;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.preservationObjectAuditLogTemplate;
import views.html.testButton;
import views.html.preservationObjectInformationTemplate;

public class Application extends Controller {

	public Result index() {
		return ok(index.render());
	}

//	public Result addExtension() {
//		Form<Extension> formExtension = Form.form(Extension.class).bindFromRequest();
//		Extension e = formExtension.get();
//		return ok(extensionTemplate.render(Form.form(Extension.class), e));
//	}
//
//	public Result extensionForm() {
//		return ok(extensionTemplate.render(Form.form(Extension.class), null));
//	}

	public Result addPreservationObjectInformation() {
		Form<PreservationObjectInformation> formPreservationObjectInformation = Form.form(PreservationObjectInformation.class).bindFromRequest();
		PreservationObjectInformation poi = formPreservationObjectInformation.get();
		return ok(preservationObjectInformationTemplate.render(Form.form(PreservationObjectInformation.class), poi));
	}
	
	public Result poiUnitTest() {
		PreservationObjectInformation poi = new PreservationObjectInformation();
		poi.setObjectRetention(new Retention("retType", "retValue"));
		poi.setPackagingFormat(new PackagingFormat("packFormat"));
		
		Extension e1 = new Extension("org1", "desc1");
		Extension e2 = new Extension("org2", "desc2");

		e1.getObjectExtensionPairs().add(new ExtensionPair("key1", "value1"));
		e1.getObjectExtensionPairs().add(new ExtensionPair("key2", "value2"));

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
	
	public Result testButton() {
		return ok(testButton.render());
	}

//	public Result extensionUnitTest() {
//		Extension e = new Extension("Org", "Desc");
//
//		ExtensionPair ep1 = new ExtensionPair("key1", "value1");
//		ExtensionPair ep2 = new ExtensionPair("key2", "value2");
//		ExtensionPair ep3 = new ExtensionPair("key3", "value3");
//		ExtensionPair ep4 = new ExtensionPair("key4", "value4");
//		ExtensionPair ep5 = new ExtensionPair("key5", "value5");
//
//		e.getObjectExtensionPairs().add(ep1);
//		e.getObjectExtensionPairs().add(ep2);
//		e.getObjectExtensionPairs().add(ep3);
//		e.getObjectExtensionPairs().add(ep4);
//		e.getObjectExtensionPairs().add(ep5);
//
//		return ok(extensionTemplate.render(Form.form(Extension.class), e));
//	}
}
