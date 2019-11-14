package Program.datamodel;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class WordProgress {

    private WordToRepeat wordToRepeat;
    private String button;
    private int badRange1, badRange2, badRange3, badRange4;
    private int goodRange1, goodRange2, goodRange3, goodRange4, goodRange5, goodRange6;
    private int veryGoodRange1, veryGoodRange2, veryGoodRange3, veryGoodRange4;

    public WordProgress() {

        setBadRangeDefaultValues();
        setGoodRangeDefaultValues();
        setVeryGoodRangeDefaultValues();
    }

    public WordProgress(WordToRepeat wordToRepeat, String button) {

        this.wordToRepeat = wordToRepeat;
        this.button = button;

        setBadRangeDefaultValues();
        setGoodRangeDefaultValues();
        setVeryGoodRangeDefaultValues();
    }

    /**
     * Method takes word and which button was pressed to calculate progress and repeat day for the word.
     *
     * @param wordToRepeat - word for which will be everything calculated.
     * @param button       - Three buttons so three options - bad, good, veryGood.
     */
    public void calculate(WordToRepeat wordToRepeat, String button) {

        this.wordToRepeat = wordToRepeat;
        this.button = button;
        calculateProgress();
    }

    private void calculateProgress() {

        switch (button) {
            case "bad":
                calculateBadProgress(wordToRepeat);
                break;
            case "good":
                calculateGoodProgress(wordToRepeat);
                break;
            case "veryGood":
                calculateVeryGoodProgress(wordToRepeat);
                break;
        }
    }

    private void calculateBadProgress(@NotNull WordToRepeat wordToRepeat) {

        // default implementation, return prevents from executing the rest of method.
        if (wordToRepeat.getCorrectRepeatNumber() == 0 || wordToRepeat.getRepeatNumber() < 4) {

            System.out.println("calculateBadProgress: Default Option");

            addProgress(0, 0);
            return;
        }

        // -------------------------------------------------------------------------------------------------------- //

        if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > 0 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= badRange1) {

            addProgress(0, 0);

            System.out.println("calculateBadProgress: First Option");
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > badRange1 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= badRange2) {

            addProgress(-1, 0);

            System.out.println("calculateBadProgress: Second Option");
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > badRange2 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= badRange3) {

            addProgress(-2, 0);

            System.out.println("calculateBadProgress: Third Option");
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > badRange3 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= badRange4) {

            addProgress(-3, 0);

            System.out.println("calculateBadProgress: Fourth Option");
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > badRange4) {

            addProgress(-5, 1);

            System.out.println("calculateBadProgress: Fifth Option");
        }
    }

    private void calculateGoodProgress(@NotNull WordToRepeat wordToRepeat) {

        // default implementation, return prevents from executing the rest of method.
        if (wordToRepeat.getCorrectRepeatNumber() == 0 || wordToRepeat.getRepeatNumber() < 4) {

            System.out.println("calculateGoodProgress: Default Option");

            addProgress(1, 1);
            return;
        }

        // -------------------------------------------------------------------------------------------------------- //

        if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > 0 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= goodRange1) {

            System.out.println("calculateGoodProgress: First Option");

            addProgress(2, 1);
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > goodRange1 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= goodRange2) {

            System.out.println("calculateGoodProgress: Second Option");

            addProgress(3, 1);
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > goodRange2 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= goodRange3) {

            System.out.println("calculateGoodProgress: Third Option");

            if (wordToRepeat.getLearningProgress() > 20) {

                addProgress(2, 2);
            } else {

                addProgress(2, 1);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > goodRange3 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= goodRange4) {

            System.out.println("calculateGoodProgress: Fourth Option");

            if (wordToRepeat.getLearningProgress() > 20) {

                addProgress(3, 2);
            } else {

                addProgress(2, 2);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > goodRange4 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= goodRange5) {

            System.out.println("calculateGoodProgress: Fifth Option");

            if (wordToRepeat.getLearningProgress() > 30 && wordToRepeat.getLearningProgress() < 60) {

                addProgress(5, 6);
            } else if (wordToRepeat.getLearningProgress() > 60 && wordToRepeat.getLearningProgress() < 100) {

                addProgress(6, 7);
            } else {

                addProgress(2, 2);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > goodRange5 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= goodRange6) {

            System.out.println("calculateGoodProgress: Sixth Option");

            if (wordToRepeat.getLearningProgress() > 30 && wordToRepeat.getLearningProgress() < 60) {

                addProgress(6, 8);
            } else if (wordToRepeat.getLearningProgress() > 60 && wordToRepeat.getLearningProgress() < 100) {

                addProgress(8, 10);
            } else {

                addProgress(4, 3);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > goodRange6) {

            System.out.println("calculateGoodProgress: Seventh Option");

            if (wordToRepeat.getLearningProgress() > 30 && wordToRepeat.getLearningProgress() < 60) {

                addProgress(7, 7);
            } else if (wordToRepeat.getLearningProgress() > 60 && wordToRepeat.getLearningProgress() < 100) {

                addProgress(10, 14);
            } else {

                addProgress(5, 4);
            }
        }
    }

    private void calculateVeryGoodProgress(@NotNull WordToRepeat wordToRepeat) {

        // default implementation, return prevents from executing the rest of method.
        if (wordToRepeat.getCorrectRepeatNumber() == 0 || wordToRepeat.getRepeatNumber() < 4) {

            System.out.println("calculateVeryGoodProgress: Default Option");

            addProgress(5, 2);
            return;
        }

        // -------------------------------------------------------------------------------------------------------- //

        if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > 0 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= veryGoodRange1) {

            System.out.println("calculateVeryGoodProgress: First Option");

            addProgress(5, 3);
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > veryGoodRange1 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= veryGoodRange2) {

            System.out.println("calculateVeryGoodProgress: Second Option");

            if (wordToRepeat.getLearningProgress() > 40) {

                addProgress(6, 5);
            } else {

                addProgress(5, 4);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > veryGoodRange2 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= veryGoodRange3) {

            System.out.println("calculateVeryGoodProgress: Third Option");

            if (wordToRepeat.getLearningProgress() > 40) {

                addProgress(7, 7);
            } else {

                addProgress(6, 5);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > veryGoodRange3 &&
                ((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) <= veryGoodRange4) {

            System.out.println("calculateVeryGoodProgress: Fourth Option");

            if (wordToRepeat.getLearningProgress() > 40) {

                addProgress(10, 10);
            } else {

                addProgress(7, 6);
            }
        } else if (((wordToRepeat.getCorrectRepeatNumber() * 100) / wordToRepeat.getRepeatNumber()) > veryGoodRange4) {

            System.out.println("calculateVeryGoodProgress: Fifth Option");

            if (wordToRepeat.getLearningProgress() > 40) {

                addProgress(12, 14);
            } else {

                addProgress(8, 7);
            }
        }
    }

    /**
     * Method adds progress to the word in WordLocalData
     *
     * @param progress      - how much progress will be added the word in WordLocalData
     * @param nextRepeatDay - The next repeat day of the word in WordLocalData
     */
    private void addProgress(int progress, int nextRepeatDay) {

        for (WordToRepeat word : WordLocalData.getInstance().getWordsToRepeat()) {

            if (word != null && word.getWordId().equals(wordToRepeat.getWordId())) {

                word.setLearningProgress(word.getLearningProgress() + progress);
                word.setNextRepeatDate(LocalDate.now().plusDays(nextRepeatDay));
                word.setRepeatNumber(word.getRepeatNumber() + 1);

                if (button != "bad") {

                    word.setCorrectRepeatNumber(word.getCorrectRepeatNumber() + 1);
                }
            }
        }
    }

    private void setBadRangeDefaultValues() {

        setBadRange1(60);
        setBadRange2(70);
        setBadRange3(80);
        setBadRange4(90);
    }

    private void setGoodRangeDefaultValues() {

        setGoodRange1(30);
        setGoodRange2(40);
        setGoodRange3(50);
        setGoodRange4(60);
        setGoodRange5(70);
        setGoodRange6(80);
    }

    private void setVeryGoodRangeDefaultValues() {

        setVeryGoodRange1(60);
        setVeryGoodRange2(70);
        setVeryGoodRange3(80);
        setVeryGoodRange4(90);
    }

    public WordToRepeat getWordToRepeat() {
        return wordToRepeat;
    }

    public void setWordToRepeat(WordToRepeat wordToRepeat) {
        this.wordToRepeat = wordToRepeat;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public int getBadRange1() {
        return badRange1;
    }

    public void setBadRange1(int badRange1) {
        this.badRange1 = badRange1;
    }

    public int getBadRange2() {
        return badRange2;
    }

    public void setBadRange2(int badRange2) {
        this.badRange2 = badRange2;
    }

    public int getBadRange3() {
        return badRange3;
    }

    public void setBadRange3(int badRange3) {
        this.badRange3 = badRange3;
    }

    public int getBadRange4() {
        return badRange4;
    }

    public void setBadRange4(int badRange4) {
        this.badRange4 = badRange4;
    }

    public int getGoodRange1() {
        return goodRange1;
    }

    public void setGoodRange1(int goodRange1) {
        this.goodRange1 = goodRange1;
    }

    public int getGoodRange2() {
        return goodRange2;
    }

    public void setGoodRange2(int goodRange2) {
        this.goodRange2 = goodRange2;
    }

    public int getGoodRange3() {
        return goodRange3;
    }

    public void setGoodRange3(int goodRange3) {
        this.goodRange3 = goodRange3;
    }

    public int getGoodRange4() {
        return goodRange4;
    }

    public void setGoodRange4(int goodRange4) {
        this.goodRange4 = goodRange4;
    }

    public int getGoodRange5() {
        return goodRange5;
    }

    public void setGoodRange5(int goodRange5) {
        this.goodRange5 = goodRange5;
    }

    public int getGoodRange6() {
        return goodRange6;
    }

    public void setGoodRange6(int goodRange6) {
        this.goodRange6 = goodRange6;
    }

    public int getVeryGoodRange1() {
        return veryGoodRange1;
    }

    public void setVeryGoodRange1(int veryGoodRange1) {
        this.veryGoodRange1 = veryGoodRange1;
    }

    public int getVeryGoodRange2() {
        return veryGoodRange2;
    }

    public void setVeryGoodRange2(int veryGoodRange2) {
        this.veryGoodRange2 = veryGoodRange2;
    }

    public int getVeryGoodRange3() {
        return veryGoodRange3;
    }

    public void setVeryGoodRange3(int veryGoodRange3) {
        this.veryGoodRange3 = veryGoodRange3;
    }

    public int getVeryGoodRange4() {
        return veryGoodRange4;
    }

    public void setVeryGoodRange4(int veryGoodRange4) {
        this.veryGoodRange4 = veryGoodRange4;
    }
}
