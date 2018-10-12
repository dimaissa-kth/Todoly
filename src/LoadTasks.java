import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
/**
 * A class that represent a task 
 */

/**
 * @author Dima Alissa
 */
public class LoadTasks {
	private Task task;
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy");
    //Number of fields of a task record in the CSV file
    private static final int NUMBER_OF_FIELDS = 5;
    // Index values for the fields in each record.
    private static final int PROJECT = 0,
                             NAME = 1,
                             DESCRIPTION = 2,
                             DUEDATE = 3,
                             STATUS = 4;
    public LoadTasks() {
    	
    }
	 /**
     * To Read the tasks from the CSV file as Lines(streams) .
     * @param filename The file path .
     * @return ArrayList of task that are stored in the CSV file.
     */
    public ArrayList<Task> readStoredTasks(String filename){
   	 /**
	  * To create a task Object from each line in the CSV file.
	  */
   	 df.setLenient(false);
		Function <String,Task> readTask = 
				record -> {
					String [] parts = record.split(";") ;
					if (parts.length==NUMBER_OF_FIELDS) {
						try {
							String project = parts[PROJECT].trim();
							String name = parts[NAME].trim();
							String description = parts[DESCRIPTION].trim();
							Date dueDate = 	df.parse(parts[DUEDATE].trim());
							String status = parts[STATUS].trim();
							return new Task(project,name,description,dueDate,status);
							}
						 catch (ParseException e)  {
				               System.out.println("problem reading task record" + record);
				               return null;} 
					}else {
				            System.out.println("Tasks details have different fields  " + record);
				            return null;
				    	}	

				};
		ArrayList<Task> ListOfTasks;
		try {
			ListOfTasks=Files.newBufferedReader(Paths.get(filename), Charset.forName("UTF-8")).lines()
						.filter(record -> record.length() > 0 )
						.map(readTask)
						.collect(Collectors.toCollection(ArrayList::new));
		}
		catch (IOException e){
			System.out.println("Unable to open " + filename);
            ListOfTasks = new ArrayList<>();
		}
		return ListOfTasks;
    }

}				