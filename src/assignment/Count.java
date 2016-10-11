package assignment;

import java.util.*;
import java.util.stream.Stream;

/**
 * Counts the number of times a particular word occurred in input.
 * A hash table has an efficient representation of a key set, easy to check for presence of query words.
 */
class Count {
    // NOTE: if we ran counting in parallel, we'd have a ConcurrentHashMap<String, AtomicInteger>.
    // If we user a tree-based map instead, we could cheaply return new immutable instances on every .count()
    // invocation. But we never need intermediate results here. The lookup time would be somehow worse, too.

    private final HashMap<String, Integer> myWordCount;

    Count() { this.myWordCount = new HashMap<>(); }

    /**
     * Adds / updates the count for the {@code word} by {@code amount}.
     * */
    void count(String word) {
        int count_so_far;
        if (myWordCount.containsKey(word)) {
            // We are sure not to have any nulls, so unboxing right away.
            count_so_far = myWordCount.get(word) + 1;
        } else count_so_far = 1;
        myWordCount.put(word, count_so_far);
    }

    Map<String, Integer> getCounts() {
        return Collections.unmodifiableMap(myWordCount);
    }

    static Count ofMissingWords(Collection<Set<String>> records, Set<String> query_words) {
        Count missing_words_count = new Count();
        Stream<Set<String>> matching_records = records.stream().filter(record -> record.containsAll(query_words));
        Stream<String> missing_words = matching_records.flatMap(Collection::stream)
                .filter(word -> ! query_words.contains(word));
        missing_words.forEach(missing_words_count::count);
        return missing_words_count;
    }

}

