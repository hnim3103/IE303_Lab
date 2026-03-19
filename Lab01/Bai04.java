package Lab01;

import java.util.*;

public class Bai04 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Using comma or whitespace as delimiter to effectively parse inputs like "7,
        // 8"
        sc.useDelimiter("[,\\s]+");

        if (!sc.hasNextInt())
            return;

        int n = sc.nextInt();
        int k = sc.nextInt();

        int[] A = new int[n];
        for (int i = 0; i < n && sc.hasNextInt(); i++) {
            A[i] = sc.nextInt();
        }

        // DP array: dp[j] stores max length of subsequence with sum j
        int[] dp = new int[k + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        // keep track of choices to reconstruct subsequence
        boolean[][] keep = new boolean[n][k + 1];

        for (int i = 0; i < n; i++) {
            int val = A[i];
            for (int j = k; j >= val; j--) {
                if (dp[j - val] != -1) {
                    if (dp[j - val] + 1 > dp[j]) {
                        dp[j] = dp[j - val] + 1;
                        keep[i][j] = true;
                    }
                }
            }
        }

        if (dp[k] == -1) {
            return;
        }

        List<Integer> result = new ArrayList<>();
        int currJ = k;

        // Reconstruct from end to start
        for (int i = n - 1; i >= 0; i--) {
            if (keep[i][currJ]) {
                result.add(A[i]);
                currJ -= A[i];
            }
        }

        // DP processes items left-to-right, backtracking builds sequence right-to-left
        Collections.reverse(result);
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
