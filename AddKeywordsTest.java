package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class AddKeywordsTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void AddKeywordsInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add conference series A",
            "add conference A,2000,A",
            "add article to journal A:a,2000,A",
            "add keywords to pub",
            "add keywords to pub ",
            "add keywords to pub  ",
            "add keywords to pub   ",
            "add keywords to pub,",
            "add keywords to pub, ",
            "add keywords to pub ,",
            "add keywords to pub , ",
            "add keywords to pub  ,",
            "add keywords to pub  , ",
            "add keywords to pub a",
            "add keywords to pub a,",
            "add keywords to pub a, ",
            "add keywords to pub ,a",
            "add keywords to pub  ,a",
            "add keywords to pub ,a,",
            "add keywords to pub  ,a, ",
            "add keywords to pub a,,a",
            "add keywords to pub a, ,a",
            "add keywords to pub ,a,a",
            "add keywords to pub  ,a,a",
            "add keywords to pub a,a,",
            "add keywords to pub a,a, ",
            "add keywords to pub  a,a",
            "add keywords to pub a,a ",
            "add keywords to puba,a",
            "add keywords to pub a,a,a",
            "add keywords to pub a,a,2000",
            "add keywords to pub a,2000,a",
            "add keywords to pub 2000,a,a",
            "add keywords to journal",
            "add keywords to journal ",
            "add keywords to journal  ",
            "add keywords to journal   ",
            "add keywords to journal,",
            "add keywords to journal, ",
            "add keywords to journal ,",
            "add keywords to journal , ",
            "add keywords to journal  ,",
            "add keywords to journal  , ",
            "add keywords to journal a",
            "add keywords to journal a,",
            "add keywords to journal a, ",
            "add keywords to journal ,a",
            "add keywords to journal  ,a",
            "add keywords to journal ,a,",
            "add keywords to journal  ,a, ",
            "add keywords to journal A",
            "add keywords to journal A,",
            "add keywords to journal A, ",
            "add keywords to journal ,A",
            "add keywords to journal  ,A",
            "add keywords to journal ,A,",
            "add keywords to journal  ,A, ",
            "add keywords to journal A,,a",
            "add keywords to journal A, ,a",
            "add keywords to journal ,A,a",
            "add keywords to journal  ,A,a",
            "add keywords to journal A,a,",
            "add keywords to journal A,a, ",
            "add keywords to journal  A,a",
            "add keywords to journal A,a ",
            "add keywords to journalA,a",
            "add keywords to journal A,a,a",
            "add keywords to journal A,a,2000",
            "add keywords to journal A,2000,a",
            "add keywords to journal 2000,A,a",
            "add keywords to series",
            "add keywords to series ",
            "add keywords to series  ",
            "add keywords to series   ",
            "add keywords to series,",
            "add keywords to series, ",
            "add keywords to series ,",
            "add keywords to series , ",
            "add keywords to series  ,",
            "add keywords to series  , ",
            "add keywords to series a",
            "add keywords to series a,",
            "add keywords to series a, ",
            "add keywords to series ,a",
            "add keywords to series  ,a",
            "add keywords to series ,a,",
            "add keywords to series  ,a, ",
            "add keywords to series A",
            "add keywords to series A,",
            "add keywords to series A, ",
            "add keywords to series ,A",
            "add keywords to series  ,A",
            "add keywords to series ,A,",
            "add keywords to series  ,A, ",
            "add keywords to series A,,a",
            "add keywords to series A, ,a",
            "add keywords to series ,A,a",
            "add keywords to series  ,A,a",
            "add keywords to series A,a,",
            "add keywords to series A,a, ",
            "add keywords to series  A,a",
            "add keywords to series A,a ",
            "add keywords to seriesA,a",
            "add keywords to series A,a,a",
            "add keywords to series A,a,2000",
            "add keywords to series A,2000,a",
            "add keywords to series 2000,A,a",
            "add keywords to conference",
            "add keywords to conference ",
            "add keywords to conference  ",
            "add keywords to conference   ",
            "add keywords to conference,",
            "add keywords to conference, ",
            "add keywords to conference ,",
            "add keywords to conference , ",
            "add keywords to conference  ,",
            "add keywords to conference  , ",
            "add keywords to conference a",
            "add keywords to conference a,",
            "add keywords to conference a, ",
            "add keywords to conference ,a",
            "add keywords to conference  ,a",
            "add keywords to conference ,a,",
            "add keywords to conference  ,a, ",
            "add keywords to conference A",
            "add keywords to conference A,",
            "add keywords to conference A, ",
            "add keywords to conference ,A",
            "add keywords to conference  ,A",
            "add keywords to conference ,A,",
            "add keywords to conference  ,A, ",
            "add keywords to conference A,,a",
            "add keywords to conference A, ,a",
            "add keywords to conference ,A,a",
            "add keywords to conference  ,A,a",
            "add keywords to conference A,a,",
            "add keywords to conference A,a, ",
            "add keywords to conference  A,a",
            "add keywords to conference A,a ",
            "add keywords to conferenceA,a",
            "add keywords to conference A,a,a",
            "add keywords to conference A,a,2000",
            "add keywords to conference 2000,A,a",
            "add keywords to conference A,,2000,a",
            "add keywords to conference A,2000,,a",
            "add keywords to conference A,a",
            "add keywords to conference a,A,2000,a",
            "add keywords to conference A,2000,a,a",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.addAll(Collections.nCopies(4,Terminal.ok));
        expected.addAll(Collections.nCopies(commands.length - 5, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }


    @Test
    public void AddKeywordsInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add conference series A",
            "add conference A,2000,A",
            "add article to journal A:a,2000,A",
            "add keywords to pub a:;",
            "add keywords to pub a:a;",
            "add keywords to pub a:;a",
            "add keywords to pub a: a",
            "add keywords to pub a:a ;a",
            "add keywords to pub a:a; a",
            "add keywords to pub a:a ; a",
            "add keywords to pub a;a:a",
            "add keywords to journal A:;",
            "add keywords to journal A:a;",
            "add keywords to journal A:;a",
            "add keywords to journal A: a",
            "add keywords to journal A:a ;a",
            "add keywords to journal A:a; a",
            "add keywords to journal A:a ; a",
            "add keywords to journal A;a:a",
            "add keywords to series A:;",
            "add keywords to series A:a;",
            "add keywords to series A:;a",
            "add keywords to series A: a",
            "add keywords to series A:a ;a",
            "add keywords to series A:a; a",
            "add keywords to series A:a ; a",
            "add keywords to series A;a:a",
            "add keywords to conference A,2000:;",
            "add keywords to conference A,2000:a;",
            "add keywords to conference A,2000:;a",
            "add keywords to conference A,2000: a",
            "add keywords to conference A,2000:a ;a",
            "add keywords to conference A,2000:a; a",
            "add keywords to conference A,2000:a ; a",
            "add keywords to conference A,2000;a:a",
            "add keywords to pub a:1",
            "add keywords to pub a:%",
            "add keywords to pub a:A",
            "add keywords to pub a:aA",
            "add keywords to pub a:a!",
            "add keywords to pub a:aAa",
            "add keywords to pub a:!a",
            "add keywords to pub a:a;5",
            "add keywords to pub a:a;A",
            "add keywords to pub a:a;aAa",
            "add keywords to journal A:1",
            "add keywords to journal A:%",
            "add keywords to journal A:A",
            "add keywords to journal A:aA",
            "add keywords to journal A:a!",
            "add keywords to journal A:aAa",
            "add keywords to journal A:!a",
            "add keywords to journal A:a;5",
            "add keywords to journal A:a;A",
            "add keywords to journal A:a;aAa",
            "add keywords to series A:1",
            "add keywords to series A:%",
            "add keywords to series A:A",
            "add keywords to series A:aA",
            "add keywords to series A:a!",
            "add keywords to series A:aAa",
            "add keywords to series A:!a",
            "add keywords to series A:a;5",
            "add keywords to series A:a;A",
            "add keywords to series A:a;aAa",
            "add keywords to conference A,2000:1",
            "add keywords to conference A,2000:%",
            "add keywords to conference A,2000:A",
            "add keywords to conference A,2000:aA",
            "add keywords to conference A,2000:a!",
            "add keywords to conference A,2000:aAa",
            "add keywords to conference A,2000:!a",
            "add keywords to conference A,2000:a;5",
            "add keywords to conference A,2000:a;A",
            "add keywords to conference A,2000:a;aAa",
            "add keywords to series A,a",
            "add keywords to journal A,a",
            "add keywords to conference A,2000,a",
            "add keywords to pub a,a",
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
    public void AddKeywordsValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "add journal A,A",
            "add conference series A",
            "add conference A,2000,A",
            "add article to journal A:a,2000,A",
            "add keywords to pub a:a",
            "add keywords to pub a:b;c",
            "add keywords to pub a:d;e;f;sdfjsfoiejfioefdsahfuiehsfklesf",
            "add keywords to journal A:a",
            "add keywords to journal A:b;c",
            "add keywords to journal A:d;e;f;sdfjsfoiejfioefdsahfuiehsfklesf",
            "add keywords to series A:a",
            "add keywords to series A:b;c",
            "add keywords to series A:d;e;f;sdfjsfoiejfioefdsahfuiehsfklesf",
            "add keywords to conference A,2000:a",
            "add keywords to conference A,2000:b;c",
            "add keywords to conference A,2000:d;e;f;sdfjsfoiejfioefdsahfuiehsfklesf",
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
    public void AddKeywordsEntitesMustExistTest()
    {
        Terminal.initialize(
            "add journal A,A",
            "add conference series B",
            "add conference B,2000,A",
            "add article to journal A:a,2000,A",
            "add keywords to pub b,a",
            "add keywords to journal B:a",
            "add keywords to series A:a",
            "add keywords to conference A,2000:a",
            "add keywords to conference B,2001:a",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.error,
            Terminal.error,
            Terminal.error,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddKeywordsAllowDuplicatesTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add conference series A",
            "add conference A,2000,A",
            "add article to journal A:a,2000,A",
            "add keywords to pub a:a",
            "add keywords to pub a:b;a;c",
            "add keywords to pub a:c",
            "add keywords to pub a:d;d",
            "add keywords to journal A:a",
            "add keywords to journal A:b;a;c",
            "add keywords to journal A:c",
            "add keywords to journal A:d;d",
            "add keywords to series A:a",
            "add keywords to series A:b;a;c",
            "add keywords to series A:c",
            "add keywords to series A:d;d",
            "add keywords to conference A,2000:a",
            "add keywords to conference A,2000:b;a;c",
            "add keywords to conference A,2000:c",
            "add keywords to conference A,2000:d;d",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length, Terminal.ok));

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
