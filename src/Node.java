import java.util.ArrayList;

public class Node
{
  int levDist;
  private String word;
  private int wordLength;
  ArrayList<Node> shorter = new ArrayList<>();
  ArrayList<Node> longer = new ArrayList<>();
  ArrayList<Node> equal = new ArrayList<>();

  public Node(String word)
  {
    this.word = word;
    this.wordLength = word.length();
  }

  public String getWord()
  {
    return this.word;
  }

  public int getWordLength()
  {
    return this.wordLength;
  }

  public void addLonger(Node node)
  {
    this.longer.add(node);
  }

  public void addShorter(Node node)
  {
    this.shorter.add(node);
  }

  public void addEqual(Node node)
  {
    this.equal.add(node);
  }


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
        return false;
    }
    return true;
  }

  public static boolean oneSwapFrom(String word1, String word2)
  {
    if(word1.equals(word2))
      return false;

    int check = 0;
    int wordLength = word1.length();
    for(int i = 0; i < wordLength; i++){
      if(word1.charAt(i) != word2.charAt(i))
        check++;
      if(check > 1)
        return false;
    }
    return true;

  }




  public static void main(String args[]){
    Node node1 = new Node("banana");
    Node node2 = new Node("ffnana");
    //System.out.println(node1.oneSwapFrom(node2));
  }



}
