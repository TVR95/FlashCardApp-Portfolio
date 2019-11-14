package Program.datamodel;

import java.time.LocalDate;

public class WordToRepeat {
    private String wordToLearn;
    private String translation;
    private String note;
    private LocalDate addingDate;
    private LocalDate nextRepeatDate;
    private Integer learningProgress;
    private Integer repeatNumber;
    private Integer correctRepeatNumber;
    private Integer wordId;

    public WordToRepeat(String wordToLearn, String translation,
                        String note, LocalDate addingDate, Integer learningProgress,
                        LocalDate nextRepeatDate, Integer repeatNumber, Integer correctRepeatNumber,
                        Integer wordId) {
        this.wordToLearn = wordToLearn;
        this.translation = translation;
        this.note = note;
        this.addingDate = addingDate;
        this.learningProgress = learningProgress;
        this.nextRepeatDate = nextRepeatDate;
        this.repeatNumber = repeatNumber;
        this.correctRepeatNumber = correctRepeatNumber;
        this.wordId = wordId;
    }

    public String getWordToLearn() {
        return wordToLearn;
    }

    public void setWordToLearn(String wordToLearn) {
        this.wordToLearn = wordToLearn;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(LocalDate addingDate) {
        this.addingDate = addingDate;
    }

    public Integer getLearningProgress() {
        return learningProgress;
    }

    public void setLearningProgress(Integer learningProgress) {
        this.learningProgress = learningProgress;
    }

    public LocalDate getNextRepeatDate() {
        return nextRepeatDate;
    }

    public void setNextRepeatDate(LocalDate nextRepeatDate) {
        this.nextRepeatDate = nextRepeatDate;
    }

    public Integer getRepeatNumber() {
        return repeatNumber;
    }

    public void setRepeatNumber(Integer repeatNumber) {
        this.repeatNumber = repeatNumber;
    }

    public Integer getCorrectRepeatNumber() {
        return correctRepeatNumber;
    }

    public void setCorrectRepeatNumber(Integer correctRepeatNumber) {
        this.correctRepeatNumber = correctRepeatNumber;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }
}
