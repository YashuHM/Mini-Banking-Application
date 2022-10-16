package bank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

class Customer {
	 private String username,pass,mob,bname,ifsc;
	 private long acc_no;
	 private double balance;
	 
	
	 
	 
	 Scanner scanner = new Scanner(System.in);
  
 public Customer(String username, String pass, String mob, String bname, String ifsc,long acc_no, double balance)
 {
		 
		this.username = username;
		this.pass = pass;
		this.mob = mob;
		this.bname = bname;
		this.ifsc = ifsc;
		this.acc_no = acc_no;
		this.balance = balance;
		 
	}





public Customer() {
	super();
}





public String getUsername() {
	return username;
}





public void setUsername(String username) {
	this.username = username;
}





public String getPass() {
	return pass;
}





public void setPass(String pass) {
	this.pass = pass;
}





public String getMob() {
	return mob;
}





public void setMob(String mob) {
	this.mob = mob;
}





public String getBname() {
	return bname;
}





public void setBname(String bname) {
	this.bname = bname;
}





public String getIfsc() {
	return ifsc;
}





public void setIfsc(String ifsc) {
	this.ifsc = ifsc;
}





public long getAcc_no() {
	return acc_no;
}





public void setAcc_no(long acc_no) {
	this.acc_no = acc_no;
}





public double getBalance() {
	return balance;
}





public void setBalance(double balance) {
	this.balance = balance;
}
}