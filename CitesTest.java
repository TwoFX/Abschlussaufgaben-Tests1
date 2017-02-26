package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class CitesTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void CitesInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,1999,A",
            "add article to journal A:c,1998,A",
            "cites a",
            "cites a,",
            "cites a, ",
            "cites ,a",
            "cites a ",
            "cites  ,a",
            "cites  a",
            "cites a,,b",
            "cites a, b",
            "cites a , b",
            "cites a,b,c",
            "cites a,b,",
            "cites ,a,b",
            "cites a,b, ",
            "cites  ,a,b",
            "cites",
            "cites ",
            "cites ,",
            "cites , ",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.addAll(Collections.nCopies(4, Terminal.ok));
        expected.addAll(Collections.nCopies(commands.length - 5, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void CitesValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,1999,A",
            "add article to journal A:c,1998,A",
            "add article to journal A:d,1997,A",
            "cites a,b",
            "cites a,c",
            "cites a,d",
            "cites b,c",
            "cites b,d",
            "cites c,d",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length, Terminal.ok));

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void CitesArticleMustExistTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,1999,A",
            "add article to journal A:c,1998,A",
            "cites b,c",
            "cites b,d",
            "cites d,b",
            "cites a,b",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.error,
            Terminal.ok,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void CitesDuplicateTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,1999,A",
            "add article to journal A:c,1998,A",
            "cites a,b",
            "cites a,c",
            "cites b,c",
            "cites a,b",
            "cites b,c",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void CitesYearMustMatchTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,1999,A",
            "add article to journal A:c,1998,A",
            "add article to journal A:d,1998,A",
            "cites a,b",
            "cites c,b",
            "cites b,c",
            "cites c,c",
            "cites c,d",
            "cites d,c",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok,
            Terminal.error,
            Terminal.error,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

}

// vim: set expandtab:
