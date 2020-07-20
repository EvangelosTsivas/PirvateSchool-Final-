package privateschool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClass {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Database db = new Database();
        //cleaning database
        db.cleanDatabase();

        System.out.println("              ****** Welcome. Let's create a private school! ******");
        while (true) {
            System.out.println("\nWhat would you like to do?");
            String[] list = {"Enter students.", "Enter trainers.", "Enter courses.", "Enter assignments.",
                "Exit with synthetic data (any inputs from user will be lost)", "Exit with the entities you entered."};
            Lists.printList(list);
            int choice = enterChoice();
            if (choice == 1) {
                addStudents();
            } else if (choice == 2) {
                addTrainers();
            } else if (choice == 3) {
                addCourses();
            } else if (choice == 4) {
                addAssignments();
            } else if (choice == 5) {
                //populates database with synthetic data
                db.populateDatabase();
                Lists.getSyntheticData();
                break;
            } else if (choice == 6) {
                //exit choices with user's inputs only

                //inserting students to database
                int records = insertRecordsToStudents(db);
                System.out.println("Entered " + records + " record(s) to Students table.");
                //inserting trainers to database
                records = insertRecordsToTrainers(db);
                System.out.println("Entered " + records + " record(s) to Trainers table.");
                //inserting assignments to database
                records = insertRecordsToAssignments(db);
                System.out.println("Entered " + records + " record(s) to Assignments table.");
                //inserting courses to database
                records = insertRecordsToCourses(db);
                System.out.println("Entered " + records + " record(s) to Courses table.");
                break;
            }
        }

        //adding students to courses and inserting them to database 
        addStudentsToCourses();
        int records = insertRecordsToStudentsPerCourse(db);
        System.out.println("Entered " + records + " record(s) to CoursesStudents table.");
        //adding trainers to courses and inserting them to database 
        addTrainersToCourses();
        records = insertRecordsToTrainersPerCourse(db);
        System.out.println("Entered " + records + " record(s) to CoursesTrainers table.");
        //adding assignments to courses and inserting them to database 
        addAssignmentsToCourses();
        records = insertRecordsToAssignmentsPerCourse(db);
        System.out.println("Entered " + records + " record(s) to CoursesAssignments table.");
        //giving user the option to print what he wants from database
        printOptions(db);

        //closing scanner
        input.close();

        // closing connection
        try {
            Database.getStatement().close();
            Database.getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int enterChoice() {
        //check input to be in range (1-6)
        int choice;
        do {
            System.out.print("\nEnter the corresponting number (1-6): ");
            while (!input.hasNextInt()) {
                System.out.print("\nPlease enter a valid number: ");
                input.next();
            }
            choice = input.nextInt();
        } while (choice < 1 || choice > 6);
        return choice;
    }

    public static void addStudents() {
        //adding student entity to list
        System.out.println("Please add students. You can enter more than one at a time.");
        while (true) {
            System.out.print("Enter First name of student or type \"next\" to add another entity: ");
            String firstName = input.next();
            if (firstName.equals("next")) {
                break;
            }
            System.out.print("Enter Last name of student: ");
            String lastName = input.next();
            //checking date and fees 
            LocalDate dateOfBirth = getDate("Enter date of birthday (dd/MM/yyyy): ");
            int fees = getFees();
            //adding student to list
            Student student = new Student(firstName, lastName, dateOfBirth, fees);
            //checking for duplicates then adding to list
            if (Lists.getStudentList() == null) {
                Lists.addStudents(student);
                System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " added !");
            } else if (containsStudent(student)) {
                System.out.println("You have entered this student. Choose another one!\n");
            } else {
                Lists.addStudents(student);
                System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " added !");
            }

        }
    }

    public static boolean containsStudent(Student student) {
        for (Student s : Lists.getStudentList()) {
            if (student.getFirstName().equals(s.getFirstName()) && student.getLastName().equals(s.getLastName()) && student.getDateOfBirth().equals(s.getDateOfBirth())) {
                return true;
            }
        }
        return false;
    }

    public static int getFees() {
        //checking fees input to be >0  and number
        int fees;
        do {
            System.out.print("Enter tuition fees: ");
            while (!input.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                input.next();
            }
            fees = input.nextInt();
        } while (fees < 0);
        return fees;
    }

    public static LocalDate getDate(String message) {
        //checking date format to be dd/MM/yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print(message);
        LocalDate date;
        while (true) {
            String inputDate = input.next();
            try {
                date = LocalDate.parse(inputDate, formatter);
                break;
            } catch (DateTimeParseException dtpe) {
                System.out.print("Please enter date in correct format (dd/MM/yyyy): ");
            }
        }
        return date;
    }

    public static void addTrainers() {
        //adding trainer entity to list
        System.out.println("Please add trainers. You can enter more than one at a time.");
        while (true) {
            System.out.print("Enter First name of trainer or type \"next\" to add another entity: ");
            String firstName = input.next();
            if (firstName.equals("next")) {
                break;
            }
            System.out.print("Enter Last name of trainer: ");
            String lastName = input.next();
            System.out.print("Please enter the trainer's subject: ");
            String subject = input.next();
            //checking for duplicates then adding to list
            Trainer trainer = new Trainer(firstName, lastName, subject);

            if (Lists.getTrainerList() == null || !Lists.getTrainerList().contains(trainer)) {
                Lists.addTrainers(trainer);
                System.out.println("Trainer " + trainer.getFirstName() + " " + trainer.getLastName() + " added !");
            } else {
                System.out.println("You have entered this trainer. Choose another one!\n");
            }

        }
    }

    public static void addCourses() {
        //adding course entity to list
        System.out.println("Please add courses. You can enter more than one at a time.");
        while (true) {
            System.out.print("Enter title of course or type \"next\" to add another entity: ");
            String title = input.next();
            if (title.equals("next")) {

                break;
            }
            System.out.print("Enter courses's stream: ");
            String stream = input.next();
            System.out.print("Enter courses's type: ");
            String type = input.next();
            //check dates 
            LocalDate startDate = getDate("Enter starting date (dd/MM/yyyy): ");
            LocalDate endDate = getDate("Enter ending date (dd/MM/yyyy): ");
            //adding course to list
            Course course = new Course(title, stream, type, startDate, endDate);
            //checking for duplicates then adding to list
            if (Lists.getCourses() == null) {
                Lists.addCourses(course);
                System.out.println("Course " + course.getTitle() + " " + course.getStream() + " added !");
            } else if (containsCourse(course)) {
                System.out.println("You have entered this course. Choose another one!\n");
            } else {
                Lists.addCourses(course);
                System.out.println("Course " + course.getTitle() + " " + course.getStream() + " added !");
            }

        }
    }

    public static boolean containsCourse(Course course) {
        for (Course c : Lists.getCourses()) {
            if (course.getTitle().equals(c.getTitle()) && course.getStream().equals(c.getStream()) && course.getType().equals(c.getType())) {
                return true;
            }
        }
        return false;
    }

    public static void addAssignments() {
        //adding assignment entity to list
        System.out.println("Please add assignments. You can enter more than one at a time.");
        while (true) {
            System.out.print("Enter title of assignment or type \"next\" to enter another entity: ");
            String title = input.next();
            if (title.equals("next")) {
                break;
            }
            System.out.print("Enter assignment's description: ");
            String description = input.next();
            //check date and mark
            LocalDate subDate = getDate("Enter submission date (dd/MM/yyyy): ");
            int oralMark = getMark("oral");
            int totalMark = getMark("total");
            //adding assignment to list
            Assignment assignment = new Assignment(title, description, subDate, oralMark, totalMark);
            //checking for duplicates then adding to list
            if (Lists.getAssignments() == null) {
                Lists.addAssignments(assignment);
                System.out.println("Assignment " + assignment.getTitle() + " added !");
            } else if (containsAssignment(assignment)) {
                System.out.println("You have entered this assignment. Choose another one!\n");
            } else {
                Lists.addAssignments(assignment);
                System.out.println("Assignment " + assignment.getTitle() + " added !");
            }
        }
    }

    public static boolean containsAssignment(Assignment assignment) {
        for (Assignment a : Lists.getAssignments()) {
            if (assignment.getTitle().equals(a.getTitle()) && assignment.getDescription().equals(a.getDescription()) && assignment.getSubDateTime().equals(a.getSubDateTime())) {
                return true;
            }
        }
        return false;
    }

    public static int getMark(String name) {
        //checking  mark to be a number and between 0-100 range
        int mark;
        do {
            System.out.print("Enter " + name + " mark: ");
            while (!input.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                input.next();
            }
            mark = input.nextInt();
        } while (mark < 0 || mark > 100);
        return mark;
    }

    public static void addStudentsToCourses() {
        System.out.println("\n              ******** Add students to courses ********");
        if (Lists.getCourses() != null && Lists.getStudentList() != null) {
            Course c;
            while (true) {
                System.out.println("\nPlease select a course to add students to. \n");
                int i;
                for (i = 0; i < Lists.getCourses().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
                }

                int choice1;
                do {
                    System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a valid number: ");
                        input.next();
                    }
                    choice1 = input.nextInt();
                } while (choice1 < 0 || choice1 > Lists.getCourses().size());
                if (choice1 == 0) {
                    break;
                }

                c = Lists.getCourses().get(choice1 - 1);

                System.out.println("\nNow choose which students are in " + c.getTitle() + " course.");
                System.out.println("You can choose more than once .\n");
                for (i = 0; i < Lists.getStudentList().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getStudentList().get(i));
                }
                while (true) {

                    int choice2;
                    do {

                        System.out.print("\nChoose a student to add to " + c.getTitle() + " or type 0 to return to course selection: ");
                        while (!input.hasNextInt()) {
                            System.out.print("Please enter a valid number: ");
                            input.next();
                        }
                        choice2 = input.nextInt();
                    } while (choice2 < 0 || choice2 > Lists.getStudentList().size());
                    if (choice2 == 0) {
                        break;
                    }
                    Student s = Lists.getStudentList().get(choice2 - 1);

                    if (c.getStudentPerCourse() == null || !c.getStudentPerCourse().contains(s)) {
                        c.addStudentsPerCourse(s);
                        System.out.println("Student " + s.getFirstName() + " " + s.getLastName() + " added to " + c.getTitle() + "!");
                    } else {
                        System.out.println("You have entered this student. Choose another one!\n");
                    }
                }
            }
        }
    }

    public static void addTrainersToCourses() {

        System.out.println("\n              ******** Add trainers to courses ********");
        if (Lists.getCourses() != null && Lists.getTrainerList() != null) {
            Course c;
            while (true) {
                System.out.println("\nPlease select a course to add trainers to. \n");

                int i;
                for (i = 0; i < Lists.getCourses().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
                }

                int choice1;
                do {

                    System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a valid number: ");
                        input.next();
                    }
                    choice1 = input.nextInt();
                } while (choice1 < 0 || choice1 > Lists.getCourses().size());
                if (choice1 == 0) {
                    break;
                }

                c = Lists.getCourses().get(choice1 - 1);

                System.out.println("\nNow choose which trainers are in " + c.getTitle() + " course.");
                System.out.println("You can choose more than once .\n");

                for (i = 0; i < Lists.getTrainerList().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getTrainerList().get(i));
                }
                while (true) {

                    int choice2;
                    do {
                        System.out.println();
                        System.out.print("\nChoose a trainer to add to " + c.getTitle() + " or type 0 to return to course selection: ");
                        while (!input.hasNextInt()) {
                            System.out.print("Please enter a valid number: ");
                            input.next();
                        }
                        choice2 = input.nextInt();
                    } while (choice2 < 0 || choice2 > Lists.getTrainerList().size());
                    if (choice2 == 0) {
                        break;
                    }
                    Trainer t = Lists.getTrainerList().get(choice2 - 1);

                    if (c.getTrainersPerCourse() == null || !c.getTrainersPerCourse().contains(t)) {
                        c.addTrainersPerCourse(t);
                        System.out.println("Trainer " + t.getFirstName() + " " + t.getLastName() + " added to " + c.getTitle() + "!");
                    } else {
                        System.out.println("You have entered this trainer in this course. Choose another one!\n");
                    }
                }
            }
        }
    }

    public static void addAssignmentsToCourses() {

        System.out.println("\n              ******** Add assignments to courses ********");
        if (Lists.getCourses() != null && Lists.getAssignments() != null) {
            Course c;
            while (true) {
                System.out.println("\nPlease select a course to add assignments to. \n");
                int i;
                for (i = 0; i < Lists.getCourses().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
                }

                int choice1;
                do {
                    System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a valid number: ");
                        input.next();
                    }
                    choice1 = input.nextInt();
                } while (choice1 < 0 || choice1 > Lists.getCourses().size());
                if (choice1 == 0) {
                    break;
                }

                c = Lists.getCourses().get(choice1 - 1);

                System.out.println("\nNow choose which assignments are in " + c.getTitle() + " course.\n");
                System.out.println("You can choose more than once .");
                for (i = 0; i < Lists.getAssignments().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getAssignments().get(i));
                }
                while (true) {

                    int choice2;
                    do {
                        System.out.println();
                        System.out.print("\nChoose an assignment to add to " + c.getTitle() + " or type 0 to return to course selection: ");
                        while (!input.hasNextInt()) {
                            System.out.print("Please enter a valid number: ");
                            input.next();
                        }
                        choice2 = input.nextInt();
                    } while (choice2 < 0 || choice2 > Lists.getAssignments().size());
                    if (choice2 == 0) {
                        break;
                    }
                    Assignment a = Lists.getAssignments().get(choice2 - 1);

                    if (c.getAssignmentsPerCourse() == null || !c.getAssignmentsPerCourse().contains(a)) {
                        c.addAssignmentsPerCourse(a);
                        System.out.println("Assignment " + a.getTitle() + " added to " + c.getTitle() + "!");
                    } else {
                        System.out.println("You have entered this assignment in this course. Choose another one!\n");
                    }
                }
            }
        }
    }

    public static void printOptions(Database db) {
        System.out.println("\n              ***** Congratulations! You created a private school! *****");
        OUTER:
        while (true) {
            System.out.println("\nWhat would you like to do?");
            String[] list = {"Print all the students.", "Print all the trainers.", "Print all the assignments.", "Print all courses.",
                "Print all students per course.", "Print all trainers per course.", "Print all the assignments per course.",
                "Print all the assignments per course per student.", "Print all students that belong to more than one course.", "Exit printing."};
            Lists.printList(list);
            int choice = enterOption();
            switch (choice) {
                case 1:
                    String query = "SELECT `first_name`,`last_name`,`date_of_birth`,`tuition_fees` FROM `Students`;";
                    ResultSet rs = db.getResults(query);
                    printStudentResults(rs);
                    break;
                case 2:
                    query = "SELECT `first_name`,`last_name`,`subject` FROM `Trainers`;";
                    rs = db.getResults(query);
                    printTrainerResults(rs);
                    break;
                case 3:
                    query = "SELECT `title`,`description`,`sub_date_time`,`oral_mark`,`total_mark`FROM `Assignments`;";
                    rs = db.getResults(query);
                    printAssignmentResults(rs);
                    break;
                case 4:
                    query = "SELECT `title`,`stream`,`type`,`start_date`,`end_date`FROM `Courses`;";
                    rs = db.getResults(query);
                    printCourseResults(rs);
                    break;
                case 5:
                    printStudentsPerCourse(db);
                    break;
                case 6:
                    printTrainersPerCourse(db);
                    break;
                case 7:
                    printAssignmentsPerCourse(db);
                    break;
                case 8:
                    printAssignmentsPerCoursePerStudent(db);
                    break;
                case 9:
                    query = "SELECT `Students`.`first_name`, `Students`.`last_name`,COUNT(`CoursesStudents`.`student_id`) FROM `Students` "
                            + "JOIN `CoursesStudents` ON `CoursesStudents`.`student_id` = `Students`.`id`"
                            + "GROUP BY `CoursesStudents`.`student_id`"
                            + "HAVING COUNT(`CoursesStudents`.`student_id`) >1;";
                    rs = db.getResults(query);
                    printStudentsHavingMultipleCoursesResults(rs);
                    break;
                case 10:
                    break OUTER;
                default:
                    break;
            }
        }
    }

    public static int enterOption() {
        int choice;
        do {
            System.out.print("Enter the corresponting number (1-10): ");
            while (!input.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                input.next();
            }
            choice = input.nextInt();
        } while (choice < 1 || choice > 10);
        return choice;
    }

    public static int insertRecordsToStudents(Database db) {
        int result = 0;
        int counter = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Lists.getStudentList() != null) {
            for (Student s : Lists.getStudentList()) {
                try {
                    String query = "INSERT INTO `Students` (`first_name`,`last_name`,`date_of_birth`,`tuition_fees`) VALUES (?,?,?,?);";

                    db.setPreparedStatement(query);

                    PreparedStatement pst = db.getPreparedStatement();

                    pst.setString(1, s.getFirstName());
                    pst.setString(2, s.getLastName());
                    pst.setString(3, s.getDateOfBirth().format(formatter));
                    pst.setInt(4, s.getFees());
                    result = pst.executeUpdate();
                    counter += result;
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Found Duplicate(s)!!");
                } catch (SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    counter = result;
                }
            }
        }
        return counter;
    }

    public static int insertRecordsToTrainers(Database db) {
        int result = 0;
        int counter = 0;
        if (Lists.getTrainerList() != null) {
            for (Trainer t : Lists.getTrainerList()) {
                try {
                    String query = "INSERT INTO `Trainers` (`first_name`,`last_name`,`subject`) VALUES (?,?,?)";
                    db.setPreparedStatement(query);

                    PreparedStatement pst = db.getPreparedStatement();

                    pst.setString(1, t.getFirstName());
                    pst.setString(2, t.getLastName());
                    pst.setString(3, t.getSubject());

                    result = pst.executeUpdate();
                    counter += result;
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Found Duplicate(s)!!");
                } catch (SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    counter = result;
                }
            }
        }
        return counter;
    }

    public static int insertRecordsToAssignments(Database db) {
        int result = 0;
        int counter = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Lists.getAssignments() != null) {
            for (Assignment a : Lists.getAssignments()) {
                try {
                    String query = "INSERT INTO `Assignments` (`title`,`description`,`sub_date_time`,`oral_mark`,`total_mark`) VALUES (?,?,?,?,?)";
                    db.setPreparedStatement(query);

                    PreparedStatement pst = db.getPreparedStatement();

                    pst.setString(1, a.getTitle());
                    pst.setString(2, a.getDescription());
                    pst.setString(3, a.getSubDateTime().format(formatter));
                    pst.setInt(4, a.getOralMark());
                    pst.setInt(5, a.getTotalMark());
                    result = pst.executeUpdate();
                    counter += result;
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Found Duplicate(s)!!");
                } catch (SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    counter = result;
                }
            }
        }
        return counter;
    }

    public static int insertRecordsToCourses(Database db) {
        int result = 0;
        int counter = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Lists.getCourses() != null) {
            for (Course c : Lists.getCourses()) {
                try {
                    String query = "INSERT INTO `Courses` (`title`,`stream`,`type`,`start_date`,`end_date`) VALUES (?,?,?,?,?)";
                    db.setPreparedStatement(query);

                    PreparedStatement pst = db.getPreparedStatement();

                    pst.setString(1, c.getTitle());
                    pst.setString(2, c.getStream());
                    pst.setString(3, c.getType());
                    pst.setString(4, c.getStartDate().format(formatter));
                    pst.setString(5, c.getEndDate().format(formatter));
                    result = pst.executeUpdate();
                    counter += result;
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Found Duplicate(s)!!");
                } catch (SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    counter = result;
                }
            }
        }
        return counter;
    }

    public static int insertRecordsToStudentsPerCourse(Database db) {
        int result = 0;
        int counter = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Lists.getCourses() != null) {
            for (Course c : Lists.getCourses()) {
                if (c.getStudentPerCourse() != null) {
                    for (Student s : c.getStudentPerCourse()) {
                        try {
                            String query = "INSERT INTO `CoursesStudents` (`course_id`,`student_id`)"
                                    + " VALUES ((SELECT `Courses`.`id` FROM `Courses` WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ? ),"
                                    + "(SELECT `Students`.`id` FROM `Students` WHERE `Students`.`first_name` = ? AND `Students`.`last_name` = ? AND `Students`.`date_of_birth`= ?))";
                            db.setPreparedStatement(query);

                            PreparedStatement pst = db.getPreparedStatement();

                            pst.setString(1, c.getTitle());
                            pst.setString(2, c.getStream());
                            pst.setString(3, c.getType());
                            pst.setString(4, s.getFirstName());
                            pst.setString(5, s.getLastName());
                            pst.setString(6, s.getDateOfBirth().format(formatter));
                            result = pst.executeUpdate();
                            counter += result;
                        } catch (SQLIntegrityConstraintViolationException e) {
                            System.out.println("Found Duplicate(s)!!");
                        } catch (SQLException ex) {
                            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                            counter = result;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public static int insertRecordsToTrainersPerCourse(Database db) {
        int result = 0;
        int counter = 0;
        if (Lists.getCourses() != null) {
            for (Course c : Lists.getCourses()) {
                if (c.getTrainersPerCourse() != null) {
                    for (Trainer t : c.getTrainersPerCourse()) {
                        try {
                            String query = "INSERT INTO `CoursesTrainers` (`course_id`,`trainer_id`)"
                                    + " VALUES ((SELECT `Courses`.`id` FROM `Courses` WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ?),"
                                    + "(SELECT `Trainers`.`id` FROM `Trainers` WHERE `Trainers`.`first_name` = ? AND `Trainers`.`last_name` = ? AND `Trainers`.`subject`= ?))";
                            db.setPreparedStatement(query);

                            PreparedStatement pst = db.getPreparedStatement();

                            pst.setString(1, c.getTitle());
                            pst.setString(2, c.getStream());
                            pst.setString(3, c.getType());
                            pst.setString(4, t.getFirstName());
                            pst.setString(5, t.getLastName());
                            pst.setString(6, t.getSubject());
                            result = pst.executeUpdate();
                            counter += result;
                        } catch (SQLIntegrityConstraintViolationException e) {
                            System.out.println("Found Duplicate(s)!!");
                        } catch (SQLException ex) {
                            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                            counter = result;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public static int insertRecordsToAssignmentsPerCourse(Database db) {
        int result = 0;
        int counter = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Lists.getCourses() != null) {
            for (Course c : Lists.getCourses()) {
                if (c.getAssignmentsPerCourse() != null) {
                    for (Assignment a : c.getAssignmentsPerCourse()) {
                        try {
                            String query = "INSERT INTO `CoursesAssignments` (`course_id`,`assignment_id`)"
                                    + " VALUES ((SELECT `Courses`.`id` FROM `Courses` WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ?),"
                                    + "(SELECT `Assignments`.`id` FROM `Assignments` WHERE `Assignments`.`title` = ? AND `Assignments`.`description` = ? AND `Assignments`.`sub_date_time`= ?))";
                            db.setPreparedStatement(query);

                            PreparedStatement pst = db.getPreparedStatement();

                            pst.setString(1, c.getTitle());
                            pst.setString(2, c.getStream());
                            pst.setString(3, c.getType());
                            pst.setString(4, a.getTitle());
                            pst.setString(5, a.getDescription());
                            pst.setString(6, a.getSubDateTime().format(formatter));
                            result = pst.executeUpdate();
                            counter += result;
                        } catch (SQLIntegrityConstraintViolationException e) {
                            System.out.println("Found Duplicate(s)!!");
                        } catch (SQLException ex) {
                            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                            counter = result;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public static void printTrainerResults(ResultSet rs) {
        try {
            System.out.println("\n                   ***** Printing Trainer List *****\n");
            while (rs.next()) {
                System.out.printf("First name: %-15s Last Name: %-15s Subject: %-15s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentResults(ResultSet rs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        try {
            System.out.println("\n                   ***** Printing Assignment List *****\n");
            while (rs.next()) {
                date = LocalDate.parse(rs.getString(3));
                System.out.printf("Title: %-15s Description: %-15s Submission Date: %-15s Oral Mark: %-5s Total Mark: %-5s\n",
                        rs.getString(1), rs.getString(2), date.format(formatter), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentResults(ResultSet rs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        try {
            System.out.println("\n                   ***** Printing Student List *****\n");
            while (rs.next()) {
                date = LocalDate.parse(rs.getString(3));
                System.out.printf("First name: %-15s Last Name: %-15s Birthday: %-15s Fees: %-5s\n",
                        rs.getString(1), rs.getString(2), date.format(formatter), rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printCourseResults(ResultSet rs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        LocalDate date2;
        try {
            System.out.println("\n                   ***** Printing Course List *****\n");
            while (rs.next()) {
                date = LocalDate.parse(rs.getString(4));
                date2 = LocalDate.parse(rs.getString(5));
                System.out.printf("Title: %-15s Stream: %-15s Type: %-15s Start Date: %-15s End Date: %-15s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3), date.format(formatter), date2.format(formatter));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsPerCourseResults(ResultSet rs) {
        try {
            System.out.println("\n                   ***** Printing Students Per Course *****\n");
            while (rs.next()) {
                System.out.printf("Course Title: %-15s Student: First name: %-15s Last Name: %-15s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printTrainersPerCourseResults(ResultSet rs) {
        try {
            System.out.println("\n                   ***** Printing Trainers Per Course *****\n");
            while (rs.next()) {
                System.out.printf("Course Title: %-15s Trainer: First name: %-15s Last Name: %-15s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsPerCourseResults(ResultSet rs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        try {
            System.out.println("\n                   ***** Printing Assignments Per Course *****\n");
            while (rs.next()) {
                date = LocalDate.parse(rs.getString(4));
                System.out.printf("Course Title: %-15s Assignment Title: %-15s Description: %-15s Submission Date: %s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3), date.format(formatter));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printAssignmentsPerCoursePerStudentResults(ResultSet rs) {
        try {
            System.out.println("\n                   ***** Printing Assignments Per Course Per Student *****\n");
            while (rs.next()) {
                System.out.printf("Course Title: %-10s Student: First name: %-15s Last Name: %-15s Assignment Title: %s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsHavingMultipleCoursesResults(ResultSet rs) {
        try {
            System.out.println("\n                   ***** Printing Students Having Multiple Courses *****\n");
            while (rs.next()) {
                System.out.printf("Student: First Name: %-15s Last Name: %-15s Number of Courses Enrolled: %s\n",
                        rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void printStudentsPerCourse(Database db) {
        Course c;
        if (Lists.getCourses() != null) {
        while (true) {
            System.out.println("\nPlease select a course to print the students in it . \n");
            int i;
            
                for (i = 0; i < Lists.getCourses().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
                }

                int choice1;
                do {
                    System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a valid number: ");
                        input.next();
                    }
                    choice1 = input.nextInt();
                } while (choice1 < 0 || choice1 > Lists.getCourses().size());
                if (choice1 == 0) {
                    break;
                }

                c = Lists.getCourses().get(choice1 - 1);

                try {
                    String query = "SELECT `Courses`.`title`,`Students`.`first_name`,`Students`.`last_name` FROM `Courses`"
                            + "JOIN `CoursesStudents` ON `Courses`.`id` = `CoursesStudents`.`course_id`"
                            + "JOIN `Students` ON `CoursesStudents`.`student_id` = `Students`.`id`"
                            + " WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ?"
                            + "ORDER BY `Courses`.`id`;";
                    db.setPreparedStatement(query);

                    PreparedStatement pst = db.getPreparedStatement();

                    pst.setString(1, c.getTitle());
                    pst.setString(2, c.getStream());
                    pst.setString(3, c.getType());

                    ResultSet rs = pst.executeQuery();
                    printStudentsPerCourseResults(rs);

                } catch (SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            System.out.println("No courses to add to !");
        }
    }

    public static void printTrainersPerCourse(Database db) {
        Course c;
        if (Lists.getCourses() != null) {
        while (true) {
            System.out.println("\nPlease select a course to print the trainers in it . \n");
            int i;
            
                for (i = 0; i < Lists.getCourses().size(); i++) {
                    System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
                }

                int choice1;
                do {
                    System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                    while (!input.hasNextInt()) {
                        System.out.print("Please enter a valid number: ");
                        input.next();
                    }
                    choice1 = input.nextInt();
                } while (choice1 < 0 || choice1 > Lists.getCourses().size());
                if (choice1 == 0) {
                    break;
                }

                c = Lists.getCourses().get(choice1 - 1);

                try {
                    String query = "SELECT `Courses`.`title`,`Trainers`.`first_name`,`Trainers`.`last_name` FROM `Courses`"
                            + "JOIN `CoursesTrainers` ON `Courses`.`id` = `CoursesTrainers`.`course_id`"
                            + "JOIN `Trainers` ON `CoursesTrainers`.`trainer_id` = `Trainers`.`id`"
                            + " WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ?"
                            + "ORDER BY `Courses`.`id`;";

                    db.setPreparedStatement(query);

                    PreparedStatement pst = db.getPreparedStatement();

                    pst.setString(1, c.getTitle());
                    pst.setString(2, c.getStream());
                    pst.setString(3, c.getType());

                    ResultSet rs = pst.executeQuery();
                    printTrainersPerCourseResults(rs);

                } catch (SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }
        }else{
            System.out.println("No courses to add to !");
        }
    }

    public static void printAssignmentsPerCoursePerStudent(Database db) {
        Course c;
         if(Lists.getCourses()!= null){
        while (true) {
            System.out.println("\nPlease select a course to print the assignments per course per student in it . \n");
            int i;
           
            for (i = 0; i < Lists.getCourses().size(); i++) {
                System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
            }

            int choice1;
            do {
                System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                while (!input.hasNextInt()) {
                    System.out.print("Please enter a valid number: ");
                    input.next();
                }
                choice1 = input.nextInt();
            } while (choice1 < 0 || choice1 > Lists.getCourses().size());
            if (choice1 == 0) {
                break;
            }

            c = Lists.getCourses().get(choice1 - 1);

            try {
                String query = "SELECT `Courses`.`title`,`Students`.`first_name`,`Students`.`last_name`,`Assignments`.`title`FROM `Assignments`"
                        + "JOIN `CoursesAssignments` ON `Assignments`.`id` = `CoursesAssignments`.`assignment_id`"
                        + "JOIN `Courses` ON `CoursesAssignments`.`course_id` = `Courses`.`id`"
                        + "JOIN `CoursesStudents` ON  `CoursesStudents`.`course_id` = `Courses`.`id`"
                        + "JOIN  `Students` ON `CoursesStudents`.`student_id` = `Students`.`id`"
                        + " WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ?"
                        + "ORDER BY `Courses`.`id`;";

                db.setPreparedStatement(query);

                PreparedStatement pst = db.getPreparedStatement();

                pst.setString(1, c.getTitle());
                pst.setString(2, c.getStream());
                pst.setString(3, c.getType());

                ResultSet rs = pst.executeQuery();
                printAssignmentsPerCoursePerStudentResults(rs);

            } catch (SQLException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        }else{
            System.out.println("No courses to add  to !");
        }
    }

    public static void printAssignmentsPerCourse(Database db) {
        Course c;
        if(Lists.getCourses() != null){
        while (true) {
            System.out.println("\nPlease select a course to print the assignments in it . \n");
            int i;
            for (i = 0; i < Lists.getCourses().size(); i++) {
                System.out.printf("%d. %s\n", (i + 1), Lists.getCourses().get(i));
            }

            int choice1;
            do {
                System.out.print("\nPlease enter the corresponding number or type 0 to exit: ");
                while (!input.hasNextInt()) {
                    System.out.print("Please enter a valid number: ");
                    input.next();
                }
                choice1 = input.nextInt();
            } while (choice1 < 0 || choice1 > Lists.getCourses().size());
            if (choice1 == 0) {
                break;
            }

            c = Lists.getCourses().get(choice1 - 1);

            try {
                String query = "SELECT `Courses`.`title`,`Assignments`.`title`,`Assignments`.`description`,`Assignments`.`sub_date_time` FROM `Courses`"
                        + "JOIN `CoursesAssignments` ON `Courses`.`id` = `CoursesAssignments`.`course_id`"
                        + "JOIN `Assignments` ON `CoursesAssignments`.`assignment_id` = `Assignments`.`id`"
                        + " WHERE `Courses`.`title` = ? AND `Courses`.`stream` = ? AND `Courses`.`type` = ?"
                        + "ORDER BY `Courses`.`id`;";

                db.setPreparedStatement(query);

                PreparedStatement pst = db.getPreparedStatement();

                pst.setString(1, c.getTitle());
                pst.setString(2, c.getStream());
                pst.setString(3, c.getType());

                ResultSet rs = pst.executeQuery();
                printAssignmentsPerCourseResults(rs);

            } catch (SQLException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }else{
            System.out.println("No courses to add to !");
        }
    }
        

}
