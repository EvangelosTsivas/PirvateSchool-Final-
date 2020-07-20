package privateschool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/Private_School?zeroDateTimeBehavior=CONVERT_TO_NULL&useUnicode=true&useJDBCCompliantTimezone"
            + "Shift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "Turin1985!";
    private static Connection connection = null;
    private static Statement statement = null;
    private static PreparedStatement pst = null;

    public Database() {
        getConnection();
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static PreparedStatement getPreparedStatement() {
        return pst;
    }

    public static void setPreparedStatement(String query) {
        try {
            pst = connection.prepareStatement(query);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ResultSet getResults(String query) {
        try {
            setStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void populateDatabase() {

        String sql = "INSERT INTO `Courses`  (`title`,`stream`,`type`,`start_date`,`end_date`)"
                + "VALUES ('CB8','Java','Full-Time','2020-04-10','2020-07-10'),"
                + "('CB9','Java','Part-Time','2020-04-08','2020-09-10'),"
                + "('CB10','C#','Full-Time','2020-05-08','2020-07-08'),"
                + "('CB11','C#','Part-Time','2020-06-05','2020-12-04'),"
                + "('CB12','JavaScript','Full-Time','2020-05-03','2020-07-09'),"
                + "('CB13','JavaScript','Part-Time','2020-04-10','2020-10-10');";

        Database.setStatement();
        Statement st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "INSERT INTO `Students` (`first_name`,`last_name`,`date_of_birth`,`tuition_fees`)"
                + "VALUES ('Evangelos','Tsivas','1985-07-23',2500),"
                + "('Petros','Grivas','1989-07-08',2200),"
                + "('Thomas','Petropoulos','1993-12-12',1850),"
                + "('Maria','Ioannidou','1987-06-22',1900),"
                + "('Evi','Kelesidou','1990-09-24',1650),"
                + "('Eleni','Kanakidou','1986-11-01',3500),"
                + "('Markos','Saridis','1995-11-11',3500),"
                + "('Mixalis','Mpekas','1988-04-13',2500),"
                + "('Evaggelia','Tasiou','1989-12-01',1500),"
                + "('Mairi','Nkolaidou','1991-07-01',1850);";

        st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        sql = "INSERT INTO `Trainers` (`first_name`,`last_name`,`subject`)"
                + "VALUES ('Anastasios','Trekos','Java'),"
                + "('Xaralampos','Ntorvas','C#'),"
                + "('Kleomenis','Mpismpis','HTML'),"
                + "('Anna','Mixailidou','Javascript'),"
                + "('Maria','Silka','SQL'),"
                + "('Athanasios','Ioannidis','Java'),"
                + "('Kaiti','Garmpi','Web Development'),"
                + "('Anastasia','Glykou','C++'),"
                + "('Xaralampos','Paxatouridis','Python'),"
                + "('Panagiota','Tsampa','C#');";
        st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "INSERT INTO `Assignments` (`title`,`description`,`sub_date_time`,`oral_mark`,`total_mark`)"
                + "VALUES ('Project 1','Individual','2020-06-24',80,90),"
                + "('Project 2','Individual','2020-07-06',70,80),"
                + "('Assignment 1','Individual','2020-05-20',60,90),"
                + "('Assignment 2','Individual','2020-09-07',80,90),"
                + "('Assignment 3','Group','2020-06-15',60,75),"
                + "('Assignment 4','Group','2020-09-11',55,65);";

        st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "INSERT INTO `Private_School`.`CoursesStudents` (`course_id`, `student_id`) VALUES ('1', '1'),"
                + "('1', '3'),"
                + "('2', '4'),"
                + "('2', '6'),"
                + "('3', '2'),"
                + "('3', '7'),"
                + "('4', '1'),"
                + "('4', '8'),"
                + "('5', '9'),"
                + "('5', '10'),"
                + "('6', '5'),"
                + "('6', '2'),"
                + "('1', '7'),"
                + "('3', '10'),"
                + "('5', '6');";
        st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "INSERT INTO `Private_School`.`CoursesTrainers` (`course_id`, `trainer_id`) VALUES ('1', '1'),"
                + "('2', '3'),"
                + "('3', '1'),"
                + "('3', '4'),"
                + "('4', '5'),"
                + "('5', '6'),"
                + "('4', '4'),"
                + "('5', '7'),"
                + "('5', '9'),"
                + "('6', '10'),"
                + "('6', '2'),"
                + "('2', '4'),"
                + "('3', '10'),"
                + "('4', '8'),"
                + "('1', '3');";
        st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "INSERT INTO `Private_School`.`CoursesAssignments` (`course_id`, `assignment_id`) VALUES ('1', '1'),"
                + "('2', '2'),"
                + "('3', '3'),"
                + "('4', '5'),"
                + "('5', '4'),"
                + "('6', '6'),"
                + "('1', '6'),"
                + "('3', '4'),"
                + "('2', '1'),"
                + "('6', '3');";

        st = Database.getStatement();
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteTables() {

        String sql
                = "DELETE FROM `Private_School`.`CoursesStudents`;";
        Database.setStatement();
        Statement st = Database.getStatement();
        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }

        sql = "DELETE FROM `Private_School`.`CoursesTrainers`;";

        st = Database.getStatement();

        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        sql = "DELETE FROM `Private_School`.`CoursesAssignments`;";

        st = Database.getStatement();

        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        sql = "DELETE FROM `Private_School`.`Courses`;";

        st = Database.getStatement();

        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        sql = "DELETE FROM `Private_School`.`Students`;";

        st = Database.getStatement();

        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }

        sql = "DELETE FROM `Private_School`.`Trainers`;";

        st = Database.getStatement();

        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
        sql = "DELETE FROM `Private_School`.`Assignments`;";

        st = Database.getStatement();

        try {
            st.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public static void alterTables() {

        String sql = "ALTER TABLE `Private_School`.`CoursesTrainers` AUTO_INCREMENT =1;";
        Database.setStatement();
        Statement st = Database.getStatement();
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        sql = "ALTER TABLE `Private_School`.`CoursesStudents` AUTO_INCREMENT =1;";
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        sql = "ALTER TABLE `Private_School`.`CoursesAssignments` AUTO_INCREMENT =1;";
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        sql = "ALTER TABLE `Private_School`.`Courses` AUTO_INCREMENT =1;";
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        sql = "ALTER TABLE `Private_School`.`Students` AUTO_INCREMENT =1;";
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        sql = "ALTER TABLE `Private_School`.`Trainers` AUTO_INCREMENT =1;";
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        sql = "ALTER TABLE `Private_School`.`Assignments` AUTO_INCREMENT =1;";
        try {
            st.execute(sql);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cleanDatabase() {
        deleteTables();
        alterTables();
    }
}
