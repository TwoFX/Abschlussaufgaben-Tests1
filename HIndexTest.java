package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class HIndexTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void DirectHIndexInvalidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "direct h-index",
            "direct h-index ",
            "direct h-index  ",
            "direct h-index ;",
            "direct h-index 1;",
            "direct h-index ;1",
            "direct h-index 1;;1",
            "direct h-index 1 ",
            "direct h-index 1; ",
            "direct h-index a",
            "direct h-index $",
            "direct h-index 1;a",
            "direct h-index a;1",
            "direct h-index A",
            "direct h-index -1",
            "direct h-index _1",
            "direct h-index 1,1",
            "direct h-index 1; 1",
            "direct h-index 1 ;1",
            "direct h-index 1 ; 1",
            "direct h-index 1,a",
            "direct h-index a,1",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.addAll(Collections.nCopies(commands.length - 1, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void DirectHIndexSurviveHugeNumbersTest()
    {
        Terminal.initialize(
            "direct h-index 2147483647",
            "direct h-index 2147483648",
            "direct h-index 4294967295",
            "direct h-index 4294967296",
            "direct h-index 9223372036854775807",
            "direct h-index 9223372036854775808",
            "direct h-index 18446744073709551615",
            "direct h-index 18446744073709551616",
            "direct h-index 100000000000000000000000000000000000000000000",
            "direct h-index 100000000000000000000000000000000000000000000;10000000000000000000000000000000000000000",
            "quit");

        Console.main(null);

        // We don't care what the program outputs here, we just want it to read all
        // the input and not crash. Depending on the implementation, some of the imputs may or may
        // not produce a correct result or an error.

        Terminal.finish();
    }

    @Test
    public void DirectHIndexValidArgumentsTest()
    {
        Terminal.initialize(
            "direct h-index 0",
            "direct h-index 1",
            "direct h-index 2",
            "direct h-index 1;2",
            "direct h-index 2;2",
            "direct h-index 3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3;3",
            "direct h-index 1;2;3;4;5;6;7;8;9;10",
            "direct h-index 10;1;9;2;8;3;7;4;6;5",
            "direct h-index 1;3;5;7;9;11",
            "direct h-index 17;3;1;5",
            "direct h-index 8;6;8;4;8;6",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs("0", "1", "1", "1", "2", "3", "5", "5",
            "4", "3", "5", "Ok"), Terminal.getOutput());
        Terminal.finish();

    }

}

// vim: set expandtab:
