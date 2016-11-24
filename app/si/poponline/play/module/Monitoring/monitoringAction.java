package si.poponline.play.module.Monitoring;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

/**
 * Created by samek on 23/11/2016.
 */
public class monitoringAction extends Action<monitoring> {

    public monitoringAction() {
        super();
    }

    private static monitoringLogger ml = monitoringLogger.getInstance();
    @Override
    public CompletionStage<Result> call(Http.Context ctx) {

        long now = System.currentTimeMillis();

        String configuration_path = configuration.path();
        boolean strip_path = configuration.strip();



        try {
            return delegate.call(ctx);
        }
        finally {

            Http.Request request = ctx.request();

            String request_path = request.path();

            request_path = request_path.replace(".","-");
            if (!configuration_path.equals("") && !strip_path) {
                request_path = request_path.replace(configuration_path,configuration_path.replace("/","."));
                request_path = request_path.replace("/","-");
                //Logger.info("path: "+request_path);
            }

            if (!configuration_path.equals("") && strip_path) {
                request_path = configuration_path.replace("/",".");
                //Logger.info("Stripam path: "+request_path);
            }

            long elapsedTime = System.currentTimeMillis() - now;
            //Logger.debug("[{}] response time ms: {} {}", request_path, elapsedTime,configuration.path());
            ml.insertLog(request_path, elapsedTime);
        }
    }
}
