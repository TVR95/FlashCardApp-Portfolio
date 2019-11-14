package Program;

import Program.datamodel.WordToRepeat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class EditWordController {
    @FXML
    private TextArea wordToLearn;
    @FXML
    private TextArea translation;
    @FXML
    private TextArea note;
    @FXML
    private Button doneButton;
    @FXML
    private Button cancelButton;

    private WordToRepeat word;

    @FXML
    public void handleDoneButton() {
        word.setWordToLearn(wordToLearn.getText());
        word.setTranslation(translation.getText());
        word.setNote(note.getText());
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCancelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setText(WordToRepeat word) {
        this.word = word;
        this.wordToLearn.setText(word.getWordToLearn());
        this.translation.setText(word.getTranslation());
        this.note.setText(word.getNote());
    }

    public String getWordToLearn() {
        if (wordToLearn.getText() != null)
            return wordToLearn.getText();
        else
            return "";
    }

    public String getTranslation() {
        if (translation.getText() != null)
            return translation.getText();
        else
            return "";
    }

    public String getNote() {
        if (note.getText() != null)
            return note.getText();
        else
            return "";
    }
}
