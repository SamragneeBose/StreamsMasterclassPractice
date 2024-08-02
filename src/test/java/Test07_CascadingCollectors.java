import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Test07_CascadingCollectors {

    private List<String> alphabet =
            List.of("alfa", "bravo", "charlie", "delta", "echo",
                    "foxtrot", "golf", "hotel", "india", "juliet",
                    "kilo", "lima", "mike", "november", "oscar",
                    "papa", "quebec", "romeo", "sierra", "tango",
                    "uniform", "victor", "whiskey", "x-ray", "yankee",
                    "zulu");

    private List<String> sonnet = List.of(
            "From fairest creatures we desire increase,",
            "That thereby beauty's rose might never die,",
            "But as the riper should by time decease,",
            "His tender heir might bear his memory:",
            "But thou contracted to thine own bright eyes,",
            "Feed'st thy light's flame with self-substantial fuel,",
            "Making a famine where abundance lies,",
            "Thy self thy foe, to thy sweet self too cruel:",
            "Thou that art now the world's fresh ornament,",
            "And only herald to the gaudy spring,",
            "Within thine own bud buriest thy content,",
            "And, tender churl, mak'st waste in niggarding:",
            "Pity the world, or else this glutton be,",
            "To eat the world's due, by the grave and thee.");

    private List<String> expand(String s) {
        return s.codePoints()
                .mapToObj(codePoint -> Character.toString((char) codePoint))
                .collect(toList());
    }

    /**
     *  Use Collectors.counting() to get
     *  the count of lines in sonnet
     *  corresponding to each of its keys.
     */
    @Test
    public void cascadingCollectors_1()
    {
        Map<String, Long> map =
                sonnet.stream()
                .collect(
                        groupingBy(
                                line-> line.substring(0, 1),
                                Collectors.counting()
                        )
                ); // TODO

        assertThat(map.size()).isEqualTo(8);
        assertThat(map).containsExactly(
                Map.entry("P", 1L),
                Map.entry("A", 2L),
                Map.entry("B", 2L),
                Map.entry("T", 4L),
                Map.entry("F", 2L),
                Map.entry("W", 1L),
                Map.entry("H", 1L),
                Map.entry("M", 1L)
        );
    }

    /**
     *  Map the first character of each line in sonnet
     *  to a corresponding value which contains a list
     *  of the lengths of the lines starting with that key.
     *  Use Collectors.mapping().
     */
    @Test
    public void cascadingCollectors_2()
    {
        Map<String, List<Integer>> map =
                sonnet.stream()
                .collect(
                        groupingBy(
                                line -> line.substring(0, 1),
                                mapping(
                                        String::length,
                                        toList()
                                )
                        )
                ); // TODO

        assertThat(map.size()).isEqualTo(8);
        assertThat(map).containsExactly(
                Map.entry("P", List.of(40)),
                Map.entry("A", List.of(36, 46)),
                Map.entry("B", List.of(40, 45)),
                Map.entry("T", List.of(43, 46, 45, 46)),
                Map.entry("F", List.of(42, 53)),
                Map.entry("W", List.of(41)),
                Map.entry("H", List.of(38)),
                Map.entry("M", List.of(37))
        );
    }

    /**
     *  Group the lines of the sonnet by first letter, and
     *  collect the first word of grouped lines into a set.
     *  -
     *  To extract the first word of a line, use:   string.split(" +")[0]
     */
    @Test
    public void cascadingCollectors_3()
    {
//        Here, var is of type Collector<String, ?, Set<String>>
//
//        var mapToFirstWordInASet = mapping(
//                (String line) -> line.split(" +")[0], // Here, var needs more type information: (String line)
//                toSet()
//        );
//
//        Allows for further nesting of collectors.
//
//        Map<String, Set<String>> map =
//                sonnet.stream()
//                        .collect(
//                                groupingBy(
//                                        line -> line.substring(0, 1),
//                                        mapToFirstWordInASet
//                                )
//                        );

        Map<String, Set<String>> map =
                sonnet.stream()
                .collect(
                        groupingBy(
                                line -> line.substring(0, 1),
                                mapping(
                                        line -> line.split(" +")[0],
                                        toSet()
                                )
                        )
                ); // TODO
;
        assertThat(map.size()).isEqualTo(8);
        assertThat(map).containsExactly(
                Map.entry("P", Set.of("Pity")),
                Map.entry("A", Set.of("And", "And,")),
                Map.entry("B", Set.of("But")),
                Map.entry("T", Set.of("That", "Thy", "To", "Thou")),
                Map.entry("F", Set.of("Feed'st", "From")),
                Map.entry("W", Set.of("Within")),
                Map.entry("H", Set.of("His")),
                Map.entry("M", Set.of("Making"))
        );
    }

    /**
     *  Group lines of a sonnet by the first letter,
     *  and collect the grouped lines into a single string
     *  separated by newlines.
     */
    @Test
    public void cascadingCollectors_4a()
    {
        Map<String, String> map =
                sonnet.stream()
                .collect(
                        groupingBy(
                                line -> line.substring(0, 1),
                                Collectors.joining("\n")
                        )
                ); // TODO

        map.forEach((letter, count) -> System.out.println(letter + " => " + count));
    }

    /**
     *  Generate a frequency table of letters in the sonnet.
     *  Remember the expand() helper method.
     *  Hints: Use flatMap(), groupingBy(), and counting().
     */
    @Test
    public void cascadingCollectors_4b()
    {
        Map<String, Long> map =
                sonnet.stream()
                        .flatMap(
                                line -> expand(line).stream()
                        )
                        .collect(
                                groupingBy(
                                        letter -> letter,
                                        counting()
                                )
                        ); // TODO

        map.forEach((letter, count) -> System.out.println(letter + " => " + count));
    }
}
