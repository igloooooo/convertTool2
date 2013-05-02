package au.com.iglooit.winerymap.converttool;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: nzhu
 * Date: 1/05/13
 * Time: 8:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    public static String[] getCoordinate(String addr) {
        String[] latLng = new String[2];
        String address = null;
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        ;
        String output = "csv";
        //key=abc
        String key = "abc";
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&sensor=false";
        URL googleMapURL = null;
        URLConnection httpsConn = null;
        // decode
        try {
            googleMapURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            httpsConn = (URLConnection)googleMapURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                if ((data = br.readLine()) != null) {
                    String[] retList = data.split(",");
                    String latitude = retList[2]; String longitude = retList[3];
                    System.out.println("Lat: "+ latitude);
                    System.out.println("Lng: "+ longitude);
                    if (retList.length > 2 && ("200".equals(retList[0]))) {
                        latLng[0] = retList[2];
                        latLng[1] = retList[3];
                    }
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    public static String[] geocode(String address)
    {
        final Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress("217 Walkers Lane, Avoca, NSW 2577").setLanguage("en").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        if(geocoderResponse.getStatus().name().equals("OK"))
        {
            LatLng location = geocoderResponse.getResults().get(0).getGeometry().getLocation();
            return new String[]{location.getLat().toString(), location.getLng().toString()};
        }
        return new String[]{"",""};
    }
}
