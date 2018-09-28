import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 * 
 */

/**
 * @author Dima Alissa
 *
 */
public class Program {
	/**
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args)   {	
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
	String filename="/Users/tmp-sda-1182/Documents/ToDoList/ToDoly/src/tasklist.csv";
	//input Variables.
	Date dueDate= null;
	Date dateFilter =null;
	String project= null;
	String name;
	String description = null;
 	String status = null;
 	//instance of the List manager
	ListManager todoly = new ListManager (filename);
	Scanner input = new Scanner(System.in);
		todoly.showAllTask();
		
		//To navigate through the interface
		System.out.println("Please enter the number of your cohice");
		int x = input.nextInt();
	switch (x) {
		case 1 :
			System.out.println("Show Task List by project, choose the project Name");
			String selectedProject= input.next();
			selectedProject =selectedProject.toUpperCase();
			todoly.showTaskByProject(selectedProject);
			break;
		case 2:
			System.out.println("Show Task List by Date, choose the project number");
			System.out.println("Insert the date (dd/mm/yyyy)");
			String selectedDate= input.next();
			try{
			    dueDate = df.parse(selectedDate);
			} catch (ParseException e){ System.out.println("invalid format");}
			todoly.showTaskByDate(dateFilter);
			break;
		case 3:
			System.out.println("Create Task");
			project = input.next();
			name = input.next();
			description = input.next();
			String inputdate =input.next();
		try {
			dueDate= df.parse(inputdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			status = input.next();
			todoly.createNewTask(project,name,description,dueDate,status);
			todoly.showAllTask();
			break;
		case 4:
			System.out.println("Modify Task");
			 break;
			 //choice 5 saving to file is still not working 
		case 5:
			System.out.println("Save and quit");
			String selectedpath = "/Users/tmp-sda-1182/Documents/ToDoList/ToDoly/src/tasklist.csv";
			todoly.saveToCSV(selectedpath);
			break;
	}
	input.close();
	}
}


