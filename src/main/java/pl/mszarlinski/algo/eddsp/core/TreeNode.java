package pl.mszarlinski.algo.eddsp.core;

import java.util.*;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class TreeNode {

    final private int id;
    final private Map<String, Object> data = new HashMap<>();
    final private List<TreeNode> children = new LinkedList<>();

    private boolean visited = false;

    public TreeNode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * TODO: create ValueTreeNode subclass?
     */
    public Optional<Integer> getValue() {
        return Optional.ofNullable((Integer) data.get("value"));
    }
}
