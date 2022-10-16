package bank;

import java.util.Scanner;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class bank {

	public static void main(String[] args) throws ClassNotFoundException,SQLException {
		Scanner sc=new Scanner(System.in);
	 
System.out.println("welcome to ABC Bank");
connection c =new connection();
System.out.println(" 1.Sign Up\n  2.Signin\n");


int a= sc.nextInt();
if(a==1) {
	 c. registerCutomer();
}
else if(a==2) {
 	c.login();
}
else
System.out.println("Invalid Option Selected\n");	
}
}