import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
/**
 * @author Dima Alissa
 *
 */
public class Program {
	/**
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @param args
	 * @throws  
	 */
	static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public static final String filename = "./src/tasklist.csv";
	//input Variables.

	static Date dueDate = null;
	static String name;
	static String description = null;
	static int statusIndex =0;
	static String status = null;
	//instance of the List manager
	static Scanner input = new Scanner(System.in);
	static ListManager todoly = new ListManager(filename);
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		//set the lenient of simple date format to false (do not allow parsing in unwanted format)
		//To navigate through the interface
		//call the Main Menu Interface
        df.setLenient(false);
		boolean finished = false;
		while (!finished) {
			try {
				userInterface();
				input = new Scanner(System.in);
				if (input.hasNextInt()) {
					int x = input.nextInt();
					switch (x) {
						case 1:
							showOption();
							break;
						case 2:
							createOption();

							break;
						case 3:
							modifyOption();

							break;
						case 4:
							deleteOption();

							break;
						//choice 5 saving to file is still not working
						case 5:
							System.out.println("Save and quit");
							todoly.saveToCSV(filename);
							finished = true;
							break;
						default:
							System.out.println("Invalid Option Number");
					}
				} else {
					System.out.println("Input is not a number!");
				}
			}catch (InputMismatchException e){
				   System.out.println("NOt right");
			}
		}
		input.close();
	}

	// To display the Main menu
	static void userInterface(){

		System.out.println("Choose an option from the menu");
		System.out.println("1>> Enter Show Menu");
		System.out.println("2>> Create A New Task");
		System.out.println("3>> Modify A Task ");
		System.out.println("4>> Delete A Task");
		System.out.println("5>> Save AND Quit");

	}
	// To display the show options show all task or by date or by project
	static void showOption(){
		System.out.println("Choose an option from the menu");
		System.out.println("1- Show All Tasks");
		System.out.println("2- Show Tasks By project");
		System.out.println("3- Show Tasks By Due Date");
		System.out.println("5-Show Tasks By Status");
		System.out.println("5-Return to Main Menu");
		int i= input.nextInt();
		switch (i){
			case 1 :
				todoly.showAllTasksSorted();
				break;
			case 2 :
				System.out.println("Show Task List by project, choose the project Name");
				todoly.loadListOfProjects();
				int selectedProject = input.nextInt();
				todoly.showTaskByProject(todoly.getProject(selectedProject));
				break;
			case 3 :
				System.out.println("Insert the date to show tasks by Use format (dd/mm/yyyy)");
				todoly.showTaskByDate(userDateInput());
				break;
			case 4 :
				todoly.showTaskByStatus(userStatusInput());
			case 5:
				break;
			default:
				System.out.println("Invalid Option Number");
		}
	}
	//  create new task option
	static void createOption(){
		System.out.println("To Create a Task choose the project it belongs to");
		int indexOfProject;
		//display project list and check user input for valid project number
		todoly.loadListOfProjects();
		indexOfProject = input.nextInt();
		while ((todoly.getProject(indexOfProject)== null)) {
			indexOfProject = input.nextInt();

			System.out.println("Invalid Project please check ");
			todoly.loadListOfProjects();
		}
		System.out.print("Task Name :");
		name = input.next();
		System.out.print("Task Description :");
		description = input.next();

		dueDate= userDateInput();
		status= userStatusInput();
		todoly.addTaskToProject(indexOfProject, name, description, dueDate,status );
	}

	static void modifyOption()throws InputMismatchException {
		System.out.println("To Modify A Task Please Insert Task No: ");
		todoly.showAllTask();
		int index;
		do{
			System.out.println("Insert Task No :");
			index = input.nextInt();
		}while (!todoly.checkTaskIndex(index));
		System.out.println(todoly.getTask(index).getDetails());
		System.out.println("what do you wnt to change\n");
		System.out.print("Task Name :");
		Scanner input = new Scanner(System.in);
		String taskname = input.nextLine();
		System.out.print("Task Description :");
		String taskdescription = input.nextLine();
		dueDate= userDateInput();
		status= userStatusInput();
		String name="";
        if (taskname.isEmpty()) {
            name = todoly.getTask(index).getName();
        }
        if (taskdescription.isEmpty()) {
            description = todoly.getTask(index).getName();
        }

		todoly.modifyTask(index,name,description, dueDate, status);
	}
	static void deleteOption()throws InputMismatchException{
		System.out.println("Choose a Task to Delete ");
		System.out.print("Task No. ");
		int index;
		do{
			System.out.println("Insert Task No :");
			index = input.nextInt();

		}while (todoly.getTask(index)==null);
		todoly.deleteTask(index);

	}
	/*
	 * Get and check user input for the status of the task
	 */
	static String userStatusInput(){
		do{
			System.out.print("Choose the status of the task : ");
			System.out.println(todoly.statusOptions());
			System.out.print("Task Status :");
			 statusIndex = input.nextInt();
			status=todoly.checkStatusOptions(statusIndex);

		}while (status==null);
		return status;
	}
	static Date userDateInput(){
		df.setLenient(false);
		do{
			System.out.print("Task DueDate Format(dd/MM/yyyy):");
			String inputDate = input.next();
			try {
				dueDate = df.parse(inputDate.trim());
			} catch (ParseException e) {
				System.out.println("Wrong Date or Wrong format :(");
			}
		} while (dueDate==null);
		return dueDate;
	}
}


