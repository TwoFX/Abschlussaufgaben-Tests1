package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class WrittenByTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void WrittenByInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add author A,B",
            "written-by a",
            "written-by A B",
            "written-by a,",
            "written-by a, ",
            "written-by a,  ",
            "written-by a,; ",
            "written-by a,;  ",
            "written-by a, ; ",
            "written-by a, ;",
            "written-by a,;A B",
            "written-by a, ;A B",
            "written-by a,  ;A B",
            "written-by a,A B;",
            "written-by a,A B; ",
            "written-by a,A B,",
            "written-by a,A B,a",
            "written-by a,A B, ",
            "written-by a,,A B",
            "written-by a,a,A B",
            "written-by ,a,A B",
            "written-by  ,a,A B",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.addAll(Collections.nCopies(3, Terminal.ok));
        expected.addAll(Collections.nCopies(commands.length - 4, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void WrittenByInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by A B,a",
            "written-by a,A B ; C D",
            "written-by a,A B; C D",
            "written-by a,A B ;C D",
            "written-by a,A",
            "written-by a,B",
            "written-by a,A B;;C D",
            "written-by a,A B,C D",
            "written-by a,A,B;C,D",
            "written-by a,A B C D",
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
    public void WrittenByValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B",
            "written-by a,C D",
            "written-by b,A B;C D",
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
    public void WrittenByArticleMustExistTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add author A,B",
            "written-by a,A B",
            "written-by b,A B",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void WrittenByAuthorMustExistTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add author A,B",
            "written-by a,A B",
            "written-by a,C D",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void WrittenByDuplicateTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B",
            "written-by a,C D",
            "written-by a,A B",
            "written-by b,A B;C D;A B",
            "written-by c,A B;C D",
            "written-by c,A B",
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
            Terminal.ok,
            Terminal.error,
            Terminal.error,
            Terminal.ok,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void WrittenByRollbackTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B",
            "written-by a,C D;A B",
            "written-by a,C D",
            "written-by b,C D;A B;A B",
            "written-by b,C D",
            "written-by b,A B",
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
            Terminal.ok,
            Terminal.ok,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
