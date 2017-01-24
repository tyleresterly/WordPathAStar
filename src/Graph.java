import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.nio.file.*;


public class Graph
{
  private final int MAX_WORD_LENGTH = 15;
  private HashMap<Integer, ArrayList<Node>> graph = new HashMap<>();

  public Graph(String dictionary)
  {
    for(int i = 1; i < MAX_WORD_LENGTH + 1; i++)
    {
      ArrayList<Node> wordList = new ArrayList<>();
      graph.put(i, wordList);
    }
    Path file = Paths.get(dictionary);

    try(InputStream in = Files.newInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
    {
      String line;
      while ((line = reader.readLine()) != null)
      {
        Node node = new Node(line);
        this.addNode(node);
      }
    }
    catch(IOException ex)
    {
      System.out.println(ex);
    }

  }

  public void addNode(Node newNode)
  {
    int wordSize = newNode.getWordLength();
    String word1 = newNode.getWord();

    this.graph.get(wordSize).add(newNode);
    if(wordSize != 1)
    {
      for (Node node : graph.get(wordSize - 1))
      {
        String word2 = node.getWord();
        if (newNode.oneDeleteFrom(word1,word2))
        {
          node.addLonger(newNode);
          newNode.addShorter(node);
        }

      }
    }
    for(Node node : graph.get(wordSize))
    {
      String word2 = node.getWord();

      if(Node.oneSwapFrom(word1,word2))
      {
        node.addEqual(newNode);
        newNode.addEqual(node);
      }
    }
    if(wordSize != MAX_WORD_LENGTH)
    {
      for (Node node : graph.get(wordSize + 1))
      {
        String word2 = node.getWord();
        if (Node.oneDeleteFrom(word2,word1))
        {
          node.addShorter(newNode);
          newNode.addLonger(node);
        }
      }
    }

  }

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

  public void printSizes()
  {
    int i = 1;
    for(ArrayList<Node> list : this.graph.values())
    {
      System.out.println("Words of Length " + i + ": " + list.size());
      i++;
    }


  }

  public static void main(String args[]){
    long startTime = System.currentTimeMillis();
    Graph graph = new Graph("dict.txt");
    long endTime   = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    System.out.println("Running time is :" + totalTime/1000.0 + " seconds.");
    graph.printSizes();
  }



}
