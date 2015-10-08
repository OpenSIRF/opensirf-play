package controllers;
import org.opensirf.obj.Extension;
import org.opensirf.obj.ExtensionPair;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.extensionPairTemplate;
import views.html.extensionTemplate;
import views.html.index;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result addExtensionPair() {
    	Form<ExtensionPair> formExtensionPair = Form.form(ExtensionPair.class).bindFromRequest();
    	ExtensionPair e = formExtensionPair.get();
		return ok("Extension pair key: " + e.getObjectExtensionKey() +
				"; value: " + e.getObjectExtensionValue());
    }
    
    public Result addExtension() {
	  Form<Extension> formExtension = Form.form(Extension.class).bindFromRequest();
	  Extension e = formExtension.get();
	  return ok("Extension organization: " + e.getObjectExtensionOrganization() +
			  "; description: " + e.getObjectExtensionDescription());
    }

    public Result loadExtensionPairForm() {
        return ok(extensionPairTemplate.render(Form.form(ExtensionPair.class)));
    }
    
    public Result loadExtensionForm() {
        return ok(extensionTemplate.render(Form.form(Extension.class)));
    }
}
