package customCollection.limitedSetImpl;

import java.util.HashMap;
import java.util.Map;

public class LimitedSet<T> implements customCollection.LimitedSet<T> {
    private static final int INITIAL_CALL_COUNTER = 0;
    private static final int MAX_CAPACITY = 10;
    private HashMap<T, Integer> map;

    public LimitedSet() {
        map = new HashMap<>();
    }

    @Override
    public void add(final T t) {
        if (map.containsKey(t)) {
            return;
        }
        if (map.size() < MAX_CAPACITY) {
            map.put(t, INITIAL_CALL_COUNTER);
        } else {
            map.remove(findMinCallCounterOwner(map));
            map.put(t, INITIAL_CALL_COUNTER);
        }
    }

    @Override
    public boolean remove(final T t) {
        return map.remove(t) != null;
    }

    @Override
    public boolean contains(final T t) {
        if (map.containsKey(t)) {
            int callCounter = map.get(t);
            map.put(t, callCounter + 1);
            return true;
        }
        return false;
    }

    private T findMinCallCounterOwner(HashMap<T, Integer> map) {
        int minCallCounterValue = findMinCallCounterValue(map);
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() == minCallCounterValue)
                .findFirst()
                .get()
                .getKey();
    }

    private int findMinCallCounterValue(HashMap<T, Integer> map) {
        return map.entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .min()
                .getAsInt();
    }

    @Override
    public String toString() {
        return "LimitedSet{" +
                "map=" + map +
                '}';
    }
}
