import java.io.*;
import java.util.*;

public class Worker {

    public static String[][] filterData(String[] neddedData, File file, Map<Integer, Integer> concat) throws IOException {


        // Stores the file name
        String fileName = file.getName();

        // Create a list to store all lines of this file
        List<List<String>> rawData = new ArrayList<>();

        // Create a reader (Basically a connection) to the actual looped file
        BufferedReader br = new BufferedReader(new FileReader(file.getParent()+"\\"+fileName));

        // Create a String that is not initialized, will be init inside the while loop
        String rawLine;

        // Assign the "line" variable to a readded line while there are still lines to read
        while ((rawLine = br.readLine()) != null){

            // Separate every value defined by ";" and store inside "values"
            String[] values = rawLine.split(";");
            rawData.add(Arrays.asList(values));
        }

        // Init an int array that will store the index of values to save inside the "lines"
        int[] indexOfValuesToSave = new int[neddedData.length];

        // Store the first line that are inside "rawData" just for efficiency
        List<String> firstLine = rawData.getFirst();

        // Iterate in every String inside the first list in lines
        for (int i = 0; i < firstLine.size(); i++){
            // Store the value for efficiency
            String actualValue = firstLine.get(i);
            // Iterate in every String inside the "neddedData"
            for (int j = 0; j<neddedData.length; j++){

                // if the actual checked value in the "firstLine" is one of that we want to save
                // store it index in "indexOfValuesToSave"
                if (Objects.equals(actualValue, neddedData[j])){
                    indexOfValuesToSave[j] = i;
                }
            }
        }
        // Init a new list to store our filtered values
        String[][] filteredValues = new String[rawData.size()][neddedData.length];

        // Iterate in every line in "rawData"
        for (int i=0;i<rawData.size();i++){

            // Init an array to store our values that we want to save
            String[] filteredLine = new String[indexOfValuesToSave.length];

            // In every loop go to the index of the actual checked line and
            // store it inside "filteredLine"
            for (int j=0; j< indexOfValuesToSave.length; j++){
                filteredLine[j] = rawData.get(i).get(indexOfValuesToSave[j]);
            }

            // Add the filtered line at "filteredValues"
            filteredValues[i] = filteredLine;
        }

        /* An array to store concatenated values, have the same amount of lines that filtered
           Values have but the amount of columns is reduced by the concat map size */
        String[][] concatenatedValues = new String[filteredValues.length][filteredValues[0].length-concat.size()];

        // Will store the values that are inside the map, we will skip it in the for loop
        List<Integer> skipList = new ArrayList<>();
        concat.forEach((k, v) -> skipList.add(v));
        Integer[] skipColumn = skipList.toArray(new Integer[0]);

        // Will store our key, will be used to check if the column must be concatenated
        List<Integer> keyList = new ArrayList<>();
        concat.forEach((k, v) -> keyList.add(k));
        Integer[] keyColumn = keyList.toArray(new Integer[0]);

        // A loop to iterate in every row
        for (int r=0;r<filteredValues.length;r++){

            // Init a list to temporary store the row
            List<String> concatedRow = new ArrayList<>();

            // A loop to iterate in every column
            for (int c=0;c<filteredValues[0].length; c++){
                // Check if "c" is one of the concated columns, if true will skip this loop
                boolean mustSkip = false;
                for (Integer i : skipColumn){
                    if (i == c) {
                        mustSkip = true;
                        break;
                    }
                }
                if (mustSkip) continue;

                // Check if this column must be concated
                boolean needConcat = false;
                for (Integer i : keyColumn){
                    if (i == c) {
                        needConcat = true;
                        break;
                    }
                }

                // If true, concat the column with the corresponding one in the map
                if (needConcat){
                    String concatedColumn = filteredValues[r][c] + filteredValues[r][concat.get(c)];
                    concatedRow.add(concatedColumn);
                }
                else{
                    concatedRow.add(filteredValues[r][c]);
                }

            }

            concatenatedValues[r] = concatedRow.toArray(new String[0]);

        }

        return concatenatedValues;

    }
}
