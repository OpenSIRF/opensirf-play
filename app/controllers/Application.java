package controllers;
import views.html.index;
import views.html.main;
import com.ibm.opensirf.object.ExtensionPair;
import play.*;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    
    public Result addExtensionPair() {
    	ExtensionPair e = Form.form(ExtensionPair.class).bindFromRequest().get();
    	System.out.println(e.getObjectExtensionKey() + " = " + e.getObjectExtensionValue());
    	return redirect(routes.Application.index());
    }
}
