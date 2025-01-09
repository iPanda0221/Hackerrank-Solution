
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'formingMagicSquare' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY s as parameter.
     */
    public static void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }

            swap(nums, i, j);
        }

        reverse(nums, i + 1, n - 1);
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    public static int getCost(int[] a, int[] b) {
        int cost = 0;
        for (int i = 0; i < a.length; i++) {
            cost += Math.abs(a[i] - b[i]);
        }
        return cost;
    }

    public static int formingMagicSquare(List<List<Integer>> s) {
        // Write your code here
        List<Integer> flatList = new ArrayList<>();
        for (List<Integer> subList : s) {
            flatList.addAll(subList);
        }
        int[] flatS = new int[flatList.size()];
        for (int i = 0; i < flatList.size(); i++) {
            flatS[i] = flatList.get(i);
        }
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int minCost = getCost(nums, flatS);
        for (int i = 0; i < 362880; i++) {
            nextPermutation(nums);
            if (nums[0] + nums[1] + nums[2] == 15 && nums[3] + nums[4] + nums[5] == 15 && nums[0] + nums[3] + nums[6] == 15 && nums[1] + nums[4] + nums[7] == 15 && nums[0] + nums[4] + nums[8] == 15 && nums[2] + nums[4] + nums[6] == 15) {
                int cost = getCost(nums, flatS);
                if (cost < minCost) {
                    minCost = cost;
                }
            }
        }
        return minCost;
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        List<List<Integer>> s = new ArrayList<>();

        IntStream.range(0, 3).forEach(i -> {
            try {
                s.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.formingMagicSquare(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
