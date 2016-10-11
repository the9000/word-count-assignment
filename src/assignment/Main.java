package assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The problem: <b/><i>
 * Consider a list of sets of words (each set of words is called a record) and a single set of words (called the query).
 * For each word that is not in the query, how many times does the word appear in all records that the entire query is
 * present in? Output a dictionary of words to counts, omitting words with counts of zero. Given a list of records and
 * a list of queries, determine the output for each query with respect to the entire list of records.
 * </i>
 * <p/>
 * Command-line interface class.
 */
public class Main {

    // For the sake of not importing a 3rd-party library into this short example.
    static String jsonify(Map<String, Integer> map) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        boolean is_not_first = false;
        for (String key : map.keySet()) {
            if (is_not_first) builder.append(", ");
            else is_not_first = true;
            builder.append("\"" + key + "\": " + map.get(key));
        }
        builder.append("}");
        return builder.toString();
    }

    static Stream<String> lineAsWordStream(String input) {
        return Arrays.stream(input.split(","));
    }

    static boolean divulge(Object x) {
        System.out.println(String.valueOf(x));
        return true;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: <prog> records_file query_file");
            System.exit(1);
        }
        // Produce the actual (I/O-error-prone) string stream from file.
        final Path record_file_path = Paths.get(args[0]);
        final Path query_file_path = Paths.get(args[1]);
        try {
            // We do not try to detect and report repeating words in data; we treat them as one.
            Stream<String> record_lines = Files.lines(record_file_path);
            Stream<Set<String>> records_set_stream = record_lines.map(Main::lineAsWordStream).map(st -> st.collect(Collectors.toSet()));;

            // We cannot operate directly on the stream of records, because we have more than one query.
            List<Set<String>> records = records_set_stream.collect(Collectors.toList());

            // TODO: make a word -> [records] index to speed up scanning.

            // Prepare the queries to run against the counts we have.
            Stream<String> query_lines = Files.lines(query_file_path);
            Stream<Set<String>> word_sets = query_lines.map(Main::lineAsWordStream)
                    .map(st -> st.collect(Collectors.toSet()));

            // Run each word set as a query against the counts.
            word_sets.forEach(query_words -> {
                System.out.println(query_words.toString()); // XXX
                Count missing_words_count = new Count();
                Stream<Set<String>> matching_records = records.stream().filter(record -> record.containsAll(query_words));
                Stream<String> missing_words = matching_records.flatMap(Collection::stream).filter(word -> !query_words.contains(word));
                missing_words.forEach(word -> missing_words_count.count(word));
                System.out.println(jsonify(missing_words_count.getCounts()));
            });

        } catch (IOException e) {
            e.printStackTrace(); // TODO: nicer handling.
        }
    }
}
