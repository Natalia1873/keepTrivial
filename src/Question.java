class Question {
    private final String question;
    private final String option1;
    private final String option2;
    private final String option3;
    private final String option4;
    private final int correctAnswer;

    public Question(String question, String option1, String option2, String option3, String option4, int correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return question + "\n1. " + option1 + "\n2. " + option2 + "\n3. " + option3 + "\n4. " + option4;
    }
}

