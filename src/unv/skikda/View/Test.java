package unv.skikda.View;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;

import unv.skikda.Control.TestControl;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.xml.transform.TransformerException;

import java.awt.Font;
import java.awt.Color;

/**
 * the {@code Test} class handles the test instrumentation 
 * @author Moussa
 *
 */
public class Test extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TestControl testControl;
	private String[] methods;
	private String selMeth;
	private String selTran;
	private JTree metTree;
	private JTree tranTree ;
	private JLabel lblConsole;
	/**
	 * Create the frame.
	 */
	public Test() {
		setTitle("Test Frame");
		testControl = new TestControl();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblModelTransitions = new JLabel("Model Transitions ");
		contentPane.add(lblModelTransitions, "4, 4");
		
		JLabel lblSourceFunctions = new JLabel("Source Functions");
		contentPane.add(lblSourceFunctions, "12, 4");
		
		lblConsole = new JLabel();
		lblConsole.setFont(new Font("Times New Roman", Font.BOLD, 14));
		contentPane.add(lblConsole, "2, 10, 11, 1, default, fill");
		
		
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Transitions");
	
		for (int i = 0; i < testControl.getRnwNames().length; i++) {
			DefaultMutableTreeNode level1 = new DefaultMutableTreeNode(testControl.getRnwNames()[i]);
			testControl.setPnmlPath(testControl.getRnwPath()[i]);
			String[] transitions = testControl.getTransitionsName();
			String ids[] = testControl.getTransitionValue();
			
			for (int j = 0; j < transitions.length; j++) {
				DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(ids[j] + ","+transitions[j]);
				level1.add(level2);
			}
			
			racine.add(level1);
		}
		
		tranTree = new JTree(racine);
		tranTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if(!tranTree.isSelectionEmpty())
				selTran = tranTree.getSelectionPath().getLastPathComponent().toString();
			}
		});
		
		JScrollPane scrollPane2 = new JScrollPane(tranTree); 
		contentPane.add(scrollPane2, "4, 8, fill, fill");
		
		// c l'exemple d' un tree tab3 hada bah dir les noueds ta3ek
		DefaultMutableTreeNode racine2 = new DefaultMutableTreeNode("Methodes");
		
		for (int x = 0; x < testControl.getSrcNames().length; x++) {
			//hada c le premier niveau 
			DefaultMutableTreeNode level1 = new DefaultMutableTreeNode(testControl.getSrcNames()[x]);
			testControl.setSrcPath(testControl.getSrcPath()[x]);
			methods = testControl.getMethods();
		
			for (int z = 0; z < methods.length; z++) {
				//hada c la deuxieme niveau
				DefaultMutableTreeNode level2 = new DefaultMutableTreeNode(methods[z]);
				level1.add(level2);
			}
			
			racine2.add(level1);
		}
		
		metTree = new JTree(racine2);
		
		metTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				if(metTree.getSelectionPath()!=null)
				selMeth = metTree.getSelectionPath().getLastPathComponent().toString();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(metTree);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(metTree, popupMenu);
		
		JMenuItem mntmTest = new JMenuItem("Instrument Code in Model");
		mntmTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Instrumentation();
			}
		});
		popupMenu.add(mntmTest);
		contentPane.add(scrollPane, "12, 8, fill, fill");
		
		JButton btnDone = new JButton("Done !");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					testControl.restartProcess();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnDone, "20, 8");
		try {
			this.setIconImage(ImageIO.read(new File("ressources/Intrumentation.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		setVisible(true);
	}
	 
	  
	private static void addPopup(Component component, final JPopupMenu popup) {
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
	
	public void Instrumentation(){
	if(selTran!=null){	
		String[] res = selTran.split("\\,");
			if (res[1].equals(selMeth)) {
				int x = metTree.getSelectionPath().getPathComponent(1).toString().indexOf(".java");
				String className = metTree.getSelectionPath().getPathComponent(1).toString().substring(0, x);
				String pnmlName = tranTree.getSelectionPath().getPathComponent(1).toString();
				lblConsole.setForeground(new Color(0, 255, 127));
				for (int i = 0; i < testControl.getRnwNames().length; i++) {
					if(testControl.getRnwNames()[i].contains(pnmlName)){
						testControl.setPnmlPath(testControl.getRnwPath()[i]);
						try {
							testControl.setDeclaration(testControl.importation("ProdCon.*")+testControl.classDeclaration(className));
							if(selMeth.equals("main")){
								testControl.setTransition(res[0], "action", "action "+testControl.objectInstanstation(className));
								// the label message 
								lblConsole.setText("Method "+ selMeth +" instrumented into transition successfully ");
							
							}else{
								testControl.setTransition(res[0], "action", "action "+testControl.objectInstanstation(className));
								testControl.setTransition(res[0], "action", "action "+testControl.callMethod(className, selMeth));
								// the label message
								lblConsole.setText("Method "+ selMeth +" instrumented into transition successfully ");
							
							}
						} catch (TransformerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}else{
			lblConsole.setForeground(Color.red);
			lblConsole.setText("Please select a transition ");
		}
	}
	

}
