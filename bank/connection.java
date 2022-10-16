package bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class connection {
	String bname,ifsc;
	long acc_no,acctblAccNo;
	double balance,acctblBalance;
	double amt;
	Random r=new Random();	
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
	LocalDate datenow = LocalDate.now(); 
	String d=String.valueOf(datenow);
	
	
	Scanner scanner = new Scanner(System.in);

	//connection
		static Connection getCon() throws ClassNotFoundException, SQLException {
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 
			Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","4BD19IS122");
	  if(con!=null)
		  return con;
	  else
		  return null;
		}

  //ifsc code
		public void genifsc() {
			if(bname=="ABC")
				ifsc= "ABC078";
			else
				ifsc= "XYZ365";
			System.out.println("Your IFSC code is"+ifsc);
		}

	//Phon no	
		public static boolean isValid(String mob) {
			Pattern p=Pattern.compile("^\\d{10}$");
			Matcher m=p.matcher(mob);
			return m.matches();
		}
		
	//registeration
		public   void registerCutomer() throws ClassNotFoundException, SQLException
		{ 
			
		  	     System.out.print("Enter username: ");
		  	     	String   username = scanner.next();
		         System.out.print("Enter Password: ");
		         	String   pass = scanner.next();
		        System.out.print("Enter Mobile number: ");
		        	String mob = scanner.next();
		        	if(isValid(mob));
			        else {
			        	System.out.print("Invalid mobile no.\n");
			        	System.exit(0);
			        }	
		         System.out.print("Choose the Branch \n");
		         System.out.print("1.Abc\n2.xyz\n");
		         	int x = scanner.nextInt();
		         	if(x==1)
		         		bname="ABC";
		         	else if(x==2) 
		         		bname="XYZ";
		         	else 
		         		System.out.print("invalid Branch name\n");
		         
		      System.out.print("Enter the amount to Deposit");
		        double  balance=scanner.nextDouble();
		        if(balance<500)
		        	System.out.println("Invalid Ammount\n");
		        else {
		         genifsc();
			Connection con=getCon();
			String query="insert into registerCutomer (username,pass,mob,bname,ifsc,balance)values(?,?,?,?,?,?)";
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2,pass );
			pst.setString(3,mob);
			pst.setString(4,bname );
			pst.setString(5,ifsc);
			pst.setDouble(6,balance);
			pst.executeUpdate();
			String q= "select acc_no from registerCutomer where acc_no=(select max(acc_no) from  registerCutomer)";
			PreparedStatement p= con.prepareStatement(q);
			ResultSet r= p.executeQuery();
		 r.next() ;
			 
				long an= r.getLong(1);
		 System.out.println("Account number is"+an);
			
	//acc table
			String query1="insert into account (date,acc_no,balance)values(?,?,?)";
			PreparedStatement pst1=con.prepareStatement(query1);
				
			pst1.setString(1, d);
			pst1.setLong(2, an);
			pst1.setDouble(3,balance);
			pst1.executeUpdate();
			
   //trans
			String query2="insert into transaction (date,acc_no,balance,cr_dr)values(?,?,?,?)";
			PreparedStatement pst2=con.prepareStatement(query2);
			 pst2.setString(1, d);
			 pst2.setLong(2, an);
			pst2.setDouble(3,balance);
			pst2.setString(4,"cr");
			pst2.executeUpdate();
			System.out.println("succ..........................................");
		}
		}
	
	//login
		public void login() throws SQLException, ClassNotFoundException {
			System.out.print("Enter username: ");
		    String  username = scanner.next();
		    System.out.print("Enter Password: ");
		    String pass = scanner.next();
		    Connection con =getCon();
			String query3="select username,pass,acc_no from registerCutomer where username=? and pass=?";
			PreparedStatement pst4= con.prepareStatement(query3);
			pst4.setString(1, username);
			pst4.setString(2, pass);
			ResultSet rs= pst4.executeQuery();
			while(rs.next())
			{
				if(rs.getString(1).equals(username) && rs.getString(2).equals(pass))
				{
					acctblAccNo=rs.getLong(3);
				}
				else {
					System.out.println("wrong username or password");
				}	
			}
			String query4="select acc_no,balance from account where acc_no=?";
			PreparedStatement pst5= con.prepareStatement(query4);
			pst5.setLong(1,acctblAccNo);
			ResultSet rs4= pst5.executeQuery();
			
			while(rs4.next())
			{
				if(rs4.getLong(1)==acctblAccNo )
				{
					 acctblBalance=rs4.getDouble(2);
					 System.out.println("Account balance from acc tbls:"+acctblBalance +"Account no:"+acctblAccNo);
				 menu();
				}
				else {
					System.out.println("wrong username or password");
				}
			}
	}
	
		
		
	// main
		public void  menu() throws ClassNotFoundException, SQLException{
				int selectedOption;
    
        System.out.println("Please Select an option below:");
        System.out.println("Press 1 to Deposit Amount.");
        System.out.println("Press 2 to Withdraw Amount.");
        System.out.println("Press 3 to View Balance");
       // System.out.println("Press 4 to View Statement");
        System.out.println("Press any  Number to Exit");
        System.out.println(" ");
        
        System.out.print("Press any Number: ");
        selectedOption = scanner.nextInt();
        switch (selectedOption) {
            case 1:
            	 deposit();
                menu();
                break;
            case 2:
                 System.out.print("Please Enter the amount to withdraw: ");
                 int withamt=scanner.nextInt();
                 withdraw(withamt);
             menu();
                break;
            case 3:
            	ckeckBalance();
            	System.out.println(" ");  
                  menu();
                break;
      
            default:
                  System.out.println("Transaction Ended, Your ABC Bank Account Logout Successfully !");
                  System.exit(0);
                break;
        }
     }

		
	//balance
		private void ckeckBalance()throws ClassNotFoundException, SQLException {
			String query12="select balance from account where acc_no=?"; 
			Connection con =getCon();
			PreparedStatement pst13= con.prepareStatement(query12);
			pst13.setDouble(1,acctblAccNo);
			ResultSet rs5= pst13.executeQuery();
				while(rs5.next()) {
					Double totBalance=rs5.getDouble(1);
					System.out.println("Account balance :"+totBalance);
		}
		
	
}
	
	//deposit
		public void deposit() throws ClassNotFoundException, SQLException {
			System.out.print("Enter the amount to be deposited: ");
	        amt = scanner.nextInt();
	        
	         acctblBalance+=amt+balance;
	         System.out.println("updated "+acctblBalance );
	         String query5="update account set balance=? where acc_no=?";
	      
	         Connection con =getCon();
				PreparedStatement pst6= con.prepareStatement(query5);
				System.out.println("now bal: "+acctblBalance);
				pst6.setDouble(1,acctblBalance);
				pst6.setLong(2,acctblAccNo);
				pst6.executeUpdate();
			 
				String query6="insert into transaction (date,acc_no,balance,cr_dr)values(?,?,?,?)";
				PreparedStatement pst7=con.prepareStatement(query6);
				 pst7.setString(1,d);
				 pst7.setLong(2,acctblAccNo);
				pst7.setDouble(3,amt);
				pst7.setString(4,"dr");
				pst7.executeUpdate();
				System.out.println("succ...........");
	    }
		

 //withdraw
		public void withdraw(int amt) throws ClassNotFoundException, SQLException{
			System.out.println(" ");
    
			if(acctblBalance < amt)
			{
				System.out.println("Not Enough Balance"); 
			}
			else{
				acctblBalance-=amt;
        System.out.println("Withdraw Sucessfull");
        System.out.println("Available Balance: " +acctblBalance);
        System.out.println(" ");
        String query7="update account set balance=? where acc_no=?";
        String query9="insert into transaction (date,acc_no,balance,cr_dr)values(?,?,?,?)";
		
        Connection con =getCon();
			PreparedStatement pst8= con.prepareStatement(query7);
			PreparedStatement pst10= con.prepareStatement(query9);
			
			System.out.println("now bal: "+acctblBalance);
			
			pst8.setDouble(1,acctblBalance);
			pst8.setLong(2,acctblAccNo);
			pst8.executeUpdate();
	
			 pst10.setString(1,d);
			 pst10.setLong(2,acctblAccNo);
			 pst10.setDouble(3,amt);
			 pst10.setString(4,"dr");
			 pst10.executeUpdate();
	        	
    }

}
}