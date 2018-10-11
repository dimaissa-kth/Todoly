import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListManagerTest2 {

    private ListManager lmNoTasks;
    private ListManager lmOneTask;
    private ListManager lmThreeTasks;
    private SimpleDateFormat sf;

    @BeforeEach
    void setUp() {
        sf = new SimpleDateFormat("dd/MM/yyyy");
        lmNoTasks = new ListManager(Program.filename);
        lmOneTask = new ListManager(Program.filename);
        lmThreeTasks = new ListManager(Program.filename);
        String name = "taskname1";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void fillTasks() {
    }

    @Test
    void addTaskToProjectOneTask() throws ParseException {
        addOneTaskWithSpecification();
    }

    private void addOneTaskWithSpecification() throws ParseException {
        lmNoTasks.loadListOfProjects();
        lmNoTasks.addTaskToProject(1,"task1","desc1",sf.parse("12/10/2019"),"Done");
        Task t1 = lmNoTasks.getTask(6);
        assertEquals("SDN", t1.getProject());
        assertEquals("Done",t1.getStatus());
        assertEquals(sf.parse("12/10/2019"),t1.getDueDate());
        assertEquals("desc1",t1.getDescription());
    }

    @Test
    void addTaskToProjectThreeTasks() throws ParseException {
       addOneTaskWithSpecification();
        lmNoTasks.addTaskToProject(2,"task2","desc2",sf.parse("12/11/2019"),"In progress");
        Task t2 = lmNoTasks.getTask(7);
        assertEquals("TEAMUP", t2.getProject());
        assertEquals("In progress",t2.getStatus());
        assertEquals(sf.parse("12/11/2019"),t2.getDueDate());
        assertEquals("desc2",t2.getDescription());

        lmNoTasks.addTaskToProject(1,"task1","desc1",sf.parse("12/10/2019"),"Suspend");
        Task t3 = lmNoTasks.getTask(8);
        assertEquals("SDN", t3.getProject());
        assertEquals("Suspend",t3.getStatus());
        assertEquals(sf.parse("12/10/2019"),t3.getDueDate());
        assertEquals("desc1",t3.getDescription());
    }

    @Test
    void addTaskToProjectTwoWrongTasks() throws ParseException {
        // undone is wrong
        lmNoTasks.addTaskToProject(1,"task1","desc1",sf.parse("12/10/2019"),"undone");
        Task t1 = lmNoTasks.getTask(6);
        assertNull(t1);

        lmNoTasks.addTaskToProject(1,"task2","desc2",null,"Done");
        Task t2 = lmNoTasks.getTask(7);
        assertNull(t2);
    }


    @Test
    void modifyTask() {
    }

    @Test
    void showTaskByDate() {
    }

    @Test
    void showTaskByProject() {
    }

    @Test
    void getTask() {
    }

    @Test
    void checkTaskIndex() {
    }

    @Test
    void saveToCSV() {
    }

    @Test
    void checkProjectIndex() {
    }

    @Test
    void getProject() {
    }

    @Test
    void loadListOfProjects() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void showAllTasksSorted() {
    }

    @Test
    void showTaskByStatus() {
    }

    @Test
    void statusOptions() {
    }

    @Test
    void checkStatusOptions() {
    }

    @Test
    void showAllTask() {
    }

    @Test
    void addTaskToProjectTesting() {
    }
}