package si.poponline.play.module.Senders;

import com.typesafe.config.ConfigFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by samek on 24/11/2016.
 */
public class UDPClient {

    private static String udpHost="127.0.0.1";
    private static String appName="my-play-app";

    public UDPClient(String udpHost, String appName) {
        this.udpHost = udpHost;
        this.appName = appName;
    }

    public static void Send(String packet, String value, String type) {

        try {
            //String host = ConfigFactory.load().getString("play.poponline.graphite.host");
            //String appname = ConfigFactory.load().getString("play.poponline.app_name");
            int port = 2003;

            Integer unixTime = (int) (System.currentTimeMillis() / 1000L);
            byte[] message = (
                    "apps."+appName +packet+"."+type+" "+value+" "+unixTime.toString()
            ).getBytes();

            // Get the internet address of the specified host
            InetAddress address = InetAddress.getByName(udpHost);

            // Initialize a datagram packet with data and address
            DatagramPacket dgram = new DatagramPacket(message, message.length, address, port);

            // Create a datagram socket, send the packet through it, close it.
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(dgram);
            //System.out.println("SYSTEM OUT: sending UDP: "+message);
            dsocket.close();
        } catch (Exception e) {
            System.err.println(e);
            System.out.println("SYSTEM ERROR: cannot send UDP: "+e);
        }
    }

}
