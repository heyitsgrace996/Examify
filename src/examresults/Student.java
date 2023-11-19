package examresults;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;

//Class for handling student records - linked to exam results. See also StudentService
public class Student implements Printable {

    //Attributes
    private final int studentId;
    private String studentName;
    private List<ExamResult> examResults = new ArrayList<>();

    // Constructor
    public Student(int studentId, String studentName) throws StudentException {

        //Id validation
        if (String.valueOf(studentId).length() != 5) {
            throw new StudentException(StudentException.INVALID_STUDENT_ID);
        }

        this.studentId = studentId;

        //Setter for validation
        setStudentName(studentName);
        this.studentName = studentName;
    }

    //Setters and Getters

    //studentName
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) throws StudentException {
        if (studentName == null || studentName.length() < 2 || studentName.length() > 30) {
            throw new StudentException(StudentException.INVALID_STUDENT_NAME);
        }
        this.studentName = studentName;
    }


    //Getter for studentId, final so no setter
    public int getStudentId() {
        return studentId;
    }


    //Method to add an exam to examsTaken
    public void addExamResult(ExamResult result) {
        this.examResults.add(result);
    }


    // Get the list of exams taken
    public List<ExamResult> getExamResults() {
        return this.examResults;
    }


    //Output methods

    //Summary Results
    @Override
    public String printSummaryResults() {

        StringBuilder builder = new StringBuilder();

        //Header
        builder.append("Student ID,Name,Total Exams Taken,Average Score\n");

        //Calculate avg score
        double totalScore = 0;
        for (ExamResult result : examResults) {
            totalScore += result.getScore();
        }

        double averageScore;

        if (examResults.isEmpty()) {
            averageScore = 0;
        } else {
            averageScore = totalScore / examResults.size();
        }

        // Append student summary details
        builder.append(String.format("%d,%s,%d,%.2f\n",
                studentId,
                studentName,
                examResults.size(),
                averageScore));

        return builder.toString();
    }


    //Detailed Results
    @Override
    public String printDetailedResults() {
        StringBuilder builder = new StringBuilder();
        // Header
        builder.append("Student ID,Name,Exam Type,Exam ID,Subject,Duration,Exam Type,Score\n");

        //Body
        for (ExamResult examResult : examResults) {
            Exam exam = examResult.getExam();
            String examType = exam.getClass().getSimpleName(); //Get the class name as exam type for Essay or MultipleChoice

            builder.append(String.format("%d,%s,%s,%s,%d,%s,%.2f\n",
                    studentId,
                    studentName,
                    exam.getExamId(),
                    exam.getSubject(),
                    exam.getDuration(),
                    examType,
                    examResult.getScore()));
        }
        return builder.toString();
    }

    //toString for tests and calls
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student ID: ").append(studentId)
                .append(", Name: ").append(studentName)
                .append(", Total Exams Taken: ").append(examResults.size())
                .append("\nExam Results:\n");

        for (ExamResult result : examResults) {
            builder.append(" - ").append(result)
                    .append("\n");
        }

        return builder.toString();
    }

    // Method to save summary results to CSV file
    public void saveSummaryResultsToCsv(String directoryPath, String fileName) {
        try {
            String summaryCsvContent = printSummaryResults();
            CsvFileWriter.writeToCsvFile(directoryPath, fileName, summaryCsvContent);
            notifyUser("Summary results saved to: " + Paths.get(directoryPath, fileName));
        } catch (IOException e) {
            notifyUser("Failed to save summary results: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveDetailedResultsToCsv(String directoryPath, String fileName) {
        try {
            String detailedCsvContent = printDetailedResults();
            CsvFileWriter.writeToCsvFile(directoryPath, fileName, detailedCsvContent);
            notifyUser("Detailed results saved to: " + Paths.get(directoryPath, fileName));
        } catch (IOException e) {
            notifyUser("Failed to save detailed results: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void notifyUser(String message) {
        System.out.println(message);
    }
}

