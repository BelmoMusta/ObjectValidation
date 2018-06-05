package musta.belmo.validation.criteria;

import java.util.*;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criteria {
    /**
     * the object to validate
     */
    private Object object;
    private Map<String, List<Criterion>> map;

    public static Criteria of(Object object) {
        return new Criteria(object);
    }

    public Criteria() {
        map = new HashMap<>();
    }

    public Criteria(Object object) {
        this();
        this.object = object;
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
