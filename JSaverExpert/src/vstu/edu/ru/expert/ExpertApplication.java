package vstu.edu.ru.expert;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import saver.common.FileInfo;
import saver.common.NodeInfo;
import saver.common.StorageInfo;
import vstu.edu.ru.expert.agents.JSaverCoordinator;
import vstu.edu.ru.expert.agents.JSaverCustomer;
import vstu.edu.ru.expert.agents.JSaverSubordinator;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ExpertApplication extends javax.swing.JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 536944877310104609L;
	public static EventListenerList listenerList = new EventListenerList();
	private JTabbedPane tabs;
	private JPanel overwiewPane;
	private JButton jRestoreButton;
	private JButton jSaveNodesInfoButton;
	private JPasswordField adminpassField;
	private JLabel jLabel4;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JPanel jPanel4;
	private JPanel nodesPane;
	private JLabel jLabel3;
	/**
	 * @uml.property  name="aboutPanel"
	 */
	private JPanel aboutPanel;
	private JButton jClearStorageButton;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JTextField jDispersionFileName;
	private JButton jBrowseButton;
	private JPanel recoveryPanel;
	private JLabel jLabel1;
	private JPanel jOverwiewInfoPanel;
	public JTree jOverviewTree;
	private JPanel dispersalPanel;
	private JPanel jPanel10;
	public JPanel jProgressBarPanel;
	private JPanel jPanel8;
	public JTree jFilesTree;
	public JTree jRecoveryStoragesTree;
	public JTree jDispersionStoragesTree;
	private JScrollPane jScrollPane6;
	private JLabel jLabel7;
	private JLabel jLabel6;
	/**
	 * @uml.property  name="buttonGroup1"
	 */
	private ButtonGroup buttonGroup1;
	private JRadioButton jRadioButton2;
	private JRadioButton jRadioButton1;
	private JPanel jPanel1;
	private JLabel jSummaryLabel;
	private JButton jRemoveButton;
	private JPanel jTreePanel;
	private JTextArea logArea;
	private JScrollPane jScrollPane2;
	private JPanel jLogPanel;
	private JPanel jMainPanel;
	private JPanel jCommonPanel;
	private JButton jDisperseButton;
	private JLabel jLabel5;
	private JScrollPane jScrollPane3;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	
	/**
	 * @uml.property  name="agentCoordinator"
	 * @uml.associationEnd  
	 */
	private JSaverCoordinator agentCoordinator;
	/**
	 * @uml.property  name="agentSubordinator"
	 * @uml.associationEnd  
	 */
	private static JSaverSubordinator agentSubordinator;
	/**
	 * @uml.property  name="agentCustomer"
	 * @uml.associationEnd  
	 */
	private JSaverCustomer agentCustomer;
	
	private DefaultTreeModel nodesTreeModel;
	private DefaultTreeModel storagesTreeModel;
	private DefaultTreeModel filesTreeModel;
	
	/**
	 * @uml.property  name="formUpdater"
	 * @uml.associationEnd  
	 */
	private JExpertFormUpdater formUpdater;
	/**
	 * @uml.property  name="currDispersionStorage"
	 * @uml.associationEnd  
	 */
	protected StorageInfo currDispersionStorage;
	/**
	 * @uml.property  name="currRecoveryStorage"
	 * @uml.associationEnd  
	 */
	protected StorageInfo currRecoveryStorage;
	/**
	 * @uml.property  name="currFileInfo"
	 * @uml.associationEnd  
	 */
	protected FileInfo currFileInfo;
	boolean fileSelected = false;
	boolean dispersionStorageSelected = false;
	boolean recoveryStorageSelected = false;
	boolean recoveryFileSelected = false;
	protected boolean clearStorageEnabled = false;
	//ExpertApplication myApp;
	
	
	public void refreshForm()
	{
		TreePath currSelected = jOverviewTree.getSelectionPath();
		jOverviewTree.repaint();
		jOverviewTree.setSelectionPath(currSelected);
		
		currSelected = jDispersionStoragesTree.getSelectionPath();
		jDispersionStoragesTree.repaint();
		jDispersionStoragesTree.setSelectionPath(currSelected);
		
		currSelected = jRecoveryStoragesTree.getSelectionPath();
		jRecoveryStoragesTree.repaint();
		jRecoveryStoragesTree.setSelectionPath(currSelected);
		
		currSelected = jFilesTree.getSelectionPath();
		jFilesTree.repaint();
		jFilesTree.setSelectionPath(currSelected);
		
		for(int i = 0; i < jProgressBarPanel.getComponentCount(); i++)
		{
			jProgressBarPanel.getComponent(i).validate();
		}
		
	}
	
	private void refreshNodesModel()
	{
		DefaultMutableTreeNode	root = new DefaultMutableTreeNode("Nodes status");	
		nodesTreeModel = new DefaultTreeModel(root);
		DefaultMutableTreeNode parent;			
		for(int i=0; i<agentCoordinator.nodesInfo.size(); i++)
		{
			parent = new DefaultMutableTreeNode(agentCoordinator.nodesInfo.get(i).label);
			parent.setUserObject(agentCoordinator.nodesInfo.get(i));
			root.add(parent);
		}
	}
	
	private void refreshStoragesModel()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Storages");	
		storagesTreeModel = new DefaultTreeModel(root);

		DefaultMutableTreeNode currStorage;			
		for(int i=0; i<agentCoordinator.storagesInfo.size(); i++)
		{
			currStorage = new DefaultMutableTreeNode(agentCoordinator.storagesInfo.get(i).name);
			currStorage.setUserObject(agentCoordinator.storagesInfo.get(i));
			DefaultMutableTreeNode currSavers = new DefaultMutableTreeNode("Storage agents");
			for(int j=0; j<agentCoordinator.storagesInfo.get(i).savers.size(); j++)
			{
				DefaultMutableTreeNode currNode = new DefaultMutableTreeNode(agentCoordinator.storagesInfo.get(i).savers.get(j));
				currNode.setUserObject(agentCoordinator.storagesInfo.get(i).savers.get(j));
				((NodeInfo) currNode.getUserObject()).saver = true;
				currSavers.add(currNode);
			}
			DefaultMutableTreeNode currDispersers;
			currDispersers = new DefaultMutableTreeNode("Dispersion agents");
			for(int j=0; j<agentCoordinator.storagesInfo.get(i).dispersers.size(); j++)
			{
				DefaultMutableTreeNode currNode = new DefaultMutableTreeNode(agentCoordinator.storagesInfo.get(i).dispersers.get(j));
				currNode.setUserObject(agentCoordinator.storagesInfo.get(i).dispersers.get(j));
				((NodeInfo) currNode.getUserObject()).disperser = true;
				currDispersers.add(currNode);
			}
			currStorage.add(currSavers);
			currStorage.add(currDispersers);
			root.add(currStorage);
		}
	}
	
	public void refreshButtons()
	{
		jBrowseButton.setEnabled(true);
		if(fileSelected&dispersionStorageSelected)
		{
			jDisperseButton.setEnabled(true);
		}
		else
		{
			jDisperseButton.setEnabled(false);
		};
		
		if(recoveryStorageSelected&recoveryFileSelected)
		{
			jRestoreButton.setEnabled(true);
			jRemoveButton.setEnabled(true);
		}
		else
		{
			jRestoreButton.setEnabled(false);
			jRemoveButton.setEnabled(false);
		};
		if(clearStorageEnabled)
			jClearStorageButton.setEnabled(true);
			else
				jClearStorageButton.setEnabled(false);
	}
	
	public void disableButtons()
	{
		 	jBrowseButton.setEnabled(false);
			jDisperseButton.setEnabled(false);
			jRestoreButton.setEnabled(false);
			jRemoveButton.setEnabled(false);
			jClearStorageButton.setEnabled(false);
	}
	
	private void refreshFilesModel()
	{
		
		   DefaultMutableTreeNode root = new DefaultMutableTreeNode("FilesInfo");	
			filesTreeModel = new DefaultTreeModel(root);

			currRecoveryStorage.filesInfo = agentCoordinator.getFileInfo(currRecoveryStorage);
//		System.out.println(currRecoveryStorage.name+" "+currRecoveryStorage.filesInfo.size());
			for(int j=0; j<currRecoveryStorage.filesInfo.size(); j++)
			{
				DefaultMutableTreeNode	parent = new DefaultMutableTreeNode(currRecoveryStorage.filesInfo.get(j).getFilename());
//			System.out.println(currRecoveryStorage.filesInfo.get(i).getFilename());
				parent.setUserObject(currRecoveryStorage.filesInfo.get(j));
				root.add(parent);
			}
	}
	
	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) 
	{
		JFrame frame1 = new JFrame();	
		ExpertApplication ololoApp = new ExpertApplication();
		frame1.getContentPane().add(ololoApp);
		frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame1.pack();
		Dimension us = frame1.getSize(), them = Toolkit.getDefaultToolkit().getScreenSize();
		int newX = (them.width - us.width)/2;
		int newY = (them.height - us.height)/2;
		frame1.setLocation(newX, newY);
		frame1.setTitle("Java Distributed Failover Storage Expert Application");
		frame1.setVisible(true);
		frame1.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				if(e.getID() == WindowEvent.WINDOW_CLOSED)
				{
					agentSubordinator.finalize();
					
					//ololoApp.currentModel.finalize();
				}
				System.exit(0);
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}
	
	public ExpertApplication() 
	{
		super();
		initGUI();
	}
	
	 
	private void initGUI() 
	{
		try 
		{
			agentCoordinator = new JSaverCoordinator();
			agentSubordinator = new JSaverSubordinator();
			agentCustomer = new JSaverCustomer();
			
			agentCoordinator.bindSubordinator(agentSubordinator);
			agentCoordinator.bindCustomer(agentCustomer);
			agentSubordinator.bindCoordinator(agentCoordinator);
			agentSubordinator.bindCustomer(agentCustomer);
			agentCustomer.bindCoordinator(agentCoordinator);
			agentCustomer.bindSubordinator(agentSubordinator);
			
			agentCoordinator.initModel();
			agentSubordinator.startRefreshing();
			
			refreshTreeModels();
			
			//System.out.println("ok");
		
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.Y_AXIS);
			this.setLayout(thisLayout);
			
			this.setPreferredSize(new java.awt.Dimension(620, 460));
			{
				jMainPanel = new JPanel();
				BoxLayout jMainPanelLayout = new BoxLayout(jMainPanel, javax.swing.BoxLayout.Y_AXIS);
				jMainPanel.setLayout(jMainPanelLayout);
				this.add(jMainPanel);
				jMainPanel.setPreferredSize(new java.awt.Dimension(684, 421));
				{ 
		tabs = new JTabbedPane();
					jMainPanel.add(tabs);
					tabs.setPreferredSize(new java.awt.Dimension(661, 272));
					{
						overwiewPane = new JPanel();
						BoxLayout overwiewPaneLayout = new BoxLayout(overwiewPane, javax.swing.BoxLayout.X_AXIS);
						overwiewPane.setLayout(overwiewPaneLayout);
						tabs.addTab("Overview", null, overwiewPane, null);
						{
							jTreePanel = new JPanel();
							BoxLayout jPanel11Layout = new BoxLayout(jTreePanel, javax.swing.BoxLayout.Y_AXIS);
							jTreePanel.setLayout(jPanel11Layout);
							overwiewPane.add(jTreePanel);
							{
								jPanel1 = new JPanel();
								jTreePanel.add(jPanel1);
								{
									jRadioButton1 = new JRadioButton();
									jPanel1.add(jRadioButton1);
									jRadioButton1.setText("Nodes");
									jRadioButton1.setSelected(true);
									jRadioButton1.addChangeListener(new ChangeListener() {
										public void stateChanged(ChangeEvent evt) {
											if(jRadioButton1.isSelected())
											{
												jOverviewTree.setModel(nodesTreeModel);
												jOverviewTree.setCellRenderer(new JSaverNodesTreeRenderer());
											} else
												if(jRadioButton2.isSelected())
												{
													jOverviewTree.setModel(storagesTreeModel);
													jOverviewTree.setCellRenderer(new JSaverStoragesTreeRenderer());
												}
											jOverviewTree.revalidate();
											jOverviewTree.repaint();
										}
									});

									getButtonGroup1().add(jRadioButton1);
								}
								{
									jRadioButton2 = new JRadioButton();
									jPanel1.add(jRadioButton2);
									jRadioButton2.setText("Storages");
									jRadioButton2.addChangeListener(new ChangeListener() {
										public void stateChanged(ChangeEvent evt) 
										{
											if(jRadioButton1.isSelected())
											{
												jOverviewTree.setModel(nodesTreeModel);
												jOverviewTree.setCellRenderer(new JSaverNodesTreeRenderer());
											} else
												if(jRadioButton2.isSelected())
												{
													jOverviewTree.setModel(storagesTreeModel);
													jOverviewTree.setCellRenderer(new JSaverStoragesTreeRenderer());
												}
											jOverviewTree.revalidate();
											jOverviewTree.repaint();
										}
									});

									getButtonGroup1().add(jRadioButton2);
								/*	jRadioButton2.addChangeListener(new ChangeListener() {
										public void stateChanged(ChangeEvent evt) {
											//System.out.println("jRadioButton2.stateChanged, event="+evt);
											//TODO add your code for jRadioButton2.stateChanged
											jRadioButton1.setSelected(!jRadioButton2.isSelected());
										}
									});*/
								}
							}
							{
								jScrollPane1 = new JScrollPane();
								jTreePanel.add(jScrollPane1);
								jScrollPane1.setPreferredSize(new java.awt.Dimension(304, 278));
								//jScrollPane1.setPreferredSize(new java.awt.Dimension(295, 373));
								//jScrollPane1.getHorizontalScrollBar().setPreferredSize(new java.awt.Dimension(117, 68));
								{
									//jTree1 = new JTree();					
									jOverviewTree = new JTree();
									jOverviewTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
									jScrollPane1.setViewportView(jOverviewTree);
									jOverviewTree.setLayout(null);
									
									jOverviewTree.setModel(nodesTreeModel);
									jOverviewTree.setCellRenderer(new JSaverNodesTreeRenderer());
									
									jOverviewTree.addTreeSelectionListener(new TreeSelectionListener() {
										public void valueChanged(TreeSelectionEvent evt) 
										{	
											TreePath currPath = evt.getPath();
											String info = "Click to node to view info...";
											try 
											{
												NodeInfo currNode = (NodeInfo) ((DefaultMutableTreeNode) currPath.getLastPathComponent()).getUserObject();
												info = currNode.getInfo();
											} 
											catch (Exception e) 
											{	
												try 
												{
													StorageInfo currNode = (StorageInfo) ((DefaultMutableTreeNode) currPath.getLastPathComponent()).getUserObject();
													info = currNode.getInfo();
												} catch (Exception e1) 
												{
													// TODO Auto-generated catch block
													//e1.printStackTrace();
													info = (String) ((DefaultMutableTreeNode) currPath.getLastPathComponent()).getUserObject();
												}
												//e.printStackTrace();
											}
											jSummaryLabel.setText(info);
										}
									});

								}
							}
						}
						{
							jOverwiewInfoPanel = new JPanel();
							BoxLayout jPanel1Layout = new BoxLayout(jOverwiewInfoPanel, javax.swing.BoxLayout.Y_AXIS);
							jOverwiewInfoPanel.setLayout(jPanel1Layout);
							overwiewPane.add(jOverwiewInfoPanel);
							{
							jPanel8 = new JPanel();
								jOverwiewInfoPanel.add(jPanel8);
								GridBagLayout jPanel8Layout = new GridBagLayout();
								jPanel8Layout.rowWeights = new double[] {0.1};
								jPanel8Layout.rowHeights = new int[] {7};
								jPanel8Layout.columnWeights = new double[] {0.1};
								jPanel8Layout.columnWidths = new int[] {7};
								jPanel8.setLayout(jPanel8Layout);
								{
									jSummaryLabel = new JLabel();
									//jSummaryLabel.setAutoscrolls(true);
									//jSummaryLabel.
									jPanel8.add(jSummaryLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
									jSummaryLabel.setText("Click to node to view details...");
									jSummaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
								}
							} 
						}
					}
					{ 
						dispersalPanel = new JPanel();
						BoxLayout dispersalPanelLayout = new BoxLayout(dispersalPanel, javax.swing.BoxLayout.Y_AXIS);
						tabs.addTab("Dispersion", null, dispersalPanel, null);
						dispersalPanel.setLayout(dispersalPanelLayout); 
						dispersalPanel.setPreferredSize(new java.awt.Dimension(595, 338));
						{
							
							jPanel10 = new JPanel();
							GridBagLayout jPanel10Layout = new GridBagLayout();
							dispersalPanel.add(jPanel10);
							jPanel10Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
							jPanel10Layout.rowHeights = new int[] {26, 66, 30, 105, 57};
							jPanel10Layout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.1};
							jPanel10Layout.columnWidths = new int[] {132, 157, 162, 7};
							jPanel10.setLayout(jPanel10Layout);
							{
								jLabel1 = new JLabel();
								jPanel10.add(jLabel1, new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabel1.setText("Choose file to put it to the storage:");
							}
							//{
								jBrowseButton = new JButton();
								jPanel10.add(jBrowseButton, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
								jBrowseButton.setText("Browse");
								jBrowseButton.setPreferredSize(new java.awt.Dimension(14, 22));
								jBrowseButton.setName("jBrowseButton");
								jBrowseButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										//System.out.println("jButton1.actionPerformed, event="+evt);
										//TODO add your code for jButton1.actionPerformed
										JFileChooser fc = new JFileChooser();
										fc.showOpenDialog(getParent());
										File f = fc.getSelectedFile();
										//fileSelected = false;
										if(f!=null)
											if(f.exists())
											{
												fileSelected = true;
												jDispersionFileName.setText(f.getAbsolutePath());
											}
											else
												fileSelected = false;
										else
											fileSelected = false;
										if(fileSelected&&dispersionStorageSelected)
										{
											jDisperseButton.setEnabled(true);
										}
										else
										{
											jDisperseButton.setEnabled(false);
										}
									}
								});
						//	}
							{
								jDispersionFileName = new JTextField();
								jPanel10.add(jDispersionFileName, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
								jDispersionFileName.setText("");
								jDispersionFileName.addCaretListener(new CaretListener() {
									public void caretUpdate(CaretEvent evt) {
										File f = new File(jDispersionFileName.getText());
										if(f.exists())
										{
											fileSelected = true;
										}
										else
										{
											fileSelected = false;
										}
										if(fileSelected&&dispersionStorageSelected)
										{
											jDisperseButton.setEnabled(true);
										}
										else
										{
											jDisperseButton.setEnabled(false);
										}
									}
								});
								jDispersionFileName.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										File f = new File(jDispersionFileName.getText());
										if(f.exists())
										{
											fileSelected = true;
										}
										else
										{
											fileSelected = false;
										}
										if(fileSelected&&dispersionStorageSelected)
										{
											jDisperseButton.setEnabled(true);
										}
										else
										{
											jDisperseButton.setEnabled(false);
										}
									}
								});

							}
							{
								jDisperseButton = new JButton();
								jPanel10.add(jDisperseButton, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jPanel10.add(getJLabel6(), new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
	//							jPanel10.add(getJTree3x(), new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	//							jPanel10.add(getJTree3x(), new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	//							jPanel10.add(getJTree3x(), new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
								jPanel10.add(getJTree2(), new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
								jDisperseButton.setText("Disperse!");
								//jDisperseButton.setEnabled(false);
								final ExpertApplication myApp = this;
								jDisperseButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										//disableButtons();
										File currFile = new File(jDispersionFileName.getText());
										FileInfo currFileInfo = new FileInfo();
										currFileInfo.setAbsolutepath(currFile.getAbsolutePath());
										currFileInfo.setSize(currFile.length());
										currFileInfo.setFilename(currFile.getName());
										currFileInfo.setDate(currFile.lastModified());
										agentCoordinator.disperseFile(currFileInfo, currDispersionStorage);
										JProgressBar jProgressBar1 = new JProgressBar();
										jProgressBar1.setName(currFile.getName());
										jProgressBar1.setString("Dispersing "+currFile.getName());
										jProgressBar1.setStringPainted(true);
										jProgressBarPanel.add(jProgressBar1);
										jCommonPanel.revalidate();
										jCommonPanel.repaint();
										new Thread(new ProgressMonitor(myApp, agentCoordinator.getTask(currFile.getName()+"_dispersion"),agentCoordinator)).start();
									} 
								});
							}
						}
					}
					{
						recoveryPanel = new JPanel();
						GridLayout recoveryPanelLayout = new GridLayout(1, 1);
						recoveryPanelLayout.setHgap(5);
						recoveryPanelLayout.setVgap(5);
						recoveryPanelLayout.setColumns(1);
						recoveryPanel.setLayout(recoveryPanelLayout);
						tabs.addTab("Storages management", null, recoveryPanel, null);
						tabs.addTab("Nodes management", null, getNodesPane(), null);
						tabs.addTab("About", null, getAboutPanel(), null);
						{
							jPanel2 = new JPanel();
							BoxLayout jPanel2Layout = new BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS);
							jPanel2.setLayout(jPanel2Layout);
							recoveryPanel.add(jPanel2);
							{
								jLabel2 = new JLabel();
								BoxLayout jLabel2Layout = new BoxLayout(jLabel2, javax.swing.BoxLayout.Y_AXIS);
								jLabel2.setLayout(jLabel2Layout);
								jPanel2.add(getJLabel7());
								jPanel2.add(getJScrollPane6());
								jPanel2.add(jLabel2);
								jLabel2.setText("Choose file to restore");
								jLabel2.setPreferredSize(new java.awt.Dimension(146, 15));
							}
							{
							jScrollPane3 = new JScrollPane();
								jPanel2.add(jScrollPane3);
								jScrollPane3.setPreferredSize(new java.awt.Dimension(305, 159));
								jScrollPane3.setViewportView(getJTree4());
							}
						}
						{
						jPanel3 = new JPanel();
						GridBagLayout jPanel3Layout = new GridBagLayout();
							recoveryPanel.add(jPanel3);
							jPanel3Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.1};
							jPanel3Layout.rowHeights = new int[] {30, 95, 32, 46, 46, 20};
							jPanel3Layout.columnWeights = new double[] {0.1};
							jPanel3Layout.columnWidths = new int[] {7};
							jPanel3.setLayout(jPanel3Layout);
							{
								jClearStorageButton = new JButton();
								jPanel3.add(jClearStorageButton, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jClearStorageButton.setText("Clear storage");
								jClearStorageButton.setEnabled(false);
								jClearStorageButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										disableButtons();
										agentCoordinator.clearStorage(currRecoveryStorage);
										refreshFilesModel();
										jFilesTree.setModel(filesTreeModel);
										jFilesTree.setCellRenderer(new JSaverFilesTreeRenderer());
										refreshButtons();
									}
								});
							}
							{
								jRestoreButton = new JButton();
								jPanel3.add(jRestoreButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jRestoreButton.setText("Restore!");
								jRestoreButton.setEnabled(false);
								final ExpertApplication myApp = this;
								jRestoreButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) 
									{
										//disableButtons();
										agentCoordinator.recoverFile(currFileInfo, currRecoveryStorage);
										JProgressBar jProgressBar1 = new JProgressBar();
										jProgressBar1.setName(currFileInfo.getFilename());
										jProgressBar1.setString("Recovering "+currFileInfo.getFilename());
										jProgressBar1.setStringPainted(true);
										jProgressBarPanel.add(jProgressBar1);
										jCommonPanel.revalidate();
										jCommonPanel.repaint(); 
										new Thread(new ProgressMonitor(myApp, agentCoordinator.getTask(currFileInfo.getFilename()+"_recovery"),agentCoordinator)).start();
							//			String filename = jList1.getSelectedValue().toString();
										//System.out.println("jButton6.actionPerformed, event="+evt);
							/*			if(currentModel.recoverFile(filename))
										{
											
										}/*/
										//TODO add your code for jButton6.actionPerformed
										//
									}
								});
							}
							{
								jRemoveButton = new JButton();
								jPanel3.add(jRemoveButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jRemoveButton.setText("Remove");
								jRemoveButton.setEnabled(false);
								jRemoveButton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent evt) {
										disableButtons();
										recoveryFileSelected = false;
										agentCoordinator.removeFile(currFileInfo, currRecoveryStorage);
										refreshFilesModel();
										jFilesTree.setModel(filesTreeModel);
										jFilesTree.setCellRenderer(new JSaverFilesTreeRenderer());
										refreshButtons();
									}


								});
							}
						}
					}
					//	tabs.setEnabledAt(3, false);
					//tabs.disable();
					//tabs.setDisabledIconAt(1, null);
					//tabs.setSelectedIndex(3);
					//	tabs.getComponentAt(3).setEnabled(false);
					//	tabs.getComponent(3).disable();
					//	tabs.getComponents()[3].disable();
				}
				{
					jCommonPanel = new JPanel();
					jMainPanel.add(jCommonPanel);
					BoxLayout jCommonPanelLayout = new BoxLayout(jCommonPanel, javax.swing.BoxLayout.Y_AXIS);
					jCommonPanel.setLayout(jCommonPanelLayout);
					{
						jLogPanel = new JPanel();
						BoxLayout jLogPanelLayout = new BoxLayout(jLogPanel, javax.swing.BoxLayout.Y_AXIS);
						jLogPanel.setLayout(jLogPanelLayout);
						jCommonPanel.add(jLogPanel);
						{
							jLabel5 = new JLabel();
							jLogPanel.add(jLabel5);
							BoxLayout jLabel5Layout = new BoxLayout(jLabel5, javax.swing.BoxLayout.X_AXIS);
							jLabel5.setLayout(jLabel5Layout);
							jLabel5.setText("Log area:                                                                                             ");
							jLabel5.setHorizontalAlignment(SwingConstants.LEFT);
							jLabel5.setPreferredSize(new java.awt.Dimension(514, 15));
							jLabel5.setAlignmentY(0.0f);
							jLabel5.setHorizontalTextPosition(SwingConstants.LEFT);
						}
						{
							jScrollPane2 = new JScrollPane();
							jLogPanel.add(jScrollPane2);
							jScrollPane2.setPreferredSize(new java.awt.Dimension(620, 96));
							{
								logArea = new JTextArea();
								jScrollPane2.setViewportView(logArea);
							}
						}
					}
					{
						jProgressBarPanel = new JPanel(); 
						jCommonPanel.add(jProgressBarPanel);
						BoxLayout jScrollbarPanelLayout = new BoxLayout(jProgressBarPanel, javax.swing.BoxLayout.Y_AXIS);
						jProgressBarPanel.setLayout(jScrollbarPanelLayout);
					}
					
					agentCustomer.bindLogArea(logArea);
				//	agentCustomer.sendMsgToLog("Test");
					formUpdater = new JExpertFormUpdater();
					formUpdater.bindForm(this);
					//formUpdater.setTreePanel(jOverviewTree);
					//formUpdater.setProgressPanel(jProgressBarPanel);	
					formUpdater.bindCustomer(agentCustomer);
					formUpdater.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}
	
	private void refreshTreeModels() {
		this.refreshNodesModel();
		this.refreshStoragesModel();	
	}

	/**
	 * @return
	 * @uml.property  name="buttonGroup1"
	 */
	private ButtonGroup getButtonGroup1() {
		if(buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
			//buttonGroup1.
		}
		return buttonGroup1;
	}

	private JLabel getJLabel6() {
		if(jLabel6 == null) {
			jLabel6 = new JLabel();
			jLabel6.setText("Choose storage:");
		}
		return jLabel6;
	}

	private JLabel getJLabel7() {
		if(jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Choose storage:");
		}
		return jLabel7;
	}

	private JScrollPane getJScrollPane6() {
		if(jScrollPane6 == null) {
		jScrollPane6 = new JScrollPane();
			jScrollPane6.setPreferredSize(new java.awt.Dimension(305, 93));
			jScrollPane6.setViewportView(getJTree3());
		}
		return jScrollPane6;
	}

	private JTree getJTree2() 
	{
		if(jDispersionStoragesTree == null) 
		{
			jDispersionStoragesTree = new JTree();
			jDispersionStoragesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			jDispersionStoragesTree.setModel(storagesTreeModel);
			jDispersionStoragesTree.setCellRenderer(new JSaverStoragesTreeRenderer());
			jDispersionStoragesTree.setRootVisible(false);
			jDispersionStoragesTree.setExpandsSelectedPaths(false);
			jDispersionStoragesTree.setScrollsOnExpand(false);
			jDispersionStoragesTree.setToggleClickCount(100); //hehehehehe
			jDispersionStoragesTree.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jDispersionStoragesTree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent evt) {
					//currStorage 
					TreePath currPath = evt.getPath();
					currDispersionStorage = (StorageInfo) ((DefaultMutableTreeNode) currPath.getLastPathComponent()).getUserObject();
					if(currDispersionStorage.canDisperse())
					{
						dispersionStorageSelected = true;
						currDispersionStorage.filesInfo = agentCoordinator.getFileInfo(currDispersionStorage);
					}
					else
					{
						dispersionStorageSelected = false;
					}
					refreshButtons();
				}
			});
		}
		return jDispersionStoragesTree;
	}
	
	private JTree getJTree3() {
		if(jRecoveryStoragesTree == null) {
			jRecoveryStoragesTree = new JTree();
			jRecoveryStoragesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			jRecoveryStoragesTree.setModel(storagesTreeModel);
			jRecoveryStoragesTree.setCellRenderer(new JSaverStoragesTreeRenderer());
			jRecoveryStoragesTree.setRootVisible(false);
			jRecoveryStoragesTree.setExpandsSelectedPaths(false);
			jRecoveryStoragesTree.setScrollsOnExpand(false);
			jRecoveryStoragesTree.setToggleClickCount(100); //hehehehehe
			jRecoveryStoragesTree.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jRecoveryStoragesTree.addTreeSelectionListener(new TreeSelectionListener() {
				public void valueChanged(TreeSelectionEvent evt) 
				{
					TreePath currPath = evt.getPath();
					currRecoveryStorage = (StorageInfo) ((DefaultMutableTreeNode) currPath.getLastPathComponent()).getUserObject();
					refreshFilesModel();
					jFilesTree.setModel(filesTreeModel);
					jFilesTree.setCellRenderer(new JSaverFilesTreeRenderer());
					jFilesTree.repaint();
					jFilesTree.revalidate();
					if(currRecoveryStorage.canDisperse())
					{
						jClearStorageButton.setEnabled(true);
						clearStorageEnabled = true;
						recoveryStorageSelected = true;
					}
					else if(currRecoveryStorage.storageIsOn())
					{
						jClearStorageButton.setEnabled(false);
						recoveryStorageSelected = true;
						clearStorageEnabled = false;
					}
					else
					{
						jClearStorageButton.setEnabled(false);
						recoveryStorageSelected = false;
						clearStorageEnabled = false;
					};
					recoveryFileSelected = false;
					if(recoveryStorageSelected&&recoveryFileSelected)
					{
						jRestoreButton.setEnabled(true);
						jRemoveButton.setEnabled(true);
					}
					else
					{
						jRestoreButton.setEnabled(false);
						jRemoveButton.setEnabled(false);
					}
				}
			});
		}
		return jRecoveryStoragesTree;
	}
		
		private JTree getJTree4() {
			if(jFilesTree == null) {
				jFilesTree = new JTree();
				jFilesTree.setModel(filesTreeModel);
				jFilesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				jFilesTree.setRootVisible(false);
				jFilesTree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent evt) {
						TreePath currPath = evt.getPath();
						currFileInfo = (FileInfo) ((DefaultMutableTreeNode) currPath.getLastPathComponent()).getUserObject();
						if(currFileInfo!=null)
						{
							recoveryFileSelected = true;
						}
						else
						{
							recoveryFileSelected = false;
						}
						refreshButtons();
					}
				});
			}
			return jFilesTree;
		}
		
		/**
		 * @return
		 * @uml.property  name="aboutPanel"
		 */
		private JPanel getAboutPanel() {
			if(aboutPanel == null) {
				aboutPanel = new JPanel();
				BoxLayout aboutPanelLayout = new BoxLayout(aboutPanel, javax.swing.BoxLayout.X_AXIS);
				aboutPanel.setLayout(aboutPanelLayout);
				aboutPanel.add(getJLabel3());
			}
			return aboutPanel;
		}
		
		private JLabel getJLabel3() {
			if(jLabel3 == null) {
				jLabel3 = new JLabel();
				BorderLayout jLabel3Layout = new BorderLayout();
				jLabel3.setLayout(jLabel3Layout);
				jLabel3.setText("<HTML>JSaverStorage expert application"+
						"<BR><a href=\"http:\\\\jsaverstorage.sourceforge.net\">jsaverstorage.sourceforge.net</a>" +
						"<BR>by Dmitry Myasnikov "+
						"<BR><a href=\"mailto:saver_is_not@bk.ru\">saver_is_not@bk.ru</a>"+"" +
						"<BR> Vologda State Techncal University, 2010"+
						"</HTML>");
				jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
			}
			return jLabel3;
		}
		
		private JPanel getNodesPane() {
			if(nodesPane == null) {
				nodesPane = new JPanel();
				BoxLayout nodesPaneLayout = new BoxLayout(nodesPane, javax.swing.BoxLayout.Y_AXIS);
				nodesPane.setLayout(nodesPaneLayout);
				nodesPane.add(getJPanel4());
				nodesPane.add(getJPanel5());
				nodesPane.add(getJPanel6());
			}
			return nodesPane;
		}
		
		private JPanel getJPanel4() {
			if(jPanel4 == null) {
				jPanel4 = new JPanel();
				GridLayout jPanel4Layout = new GridLayout(1, 1);
				jPanel4Layout.setHgap(5);
				jPanel4Layout.setVgap(5);
				jPanel4Layout.setColumns(1);
				jPanel4.setLayout(jPanel4Layout);
				jPanel4.add(getJLabel4());
			}
			return jPanel4;
		}
		
		private JPanel getJPanel5() {
			if(jPanel5 == null) {
				jPanel5 = new JPanel();
				GridBagLayout jPanel5Layout = new GridBagLayout();
				jPanel5Layout.rowWeights = new double[] {0.1, 0.1, 0.1};
				jPanel5Layout.rowHeights = new int[] {7, 7, 7};
				jPanel5Layout.columnWeights = new double[] {0.1, 0.1, 0.1};
				jPanel5Layout.columnWidths = new int[] {7, 7, 7};
				jPanel5.setLayout(jPanel5Layout);
				jPanel5.setPreferredSize(new java.awt.Dimension(615, 114));
				jPanel5.add(getAdminpassField(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			return jPanel5;
		}
		
		private JPanel getJPanel6() {
			if(jPanel6 == null) {
				jPanel6 = new JPanel();
				GridBagLayout jPanel6Layout = new GridBagLayout();
				jPanel6Layout.rowWeights = new double[] {0.1, 0.1, 0.1};
				jPanel6Layout.rowHeights = new int[] {7, 7, 7};
				jPanel6Layout.columnWeights = new double[] {0.1, 0.1, 0.1};
				jPanel6Layout.columnWidths = new int[] {7, 7, 7};
				jPanel6.setLayout(jPanel6Layout);
				jPanel6.setPreferredSize(new java.awt.Dimension(615, 84));
				jPanel6.add(getJSaveNodesInfoButton(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
			}
			return jPanel6;
		}
		
		private JLabel getJLabel4() {
			if(jLabel4 == null) {
				jLabel4 = new JLabel();
				GridBagLayout jLabel4Layout = new GridBagLayout();
				jLabel4.setText("Admin password:");
				jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
				jLabel4Layout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jLabel4Layout.rowHeights = new int[] {7, 7, 7, 7};
				jLabel4Layout.columnWeights = new double[] {0.1, 0.1, 0.1, 0.1};
				jLabel4Layout.columnWidths = new int[] {7, 7, 7, 7};
				jLabel4.setLayout(jLabel4Layout);
				jLabel4.setPreferredSize(new java.awt.Dimension(615, 91));
			}
			return jLabel4;
		}
		
		private JPasswordField getAdminpassField() {
			if(adminpassField == null) {
				adminpassField = new JPasswordField();
				adminpassField.setText("11111111111");
				adminpassField.setPreferredSize(new java.awt.Dimension(210, 21));
			}
			return adminpassField;
		}
		
		private JButton getJSaveNodesInfoButton() {
			if(jSaveNodesInfoButton == null) {
				jSaveNodesInfoButton = new JButton();
				jSaveNodesInfoButton.setText("Save");
			}
			return jSaveNodesInfoButton;
		}

}
