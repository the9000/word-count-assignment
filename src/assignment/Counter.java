package assignment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Counts the number of times a particular word occurred in input.
 */
class Counter {
    // NOTE: if we ran counting in parallel, we'd have a ConcurrentHashMap<String, AtomicInteger>.
    private final HashMap<String, Integer> myWordCount;

    Counter() {
        this.myWordCount = new HashMap<String, Integer>();
    }

    public void count(String word) {
        int count_so_far;
        if (myWordCount.containsKey(word)) {
            // We are sure not to have any nulls, so unboxing right away.
            count_so_far = myWordCount.get(word) + 1;
        }
        else count_so_far = 1;
        myWordCount.put(word, count_so_far);
    }

    public Map<String, Integer> getCounts() {
        return Collections.unmodifiableMap(myWordCount);
    }
}
