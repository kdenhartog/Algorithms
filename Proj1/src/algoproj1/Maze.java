/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj1;

/**
 *
 * @author kyle
 */
import java.util.*;

public class Maze {

    private static final int right = 0;
    private static final int down = 1;
    private static final int left = 2;
    private static final int up = 3;
    private static Random randomGenerator;  // for random numbers
    
    public static int Size;    
    
    public static class Point {  // a Point is a position in the maze

        public int x, y;
        public boolean visited;   // for DFS
        public Point parent;      // for DFS

        // Constructor
        public Point(int x, int y) {
            this.x = x;
	        this.y = y;
        }

        public void copy(Point p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
    
    public static class Edge { 
	   // an Edge is a link between two Points: 
	   // For the grid graph, an edge can be represented by a point and a direction.
	   Point point;
	   int direction;
	   boolean used;     // for maze creation
	   boolean deleted;  // for maze creation
	
	   // Constructor
	   public Edge(Point p, int d) {
               this.point = p;
	       this.direction = d;
	       this.used = false;
	       this.deleted = false;
       }
    }
    
 

    // A board is an SizexSize array whose values are Points                                                                                                           
    public static Point[][] board;
    
    // A graph is simply a set of edges: graph[i][d] is the edge 
    // where i is the index for a Point and d is the direction 
    public static Edge[][] graph;
    public static int N;   // number of points in the graph
    public static int[] Up;
    public static int[] Rank;
    
    public static void displayInitBoard() {
        System.out.println("\nInitial Configuration:");

        for (int i = 0; i < Size; ++i) {
            System.out.print("    -");
            for (int j = 0; j < Size; ++j) System.out.print("----");
            System.out.println();
            if (i == 0) System.out.print("Start");
            else System.out.print("    |");
            for (int j = 0; j < Size; ++j) {
                if (i == Size-1 && j == Size-1)
		    System.out.print("    End");
                else System.out.print("   |");
            }
            System.out.println();
        }
        System.out.print("    -");
        for (int j = 0; j < Size; ++j) System.out.print("----");
        System.out.println();
    }
    
    public static Edge CreateMaze(Edge S, Edge E){
        while(!S.used){
            int p = randomGenerator.nextInt(N);
            int d = randomGenerator.nextInt(3);
            int u = findWithPC(p);
            int v = findWithPC(d);
            if (u != v){
                
            }
        }
        return E;
    }
    public static void union(int[] up, int x, int y) {
        if(-1 == x){
            if(-1 == y){
                up[x] = y;
            }
        }    
    }
    
    public static int findWithPC(int i){
        int r = i;
        while (Up[r] != -1){
            r = Up[r];
            if(i != r){
                int k = Up[1];
                while (k != r){
                    Up[1] = k;
                    i = k;
                    k = Up[k];
                }
            } 
        }
        return r;
    }
    
    public void unionRank(int i,int j){
        int ri = Rank [i];
        int rj = Rank [j];
        if (ri<rj) {
            Up[i] = j;
        } if (ri > rj) {
            Up[j] = 1;
        }
    }
    
    public static void main(String[] args) {
         
    	// Read in the Size of a maze
	    Scanner scan = new Scanner(System.in);         
	    try {	     
	        System.out.println("What's the size of your maze? ");
	        Size = scan.nextInt();
	    }
	    catch(Exception ex){
	        ex.printStackTrace();
	    }
	    scan.close();

         
	    // Create one dummy edge for all boundary edges.
	    Edge dummy = new Edge(new Point(0, 0), 0);
	    dummy.used = true;
	    dummy.point.visited = true;
	     
	    // Create board and graph.
	    board = new Point[Size][Size];
	    N = Size*Size;  // number of points
	    graph = new Edge[N][4];         
	     
	    for (int i = 0; i < Size; ++i) 
		  for (int j = 0; j < Size; ++j) {
		    Point p = new Point(i, j);
		    int pindex = i*Size+j;   // Point(i, j)'s index is i*Size + j
		     
		    board[i][j] = p;
		     
		    graph[pindex][right] = (j < Size-1)? new Edge(p, right) : dummy;
		    graph[pindex][down] = (i < Size-1)? new Edge(p, down) : dummy;        
		    graph[pindex][left] = (j > 0)? graph[pindex-1][right] : dummy;         
		    graph[pindex][up] = (i > 0)? graph[pindex-Size][down] : dummy;

		}
	    
	    displayInitBoard();
         
	    // Hint: To randomly pick an edge in the maze, you may 
	    // randomly pick a point first, then randomly pick
	    // an edge associated with the point.
	    randomGenerator = new Random();
	    int i = randomGenerator.nextInt(N);
	    System.out.println("\nA random number between 0 and " + (N-1) + ": " + i);

    }
}
