package unv.skikda.Model;

import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * the {@code JavaParser} class handles the java file  source it makes a static 
 * analysis for the code and retrieves constructors and methods from that it. 
 * @author Moussa
 * @see {@link Visitor}
 */
public class JavaParser extends Visitor {
	private String path;
	private FileInputStream fileInputStream;
	private CompilationUnit compilationUnit;
	
	public JavaParser(String path) {
		this.path = path;
		
		init();
	}
	
	/**
	 * it initials the file to be treaded 
	 * @exception {@link FileNotFoundException}
	 * @exception {@link ParseException}
	 * @exception {@link IOException}
	 */
	public void init(){
		
		try {
			fileInputStream = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	    try {
            try {
				compilationUnit = japa.parser.JavaParser.parse(fileInputStream);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	} finally {
            try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	/**
	 * this method retrieves constructors and methods from the java file
	 * @return an array contains constructors and methods. 
	 */
	public String[] parseMethods() {
        	visit(compilationUnit,null);
        	String methods = getConstructors()+getMethods();
        	String[] str = methods.split("\\.");
        
        return str;
    }
	
}
