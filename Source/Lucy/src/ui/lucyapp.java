package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import engine.*;
import javax.swing.border.EtchedBorder;

public class lucyapp {

	private static JFrame frame;
	private JTextField textField_1;
	private JFormattedTextField initPopulationTextField = new JFormattedTextField();
	private JFormattedTextField populationTextField = new JFormattedTextField();
	private JFormattedTextField mutationProbabilityTextField = new JFormattedTextField();
	private JFormattedTextField crossoverProbabilityTextField = new JFormattedTextField();
	private JFormattedTextField maxDurationTextField = new JFormattedTextField();
	private JFormattedTextField maxTreeDepthTextField = new JFormattedTextField();
	private JFormattedTextField keeperTextField = new JFormattedTextField();
	private JTable operandtbl = new JTable();
	private JTable operatortbl = new JTable();
	private JTable trainingtbl = new JTable();
	private JLabel lblFunction = new JLabel("");
	private int progressbarDelta;
	private File selectedFile = new File("./settings.xml");
	private JTextArea textArea = new JTextArea();
	private JProgressBar progressBar = new JProgressBar(0,900);
	private String lastfolder;
	private static Logger logger = Logger.getLogger( lucyapp.class.getName() );
	private engine.GeneticProgramManager gm;
	private JLabel lblFitnessValue = new JLabel();
	private JLabel lblGenerationsValue = new JLabel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					lucyapp window = new lucyapp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public lucyapp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 10));
		frame.setBounds(100, 100, 630, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("genetics.png")));
		frame.setIconImage(icon.getImage());
		frame.setTitle("Lucy");
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		String SETTINGSPANEL = "Settings";
		String OPERANDSPANEL = "Operands";
		String OPERATORSPANEL = "Operators";
		String TRAININGPANEL = "Training";
		
		JPanel bottomPanel = new JPanel(new CardLayout());
		bottomPanel.setBorder(null);
		frame.getContentPane().add(bottomPanel);
		
		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		JPanel card4 = new JPanel();
				
		//Create the panel that contains the "cards".
		bottomPanel.add(card1,SETTINGSPANEL);
		bottomPanel.add(card2,OPERANDSPANEL);
		bottomPanel.add(card3,OPERATORSPANEL);
		bottomPanel.add(card4,TRAININGPANEL);
		
		card1.setLayout(null);
		
		JRadioButton rdbtnSevere = new JRadioButton("SEVERE");
		rdbtnSevere.setBounds(20, 39, 71, 23);
		//rdbtnSevere.setSelected(true);
		//card1.add(rdbtnSevere);
		
		JRadioButton rdbtnConfig = new JRadioButton("BASIC");
		rdbtnConfig.setSelected(true);
		rdbtnConfig.setBounds(21, 33, 70, 23);
		rdbtnConfig.setSelected(true);
		card1.add(rdbtnConfig);
		
		JRadioButton rdbtnFine = new JRadioButton("FINE");
		rdbtnFine.setBounds(96, 33, 59, 23);
		card1.add(rdbtnFine);
		
		JRadioButton rdbtnFiner = new JRadioButton("FINER");
		rdbtnFiner.setBounds(157, 33, 59, 23);
		card1.add(rdbtnFiner);
		
		JRadioButton rdbtnFinest = new JRadioButton("FINEST");
		rdbtnFinest.setBounds(218, 33, 78, 23);
		card1.add(rdbtnFinest);
		
		ButtonGroup buttongrp = new ButtonGroup();
		//buttongrp.add(rdbtnSevere);
		buttongrp.add(rdbtnConfig);
		buttongrp.add(rdbtnFine);
		buttongrp.add(rdbtnFiner);
		buttongrp.add(rdbtnFinest);
						
		initPopulationTextField.setEditable(false);
		initPopulationTextField.setBounds(176, 174, 49, 16);
		card1.add(initPopulationTextField);
								
		JLabel lblInitialPopulationSize = new JLabel("Initial Population Size:");
		lblInitialPopulationSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblInitialPopulationSize);
		lblInitialPopulationSize.setBounds(21, 176, 153, 14);
								
		populationTextField.setEditable(false);
		populationTextField.setBounds(176, 193, 49, 16);
		card1.add(populationTextField);
								
		populationTextField.setText(Integer.toString(Settings.get().PopulationSize));
								
		JLabel lblPopulationSize = new JLabel("Population Size:");
		lblPopulationSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblPopulationSize);
		lblPopulationSize.setBounds(21, 195, 153, 14);
		
		mutationProbabilityTextField.setEditable(false);
		mutationProbabilityTextField.setBounds(176, 212, 49, 16);
		card1.add(mutationProbabilityTextField);
								
		mutationProbabilityTextField.setText(Float.toString(Settings.get().MutationProbability));
								
		JLabel lblMutationProbability = new JLabel("Mutation Probability:");
		lblMutationProbability.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblMutationProbability);
		lblMutationProbability.setBounds(21, 214, 147, 14);
		
		crossoverProbabilityTextField.setEditable(false);
		crossoverProbabilityTextField.setBounds(176, 232, 49, 16);
		card1.add(crossoverProbabilityTextField);
								
		crossoverProbabilityTextField.setText(Float.toString(Settings.get().CrossoverProbability));
								
		JLabel lblCrossoverProbability = new JLabel("Crossover Probability:");
		lblCrossoverProbability.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblCrossoverProbability);
		lblCrossoverProbability.setBounds(21, 234, 153, 14);
		
		maxDurationTextField.setEditable(false);
		maxDurationTextField.setBounds(176, 251, 49, 16);
		card1.add(maxDurationTextField);
								
		maxDurationTextField.setText(Float.toString(Settings.get().MaxDuration));
								
		JLabel lblMaxDuration = new JLabel("Max Duration (in seconds):");
		lblMaxDuration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblMaxDuration);
		lblMaxDuration.setBounds(21, 253, 147, 14);
		
		maxTreeDepthTextField.setEditable(false);
		maxTreeDepthTextField.setBounds(176, 270, 49, 16);
		card1.add(maxTreeDepthTextField);
								
		maxTreeDepthTextField.setText(Integer.toString(Settings.get().MaxTreeDepth));
								
		JLabel lblMaxTreeDepth = new JLabel("Max Tree Depth:");
		lblMaxTreeDepth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblMaxTreeDepth);
		lblMaxTreeDepth.setBounds(21, 272, 153, 14);
		
		keeperTextField.setEditable(false);
		keeperTextField.setBounds(176, 290, 49, 16);
		card1.add(keeperTextField);
								
		keeperTextField.setText(Integer.toString(Settings.get().KeeperThreshhold));
								
		JLabel lblKeeper = new JLabel("Keeper Threshhold:");
		lblKeeper.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblKeeper);
		lblKeeper.setBounds(21, 292, 147, 14);
		
		bottomPanel.setVisible(true);
		card1.setVisible(true);

		// set operand table properties
		operandtbl.setEnabled(false);
		operandtbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		operandtbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane operandsp = new JScrollPane(operandtbl);
		operandsp.setEnabled(false);
		operandsp.setSize(90, 132);
		operandsp.setLocation(261, 174);
		operandsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		card1.add(operandsp);
		
		// set operator table properties
		operatortbl.setEnabled(false);
		operatortbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		operatortbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane operatorsp = new JScrollPane(operatortbl);
		operatorsp.setEnabled(false);
		operatorsp.setSize(90, 132);
		operatorsp.setLocation(361, 174);
		operatorsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		card1.add(operatorsp);
		
		// set training table properties
		trainingtbl.setEnabled(false);
		trainingtbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		trainingtbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane trainingsp = new JScrollPane(trainingtbl);
		trainingsp.setEnabled(false);
		trainingsp.setSize(107, 132);
		trainingsp.setLocation(461, 174);
		trainingsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		card1.add(trainingsp);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText("settings.xml");
		textField_1.setColumns(10);
		textField_1.setBounds(20, 100, 469, 20);
		//textField_1.setEnabled(false);
		card1.add(textField_1);
		
		JButton button = new JButton("browse");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				
				if (lastfolder != null)
				{
					fileChooser.setCurrentDirectory(new File(lastfolder));
				}
				fileChooser.setFont(new Font("Tahoma", Font.PLAIN, 12));
				boolean validsettings = false;
				while (!validsettings)
				{
					int result = fileChooser.showOpenDialog(bottomPanel);
					if (result == JFileChooser.APPROVE_OPTION) {
					    selectedFile = fileChooser.getSelectedFile();
	
					    Settings settings = null;
					    try {
					    settings = Settings.reget(selectedFile.getAbsolutePath());
					    lastfolder = selectedFile.getAbsolutePath();
					    validsettings = true;
						textField_1.setText(selectedFile.getAbsolutePath());
					    loadSettingsUI();
					    
					    } catch (Exception ex) { }
					    if (settings == null)
					    {
					    	JOptionPane.showMessageDialog(frame, "Invalid settings file.  Please select another file.");
					    }
					}
					else if (result == JFileChooser.CANCEL_OPTION)
					{
						Settings.reget(Settings.lastFile);
						loadSettingsUI();
						return;
					}
				}
			}
		});
		
		button.setFont(new Font("Tahoma", Font.PLAIN, 10));
		button.setBounds(498, 99, 70, 23);
		card1.add(button);
		
		JLabel lblConfiguration = new JLabel("Configuration");
		lblConfiguration.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		lblConfiguration.setForeground(new Color(0, 102, 102));
		lblConfiguration.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 17));
		lblConfiguration.setBounds(10, 73, 488, 21);
		card1.add(lblConfiguration);
		
		JLabel lblExecution = new JLabel("Execution");
		lblExecution.setForeground(new Color(0, 102, 102));
		lblExecution.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 17));
		lblExecution.setBounds(10, 317, 241, 21);
		card1.add(lblExecution);

		textArea.setBackground(new Color(255, 255, 255));
		
		JScrollPane resultsp = new JScrollPane(textArea);
		resultsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		resultsp.setSize(552, 103);
		resultsp.setLocation(20, 370);
		card1.add(resultsp);
		
		progressBar.setBounds(289, 343, 283, 23);
		
	    ActionListener updateProBar = new ActionListener() {
	    	public void actionPerformed(ActionEvent actionEvent) {
		        int val = progressBar.getValue();
		        progressBar.setValue(val + progressbarDelta);
		        lblFitnessValue.setText(Float.toString(gm.FitnessValue));
		        lblGenerationsValue.setText(Integer.toString(gm.GenerationsValue));
	    	}
	    };
		    
	    Timer timer = new Timer(1000, updateProBar);
		
		JButton btnGoButton = new JButton("Execute");
		btnGoButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGoButton.setBounds(20, 343, 71, 23);
		card1.add(btnGoButton);
		btnGoButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            new SwingWorker() {

	               @Override
	               protected Object doInBackground() throws Exception {
	            	   
	            	   btnGoButton.setEnabled(false);
	            	   button.setEnabled(false);

	            	   
		           		try {

			            	if (rdbtnSevere.isSelected())
			            	{
			            		LogManager.getLogManager().readConfiguration(new FileInputStream("./severelog.properties"));
			            		logger.log(Level.SEVERE, "Logging severe");
			            	}
			            	else if (rdbtnConfig.isSelected())
			            	{
			            		LogManager.getLogManager().readConfiguration(new FileInputStream("./configlog.properties"));
			            		logger.log(Level.CONFIG, "Logging config");
			            	}
			            	else if (rdbtnFine.isSelected())
			            	{
			            		LogManager.getLogManager().readConfiguration(new FileInputStream("./finelog.properties"));
			            		logger.log(Level.FINE, "Logging fine");
			            	}
			            	else if (rdbtnFiner.isSelected())
			            	{
			            		LogManager.getLogManager().readConfiguration(new FileInputStream("./finerlog.properties"));
			            		logger.log(Level.FINER, "Logging finer");
			            	}
			            	else if (rdbtnFinest.isSelected())
			            	{
			            		LogManager.getLogManager().readConfiguration(new FileInputStream("./finestlog.properties"));
			            		logger.log(Level.FINEST, "Logging finest");
			            	}
			            	else 
			            	{
			            		LogManager.getLogManager().readConfiguration(new FileInputStream("./logging.properties"));
			            		logger.log(Level.SEVERE, "Logging Severe");
			            	}
			            	
			            	logger.log(Level.CONFIG, "Loading " + selectedFile.getAbsolutePath());		            	
			            	Settings.reget(selectedFile.getAbsolutePath());
			            	
			            	progressbarDelta =  (int) Math.round(900/Settings.get().MaxDuration);
		        			
		            	    // start
		            	    progressBar.setValue(0);

		            	    timer.start();
		            		long start = System.currentTimeMillis();
		            	    String starttime = "START TIME: " + MessageFormatter.dateTime(start);
		            	    textArea.setText(starttime + "\n");
		            	    
		            	    // execute engine
							gm = new GeneticProgramManager();
							String result = gm.execute();

							// end
							timer.stop();
							progressBar.setValue(900);
					        lblFitnessValue.setText(Float.toString(gm.FitnessValue));
					        lblGenerationsValue.setText(Integer.toString(gm.GenerationsValue));
					        
							long end = System.currentTimeMillis();
		            	    String endtime = "END TIME: " + MessageFormatter.dateTime(end);
		            	    textArea.append(endtime + "\n");
							textArea.append(result + "\n");
							
							LogManager.getLogManager().reset();
		        			
		        		} catch (SecurityException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		} catch (FileNotFoundException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		} catch (IOException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		}
		           		
		           		btnGoButton.setEnabled(true);
		            	button.setEnabled(true);

						return null;
	               }
	            }.execute();
	         }
			
			
		});
		
		card1.add(progressBar);
		
		JLabel lblLogging = new JLabel("Logging Level");
		lblLogging.setForeground(new Color(0, 102, 102));
		lblLogging.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 17));
		lblLogging.setBackground(SystemColor.activeCaption);
		lblLogging.setBounds(10, 11, 488, 21);
		card1.add(lblLogging);

		
		lblFitnessValue.setText("-");
		lblFitnessValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblFitnessValue.setForeground(new Color(0, 128, 0));
		lblFitnessValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFitnessValue.setBounds(96, 352, 90, 14);
		card1.add(lblFitnessValue);
		
		JLabel lblFitness = new JLabel();
		lblFitness.setHorizontalAlignment(SwingConstants.CENTER);
		lblFitness.setText("Fitness");
		lblFitness.setForeground(Color.BLACK);
		lblFitness.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFitness.setBounds(96, 339, 90, 14);
		card1.add(lblFitness);
		
		JLabel lblGenerations = new JLabel();
		lblGenerations.setHorizontalAlignment(SwingConstants.CENTER);
		lblGenerations.setText("Generations");
		lblGenerations.setForeground(Color.BLACK);
		lblGenerations.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGenerations.setBounds(190, 339, 80, 14);
		card1.add(lblGenerations);
		lblGenerationsValue.setText("-");
		lblGenerationsValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblGenerationsValue.setForeground(new Color(0, 128, 0));
		lblGenerationsValue.setBackground(Color.DARK_GRAY);
		lblGenerationsValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGenerationsValue.setBounds(190, 352, 80, 14);
		card1.add(lblGenerationsValue);
		
		JPanel targetPanel = new JPanel();
		targetPanel.setBackground(new Color(128, 128, 128));
		targetPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblTargetFunction = new JLabel("Target Function:");
		lblTargetFunction.setForeground(UIManager.getColor("Button.highlight"));
		lblTargetFunction.setFont(new Font("Arial", Font.BOLD, 20));
		targetPanel.add(lblTargetFunction);
		lblFunction.setHorizontalAlignment(SwingConstants.LEFT);
		lblFunction.setFont(new Font("Arial", Font.BOLD, 20));
		lblFunction.setForeground(UIManager.getColor("Button.highlight"));
		targetPanel.add(lblFunction);
		targetPanel.setBounds(21, 131, 547, 38);
		card1.add(targetPanel);
		 
		loadSettingsUI();

	}
	
	private void loadSettingsUI() {

		Settings settings = Settings.get();
		
		textArea.setText("");
		progressBar.setValue(0);
		lblFitnessValue.setText("-");
        lblGenerationsValue.setText("-");
		
		initPopulationTextField.setText(Integer.toString(settings.InitPopulationSize));
		populationTextField.setText(Integer.toString(settings.PopulationSize));
		mutationProbabilityTextField.setText(Float.toString(settings.MutationProbability));
		crossoverProbabilityTextField.setText(Float.toString(settings.CrossoverProbability));
		maxDurationTextField.setText(Float.toString(settings.MaxDuration));
		maxTreeDepthTextField.setText(Integer.toString(settings.MaxTreeDepth));
		keeperTextField.setText(Integer.toString(settings.KeeperThreshhold));
		lblFunction.setText(settings.Function);
		
		Vector<Vector> rowData;
		Vector<String> columnNames;
		
		// Operand Panel on Card1
		rowData = new Vector<Vector>();
		for(Operand op : settings.Operands)  {
			Vector<String> row = new Vector<String>();
			row.addElement(op.getValue());
			rowData.addElement(row);
		}			    
		columnNames = new Vector<String>();
		columnNames.addElement("Operands");
		
		DefaultTableModel operandmodel = (DefaultTableModel) operandtbl.getModel();
		operandmodel.setRowCount(0);
		operandmodel.setDataVector(rowData, columnNames);
		operandmodel.fireTableDataChanged();
		operandtbl.repaint();
		
		// Operator Panel on Card1
		rowData = new Vector<Vector>();
		for(Operator op : settings.Operators)  {
			Vector<String> row = new Vector<String>();
			row.addElement(op.getValue());
			rowData.addElement(row);
		}			    
		columnNames = new Vector<String>();
		columnNames.addElement("Operator");
		
		DefaultTableModel operatormodel = (DefaultTableModel) operatortbl.getModel();
		operatormodel.setRowCount(0);
		operatormodel.setDataVector(rowData, columnNames);
		operatormodel.fireTableDataChanged();
		operatortbl.repaint();
		
		// Training Panel on Card1
		rowData = new Vector<Vector>();
		for(OrderedPair pair : settings.Training)  {
			Vector<String> row = new Vector<String>();
			row.addElement(Float.toString(pair.X));
			row.addElement(Float.toString(pair.Y));
			rowData.addElement(row);
		}			    
		columnNames = new Vector<String>();
		columnNames.addElement("X");
		columnNames.addElement("Y");
		
		DefaultTableModel trainingmodel = (DefaultTableModel) trainingtbl.getModel();
		trainingmodel.setRowCount(0);
		trainingmodel.setDataVector(rowData, columnNames);
		trainingmodel.fireTableDataChanged();
		trainingtbl.repaint();	
	}
}
