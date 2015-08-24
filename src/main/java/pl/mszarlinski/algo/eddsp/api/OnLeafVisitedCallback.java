package pl.mszarlinski.algo.eddsp.api;

import pl.mszarlinski.algo.eddsp.core.TreeNode;

import java.util.Map;

/**
 * Created by Maciej on 2015-08-23.
 */
@FunctionalInterface
public interface OnLeafVisitedCallback {
    void onLeafVisited(TreeNode leaf, Map<String, Object> processingContext);
}
