package au.com.iglooit.winerymap.converttool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: nzhu
 * Date: 3/05/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="WineryInfo")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class WineryInfo {
    String name;
    String lat;
    String lng;
    String keyValue;

    public WineryInfo() {
    }

    public WineryInfo(WineryInfoDetails details)
    {
        name = details.name;
        lat = details.lat;
        lng = details.lng;
        keyValue = details.keyValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
