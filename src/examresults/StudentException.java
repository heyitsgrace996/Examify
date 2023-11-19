package examresults;

public class StudentException extends Exception {

    public static final String INVALID_STUDENT_NAME = "Student name must be between 2 and 30 characters.";
    public static final String INVALID_STUDENT_ID = "Student ID must be 5 digits.";
    public static final String ID_NOT_FOUND = "Student ID does not exist. Please create a new student first.";
    public static final String NAME_NOT_FOUND = "Student Name does not exist. Please create a new student first.";
    public static final String ALREADY_EXISTS = "Student ID already present in directory. Please verify details and try again.";

    // Constructor and other methods
    public StudentException(String message) {
        super(message);
    }
}

