package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class AddConferenceTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void AddConferenceInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference ",
            "add conference  ",
            "add conference   ",
            "add conference   ,",
            "add conference , ",
            "add conference  , ",
            "add conference ,,",
            "add conference , , ",
            "add conference  , , ",
            "add conference ,",
            "add conference  A,",
            "add conference  A,B",
            "add conference  A, ",
            "add conference  A B,",
            "add conference ,A",
            "add conference A,2000",
            "add conference A,2000,",
//            "add conference A,2000, ",
            "add conference A,,G",
            "add conference A G",
            "add conference A,G",
            "add conference A, ,G",
            "add conference  ,,",
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
    public void AddConferenceInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference A,B,C",
            "add conference A B,B,B C",
//            "add conference  ,2000,A",
//            "add conference A,2000, ",
//            "add conference  A,2000,B",
//            "add conference A ,2000,B",
//            "add conference A,2000,B ",
//            "add conference A,2000, B",
            "add conference	A,2000,B",
//            "add conference 	A,2000,B",
//            "add conference A,2000,	B",
//            "add conference A,2000,B	",
            "add conference A, 2000,B",
//            "add conference A,2000, B",
            "add conference A,	2000,B",
//            "add conference A,2000,	B",
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
    public void AddConferenceInvalidNumericArgumentTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference A,0,B",
            "add conference A,1,B",
            "add conference A,999,B",
            "add conference A,0999,B",
            "add conference A,10000,B",
            "add conference A,02000,B",
            "add conference A,2000.0,B",
            "add conference A,2e3,B",
            "add conference A,2.0e3,B",
            "add conference A,1e3,B",
            "add conference A,10^3,B",
            "add conference A,2*10^3,B",
            "add conference A,999.9999999999,B",
            "add conference A,1000.0000000000,B",
            "add conference A,M,B",
            "add conference A,MMXVII,B",
            "add conference A,CMXCIX,B",
            "add conference A,1.990,B",
            "add conference A,1,990,B",
            "add conference A,1.990,000,B",
            "add conference A,1,990.000,B",
            "add conference A,2017 AD,B",
            "add conference A,2017 A.D.,B",
            "add conference A,2017-02-15,B",
            "add conference A,2017-02-15T11:29:15+00:00,B",
            "add conference A,the year 2017,B",
            "add conference A,Feb 15, 2017,B",
            "add conference A,15. Februar 2017,B",
            "add conference A,15 Feb 2017,B",
            "add conference A,zweitausendsiebzehn,B",
            "add conference A,-1,B",
            "add conference A,4294967297,B",
            "add conference A,-4294967297,B",
            "add conference A,18446744073709551617,B",
            "add conference A,340282366920938463463374607431768211457,B",
            "add conference A,10011100001111,B",
            "add conference A,0b10011100001111,B",
            "add conference A,270f,B",
            "add conference A,270F,B",
            "add conference A,0x270f,B",
            "add conference A,0x270F,B",
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
    public void AddConferenceValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference series A B",
            "add conference A,2017,B",
            "add conference A,1000,B",
            "add conference A,9999,B",
            "add conference A B,2017,B C",
            "add conference A,2016,B	C",
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
    public void AddConferenceSeriesMustExistTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference series B",
            "add conference A,2017,A",
            "add conference A,2016,B",
            "add conference A,2015,C",
            "add conference C,2017,D",
            "add conference C,2017,A",
            "add conference A',2017,E",
            "add conference A*,2017,A",
            "add conference B,2016,A",
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
            Terminal.error,
            Terminal.error,
            Terminal.ok,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddConferenceTwiceTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference series B",
            "add conference A,2017,B",
            "add conference A,2018,B",
            "add conference B,2017,B",
            "add conference A,2017,A",
            "add conference A,2017,E",
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
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
