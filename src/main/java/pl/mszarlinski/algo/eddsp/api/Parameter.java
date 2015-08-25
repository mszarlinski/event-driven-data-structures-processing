package pl.mszarlinski.algo.eddsp.api;

import java.util.Map;

/**
 * @author mszarlinski@bravurasolutions.com on 2015-08-25.
 */
public class Parameter<T> {

    private final String name;

    public Parameter(final String name) {
        this.name = name;
    }

    public T get(final Map<String, Object> map) {
        return (T) map.get(name);
    }

    public void put(final Map<String, Object> map, final T value) {
        map.put(name, value);
    }
}
