/**
 * File: P2GUI.java
 * Author: Deepak Galami
 * Date: 04 Feb 2017
 * Purpose: Write a program that implemants an ATM machine. The Interafce of the 
 * program should be a java Swing GUI and should be hand coded. The first class P2GUI
 * should define GUI and must cotain Account objects, one for checking and another for 
 * saving Account. The program should perform Withdraw, Deposit, Transfer to and balance
 * of each account. It also have InsufficientFunds class which is user defined checked 
 * exception.
 * 
 * ***/


//import java class for GUI and event handling
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class P2GUI extends JFrame implements ActionListener{	//inheriting JFrame and implementing ActionListener(multiple inheritance)
	//default witdth and height of frame
	static final int WIDTH = 500, HEIGHT = 250;
	
	//create jbutton
		private JButton	withdrawButton = new JButton("Withdraw");
		private JButton	depositButton = new JButton("Deposit");
		private JButton	transferButton = new JButton("Transfer To");
		private JButton	balanceButton = new JButton("Balance");
		private JRadioButton checkingRadio = new JRadioButton("Checking");
		private JRadioButton savingRadio = new JRadioButton("Savings");
		private JTextField textField = new JTextField("",25);
		
		//create checking and saving account of Account type
		Account checking = new Account();
		Account saving = new Account();
		
	//default constructor
	P2GUI(){
		super("ATM Machine");
		setFrame(WIDTH, HEIGHT);
		
	}

	public void setFrame(int width, int height){
		//add properties to frame
		setSize(width, height);
		setLocationRelativeTo(null);
		setPreferredSize(new Dimension(491,252));
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//craete panel and add components
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());	//set layout manager
		getContentPane().add(mainPanel);
		withdrawButton.setPreferredSize(new Dimension(150,50));
		depositButton.setPreferredSize(new Dimension(150,50));
		mainPanel.add(withdrawButton);
		mainPanel.add(depositButton);
		
		JPanel secondPanel = new JPanel(new FlowLayout());
		mainPanel.add(secondPanel);
		transferButton.setPreferredSize(new Dimension(150,50));
		balanceButton.setPreferredSize(new Dimension(150,50));
		secondPanel.add(transferButton);
		secondPanel.add(balanceButton);
		
		ButtonGroup radioGroup = new ButtonGroup();	//grouping radio button
		radioGroup.add(checkingRadio);
		radioGroup.add(savingRadio);
	
		JPanel radioPanel = new JPanel(new GridLayout(1,1, 90, 5));	//panel for radio buton with gridlayout
		mainPanel.add(radioPanel);
		checkingRadio.setSelected(true);
		radioPanel.add(checkingRadio);
		radioPanel.add(savingRadio);
	
		JPanel textPanel = new JPanel(new FlowLayout());
		mainPanel.add(textPanel);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setPreferredSize(new Dimension(150,30));
		textPanel.add(textField);
		
		//add action lister of this class
		withdrawButton.addActionListener(this);
		depositButton.addActionListener(this);
		balanceButton.addActionListener(this);
		transferButton.addActionListener(this);
		
	}
	
	//catch the action event in this class and perform the task.
	public void actionPerformed(ActionEvent e){
		Object COMPONENT = e.getSource();
		
		//perform withdraw balance when withdraw button clicked.
		//throw exception if insufficient fund
		//throw exception of invalid input in text box.
		if(COMPONENT == withdrawButton){
			try{
				String value = textField.getText();
				//withdraw from checking account
				if(isPositiveNumber(value) && checkingRadio.isSelected()){
					int dialogResult = JOptionPane.showConfirmDialog(null, "Confirmed Withdraw Amount" ,"WITHDRAW", JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
						if(dialogResult == JOptionPane.OK_OPTION){	//perform only when ok button is clicked
							checking.serviceCharge(value);
							checking.withdrawProcess(value);
							JOptionPane.showMessageDialog(null, "Withdraw Successful from Checking Account","WITHDRAW", JOptionPane.INFORMATION_MESSAGE);
							
						}
						textField.setText(null);
					
						//withdraw from saving account
				} else if(isPositiveNumber(value) && savingRadio.isSelected()){
					int dialogResult = JOptionPane.showConfirmDialog(null, "Confirmed Withdraw Amount" ,"WITHDRAW", JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
					if(dialogResult == JOptionPane.OK_OPTION){ //perform only when ok button is clicked
						saving.serviceCharge(value);
						saving.withdrawProcess(value);
						JOptionPane.showMessageDialog(null, "Withdraw Successful from Saving Account","WITHDRAW", JOptionPane.INFORMATION_MESSAGE);
						
					}
					textField.setText(null);
					
				} else {
					throw new IllegalArgumentException();	//throw exception if invalid input.
				}
				
				//catch exceptions
			} catch(InsufficientFunds x){
				JOptionPane.showMessageDialog(null, "Sorry your balance is low", "INSUFFICIENT FUNDS", JOptionPane.ERROR_MESSAGE);
				textField.setText(null);
				
			} catch(IllegalArgumentException x){
				JOptionPane.showMessageDialog(null, "Invalid Input! Try Again", "ERROR", JOptionPane.ERROR_MESSAGE);
				textField.setText(null);
			}
			
		}
		
		//perform deposit amount in selected account when deposit button clicked
		if(COMPONENT == depositButton){
			String value = textField.getText();
			//diposit in checking account
			if(isPositiveNumber(value) && checkingRadio.isSelected()){
				int okResult = JOptionPane.showConfirmDialog(null, "Confirmed Deposit" ,"CHECKING ACCOUNT DEPOSIT",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(okResult == 0){ //only if ok button is clicked then perform deposit
						checking.depositProcess(value);
						JOptionPane.showMessageDialog(null, "Balance Deposited Successfully", "BALANCE DEPOSIT",JOptionPane.INFORMATION_MESSAGE);
					}
					textField.setText(null);
			} 
			
			//deposit in saving account
			else if(isPositiveNumber(value) && savingRadio.isSelected()){
				int okResult = JOptionPane.showConfirmDialog(null, "Confirmed Deposit" ,"SAVING ACCOUNT DEPOSIT",JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(okResult == 0){	//only if ok button is clicked then perform deposit
						saving.depositProcess(value);
						JOptionPane.showMessageDialog(null, "Balance Deposited Successfully", "BALANCE DEPOSIT",JOptionPane.INFORMATION_MESSAGE);
					}
					textField.setText(null);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Input! Try Again", "ERROR", JOptionPane.ERROR_MESSAGE);
				textField.setText(null);
			}
		}
		
		//perform balance transfer when transfer button is clicked
		//also check if sufficient fund to transfer else throw catch exception
		if(COMPONENT == transferButton){
			try {
				String value = textField.getText();
				
				//for amount transfer from saving to checking account
				if(isPositiveNumber(value) && checkingRadio.isSelected()){
					int dialogResult = JOptionPane.showConfirmDialog(null, "Amount Transfering from Saving to Checking Account" ,"BALANCE TRANSFER", JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
						if(dialogResult == JOptionPane.OK_OPTION){ ////perform only when ok button is clicked
								checking.depositProcess(saving.transferProcess(value));
							JOptionPane.showMessageDialog(null, "Balance Transfered Successfully", "BALANCE TRANSFER",JOptionPane.INFORMATION_MESSAGE);
						}
					textField.setText(null);
				}
			
				//for amount transfer from checking to saving account
				else if(isPositiveNumber(value) && savingRadio.isSelected()){
					int dialogResult = JOptionPane.showConfirmDialog(null, "Amount Transfering from Checking to Saving Account" ,"BALANCE TRANSFER", JOptionPane.OK_CANCEL_OPTION ,JOptionPane.INFORMATION_MESSAGE);
						if(dialogResult == JOptionPane.OK_OPTION){ //perform only when ok button is clicked
								saving.depositProcess(checking.transferProcess(value));
							JOptionPane.showMessageDialog(null, "Balance Transfered Successfully", "BALANCE TRANSFER",JOptionPane.INFORMATION_MESSAGE);
						}
					textField.setText(null);
				} else {
					throw new IllegalArgumentException();
				}
				//catch exception
			} catch(InsufficientFunds x){
				JOptionPane.showMessageDialog(null, "Sorry your balance is low", "INSUFFICIENT FUNDS", JOptionPane.ERROR_MESSAGE);
				textField.setText(null);
				
			} catch(IllegalArgumentException x){
				JOptionPane.showMessageDialog(null, "Invalid Input! Try Again", "ERROR", JOptionPane.ERROR_MESSAGE);
				textField.setText(null);
			}
		}
		
		//show balance of the selected account when balance button is clicked
		if(COMPONENT == balanceButton){
			
			//display balance of checking account in text field
			if(checkingRadio.isSelected()){
				double checkingBalance = checking.getBalance();
				JOptionPane.showMessageDialog(null, "Checking Account balance: $" + checkingBalance, "CHECKING ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
			}
			
			//display balance of saving account in text field
			if(savingRadio.isSelected()){
				double savingBalance = saving.getBalance();
				JOptionPane.showMessageDialog(null, "Saving Account balance: $" + savingBalance, "SAVING ACCOUNT", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		
		
	}
	
	//method to check if input text is positive number
	public boolean isPositiveNumber(String value){
		if(value == null){
			return false;
		} 
		int size = value.length();
		if(size == 0){
			return false;
		}
		if(value.charAt(0) == '-'){
			return false;
		}
		if(!isNumber(value)){
			return false;
		}
		return true;
	}
	
	//check if input text is double
	public boolean isNumber(String value){
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
		
	//display frame
	public void displayFrame(){
		setVisible(true);
	}
	
	//main method 
	public static void main(String[] args){
		
		P2GUI test = new P2GUI();	//create object of P2GUI class
		test.displayFrame();	//display frame
	}
}
