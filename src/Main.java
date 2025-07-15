import java.io.*;
import java.util.Map;

public class Main {

    // Values that you want to save:
    static String[] forSaveValues = {"CPF","NOME","DDDCEL1","CEL1","DDDCEL2","CEL2"};
    // Folder Path
    static String folderPath = (new File(System.getProperty("user.dir")).getParentFile()).getAbsolutePath() + "\\subjects";
    // A Map to link the data we want to concat
    static Map<Integer, Integer> concat = Map.of(2,3,4,5);

    // -----------------------------------------------------------------------

    public static void main(String[] args) throws IOException {
        // Store the files in a variable
        File[] filerInSubject = new File(folderPath).listFiles();

        // Check if there are a file in the folder
        assert filerInSubject != null;

        // Runs for every file name in "fileNames" variable
        for (File file : filerInSubject){

            String[][] filteredData = Worker.filterData(forSaveValues,file,concat);

            Printer pw = new Printer(file.getName(),filteredData,folderPath);
            Printer.print(pw);
        }
    }
}