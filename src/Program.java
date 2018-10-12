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
	/** The Main Menu class 
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @param args
	 * @throws  
	 */
	static SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public static final String filename = "./src/tasklist.csv";
	//input Variables.
	static String project= null;
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

		System.out.println("<<<<<<    Welcome To ToDOLY    >>>>>>");
		System.out.println("<<<<<< Your ToDoList Organizer >>>>>>>");
		todoly.showTasksCount();

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
				   System.out.println("Not right");
			}
		}
		input.close();
	}

	/*
	 * To display the Main menu
	 */
	static void userInterface(){
		System.out.println("Choose an option from the menu");
		System.out.println("1>> Enter Show Menu");
		System.out.println("2>> Create A New Task or Project");
		System.out.println("3>> Modify A Task ");
		System.out.println("4>> Delete A Task");
		System.out.println("5>> Save AND Quit");

	}
	/*
	 * To display the show options show all task or by date or by project
	 */
	static void showOption() throws InputMismatchException{
		System.out.println("Choose an option from the menu");
		System.out.println("1- Sort All Tasks By Date");
		System.out.println("2- Show Tasks By project");
		System.out.println("3- Show Tasks By Due Date");
		System.out.println("4-Show Tasks By Status");
		System.out.println("5-Return to Main Menu");
		int i= input.nextInt();
		switch (i){
			case 1 :// Show Tasks that are sorted and grouped by their status
				todoly.showAllTasksSorted();
				break;
			case 2 : //display tasks based on selected project
				System.out.println("Show Task List by project, choose the project Name");
				todoly.showListOfProjects();
				int selectedProject = input.nextInt();
				todoly.showTaskByProject(todoly.getProject(selectedProject));
				break;
			case 3 :// display tasks based on selected date 
				System.out.println("Insert the date to show tasks by Use format (dd/mm/yyyy)");
				todoly.showTaskByDate(userDateInput());
				break;
			case 4 :// display tasks based on selected status
				todoly.showTaskByStatus(userStatusInput());
			case 5: // go back go main menu
				break;
			default:
				System.out.println("Invalid Option Number");
		}
	}
	/**
	 * create new option, Has two cases either to create a new task or create a new project
	 * @throws InputMismatchException
	 */
	static void createOption() throws InputMismatchException{
		System.out.println("Choose a number ");
		System.out.println("1>>Add a new Task");
		System.out.println("2>>Add a new project");
		int i = input.nextInt();
		input.nextLine();
		switch (i){
			//To create a new Task to an existing project
			case 1 :
				if (!todoly.isListOfProjectsIsempty()){
					System.out.println("To Create a Task choose the project it belongs to");
					int indexOfProject;
					//display project list and check user input for valid project number
					todoly.showListOfProjects();
					indexOfProject = input.nextInt();
					while ((todoly.getProject(indexOfProject)== null)) {
						indexOfProject = input.nextInt();

						System.out.println("Invalid Project please check ");
						todoly.showListOfProjects();
					}
					input.nextLine();//To avoid blank input
					System.out.print("Task Name :");
					name = input.nextLine();
					System.out.print("Task Description :");
					description = input.nextLine();
					dueDate= userDateInput();
					status= userStatusInput();
					todoly.addTaskToProject(indexOfProject, name, description, dueDate,status );
					break;
				}else{
					System.out.println("You do not have any project , Add new project First");
				}
			// To create a new Project	
			case 2 :
				System.out.println("Enter new Project name :");
				//input.nextLine();
				String projectName =input.nextLine().trim();
				todoly.addNewProject(projectName);
				todoly.showListOfProjects();
				break;
			default:
				break;
		}
	}
	/**
	 *Display the Modify options , to modify a task based on its index ,
	 * new values should be entered otherwise old ones are used
	 * @throws InputMismatchException
	 */
	static void modifyOption()throws InputMismatchException {
		//
		System.out.println("To Modify A Task Please Insert Task No: ");
		todoly.showAllTask();
		int index;
		do{
			System.out.println("Insert Task No :");
			index = input.nextInt();
		}while (!todoly.checkTaskIndex(index));
		input.nextLine();
		System.out.println(todoly.getTask(index).getDetails());
		System.out.println("Do you want to change Task Project choose( y or n)");
		String choice = input.nextLine();
		if (choice.equalsIgnoreCase("y"))
		{
			project=userProjecInput();
			input.nextLine();
		}
			else {
			project = todoly.getTask(index).getProject();
		}
		
		System.out.println("Do you want to change Task Name choose( y or n)");
		choice = input.nextLine();
		if (choice.equalsIgnoreCase("y"))
		{
			System.out.print("Task New Name :");
			Scanner input = new Scanner(System.in);
			name = input.nextLine();
		}
			else {
			name = todoly.getTask(index).getName();
		}
		System.out.println("Do you want to change Task Description choose( y or n)");
		Scanner input = new Scanner(System.in);
		choice = input.nextLine();
		if (choice.equalsIgnoreCase("y"))
		{	
			System.out.print("Task New Description :");
			description = input.nextLine();
		}
			else {
			description = todoly.getTask(index).getDescription();
		}
		
		System.out.println("Do you want to change Task Date choose( y or n)");
         choice = input.nextLine();
        if (choice.equalsIgnoreCase("y"))
		{
			dueDate= userDateInput();
		} else {
        	dueDate = todoly.getTask(index).getDueDate();
		}
		System.out.println("Do you want to change Task Status choose( y or n)");
		
		 choice = input.nextLine();
		if (choice.equalsIgnoreCase("y"))
		{	
			status= userStatusInput();
		} else {
			status = todoly.getTask(index).getStatus();
		}
		todoly.modifyTask(index,project,name,description, dueDate, status);
	}
	/**
	 * To Delete a task based on its index (task Number) 
	 * @throws InputMismatchException
	 */
	static void deleteOption()throws InputMismatchException{
		System.out.println("Choose a Task to Delete ");
		todoly.showAllTask();
		System.out.print("Task No. ");
		int index;
		do{
			System.out.println("Insert Task No :");
			index = input.nextInt();

		}while (todoly.getTask(index)==null);
		todoly.deleteTask(index);

	}
	/**
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
	/**
	 * check the date which is entered by the user 
	 * @return date : A valid Due date of the task 
	 */
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
	/**
	 * displays all projects and check the user input to validate if the project index is existed
	 * @return String the project name
	 */
	
	static String userProjecInput(){
		do{
			todoly.showListOfProjects();
			System.out.print("Task New Project :");
			int projectIndex = input.nextInt();
			project= todoly.getProject(projectIndex);
		} while (project==null);
		return project;
	}

}


