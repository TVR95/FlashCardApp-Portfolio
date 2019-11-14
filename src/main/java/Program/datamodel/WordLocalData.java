package Program.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class WordLocalData {
    private static WordLocalData instance = new WordLocalData();
    private ObservableList<WordToRepeat> wordsToRepeat = FXCollections.observableArrayList();
    private DateTimeFormatter formatter;
    private String filePath;
    private String importFilePath;

    public static WordLocalData getInstance() {
        return instance;
    }

    private WordLocalData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<WordToRepeat> getWordsToRepeat() {
        return wordsToRepeat;
    }

    public WordToRepeat getWordToRepeat(int index) {
        return wordsToRepeat.get(index);
    }

    public void addWordToRepeat(WordToRepeat word) {
        wordsToRepeat.add(word);
    }

    public void setWordsToRepeat(ObservableList<WordToRepeat> wordsToRepeat) {
        this.wordsToRepeat = wordsToRepeat;
    }

    public void loadWordLocal(Path path) {
        wordsToRepeat.clear();
        loadFile(path);
    }

    public void storeWordLocal(Path path) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            Iterator<WordToRepeat> iter = wordsToRepeat.iterator();
            while (iter.hasNext()) {
                WordToRepeat item = iter.next();
                bw.write(String.format(("%s\t%s\t%s\t%s\t%s\t%d\t%d\t%d"),
                        item.getWordToLearn().replace("\n", "/%n-l%/"),
                        item.getTranslation().replace("\n", "/%n-l%/"),
                        item.getNote().replace("\n", "/%n-l%/"),
                        item.getAddingDate().format(formatter),
                        item.getNextRepeatDate().format(formatter), item.getLearningProgress(),
                        item.getRepeatNumber(), item.getCorrectRepeatNumber()));
                bw.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

    public void newFile() {
        wordsToRepeat.clear();
        // clear the file path to avoid saving a new file in  the place of old one.
        filePath = null;
    }

    public void deleteWord(WordToRepeat word) {
        wordsToRepeat.remove(word);
    }

    public void importWords(Path path) {
        if (wordsToRepeat != null) {
            loadFile(path);
        } else {
            loadWordLocal(path);
        }
    }

    private void loadFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            Integer wordId;
            if(wordsToRepeat.isEmpty()){
                wordId = 1;
            } else {
                wordId = wordsToRepeat.size() + 1;
            }

            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");
                String wordToLearn = itemPieces[0].replace("/%n-l%/", "\n");
                String translation = itemPieces[1].replace("/%n-l%/", "\n");
                String note = itemPieces[2].replace("/%n-l%/", "\n");
                String addingDateString = itemPieces[3];
                String nextRepeatDateString = itemPieces[4];
                String learningProcessString = itemPieces[5];
                String repeatNumberString = itemPieces[6];
                String correctRepeatNumberString = itemPieces[7];

                LocalDate addingDate = LocalDate.parse(addingDateString, formatter);
                LocalDate nextRepeatDate = LocalDate.parse(nextRepeatDateString, formatter);
                Integer learningProcess = Integer.parseInt(learningProcessString);
                Integer repeatNumber = Integer.parseInt(repeatNumberString);
                Integer correctRepeatNumber = Integer.parseInt(correctRepeatNumberString);

                WordToRepeat wordToRepeat = new WordToRepeat(wordToLearn, translation, note,
                        addingDate, learningProcess, nextRepeatDate, repeatNumber, correctRepeatNumber,
                        wordId);
                addWordToRepeat(wordToRepeat);

                wordId++;
            }
        } catch (IOException exception) {
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImportFilePath() {
        return importFilePath;
    }

    public void setImportFilePath(String importFilePath) {
        this.importFilePath = importFilePath;
    }
}
