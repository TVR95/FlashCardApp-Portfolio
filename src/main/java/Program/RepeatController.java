/*
 *  TODO LIST OF STUFF TO IMPLEMENT:
 *   1) Auto Save - Super simple version of this is already implemented.
 *   2) Simple css - black theme and light theme
 *   3) Fix bugs:
 *      - Import bug (shouldn't use the same path as open file path)
 *      - During creating words, bug with enter. Creates empty enter in file and damage it (can't open file)
 *   4) Create version that use data bases instead of files
 *   5) Create simple android version of this program
 * */

package Program;

import Program.datamodel.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class RepeatController {

    private RepeatList repeatList;
    private FileOperation fileOperation;
    private WordProgress wordProgress;
    private boolean enterPressedAnswerArea;
    private boolean wrongAnswer;
    private int counterEnterPressedAnswerArea;
    // simple auto save variable that is used for auto saving our file.
    private int autoSaveCounter;

    // temporary variable used to compare old list with new.
    private ObservableList<WordToRepeat> wordsToRepeatTemp = FXCollections.observableArrayList();

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TextArea answerTextArea, askTextArea;

    @FXML
    private HBox buttonsHBox;

    @FXML
    private Label wordRepeatNumberLabel;

    @FXML
    public void initialize() {

        initializeNewRepeatList();
        addListenerRepeatList();
        initializeFileOperation();
        initializeWordProgress();
        setDefaultVariableValues();
    }

    @FXML
    public void handleNewFile() {

        fileOperation.newFile();
        repeatList.clearRepeatList();
        repeatList.addAllTodayList(WordLocalData.getInstance().getWordsToRepeat());
        refreshWordNumberLabel();
        askTextArea.clear();
        answerTextArea.setEditable(false);
    }

    @FXML
    public void handleOpenFile() {

        try {

            fileOperation.openFile(mainBorderPane);

            // the repeat list is cleared to avoid stacking words from different files during open operation
            repeatList.clearRepeatList();
        } catch (NullPointerException exception) {

            return;
        }

        loadRepeatList();
    }

    @FXML
    public void handleSaveFile() {

        try {

            fileOperation.saveFile(mainBorderPane);
        } catch (NullPointerException exception) {

            return;
        }
    }

    @FXML
    public void handleSaveFileAs() {

        try {

            fileOperation.saveFileAs(mainBorderPane);
        } catch (NullPointerException exception) {

            // handle NullPointerException when user choose "cancel" option in the file chooser window.
            return;
        }
    }

    @FXML
    public void handleImportWords() {

        try {

            fileOperation.importWord(mainBorderPane);
        } catch (NullPointerException exception) {

            // handle NullPointerException when user choose "cancel" option in the file chooser window.
            return;
        }
    }

    @FXML
    public void handleExit() {

        // Temporary implementation of handling exit operation. It should ask user about saving file before closing our app.
        Platform.exit();
    }

    @FXML
    public void handleListEditor() {

        wordsToRepeatTemp.setAll(WordLocalData.getInstance().getWordsToRepeat());

        Stage window = new Stage();
        window.setTitle("List Editor");
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(mainBorderPane.getScene().getWindow());
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/list-editor-scene.fxml"));
            Scene stage = new Scene(root, 1000, 800);
            window.setScene(stage);
        } catch (IOException exception) {

            exception.printStackTrace();
            return;
        }
        // this will show the window
        window.show();
        // now after closing the window it will refresh our list with words
        window.setOnHiding(e -> {
            closeEditListWindowAction();
            if (repeatList.getNextTodayWord() != null) {
                askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn());
            }
            answerTextArea.clear();
            requestFocusAnswerArea();
        });
    }

    @FXML
    public void handleKeyPressAnswerArea(KeyEvent keyEvent) {

        if (repeatList.getNextTodayWord() != null &&
                keyEvent.getCode().equals(KeyCode.ENTER)) {

            counterEnterPressedAnswerArea++;

            if (!enterPressedAnswerArea) {

                enterPressedAnswerArea = true;
                WordToRepeat word = repeatList.getWordAtIndex(0);

                if (answerTextArea.getText().replace("\n", "").equalsIgnoreCase(word.getTranslation())) {

                    correctAnswerTextArea();
                } else {

                    badAnswerTextArea();
                }
            }
        }

        if (counterEnterPressedAnswerArea > 1) {
            // there is default implementation of pressing enter second time.
            if (wrongAnswer) {

                handleBadButton();
            } else {

                handleGoodButton();
            }

            enterPressedAnswerArea = false;
            keyEvent.consume();
        }

        if (keyEvent.getCode().equals(KeyCode.F1) && buttonsHBox.isVisible()) {

            enterPressedAnswerArea = false;
            handleBadButton();
        }
        if (keyEvent.getCode().equals(KeyCode.F2) && buttonsHBox.isVisible()) {

            enterPressedAnswerArea = false;
            handleGoodButton();
        }
        if (keyEvent.getCode().equals(KeyCode.F3) && buttonsHBox.isVisible()) {

            enterPressedAnswerArea = false;
            handleVeryGoodButton();
        }
    }

    @FXML
    public void handleBadButton() {

        calculateProgressButton("bad");
        manageBadAnswerRepeatList();
    }

    @FXML
    public void handleGoodButton() {

        calculateProgressButton("good");
        manageGoodAnswerRepeatList();
    }

    @FXML
    public void handleVeryGoodButton() {

        calculateProgressButton("veryGood");
        manageGoodAnswerRepeatList();
    }

    private void loadRepeatList() {

        repeatList.addAllTodayList(WordLocalData.getInstance().getWordsToRepeat());
        requestFocusAnswerArea();
    }

    private void initializeNewRepeatList() {

        repeatList = new RepeatList();
    }

    private void initializeFileOperation() {

        fileOperation = new FileOperation();
    }

    private void initializeWordProgress() {

        wordProgress = new WordProgress();
    }

    private void answerBehavior() {

        setVisibilityProgressButtons(false);
        setEditableAnswerArea(true);
        setDefaultStyleAnswerArea();
        setDefaultVariableValues();
        requestFocusAnswerArea();
        autoSave(1);
    }

    private void setVisibilityProgressButtons(boolean value) {

        buttonsHBox.setVisible(value);
    }

    private void setEditableAnswerArea(boolean value) {

        answerTextArea.setEditable(value);
    }

    private void setDefaultStyleAnswerArea() {

        answerTextArea.setStyle("-fx-text-fill: black; -fx-font-weight: normal");
        answerTextArea.clear();
    }

    private void setDefaultVariableValues() {

        enterPressedAnswerArea = false;
        counterEnterPressedAnswerArea = 0;
        wrongAnswer = false;
    }

    private void requestFocusAnswerArea() {

        answerTextArea.requestFocus();
    }

    private void correctAnswerTextArea() {

        answerTextArea.setStyle("-fx-text-fill: green; -fx-font-weight: bold");
        askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn()
                + "\n" + repeatList.getNextTodayWord().getTranslation()
                + "\n" + repeatList.getNextTodayWord().getNote());
        answerObjectVisibility();
    }

    private void badAnswerTextArea() {

        wrongAnswer = true;
        answerTextArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn()
                + "\n" + repeatList.getNextTodayWord().getTranslation()
                + "\n" + repeatList.getNextTodayWord().getNote());
        answerObjectVisibility();
    }

    private void answerObjectVisibility() {

        setVisibilityProgressButtons(true);
        setEditableAnswerArea(false);
    }

    private void clearTextAreas() {

        answerTextArea.clear();
        askTextArea.clear();
    }

    private void refreshWordNumberLabel() {

        if (repeatList.getNextTodayWord() != null) {

            if (repeatList.getTodayList().size() > 0) {

                wordRepeatNumberLabel.setText("Words: " + repeatList.getTodayList().size()
                        + "(" + repeatList.getWrongAnswerList().size() + ")");
            } else if (repeatList.getTodayList().size() == 0
                    && repeatList.getWrongAnswerList().size() > 0) {

                wordRepeatNumberLabel.setText("Words: " + repeatList.getWrongAnswerList().size());
            }

        } else {

            wordRepeatNumberLabel.setText("Words: 0");
        }
    }

    private void shuffleRepeatList() {

        if (repeatList.getTodayList().size() > 0) {

            repeatList.shuffleTodayList();
        } else {

            repeatList.shuffleWrongAnswerList();
        }
    }

    private void calculateProgressButton(String button) {

        if (repeatList.getNextTodayWord() != null) {

            wordProgress.calculate(repeatList.getWordAtIndex(0), button);
            answerBehavior();
        }
    }

    private void manageBadAnswerRepeatList() {

        if (repeatList.getTodayList().size() != 0) {

            repeatList.addWordWrongAnswerList(repeatList.getWordAtIndex(0));
            repeatList.removeWordAtIndex(0);
            refreshWordNumberLabel();
        }

        shuffleRepeatList();
    }

    private void manageGoodAnswerRepeatList() {

        if (repeatList.getNextTodayWord() != null) {


            repeatList.removeWordAtIndex(0);
            refreshWordNumberLabel();

            /**
             *  If after removing the object the list is empty it will lock the answer area
             */
            if (repeatList.getNextTodayWord() != null) {

                shuffleRepeatList();
                System.out.println("GOOD ANSWER - NEW WORD INDEX 0: " + repeatList.getWordAtIndex(0).getWordToLearn());
            } else {

                setEditableAnswerArea(false);
                setVisibilityProgressButtons(false);
                clearTextAreas();
                wordRepeatNumberLabel.setText("Words: 0");
            }
        }
    }

    private void ifNewWordAddNewWordRepeatList() {

        for(WordToRepeat item : WordLocalData.getInstance().getWordsToRepeat()) {

            if(wordsToRepeatTemp.size() != 0
                    && item.getWordId() > wordsToRepeatTemp.get(wordsToRepeatTemp.size()-1).getWordId()) {

                repeatList.addWordTodayList(item);
            } else if (wordsToRepeatTemp.size() == 0) {
                repeatList.addWordTodayList(item);
            }
        }
    }

    /**
     *  Super simple method for auto save. If autoSaveCounter variable equals 5 it will save the file.
     *  Better version of this should have created new thread that count the number of answers and saves
     *  the file every time it counts the number we had defined.
     * @param increment - it tells how much it needs to increment autoSaveCounter variable
     */
    private void autoSave(int increment) {

        autoSaveCounter += increment;
        if (autoSaveCounter == 5) {
            fileOperation.saveFile(mainBorderPane);
            autoSaveCounter = 0;
            System.out.println("autoSave: Auto save operation DONE!");
        }
    }

    private void ifChangeUpdateRepeatList() {

        //TODO NEXT THING TO DO. METHOD's NAME DESCRIBES IT
        //TODO THE PROGRAM STILL REQUIRES IMPLEMENTATION OF THE CASE WHEN WE DELETE ITEMS FROM THE LIST
    }

    /**
     * Ok fuck this shit.
     * For now this will be the method used after closing the edit list window.
     * The method simply clearing and refilling again the repeat list.
     */
    private void closeEditListWindowAction() {

        repeatList.clearRepeatList();
        repeatList.addAllTodayList(WordLocalData.getInstance().getWordsToRepeat());
        refreshWordNumberLabel();
    }

    private void addListenerRepeatList() {

        repeatList.getTodayList().addListener(new ListChangeListener<WordToRepeat>() {
            @Override
            public void onChanged(Change<? extends WordToRepeat> change) {

                try {

                    while (change.next()) {

                        if (repeatList.getNextTodayWord() != null) {

                            if (change.wasAdded() || change.wasUpdated() || change.wasReplaced()) {

                                refreshWordNumberLabel();
                                setEditableAnswerArea(true);
                                askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn());
                            } else if (change.wasRemoved()) {

                                askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn());
                            }
                        }
                    }
                } catch (UnsupportedOperationException exception) {

                }

            }
        });

        repeatList.getWrongAnswerList().addListener(new ListChangeListener<WordToRepeat>() {
            @Override
            public void onChanged(Change<? extends WordToRepeat> change) {

                try {

                    while (change.next()) {

                        if (repeatList.getNextTodayWord() != null
                                && repeatList.getTodayList().size() == 0) {

                            if (change.wasAdded() || change.wasUpdated()) {

                                askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn());
                            } else if (change.wasRemoved()) {

                                askTextArea.setText(repeatList.getNextTodayWord().getWordToLearn());
                            }
                        }
                    }
                } catch (UnsupportedOperationException exception) {

                }

            }
        });

    }


}

