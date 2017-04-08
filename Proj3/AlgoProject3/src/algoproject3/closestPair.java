/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproject3;

import java.util.*;
import java.lang.*;

/**
 *
 * @author kyle
 */
public class closestPair {

    private static Random randomGenerator;  // for random numbers

    public static class Point implements Comparable<Point> {

        public long x, y;

        // Constructor
        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Point p) {
            // compare this and p and there are three results: >0, ==0, or <0
            if (this.x == p.x) {
                if (this.y == p.y) {
                    return 0;
                } else {
                    return (this.y > p.y) ? 1 : -1;
                }
            } else {
                return (this.x > p.x) ? 1 : -1;
            }
        }

        public String toString() {
            return " (" + Long.toString(this.x) + "," + Long.toString(this.y) + ")";
        }

        public double distance(Point p) {
            long dx = (this.x - p.x);
            long dy = (this.y - p.y);
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    static class CompareY implements java.util.Comparator<Point> {

        public int compare(Point p1, Point p2) {
            if (p1.y < p2.y) {
                return -1;
            } else if (p1.y == p2.y) {
                // Secondary order on x-coordinates
                if (p1.x < p2.x) {
                    return -1;
                } else if (p1.x == p2.x) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }
    }

    public static Point[] plane;

    public static Point[] planeY;
    
    public static Point[] aux;

    public static int N;   // number of points in the plane
    
    public static double minDistance= Double.POSITIVE_INFINITY;
    
    public static Point min1, min2;
    
    public static void main(String[] args) {

        // Read in the Size of a maze
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("How many points in your plane? ");
            N = scan.nextInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        scan.close();

        // Create plane of N points.
        plane = new Point[N];
        randomGenerator = new Random();

        for (int i = 0; i < N; ++i) {
            long x = randomGenerator.nextInt(N << 6);
            long y = randomGenerator.nextInt(N << 6);
            plane[i] = new Point(x, y);
        }
        Arrays.sort(plane); // sort points according to compareTo.
        for (int i = 1; i < N; ++i) // make all x's distinct.
        {
            if (plane[i - 1].x >= plane[i].x) {
                plane[i].x = plane[i - 1].x + 1;
            }
        }
        planeY = plane;
        Arrays.sort(planeY, new CompareY());
        aux = new Point[N];

        System.out.println(N + " points are randomly created.");
        System.out.println("The first two points are" + plane[0]
                + " and" + plane[1]);
        System.out.println("The distance of the first two points is "
                + plane[0].distance(plane[1]));
        
        // Compute the minimal distance of any pair of points by divide-and-conquer
        double min2 = minDisDivideConquer(plane, planeY, aux, 0, N - 1);
        System.out.println("The distance of the two closest points is " + min2);
        // Compute the minimal distance of any pair of points by exhaustive search.
        double min1 = minDisSimple();
        System.out.println("The distance of the two closest points is " + min1);
        
    }

    static double minDisSimple() {
        // A straightforward method for computing the distance 
        // of the two closest points in plane[0..N-1].
        double minDist = -1;
        for (int i = 1; i < N - 1; i++) {
            for (int j = i + 1; j < N; j++) {
                Point p1 = plane[i];
                Point p2 = plane[j];
                double dist = p1.distance(p2);
                if (minDist == -1 || dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist;
    }

    static void exchange(int i, int j) {
        Point x = plane[i];
        plane[i] = plane[j];
        plane[j] = x;
    }
    
    public static void merge(Point[] a, Point[] aux,
            int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
    
        // merge back to a[] 
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0)
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }


    static double minDisDivideConquer(Point[] pointX, Point[] pointY, Point[] aux,
            int low, int high) {

        if (high == low + 1) { // two points
            if (plane[low].y > plane[high].y) {
                exchange(low, high);
            }
            return plane[low].distance(plane[high]);
        } else if (high == low + 2) { // three points
            // sort these points by y-coordinate
            if (plane[low].y > plane[high].y) {
                exchange(low, high);
            }
            if (plane[low].y > plane[low + 1].y) {
                exchange(low, low + 1);
            } else if (plane[low + 1].y > plane[high].y) {
                exchange(low + 1, high);
            }
            // compute pairwise distances
            double d1 = plane[low].distance(plane[high]);
            double d2 = plane[low].distance(plane[low + 1]);
            double d3 = plane[low + 1].distance(plane[high]);
            return ((d1 < d2) ? ((d1 < d3) ? d1 : d3) : (d2 < d3) ? d2 : d3);  // return min(d1, d2, d3)
        } else {  // 4 or more points: Divide and conquer
            if (high <= low) {
                return Double.POSITIVE_INFINITY;
            }
            int mid = low + (high - low) / 2;
            Point median = pointX[mid];

            double dist1 = minDisDivideConquer(pointX, pointY, aux, low, mid);
            double dist2 = minDisDivideConquer(pointX, pointY, aux, mid + 1, high);
            double delta = Math.min(dist1, dist2);

            merge(pointY, aux, low, mid, high);
            int S = 0;
            for (int i = low; i <= high; i++) {
                if (Math.abs(pointY[i].x - median.x) < delta) {
                    aux[S++] = pointY[i];
                }
            }

            for (int i = 0; i < S; i++) {
                for (int j = i + 1; (j < S) && (aux[j].y - aux[i].y < delta); j++) {
                    double distance = aux[i].distance(aux[j]);
                    if (distance < delta) {
                        delta = distance;
                        if (distance < minDistance) {
                            minDistance = delta;
                            min1 = aux[i];
                            min2 = aux[j];
                        }
                    }
                }
            }
            return delta;
        }

    }
}
