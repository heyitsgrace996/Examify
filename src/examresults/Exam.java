package examresults;

import java.util.List;
import java.util.Arrays;

//Exam and sub-classes Essay and Multiple-Choice to handle details of each exam. *Separate to exam results*
public abstract class Exam {


        //Attributes
        public static final List<String> VALID_SUBJECTS = Arrays.asList("Math", "Science", "History", "English", "Geography");
        private String examId;
        private String subject;
        private int duration;


        //Constructor
        public Exam(String examId, String subject, int duration) throws ExamException{

                //setters for validation check
                setExamId(examId);
                setSubject(subject);
                setDuration(duration);

                this.examId = examId;
                this.subject = subject;
                this.duration = duration;
        }


        // Getters and Setters for each attribute

        //examId
        public String getExamId() {
                return examId;
        }

        public void setExamId(String examId) throws ExamException {
                if (examId == null || !examId.matches("\\d{5}")) {
                        throw new ExamException(ExamException.INVALID_EXAM_ID);
                }
                this.examId = examId;
        }

        //subject
        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) throws ExamException {
                if (!VALID_SUBJECTS.contains(subject)) {
                        throw new ExamException(ExamException.INVALID_SUBJECT);
                }
                this.subject = subject;
        }

        //duration
        public int getDuration() {
                return duration;
        }

        public void setDuration(int duration) throws ExamException {
                if (duration < 30 || duration > 180) {
                        throw new ExamException(ExamException.INVALID_DURATION);
                }
                this.duration = duration;
        }


        //Methods

        //toString for details of each exam;
        public String toString() {
                return "examId='" + examId + '\'' +
                        ", subject='" + subject + '\'' +
                        ", duration=" + duration;
        }
}
