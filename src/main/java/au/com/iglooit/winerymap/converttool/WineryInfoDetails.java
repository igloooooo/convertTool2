package au.com.iglooit.winerymap.converttool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: nzhu
 * Date: 1/05/13
 * Time: 8:33 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="WineryInfoDetails")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class WineryInfoDetails {
    String name;
    String region;
    String address1;
    String address2;
    String website;
    String telephone;
    String winemaker;
    String wineryards;
    String facsimilie;
    String description;
    String lat;
    String lng;
    String keyValue;

    public WineryInfoDetails()
    {

    }

    public WineryInfoDetails(Extraction obj)
    {
        this();
        name = obj.getWineryInfoDetails().getItem().name.trim();
        region = obj.getWineryInfoDetails().getItem().region.trim();
        address1 = obj.getWineryInfoDetails().getItem().address1.trim().replaceAll("\\n", " ");
        address2 = obj.getWineryInfoDetails().getItem().address2.trim().replaceAll("\\n", " ");
        website = obj.getWineryInfoDetails().getItem().website.trim();
        telephone = obj.getWineryInfoDetails().getItem().telephone.trim();
        winemaker = obj.getWineryInfoDetails().getItem().winemaker.trim();
        facsimilie = obj.getWineryInfoDetails().getItem().facsimilie.trim();
        wineryards = obj.getWineryInfoDetails().getItem().wineryards.trim();
        description = obj.getWineryInfoDetails().getItem().description.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWinemaker() {
        return winemaker;
    }

    public void setWinemaker(String winemaker) {
        this.winemaker = winemaker;
    }

    public String getWineryards() {
        return wineryards;
    }

    public void setWineryards(String wineryards) {
        this.wineryards = wineryards;
    }

    public String getFacsimilie() {
        return facsimilie;
    }

    public void setFacsimilie(String facsimilie) {
        this.facsimilie = facsimilie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setLatLng(String[] latLng) {
        if(latLng != null && latLng.length > 1)
        {
            this.lat = latLng[0];
            this.lng = latLng[1];
        }
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
