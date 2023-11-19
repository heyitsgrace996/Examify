package examresults;

public class MCResult extends ExamResult{

    //Attributes
    private int correctAnswers;


    //Constructor
    public MCResult(Student student, Exam exam, int correctAnswers) throws ExamException {
        super(student, exam);

        //Validate exam type
        if (!(exam instanceof MultipleChoice)) {
            throw new ExamException("Invalid exam type for MCResult");
        }

        // Setters for validation
        setCorrectAnswers(correctAnswers);

        this.correctAnswers = correctAnswers;
    }

    //Getters and Setters

    //correctAnswers
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) throws ExamException {
        MultipleChoice mcExam = (MultipleChoice) getExam();
        if (correctAnswers < 0 || correctAnswers > mcExam.getNoQuestions()) {
            throw new ExamException(ExamException.INVALID_CORRECT_ANS_CNT);
        }
        this.correctAnswers = correctAnswers;
    }


    //Methods

    @Override
    public double calculateScore() {
        MultipleChoice mcExam = (MultipleChoice) getExam();
        return ((double) correctAnswers / mcExam.getNoQuestions()) * 100;
    }


    public double getScore() {
        return calculateScore();
    }


    //Get result details
    @Override
    public String toString() {
        return super.toString() +
                ", Correct Answers: "+ getCorrectAnswers() +
                ", Final Score: "+ getScore();
    }

}
