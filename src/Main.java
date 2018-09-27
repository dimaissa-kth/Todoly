import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int [] choices = {1,2,3,4,5};
		System.out.println("Please enter the number of your cohice");
		Scanner input = new Scanner(System.in);
		int x = input.nextInt();
		
		
		
		switch (x) {
			case 1 :
				System.out.println("Show Task List by date ");
				System.out.println("Insert the date (dd/mm/yyyy)");
				String testDate= input.next();
			case 2 :
				System.out.println("Show Task List by project, choose the project number");
				
				
		}
		
		
		
		
	}

}
