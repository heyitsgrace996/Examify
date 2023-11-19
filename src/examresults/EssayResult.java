package examresults;

public class EssayResult extends ExamResult{


    //Attributes
    private String essayAnswer;
    private int grammar;
    private int content;
    private int wordCount;
    private double totalScore;


    //Constructor
    public EssayResult(Student student, Exam exam, String essayAnswer, int grammar, int content) throws ExamException {
        super(student, exam);

        if (!(exam instanceof Essay)) {
            throw new ExamException("Invalid exam type for EssayResult");
        }

        //Setters for validation
        setEssayAnswer(essayAnswer);
        setGrammar(grammar);
        setContent(content);

        this.essayAnswer = essayAnswer;
        this.grammar = grammar;
        this.content = content;

        //Final essay grade needs to be set as soon as we get the values, so init here
        gradeEssay();
    }


    //Getters and Setters

    //EssayAnswer
    public String getEssayAnswer() {return essayAnswer;}
    public void setEssayAnswer(String essayAnswer) throws ExamException {

        //Validate essayAnswer
        if (essayAnswer == null) {
            throw new ExamException(ExamException.INVALID_ESSAY_ANS);
        }

        //Count words
        int newWordCount = countWordsInEssay(essayAnswer);

        //Validate word count
        if (newWordCount <= 0) {
            throw new ExamException(ExamException.INVALID_WORD_CNT);
        }

        //Set the essay answer and update the word count together
        this.essayAnswer = essayAnswer;
        this.wordCount = newWordCount;
    }

    //Count words in the essayAnswer
    private int countWordsInEssay(String essay) {
        if (essay == null || essay.isEmpty()) {
            return 0;
        }

        //split with regex notation for whitespace and add to array, then get length
        String[] words = essay.trim().split("\\s+");
        return words.length;
    }

    //grammar
    public int getGrammar() {
        return grammar;
    }

    public void setGrammar(int grammar) throws ExamException{
        if (grammar < 0 || grammar > 10) {
            throw new ExamException(ExamException.INVALID_GRAMMAR_MARK);
        }
        this.grammar = grammar;
    }

    //content
    public int getContent() {
        return content;
    }

   //word count
    public int getWordCount() {return wordCount;}

    public void setContent(int content) throws ExamException{
        if (content < 0 || content > 100) {
            throw new ExamException(ExamException.INVALID_ESSAY_MARK);
        }
        this.content = content;
    }

    //Essay Grading Logic:
    //Grammar - 10% of grade marked out of 10
    //Content - 90% of grade - will be marked out of 100 and then normalised
    //WordCount - If count is below 500, or above the specified word limit
    //Below 500 - instead of 90%, content mark is capped at 45% of total grade
    //Over word limit - Dock 2% for every 10% over limit - capped at 20%
    public void gradeEssay() {
        Essay eExam = (Essay) getExam();

        double contentScore = 0;

        //Grammar score calc as %
        double grammarScore = grammar * .1;

        //Content score as %
        if (wordCount >= 500 && wordCount < eExam.getWordLimit()) {
            contentScore = (double) content * .9;
        } else if (wordCount < 500) {
            //Normalize to 45% of total grade
            contentScore = (double) content * .45;
        } else if (wordCount > eExam.getWordLimit()) {
            //Calculate the penalty for exceeding word limit
            double percentOver = ((double) (wordCount - eExam.getWordLimit()) / eExam.getWordLimit()) * 100;
            int penalty = (int)(2 * (percentOver / 10));
            penalty = Math.min(penalty,20);
            contentScore = Math.max(0, contentScore - penalty);
        }

        totalScore = grammarScore + contentScore;

    }
    @Override
    public double calculateScore() {
        return totalScore;
    }

    public double getScore() {return calculateScore();}

    //Get result details
    @Override
    public String toString() {
        return super.toString() +
                ", Essay Answer: "+ getEssayAnswer() +
                ", Grammar Score: "+ getGrammar() +
                ", Content Score: "+ getContent() +
                ", Word Count: "+ getWordCount() +
                ", Final Score: "+ getScore();
    }
}
