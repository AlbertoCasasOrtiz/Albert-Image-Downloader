package project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import project.exception.DirectoryNotCreatedException;
import project.exception.FileNotCreatedException;
import project.model.Model;

/**
 * View of the MVC from the Albert Image Downloader Project.
 * 
 * @author Alberto Casas Ortiz.
 */
public class View extends JFrame{
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	
	/** ArrayList with checkboxes of image formats. */
	private ArrayList<JCheckBox> imageCheckBox;
	
	/** GridBagConstraints of the layout. */
	private GridBagConstraints constraintsGbc;
	/** Layout of the frame. */
	private GridBagLayout layoutGbc;
	
	/** Label of minimum width text field. */
	private JLabel labelMinWidth;
	/** Label of minimum height text field. */
	private JLabel labelMinHeight;
	/** Label of URL text field. */
	private JLabel labelURL;
	/** Label of progress bar. */
	private JLabel labelProgress;
	
	/** Text field of min width. */
	private JTextField textFieldMinWidth;
	/** Text field of min height. */
	private JTextField textFieldMinHeight;
	/** Text field of URL. */
	private JTextField textFieldURL;
	
	/** Button of start download. */
	private JButton buttonStart;
	
	/** Progress bar of download. */
	private JProgressBar progressBar;
	
	/** Model of the MVC. */
	private Model model;
	
	/**
	 * Constructor of the class View.
	 * 
	 * @param model Model of the MVC.
	 */
	public View(Model model){
		super("Albert Image Downloader");
		this.model = model;
		
		this.initializeComponents();
		
		this.setSize(new Dimension(350, 250));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(this.layoutGbc);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.locateElements();
		
		this.listener();
		
		this.setVisible(true);
	}
	
	/**
	 * Initialize components of GUI.
	 */
	private void initializeComponents(){
		this.imageCheckBox = new ArrayList<JCheckBox>();
		this.layoutGbc = new GridBagLayout();
		this.constraintsGbc = new GridBagConstraints();
		
		this.labelMinWidth = new JLabel("Min. Width");
		this.labelMinHeight = new JLabel("Min. Height");
		this.labelURL = new JLabel("URL");
		this.labelProgress = new JLabel("0 %");
		
		this.textFieldMinWidth = new JTextField("100");
		this.textFieldMinHeight = new JTextField("100");
		this.textFieldURL = new JTextField();
		
		this.buttonStart = new JButton("Start Download");

		this.progressBar = new JProgressBar(0, 100);
		this.progressBar.setForeground(Color.GREEN);
	}

	/**
	 * Locate elements in the GUI.
	 */
	private void locateElements(){
		this.constraintsGbc.insets = new Insets(5, 5, 5, 5);
		this.constraintsGbc.fill = GridBagConstraints.HORIZONTAL;
		this.constraintsGbc.weightx = 1.0; this.constraintsGbc.weighty = 0.0;	
		this.constraintsGbc.gridx = 0; this.constraintsGbc.gridy = 0;
		this.add(this.panelInputURL(), this.constraintsGbc);
		
		this.constraintsGbc.fill = GridBagConstraints.HORIZONTAL;
		this.constraintsGbc.weightx = 1.0; this.constraintsGbc.weighty = 0.0;	
		this.constraintsGbc.gridx = 0; this.constraintsGbc.gridy = 1;
		this.add(this.panelSelectImageFormats(), this.constraintsGbc);

		this.constraintsGbc.fill = GridBagConstraints.HORIZONTAL;
		this.constraintsGbc.weightx = 1.0; this.constraintsGbc.weighty = 0.0;	
		this.constraintsGbc.gridx = 0; this.constraintsGbc.gridy = 2;
		this.add(this.panelMinParameters(), this.constraintsGbc);

		this.constraintsGbc.fill = GridBagConstraints.HORIZONTAL;
		this.constraintsGbc.weightx = 1.0; this.constraintsGbc.weighty = 0.0;	
		this.constraintsGbc.gridx = 0; this.constraintsGbc.gridy = 3;
		this.add(this.panelProgress(), this.constraintsGbc);
	}
	
	/**
	 * Locate elements of the panel of input data.
	 * 
	 * @return Panel with located elements.
	 */
	private JPanel panelInputURL(){
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0; gbc.weighty = 0.0;	
		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(this.labelURL, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0; gbc.weighty = 0.0;	
		gbc.gridx = 1; gbc.gridy = 0;
		panel.add(this.textFieldURL, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0; gbc.weighty = 0.0;	
		gbc.gridx = 2; gbc.gridy = 0;
		panel.add(this.buttonStart, gbc);
		
		
		return panel;
	}
	
	/**
	 * Locate checkboxes of image formats.
	 * 
	 * @return Panel with located elements.
	 */
	private JPanel panelSelectImageFormats(){
		JPanel panel = new JPanel(new GridLayout(3, 3));
		panel.setBorder(BorderFactory.createTitledBorder("Image Formats"));
		for(int i = 0; i < Model.imageFormatList.length; i++){
			this.imageCheckBox.add(new JCheckBox(Model.imageFormatList[i]));
			panel.add(this.imageCheckBox.get(i));
		}
		return panel;
	}
	
	/**
	 * Locate elements of parameters.
	 * 
	 * @return Panel with located elements.
	 */
	private JPanel panelMinParameters(){
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0; gbc.weighty = 0.0;	
		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(this.labelMinWidth, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0; gbc.weighty = 0.0;	
		gbc.gridx = 1; gbc.gridy = 0;
		panel.add(this.textFieldMinWidth, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0; gbc.weighty = 0.0;	
		gbc.gridx = 2; gbc.gridy = 0;
		panel.add(this.labelMinHeight, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0; gbc.weighty = 0.0;	
		gbc.gridx = 3; gbc.gridy = 0;
		panel.add(this.textFieldMinHeight, gbc);
		
		return panel;
	}
	
	/**
	 * Locate elements of the progress panel.
	 * 
	 * @return Panel with located elements.
	 */
	private JPanel panelProgress(){
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0; gbc.weighty = 0.0;	
		gbc.gridx = 0; gbc.gridy = 0;
		panel.add(this.progressBar, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.0; gbc.weighty = 0.0;	
		gbc.gridx = 1; gbc.gridy = 0;
		panel.add(this.labelProgress, gbc);
		
		return panel;
	}
	
	/**
	 * Listeners of the elements in the GUI.
	 */
	private void listener(){
		this.buttonStart.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread(new Runnable() {

					public void run() {
						buttonStart.setEnabled(false);
						
						progressBar.setValue(0);
						
						ArrayList<String> imageFormats = new ArrayList<String>();

						for (JCheckBox anImageCheckBox : imageCheckBox) {
							if (anImageCheckBox.isSelected())
								imageFormats.add(anImageCheckBox.getText());
						}
						
						if(!imageFormats.isEmpty()){
							model.setValues(textFieldURL.getText(), imageFormats, Integer.parseInt(textFieldMinWidth.getText()), Integer.parseInt(textFieldMinHeight.getText()));
						
							progressUpdater();
							
							try {
								model.saveImages();
							} catch (IOException e) {
								JOptionPane.showMessageDialog(new JFrame(), "Error of IO in the connection.", "Error", JOptionPane.ERROR_MESSAGE);
							} catch (IllegalArgumentException e) {
								JOptionPane.showMessageDialog(new JFrame(), "Bad format for URL.", "Warning", JOptionPane.WARNING_MESSAGE);
							} catch (DirectoryNotCreatedException e) {
								JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							} catch (FileNotCreatedException e) {
								JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}

						}else{
							JOptionPane.showMessageDialog(new JFrame(), "Choose any image format.", "Warning", JOptionPane.WARNING_MESSAGE);
						}

						buttonStart.setEnabled(true);
					}
				});
				
				thread.start();
				
			}
		});
	}
	
	/**
	 * Update progress bar using information of the model.
	 */
	private void progressUpdater(){
		Thread thread = new Thread(new Runnable() {

			public void run() {
				while(progressBar.getValue() != 100){
					int progress = model.getProgress();
					progressBar.setValue(progress);
					labelProgress.setText(progress + " %");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(new JFrame(), "Internal application error, please restart application.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		
		thread.start();
	}
}
