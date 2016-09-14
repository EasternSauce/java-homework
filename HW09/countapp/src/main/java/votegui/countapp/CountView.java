package votegui.countapp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class CountView extends JFrame{
	private JLabel introLabel = new JLabel("With this app you can secure and count votes");
	private JButton secureButton = new JButton("Secure votes");
	private JButton countButton = new JButton("Count votes");
	private JLabel sysLabel = new JLabel("Voting system:");
	private JTextField sysField = new JTextField(10);
	private JTextArea resultsArea = new JTextArea(10, 50);
	private JScrollPane scrollPane = new JScrollPane(resultsArea);
	private JProgressBar progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
	private JRadioButton xmlRadio = new JRadioButton("XML");
	private JRadioButton saxRadio = new JRadioButton("SAX");
	private boolean xml = false;
	private boolean sax = false;

	CountView(){
		this.setTitle("Count App");
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel countPanel = new JPanel(new GridBagLayout());

	

		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 1;

		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 4;
		countPanel.add(introLabel, gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		countPanel.add(sysLabel, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		gc.gridwidth = 1;
		countPanel.add(sysField, gc);

		gc.gridx = 2;
		gc.gridy = 1;
		gc.gridwidth = 1;
		countPanel.add(xmlRadio, gc);

		gc.gridx = 3;
		gc.gridy = 1;
		gc.gridwidth = 1;
		countPanel.add(saxRadio, gc);


		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 4;
		countPanel.add(secureButton, gc);

		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 4;
		countPanel.add(countButton, gc);

		gc.gridx = 0;
		gc.gridy = 4;
		gc.gridwidth = 4;
		countPanel.add(scrollPane, gc);

		gc.gridx = 0;
		gc.gridy = 5;
		gc.weightx = 1.0;
		gc.anchor = GridBagConstraints.PAGE_END;
		countPanel.add(progressBar, gc);

		this.add(countPanel);
		
	}

	public void showMessage(String message){
		JOptionPane.showMessageDialog(this, message);
	}


	public String getSystem(){
		return sysField.getText();
	}

	public void addSecureListener(final MyButtonListener listener){
		secureButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(MyButtonListener.canPressSecure){
					MyButtonListener.canPressSecure = false;
					new SwingWorker<Integer, Integer>(){
						public Integer doInBackground(){
							listener.workerJob();
							return 0;
						}

						public void done(){
							listener.done();
							MyButtonListener.canPressSecure = true;
						}
					}.execute();
				}
			}
		});
	}

	public void addCountListener(final MyButtonListener listener){
		countButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(MyButtonListener.canPressCount){
					MyButtonListener.canPressCount = false;
					new SwingWorker<Integer, Integer>(){
						public Integer doInBackground(){
							listener.workerJob();
							return 0;
						}

						public void done(){
							listener.done();
							MyButtonListener.canPressCount = true;
						}
					}.execute();

				}
			}
		});
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
		saxRadio.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(sax == false){
					sax = true;
				}
				else{
					sax = false;
				}

			}
		});
	}

	public void showResult(String string){
		resultsArea.setText(string);
	}

	public void progressBarOn(boolean v){
		progressBar.setIndeterminate(v);
	}

	public boolean isXML(){
		return xml;
	}

	public boolean isSAX(){
		return sax;
	}

	public void setProperties(){
		String result = "";
		Properties prop = new Properties();
		InputStream inputStream = null;
		try{
			inputStream = new FileInputStream("config.properties");
		}catch(FileNotFoundException e){
			System.out.println("config file not found");
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
		String sax = prop.getProperty("sax");

		if(xml.equals("true")) this.xml = true;
		if(xml.equals("false")) this.xml = false;
		if(sax.equals("true")) this.sax = true;
		if(sax.equals("false")) this.sax = false;

		xmlRadio.setSelected(this.xml);
		saxRadio.setSelected(this.sax);
	}
}
