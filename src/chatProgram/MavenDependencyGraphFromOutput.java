import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MavenDependencyGraphFromOutput {

    public static class DependencyNode {
        private String groupId;
        private String artifactId;
        private String version;

        public DependencyNode(String groupId, String artifactId, String version) {
            this.groupId = groupId;
            this.artifactId = artifactId;
            this.version = version;
        }

        public String getId() {
            return groupId + ":" + artifactId + ":" + version;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DependencyNode that = (DependencyNode) o;
            return groupId.equals(that.groupId) && artifactId.equals(that.artifactId) && version.equals(that.version);
        }

        @Override
        public int hashCode() {
            return Objects.hash(groupId, artifactId, version);
        }

        @Override
        public String toString() {
            return getId();
        }
    }

    public static Map<DependencyNode, Set<DependencyNode>> buildDependencyGraphFromOutput(String projectDirectory)
            throws IOException, InterruptedException {
        List<String> outputLines = readDependencyTreeOutput(projectDirectory);
        Map<DependencyNode, Set<DependencyNode>> graph = new HashMap<>();
        Map<String, DependencyNode> nodes = new HashMap<>();
        DependencyNode root = null;
        Pattern dependencyPattern = Pattern.compile("([\\w.-]+):([\\w.-]+):([\\w.-]+):([\\w.-]+)");

        for (String line : outputLines) {
            int level = 0;
            while (line.startsWith(" ")) {
                line = line.substring(1);
                level++;
            }
            line = line.trim();
            if (line.startsWith("[INFO]") || line.isEmpty() || line.startsWith("---")) {
                continue;
            }

            Matcher matcher = dependencyPattern.matcher(line);
            if (matcher.find()) {
                String groupId = matcher.group(1);
                String artifactId = matcher.group(2);
                String version = matcher.group(3);
                DependencyNode currentNode = new DependencyNode(groupId, artifactId, version);
                nodes.computeIfAbsent(currentNode.getId(), k -> currentNode);
                currentNode = nodes.get(currentNode.getId());

                if (level == 0 && root == null) {
                    root = currentNode;
                    graph.put(root, new HashSet<>());
                } else if (root != null) {
                    // Need to find the parent based on the level
                    DependencyNode parent = findParentAtLevel(graph, root, level - 1);
                    if (parent != null) {
                        graph.computeIfAbsent(parent, k -> new HashSet<>()).add(currentNode);
                        graph.computeIfAbsent(currentNode, k -> new HashSet<>());
                    }
                }
            }
        }
        return graph;
    }

    private static DependencyNode findParentAtLevel(Map<DependencyNode, Set<DependencyNode>> graph,
                                                    DependencyNode startNode, int targetLevel) {
        // This is a simplified approach and might not be perfectly robust for all complex tree structures.
        // A more sophisticated parsing of the output might be needed for highly nested scenarios.
        if (targetLevel < 0) {
            return null;
        }
        LinkedList<Pair<DependencyNode, Integer>> queue = new LinkedList<>();
        queue.offer(new Pair<>(startNode, 0));

        DependencyNode foundParent = null;

        while (!queue.isEmpty()) {
            Pair<DependencyNode, Integer> current = queue.poll();
            DependencyNode node = current.getKey();
            int level = current.getValue();

            if (level == targetLevel) {
                foundParent = node;
                break;
            }

            Set<DependencyNode> dependencies = graph.get(node);
            if (dependencies != null) {
                for (DependencyNode dep : dependencies) {
                    queue.offer(new Pair<>(dep, level + 1));
                }
            }
        }
        return foundParent;
    }

    public static List<String> readDependencyTreeOutput(String projectDirectory) throws IOException, InterruptedException {
        List<String> outputLines = MavenDependencyTreeReader.readDependencyTree(projectDirectory);
        return outputLines;
    }

    public static void printDependencyGraph(Map<DependencyNode, Set<DependencyNode>> graph) {
        if (graph.isEmpty()) {
            System.out.println("Dependency graph is empty.");
            return;
        }

        // Find the root (the node with no incoming edges, or the first level dependency)
        DependencyNode root = graph.keySet().stream()
                .filter(node -> graph.values().stream().noneMatch(deps -> deps.contains(node)))
                .findFirst()
                .orElse(graph.keySet().iterator().next()); // Fallback

        printGraphRecursive(graph, root, "");
    }

    private static void printGraphRecursive(Map<DependencyNode, Set<DependencyNode>> graph, DependencyNode node, String indent) {
        System.out.println(indent + node);
        Set<DependencyNode> dependencies = graph.get(node);
        if (dependencies != null) {
            for (DependencyNode dep : dependencies) {
                printGraphRecursive(graph, dep, indent + "  ");
            }
        }
    }

    public static void main(String[] args) {
        String projectDir = "/path/to/your/maven/project"; // Replace with the actual path
        try {
            Map<DependencyNode, Set<DependencyNode>> dependencyGraph = buildDependencyGraphFromOutput(projectDir);
            if (dependencyGraph != null) {
                System.out.println("Maven Dependency Graph (from output):");
                printDependencyGraph(dependencyGraph);

                // You can now work with the 'dependencyGraph' variable
                System.out.println("\nGraph Data Structure:");
                for (Map.Entry<DependencyNode, Set<DependencyNode>> entry : dependencyGraph.entrySet()) {
                    System.out.println(entry.getKey() + " -> " + entry.getValue());
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
