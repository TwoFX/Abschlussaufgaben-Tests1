package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class AddAuthorTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void AddAuthorInvalidArgumentCountTest()
    {

        String[] commands = new String[]
        {
            "add author",
            "add author ",
            "add author , ",
            "add author A",
            "add author ,",
            "add author A,",
            "add author ,A",
            "add author  ,B",
            "add author , ",
            "add author ,A,B",
            "add author A,,B",
            "add author A,B,",
            "add author A B",
            "add author,A,B",
            "add authorA,B",
            "add author A,B,C",
            "add author ,A,B,C",
            "add author A,B ,C",
            "add author A,B,C,D",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> outp = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.error));
        outp.add(Terminal.ok);

        assertArrayEquals(outp.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddAuthorInvalidArgumentFormatTest()
    {

        String[] commands = new String[]
        {
            "add author A a,B",
            "add author A,B b",
            "add author A ,b",
            "add author A,b ",
            "add author A, b",
            "add author D.,Wagner",
            "add author Titus,O'Neil",
            "add author a1,B",
            "add author A,b1",
            "add author 345,A",
            "add author B,123",
            "add author A&B,C",
            "add author C,B^t",
            "add author 123,456",
            "add author Professor,Mueller-Quade",
            "add author Professor,Müller",
            "add author Marku5,Himmel",
            "add author Markus,Himme1",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> outp = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.error));
        outp.add(Terminal.ok);

        assertArrayEquals(outp.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddAuthorValidArgumentFormatTest()
    {

        String[] commands = new String[]
        {
            "add author Markus,Himmel",
            "add author markus,himmel",
            "add author m,h",
            "add author marku,himme",
            "add author Himmel,Markus",
            "add author Markus,Markus",
            "add author Himmel,Himmel",
            "add author Lukas,Himmel",
            "add author Lukas,Graham",
            "add author L,Himmel",
            "add author LUKAS,HIMMEL",
            "add author lUKAS,hIMMEL",
            "add author LuKaS,HiMmEl",
            "add author aaaaaaaaaaa,bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
            "add author null,null",
            "add author null,himmel",
            "add author null,nil",
            "add author nil,nil",
            "add author NaN,NaN",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> outp = new ArrayList<>(
            Collections.nCopies(commands.length, Terminal.ok));

        assertArrayEquals(outp.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AddAuthorTwiceTest()
    {
        Terminal.initialize(
            "add author Markus,Himmel",
            "add author Markus,HimmeL",
            "add author Markus,Himmel",
            "add author Markus,HimmEl",
            "add author Markus,HimmeL",
            "add author Markus,HimMel",
            "add author Markus Himmel",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[] {
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok,
            Terminal.error,
            Terminal.ok,
            Terminal.error,
            Terminal.ok }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
