package privateschool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Student {

    private String firstName;
    private String lastName;
    private LocalDate dateOFBirth;
    private int tuitionFees;
    

    public Student(String firstName, String lastName, LocalDate dateOFBirth, int tuitionFees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOFBirth = dateOFBirth;
        this.tuitionFees = tuitionFees;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOFBirth;
    }

    public void setDateOfBirth(LocalDate dateOFBirth) {
        this.dateOFBirth = dateOFBirth;
    }

    public int getFees() {
        return tuitionFees;
    }

    public void setFees(int tuitionFees) {
        this.tuitionFees = tuitionFees;
    }
    
   
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String message = String.format("First Name: %-10s Last Name: %-12s Birthday: %-12s Fees: %-5d€",firstName,lastName,dateOFBirth.format(formatter),tuitionFees);
        return message;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.firstName);
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.dateOFBirth);
        hash = 97 * hash + this.tuitionFees;
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
        final Student other = (Student) obj;
        if (this.tuitionFees != other.tuitionFees) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.dateOFBirth, other.dateOFBirth)) {
            return false;
        }
        return true;
    }

    
    
}
