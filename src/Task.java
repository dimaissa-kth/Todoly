import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that represent a task
 *
 */

/**
 * @author Dima Alissa
 */
public class Task {
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private String project =null;
	private String name =null;
	private	String description=null;
	private Date dueDate=null;
	private String status= null;

	  /**
     * Create a Task with its corresponding fields.
     * @param project The project the task belongs to.
     * @param name title of the task.
     * @param description of the task.
     * @param dueDate The due date of the task .
     * @param status The state of the task wether its done or in progress. 
     * 
     */
	  public Task(){

	  }
	public Task(String project,String name,String description,Date dueDate,String status) {
	  	df.setLenient(false);
		this.name=name;
		this.dueDate= dueDate;
		this.description=description;
		this.project =project;
		this.status=status;
	}
	
	public void setProject(String project) {
		this.project = project;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}
	public String getProject() {
		return project;
	}
	public String getDescription() {
		return description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public String getStatus() {
		return status;
	}
	public String getDetails() {
		return "  PROJECT: "+project+" -- "+"NAME: "+name+" -- "+"DESCRIPTION: "+description+" -- "+"Due Date: "+df.format(dueDate)+" -- "+"Status: "+status+"\n";
	
		
	}
	public String toCSVformat() {
		return project+";"+name+";"+description+";"+df.format(dueDate)+";"+status+"\n";
		
	}
	public boolean equals(Task t) {
	  	return this.getName().equals(t.getName()) &&
				this.getDescription().equals(t.getDescription())&&
				this.getStatus().equals(t.getStatus()) &&
				this.getProject().equals(t.getProject()) &&
				this.getDueDate().equals(t.getDueDate());
	}
	

}
