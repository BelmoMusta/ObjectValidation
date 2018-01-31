package musta.belmo.validation.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 22/01/2018.
 */
public class Criteria {


    public Map<String, List<Criterion>> map;

    public Criteria() {
        map = new HashMap<>();
    }

    public void add(Criterion criterion) {
        String fieldName = criterion.getFieldName();

        List<Criterion> criteria;

        if (map.containsKey(fieldName)) {
            criteria = map.get(fieldName);
        } else {

            criteria = new ArrayList<>();
            map.put(fieldName,criteria);
        }
        criteria.add(criterion);
    }

    public static Criteria of(Criterion name) {
        Criteria criteria = new Criteria();
        criteria.add(name);
        return criteria;
    }
}
