//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.01 at 10:35:08 AM EST 
//


package au.com.iglooit.winerymap.converttool;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.com.iglooit.winerymap.converttool package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.com.iglooit.winerymap.converttool
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Extraction }
     * 
     */
    public Extraction createExtraction() {
        return new Extraction();
    }

    /**
     * Create an instance of {@link Extraction.WineryInfoDetails }
     * 
     */
    public Extraction.WineryInfoDetails createExtractionWineryInfoDetails() {
        return new Extraction.WineryInfoDetails();
    }

    /**
     * Create an instance of {@link Extraction.BaseURI }
     * 
     */
    public Extraction.BaseURI createExtractionBaseURI() {
        return new Extraction.BaseURI();
    }

    /**
     * Create an instance of {@link Extraction.WineryInfoDetails.Item }
     * 
     */
    public Extraction.WineryInfoDetails.Item createExtractionWineryInfoDetailsItem() {
        return new Extraction.WineryInfoDetails.Item();
    }

    /**
     * Create an instance of {@link Extraction.BaseURI.BucketBaseURI }
     * 
     */
    public Extraction.BaseURI.BucketBaseURI createExtractionBaseURIBucketBaseURI() {
        return new Extraction.BaseURI.BucketBaseURI();
    }

    public WineryInfoDetails createWineryInfoDetails()
    {
        return new WineryInfoDetails();
    }

    public WineryInfoDetailsList createWineryInfoDetailsList()
    {
        return new WineryInfoDetailsList();
    }

}