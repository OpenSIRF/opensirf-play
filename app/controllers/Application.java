package controllers;
import models.Extension;
import models.ExtensionPair;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.extensionTemplate;
import views.html.index;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public Result addExtension() {
	  Form<Extension> formExtension = Form.form(Extension.class).bindFromRequest();
	  Extension e = formExtension.get();
	  
	  StringBuffer sb = new StringBuffer();
	  
	  sb.append("Extension:\n");
	  sb.append("\n");
	  sb.append("Organization: " + e.getObjectExtensionOrganization() + "\n");
	  sb.append("Description: " + e.getObjectExtensionDescription() + "\n");
	  sb.append("\n");
	  sb.append("Extension pairs:\n");
	  
	  for(ExtensionPair ep: e.getObjectExtensionPairs()) {
		  sb.append("Key: " + ep.getObjectExtensionKey() +
				  " Value: " + ep.getObjectExtensionValue() + "\n");
	  }
	  
	  return ok(new String(sb));
    }
    
    public Result loadExtensionForm() {
        return ok(extensionTemplate.render(Form.form(Extension.class)));
    }
}
