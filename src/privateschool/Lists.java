package privateschool;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lists {

    private static ArrayList<Student> studentsList;
    private static ArrayList<Trainer> trainersList;
    private static ArrayList<Assignment> assignmentsList;
    private static ArrayList<Course> coursesList;

    public static void addStudents(Student student) {
        if (studentsList == null) {
            studentsList = new ArrayList();
        }
        studentsList.add(student);
    }

    public static ArrayList<Student> getStudentList() {
        return studentsList;
    }

    public static void addTrainers(Trainer trainer) {
        if (trainersList == null) {
            trainersList = new ArrayList();
        }
        trainersList.add(trainer);
    }

    public static ArrayList<Trainer> getTrainerList() {
        return trainersList;
    }

    public static ArrayList<Assignment> getAssignments() {
        return assignmentsList;
    }

    public static void addAssignments(Assignment assignment) {
        if (assignmentsList == null) {
            assignmentsList = new ArrayList();
        }
        assignmentsList.add(assignment);
    }

    public static void addCourses(Course course) {
        if (coursesList == null) {
            coursesList = new ArrayList();
        }
        coursesList.add(course);
    }

    public static ArrayList<Course> getCourses() {
        return coursesList;
    }

    public static void printList(String[] list) {
        for (int i = 0; i < list.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), list[i]);
        }
    }

    public static ArrayList<Student> getSyntheticStudentsList() {

        Student s1 = new Student("Evangelos", "Tsivas", LocalDate.of(1985, 7, 23), 2500);
        Student s2 = new Student("Petros", "Grivas", LocalDate.of(1989, 7, 8), 2200);
        Student s3 = new Student("Thomas", "Petropoulos", LocalDate.of(1993, 12, 12), 1850);
        Student s4 = new Student("Maria", "Ioannidou", LocalDate.of(1987, 6, 22), 1900);
        Student s5 = new Student("Evi", "Kelesidou", LocalDate.of(1990, 9, 24), 1650);
        Student s6 = new Student("Eleni", "Kanakidou", LocalDate.of(1986, 11, 1), 3500);
        Student s7 = new Student("Markos", "Saridis", LocalDate.of(1995, 11, 11), 3500);
        Student s8 = new Student("Mixalis", "Mpekas", LocalDate.of(1988, 4, 13), 2500);
        Student s9 = new Student("Evaggelia", "Tasiou", LocalDate.of(1989, 12, 1), 1500);
        Student s10 = new Student("Mairi", "Nkolaidou", LocalDate.of(1991, 7, 1), 1850);
        
        addStudents(s1);
        addStudents(s2);
        addStudents(s3);
        addStudents(s4);
        addStudents(s5);
        addStudents(s6);
        addStudents(s7);
        addStudents(s8);
        addStudents(s9);
        addStudents(s10);
        
        return studentsList;
    }

    public static ArrayList<Trainer> getSyntheticTrainersList() {

        Trainer t1 = new Trainer("Anastasios", "Trekos", "Java");
        Trainer t2 = new Trainer("Xaralampos", "Ntorvas", "C#");
        Trainer t3 = new Trainer("Kleomenis", "Mpismpis", "HTML");
        Trainer t4 = new Trainer("Anna", "Mixailidou", "Javascript");
        Trainer t5 = new Trainer("Maria", "Silka", "SQL");
        Trainer t6 = new Trainer("Athanasios", "Ioannidis", "Java");
        Trainer t7 = new Trainer("Kaiti", "Garmpi", "Web Development");
        Trainer t8 = new Trainer("Anastasia", "Glykou", "C++");
        Trainer t9 = new Trainer("Xaralampos", "Paxatouridis", "Python");
        Trainer t10 = new Trainer("Panagiota", "Tsampa", "C#");

        addTrainers(t1);
        addTrainers(t2);
        addTrainers(t3);
        addTrainers(t4);
        addTrainers(t5);
        addTrainers(t6);
        addTrainers(t7);
        addTrainers(t8);
        addTrainers(t9);
        addTrainers(t10);
       
        return trainersList;
    }

    public static ArrayList<Course> getSyntheticCoursesList() {

        Course c1 = new Course("CB8", "Java", "Full-Time", LocalDate.of(2020, 4, 10), LocalDate.of(2020, 7, 10));
        Course c2 = new Course("CB9", "Java", "Part-Time", LocalDate.of(2020, 4, 8), LocalDate.of(2020, 9, 10));
        Course c3 = new Course("CB10", "C#", "Full-Time", LocalDate.of(2020, 5, 8), LocalDate.of(2020, 7, 8));
        Course c4 = new Course("CB11", "C#", "Part-Time", LocalDate.of(2020, 6, 5), LocalDate.of(2020, 12, 4));
        Course c5 = new Course("CB12", "JavaScript", "Full-Time", LocalDate.of(2020, 5, 3), LocalDate.of(2020, 7, 9));
        Course c6 = new Course("CB13", "JavaScript", "Part-Time", LocalDate.of(2020, 4, 10), LocalDate.of(2020, 10, 10));

        addCourses(c1);
        addCourses(c2);
        addCourses(c3);
        addCourses(c4);
        addCourses(c5);
        addCourses(c6);
        
        return coursesList;
    }

    public static ArrayList<Assignment> getSyntheticAssignmentsList() {

        Assignment a1 = new Assignment("Project1", "Individual", LocalDate.of(2020, 6, 24), 80, 90);
        Assignment a2 = new Assignment("Project2", "Individual", LocalDate.of(2020, 7, 6), 70, 80);
        Assignment a3 = new Assignment("Assignment 1", "Individual", LocalDate.of(2020, 5, 20), 60, 90);
        Assignment a4 = new Assignment("Assignment 2", "Individual", LocalDate.of(2020, 9, 7), 80, 90);
        Assignment a5 = new Assignment("Assignment 3", "Group", LocalDate.of(2020, 6, 15), 60, 75);
        Assignment a6 = new Assignment("Assignment 4", "Group", LocalDate.of(2020, 9, 11), 55, 65);
        
        addAssignments(a1);
        addAssignments(a2);
        addAssignments(a3);
        addAssignments(a4);
        addAssignments(a5);
        addAssignments(a6);

        return assignmentsList;
    }
    
     public static void getSyntheticData() {
        //clear lists 
        if (Lists.getStudentList() != null) {
            Lists.getStudentList().clear();
        }
        if (Lists.getTrainerList() != null) {
            Lists.getTrainerList().clear();
        }
        if (Lists.getCourses() != null) {
            Lists.getCourses().clear();
        }
        if (Lists.getAssignments() != null) {
            Lists.getAssignments().clear();
        }
        // add synthetic data to lists
        Lists.getSyntheticStudentsList();
        Lists.getSyntheticTrainersList();
        Lists.getSyntheticCoursesList();
        Lists.getSyntheticAssignmentsList();

    }
}
