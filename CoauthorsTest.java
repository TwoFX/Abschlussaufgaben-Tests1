package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;
import static edu.kit.informatik.bibliography.tests.TestHelper.*;

public class CoauthorsTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }


    @Test
    public void CoauthorsInvalidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add author A,B",
            "add author C,D",
            " coauthors of A B",
            "coauthors of A,B",
            "coauthors of A B ",
            "coauthors of A B,",
            "coauthors of  A B",
            "coauthors of A;B",
            "coauthors of A  B",
            "coauthors ofA B",
            "coauthors ofaaaa A B",
            "coauthors of A",
            "coauthors of B",
            "coauthors of B A",
            "coauthors of A B,C D",
            "coauthors of A B C D",
            "coauthors of A B;C D",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.add(Terminal.ok);
        expected.add(Terminal.ok);
        expected.addAll(Collections.nCopies(commands.length - 3, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void CoauthorsValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "add author A,B",
            "add author C,D",
            "add author E,F",
            "add author G,H",
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "written-by a,A B;C D",
            "written-by b,A B;C D;E F",
            "written-by c,C D;E F",
            "coauthors of A B",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(Collections.nCopies(11, Terminal.ok)
            .toArray(new Terminal.Output[0]),
            Terminal.strs("C D", "E F"), Terminal.strs("Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void CoauthorsAuthorMustExistTest()
    {
        Terminal.initialize(
            "coauthors of A B",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
