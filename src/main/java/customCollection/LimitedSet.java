package customCollection;

import java.util.Set;

public interface LimitedSet<T> {
     void add(final T t);
     boolean remove(final T t);
     boolean contains(final T t);
}