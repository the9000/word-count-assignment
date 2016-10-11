package assignment;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Turn (streams of) strings into useful representations.
 */
class Parse {
    static Stream<String> lineAsWordStream(String input) {
        return Arrays.stream(input.split(","));
    }

    static Stream<Set<String>> asStreamOfSets(Stream<String> lines) {
      return lines.map(Parse::lineAsWordStream).map(st -> st.collect(Collectors.toSet()));
    }

}
