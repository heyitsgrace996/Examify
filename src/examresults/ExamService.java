package examresults;

import java.util.ArrayList;
import java.util.List;



//ExamService handles storing all exams after they are added/initialized.
//This service only holds exam details, no student or result information.
public class ExamService {

    //Attributes
    private List<Exam> exams;

    //Constructor - init list as empty to start
    public ExamService() {

        exams = new ArrayList<>();
    }


    //Getters and Setters
    public Exam getExamById(String examId) throws ExamException {
        for (Exam exam : exams) {
            if (exam.getExamId().equals(examId)) {
                return exam;
            }
        }
        throw new ExamException(ExamException.ID_NOT_FOUND);
    }

    //Add exam to exam list
    public void addExam(Exam exam) {
        exams.add(exam);
    }


    //Methods

    //Subject Validation
    public void validateSubject(String subject) throws ExamException {
        if (!Exam.VALID_SUBJECTS.contains(subject)) {
            throw new ExamException(ExamException.INVALID_SUBJECT);
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Exams:\n");
        for (Exam exam : exams) {
            builder.append(exam).append("\n"); // Assuming Exam has a meaningful toString implementation
        }
        return builder.toString();
    }
}
