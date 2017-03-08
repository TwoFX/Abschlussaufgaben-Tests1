package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class AddJournalTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void AddJournalInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add journal",
            "add journal ",
            "add journal , ",
            "add journal A",
            "add journal ,",
            "add journal A,",
            "add journal ,A",
//            "add journal  ,B",
            "add journal , ",
            "add journal ,A,B",
            "add journal A,,B",
            "add journal A,B,",
            "add journal A B",
            "add journal,A,B",
            "add journalA,B",
            "add journal A,B,C",
            "add journal ,A,B,C",
            "add journal A,B ,C",
            "add journal A,B,C,D",
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
    public void AddJournalInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
//            "add journal  A,B",
//            "add journal A ,B",
//            "add journal A, B",
//            "add journal A,B ",
//            "add journal  A , B ",
//            "add journal A B ,C",
//            "add journal A B, C D",
            "add journal	A,B",
//            "add journal 	A,B",
            "add journal Journal of Neurobiology, Molecular Neuroscience and General Biology,Elsevier",
            "add journal A;B,C",
            "add journal A,B;C",
            "add journal A;B",
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
    public void AddJournalValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "add journal %,%",
            "add journal %%,%%",
            "add journal A B,C D",
            "add journal A	B,C	D",
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
    public void AddJournalTwiceTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add journal B,B",
            "add journal A,C",
            "add journal D,E",
            "add journal D,F",
            "add journal E,A",
            "add journal C,C",
            "add journal B,X",
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
