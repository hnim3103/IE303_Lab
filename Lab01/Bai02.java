package Lab01;

public class Bai02 {
    public static double approximatePi() {
        long pointsInside = 0;
        long totalPoints = 10000000;

        for (long i = 0; i < totalPoints; i++) {
            // Random point in [-1, 1] x [-1, 1]
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;

            // Unit circle equation: x^2 + y^2 <= 1
            if (x * x + y * y <= 1) {
                pointsInside++;
            }
        }

        // Area of unit circle is Pi. Area of bounding square is 4.
        // Pi = 4 * (Points inside) / (Total points)
        return 4.0 * pointsInside / totalPoints;
    }

    public static void main(String[] args) {
        System.out.println("Giá trị Pi xấp xỉ: " + approximatePi());
    }
}
