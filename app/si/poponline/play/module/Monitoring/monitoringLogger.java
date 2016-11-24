package si.poponline.play.module.Monitoring;

import si.poponline.play.module.Statistics.Statistics;
import si.poponline.play.module.Senders.UDPClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by samek on 24/11/2016.
 */
public class monitoringLogger {
    private static monitoringLogger ourInstance = new monitoringLogger();

    public static monitoringLogger getInstance() {
        return ourInstance;
    }

    private monitoringLogger() {
    }

    private Integer graphiteDump = 30;
    private Integer cleanUpAfter = 300;
    private UDPClient uclient = new UDPClient();
    private Integer lastCleanUp=0;
    private Integer lastGraphiteSend=0;
    private ConcurrentHashMap<String,Statistics> logged = new ConcurrentHashMap<String,Statistics>();

    private Integer getUnixTime() {
        return (int) (System.currentTimeMillis()/1000L );
    }


    private Integer getStatsWindowTime() {

        return this.getUnixTime() - this.lastCleanUp;
    }

    private void send2graphite() {
        Statistics ma;
        String url = "";
        Integer currentWindow = this.getStatsWindowTime();
        for (Map.Entry<String,Statistics> entry: this.logged.entrySet()) {
            url  = entry.getKey();
            ma = entry.getValue();
            url = url.replace("%2F",".");
            url = url.replace("?","-");
            url = url.replace("&","-");
            //url = ma.url.replace("/",".");

            if (url.equals("."))
                continue;
            Integer  rqps = (int) (ma.populationSize() /currentWindow);

            uclient.Send(url ,Long.toString(ma.max()), "max");
            uclient.Send(url ,Long.toString(ma.min()),"min");
            uclient.Send(url ,Double.toString(ma.mean()),"mean");
            uclient.Send(url,Integer.toString(rqps),"rqps");

        }
    }

    public void insertLog(String url, Long tookTime) {


        Statistics ma;


        if (this.lastCleanUp==0) {
            this.lastCleanUp = this.getUnixTime();
        }

        if (this.lastGraphiteSend==0) {
            this.lastGraphiteSend= this.getUnixTime();
        }



        if (this.getUnixTime() - this.lastCleanUp > this.cleanUpAfter) {
            this.send2graphite();
            this.logged.clear();
            this.lastCleanUp = this.getUnixTime();
        }

        if (this.getUnixTime() - this.lastGraphiteSend > this.graphiteDump) {
            this.send2graphite();
            this.lastGraphiteSend = this.getUnixTime();
        }

        if (this.logged.containsKey(url)) {
            //We have it in..
            ma = this.logged.get(url);
            ma.accumulate(tookTime);
            this.logged.put(url,ma);

        } else {
            ma = new Statistics();
            //We add a new one//
            ma.accumulate(tookTime);
            this.logged.put(url,ma);
        }
    }


    public ArrayList<monitAsset> getCurrentData() {

        ArrayList<monitAsset> mar = new ArrayList<monitAsset>();
        for (Map.Entry<String,Statistics> entry: this.logged.entrySet()) {
            Statistics stat = entry.getValue();
            monitAsset ma = new monitAsset();
            ma.maxTime = stat.max();
            ma.minTime = stat.min();
            ma.meanTime = stat.mean();
            ma.url = entry.getKey();
            mar.add(ma);
        }

        return mar;

    }
    public class monitAsset {
        public long minTime=0;
        public long maxTime=0;
        public Double  meanTime=0.0;
        public String  url = "";
    }
}
