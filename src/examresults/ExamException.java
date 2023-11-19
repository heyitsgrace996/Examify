package examresults;

public class ExamException extends Exception {

    /*
    Exceptions and exception messages created based on project requirements.
    ExamExceptions to be used across both Exam and ExamResult classes and sub-classes.

    duration should be between 30 and 180 minutes (Exam)
    wordCount should be greater than or equal to 0 (Essay)
    wordLimit should be between 500 and 10000 (Essay)
    noQuestions should be between 10 and 50 (MultipleChoice)
    correctAnswers should be greater than or equal to zero (MultipleChoice)
     */

    public static final String INVALID_EXAM_ID = "Invalid exam ID: ID must be 5 digits.";
    public static final String INVALID_SUBJECT = "Invalid subject: Subject is not recognized. \n Subject must be Math, Science, History, English or Geography. \n Contact your Admin if you need to add more subjects. ";
    public static final String INVALID_DURATION = "Invalid duration: Duration must be between 30 and 180 minutes.";
    public static final String INVALID_WORD_CNT = "Invalid word count: Essay word count should be greater than or equal to 0.";
    public static final String INVALID_WORD_LIMIT = "Invalid word limit: Essay word limit should be between 500 and 10000.";
    public static final String INVALID_QUESTION_CNT = "Invalid number of questions: There should be between 10 and 50 Multiple Choice questions";
    public static final String INVALID_CORRECT_ANS_CNT = "Invalid number of correct answers: The number of correct answers should be greater or equal to 0.";
    public static final String INVALID_GRAMMAR_MARK = "Invalid Grammar mark: Essay grammar is marked out of 10 and must be equal to or greater than 0.";
    public static final String INVALID_ESSAY_MARK = "Invalid essay mark: Essay is marked out of 100 and must be equal to or greater than 0.";
    public static final String INVALID_ESSAY_ANS = "Invalid essay answer: Essay answer cannot be Null.";
    public static final String ID_NOT_FOUND = "Exam ID not found. Please create a new exam first.";

    // Constructor and other methods
    public ExamException(String message) {
        super(message);
    }
}

