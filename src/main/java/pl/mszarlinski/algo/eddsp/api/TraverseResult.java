package pl.mszarlinski.algo.eddsp.api;

import java.util.Map;

/**
 * Created by Maciej on 2015-08-23.
 */
public class TraverseResult {

    private final Map<String, Object> processingContext;

    public TraverseResult(Map<String, Object> processingContext) {
        this.processingContext = processingContext;
    }

    public Map<String, Object> getProcessingContext() {
        return processingContext;
    }
}
