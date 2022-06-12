package custom.limitedset;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LimitedSet<E> extends AbstractSet<E> implements Set<E> {
    private static final int INITIAL_CALL_COUNTER = 0;
    private static final int MAX_CAPACITY = 10;
    private HashMap<E, Integer> map;

    public LimitedSet() {
        map = new HashMap<>();
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean add(E e) {
        if (map.containsKey(e)) {
            return false;
        }
        if (map.size() < MAX_CAPACITY) {
            map.put(e, INITIAL_CALL_COUNTER);
        } else {
            map.remove(findMinCallCounterOwner(map));
            map.put(e, INITIAL_CALL_COUNTER);
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        if (map.containsKey(o)) {
            int callCounter = map.get(o);
            map.put((E) o, callCounter + 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    private E findMinCallCounterOwner(HashMap<E, Integer> map) {
        int minCallCounterValue = findMinCallCounterValue(map);
        return map.entrySet().stream()
                .filter(entry -> entry.getValue() == minCallCounterValue)
                .findFirst()
                .get()
                .getKey();
    }

    private int findMinCallCounterValue(HashMap<E, Integer> map) {
        return map.entrySet().stream()
                .mapToInt(Map.Entry::getValue)
                .min()
                .getAsInt();
    }

    @Override
    public String toString() {
        return "LimitedSet{"
                + "map=" + map
                + '}';
    }
}
