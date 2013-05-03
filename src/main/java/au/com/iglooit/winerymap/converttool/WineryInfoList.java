package au.com.iglooit.winerymap.converttool;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nzhu
 * Date: 3/05/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="WineryInfoList")
public class WineryInfoList {
    List<WineryInfo> wineryInfoList;

    public WineryInfoList() {
    }

    @XmlElements({
            @XmlElement(name="wineryInfo", type=WineryInfo.class),
    })
    @XmlElementWrapper
    public List<WineryInfo> getWineryInfoList() {
        return wineryInfoList;
    }

    public void setWineryInfoList(List<WineryInfo> wineryInfoList) {
        this.wineryInfoList = wineryInfoList;
    }

    public WineryInfoList(WineryInfoDetailsList detailsList)
    {
        wineryInfoList = new ArrayList<WineryInfo>();
        for(WineryInfoDetails details : detailsList.getWineryInfoDetailses())
        {
            WineryInfo info = new WineryInfo(details);
            wineryInfoList.add(info);
        }
    }
}
