package unv.skikda.Model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;


/**
 * This class is for handling the XML Files, it Creates, reads and saves 
 * XML files  
 * @see Document
 * @see DocumentBuilder
 * @see DocumentBuilderFactory
 * @author Moussa
 */
public class XmlFile {
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document document;
    
    public XmlFile() {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Instantiation of the document file
     * @throws ParserConfigurationException
     */
    public void creatXMLFile() throws ParserConfigurationException {
        document = docBuilder.newDocument();
	
        
    }
    
    /**
     * reads the XML file 
     * @param path the XML file path
     * @return {@link Document}
     * @throws SAXException
     * @throws IOException
     */
    public Document readXMLFile(String path) throws SAXException, IOException {
        File fXmlFile = new File(path);
        document = docBuilder.parse(fXmlFile);
        return document;
    }
    
    /**
     * gets the document
     * @return {@link Document}
     */
    public Document getDocument() {
        return this.document;
    }
    
    /**
     * saves the XML file
     * @param path the XML file path
     * @param paramdoc the document to be saved
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws TransformerException
     * @throws TransformerConfigurationException
     */
    public void saveXMLfileIn(String path, Document paramdoc) throws TransformerConfigurationException, TransformerException , TransformerException , TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	Transformer transformer = transformerFactory.newTransformer();
	DOMSource source = new DOMSource(paramdoc);
	StreamResult result = new StreamResult(new File(path));
	transformer.transform(source, result);
    }
    
}
