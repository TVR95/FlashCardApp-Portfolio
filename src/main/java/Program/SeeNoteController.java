package Program;

import Program.datamodel.WordToRepeat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SeeNoteController {
    @FXML
    TextArea note;

    public void setText(WordToRepeat word){
        note.setText(word.getNote());
    }
}
