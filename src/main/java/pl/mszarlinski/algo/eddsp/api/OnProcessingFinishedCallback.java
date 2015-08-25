package pl.mszarlinski.algo.eddsp.api;

import java.util.Map;

/**
 * Created by mszarlinski on 2015-08-23.
 */
@FunctionalInterface
public interface OnProcessingFinishedCallback {
    void onProcessginFinished(Map<String, Object> processingContext);
}
