package assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Command-line interface.
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

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <prog> request_file_name");
            System.exit(1);
        }
        // Produce the actual (I/O-error-prone) string stream from file.
        try {
            Stream<String> input_lines = Files.lines(Paths.get(args[0]));
            Stream<String> words = input_lines.flatMap(new LineParser());
            Counter counter = new Counter();
            words.forEach(counter::count);
            System.out.println(jsonify(counter.getCounts()));
        } catch (IOException e) {
            e.printStackTrace(); // TODO: nicer handling.
        }
    }
}
