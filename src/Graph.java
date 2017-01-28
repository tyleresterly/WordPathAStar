/*************************************************************/
// Tyler Esterly

// This class contains variables and methods used to build a
// graph of nodes containing Strings.
/*************************************************************/



import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.nio.file.*;


public class Graph
{
  static final int MAX_WORD_LENGTH = 15;
  HashMap<Integer, ArrayList<Node>> graph = new HashMap<>();



  /***********************************************/
  // Inputs:
  //  String - dictionary: name of the dictionary file
  //           to be used
  //
  // Constructor for the Graph class, also creates
  // a number of lists based on whatever the maximum word
  // length is set to be
  /**********************************************/

  public Graph(String dictionary)
  {
    for(int i = 1; i < MAX_WORD_LENGTH + 1; i++)
    {
      ArrayList<Node> wordList = new ArrayList<>();
      graph.put(i, wordList);
    }
    readDictionary(dictionary);


  }

  /***********************************************/
  // Inputs:
  //  String - dictionary: name of the dictionary file
  //           to be used
  //
  // Reads all the Strings from the given dictionary
  // file
  /**********************************************/
  public void readDictionary(String dictionary)
  {

    Path file = Paths.get(dictionary);
    try(InputStream in = Files.newInputStream(file))
    {
      Scanner sc = new Scanner(in);

      String word;
      while (sc.hasNext())
      {
        word = sc.next().toLowerCase();
        Node newNode = new Node(word);
        this.graph.get(word.length()).add(newNode);
      }
    }
    catch(IOException ex)
    {
      System.out.println(ex);
    }

  }

  /***********************************************/
  // Inputs:
  //  String - word1/word2: Strings to be compared
  //
  // compares two string and sees if they are a delete
  // away from each other. Can also be used in reverse
  // to see if Strings are one add from each other.
  /**********************************************/

  public static boolean oneDeleteFrom(String word1, String word2)
  {
    if(word1.equals(word2))
      return false;

    int offset = 0;
    int wordLength = word2.length();
    //System.out.println(word1 + ", " + word2);
    for(int i = 0; i < wordLength; i++){
      if(word1.charAt(i + offset) != word2.charAt(i))
      {
        offset++;
        i--;
      }
      if(offset > 1)
      {
        return false;
      }
    }
    return true;
  }

  /***********************************************/
  // Inputs:
  //  String - word1/word2: Strings to be compared
  // Outputs:
  //  Boolean - True if the strings are a swap from
  //            eachother
  // compares two string and sees if they are a swap
  // away from each other.
  /**********************************************/
  public static boolean oneSwapFrom(String word1, String word2)
  {
    if(word1.equals(word2))
      return false;

    int check = 0;
    int wordLength = word1.length();
    for(int i = 0; i < wordLength; i++){
      if(word1.charAt(i) != word2.charAt(i))
      {
        check++;
      }
      if(check > 1)
      {
        return false;
      }
    }
    return true;

  }


  /***********************************************/
  // Prints every word contained in the graph,
  // as well as it's neighbors
  /**********************************************/
  public void printGraph()
  {
    for (ArrayList<Node> list : this.graph.values())
    {
      for (int i = 0; i < list.size(); i++)
      {
        System.out.println("Word: " + list.get(i).getWord());
        for (int j = 0; j < list.get(i).shorter.size(); j++)
        {
          System.out.print(list.get(i).shorter.get(j).getWord() + ", ");
        }
        System.out.println();

        for (int j = 0; j < list.get(i).equal.size(); j++)
        {
          System.out.print(list.get(i).equal.get(j).getWord() + ", ");
        }
        System.out.println();

        for (int j = 0; j < list.get(i).longer.size(); j++)
        {
          System.out.print(list.get(i).longer.get(j).getWord() + ", ");
        }
        System.out.println("\n---------------");
      }
    }
  }

  /***********************************************/
  // Prints the number of words of all sizes.
  /**********************************************/
  public void printSizes()
  {
    int i = 1;
    for(ArrayList<Node> list : this.graph.values())
    {
      System.out.println("Words of Length " + i + ": " + list.size());
      i++;
    }
  }

  /***********************************************/
  // Inputs:
  //  String - word: Strings whose existent is being
  //                 questioned
  // Outputs:
  //  Boolean - True if the string is contained in the//
  //            dictionary
  // Checks whether or not the given string exists
  // in the dictionary
  /**********************************************/
  public boolean checkForWord(String word)
  {
    int wordSize = word.length();
    for(Node node : graph.get(wordSize))
    {
      if (node.getWord().equals(word))
        return true;
    }
    return false;

  }
}
