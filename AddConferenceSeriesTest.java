package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class AddConferenceSeriesTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void AddConferenceSeriesInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add conference series",
            "add conference series ",
            "add conference series  ",
            "add conference series  ,",
            "add conference series, ",
            "add conference series , ",
            "add conference series,",
            "add conference series A,",
            "add conference series A,B",
            "add conference series A, ",
            "add conference series A B,",
            "add conference series A,B,C",
            "add conference series,A",
            "add conference series ,,",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }


    @Test
    public void AddConferenceSeriesInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
//            "add conference series  A",
//            "add conference series A ",
//            "add conferense series 	A",
            "add conference series	A",
//            "add conference series A	",
            "add conference series A,",
            "add conference series ;",
            "add conference series A;B",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddConferenceSeriesValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "add conference series %",
            "add conference series %%",
            "add conference series A B",
            "add conference series A	B",
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
    public void AddConferenceSeriesTwiceTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference series B",
            "add conference series B",
            "add conference series C",
            "add conference series A",
            "add conference series D",
            "add conference series E",
            "add conference series C",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok,
            Terminal.error,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
