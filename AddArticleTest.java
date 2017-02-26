package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class AddArticleTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void AddArticleToJournalInvalidArgumentCoutTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal:a,2000,A",
            "add article to journal A:2000,A",
            "add article to journal A:a,A",
            "add article to journal A:a,2000",
            "add article to journal a,2000,A",
            "add article to journal A:a,2000,A,a",
            "add article to journal A:a,2000,A,2000",
            "add article to journal A",
            "add article to journal",
            "add article to journal ",
            "add article to journal A:,,",
            "add article to journal A ",
            "add article to journal  A:a,2000,A",
//            "add article to journal A:a,2000,A ",
            "add article to journal :,,",
            "add article to journal :a,2000,A",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.add(Terminal.ok);
        expected.addAll(Collections.nCopies(commands.length - 2, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddArticleToSeriesInvalidArgumentCoutTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference A,2000,B",
            "add article to series:a,2000,A",
            "add article to series A:2000,A",
            "add article to series A:a,A",
            "add article to series A:a,2000",
            "add article to series a,2000,A",
            "add article to series A:a,2000,A,a",
            "add article to series A:a,2000,A,2000",
            "add article to series A",
            "add article to series",
            "add article to series ",
            "add article to series A:,,",
            "add article to series A ",
            "add article to series  A:a,2000,A",
//            "add article to series A:a,2000,A ",
            "add article to series :,,",
            "add article to series :a,2000,A",
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
    public void AddArticleToJournalInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:A,2000,A",
            "add article to journal A:%,2000,A",
            "add article to journal A:a b,2000,A",
            "add article to journal A,a,2000,A",
            "add article to journal A,a:2000,A",
            "add article to journal A,a,2000:A",
            "add article to journal A:a ,2000,A",
            "add article to journal A:a, 2000,A",
            "add article to journal A:a,2000 ,A",
            "add article to journal A:a,	2000,A",
            "add article to journal A:a,2000	,A",
            "add article to journal A:a,2000,A;B",
            "add article to journal A:a,2000,;A",
            "add article to journal A:a,2000,A;",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.add(Terminal.ok);
        expected.addAll(Collections.nCopies(commands.length - 2, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddArticleToSeriesInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference A,2000,B",
            "add article to series A:A,2000,A",
            "add article to series A:%,2000,A",
            "add article to series A:a b,2000,A",
            "add article to series A,a,2000,A",
            "add article to series A,a:2000,A",
            "add article to series A,a,2000:A",
            "add article to series A:a ,2000,A",
            "add article to series A:a, 2000,A",
            "add article to series A:a,2000 ,A",
            "add article to series A:a,	2000,A",
            "add article to series A:a,2000	,A",
            "add article to series A:a,2000,A;B",
            "add article to series A:a,2000,;A",
            "add article to series A:a,2000,A;",
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
    public void AddArticleToJournalInvalidNumericArgumentTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,0,B",
            "add article to journal A:a,1,B",
            "add article to journal A:a,999,B",
            "add article to journal A:a,0999,B",
            "add article to journal A:a,10000,B",
            "add article to journal A:a,02000,B",
            "add article to journal A:a,2000.0,B",
            "add article to journal A:a,2e3,B",
            "add article to journal A:a,2.0e3,B",
            "add article to journal A:a,1e3,B",
            "add article to journal A:a,10^3,B",
            "add article to journal A:a,2*10^3,B",
            "add article to journal A:a,999.9999999999,B",
            "add article to journal A:a,1000.0000000000,B",
            "add article to journal A:a,M,B",
            "add article to journal A:a,MMXVII,B",
            "add article to journal A:a,CMXCIX,B",
            "add article to journal A:a,1.990,B",
            "add article to journal A:a,1,990,B",
            "add article to journal A:a,1.990,000,B",
            "add article to journal A:a,1,990.000,B",
            "add article to journal A:a,2017 AD,B",
            "add article to journal A:a,2017 A.D.,B",
            "add article to journal A:a,2017-02-15,B",
            "add article to journal A:a,2017-02-15T11:29:15+00:00,B",
            "add article to journal A:a,the year 2017,B",
            "add article to journal A:a,Feb 15, 2017,B",
            "add article to journal A:a,15. Februar 2017,B",
            "add article to journal A:a,15 Feb 2017,B",
            "add article to journal A:a,zweitausendsiebzehn,B",
            "add article to journal A:a,-1,B",
            "add article to journal A:a,4294967297,B",
            "add article to journal A:a,-4294967297,B",
            "add article to journal A:a,18446744073709551617,B",
            "add article to journal A:a,340282366920938463463374607431768211457,B",
            "add article to journal A:a,10011100001111,B",
            "add article to journal A:a,0b10011100001111,B",
            "add article to journal A:a,270f,B",
            "add article to journal A:a,270F,B",
            "add article to journal A:a,0x270f,B",
            "add article to journal A:a,0x270F,B",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.add(Terminal.ok);
        expected.addAll(Collections.nCopies(commands.length - 2, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddArticleToSeriesInvalidNumericArgumentTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference A,2000,B",
            "add article to series A:a,0,B",
            "add article to series A:a,1,B",
            "add article to series A:a,999,B",
            "add article to series A:a,0999,B",
            "add article to series A:a,10000,B",
            "add article to series A:a,02000,B",
            "add article to series A:a,2000.0,B",
            "add article to series A:a,2e3,B",
            "add article to series A:a,2.0e3,B",
            "add article to series A:a,1e3,B",
            "add article to series A:a,10^3,B",
            "add article to series A:a,2*10^3,B",
            "add article to series A:a,999.9999999999,B",
            "add article to series A:a,1000.0000000000,B",
            "add article to series A:a,M,B",
            "add article to series A:a,MMXVII,B",
            "add article to series A:a,CMXCIX,B",
            "add article to series A:a,1.990,B",
            "add article to series A:a,1,990,B",
            "add article to series A:a,1.990,000,B",
            "add article to series A:a,1,990.000,B",
            "add article to series A:a,2017 AD,B",
            "add article to series A:a,2017 A.D.,B",
            "add article to series A:a,2017-02-15,B",
            "add article to series A:a,2017-02-15T11:29:15+00:00,B",
            "add article to series A:a,the year 2017,B",
            "add article to series A:a,Feb 15, 2017,B",
            "add article to series A:a,15. Februar 2017,B",
            "add article to series A:a,15 Feb 2017,B",
            "add article to series A:a,zweitausendsiebzehn,B",
            "add article to series A:a,-1,B",
            "add article to series A:a,4294967297,B",
            "add article to series A:a,-4294967297,B",
            "add article to series A:a,18446744073709551617,B",
            "add article to series A:a,340282366920938463463374607431768211457,B",
            "add article to series A:a,10011100001111,B",
            "add article to series A:a,0b10011100001111,B",
            "add article to series A:a,270f,B",
            "add article to series A:a,270F,B",
            "add article to series A:a,0x270f,B",
            "add article to series A:a,0x270F,B",
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
    public void AddArticleToJournalValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add journal B,B",
            "add article to journal A:0,2000,A",
            "add article to journal A:a,9999,A",
            "add article to journal A:b,1000,A",
            "add article to journal B:1,2000,A",
            "add article to journal B:2,2000,A B",
            "add article to journal B:3,2000,%",
            "add article to journal B:4,2000,A	B",
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
    public void AddArticleToSeriesValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference series B",
            "add conference A,2000,B",
            "add conference A,9999,B",
            "add conference B,2000,B",
            "add conference B,1000,B",
            "add article to series A:0,2000,A",
            "add article to series A:a,9999,A",
            "add article to series B:b,1000,A",
            "add article to series B:1,2000,A",
            "add article to series B:2,2000,A B",
            "add article to series B:3,2000,%",
            "add article to series B:4,2000,A	B",
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
    public void AddArticleToJournalJournalMustExistTest()
    {
        Terminal.initialize(
            "add journal Aa,Bb",
            "add journal Cc,Dd",
            "add article to journal Aa:a,2000,A",
            "add article to journal Bb:b,2000,A",
            "add article to journal Cc:c,2000,A",
            "add article to journal Dd:d,2000,A",
            "add article to journal A:e,2000,A",
            "add article to journal C:f,2000,A",
            "add article to journal Aaa:g,2000,A",
            "add article to journal Ccc:h,2000,A",
            "add article to journal X:i,2000,A",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok,
            Terminal.error,
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
    public void AddArticleToSeriesConferenceMustExistTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference series B",
            "add conference A,2000,B",
            "add conference B,2001,B",
            "add article to series A:a,2000,A",
            "add article to series A:b,2001,A",
            "add article to series B:c,2000,A",
            "add article to series B:d,2001,A",
            "add article to series C:e,2000,A",
            "add article to series C:f,2000,A",
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
            Terminal.error,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddArticleToJournalTwiceTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add journal C,D",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal B:a,2000,A",
            "add article to journal A:a,2000,A",
            "add article to journal B:b,2000,A",
            "add article to journal A:b,2000,A",
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
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddArticleToSeriesTwiceTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference series B",
            "add conference A,2000,A",
            "add conference B,2001,B",
            "add article to series A:a,2000,A",
            "add article to series B:b,2001,B",
            "add article to series B:a,2001,C",
            "add article to series A:b,2000,D",
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
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddArticleToBothTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference A,2000,A",
            "add journal A,A",
            "add article to journal A:a,2001,A",
            "add article to series A:b,2000,A",
            "add article to series A:a,2000,A",
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
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
