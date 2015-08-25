package pl.mszarlinski.algo.eddsp.api;

import pl.mszarlinski.algo.eddsp.core.TreeNode;

import java.util.Map;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public class TraverseResult {

    private final Map<String, Object> processingContext;

    private final TreeNode rootNode;

    public TraverseResult(final Map<String, Object> processingContext, final TreeNode rootNode) {
        this.processingContext = processingContext;
        this.rootNode = rootNode;
    }

    public Map<String, Object> getProcessingContext() {
        return processingContext;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }
}
