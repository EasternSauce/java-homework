package votegui.voteapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


public class VoteView extends JFrame{
	private JLabel introLabel = new JLabel("This app lets you vote for your candidate.\n Enter data below.");
	private JLabel nameLabel = new JLabel("Your name: ");
	private JTextField nameField = new JTextField(10);
	private JLabel candLabel = new JLabel("Your candidates: ");
	private JTextField candField[] = new JTextField[5];
	private JLabel weightLabel = new JLabel("  Weights: ");
	private JTextField weightField[] = new JTextField[5];
	private JLabel sysLabel = new JLabel("Voting system: ");
	private JTextField sysField = new JTextField(10);
	private JButton voteButton = new JButton("Vote");
	private JRadioButton xmlRadio = new JRadioButton("XML");
	private boolean xml = false;

	VoteView(){
		for(int i = 0; i < 5; i++){
			candField[i] = new JTextField(10);
			weightField[i] = new JTextField(3);
		}

		this.setTitle("Vote App");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel votePanel = new JPanel(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0;
		gc.weighty = 1;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 4;

		votePanel.add(introLabel, gc);

		gc.anchor = GridBagConstraints.LINE_END;

		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy = 1;

		votePanel.add(nameLabel, gc);

		gc.gridwidth = 1;
		gc.gridx = 3;
		gc.gridy = 1;

		votePanel.add(xmlRadio, gc);

		gc.weighty = 0;

		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridheight = 5;

		votePanel.add(candLabel, gc);

		gc.gridx = 2;
		gc.gridy = 2;
		gc.gridheight = 5;

		votePanel.add(weightLabel, gc);
		
		gc.anchor = GridBagConstraints.LINE_START;

		gc.weighty = 1;
		gc.gridheight = 1;
		gc.gridx = 1;
		gc.gridy = 1;

		votePanel.add(nameField, gc);

		gc.weighty = 0;
				
		for(int i = 0; i < 5; i++){
			gc.gridx = 1;
			gc.gridy = 2 + i;
			votePanel.add(candField[i], gc);
			gc.gridx = 3;
			gc.gridy = 2 + i;
			votePanel.add(weightField[i], gc);
		}

		gc.weighty = 1;

		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx = 0;
		gc.gridy = 7;

		votePanel.add(sysLabel, gc);

		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 7;

		votePanel.add(sysField, gc);

		gc.anchor = GridBagConstraints.CENTER;

		gc.gridx = 0;
		gc.gridy = 8;
		gc.gridwidth = 4;

		votePanel.add(voteButton, gc);
		
		this.add(votePanel);
	}

	public String getName(){
		return nameField.getText();
	}
	public String[] getCandidates(){
		String cands[] = new String[5];
		for(int i = 0; i < 5; i++){
			cands[i] = candField[i].getText();
		}
		
		return cands;
	}

	public String[] getWeights(){
		String weights[] = new String[5];
		for(int i = 0; i < 5; i++){
			weights[i] = weightField[i].getText();
		}
		
		return weights;
	}

	public String getVotingSystem(){
		return sysField.getText();
	}


	public void addVoteListener(ActionListener listener){
		voteButton.addActionListener(listener);
	}

	public void showMessage(String message){
		JOptionPane.showMessageDialog(this, message);
	}

	public void clearFields(){
		nameField.setText("");
		sysField.setText("");
		for(int i = 0; i < 5; i++){
			candField[i].setText("");
			weightField[i].setText("");
		}
	}

	public void addRadioListener(){
		xmlRadio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(xml == false){
					xml = true;
				}
				else{
					xml = false;
				}
			}
		});
	}

	public boolean isXML(){
		return xml;
	}

	public void setProperties(){
		String result = "";
		Properties prop = new Properties();
		InputStream inputStream = null;
		try{
			inputStream = new FileInputStream("config.properties");
		}catch(FileNotFoundException e){
			System.out.println("no config file");
		}

		try{
			if(inputStream != null){
				prop.load(inputStream);
			}else{
				System.out.println("property file problem");
			}
		}catch(IOException e){
			System.out.println("io problem");
		}

		String xml = prop.getProperty("xml");

		if(xml.equals("true")) this.xml = true;
		if(xml.equals("false")) this.xml = false;

		xmlRadio.setSelected(this.xml);
	
	}

}
