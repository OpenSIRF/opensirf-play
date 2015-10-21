package controllers;

import org.opensirf.audit.AuditLogReference;
import org.opensirf.audit.PreservationObjectAuditLog;

import models.DigestInformation;
import models.Extension;
import models.ExtensionPair;
import models.FixityInformation;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.extensionTemplate;
import views.html.fixityInformationTemplate;
import views.html.index;

public class Application extends Controller {

	public Result index() {
		return ok(index.render());
	}

	public Result addExtension() {
		Form<Extension> formExtension = Form.form(Extension.class).bindFromRequest();
		Extension e = formExtension.get();
		return ok(extensionTemplate.render(Form.form(Extension.class), e));
	}

	public Result extensionForm() {
		return ok(extensionTemplate.render(Form.form(Extension.class), null));
	}

	public Result addFixityInformation() {
		Form<FixityInformation> formFixityInformation = Form.form(FixityInformation.class).bindFromRequest();
		FixityInformation e = formFixityInformation.get();
		return ok(fixityInformationTemplate.render(Form.form(FixityInformation.class), e));
	}

	public Result fixityInformationForm() {
		return ok(fixityInformationTemplate.render(Form.form(FixityInformation.class), null));
	}

	public Result addPreservationObjectAuditLog() {
		Form<PreservationObjectAuditLog> formPreservationObjectAuditLog = Form.form(PreservationObjectAuditLog.class).bindFromRequest();
		PreservationObjectAuditLog poal = formPreservationObjectAuditLog.get();
		return ok(preservationObjectAuditLogTemplate.render(Form.form(PreservationObjectAuditLog.class), poal));
	}

	public Result preservationObjectAuditLogForm() {
		return ok(preservationObjectAuditLogTemplate.render(Form.form(PreservationObjectAuditLog.class), null));
	}
		
	public Result fixityInformationUnitTest() {
		FixityInformation fi = new FixityInformation();

		DigestInformation di1 = new DigestInformation("orig1", "alg1", "val1");
		DigestInformation di2 = new DigestInformation("orig2", "alg2", "val2");
		DigestInformation di3 = new DigestInformation("orig3", "alg3", "val3");
		DigestInformation di4 = new DigestInformation("orig4", "alg4", "val4");
		DigestInformation di5 = new DigestInformation("orig5", "alg5", "val5");

		fi.getDigestInformation().add(di1);
		fi.getDigestInformation().add(di2);
		fi.getDigestInformation().add(di3);
		fi.getDigestInformation().add(di4);
		fi.getDigestInformation().add(di5);

		return ok(fixityInformationTemplate.render(Form.form(FixityInformation.class), fi));
	}
	
	public Result preservationObjectAuditLogUnitTest() {
		PreservationObjectAuditLog poal = new PreservationObjectAuditLog();

		poal.setObjectAuditLogReference(new AuditLogReference("type", "role", "value"));

		return ok(preservationObjectAuditLogTemplate.render(Form.form(PreservationObjectAuditLog.class), poal));
	}
	

	public Result extensionUnitTest() {
		Extension e = new Extension("Org", "Desc");

		ExtensionPair ep1 = new ExtensionPair("key1", "value1");
		ExtensionPair ep2 = new ExtensionPair("key2", "value2");
		ExtensionPair ep3 = new ExtensionPair("key3", "value3");
		ExtensionPair ep4 = new ExtensionPair("key4", "value4");
		ExtensionPair ep5 = new ExtensionPair("key5", "value5");

		e.getObjectExtensionPairs().add(ep1);
		e.getObjectExtensionPairs().add(ep2);
		e.getObjectExtensionPairs().add(ep3);
		e.getObjectExtensionPairs().add(ep4);
		e.getObjectExtensionPairs().add(ep5);

		return ok(extensionTemplate.render(Form.form(Extension.class), e));
	}
}
