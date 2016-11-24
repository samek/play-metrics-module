package si.poponline.play.module.Controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import si.poponline.play.module.Monitoring.monitoringLogger;

/**
 * Created by samek on 24/11/2016.
 */
public class adminController extends Controller {

    private static monitoringLogger ml = monitoringLogger.getInstance();

    public Result getStats() {
        return ok(Json.toJson(ml.getCurrentData()));
    }
}
