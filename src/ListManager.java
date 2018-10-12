import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Dima Alissa
 *The List Manger is a class that have all the methods that handle opreations on Tasks
 *
 */
public class ListManager {
	private ArrayList<Task> listOfTasks;
	private ArrayList<String> listOfProjects;
	public ListManager(String filename) {
		this.listOfTasks = new ArrayList<>();
		fillTasks(filename);
		listOfProjects = listOfTasks.stream()
				.map(task -> task.getProject().trim()).distinct()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * To Fill the list of tasks with the stored task in the CSV file.
	 * @param filename the path of the file.
	 */
	 void fillTasks(String filename) {
		LoadTasks loader = new LoadTasks();
		listOfTasks= loader.readStoredTasks(filename);
	}

	/**
	 * To create a new task  .
	 * @param  name :task name
	 * @param  dueDate: task due to date
	 * @param status: status of string
	 */

	public void addTaskToProject(int projectIndex,String name,String description,Date dueDate,String status) {
		String selectedProject = getProject(projectIndex).toUpperCase();
		if (selectedProject != null && name != null && dueDate !=null && statusOptions().values().contains(status)) {
			listOfTasks.add(new Task(selectedProject, name, description, dueDate, status));
			System.out.println("task added successfully");
			System.out.println(getTask(listOfTasks.size()-1).getDetails());
		}else {
			System.out.println("Task information are empty :Name -Due date are required ");
		}
	}
    /**
     * To Modify a new task  .
     * @param  name :task name
     * @param  dueDate: task due to date
     * @param status: status of string
     */
	public void modifyTask(int index,String project,String name,String description,Date dueDate,String status) {

		if (project!= null&& name !=null&& dueDate !=null && status !=null ){
			Task selectedTask = getTask(index);
			selectedTask.setProject(project);
			selectedTask.setName(name);
			selectedTask.setDescription(description);
			selectedTask.setDueDate(dueDate);
			selectedTask.setStatus(status);
			System.out.println("Task Has Been Modified Successfully");
			System.out.println(selectedTask.getDetails());
		}else {
			System.out.println("Task can not have empty information");
		}
	}
	/**
	 * To display all tasks that are due to specific  date  .
	 */
	public void showTaskByDate(Date inputDate) {
		int y=0;// a variable to check if there is no result is found
		for (Task task :listOfTasks) {
			if (task.getDueDate().equals(inputDate)) {
				System.out.println(task.getDetails());
				y++;
			}
		}
		if (y==0){System.out.println("There is no task on this date");}
		}

	/**
	 *
	 *Displays the tasks based on entered project 
	 * @param selectedProject : entered project by user
	 */
	public void showTaskByProject(String selectedProject) {
		listOfTasks.stream()
				.filter(task -> task.getProject().equalsIgnoreCase(selectedProject))
				.map(task -> task.getDetails())
				.forEach(details -> System.out.println(details));
	}
	/**
	 * To get a task based on its index
	 * @param index of the task
	 * @return Task
	 */

	public Task getTask(int index) {
		if (checkTaskIndex(index)) {
			Task returnedTask = listOfTasks.get(index);
			return returnedTask;
		} else {
			System.out.println("Unkonwun Task");
			return null;
		}
	}
	/**
	 * 
	 * @param index of the task in the list of tasks
	 * @return true if index is existed , false if it is not existed 
	 */

	boolean checkTaskIndex(int index) {
		if (index >= 0 && index < listOfTasks.size()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * To write the list of tasks to the CSV file , located in the selected path  
	 * @param selectedpath , the location of the created file 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void saveToCSV(String selectedpath) throws FileNotFoundException, UnsupportedEncodingException {
	    File selectedPath = new File (selectedpath);
        try {
            selectedPath.createNewFile();
        } catch (IOException e) {
            System.out.println("Can not Create File");
        }
        Writer bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(selectedpath,false), StandardCharsets.UTF_8));
		try {

			for (Task task : listOfTasks) {
				bw.write(task.toCSVformat());
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Check if the project index is valid
	 * @param index : project index in the project list
	 * @return true if index is valid , false if it is not existed  
	 */
	boolean checkProjectIndex(int index) {
		if (index >= 0 && index < listOfProjects.size()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * get the project name
	 * @param index of the project
	 * @return the name of the project
	 */
	public String getProject(int index) {
		if (checkProjectIndex(index)) {
			String selectedProject = listOfProjects.get(index);
			return selectedProject;
		} else {
			System.out.println("Project does Not exist ");
			return null;
		}
	}
	/**
	 * Display list of projects
	 */
	public void showListOfProjects(){
		for (String project : listOfProjects) {
			System.out.println("No." + listOfProjects.indexOf(project)+" :"+project);
		}
	}
	/**
	 * Delete a task from the list of tasks
	 * @param index of the task entered by the user
	 */
	public void deleteTask(int index){
		System.out.println("This task will be deleted \n "+getTask(index).getDetails());
		listOfTasks.remove(index);
	}
	/**
	 * show tasks that are sorted by date ascending and categorized by Status
	 */
	public void showAllTasksSorted() {
		// to sort the task according to their dates
		listOfTasks.sort(Comparator.comparing(Task::getDueDate));
		// To Display all tasks that have status DONE
		System.out.println("DONE TASKS ARE :\n");
		listOfTasks.stream()
				.filter(task -> task.getStatus().equalsIgnoreCase("Done"))
				.map(task -> task.getDetails())
				.forEach(details -> System.out.println( details));
		// To Display all tasks that have status In Progress
        System.out.println("IN PROGRESS TASKS ARE :\n");
        listOfTasks.stream()
				.filter(task -> task.getStatus().equalsIgnoreCase("IN PROGRESS"))
				.map(task -> task.getDetails())
				.forEach(details -> System.out.println(details));
        System.out.println("Suspend TASKS ARE :\n");
        listOfTasks.stream()
				.filter(task -> task.getStatus().equalsIgnoreCase("Suspend"))
				.map(task -> task.getDetails())
				.forEach(details -> System.out.println(details));
			}
	/**
	 * Displays the tasks based on selected status
	 * @param statusInput 
	 */
	public void showTaskByStatus(String statusInput){
        listOfTasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(statusInput))
                .map(task -> task.getDetails())
                .forEach(details -> System.out.println(details));
    }
	/**
	 * Stored status with their numbers
	 * @return HaskMap of status options 
	 */
    public HashMap <Integer,String> statusOptions() {
        HashMap<Integer,String> statusOptions = new HashMap<Integer,String>();
        statusOptions.put(1,"DONE");
        statusOptions.put(2,"IN PROGRESS");
        statusOptions.put(3,"SUSPEND");

        return statusOptions;
    }
	/**
	 * check if the entered int is existed in the HashMap of the status options
	 * @param  statusIndex :int that represent the index of the status in the HashMap of status options
	 */
    public String checkStatusOptions (int statusIndex){
        if (statusOptions().containsKey(statusIndex)){
            return statusOptions().get(statusIndex);
        }
        return null;
    }

	/**
	 * Display all tasks with their indexes in the listOfTasks array
	 */
    public void showAllTask() {
		for (Task task : listOfTasks) {
            System.out.println("No."+listOfTasks.indexOf(task)+" : "+task.getDetails());
        }
    }


	/**
	 * TO add a new project , check if the project is already there
	 * @param newProjectName : the name of the project
	 */
    public void addNewProject(String newProjectName){
    	for (String p : listOfProjects){
			if (p.equalsIgnoreCase(newProjectName)){
				System.out.println("Project already exists");
				return;
			}
    	}
		listOfProjects.add(newProjectName.toUpperCase());
		System.out.println("Project has been added successfully");

    }
    /**
     * To display the count of  each tasks grouped by their status   
     */
    public void showTasksCount (){
		long  count_of_done_tasks = listOfTasks.stream().filter(task -> task.getStatus().equalsIgnoreCase("DONE")).count();
		System.out.println("YOU HAVE <<"+count_of_done_tasks+">> DONE TASKS");
		long count_of_inProgress_tasks =listOfTasks.stream().filter(task -> task.getStatus().equalsIgnoreCase("IN PROGRESS")).count();
		System.out.println("YOU HAVE <<"+count_of_done_tasks+">> IN PROGRESS TASKS");
		long count_of_suspend_tasks=listOfTasks.stream().filter(task -> task.getStatus().equalsIgnoreCase("SUSPEND")).count();
		System.out.println("YOU HAVE <<"+count_of_done_tasks+">> SUSPEND TASKS");
	}
    /**
     * To check if the project list is empty ,
     * @return true if there is no project in the list , false there is at least one project 
     */
	public boolean isListOfProjectsIsempty(){
    	if (listOfProjects.size()==0){
    		return true;
		}else {
			return false;
		}

	}

}
