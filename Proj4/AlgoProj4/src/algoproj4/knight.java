/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj4;

/**
 *
 * @author kyle
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * The knight class provides a static main method to read the dimensions of a
 * board and print a solution of the knight tour.
 *
 * See <a href="http://en.wikipedia.org/wiki/Knight%27s_tour">Wikipedia:
 * Knight's tour</a> for more information.
 *
 * The algorithm employed is similar to the standard backtracking
 * <a href="http://en.wikipedia.org/wiki/Eight_queens_puzzle">eight queens
 * algorithm</a>.
 *
 */
public class knight {

    static long recurCalls = 0;      // number of recursive calls to solve()
    static final int[][] direction = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
    static int M = 4;
    static int N = 4;
    static int[][] Board;

    private static BufferedReader read;

    static boolean solve(int step, int i, int j) {
        // recursive backtrack search.
        recurCalls++;
        Board[i][j] = step;

        if (step == N * M) {
            return true;  // all positions are filled
        }
        for (int k = 0; k < 8; k++) {
            int i1 = 8, j1 = 8;
            if (i + direction[k][0] <= i1){
                i1 = i + direction[k][0];
            }
            if (j + direction[k][1] <= j1){
                j1 = j + direction[k][1];
            }
            if (0 <= i1 && i1 < N && 0 <= j1 && j1 < M && Board[i1][j1] == 0) {
                if (solve(step + 1, i1, j1)) {
                    return true;
                }
            }
        }

        Board[i][j] = 0; // no more next position, reset on backtrack
        return false;
    }

    static void printBoard(int[][] solution) {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; j++) {
                System.out.print("------");
            }
            System.out.println("-");
            for (int j = 0; j < M; ++j) {
                System.out.format("| %3d ", solution[i][j]);
            }
            System.out.println("|");
        }
        for (int j = 0; j < M; j++) {
            System.out.print("------");
        }
        System.out.println("-");
    }

    /*
     * read in the dimensions of Knight's tour board and try to find one.  
     */
    public static void main(String[] args) {
        read = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Please enter the number of rows : ");
            N = Integer.parseInt(read.readLine());

            System.out.print("Please enter the number of columns : ");
            M = Integer.parseInt(read.readLine());
        } catch (Exception ex) {
            System.err.println("Error: " + ex);
            ex.printStackTrace();
        }
        if (N > M) {
            int i = N;
            N = M;
            M = i;
        }

        // create Board and set each entry to 0
        Board = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                Board[i][j] = 0;
            }
        }

        if (solve(1, 0, 0)) {
            printBoard(Board);
        } else {
            System.out.println("No tour was found.");
        }

        System.out.println("Number of recursive calls = " + recurCalls);
    }

}
