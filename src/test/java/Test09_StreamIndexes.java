import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test09_StreamIndexes {

    private List<String> alphabet =
            List.of("alfa", "bravo", "charlie", "delta", "echo",
                    "foxtrot", "golf", "hotel", "india", "juliet",
                    "kilo", "lima", "mike", "november", "oscar",
                    "papa", "quebec", "romeo", "sierra", "tango",
                    "uniform", "victor", "whiskey", "x-ray", "yankee",
                    "zulu");

    /**
     * Split alphabet into sub-lists of size N adjacent elements
     */
    @Test
    public void streamIndexes_1()
    {
        int N = 3;
        int SIZE = alphabet.size();

        // Cannot directly stream over alphabet -> Will return stream of String elements
        // Using IntStream to get a stream of Integer values for the indexes
        List<List<String>> sublists =
                IntStream.range(0, SIZE/N) // Similar to for loop
                        .mapToObj(
                                i -> alphabet.subList( // sublist() method generates a sublist
                                        N*i, N*(i+1) // Range of sublist, Index Calculations
                                )
                            )
                        .collect(Collectors.toList()); // TODO

        // Since SIZE is not a multiple of N,
        // Last part of the list is truncated.
        // Here, the tail => ["yankee", "zulu"] is missing from the list.
        System.out.println(sublists);
    }

    /**
     * Modify the previous code to include the tail of the list
     */
    @Test
    public void streamIndexes_2a()
    {
        int N = 3;
        int SIZE = alphabet.size();

        List<List<String>> sublists =
                IntStream.range(0, (SIZE+N-1)/N) // Adjusting the range
                        .mapToObj(
                                i -> alphabet.subList(
                                        N*i, Math.min(SIZE, N*(i+1)) //Protecting the bounds with Math.min()
                            )
                        )
                        .collect(Collectors.toList()); // TODO

        System.out.println(sublists);
    }

    /**
     * Write the previous code using rangeClosed().
     */
    @Test
    public void streamIndexes_2b()
    {
        int N = 3;
        int SIZE = alphabet.size();

        List<List<String>> sublists =
                IntStream.rangeClosed(0, ((SIZE+N)/N)-1) // Adjusting the range, it is inclusive of the endpoint
                        .mapToObj(
                                i -> alphabet.subList(N*i, Math.min(SIZE, N*(i+1))) //Protecting the bounds with Math.min())
                        )
                        .collect(Collectors.toList()); // TODO

        System.out.println(sublists);
    }

    /**
     * From the alphabet list,
     * produce a list of overlapping sublists
     * of length N (Sliding Window).
     */
    @Test
    public void streamIndexes_3()
    {
        int N = 3;
        int SIZE = alphabet.size();

        List<List<String>> sublists =
                IntStream.rangeClosed(0, SIZE-N) // or range(0, SIZE-N+1)
                        .mapToObj(i -> alphabet.subList(i, i+N))
                        .collect(Collectors.toList()); //TODO

        System.out.println(sublists);
    }

    /**
     * Split the alphabet list into runs(sublists)
     * of strings od non-decreasing length,
     * preserving the order.
     * That is, within each sublist, the next string
     * should always be the same length or longer.
     * ---
     * Insights:
     * A new sublist starts when this string is shorter
     * than the previous string.
     * Find the indexes where this occurs.
     * ---
     * We want sublists between these breaks.
     * Run a stream over the breaks to generate sublists.
     */
    @Test
    public void streamIndexes_4a()
    {
        // Finding the indexes where we need to partition
        List<Integer> breaks =
                IntStream.range(1, alphabet.size())
                        .filter(i -> alphabet.get(i).length() < alphabet.get(i-1).length())
                        .boxed() //Converts int stream to Integer stream
                        .toList(); // TODO

        System.out.println(breaks);

        List<List<String>> sublists =
                IntStream.range(0, breaks.size()-1)
                        .mapToObj(i -> alphabet.subList(breaks.get(i), breaks.get(i+1)))
                        .collect(Collectors.toList()); // TODO

        // Head[alfa, bravo, charlie] and Tail[zulu]
        // are not included
        System.out.println(sublists);
    }

    /**
     * Add starting and ending indexes to breaks list
     * to pick up the leading and trailing sublists.
     */
    @Test
    public void streamIndexes_4b()
    {
        List<Integer> breaks =
                IntStream.range(1, alphabet.size())
                        .filter(i -> alphabet.get(i).length() < alphabet.get(i-1).length())
                        .boxed()
                        .collect(Collectors.toList()); // TODO

        breaks.addFirst(0); // To include the Head, breaks.add(0, 0);
        breaks.add(alphabet.size()); // To include the tail

        System.out.println(breaks);

        List<List<String>> sublists =
                IntStream.range(0, breaks.size()-1)
                        .mapToObj(i -> alphabet.subList(breaks.get(i), breaks.get(i+1)))
                        .collect(Collectors.toList()); // TODO

        // Include the Head and the Tail
        System.out.println(sublists);
    }

}
