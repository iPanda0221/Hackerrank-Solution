
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

class Node {

    int index;
    Node left;
    Node right;

    Node(int index) {
        this.index = index;
        this.left = null;
        this.right = null;
    }
}

class Result {

    /*
     * Complete the 'swapNodes' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY indexes
     *  2. INTEGER_ARRAY queries
     */
    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        // Write your code here
        Node root = buildTree(indexes);
        List<List<Integer>> result = new ArrayList<>();
        for (int k : queries) {
            swapAtDepth(root, k, 1);
            List<Integer> inOrderTraversal = new ArrayList<>();
            inOrder(root, inOrderTraversal);
            result.add(inOrderTraversal);
        }
        return result;
    }

    public static Node buildTree(List<List<Integer>> indexes) {
        Node[] nodes = new Node[indexes.size() + 1];
        for (int i = 1; i <= indexes.size(); i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < indexes.size(); i++) {
            int left = indexes.get(i).get(0);
            int right = indexes.get(i).get(1);
            if (left != -1) {
                nodes[i + 1].left = nodes[left];
            }
            if (right != -1) {
                nodes[i + 1].right = nodes[right];
            }
        }
        return nodes[1];
    }

    public static void swapAtDepth(Node node, int k, int depth) {
        if (node == null) {
            return;
        }
        if (depth % k == 0) {
            Node temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
        swapAtDepth(node.left, k, depth + 1);
        swapAtDepth(node.right, k, depth + 1);
    }

    public static void inOrder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrder(node.left, result);
        result.add(node.index);
        inOrder(node.right, result);
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                indexes.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        result.stream()
                .map(
                        r -> r.stream()
                                .map(Object::toString)
                                .collect(joining(" "))
                )
                .map(r -> r + "\n")
                .collect(toList())
                .forEach(e -> {
                    try {
                        bufferedWriter.write(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
