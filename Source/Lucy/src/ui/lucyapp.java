package ui;

import java.awt.EventQueue;
import java.awt.List;
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import engine.GeneticProgramManager;
import engine.MessageFormatter;
import engine.Operand;
import engine.Operator;
import engine.OrderedPair;
import engine.Settings;

import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

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
	private DefaultTableModel operandmodel = new DefaultTableModel();
	private JTable operandtbl = new JTable();
	private DefaultTableModel operatormodel = new DefaultTableModel();
	private JTable operatortbl = new JTable();
	private DefaultTableModel trainingmodel = new DefaultTableModel();
	private JTable trainingtbl = new JTable();
	File selectedFile;

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
		frame.setBounds(100, 100, 650, 500);
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
						
		initPopulationTextField.setEditable(false);
		initPopulationTextField.setBounds(175, 101, 43, 16);
		card1.add(initPopulationTextField);
								
		JLabel lblInitialPopulationSize = new JLabel("Initial Population Size:");
		lblInitialPopulationSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblInitialPopulationSize);
		lblInitialPopulationSize.setBounds(20, 103, 153, 14);
								
		populationTextField.setEditable(false);
		populationTextField.setBounds(175, 120, 43, 16);
		card1.add(populationTextField);
								
		populationTextField.setText(Integer.toString(Settings.get().PopulationSize));
								
		JLabel lblPopulationSize = new JLabel("Population Size:");
		lblPopulationSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblPopulationSize);
		lblPopulationSize.setBounds(20, 122, 153, 14);
		
		mutationProbabilityTextField.setEditable(false);
		mutationProbabilityTextField.setBounds(175, 139, 43, 16);
		card1.add(mutationProbabilityTextField);
								
		mutationProbabilityTextField.setText(Float.toString(Settings.get().MutationProbability));
								
		JLabel lblMutationProbability = new JLabel("Mutation Probability:");
		lblMutationProbability.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblMutationProbability);
		lblMutationProbability.setBounds(20, 141, 147, 14);
		
		crossoverProbabilityTextField.setEditable(false);
		crossoverProbabilityTextField.setBounds(175, 159, 43, 16);
		card1.add(crossoverProbabilityTextField);
								
		crossoverProbabilityTextField.setText(Float.toString(Settings.get().CrossoverProbability));
								
		JLabel lblCrossoverProbability = new JLabel("Crossover Probability:");
		lblCrossoverProbability.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblCrossoverProbability);
		lblCrossoverProbability.setBounds(20, 161, 153, 14);
		
		maxDurationTextField.setEditable(false);
		maxDurationTextField.setBounds(175, 178, 43, 16);
		card1.add(maxDurationTextField);
								
		maxDurationTextField.setText(Float.toString(Settings.get().MaxDuration));
								
		JLabel lblMaxDuration = new JLabel("Max Duration (in seconds):");
		lblMaxDuration.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblMaxDuration);
		lblMaxDuration.setBounds(20, 180, 147, 14);
		
		maxTreeDepthTextField.setEditable(false);
		maxTreeDepthTextField.setBounds(175, 197, 43, 16);
		card1.add(maxTreeDepthTextField);
								
		maxTreeDepthTextField.setText(Integer.toString(Settings.get().MaxTreeDepth));
								
		JLabel lblMaxTreeDepth = new JLabel("Max Tree Depth:");
		lblMaxTreeDepth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblMaxTreeDepth);
		lblMaxTreeDepth.setBounds(20, 199, 153, 14);
		
		keeperTextField.setEditable(false);
		keeperTextField.setBounds(175, 217, 43, 16);
		card1.add(keeperTextField);
								
		keeperTextField.setText(Integer.toString(Settings.get().KeeperThreshhold));
								
		JLabel lblKeeper = new JLabel("Keeper Threshhold:");
		lblKeeper.setFont(new Font("Tahoma", Font.PLAIN, 12));
		card1.add(lblKeeper);
		lblKeeper.setBounds(20, 219, 147, 14);
		
		bottomPanel.setVisible(true);
		card1.setVisible(true);

		// set operand table properties
		operandtbl.setEnabled(false);
		operandtbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		operandtbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane operandsp = new JScrollPane(operandtbl);
		operandsp.setEnabled(false);
		operandsp.setSize(90, 150);
		operandsp.setLocation(253, 90);
		operandsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		card1.add(operandsp);
		
		// set operator table properties
		operatortbl.setEnabled(false);
		operatortbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		operatortbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane operatorsp = new JScrollPane(operatortbl);
		operatorsp.setEnabled(false);
		operatorsp.setSize(90, 150);
		operatorsp.setLocation(374, 90);
		operatorsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		card1.add(operatorsp);
		
		// set training table properties
		trainingtbl.setEnabled(false);
		trainingtbl.setFont(new Font("Tahoma", Font.PLAIN, 11));
		trainingtbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane trainingsp = new JScrollPane(trainingtbl);
		trainingsp.setEnabled(false);
		trainingsp.setSize(90, 150);
		trainingsp.setLocation(498, 90);
		trainingsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		card1.add(trainingsp);
		
		textField_1 = new JTextField();
		textField_1.setText("settings.xml");
		textField_1.setColumns(10);
		textField_1.setBounds(20, 42, 504, 20);
		card1.add(textField_1);
		
		JButton button = new JButton("Find");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFont(new Font("Tahoma", Font.PLAIN, 12));
				int result = fileChooser.showOpenDialog(bottomPanel);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    Settings.reget(selectedFile.getAbsolutePath());
				    textField_1.setText(selectedFile.getAbsolutePath());
				    loadSettingsUI();
				}
			}

		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 10));
		button.setBounds(534, 41, 54, 23);
		card1.add(button);
		
		JLabel lblConfiguration = new JLabel("Configuration:");
		lblConfiguration.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		lblConfiguration.setForeground(new Color(0, 102, 102));
		lblConfiguration.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 17));
		lblConfiguration.setBounds(10, 11, 488, 21);
		card1.add(lblConfiguration);
		
		JLabel lblExecution = new JLabel("Execution:");
		lblExecution.setForeground(new Color(0, 102, 102));
		lblExecution.setFont(new Font("Verdana", Font.BOLD | Font.ITALIC, 17));
		lblExecution.setBounds(10, 251, 241, 21);
		card1.add(lblExecution);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(255, 255, 255));
		
		JScrollPane resultsp = new JScrollPane(textArea);
		resultsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		resultsp.setSize(568, 103);
		resultsp.setLocation(20, 308);
		card1.add(resultsp);
		
		JProgressBar progressBar = new JProgressBar(0,100);
		progressBar.setBounds(101, 283, 487, 14);
		
	    ActionListener updateProBar = new ActionListener() {
	    	public void actionPerformed(ActionEvent actionEvent) {
	    		int i = (int) Math.round(100/Settings.get().MaxDuration);
		        int val = progressBar.getValue();
		        progressBar.setValue(val + i);
	    	}
	    };
		    
	    Timer timer = new Timer(1000, updateProBar);
		
		JButton btnGoButton = new JButton("Execute");
		btnGoButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnGoButton.setBounds(20, 277, 71, 23);
		card1.add(btnGoButton);
		btnGoButton.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            new SwingWorker() {

	               @Override
	               protected Object doInBackground() throws Exception {

	            	    // start
	            	    progressBar.setValue(0);
	            	    timer.start();
	            		long start = System.currentTimeMillis();
	            	    String starttime = "Start time: " + MessageFormatter.dateTime(start);
	            	    textArea.setText(starttime + "\n");
	            	    
	            	    // execute engine
						engine.GeneticProgramManager gm = new GeneticProgramManager();
						String result = gm.execute();
						
						// end
						timer.stop();
						progressBar.setValue(100);
						long end = System.currentTimeMillis();
	            	    String endtime = "End time: " + MessageFormatter.dateTime(end);
	            	    textArea.append(endtime + "\n");
						textArea.append(result + "\n");
						
						return null;
	               }
	            }.execute();
	         }
			
			
		});
		
		card1.add(progressBar);
		
		loadSettingsUI();

	}
	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {

		}
	}
	

	private void loadSettingsUI() {
		initPopulationTextField.setText(Integer.toString(Settings.get().InitPopulationSize));
		populationTextField.setText(Integer.toString(Settings.get().PopulationSize));
		mutationProbabilityTextField.setText(Float.toString(Settings.get().MutationProbability));
		crossoverProbabilityTextField.setText(Float.toString(Settings.get().CrossoverProbability));
		maxDurationTextField.setText(Float.toString(Settings.get().MaxDuration));
		maxTreeDepthTextField.setText(Integer.toString(Settings.get().MaxTreeDepth));
		keeperTextField.setText(Integer.toString(Settings.get().KeeperThreshhold));
		
		Vector<Vector> rowData;
		Vector<String> columnNames;
		
		// Operand Panel on Card1
		rowData = new Vector<Vector>();
		for(Operand op : Settings.get().Operands)  {
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
		for(Operator op : Settings.get().Operators)  {
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
		for(OrderedPair pair : Settings.get().Training)  {
			Vector<String> row = new Vector<String>();
			row.addElement(Float.toString(pair.X));
			row.addElement(Float.toString(pair.X));
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
