package si.poponline.play.module.Monitoring;

import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by samek on 23/11/2016.
 */
@With(monitoringAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface monitoring {
    String path() default "";
    boolean strip() default false;
}
