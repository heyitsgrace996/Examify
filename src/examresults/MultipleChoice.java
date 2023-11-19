package examresults;


public class MultipleChoice extends Exam {


    //Attributes
    private int noQuestions;


    //Constructor
    public MultipleChoice(String examId, String subject, int duration, int noQuestions) throws ExamException {
        super(examId, subject, duration);

        //Setter for validation
        setNoQuestions(noQuestions);

        this.noQuestions = noQuestions;
    }


    //Getters and Setters

    //Number of questions for MC exam
    public int getNoQuestions() {

        return noQuestions;
    }

    public void setNoQuestions(int noQuestions) throws ExamException {
        if (noQuestions < 10 || noQuestions > 50) {
            throw new ExamException(ExamException.INVALID_QUESTION_CNT);
        }
        this.noQuestions = noQuestions;
    }


    //Methods

    //toString for exam details
    @Override
    public String toString() {
        return super.toString() +
                ", Number of Questions: " + noQuestions;
    }

}

