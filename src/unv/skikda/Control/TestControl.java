package unv.skikda.Control;


import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.NodeList;

import unv.skikda.Model.JavaParser;
import unv.skikda.Model.PnmlParser;
import unv.skikda.View.Main;

/**
 * the {@code TestControl } it handles the {@code Test} view 
 * activities including getting and setting informations from 
 * PNML file, and from java source files
 * @author Moussa
 *
 */
public class TestControl {

	private PnmlParser pnmlParser;
	private JavaParser javaParser;
	
	/**
	 * sets the PNML file path
	 * @param path the path of the PNML file
	 */
	public void setPnmlPath(String path){
		pnmlParser = new PnmlParser(path);
	}
	
	/**
	 * sets the java source file path
	 * @param path the path of the java source file
	 */
	public void setSrcPath(String path){
		javaParser = new JavaParser(path);
	}
	
	/**
	 * gets the transitions names
	 * @return transition names
	 */
	public String[] getTransitionsName(){
		String[] tranVal = pnmlParser.getTransitionsValue();
		String[] transitions = new String[tranVal.length];
		for (int i = 0; i < transitions.length; i++) {
			transitions[i] = pnmlParser.getTransitionName(tranVal[i]);
			
		}
		return transitions;
	}
	
	/**
	 * gets the methods from the java source files
	 * @return methods from java source file
	 */
	public String[] getMethods(){
		String[] methods = javaParser.parseMethods();

		return methods;
	}
	
	/**
	 * gets the transitions values from the PNML file
	 * @return transitions values
	 */
	public String[] getTransitionValue(){
		return pnmlParser.getTransitionsValue();
	}
	
	/**
	 * gets transitions from PNML file
	 * @return {@link NodeList} of transitions
	 */
	public NodeList getTransitions(){
		return pnmlParser.getTransitions();
	}
	
	/**
	 * add an instrument to an transition 
	 * @param id the transition id
	 * @param type the type to be added or to be set on the transition
	 * @param instrument instrument to be added
	 */
	public void setTransition(String id, String type, String instrument) {
		if(pnmlParser.isElemTypeExist(id, type)){
		pnmlParser.setTransitionText("transition", id, type, pnmlParser.getTransitionText(id, type)+'\n'+instrument);
		}else{
			pnmlParser.addElemType(type, id, instrument);
		}
	
	}

	/**
	 * gets the PNML file name
	 * @return the PNML file name
	 */
	public String[] getRnwNames(){
		int lenght = Main.getController().getRnwFiles().length;
		String[] rnwPath = new String[lenght];
		File[] files =  Main.getController().getRnwFiles();
		
		for (int i = 0; i < lenght; i++) {
			rnwPath[i] = files[i].getName();
		}
		return rnwPath;
	}
	
	public String[] getSrcNames(){
		int lenght = Main.getController().getFiles().length;
		String[] srcPath = new String[lenght];
		File[] files = Main.getController().getFiles();
		
		for (int i = 0; i < lenght; i++) {
			srcPath[i] = files[i].getName();
		}
		return srcPath;
	}
	
	/**
	 * gets the PNML file path
	 * @return PNML file path
	 */
	public String[] getRnwPath(){
		int lenght = Main.getController().getRnwFiles().length;
		String[] rnwPath = new String[lenght];
		File[] files =  Main.getController().getRnwFiles();

		for (int i = 0; i < lenght; i++) {
			rnwPath[i] = files[i].getPath();
		}
		return rnwPath;
	}
	
	/**
	 * gets the java source file path
	 * @return the java source file path
	 */
	public String[] getSrcPath(){
		int lenght = Main.getController().getFiles().length;
		String[] srcPath = new String[lenght];
		File[] files = Main.getController().getFiles();
		
		for (int i = 0; i < lenght; i++) {
			srcPath[i] = files[i].getPath();
		}
		return srcPath;
	}
	
	/**
	 * this method restarts the Renew application
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void restartProcess() throws IOException, InterruptedException{
		String comm ="TASKKILL /F /IM cmd.exe /T";
		String[] command = {"cmd.exe", "/c", "start "+ comm};
		
		Runtime.getRuntime().exec(command);
		
		Thread.sleep(500);
		Main.getController().launchRenew();
	}
	
	public String objectInstanstation(String className){
		char c = className.toLowerCase().charAt(0);
		String stm = c +" = new "+className+"();"+'\n';
		
		return stm;
		
	 }
	 
	/**
	 * this method is a template for calling a method.
	 * @param className the class name 
	 * @param method the method to be called name
	 * @return a method declaration template
	 */
	 public String callMethod(String className,String method){
		 char c = className.toLowerCase().charAt(0);
		 String stm = c+"."+method+"();"+'\n';
		 return stm;
	 }
	 
	 /**
	  * a template of a class declaration 
	  * @param className the class name
	  * @return  a class declaration template
	  */
	 public String classDeclaration(String className){
		 char c = className.toLowerCase().charAt(0);
		 String stm = className+" "+c +";"+'\n';
		 return stm;
	 }
	 
	 /**
	  * a template of package importation 
	  * @param toBeImported the package to be imported
	  * @return importation template
	  */
	 public String importation(String toBeImported){
		 String stm = "import "+toBeImported+";"+'\n'; 
		 
		 return stm;
	 }
	
	 /**
	  * sets the declaration text, it creates a declaration if necessary 
	  * @param stm the statement to be added
	  * @throws TransformerConfigurationException
	  * @throws TransformerException
	  */
	 public void setDeclaration(String stm) throws TransformerConfigurationException, TransformerException{
		 String txt = pnmlParser.getDeclarationText()+'\n';
		 if(pnmlParser.isElemExist("declaration")){
			 if(txt.contains(stm)){
				 pnmlParser.setDeclarationText(txt);
			 }else{
			pnmlParser.setDeclarationText(stm+txt);
			}
		 }else{
			 pnmlParser.addDeclaration();
			 pnmlParser.setDeclarationText(stm);
		 }
	 }
	 
	 /**
	  * gets the PNML parser
	  * @return {@code PnmlParser}
	  */
	 public PnmlParser getPnmlParser() {
		return pnmlParser;
	}
	 
	 /**
		 * this method gets the method body 
		 * @param name the method name 
		 * @return the method body
		 */
		public String getMethBody(String name){
			String body = "";
			if(name!=null){
				body = javaParser.getBody().get(name);
			}
			return body;
		} 
	 
}
