/* The program requires changes in order to be more friendly for the user.
   This stage probably will be a base for the stage where user will be able to edit list and do other cool stuff.
   Main stage should be a window where we can choose the list of words we want to repeat or already be
   the stage where we will be repeating words.
 */
/*
 * Program still has a problem with saving where instead of inserting TAB
 * the saving method is inserting WHITE SPACES what causes the problem
 * during the loading of file. It's a major problem and needs to be tested out.
 * Using ENTER is probably the problem what causes the problems with saving properly the FILE.
 * */
/*
 * Implementing AUTO-SAVE would be GREAT!!!
 * */
/*
 * UNDO and REDO will be great to have but it probably can be implemented only into word list editor as the importing
 * words will be the most UNDO-ing thing.
 * */
/*
 * Importing words have a major problem with PATH as it saves the path to the imported file instead of file into which
 * words are imported.
 * */

package Program;

import Program.datamodel.FileOperation;
import Program.datamodel.WordLocalData;
import Program.datamodel.WordToRepeat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class ListEditorController {

    private FileOperation fileOperation;

    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private VBox textAreaVBox;
    @FXML
    private TextArea wordTextArea, translationTextArea, noteTextArea;
    @FXML
    private ListView<WordToRepeat> wordListView;
    @FXML
    private ContextMenu listContext;
    @FXML
    private Button saveChange, doneButton;
    @FXML
    private ToggleButton editModeToggleButton;
    @FXML
    private HBox toolBarMenu;

    public void initialize() {

        initFileOperation();
        initWordListListener();
        cellFactoryListView(wordListView);
        fillAndSetSelectionModeList(wordListView);
        initListContext();
    }

    @FXML
    public void saveFile() {

        fileOperation.saveFile(mainBorderPane);
    }

    @FXML
    public void saveFileAs() {

        fileOperation.saveFileAs(mainBorderPane);
    }

    @FXML
    public void openFile() {

        fileOperation.openFile(mainBorderPane);

        refreshWordList();
        wordListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void importWords() {

        fileOperation.importWord(mainBorderPane);
        refreshWordList();
        wordListView.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleAddWord() throws IOException {

        if (WordLocalData.getInstance().getWordsToRepeat() != null) {

            Stage window = new Stage();
            window.setTitle("Add new words");
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner(mainBorderPane.getScene().getWindow());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/new-word-scene.fxml"));
                Scene stage = new Scene(root, 1000, 800);
                window.setScene(stage);
            } catch (IOException exception) {
                exception.printStackTrace();
                return;
            }
            window.show();
            window.setOnHiding(event -> wordListView.getSelectionModel().selectFirst());
        } else {

            return;
        }
    }

//    @FXML
//    public void handleEditWord() {
//
//        if (focusedListItem() != null) {
//            ListView<WordToRepeat> focusedList = focusedListView();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit-word-scene.fxml"));
//            try {
//                loader.load();
//            } catch (IOException exception) {
//                exception.getMessage();
//                return;
//            }
//            Stage window = new Stage();
//            window.setTitle("Edit Word");
//            window.initModality(Modality.WINDOW_MODAL);
//            window.initOwner(mainBorderPane.getScene().getWindow());
//            Parent root = loader.getRoot();
//            Scene stage = new Scene(root, 1000, 800);
//            window.setScene(stage);
//            EditWordController controller = loader.getController();
//            WordToRepeat item = focusedListItem();
//            controller.setText(item);
//            window.show();
//            window.setOnHiding(e -> {
//                refreshAskListAndAddNewItem();
//                refreshRepeatedList();
//                cellFactoryListView(focusedList);
//                focusedList.getSelectionModel().select(item);
//            });
//        } else {
//            return;
//        }
//    }

    @FXML
    public void handleResetProgress() {

        if (focusedListItem() != null) {

            WordToRepeat word = focusedListItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reset progress of the word");
            alert.setHeaderText(word.getWordToLearn());
            alert.setContentText("The progress will be reset");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {

                word.setLearningProgress(0);
                word.setNextRepeatDate(LocalDate.now());
                word.setCorrectRepeatNumber(0);
                word.setRepeatNumber(0);
            }
            refreshWordList();
        } else {

            return;
        }
    }

    @FXML
    public void handleDeleteWord() {

        if (focusedListItem() != null) {

            WordToRepeat word = focusedListItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete word");
            alert.setHeaderText(word.getWordToLearn());
            alert.setContentText("The word will be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {

                WordLocalData.getInstance().deleteWord(word);
            }
            refreshWordList();
        } else {

            return;
        }
    }

    @FXML
    public void handleResetEntireProgress() {

        if (WordLocalData.getInstance().getWordsToRepeat() != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reset entire progress");
            alert.setHeaderText("Are you sure?");
            alert.setContentText("Entire progress will be reset");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {

                for (WordToRepeat item : WordLocalData.getInstance().getWordsToRepeat()) {
                    item.setLearningProgress(0);
                    item.setNextRepeatDate(LocalDate.now());
                    item.setRepeatNumber(0);
                    item.setCorrectRepeatNumber(0);
                }
            }
            refreshWordList();
        } else {

            return;
        }
    }

    public void handleChangeEditMode() {

        if(editModeToggleButton.isSelected()) {

            saveChange.setVisible(true);
            wordTextArea.setEditable(true);
            translationTextArea.setEditable(true);
            noteTextArea.setEditable(true);
        } else {

            saveChange.setVisible(false);
            wordTextArea.setEditable(false);
            translationTextArea.setEditable(false);
            noteTextArea.setEditable(false);
        }
    }

    public void handleSaveChange() {

        WordToRepeat word = wordListView.getSelectionModel().getSelectedItem();

        for(WordToRepeat item : WordLocalData.getInstance().getWordsToRepeat()) {

            if(item.getWordId() == word.getWordId()) {

                item.setWordToLearn(wordTextArea.getText());
                item.setTranslation(translationTextArea.getText());
                item.setNote(noteTextArea.getText());
            }
        }
    }

    public void handleDoneButton() {

        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    private void initFileOperation() {

        fileOperation = new FileOperation();
    }

    private void initWordListListener() {

        wordListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WordToRepeat>() {
            @Override
            public void changed(ObservableValue<? extends WordToRepeat> observable, WordToRepeat oldValue, WordToRepeat newValue) {

                if (newValue != null) {

                    WordToRepeat item = wordListView.getSelectionModel().getSelectedItem();
                    wordTextArea.setText(item.getWordToLearn());
                    translationTextArea.setText(item.getTranslation());
                    noteTextArea.setText(item.getNote());

                    if(editModeToggleButton.isSelected()){

                        wordTextArea.setEditable(true);
                        translationTextArea.setEditable(true);
                        noteTextArea.setEditable(true);
                    }
                } else if (newValue == null) {

                    wordTextArea.clear();
                    translationTextArea.clear();
                    noteTextArea.clear();
                    wordTextArea.setEditable(false);
                    translationTextArea.setEditable(false);
                    noteTextArea.setEditable(false);
                }
            }
        });
    }

    private void fillAndSetSelectionModeList(ListView<WordToRepeat> listView) {

        listView.setItems(WordLocalData.getInstance().getWordsToRepeat());
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
    }

    private void initListContext() {

        listContext = new ContextMenu();
        MenuItem deleteWord = new MenuItem("Delete");
        MenuItem resetProgress = new MenuItem("Reset progress");
        listContext.getItems().addAll(deleteWord, resetProgress);

        deleteWord.setOnAction((ActionEvent ae) -> {
            handleDeleteWord();
        });

        resetProgress.setOnAction((ActionEvent ae) -> {
            handleResetProgress();
        });
    }

    private void refreshWordList() {

        SortedList<WordToRepeat> sortedList = new SortedList<>(wordListView.getItems());
        wordListView.setItems(sortedList);
    }

    private void cellFactoryListView(ListView<WordToRepeat> list) {

        list.setCellFactory(new Callback<ListView<WordToRepeat>, ListCell<WordToRepeat>>() {
            @Override
            public ListCell<WordToRepeat> call(ListView<WordToRepeat> param) {
                ListCell<WordToRepeat> cell = new ListCell<WordToRepeat>() {
                    @Override
                    protected void updateItem(WordToRepeat item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {

                            setText(null);
                        } else {

                            setText(item.getWordToLearn());
                        }
                    }
                };
                cell.emptyProperty().addListener(

                        (obs, wasEmpty, isNowEmpty) -> {

                            if (isNowEmpty)
                                cell.setContextMenu(null);
                            else
                                cell.setContextMenu(listContext);
                        }
                );
                return cell;
            }
        });
    }

    private WordToRepeat focusedListItem() {

        if (wordListView.isFocused()) {

            return wordListView.getSelectionModel().getSelectedItem();
        } else {

            return null;
        }
    }

    private ListView<WordToRepeat> focusedListView() {

        if (wordListView.isFocused()) {

            return wordListView;
        } else {

            return null;
        }
    }
}
