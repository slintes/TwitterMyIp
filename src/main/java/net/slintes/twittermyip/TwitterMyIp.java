package net.slintes.twittermyip;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: msluiter
 * Date: 06.01.14
 * Time: 14:23
 */
public class TwitterMyIp implements OAuthConfig {

    public static void main(String[] args) throws Exception {
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();

        // get ip adresses and concat them
        String date = SimpleDateFormat.getDateTimeInstance().format(new Date());
        String tweet = date + " - ";
        while(interfaces.hasMoreElements())
        {
            NetworkInterface ni = (NetworkInterface) interfaces.nextElement();
            Enumeration addresses = ni.getInetAddresses();

            // skip loopback
            if(ni.getName().equals("lo")) continue;

            if(addresses.hasMoreElements()){
                tweet += ni.getName() + ": ";
            }
            while(addresses.hasMoreElements())
            {
                InetAddress a = (InetAddress) addresses.nextElement();
                tweet += (a.getHostAddress() + "  ");
            }
        }

        // System.out.println(tweet);

        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        AccessToken at = new AccessToken(accessToken, accessTokenSecret);
        twitter.setOAuthAccessToken(at);

        twitter.updateStatus(tweet);
    }
}
