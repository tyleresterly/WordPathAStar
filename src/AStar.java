/*************************************************************/
// Tyler Esterly

// This class contains all the methods and variables used for
// calculating the shortest path between two needs using the
// A* pathfinding algorithm
/*************************************************************/



import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashSet;
public class AStar
{
  private Graph graph;
  private Comparator<Node> comparator = new nodeComparator();
  private PriorityQueue<Node> frontier = new PriorityQueue<>(comparator);
  private HashSet<Node> closed = new HashSet<>();
  private HashSet<Node> foundNeighbors = new HashSet<>();

  public AStar(String dict)
  {
    graph = new Graph(dict);
  }


  /***********************************************/
  // Inputs:
  //  Strings - lhs,rhs: Strings to calculate the
  //            levenshtein distance for.
  // Outputs:
  //  int - cost: levenshtein distance of the given
  //              strings
  // Calculates the levenshtein distance between
  // two strings, i.e. the minimum number of
  // changes needed to turn String rhs into String lhs
  //
  // NOTE - Algorithm copied directly from
  // https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance
  /**********************************************/
  public static int levenshteinDistance (String lhs, String rhs) {
    int len0 = lhs.length() + 1;
    int len1 = rhs.length() + 1;

    // the array of distances
    int[] cost = new int[len0];
    int[] newcost = new int[len0];

    // initial cost of skipping prefix in String s0
    for (int i = 0; i < len0; i++) cost[i] = i;

    // dynamically computing the array of distances

    // transformation cost for each letter in s1
    for (int j = 1; j < len1; j++) {
      // initial cost of skipping prefix in String s1
      newcost[0] = j;

      // transformation cost for each letter in s0
      for(int i = 1; i < len0; i++) {
        // matching current letters in both strings
        int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

        // computing cost for each transformation
        int cost_replace = cost[i - 1] + match;
        int cost_insert  = cost[i] + 1;
        int cost_delete  = newcost[i - 1] + 1;

        // keep minimum cost
        newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
      }

      // swap cost/newcost arrays
      int[] swap = cost; cost = newcost; newcost = swap;
    }

    // the distance is the cost for transforming all letters in both strings
    return cost[len0 - 1];
  }


  /***********************************************/
  // Inputs:
  //  Node - start: the node to find the path from
  //  Node - dest: the final node in the path
  // Outputs:

  // Uses the A* path-finding algorithm to find the
  // shortest path between two nodes.
  /**********************************************/
  public void findPath(Node start, Node dest)
  {

    frontier.add(start);
    Node current;
    String destWord = dest.getWord();
    while (!frontier.isEmpty())
    {
      current = frontier.poll();
      closed.add(current);

      if (current.getWord().equals(destWord))
      {
        //System.out.print(+ " " current.getWord())
        printWords(current);
        frontier.clear();
        closed.clear();
        return;
      }
      if(!foundNeighbors.contains(current))
      {
        findNeighbors(current);
        foundNeighbors.add(current);
      }

      for(Node neighbor : current.shorter)
      {
        if(!closed.contains(neighbor))
        {
          updateCosts(current, neighbor, current.cost + 1, destWord);
        }
      }
      for(Node neighbor : current.equal)
      {
        if(!closed.contains(neighbor))
        {
          updateCosts(current, neighbor, current.cost + 1, destWord);
        }
      }
      for(Node neighbor : current.longer)
      {
        if(!closed.contains(neighbor))
        {
          updateCosts(current, neighbor, current.cost + 1, destWord);
        }
      }

    }
    System.out.println("NO POSSIBLE PATH: " + start.getWord() + " " + dest.getWord());
    frontier.clear();
    closed.clear();
  }

  /***********************************************/
  // Inputs:
  //  Node - current: the current node in the A*
  //         priority queue.
  // Node - newNode: the node whose total cost is being updated
  // int - cost: The cost of the current node plus the
  //             cost of moving between nodes (1)
  // String - dest: The final string in the path
  //
  // Updates the cost of the inputed node (newNode), determines if
  // it should be added to the priority queue, and updates its
  // levenshtein distance to the destination node.
  /**********************************************/
  private void updateCosts(Node current, Node newNode, int cost, String dest)
  {
    boolean inFrontier = frontier.contains(newNode);
      int finalCost = levenshteinDistance(newNode.getWord(), dest) + cost;
      if(!inFrontier || finalCost < newNode.cost)
      {
        newNode.cost = finalCost;
        newNode.parent = current;
        if(!inFrontier)
        {
          frontier.add(newNode);
        }
      }
  }

  /***********************************************/
  // Inputs:
  //  Node - current: The node whose neighbors need
  //         to be generated
  //
  // Generates and stores all the neighbors for the given
  // node in its respective lists
  /**********************************************/
  public void findNeighbors(Node current)
  {
    int wordLength = current.getWordLength();
    if(wordLength > 1)
    {
      for (Node node : this.graph.graph.get(wordLength - 1))
      {
          if (Graph.oneDeleteFrom(current.getWord(), node.getWord()))
          {
            current.addShorter(node);
            node.addLonger(current);
          }
      }
    }
    for (Node node : this.graph.graph.get(wordLength ))
    {
        if (Graph.oneSwapFrom(current.getWord(), node.getWord()))
        {
          current.addShorter(node);
          node.addLonger(current);
        }
    }
    if(wordLength < Graph.MAX_WORD_LENGTH)
    {
      for (Node node : this.graph.graph.get(wordLength + 1))
      {
          if (Graph.oneDeleteFrom(node.getWord(), current.getWord()))
          {
            current.addShorter(node);
            node.addLonger(current);
          }
      }
    }

  }

  /***********************************************/
  // Inputs:
  //  Node - current: The node whose parents need
  //         to be printed
  //
  // Prints a list of the currents nodes parent
  // grandparents, etc.
  /**********************************************/
  private void printWords(Node current)
  {
    ArrayList<Node> wordPath = new ArrayList<>();
    while(current != null)
    {
      wordPath.add(current);
      current = current.parent;
    }
    for(int i = wordPath.size() - 1; i >= 0; i--)
    {
      System.out.print(wordPath.get(i).getWord() + " ");
    }
    System.out.print("\n");
  }


  /***********************************************/
  // Class that implements the comparator interface
  // to be used in the priority queue
  /**********************************************/

  public class nodeComparator implements Comparator<Node>
  {
    public int compare(Node x, Node y)
    {
      if (x.cost < y.cost)
      {
        return -1;
      }
      if (x.cost > y.cost)
      {
        return 1;
      }
      return 0;
    }
  }

  public static void main(String args[])
  {

    //long startTime = System.currentTimeMillis();
    if(args.length < 3)
    {
      System.out.println("Too few arguments. At least 3 are needed.");
      return;
    }

    String dict = args[0];
    AStar astar = new AStar(dict);

    for(int i = 1; i < args.length; i+=2)
    {
      if(astar.graph.checkForWord(args[i].toLowerCase()) && astar.graph.checkForWord(args[i+1].toLowerCase()))
      {
        Node start = new Node(args[i]);
        Node dest = new Node(args[i + 1]);
        astar.findPath(start, dest);
      }
      else
        System.out.println("Word not found");

    }
    //long endTime   = System.currentTimeMillis();
    //long totalTime = endTime - startTime;

    //System.out.println("Running time is :" + totalTime/1000.0 + " seconds.");

  }
}
