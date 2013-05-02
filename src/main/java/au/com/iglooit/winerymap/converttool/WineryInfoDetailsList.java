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
 * Date: 1/05/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="WineryInfoDetailsList")
public class WineryInfoDetailsList {
    List<WineryInfoDetails> wineryInfoDetailses;

    public WineryInfoDetailsList() {
        wineryInfoDetailses = new ArrayList<WineryInfoDetails>();
    }

    public WineryInfoDetailsList(List<WineryInfoDetails> wineryInfoDetailses) {
        this.wineryInfoDetailses = wineryInfoDetailses;
    }
    @XmlElements({
            @XmlElement(name="wineryInfoDetails", type=WineryInfoDetails.class),
    })
    @XmlElementWrapper
    public List<WineryInfoDetails> getWineryInfoDetailses() {
        return wineryInfoDetailses;
    }

    public void setWineryInfoDetailses(List<WineryInfoDetails> wineryInfoDetailses) {
        this.wineryInfoDetailses = wineryInfoDetailses;
    }

    public void add(WineryInfoDetails details)
    {
        this.wineryInfoDetailses.add(details);
    }
}
