package examresults;

//ExamResults and sub-classes EssayResults and MCResults to handle the results of each student for each exam
public abstract class ExamResult implements Scorable/*,Comparable<ExamResult>*/ {


    //Attributes
    private Student student;
    private Exam exam;


    // Constructor
    public ExamResult(Student student, Exam exam) {
        this.student = student;
        this.exam = exam;
    }

    // getScore abstract here since Essay and MC use different methods of score calculation
    abstract double getScore();


    //Getters - no setters because so far we are just pulling Student and Exam objects.

    public Student getStudent() {
        return student;
    }

    public Exam getExam() {
        return exam;
    }

    /* compareTo method for Comparable interface
    @Override
    public int compareTo(ExamResult other) {
        return Double.compare(this.getScore(), other.getScore());
    }*/

    // Get result details using toString
    @Override
    public String toString() {
        return "Student Id=" + student.getStudentId() +
                ", Exam Id=" + exam.getExamId();
    }
}
