package Lab01;

import java.util.Scanner;

public class Bai01 {
    // Use a numerical integration method or Monte Carlo Simulation.
    public static double approximateArea(double r) {
        long pointsInside = 0;
        long totalPoints = 10000000;

        for (long i = 0; i < totalPoints; i++) {
            // Generate random points in the square [-r, r] x [-r, r]
            double x = (Math.random() * 2 * r) - r;
            double y = (Math.random() * 2 * r) - r;

            // Equation of circle: x^2 + y^2 <= r^2
            if (x * x + y * y <= r * r) {
                pointsInside++;
            }
        }

        // Area of the bounding square is (2r) * (2r)
        double squareArea = (2 * r) * (2 * r);
        return squareArea * pointsInside / totalPoints;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập vào bán kính r: ");
        double r = sc.nextDouble();
        System.out.println("Diện tích xấp xỉ: " + approximateArea(r));
    }
}
