
package privateschool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;


public class Course {
    
    private String title;
    private String stream;
    private String type;
    private LocalDate start_date;
    private LocalDate end_date;
    private ArrayList<Student> students;
    private ArrayList<Trainer> trainers;
    private ArrayList<Assignment> assignments;

    public Course(String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }

  public  void addStudentsPerCourse(Student student) {
        if (this.students == null) {
            this.students = new ArrayList();
        }
        this.students.add(student);
    }

    public  ArrayList<Student> getStudentPerCourse() {
        return this.students;
    }
    
    public  void addTrainersPerCourse(Trainer trainer) {
        if (this.trainers == null) {
            this.trainers = new ArrayList();
        }
        this.trainers.add(trainer);
    }
    
    public  ArrayList<Trainer> getTrainersPerCourse() {
        return this.trainers;
    }
    
    
     public  void addAssignmentsPerCourse(Assignment assignment) {
        if (this.assignments == null) {
            this.assignments = new ArrayList();
        }
        this.assignments.add(assignment);
    }
     
     public  ArrayList<Assignment> getAssignmentsPerCourse() {
        return this.assignments;
    } 
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String message = String.format("Title: %-5s  Stream: %-7s Type: %-12s Start-Date: %-12s End-Date: %-12s",title,stream,type,start_date.format(formatter),end_date.format(formatter )); 
        return  message;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.stream);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.start_date);
        hash = 97 * hash + Objects.hashCode(this.end_date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.stream, other.stream)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.start_date, other.start_date)) {
            return false;
        }
        if (!Objects.equals(this.end_date, other.end_date)) {
            return false;
        }
        return true;
    }
 
}
