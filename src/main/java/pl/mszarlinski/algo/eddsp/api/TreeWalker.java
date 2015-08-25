package pl.mszarlinski.algo.eddsp.api;

import pl.mszarlinski.algo.eddsp.impl.TreeWalkerImpl;

/**
 * Created by mszarlinski on 2015-08-23.
 */
public interface TreeWalker {

    static TreeWalkerImpl aTreeWalker() {
        return new TreeWalkerImpl();
    }
}
