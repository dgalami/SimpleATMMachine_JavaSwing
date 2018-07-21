
//Account class
public class Account {
	private double balance = 0.0;
	private static int numberOfWithdrawals = 0;
	
	//default constructor 
	Account(){
		balance = 0;
	}
	
	//constructor overload
	Account(double balance){
		this.balance = balance;
	}
	
	//method to withdraw money from account, throw exception if insufficient fund
	 public void withdrawProcess(String withdrawAmount) throws InsufficientFunds {
		 //check for sufficient funds for withdrawals and servicecharge
		 double amount = Double.parseDouble(withdrawAmount);
		 if(balance < amount){
			 throw new InsufficientFunds();
		 } else {
		 balance = balance - amount;
		 numberOfWithdrawals++;
		 }
	 }
	 
	 //method to deposit amount in account
	 public void depositProcess(String value){
		 balance = balance + Double.parseDouble(value);
	 }
	 
	 //method to subtract transfer amount from initial account balance and return transfer amount to deposit in another account
	 public String transferProcess(String transferAmount) throws InsufficientFunds {
		 double amount = Double.parseDouble(transferAmount);
		
		 	if(!((amount % 10) == 0)){
		 		throw new IllegalArgumentException();//throw IllegalArgumentException (Invalid Input) if withdraw amount is not multiple of 10
		 	}
		 	else if(balance < amount){
		 		throw new InsufficientFunds();//throw exception if insufficient funds
		 	}else {
		 		balance = balance - amount;
		 	}
		 	
		 return String.valueOf(amount);
	 }
	 
	 //method to check if total withdrawals is 5. If true, it will charge service fee of 1.25 from last withdrawals account.
	 //also check if balance is sufficient for service charge else throw exception
	 public void serviceCharge(String value) throws InsufficientFunds{
		 //service charge of 1.25 when more then 5 withdrawals
		 if(numberOfWithdrawals == 5){
			 double withdrawValue = Double.parseDouble(value);
			 if((balance - (withdrawValue + 1.25)) < 0){
				 throw new InsufficientFunds();
			 } else {
				 balance = balance -1.25;
				 numberOfWithdrawals = 0;
			 }
		 }
	 }
	 
	 //return balance of account
	 public double  getBalance(){
		 return balance;
	 }

}
