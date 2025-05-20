import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Printer {

    String fileName;
    String[][] content;
    String directory;

    public Printer(String nm, String[][] cntt, String drct){
        this.fileName = nm;
        this.content = cntt;
        this.directory = drct;
    }

    // A function to print the data to a file
    public static void print(Printer printer) throws FileNotFoundException {
        // Initialize a print writer in our directory with our file name
        PrintWriter pw = new PrintWriter(printer.directory+"\\"+"FILTERED_"+printer.fileName);

        // Print each element separated by ";"
        for (String[] i : printer.content){
            pw.println(String.join(";",i));
        }
    }
}
