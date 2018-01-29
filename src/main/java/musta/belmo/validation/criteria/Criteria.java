package musta.belmo.validation.criteria;

import java.util.*;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criteria {
    public Map<String, List<Criterion>> map;

    public List<Criterion> get(String fieldName) {
        return map.get(fieldName);
    }

    public Criteria() {
        map = new HashMap<>();
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
}
