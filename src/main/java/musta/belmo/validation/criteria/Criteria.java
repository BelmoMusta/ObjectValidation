package musta.belmo.validation.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Criteria class.
 */
public class Criteria {
    /**
     * the object to validate
     */
    private Object object;
    /**
     * The map of criteria
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
    public Criteria(Object object) {
        this();
        this.object = object;
    }

    /**
     * creates an instance of criteria of the given object
     *
     * @param object the object
     * @return Criteria
     */
    public static Criteria of(Object object) {
        return new Criteria(object);
    }

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

    public List<Criterion> all() {
        List<Criterion> list = new ArrayList<>();
        for (Map.Entry<String, List<Criterion>> stringListEntry : map.entrySet()) {
            List<Criterion> criterionList = stringListEntry.getValue();
            list.addAll(criterionList);
        }
        return list;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
