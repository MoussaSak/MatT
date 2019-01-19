

package unv.skikda.Control;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import unv.skikda.View.TextUIListner;

/**
 * the {@code MainController} class is the controller for the {@code Main} View
 * it includes methods for reading and setting loaded source files  
 * @author Moussa
 */

public class MainController {
	private File[] files;
	private File[] rnwFiles;
	
	/**
	 * gets the sources files 
	 * @return {@link File}
	 */
	public File[] getFiles() {
		return files;
	}
	
	/**
	 * sets the files values
	 * @param files new files values 
	 */
	public void setFiles(File[] files) {
		this.files = files;
	}
	
	/**
	 * gets PNML files 
	 * @return {@link File}
	 */
	public File[] getRnwFiles() {
		return rnwFiles;
	}
	
	/**
	 * sets the PNML files 
	 * @param rnwFiles new PNML files 
	 */
	public void setRnwFiles(File[] rnwFiles) {
		this.rnwFiles = rnwFiles;
	}
	
	/**
	 * Reads the source file 
	 * @param file the file to be ridden
	 * @return {@link String}
	 * @exception IOException
	 */
	public String read(File file){
		char a;
		String txt = "";

		try {
			byte[] bytes = Files.readAllBytes(file.toPath());
			for (byte b : bytes) {
				 a = (char) b;
				 txt += a;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return txt;
	}
	
	/**
	 * saves a string to a specified path 
	 * @param str the text to be saved
	 * @param path the new file path
	 * @exception IOException
	 */
	public void save(String str,String path){
		File file = new File(path);
		if(!file.exists()){
			try {
				Files.write(file.toPath(), str.getBytes(), StandardOpenOption.CREATE_NEW);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Handle the java source JTabbedPane from the main application 
	 * @param tabbedPane the tapped pane to be handled
	 * @param listner  listener to be added to the component
	 */
	public void rightTabbedControl(JTabbedPane tabbedPane,TextUIListner listner){
		
		for (int i = 0; i < files.length; i++) {
			
			String source = this.read(files[i]);
			JEditorPane editorPane = new JEditorPane();
			JScrollPane scrollPane = new JScrollPane(editorPane);
			String tabName = files[i].getName();
			
			jsyntaxpane.DefaultSyntaxKit.initKit();
			editorPane.setContentType("text/java");
			
			editorPane.setText(source);
			editorPane.setSelectedTextColor(Color.red);
			listner = new TextUIListner(tabbedPane, scrollPane,editorPane);
			tabbedPane.addTab(tabName,null,scrollPane,tabName);
			
		}
	}
	
	/**
	 * Handle the left JTabbed pane from the main application
	 * @param tabbedPane the tabbedPane to be handled 
	 */
	public void leftTabbedControl(JTabbedPane tabbedPane){
		fillModelTabbedPane(tabbedPane);
		launchRenew();
	}
	
	/**
	 * for launching the renew workshop it passes the renew files paths 
	 * as arguments 
	 * @exception IOException
	 */
	public void launchRenew() {
			
			String comm = "renew ";
			String path = "";
			int lenght = rnwFiles.length;
			for (int i = 0; i < lenght; i++) {
				path = rnwFiles[i].getPath();
				comm += path + " ";
			}
			
			String[] command = {"cmd.exe", "/c", "start "+ comm};
			try {
				Runtime.getRuntime().exec(command);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		/**
		 * the source browse button handler on the main application		
		 * @param textField_1 the text filed to be set
		 * @param tabbedPane the tabbedPane to be added
		 * @param listner the listener to be added to the component 
		 */
		public void browseSourceHandler(JTextField textField_1,JTabbedPane tabbedPane,TextUIListner listner){
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		FileFilter filter = new FileNameExtensionFilter("Java File .java", "java");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(null);
			if (chooser.getSelectedFile()!= null) {
				files = chooser.getSelectedFiles();
				this.setFiles(files);
				textField_1.setText(files[0].getAbsolutePath());
				this.rightTabbedControl(tabbedPane, listner);
				
			}
		}
	
		/**
		 * the model browse button handler on the main application 
		 * @param textFieldModel the text field to be set 
		 * @param tabbedPaneModel the tabbedPanne to be added 
		 * @exception IOException
		 */
	public void browseModelHandler(JTextField textFieldModel,JTabbedPane tabbedPaneModel){
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		FileFilter filter = new FileNameExtensionFilter("Pnml File .pnml", "pnml");
		chooser.setFileFilter(filter);
		chooser.showOpenDialog(null);
		if (chooser.getSelectedFile()!= null) {
			rnwFiles = chooser.getSelectedFiles();
			try {
				this.setRnwFiles(copyFiles(rnwFiles));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			textFieldModel.setText(rnwFiles[0].getAbsolutePath());
			this.leftTabbedControl(tabbedPaneModel);
		}
	
	}
	
	/**
	 * the model tappedPanne handler 
	 * @param tabbedPane the tabbedPane to be handled 
	 */
	public void fillModelTabbedPane	(JTabbedPane tabbedPane){
		
		for (int i = 0; i < rnwFiles.length; i++) {
			String source = read(rnwFiles[i]);
			JEditorPane editorPane = new JEditorPane();
			JScrollPane scrollPane = new JScrollPane(editorPane);
			jsyntaxpane.DefaultSyntaxKit.initKit();
			editorPane.setContentType("text/xml");
			editorPane.setText(source);
			tabbedPane.addTab(rnwFiles[i].getName(),null,scrollPane,null);
		}
	
	}
	
	/**
	 * this method copies a group of files to specified directory
	 * calls <i>testFiles</i>. 
	 * @param files a group of files.
	 * @return the copied files.
	 * @throws IOException
	 */
	public File[] copyFiles(File[] files) throws IOException{
		File[] copiedFiles = new File[files.length];
		File parentFile = files[0].getParentFile();
		String testPath = parentFile.getAbsolutePath()+"/testFiles";
		File testFile = new File(testPath);
		testFile.mkdir();
		
		 for (int i = 0; i < files.length; i++) {
			String newPath = testPath+'/'+files[i].getName();
            save(read(files[i]), newPath);
            copiedFiles[i] = new File(newPath);
         }
		 return copiedFiles;
	}
	
}
	