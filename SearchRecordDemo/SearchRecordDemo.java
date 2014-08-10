import comp102x.IO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class SearchRecordDemo
{   
    // for loading OCR libraries
    private static Loader loader = new Loader();
    
    /**
     * Askes user for an input word and searches the word from a record.
     * Prints whether the word exist in the record or not.
     * 
     */
    public void searchWord() throws IOException
    {
        // Input from console
       //String word = IO.inputString();
        
        // Input from image by performing Optical Character Recognition(OCR)
        
        String word = IO.inputTextImage();
        word = removeExtraSpace(word);
        IO.outputln(word);
        
        
        boolean exist = searchFromRecord("record.txt", word);
         
        if (exist) {
            
            System.out.println("The word \"" + word + "\" is in the record.");
            
        } else {
            
            System.out.println("The word \"" + word + "\" is not in the record.");
            
        }
    }
    
    /**
     * Removes the extra spaces from a word.
     * 
     * @param word The String to be processed.
     */
    private String removeExtraSpace(String word)
    {
        return word.replace("\n", "").replace("[ ]{2, }", "");
    }
    
    /**
     * Searches the record and returns if a specified word is in the record.
     * 
     * @param recordName The name of the record text file.
     * @param word The word to be searched.
     * @return true if the word presents in the record, false otherwise.
     */
    private boolean searchFromRecord(String recordName, String word) throws IOException
    {
        // Please write your code after this line
        File record = new File(recordName);
        Scanner scanner = new Scanner(record);

        String line;

        while (scanner.hasNextLine()) {

            line = scanner.nextLine();
            
        if (line.equals(word)) {
                scanner.close();
                return true;
        }            
    }

    scanner.close();
    return false;
    }
}
