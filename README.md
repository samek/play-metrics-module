#Play Framework *2.5.10* module for monitoring route metrics.

##Usage

In your play framework project add this line to build.sbt

``` "si.poponline" % "play-metrics-module_2.11" % "1.0"```

to application config add: 

```aidl
play.poponline.graphite.host="YOUR_GRAPHITE_HOST"
play.poponline.app_name="PROJECT_NAME"
```


Make sure you have default  UDP port open on GRAPHITE_HOST

In order to use the metrics add annotation above controller/method

eg. 

```java
package controllers;

import models.joke;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import si.poponline.play.module.Monitoring.monitoring;


/**
 * Created by simke on 23/05/16.
 */
@monitoring
public class jokeController extends Controller {
    
....
....

```

If you want to grab json stats from the server you can do so by adding folowing linke to the route file

```java
GET     /_admin/stats                si.poponline.play.module.Controllers.adminController.getStats()
```


