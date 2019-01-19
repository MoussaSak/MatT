package unv.skikda.Model;


import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * this class handles the PNML files it have some activities such as 
 * getting the transitions, arcs, places  or declaration . Setting the 
 * transitions, declaration text. Adds an element to a PNML file .
 * @see XmlFile
 * @see DocumentFragment
 * @author Moussa
 *
 */


public class PnmlParser extends XmlFile {

	private Document document;
	private String path;
	private NodeList transitions;
	private NodeList places;
	private NodeList arcs;
	private NodeList declaration;
	
	public PnmlParser(String path) {
		this.path = path;
		init();
	}
	
	/**
	 * this method initials the document 
	 */
	public void init(){
		try {
			this.document = readXMLFile(path);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * gets the transitions from the PNML file 
	 * @return {@link NodeList}
	 */
	public NodeList getTransitions(){
		if (document.getElementsByTagName("transition")!=null) {
			transitions = document.getElementsByTagName("transition");
		}
		return transitions;
	}
	
	/**
	 * gets the places from the PNML file
	 * @return {@link NodeList}
	 */
	public NodeList getPlaces(){
		if (document.getElementsByTagName("place")!=null) {
			places = document.getElementsByTagName("place");	
		}
		return places;
	}
	
	/**
	 * gets the arcs from the PNML files 
	 * @return {@link NodeList}
	 */
	public NodeList getArcs(){
		if (document.getElementsByTagName("arc")!=null) {
			arcs = document.getElementsByTagName("arc");	
		}
		return arcs;
	}
	
	/**
	 * gets the declaration from the PNML file
	 * @return {@link NodeList}
	 */
	public NodeList getDeclaration(){
		if (document.getElementsByTagName("declaration")!=null) {
			declaration = document.getElementsByTagName("declaration");	
		}
		return declaration;
	}
	
	/**
	 * adds a declaration node to document  
	 */
	public void addDeclaration() {
		if(!isElemExist("declaration")){
			NodeList list = document.getElementsByTagName("pnml");
			for (int i = 0; i < list.getLength(); i++) {
				NodeList list2 = list.item(i).getChildNodes();
				for (int j = 0; j < list2.getLength(); j++) {
					if(list2.item(j).getNodeName().equals("net")){
						try {
							list2.item(j).appendChild(AddFragment("declaration", "", "379", "217"));
						} catch (DOMException | TransformerException | ParserConfigurationException e) {
						e.printStackTrace();
						}
					}
				}
			}
		try {
			saveXMLfileIn(path, document);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	/**
	 * gets the PNML file name 
	 * @return  PNML file name
	 */
	public String getName(){
		String name="";
		NodeList list = document.getElementsByTagName("net");
		for (int i = 0; i < list.getLength(); i++) {
			NodeList list2 = list.item(i).getChildNodes();
			for (int j = 0; j < list2.getLength(); j++) {
				if(list2.item(j).getNodeName().equals("name")){
					name = list2.item(j).getTextContent().trim();
				}
			}
		}
		return name;
	}
	
	/**
	 * gets the transitions values from specified PNML file
	 * @return transition values
	 */
	public String[] getTransitionsValue(){
				String[] transitions = null;
				NodeList nodes = getTransitions();
				transitions = new String[nodes.getLength()];
				for (int i = 0; i < nodes.getLength(); i++) {
					transitions[i] = nodes.item(i).getAttributes().getNamedItem("id").getNodeValue();
				}
			return transitions;
	}

	/**
	 * gets the places values from the PNML file 
	 * @return  places value
	 */
	public String[] getPlacesValue(){
				String[] plcId = null;
				NodeList nodes = getPlaces();	
				plcId = new String[nodes.getLength()];
				for (int x = 0; x < nodes.getLength(); x++) {
					plcId[x] = nodes.item(x).getAttributes().getNamedItem("id").getNodeValue();
				}
		return plcId;
	}
	
	/**
	 * gets the arcs values from the PNML file
	 * @return arcs values
	 */
	public String[] getArcsValue(){
				String[] arc = null;
				NodeList nodes = getArcs();
				arc = new String[nodes.getLength()];
				for (int i = 0; i < nodes.getLength(); i++) {
					
					arc[i] =  nodes.item(i).getAttributes().getNamedItem("id").getNodeValue();
				}
			return arc;
	}
	
	/**
	 * gets the arc sources values 
	 * @return arc sources values
	 */
	public String[] getArcSourceValue() {
			String[] arcs = null ;
			NodeList nodes = getArcs();
			arcs = new String[nodes.getLength()];
			for (int i = 0; i < nodes.getLength(); i++) {
				arcs[i] =  nodes.item(i).getAttributes().getNamedItem("source").getNodeValue();
			}
		return arcs;
	}
	
	/**
	 * gets the arcs target values 
	 * @return arcs target values
	 */
	public String[] getArcTargetValue() {
			String[] tarVl = null;
			NodeList nodes = getArcs();
			tarVl = new String[nodes.getLength()];
			for (int i = 0; i < nodes.getLength(); i++) {
				tarVl[i] = nodes.item(i).getAttributes().getNamedItem("target").getNodeValue();
			}
		return tarVl;
	}
	
	/**
	 * gets the declared text 
	 * @return Declaration text
	 */
	public String getDeclarationText() {
		String decVl = null;
			NodeList nodes = getDeclaration();
			for (int i = 0; i < nodes.getLength(); i++) {
				decVl = nodes.item(i).getTextContent().trim();
			}
		return decVl;
	}
	
	/**
	 * sets the text from the declaration 
	 * @param statement the new text of the declaration
	 */
	public void setDeclarationText(String statement) {
		NodeList list = getDeclaration().item(0).getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if(list.item(i).getNodeName().equals("text")){
				list.item(i).setTextContent(statement);
			}
		}
		try {
			saveXMLfileIn(path, document);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the text from an element (arc,place,transition)
	 * @param element it could be an arc or place or transition
	 * @return the element text
	 */
	public String getText(String element) {
		String text = null;
		if(document.getElementsByTagName(element)!=null) {
			NodeList nodes = document.getElementsByTagName(element);
			for (int i = 0; i < nodes.getLength(); i++) {
				text = nodes.item(i).getTextContent();
			}
		}
		return text;
	}
	
	/**
	 * gets the text from specified node 
	 * @param element the specified element it can be arc,transition or place.
	 * @param id the element id.
	 * @return String Node text.
	 */
	public String getNodeText(String element,String id) {
		String text = "";
		if(document.getElementsByTagName("text")!=null && id!=null) {
			NodeList list = document.getElementsByTagName("text");
					for (int j = 0; j < list.getLength(); j++) {

						if(list.item(j).getParentNode().getNodeName().equals(element) 
								&& list.item(j).getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(id)){
							
							text += list.item(j).getTextContent();
							
						}if(list.item(j).getParentNode().getParentNode().getNodeName().equals(element) 
								&& list.item(j).getParentNode().getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(id)){
							
							text += list.item(j).getTextContent();
							
						}if(list.item(j).getParentNode().getParentNode().getParentNode().getNodeName().equals(element)
								&& list.item(j).getParentNode().getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(id)){
							
							text += list.item(j).getTextContent();
						}
					}
				}
		return text;
	}
	
	/**
	 * for changing text on specified element with specified type the type can be expression, create, action, uplink, 
	 * inscription,declaration  
	 * @param element transition
	 * @param id the transition id
	 * @param type can be expression, create, action, uplink, inscription.
	 * @param newText the transition text 
	 */
	public void setTransitionText(String element,String id,String type,String newText){
		if(document.getElementsByTagName("text")!=null && id!=null) {
			NodeList list = document.getElementsByTagName("text");
					for (int j = 0; j < list.getLength(); j++) {

						if(list.item(j).getParentNode().getNodeName().equals(element) 
								&& list.item(j).getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(id)){

							if(list.item(j).getNodeName().equals(type)){
								list.item(j).setTextContent(newText);
							}
							
						}if(list.item(j).getParentNode().getParentNode().getNodeName().equals(element) 
								&& list.item(j).getParentNode().getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(id)){
							
							if(list.item(j).getParentNode().getNodeName().equals(type)){
								list.item(j).setTextContent(newText);
							}
							 
							
						}if(list.item(j).getParentNode().getParentNode().getParentNode().getNodeName().equals(element)
								&& list.item(j).getParentNode().getParentNode().getAttributes().getNamedItem("id").getNodeValue().equals(id)){
							
							if(list.item(j).getParentNode().getParentNode().getNodeName().equals(type)){
								list.item(j).setTextContent(newText);
							}
						}
					}
		}
		try {
			saveXMLfileIn(path, document);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the text from sub element of a specified transition
	 * by its id
	 * @param id the transition id
	 * @param elmType the sub element of a transition such : action, inscription. 
	 * @return the text content from the element
	 */
	public String getTransitionText(String id,String elmType){
		Node node = getTransition(id);
		String txt="";
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if(list.item(i).getNodeName().equals(elmType)){
				txt = list.item(i).getTextContent().trim();
			}
		}
		return txt;
	}
	
	/**
	 * gets the transitions name by its id	
	 * @param id the id of the transition
	 * @return the transitions name
	 */
	public String getTransitionName(String id){
		String name = "";
		NodeList transitions = getTransitions();
		for (int i = 0; i < transitions.getLength(); i++) {
			if(transitions.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(id)){
				Node node = transitions.item(i);
				NodeList list = node.getChildNodes();
				for (int y = 0; y < list.getLength(); y++) {
					if(list.item(y).getNodeName().equals("name")){
						name = list.item(y).getTextContent().trim();
					}
				}
			}
		}
		return name;
	}
	
	/**
	 * gets a transition by its id.
	 * @param id the id of the transition
	 * @return {@link Node} 
	 */
	public Node getTransition(String id){
		NodeList transitions = getTransitions();
		Node node = null;
		for (int i = 0; i < transitions.getLength(); i++) {
			if(transitions.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(id)){
				node = transitions.item(i);
			}
		}
		return node;
	}
	
	/**
	 * creates a document fragment to add in case of missing 
	 * a type declaration
	 * @param nodeName it can be an action, downLink, upLink, inscription, expression, create. 
	 * @param instrument the instrument to be added to the node
	 * @param cordX the horizontal position of the instrument
	 * @param cordY the vertical position of the instrument
	 * @return {@link DocumentFragment}
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	
	public DocumentFragment AddFragment(String nodeName,String instrument,String cordX,String cordY) throws TransformerConfigurationException, TransformerException, ParserConfigurationException{
		XmlFile xmlFile = new XmlFile();
		xmlFile.creatXMLFile();
		DocumentFragment documentFragment = document.createDocumentFragment();
		Element typeName = document.createElement(nodeName);
		Element graphics = document.createElement("graphics");
		Element offset = document.createElement("offset");
		
		offset.setAttribute("x", cordX);
		offset.setAttribute("y", cordY);
		Element text = document.createElement("text");
		text.setTextContent(instrument);
		
		typeName.appendChild(graphics);
		typeName.appendChild(text);
		graphics.appendChild(offset);
		documentFragment.appendChild(typeName);
		return documentFragment;
	}
	
	/**
	 * this method tests whether an element type  exists inside 
	 * an transition
	 * @param id the Transition id
	 * @param elemType the element type to be tested 
	 * @return return true if element type exists
	 */
	
	public boolean isElemTypeExist(String id,String elemType){
			Node node = getTransition(id);
			NodeList list2 = node.getChildNodes();
			for (int j = 0; j < list2.getLength(); j++) {
				if(list2.item(j).getNodeName().equals(elemType)){
					return true;
				}
			}
		return false;
	}
	
	/**
	 * allows the the test weather an Node exist inside 
	 * an document or not.
	 * @param nodeName the name of the Node to be tested .
	 * @return true if it exist false otherwise .
	 */
	public boolean isElemExist(String nodeName){
		if(document.getElementsByTagName(nodeName).item(0)!=null)
			return true;
		return false;
	}
	
	/**
	 * this method allows a type insert in the pnml file 
	 * @param elemType it could be create or expression or other type
	 * @param id the transition to be added id
	 * @param instrument the code instrument on the transition
	 */
	public void addElemType(String elemType,String id,String instrument){
		String cordX = null;
		String cordY = null;
		if(!isElemTypeExist(id,elemType)){
			try {
				cordX = String.valueOf(50);
				cordY = String.valueOf(0);
				
				getTransition(id).appendChild(AddFragment(elemType, instrument, cordX, cordY));
				saveXMLfileIn(path, document);
			} catch (DOMException | TransformerException | ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * gets the position of the transition 
	 * @param id the transition id
	 * @return cordX and cordY
	 */
	
	public int[] getPosition(String id){
		String corX ="";
		String corY ="";
		if(getTransition(id).getChildNodes()!=null){
			NodeList list = getTransition(id).getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if(list.item(i).getNodeName().equals("graphics")){
					NodeList list2 = list.item(i).getChildNodes();
					for (int j = 0; j < list2.getLength(); j++) {
						if(list2.item(j).getNodeName().equals("position")){
							 corX = list2.item(j).getAttributes().getNamedItem("x").getNodeValue();
							 corY = list2.item(j).getAttributes().getNamedItem("y").getNodeValue();
						}
					}
				}
			}
		}
		int x = Integer.parseInt(corX);
		int y = Integer.parseInt(corY);
		int[] cor = {x,y};
		
		return cor;
	}
	
	public static void main(String[] args) {
		PnmlParser parser = new PnmlParser("C:\\Users\\Moussa.Moussa-PC\\Desktop\\test\\pro.pnml");
		System.out.println(parser.isElemTypeExist("695", "downlink"));
		
	}
	
}
