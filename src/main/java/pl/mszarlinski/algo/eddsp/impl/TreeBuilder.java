package pl.mszarlinski.algo.eddsp.impl;

import pl.mszarlinski.algo.eddsp.api.Parameter;
import pl.mszarlinski.algo.eddsp.core.TreeNode;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class TreeBuilder {
    public static TreeNode buildTreeFromArrays(final int[] from, final int[] to, final int rootId) {
        final int nodesCount = from.length + 1;
        final TreeNode[] nodes = new TreeNode[nodesCount];

        for (int i = 0; i < from.length; i++) {
            final int fromId = from[i];
            final int toId = to[i];

            if (nodes[fromId] == null) {
                nodes[fromId] = new TreeNode(fromId);
            }

            if (nodes[toId] == null) {
                nodes[toId] = new TreeNode(toId);
            }

            nodes[fromId].getChildren().add(nodes[toId]);
        }

        return nodes[rootId];
    }

    public static TreeNode buildTreeFromArraysWithValues(final int[] from, final int[] to, final int[] values, final int rootId) {
        final Parameter<Integer> value = new Parameter<>("value");

        final int nodesCount = from.length + 1;
        final TreeNode[] nodes = new TreeNode[nodesCount];

        for (int i = 0; i < from.length; i++) {
            final int fromId = from[i];
            final int toId = to[i];

            if (nodes[fromId] == null) {
                nodes[fromId] = new TreeNode(fromId);
                value.put(nodes[fromId].getData(), values[fromId]);
            }

            if (nodes[toId] == null) {
                nodes[toId] = new TreeNode(toId);
                value.put(nodes[toId].getData(), values[toId]);
            }

            nodes[fromId].getChildren().add(nodes[toId]);
        }

        return nodes[rootId];
    }
}
