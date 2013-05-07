package au.com.iglooit.winerymap.converttool;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nzhu
 * Date: 1/05/13
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class WineryMapConvertTool {
    private static final String END_POINT = "";
    private static final String PATH_WINERY_INFO = "wineryinfo";
    private static final String POST_ADD_WINERY_INFO = "addWineryInfo";
    private static final String END_POINT_LOCAL = "http://localhost:7916/webservice/WineryMapWebService";
    private static final String OUT_PTAH = "C:\\playspace\\convertTool2\\output\\result.xml";
    private static final String WINERY_INFO_DETAILS_PTAH = "C:\\playspace\\convertTool2\\output\\winerydetails.xml";
    private static final String WINERY_INFO_PTAH = "C:\\playspace\\convertTool2\\output\\wineryinfo.xml";
    private static final String WINERY_INFO_CSV_PTAH = "C:\\playspace\\convertTool2\\output\\wineryinfo.csv";

    private static final boolean parseRawData = false;

    public static void main(String... args) {
        WineryInfoDetailsList result = new WineryInfoDetailsList();
        String inputPath = (args != null && args.length > 0) ? args[0] : "C:\\playspace\\convertTool2\\temp";
        try {
            System.out.println("======= parse data file=====");
            if(parseRawData)
            {
                result = parseRawData(inputPath);
            }
            else
            {
                result = parseRawResult(OUT_PTAH);
            }
            System.out.println("======= end parse data file=====");
            System.out.println("======= start insert into remote db=====");
            upgradeDB(result);
            System.out.println("======= end insert into remote db=====");

            System.out.println("======= export wineryInfoDetails xml =====");
            exportWineryInfoDetailsXml(result);
            System.out.println("======= export wineryInfo xml =====");
            exportWineryInfoXml(result);
            System.out.println("======= export wineryInfo csv =====");
            generateCsvFile(WINERY_INFO_CSV_PTAH,result);
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static WineryInfoDetailsList parseRawResult(String inputPath) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance("au.com.iglooit.winerymap.converttool");
        WineryInfoDetailsList result = (WineryInfoDetailsList) jc.createUnmarshaller().unmarshal(new File(inputPath));
        return result;
    }

    private static WineryInfoDetailsList parseRawData(String inputPath) throws JAXBException {
        WineryInfoDetailsList result = new WineryInfoDetailsList();
        JAXBContext jc = JAXBContext.newInstance("au.com.iglooit.winerymap.converttool");
        // find the folder
        // get file list where the path has
        File file = new File(inputPath);
        // get the folder list
        File[] array = file.listFiles();
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (array[i].isFile()) {
                System.out.println("parsing file " + i +"/"+ len);
                Extraction rawObject = (Extraction) jc.createUnmarshaller().unmarshal(array[i]);
                WineryInfoDetails detail = new WineryInfoDetails(rawObject);
                detail.setLatLng(Utils.geocode(detail.address1));
//                    System.out.println("parse element : " + detail.toString());
                result.add(detail);
            } else if (array[i].isDirectory()) {
                System.out.println("ignore the directory : " + array[i].getPath());
            }
        }
        return result;
    }

    private static void exportWineryInfoDetailsXml(WineryInfoDetailsList list) throws JAXBException, FileNotFoundException {
        JAXBContext jc = JAXBContext.newInstance("au.com.iglooit.winerymap.converttool");
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(list, new FileOutputStream(WineryMapConvertTool.WINERY_INFO_DETAILS_PTAH));
    }

    private static void exportWineryInfoXml(WineryInfoDetailsList list) throws JAXBException, FileNotFoundException {

        WineryInfoList infoList = new WineryInfoList(list);
        JAXBContext jc = JAXBContext.newInstance("au.com.iglooit.winerymap.converttool");
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(infoList, new FileOutputStream(WineryMapConvertTool.WINERY_INFO_PTAH));
    }

    private static WineryInfoDetailsList upgradeDB(WineryInfoDetailsList rawData)
    {
        ClientConfig clientConfig;
        Client client;
        WebResource webResource;
        clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(clientConfig);
        webResource = client.resource(END_POINT_LOCAL);
        for(WineryInfoDetails wineryInfoDetails : rawData.getWineryInfoDetailses())
        {
            Form f = new Form();
            f.add("address1", wineryInfoDetails.address1);
            f.add("address2", wineryInfoDetails.address2);
            f.add("description", wineryInfoDetails.description);
            f.add("facsimilie", wineryInfoDetails.facsimilie);
            f.add("lat", wineryInfoDetails.lat);
            f.add("lng", wineryInfoDetails.lng);
            f.add("name", wineryInfoDetails.name);
            f.add("region", wineryInfoDetails.region);
            f.add("telephone", wineryInfoDetails.telephone);
            f.add("website", wineryInfoDetails.website);
            f.add("winemaker", wineryInfoDetails.winemaker);
            f.add("wineryards", wineryInfoDetails.wineryards);

            String key = webResource.path(PATH_WINERY_INFO).path(POST_ADD_WINERY_INFO)
                    .accept(MediaType.APPLICATION_FORM_URLENCODED).post(String.class, f);
            wineryInfoDetails.setKeyValue(key);
        }
        return rawData;
    }

    private static void generateCsvFile(String sFileName, WineryInfoDetailsList list)
    {
        try
        {
            WineryInfoList infoList = new WineryInfoList(list);
            FileWriter writer = new FileWriter(sFileName);
            Integer i = 100;
            for(WineryInfo info : infoList.getWineryInfoList())
            {
                writer.append(info.getName());
                writer.append('|');
                writer.append(info.getKeyValue());
                writer.append('|');
                writer.append(info.getLat());
                writer.append('|');
                writer.append(info.getLng());
                writer.append('|');
                writer.append(i.toString());
                writer.append('\n');
                i++;
            }

            //generate whatever data you want

            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
