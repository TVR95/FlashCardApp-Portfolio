package Program;

import Program.datamodel.WordLocalData;
import Program.datamodel.WordToRepeat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class NewWordController {
    @FXML
    private VBox mainPane;
    @FXML
    private TextArea wordToLearn, translation, note;
    @FXML
    private Button addButton, doneButton;

    @FXML
    public void handleAddWord() {
        String wordText, translationText, noteText;
        LocalDate addingDate = LocalDate.now();
        LocalDate nextRepeatDate = LocalDate.now();
        Integer learningProgress = 0;
        Integer repeatNumber = 0;
        Integer correctRepeatNumber = 0;
        Integer wordId = 0;

        if (WordLocalData.getInstance().getWordsToRepeat().size() > 0) {
            wordId = WordLocalData.getInstance().getWordsToRepeat().
                    get(WordLocalData.getInstance().getWordsToRepeat().size()-1).getWordId();
        }

        if (wordToLearn.getText().isEmpty())
            wordText = "";
        else
            wordText = wordToLearn.getText().trim();

        if (translation.getText().isEmpty())
            translationText = "";
        else
            translationText = translation.getText().trim();

        if (note.getText().isEmpty())
            noteText = "";
        else
            noteText = note.getText().trim();

        WordToRepeat item = new WordToRepeat(wordText, translationText, noteText,
                addingDate, learningProgress, nextRepeatDate, repeatNumber, correctRepeatNumber,
                wordId + 1);
        WordLocalData.getInstance().addWordToRepeat(item);

        wordToLearn.clear();
        translation.clear();
        note.clear();
        wordToLearn.requestFocus();
    }

    @FXML
    public void handleDoneButton() {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleKeyPressedWordArea(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            translation.requestFocus();
    }

    @FXML
    public void handleKeyPressedTranslationArea(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            note.requestFocus();
    }

    @FXML
    public void handleKeyPressedNoteArea(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            handleAddWord();
    }
}
