package examresults;

public class Essay extends Exam {


    // Attributes
    private int wordLimit;


    // Constructor
    public Essay(String examId, String subject, int duration, int wordLimit) throws ExamException {
        super(examId, subject, duration);

        // Setter for validation
        setWordLimit(wordLimit);

        this.wordLimit = wordLimit;
    }


    //Setters and Getters

    //wordLimit
    public int getWordLimit() {
        return wordLimit;
    }

    public void setWordLimit(int wordLimit) throws ExamException{
        if (wordLimit < 500 || wordLimit > 10000) {
            throw new ExamException(ExamException.INVALID_WORD_LIMIT);
        }
        this.wordLimit = wordLimit;
    }


    //Methods

    //toString for exam details
    @Override
    public String toString() {
        return super.toString() +
                ", Word Limit: " + wordLimit;
    }
}

