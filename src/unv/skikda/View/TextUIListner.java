package unv.skikda.View;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import unv.skikda.Control.MainController;

/**
 * @author Moussa
 *
 */
public class TextUIListner extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	private JMenuItem mntmCopy ;
	private JMenuItem mntmCut ;
	private JMenuItem mntmPaste ;
	private JMenuItem mntmTest;
	private String slText = "";
	private MainController controller ;
	
	public TextUIListner(JTabbedPane tabbedPane,JScrollPane scrollPane,JEditorPane editorPane) {
		controller = new MainController();
		mntmCopy = new JMenuItem("Copy");
		mntmCut = new JMenuItem("Cut");
		mntmPaste = new JMenuItem("Paste");
		mntmTest = new JMenuItem("Test Selected Text");
		
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.copy();
			}
		});
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		add(mntmCopy);
		
		mntmCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.cut();
			}
		});
		mntmCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		add(mntmCut);
		
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorPane.paste();
			}
		});
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		add(mntmPaste);
		
		mntmTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slText = editorPane.getSelectedText();
				controller.save(slText,"C:/Users/Moussa.Moussa-PC/Documents/Log.txt");
			}
		});
		mntmTest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
		add(mntmTest);
		
		addPopup(editorPane, this);
	}
	
	
	public String getSlText() {
		return slText;
	}
	
	public static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
}
