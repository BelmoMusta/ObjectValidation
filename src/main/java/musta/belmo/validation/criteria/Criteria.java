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
    public Map<String, List<Criterion>> map;

    public List<Criterion> get(String fieldName) {
        return map.get(fieldName);
    }

    public Criteria() {
        map = new HashMap<>();
    }

    public Criteria(Object object) {
        this();
        this.object = object;
    }

    public Criteria add(Criterion criterion) {
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
        return this;
    }

    public List<Criterion> all() {
        List<Criterion> list = new ArrayList<>();
        Iterator<Map.Entry<String, List<Criterion>>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            List<Criterion> criterionList = iterator.next().getValue();
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

    public static Criteria of(Criterion name) {
        Criteria criteria = new Criteria();
        criteria.add(name);
        return criteria;
    }
}

