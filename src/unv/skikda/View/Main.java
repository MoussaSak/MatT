package unv.skikda.View;

import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import unv.skikda.Control.MainController;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;

/**
 * Creates the Application Main Frame 
 * it contains JtabbedPane and PopupMenu, JMenuBar
 * 
 * 
 * @author Moussa
 *  
 */

public class Main extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static MainController controller;
	private JTextField textFieldModel;
	private JTextField textFieldSrc;
	private JTabbedPane tabbedPaneSrc;
	private JTabbedPane tabbedPaneModel ;
	private TextUIListner listner;

	public Main() {
		controller = new MainController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2;
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2;
		setBounds(100, 100,width,height);
		

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openHandler();
			}
		});
		try {
			mntmOpen.setIcon(new ImageIcon(ImageIO.read(new File("ressources/open.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		
		mnFile.add(mntmOpen);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JScrollPane pane = (JScrollPane) tabbedPaneSrc.getSelectedComponent();
				tabbedPaneSrc.remove(pane);
				if (tabbedPaneModel.getSelectedComponent()!=null) {
					JScrollPane modelPane = (JScrollPane) tabbedPaneModel.getSelectedComponent();
					tabbedPaneModel.remove(modelPane);
				}
			}
		});
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnFile.add(mntmClose);
		
		JMenuItem mntmCloseAll = new JMenuItem("Close All");
		mntmCloseAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPaneSrc.removeAll();
			}
		});
		mntmCloseAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
		mnFile.add(mntmCloseAll);
		
		/**
		JMenuItem mntmSave = new JMenuItem("Save");
		try {
			mntmSave.setIcon(new ImageIcon(ImageIO.read(new File("ressources/save.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		*/
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		try {
			mntmExit.setIcon(new ImageIcon(ImageIO.read(new File("ressources/exit.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnConfigure = new JMenu("Configure");
		menuBar.add(mnConfigure);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		
		try {
			mntmHelp.setIcon(new ImageIcon(ImageIO.read(new File("ressources/help.gif"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		mnHelp.add(mntmHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
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
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, "1, 2, 64, 1");
		
		JButton btnOpen = new JButton();
		try {
			btnOpen.setIcon(new ImageIcon(ImageIO.read(new File("ressources/open.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnOpen.setToolTipText("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openHandler();
			}
		});
		toolBar.add(btnOpen);
		
		/**
		JButton btnSave = new JButton();
		try {
			btnSave.setIcon(new ImageIcon(ImageIO.read(new File("ressources/save.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnSave.setToolTipText("Save");
		toolBar.add(btnSave);
		*/
		JButton btnExecute = new JButton();
		try {
			btnExecute.setIcon(new ImageIcon(ImageIO.read(new File("ressources/run.png"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnExecute.setToolTipText("Add Instrument");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getController().getRnwFiles()!=null && getController().getFiles()!=null)
				new Test();
			}
		});
		toolBar.add(btnExecute);

		JButton btnRefresh = new JButton();
		try {
			btnRefresh.setIcon(new ImageIcon(ImageIO.read(new File("ressources/refresh.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnRefresh.setToolTipText("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tabbedPaneModel.getTabCount()!=0){
				tabbedPaneModel.removeAll();
				controller.fillModelTabbedPane(tabbedPaneModel);
				}
			}
		});
		toolBar.add(btnRefresh);
		
		JLabel lblModel = new JLabel("   Model(s)  ");
		contentPane.add(lblModel, "2, 4");
		
		textFieldModel = new JTextField();
		contentPane.add(textFieldModel, "4, 4, 23, 1");
		textFieldModel.setColumns(10);
		
		JButton BrowseModel = new JButton("Browse");
		BrowseModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.browseModelHandler(textFieldModel, tabbedPaneModel);
			}
		});
		contentPane.add(BrowseModel, "28, 4");
		
		JLabel lblSourceFiles = new JLabel("Source File(s)");
		contentPane.add(lblSourceFiles, "30, 4, right, default");
		
		textFieldSrc = new JTextField();
		contentPane.add(textFieldSrc, "32, 4, 23, 1");
		textFieldSrc.setColumns(10);
		
		JButton BrowseSource = new JButton("Browse");
		BrowseSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.browseSourceHandler(textFieldSrc, tabbedPaneSrc, listner);		
				}
		});
		contentPane.add(BrowseSource, "56, 4");
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, "2, 6, 56, 11, fill, fill");
		
		tabbedPaneSrc = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setRightComponent(tabbedPaneSrc);
		
		tabbedPaneModel = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPaneModel);
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(getWidth()/2);
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
		
		setTitle("MatT Multi agent test Tool");
		
		Image image = null;
		try {
			image = ImageIO.read(new File("ressources/MatT.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		setIconImage(image);
		setVisible(true);
	}
	
	/**
	 * gets the controller 
	 * @return {@link MainController}
	 */
	public static MainController getController() {
		return controller;
	}
	
	/**
	 * the button open handler 
	 */
	public void openHandler(){
		JFrame frame = new JFrame();
		frame.setTitle("Open");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 329, 240);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnModel = new JButton("Model");
		btnModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			controller.browseModelHandler(textFieldModel, tabbedPaneModel);	
			}
		});
		btnModel.setBounds(10, 11, 145, 180);
		contentPane.add(btnModel);
		
		JButton btnSource = new JButton("Source");
		btnSource.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			controller.browseSourceHandler(textFieldSrc, tabbedPaneSrc, listner);
			}
		});
		btnSource.setBounds(165, 11, 138, 180);
		contentPane.add(btnSource);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	

}
