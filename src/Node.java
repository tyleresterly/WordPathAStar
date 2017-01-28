/*************************************************************/
// Tyler Esterly

// This class contains variables and methods used to build nodes
// that will be inserted into a graph.
/*************************************************************/


import java.util.ArrayList;

public class Node
{
  int cost = 0;
  Node parent;
  ArrayList<Node> shorter;
  ArrayList<Node> longer;
  ArrayList<Node> equal;
  private String word;
  private int wordLength;

  /***********************************************/
  // Inputs:
  //  String - word: the word that will be
  //                 contained in the node
  //
  // Constructor for the Node class
  /**********************************************/
  public Node(String word)
  {
    this.word = word;
    this.wordLength = word.length();
    shorter = new ArrayList<>();
    longer = new ArrayList<>();
    equal = new ArrayList<>();
  }

  // Returns the node's word
  public String getWord()
  {
    return this.word;
  }
  // Returns the length of the node's contained word
  public int getWordLength()
  {
    return this.wordLength;
  }


  /***********************************************/
  // Inputs:
  //  Node - node: node to be added to the
  //               the list of neighbors
  //
  // Adds a node to the calling node's list of
  // neighbors that are one character larger than
  // itself
  /**********************************************/
  public void addLonger(Node node)
  {
    this.longer.add(node);
  }

  /***********************************************/
  // Inputs:
  //  Node - node: node to be added to the
  //               the list of neighbors
  //
  // Adds a node to the calling node's list of
  // neighbors that are one character shorter than
  // itself
  /**********************************************/
  public void addShorter(Node node)
  {
    this.shorter.add(node);
  }

  /***********************************************/
  // Inputs:
  //  Node - node: node to be added to the
  //               the list of neighbors
  //
  // Adds a node to the calling node's list of
  // neighbors that are of equal length
  /**********************************************/
  public void addEqual(Node node)
  {
    this.equal.add(node);
  }



}
