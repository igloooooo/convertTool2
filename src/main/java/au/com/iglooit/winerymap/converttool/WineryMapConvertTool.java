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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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

    public static void main(String... args) {
        WineryInfoDetailsList result = new WineryInfoDetailsList();
        String inputPath = (args != null && args.length > 0) ? args[0] : "C:\\playspace\\convertTool2\\temp";
        String outputPath = "C:\\playspace\\convertTool2\\output\\result.xml";
        try {
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
            System.out.println("======= end read file=====");
            ClientConfig clientConfig;
            Client client;
            WebResource webResource;
            System.out.println("======= start insert into remote db=====");
            clientConfig = new DefaultClientConfig();
            clientConfig.getClasses().add(JacksonJsonProvider.class);
            client = Client.create(clientConfig);
            webResource = client.resource(END_POINT);
            for(WineryInfoDetails wineryInfoDetails : result.getWineryInfoDetailses())
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

            System.out.println("======= end insert into remote db=====");
            System.out.println("======= out put file=====");
            Marshaller marshaller = jc.createMarshaller();
            System.out.println("======= end put file=====");
            marshaller.marshal(result, new FileOutputStream(outputPath));
        } catch (JAXBException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
