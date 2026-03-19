package Lab01;

import java.util.*;

public class Bai03 {
    static class Point implements Comparable<Point> {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int compareTo(Point p) {
            if (this.x == p.x)
                return this.y - p.y;
            return this.x - p.x;
        }

        public String toString() {
            return x + " " + y;
        }
    }

    // Returns a positive value if o-a-b makes a counter-clockwise turn,
    // negative for clockwise, zero if they are collinear.
    public static long crossProduct(Point o, Point a, Point b) {
        return (long) (a.x - o.x) * (b.y - o.y) - (long) (a.y - o.y) * (b.x - o.x);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng trạm phát sóng (n): ");
        if (!sc.hasNextInt())
            return;

        int n = sc.nextInt();
        Point[] points = new Point[n];
        System.out.println("Nhập tọa độ (x, y) cho " + n + " trạm:");
        for (int i = 0; i < n; i++) {
            points[i] = new Point(sc.nextInt(), sc.nextInt());
        }
        System.out.println("Các trạm cảnh báo là:");

        if (n <= 3) {
            for (Point p : points) {
                System.out.println(p);
            }
            return;
        }

        // Monotone chain algorithm to find Convex Hull
        Arrays.sort(points);
        Point[] hull = new Point[2 * n];
        int k = 0;

        // Build upper hull
        for (int i = 0; i < n; ++i) {
            while (k >= 2 && crossProduct(hull[k - 2], hull[k - 1], points[i]) >= 0) {
                k--;
            }
            hull[k++] = points[i];
        }

        // Build lower hull
        for (int i = n - 2, t = k + 1; i >= 0; i--) {
            while (k >= t && crossProduct(hull[k - 2], hull[k - 1], points[i]) >= 0) {
                k--;
            }
            hull[k++] = points[i];
        }

        // Output clockwise result exactly matching example format
        for (int i = 0; i < k - 1; i++) {
            System.out.println(hull[i]);
        }
    }
}
