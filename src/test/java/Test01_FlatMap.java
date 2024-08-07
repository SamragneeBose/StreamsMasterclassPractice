import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class Test01_FlatMap {
    private List<String> alphabet = List.of("alfa", "bravo", "charlie", "delta");

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

    private String[] splitToWords(String line) {
        return line.split(" +");
    }

    /**
     * Given a list of Strings
     * [alfa, bravo, charlie,...]
     * Expand each String to a list of one-letter Strings
     * [[a, l, f, a], [b, r, a, v, o], [c, h,...],...]
     */
    @Test
    public void flatMap_1()
    {
        // word->expand(word)
        List<List<String>> result =
                alphabet.stream()
                .map(this::expand)
                .collect(toList()); // TODO

        assertThat(result).containsExactly(
                List.of("a", "l", "f", "a"),
                List.of("b", "r", "a", "v", "o"),
                List.of("c", "h", "a", "r", "l", "i", "e"),
                List.of("d", "e", "l", "t", "a")
        );
    }

    /**
     * Given a list of Strings
     * [alfa, bravo, charlie,...]
     * Expand each String to a list of one-letter Strings
     * [[a, l, f, a], [b, r, a, v, o], [c, h,...],...]
     * but <<flatten>> the nesting structure
     * [a, l, f, a, b,r, a, v,...]
     */
    @Test
    public void flatMap_2()
    {
        List<String> result =
                alphabet.stream()
                .flatMap(word -> expand(word).stream())
                .collect(toList()); // TODO

        assertThat(result).containsExactly(
                "a", "l", "f", "a",
                        "b", "r", "a", "v", "o",
                        "c", "h", "a", "r", "l", "i", "e",
                        "d", "e", "l", "t", "a"
        );
    }

    /**
     * Given the lines of the sonnet, split each line into words and
     * return a single list of all words.
     */
    @Test
    public void flatMap_3()
    {
//        Pattern pattern = Pattern.compile(" +");
//
//        // Same issue -> "increase," instead of "increase"
//        List<String> words=sonnet.stream()
//                .flatMap(pattern::splitAsStream)
//                .map(word->word.replaceAll("[,.:]", "")) // Issue fixed!
//                .collect(toList()); // TODO
//

        // Issue -> "increase," instead of "increase"
        List<String> words=sonnet.stream()
                .flatMap(line -> Arrays.stream(splitToWords(line)))
                .map(word -> word.replaceAll("[,.:]", ""))// Issue fixed!
                .toList(); // TODO

        assertThat(words.size()).isEqualTo(106);
        assertThat(words).contains("From", "fairest", "creatures", "we", "desire", "increase");
    }
}
