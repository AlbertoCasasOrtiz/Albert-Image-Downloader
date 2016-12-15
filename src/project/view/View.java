package project.view;

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
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.model.Model;

public class View extends JFrame{
	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
	private ArrayList<JCheckBox> imageCheckBox;
	
	private GridBagConstraints constraintsGbc;
	private GridBagLayout layoutGbc;
	
	private JLabel labelMinWidth;
	private JLabel labelMinHeight;
	private JLabel labelURL;
	
	private JTextField textFieldMinWidth;
	private JTextField textFieldMinHeight;
	private JTextField textFieldURL;
	
	private JButton buttonStart;
	
	private Model model;
	
	public View(Model model){
		super("Image Downloader");
		this.model = model;
		
		this.initializeComponents();
		
		this.setSize(new Dimension(600, 300));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(this.layoutGbc);

		this.locateElements();
		
		this.listener();
		
		this.setVisible(true);
	}
	
	private void initializeComponents(){
		this.imageCheckBox = new ArrayList<JCheckBox>();
		this.layoutGbc = new GridBagLayout();
		this.constraintsGbc = new GridBagConstraints();
		
		this.labelMinWidth = new JLabel("Min. Width");
		this.labelMinHeight = new JLabel("Min. Height");
		this.labelURL = new JLabel("URL");
		
		this.textFieldMinWidth = new JTextField("100");
		this.textFieldMinHeight = new JTextField("100");
		this.textFieldURL = new JTextField();
		
		this.buttonStart = new JButton("Start");
	}

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
	}
	
	private JPanel panelInputURL(){
		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(this.labelURL);
		panel.add(this.textFieldURL);
		panel.add(this.buttonStart);
		return panel;
	}
	
	private JPanel panelSelectImageFormats(){
		JPanel panel = new JPanel(new GridLayout(6, 4));
		panel.setBorder(BorderFactory.createTitledBorder("Image Formats"));
		for(int i = 0; i < Model.imageFormatList.length; i++){
			this.imageCheckBox.add(new JCheckBox(Model.imageFormatList[i]));
			panel.add(this.imageCheckBox.get(i));
		}
		return panel;
	}
	
	private JPanel panelMinParameters(){
		JPanel panel = new JPanel(new GridLayout(1, 4));
		panel.add(this.labelMinWidth);
		panel.add(this.textFieldMinWidth);
		panel.add(this.labelMinHeight);
		panel.add(this.textFieldMinHeight);
		return panel;
	}
	
	private void listener(){
		this.buttonStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						buttonStart.setEnabled(false);
						
						ArrayList<String> imageFormats = new ArrayList<String>();
						
						for(int i = 0; i < imageCheckBox.size(); i++){
							if(imageCheckBox.get(i).isSelected())
								imageFormats.add(imageCheckBox.get(i).getText());
						}
						
						model.setValues(textFieldURL.getText(), imageFormats, Integer.parseInt(textFieldMinWidth.getText()), Integer.parseInt(textFieldMinHeight.getText()));
						
						try {
							model.saveImages();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						buttonStart.setEnabled(true);
					}
				});
				
				thread.start();
				
			}
		});
	}
}
