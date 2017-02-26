package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class CompleteTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void immediatelyQuitTest()
    {
        Terminal.initialize("quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[] { Terminal.ok }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void barfOnInvalidCommands1Test()
    {
        // This test contains strings that should not match to any matcher,
        // inputs which should match but have the wrong argument format
        // are tested elsewhere.
        String[] commands = new String[]
        {
            "exit",
            "qui",
            "quitt",
            "add Eniola,Lowry",
            "add ICSA",
            "author Eniola,Lowry",
            "addd author En,Lo",
            "add authr En,Lo",
            "add-author En,Lo",
            "add_author En,Lo",
            "addauthor En,Lo",
            "add  author En,Lo",
            "addd journal TSE,IEEE",
            "add jornal TSE,IEEE",
            "add-journal TSE,IEEE",
            "add_journal TSE,IEEE",
            "addjournal TSE,IEEE",
            "add  journal TSE,IEEE",
            "journal TSE,IEEE",
            "addd conference series ICSA",
            "add conference ICSA",
            "add series ICSA",
            "add confrence series ICSA",
            "add conference serie ICSA",
            "add  conference series ICSA",
            "add conference  series ICSA",
            "add-conference-series ICSA",
            "add conference-series ICSA",
            "add conferenceseries ICSA",
            "add_conference_series ICSA",
            "add conference_series ICSA",
            "addconferenceseries ICSA",
            "conference series ICSA",
            "series ICSA",
            "add series conference ICSA",
            "add confrence ICSA,2017,Gothenburg",
            "add-conference ICSA,2017,Gothenburg",
            "add_conference ICSA,2017,Gothenburg",
            "addconference ICSA,2017,Gothenburg",
            "add article ICSA:rr2017,2017,A B",
            "add article conference ICSA:rr2017,2017,A B",
            "add article to confrence ICSA:rr2017,2017,A B",
            "add article to jornal ICSA:rr2-17,2017,A B",
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
    public void barfOnInvalidCommands2Test()
    {
        String[] commands = new String[]
        {
            "sdfkodsjfk",
            "sdkfjdsklfjdkslafjdklsajdsklfjlkdsghjiuoerngkroaesbgnioesjgvsdngaiojerwisgfaies",
            "a",
            "b",
            "",
            " ",
            "	",
            "\u0000",
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
    public void hypotheticalTest()
    {
        Terminal.initialize("add author A,B", "add author", "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[] {
            Terminal.ok,
            Terminal.error,
            Terminal.ok }, Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
