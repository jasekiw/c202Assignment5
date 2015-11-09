import java.io.*;
import trees.*;
/**
 * @author Jason Gallavin
 * @date 11/08/15
 */
public class Program {

    BinarySearchTree[] dictionary;
    long wordsFound = 0;
    long wordsNotFound = 0;
    long compsWordsFound = 0;
    long compsWordsNotFound = 0;

    public static void main(String[] args) {
        new Program().run();
    }

    /**
     * The Main Method that gets the spelling statistics of the document
     */
    void run()
    {
        dictionary = new BinarySearchTree[26];
        for(int i =0; i < dictionary.length; i++)
            dictionary[i] = new BinarySearchTree<String>();
        readDictionaryIntoTrees();
        spellCheck();
        double avgComparisonsFound = compsWordsFound / wordsFound;
        double avgComparisonsNotFound = compsWordsNotFound / wordsNotFound;
        System.out.println("Words Found: " + wordsFound);
        System.out.println("Words Not Found: " + wordsNotFound);
        System.out.println("Average comparisons of Words Found: " + avgComparisonsFound);
        System.out.println("Average comparisons of Words Not Found: " + avgComparisonsNotFound);
        System.out.println("comparisons of Words Found: " + compsWordsFound);
        System.out.println("comparisons of Words Not Found: " + compsWordsNotFound);
    }


    /**
     * Reads the random dictionary into an array of 26 binary trees
     * @requires random_dictionary.txt must exist and be readable
     * @ensures 26 binary trees will be populated with non case sensitive words
     */
    private void readDictionaryIntoTrees()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("random_dictionary.txt")));
            int letter = reader.read();
            String str = "";
            while(letter != -1)
            {
                if(Character.isLetter((char)letter))
                    str+= Character.toUpperCase((char) letter);

                if((Character.isWhitespace((char)letter) || (char)letter == '-') && str.length() > 0)
                {
                    dictionary[(int)str.charAt(0) - 65].insert(str);
                    str = "";
                }
                letter = reader.read();
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1); // could not find file or read from it
        }
    }

    /**
     * Spell checks the document for errors and stores the statistics into the comparison variables
     * @requires oliver.txt must exist and be readable
     * @ensures the document will be spell checked
     * @see this.searchDictionary()
     */
    private void spellCheck()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(new File("oliver.txt")));
            int letter = reader.read();
            String str = "";
            while(letter != -1)
            {
                if(Character.isLetter((char)letter))
                    str+= (char)letter;

                if((Character.isWhitespace((char)letter) || (char)letter == '-') && str.length() > 0)
                {
                    searchDictionary(str);
                    str = "";
                }
                letter = reader.read();
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1); // could not find file or read from it
        }
    }

    /**
     * Searches the dictionary Linked List for a word
     * @requires word to have characters and start with a letter
     * @ensures the word will be counted into the statistics
     * @param word The word to be searched
     */
    private void searchDictionary(String word)
    {
        word = word.toUpperCase();
        int index = ((int)word.charAt(0)) - 65;
        dictionary[index].comparisons = 0;
        if(dictionary[index].search(word))
        {
            compsWordsFound += dictionary[index].comparisons;
            wordsFound++;
        }
        else
        {
            compsWordsNotFound += dictionary[index].comparisons;
            wordsNotFound++;
        }
    }
}
