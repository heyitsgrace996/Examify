package examresults;

import java.util.ArrayList;
import java.util.List;


//Class for storing details of students and their exam results after input/initialization.
public class StudentService {

    //Attributes

    //Init list as empty
    public static List<Student> students = new ArrayList<>();

    //get list of Students
    public List<Student> getStudents() {
        return new ArrayList<>(students); // Return a copy of the list to prevent external modifications
    }

    //Add student to list + validate
    public void addStudent(Student student) throws StudentException {
        if (studentExists(student.getStudentId())) {
            throw new StudentException(StudentException.ALREADY_EXISTS);
        }
        students.add(student);
    }

    //Check if student already exists via ID
    public boolean studentExists(int studentId) {
        return students.stream().anyMatch(student -> student.getStudentId() == studentId);
    }

    //Get student by their ID
    public Student getStudentById(int studentId) throws StudentException {
        return students.stream()
                .filter(student -> student.getStudentId() == studentId)
                .findFirst()
                .orElseThrow(() -> new StudentException(StudentException.ID_NOT_FOUND));
    }


    //Get student by their Name
    public Student getStudentByName(String studentName) throws StudentException{
        return students.stream()
                .filter(student -> student.getStudentName().equals(studentName))
                .findFirst()
                .orElseThrow(() -> new StudentException(StudentException.INVALID_STUDENT_NAME));
    }

    //Method to print just names and IDs of all students
    public String studentIndex() {
        StringBuilder builder = new StringBuilder();
        builder.append("List of Students:\n");
        for (Student student : students) {
            builder.append("ID: ").append(student.getStudentId())
                    .append(", Name: ").append(student.getStudentName())
                    .append("\n");
        }
        return builder.toString();
    }

    //toString for all details
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StudentService: \n");
        for (Student student : students) {
            builder.append(student.toString()).append("\n");
        }
        return builder.toString();
    }
}