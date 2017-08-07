package andy.zhu.codewars.isomorphism;

import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static andy.zhu.codewars.isomorphism.ISO.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Andy.Zhu on 2017/8/7.
 */

public class ISOTest {

    @Test
    public void subStLTest() {
        assertEquals(false, subStL(isoBool()).apply(false));
        assertEquals(true, subStL(isoBool()).apply(true));
    }

    @Test
    public void subStRTest() {
        assertEquals(true, subStR(isoBool()).apply(true));
        assertEquals(false, subStR(isoBool()).apply(false));
    }
}
