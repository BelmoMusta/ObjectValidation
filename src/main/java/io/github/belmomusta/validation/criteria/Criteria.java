package io.github.belmomusta.validation.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Criteria class.
 *
 * @param <T> The Generique type
 * @since 0.0.0.SNAPSHOT
 * @author default author
 * @version 0.0.0
 */
public class Criteria<T> {

    /**
     * The {@link #object} attribute.
     */
    private T object;

    /**
     * The {@link #map} attribute.
     */
    private Map<String, List<Criterion>> map;

    /**
     * The default constructor
     */
    public Criteria() {
        map = new HashMap<>();
    }

    /**
     * Constructor with parameters
     *
     * @param object the object
     */
    public Criteria(T object) {
        this();
        this.object = object;
    }

    /**
     * creates an instance of criteria of the given object
     *
     * @param <R> The Generic type
     * @param object the object
     * @return Criteria
     */
    public static <R> Criteria of(R object) {
        return new Criteria<>(object);
    }

    /**
     * Add a criterion to the criteria list
     *
     * @param criterion {@link Criterion}
     */
    public void add(Criterion criterion) {
        final List<Criterion> criteria;
        final String fieldName;
        if (criterion != null) {
            fieldName = criterion.getFieldName();
            if (map.containsKey(fieldName)) {
                criteria = map.get(fieldName);
            } else {
                criteria = new ArrayList<>();
                map.put(fieldName, criteria);
            }
            criteria.add(criterion);
        }
    }

    /**
     * List all the criteria
     *
     * @return List
     */
    public List<Criterion> all() {
        return map.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * @return Attribut {@link #object}
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object Value to be assigned to the {@link #object} attribute.
     */
    public void setObject(T object) {
        this.object = object;
    }
}
