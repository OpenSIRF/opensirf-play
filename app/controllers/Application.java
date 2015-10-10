package controllers;
import models.Extension;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.extensionTemplate;
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
}
