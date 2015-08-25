package pl.mszarlinski.algo.eddsp.impl;

import pl.mszarlinski.algo.eddsp.api.*;
import pl.mszarlinski.algo.eddsp.core.TreeNode;

import java.util.*;

import static java.lang.String.format;
import static org.apache.commons.lang3.BooleanUtils.isNotTrue;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class TreeWalkerImpl {
    private TreeNode rootNode;
    private final List<OnProcessingStartedCallback> onProcessingStartedCallbackList = new LinkedList<>();
    private final List<OnProcessingFinishedCallback> onProcessingFinishedCallbackList = new LinkedList<>();
    private final List<OnBottomUpNodeVisitedCallback> onBottomUpNodeVisitedCallbackList = new LinkedList<>();
    private final List<OnUpBottomNodeVisitedCallback> onUpBottomNodeVisitedCallbackList = new LinkedList<>();
    private final List<OnLeafVisitedCallback> onLeafVisitedCallbackList = new LinkedList<>();

    public TreeWalkerImpl from(final int[] from, final int[] to, final int rootId) {
        rootNode = TreeBuilder.buildTreeFromArrays(from, to, rootId);
        return this;
    }

    public TreeWalkerImpl from(final int[] from, final int[] to, final int[] value, final int rootId) {
        rootNode = TreeBuilder.buildTreeFromArraysWithValues(from, to, value, rootId);
        return this;
    }

    /**
     * TODO: logging levels (SIMPLE, DETAILED)
     */
    public TreeWalkerImpl withLogging(boolean loggingEnabled) {
        if (loggingEnabled) {
            onProcessingStartedCallbackList.add(ctx -> TreeLogger.log("Processing started"));
            onProcessingFinishedCallbackList.add(ctx -> TreeLogger.log("Processing finished"));
            onBottomUpNodeVisitedCallbackList.add((node, ctx) -> TreeLogger.log(format("Processing of node %d subtree finished", node.getId())));
            onUpBottomNodeVisitedCallbackList.add((node, ctx) -> TreeLogger.log(format("Processing of node %d subtree started", node.getId())));
            onLeafVisitedCallbackList.add((leaf, ctx) -> TreeLogger.log(format("Leaf %d visited", leaf.getId())));
        }
        return this;
    }

    public TreeWalkerImpl onLeaf(final OnLeafVisitedCallback callback) {
        onLeafVisitedCallbackList.add(callback);
        return this;
    }

    public TreeWalkerImpl onBottomUpNode(final OnBottomUpNodeVisitedCallback callback) {
        onBottomUpNodeVisitedCallbackList.add(callback);
        return this;
    }

    public TreeWalkerImpl onUpBottomNode(final OnUpBottomNodeVisitedCallback callback) {
        onUpBottomNodeVisitedCallbackList.add(callback);
        return this;
    }

    public TraverseResult traverse() {

        final Map<String, Object> processingContext = new HashMap<>();
        final Parameter<Boolean> halt = new Parameter<>("halt"); // enable exiting main loop
        final Deque<TreeNode> stack = new LinkedList<>();
        stack.push(rootNode);

        fireEvent(TreeEvent.PROCESSING_STARTED, processingContext);

        while (keepTraversing(processingContext, halt, stack)) {
            final TreeNode currentNode = stack.pop();
            processNode(currentNode, stack, processingContext);
        }

        fireEvent(TreeEvent.PROCESSING_FINISHED, processingContext);

        return new TraverseResult(processingContext, rootNode);
    }

    private boolean keepTraversing(Map<String, Object> processingContext, Parameter<Boolean> halt, Deque<TreeNode> stack) {
        return !stack.isEmpty() && isNotTrue(halt.get(processingContext));
    }

    private void fireEvent(final TreeEvent event, final Map<String, Object> processingContext) {
        fireEvent(event, processingContext, Optional.empty());
    }

    private void fireEvent(final TreeEvent event, final Map<String, Object> processingContext, final Optional<TreeNode> node) {
        switch (event) {

            case PROCESSING_STARTED:
                onProcessingStartedCallbackList.forEach(c -> c.onProcessingStarted(processingContext));
                break;
            case PROCESSING_FINISHED:
                onProcessingFinishedCallbackList.forEach(c -> c.onProcessginFinished(processingContext));
                break;
            case BOTTOM_UP_NODE_VISITED:
                onBottomUpNodeVisitedCallbackList.forEach(c -> c.onBottomUpNodeVisited(node.get(), processingContext));
                break;
            case UP_BOTTOM_NODE_VISITED:
                onUpBottomNodeVisitedCallbackList.forEach(c -> c.onUpBottomNodeVisited(node.get(), processingContext));
                break;
            case LEAF_VISITED:
                onLeafVisitedCallbackList.forEach(c -> c.onLeafVisited(node.get(), processingContext));
                break;

            default:
                throw new IllegalArgumentException("Unknown event: " + event);
        }
    }

    private void processNode(final TreeNode node, final Deque<TreeNode> stack, final Map<String, Object> processingContext) {
        if (node.isVisited()) {
            if (node.isLeaf()) {
                throw new IllegalStateException("Leaf should not be mark as visited");
            }
            fireEvent(TreeEvent.BOTTOM_UP_NODE_VISITED, processingContext, Optional.of(node));
        } else {
            if (node.isLeaf()) {
                fireEvent(TreeEvent.LEAF_VISITED, processingContext, Optional.of(node));
            } else {
                fireEvent(TreeEvent.UP_BOTTOM_NODE_VISITED, processingContext, Optional.of(node));
                node.setVisited(true);
                stack.push(node);
                node.getChildren().forEach(stack::push);
            }
        }
    }
}
