/*
 * This class handles I/O of the program.
 * */
package Program.datamodel;

import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperation {

    public FileOperation() {
    }

    public void newFile() {

        WordLocalData.getInstance().newFile();
    }

    public void saveFile(Pane pane) {

        if (WordLocalData.getInstance().getFilePath() != null) {

            Path path = Paths.get(WordLocalData.getInstance().getFilePath());
            try {
                WordLocalData.getInstance().storeWordLocal(path);
            } catch (IOException exception) {

            }

        } else {
            saveFileAs(pane);
        }
    }

    public void saveFileAs(Pane pane) {

        showFileChooser(pane, "RUM - Save File", "save");
        Path path = Paths.get(WordLocalData.getInstance().getFilePath());
        try {
            WordLocalData.getInstance().storeWordLocal(path);
        } catch (IOException exception) {

        }
    }

    public void openFile(Pane pane) {

        showFileChooser(pane, "RUM - Open File", "open");
        Path path = Paths.get(WordLocalData.getInstance().getFilePath());
        WordLocalData.getInstance().loadWordLocal(path);
    }

    public void importWord(Pane pane) {

        showFileChooser(pane, "RUM - Import", "import");
        Path path = Paths.get(WordLocalData.getInstance().getImportFilePath());
        WordLocalData.getInstance().importWords(path);

    }

    private void showFileChooser(Pane pane, String title, String operation) {

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("(*.wrd)", "*.wrd"));
        chooser.setTitle(title);
        File defaultDirectory = new File("src/main/java/Program/wordlists");
        chooser.setInitialDirectory(defaultDirectory);
        if (operation == "save") {
            File selectedDirectory = chooser.showSaveDialog(pane.getScene().getWindow());
            WordLocalData.getInstance().setFilePath(selectedDirectory.getAbsolutePath());
        } else if (operation == "open") {
            File selectedDirectory = chooser.showOpenDialog(pane.getScene().getWindow());
            WordLocalData.getInstance().setFilePath(selectedDirectory.getAbsolutePath());
        } else if (operation == "import") {
            File selectedDirectory = chooser.showOpenDialog(pane.getScene().getWindow());
            WordLocalData.getInstance().setImportFilePath(selectedDirectory.getAbsolutePath());
        }
    }
}
