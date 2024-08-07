import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Test08_StreamingMaps {

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
            "To eat the world's due, by the grave and thee.",
            "to to thy");

    /**
     * Find the most frequently occurring word in the Sonnet.
     * - Step 1: Find one of those word.
     * - Step 2: Find all those word in a list
     * ---
     * Hints:
     * 1. You cannot stream a map. To stream a map, you need to
     *    get a stream of entries from its entrySet().
     * 2. There is a Stream.max() method, and Map.Entry provides comparators
     */
    @Test
    public void streamingMaps_1a() {
        Pattern pattern = Pattern.compile(("[ ,':\\-]+"));

        Map<String, Long> words =
                sonnet.stream()
                        .map(String::toLowerCase)
                        .flatMap(pattern::splitAsStream)
                        .collect(
                                Collectors.groupingBy(
                                        word -> word,
                                        Collectors.counting()
                                )
                        ); // TODO

        words.forEach((letter, count) -> System.out.println(letter + " => " + count));

        // Returns Map.Entry<K, V>, NOT Map<K, V>
        Map.Entry<String, Long> mostFrequentWord =
                words.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow(); // TODO

        // Returns last occurrence of mostFrequentWord
        System.out.println("Most Frequent Word: "+mostFrequentWord);
    }

    /**
     * Find the most frequently occurring word in the Sonnet.
     * - Step 1: Find one of those words.
     */
    @Test
    public void streamingMaps_1b() {
        Pattern pattern = Pattern.compile(("[ ,':\\-]+"));

        Map<String, Long> words =
                sonnet.stream()
                        .map(String::toLowerCase)
                        .flatMap(pattern::splitAsStream)
                        .collect(
                                Collectors.collectingAndThen(
                                    Collectors.groupingBy(
                                            word -> word,
                                            Collectors.counting()
                                    ),
                                        Map::copyOf // map -> Map.copyOf(map)
                                )
                        ); // TODO

        words.forEach((letter, count) -> System.out.println(letter + " => " + count));

        // Returns Map.Entry<K, V>, NOT Map<K, V>
        Map.Entry<String, Long> mostFrequentWord =
                words.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow(); // TODO

        // Returns an instance of mostFrequentWord -> Random O/P
        System.out.println("Most Frequent Word: "+mostFrequentWord);
    }

    /**
     * Find the list of words that occur frequently
     * by inverting the map.
     */
    @Test
    public void streamingMaps_1c() {
        Pattern pattern = Pattern.compile(("[ ,':\\-]+"));

        Map<String, Long> words =
                sonnet.stream()
                        .map(String::toLowerCase)
                        .flatMap(pattern::splitAsStream)
                        .collect(
                                Collectors.collectingAndThen(
                                        Collectors.groupingBy(
                                                word -> word,
                                                Collectors.counting()
                                        ),
                                        Map::copyOf // map -> Map.copyOf(map)
                                )
                        );

//      Without downstream collector in groupingBy,
//      Map<Long, List<Map.Entry<String, Long>>> would be returned
        Map<Long, List<String>> otherWords =
                words.entrySet().stream() // Stream<Map.Entry<String, Long>>
                        .collect(
                                Collectors.groupingBy(
                                        Map.Entry::getValue, // entry -> entry.getValue()
                                        Collectors.mapping(
                                                Map.Entry::getKey, // entry -> entry.getKey()
                                                Collectors.toList()
                                        )
                                )
                        ); // TODO

        Map.Entry<Long, List<String>> mostSeenWords =
                otherWords.entrySet().stream() // Stream<Map.Entry<Long, List<String>>>
                        .max(
                                Map.Entry.comparingByKey()
                        )
                        .orElseThrow(); // TODO

        System.out.println("Most Seen Words: "+mostSeenWords);
    }
}
