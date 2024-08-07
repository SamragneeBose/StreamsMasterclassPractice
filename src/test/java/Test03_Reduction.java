import org.junit.Test;

import java.math.BigInteger;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Test03_Reduction {

    /**
     * Compute the factorial as a BigInteger using
     * Streams and Reductions
     */
    @Test
    public void reduction_1() {
        long number = 21;

        // TODO
        BigInteger result =
                LongStream.rangeClosed(1, number) // Stream of Long values
                .mapToObj(BigInteger::valueOf) // Converts long to BigInteger
                .reduce(BigInteger.ONE, BigInteger::multiply); // Performs operation on each pair of elements

        assertThat(result).isEqualTo(new BigInteger("51090942171709440000"));
    }
}
