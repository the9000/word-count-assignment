package assignment;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Parses a comma-separated string into a stream of words.
 */
class LineParser implements Function<String, Stream<String>> {

    @Override
    public Stream<String> apply(String input) {
        Stream.Builder<String> builder = Stream.builder();
        int start = 0;
        int end = 0;
        while (end >= 0) {  // Do not allocate a whole-string array like .split() does.
            end = input.indexOf(',', start);  // OK to pass start past EOL.
            if (end > 0) {
                builder.add(input.substring(start, end));
                start = end + 1;  // 1 is the length of ",".
            }
        }
        return builder.build();
    }
}
