import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



/**
 * 
 */

/**
 * @author Dima Alissa
 *
 */
public class ListManager {
	
	private ArrayList <Task> listOfTasks;
	
	public ListManager(String filename) {
		this.listOfTasks = new ArrayList<Task>();
		fillTasks(filename);
	}
	 /**
     * To Fill the list of tasks with the stored task in the CSV file.
     * @param filename the path of the file.
     */
	public void fillTasks (String filename) {
		LoadTasks loader = new LoadTasks();
		listOfTasks.addAll(loader.readStoredTasks(filename));
	}
	 /**
     * To create a new task .
     */
	public void createNewTask(String project,String name,String description,Date dueDate,String status) {
		listOfTasks.add(new Task(project,name,description,dueDate,status));
	}
	
	public void modifyTask(int index, String description) {
			Task selectedTask =getTask(index);
			selectedTask.setDescription(description);
			System.out.println(selectedTask.getDescription());
		} 		
	public void showAllTask() {
		for (Task task : listOfTasks) {
			System.out.println("Task No."+listOfTasks.indexOf(task)+task.getDetails());
		}
	}
	 /**
     * To display all tasks that are due to specific  date   .
     */
	public void showTaskByDate(Date dueDate) {
		listOfTasks.stream()
        .filter(task -> task.getDueDate().equals(dueDate))
        .map(task -> task.getDetails())
        .forEach(details -> System.out.println(details)); 
	}	
	public void showTaskByProject(String selectedProject) {
		listOfTasks.stream()
        .filter(task -> task.getProject().equals(selectedProject))
        .map(task -> task.getDetails())
        .forEach(details -> System.out.println(details)); 
	}	
	public Task getTask(int index) {
		if (checkIndex(index)) {
			Task returnedTask= listOfTasks.get(index);
			return returnedTask;
		}else {
			System.out.println("unkonwun Task");
			return null;
		}
	}
	boolean checkIndex (int index) {
		if(index>=0 && index<listOfTasks.size()){
			return true;
		}else {
				return false;
		}
	}

	public void saveToCSV(String selectedpath) {
		try {
			Writer writer = null;
			for (Task task :listOfTasks) {
				writer.append(task.toCSVformat());	
			}
			writer = new OutputStreamWriter(new FileOutputStream(selectedpath), StandardCharsets.UTF_8);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	}
