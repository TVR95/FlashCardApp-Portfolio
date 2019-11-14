package Program.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Collections;

public class RepeatList {

    private ObservableList<WordToRepeat> todayList;
    private ObservableList<WordToRepeat> wrongAnswerList;

    public RepeatList() {

        todayList = FXCollections.observableArrayList();
        wrongAnswerList = FXCollections.observableArrayList();
    }

    public void addWordTodayList(WordToRepeat word) {

        todayList.add(word);
    }

    public void addWordWrongAnswerList(WordToRepeat word) {

        wrongAnswerList.add(word);
        System.out.println("AddWordWrongAnswerList: " + word.getWordToLearn() + "Size After Adding: " + wrongAnswerList.size());
    }

    public void addAllTodayList(ObservableList<WordToRepeat> list) {

        for (WordToRepeat word : list) {

            if (word.getNextRepeatDate().isBefore(LocalDate.now().plusDays(1))) {

                todayList.add(word);
            }
        }
    }

    public WordToRepeat getNextTodayWord() {

        if (todayList.size() != 0) {

            return todayList.get(0);
        } else if (todayList.size() == 0 && wrongAnswerList.size() != 0) {

            return wrongAnswerList.get(0);
        } else {

            return null;
        }
    }

    public WordToRepeat getWordAtIndex(int index) {

        if (todayList.size() != 0) {

            return todayList.get(index);
        } else if (todayList.size() == 0 && wrongAnswerList.size() != 0) {

            return wrongAnswerList.get(index);
        } else {

            return null;
        }
    }

    public WordToRepeat removeWordAtIndex(int index) {

        if (todayList.size() != 0) {

            System.out.println("Today List: Removing object --------- Size before removing: " + todayList.size());
            return todayList.remove(index);
        } else if (todayList.size() == 0 && wrongAnswerList.size() != 0) {

            return wrongAnswerList.remove(index);
        } else {

            return null;
        }
    }

    public void shuffleTodayList() {

        if (todayList.size() != 0) {

            Collections.shuffle(todayList);
        }
    }

    public void shuffleWrongAnswerList() {

        if (wrongAnswerList.size() != 0) {

            Collections.shuffle(wrongAnswerList);
        }
    }

    public void clearRepeatList() {

        todayList.clear();
        wrongAnswerList.clear();
    }

    public ObservableList<WordToRepeat> getTodayList() {
        return todayList;
    }

    public void setTodayList(ObservableList<WordToRepeat> todayList) {
        this.todayList = todayList;
    }

    public ObservableList<WordToRepeat> getWrongAnswerList() {
        return wrongAnswerList;
    }

    public void setWrongAnswerList(ObservableList<WordToRepeat> wrongAnswerList) {
        this.wrongAnswerList = wrongAnswerList;
    }
}
