package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;
import static edu.kit.informatik.bibliography.tests.TestHelper.*;

public class ListPublicationsTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void ListPublicationsInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "add author A,B",
            "add conference series A",
            "add conference A,2000,A",
            "all publications ",
            "all publications ,",
            "all publications,",
            "all publications, ",
            "all publications  ",
            "all publications , ",
            "all publications a",
            "list invalid publications ",
            "list invalid publications ,",
            "list invalid publications,",
            "list invalid publications, ",
            "list invalid publications  ",
            "list invalid publications , ",
            "list invalid publications a",
            "publications by",
            "publications by ",
            "publications by  ",
            "publications by ,",
            "publications by,",
            "publications by , ",
            "publications by A B,b",
            "publications by b,A B",
            "in proceedings",
            "in proceedings ",
            "in proceedings,",
            "in proceedings, ",
            "in proceedings ,",
            "in proceedings , ",
            "in proceedings A",
            "in proceedings 2000",
            "in proceedings A,",
            "in proceedings 2000,",
            "in proceedings A, ",
            "in proceedings 2000, ",
            "in proceedings ,A",
            "in proceedings ,2000",
            "in proceedings  ,A",
            "in proceedings  ,2000",
            "in proceedings A,2000,a",
            "in proceedings a,A,2000",
            "find keywords",
            "find keywords ",
            "find keywords a,b",
            "find keywords a,2000",
            "find keywords 2000,a",
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
    public void PublicationsByInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "add author A,B",
            "add author C,D",
            "publications by A,B",
            "publications by A;B",
            "publications by C;D",
            "publications by;",
            "publications by ;",
            "publications by A B;",
            "publications by A B ",
            "publications by  A B",
            "publications by A B; ",
            "publications by  A B;C D",
            "publications by A B;C D ",
            "publications by ;A B",
            "publications by  ;A B",
            "publications by A B; C D",
            "publications by A B ;C D",
            "publications by A B ; C D",
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
    public void InProceedingsInvalidArgumentFormatTest()
    {
        Terminal.initialize(
            "add conference series A",
            "add conference A,2000,A",
            "in proceedings 2000,A",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
            Terminal.ok,
            Terminal.ok,
            Terminal.error,
            Terminal.ok
        }, Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "find keywords A",
            "find keywords 2",
            "find keywords !",
            "find keywords a;",
            "find keywords ;a",
            "find keywords ;",
            "find keywords  ;a",
            "find keywords a; ",
            "find keywords ; ",
            "find keywords  ; ",
            "find keywords a;!",
            "find keywords a;!;b",
            "find keywords a;b;!",
            "find keywords a;b;!;c",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.addAll(Collections.nCopies(commands.length - 1, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void AllPublicationsValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add conference series A",
            "add conference A,2000,A",
            "add article to journal A:b,2000,A",
            "add article to series A:a,2000,A",
            "all publications",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(5, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());

        Terminal.finish();
    }

    @Test
    public void ListInvalidPublicationsValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "add journal A,B",
            "add conference series A",
            "add conference A,2000,A",
            "add author A,B",
            "add article to journal A:b,2000,A",
            "add article to series A:a,2000,A",
            "add article to journal A:c,2000,A",
            "written-by b,A B",
            "list invalid publications",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(8, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("c"),
                new Terminal.StringOutput("a")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());

        Terminal.finish();
    }

    @Test
    public void PublicationsByValidArguments1Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B;C D",
            "written-by b,A B",
            "written-by c,C D",
            "publications by A B",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PublicationsByValidArguments2Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B;C D",
            "written-by b,A B",
            "written-by c,C D",
            "publications by A B;C D",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b"),
                new Terminal.StringOutput("c")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void InProceedingsValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference series B",
            "add conference A,2000,A",
            "add conference A,2001,A",
            "add conference B,2000,A",
            "add article to series A:a,2000,A",
            "add article to series A:b,2000,A",
            "add article to series A:c,2001,A",
            "add article to series B:d,2000,A",
            "in proceedings A,2000",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsValidArguments0Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add keywords to pub a:a",
            "find keywords a",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add article to journal A:d,2000,A",
            "add keywords to pub a:a;b;c",
            "add keywords to pub b:b;c;d",
            "add keywords to pub c:a;c;d",
            "add keywords to pub d:a;b;d",
            "find keywords b;c",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PublicationsByAllowDuplicateAuthorTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B;C D",
            "written-by b,A B",
            "written-by c,C D",
            "publications by A B;A B",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsAllowDuplicateKeywordsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add article to journal A:d,2000,A",
            "add keywords to pub a:a;b;c",
            "add keywords to pub b:b;c;d",
            "add keywords to pub c:a;c;d",
            "add keywords to pub d:a;b;d",
            "find keywords b;c;c;b;b;c;c;c;c;b",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PublicationsByAuthorMustExistTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add author A,B",
            "written-by a,A B",
            "publications by A B;C D",
            "publications by C D",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertArrayEquals(new Terminal.Output[]
        {
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
    public void AllPublicationsNoArticlesDoNotPrintNewlineTest()
    {
        Terminal.initialize(
            "all publications",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test public void ListInvalidPublicationsNoArticlesDoNotPrintNewlineTest()
    {
        Terminal.initialize(
            "list invalid publications",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test public void ListInvalidPublicationsNoArticlesDoNotPrintNewline2Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add conference series A",
            "add conference A,2000,A",
            "add author A,B",
            "add article to journal A:b,2000,A",
            "add article to series A:a,2000,A",
            "add article to journal A:c,2000,A",
            "written-by a,A B",
            "written-by b,A B",
            "written-by c,A B",
            "list invalid publications",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.ok));

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PublicationsByNoArticlesDoNotPrintNewlineTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add author A,B",
            "add author C,D",
            "written-by a,A B",
            "publications by C D",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.ok));

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void InProceedingsNoArticlesDoNotPrintNewlineTest()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference series B",
            "add conference A,2000,A",
            "add conference A,2001,A",
            "add conference B,2001,A",
            "add article to series A:a,2000,A",
            "add article to series A:b,2000,A",
            "add article to series B:d,2001,A",
            "in proceedings A,2001",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.ok));

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsNoArticlesDoNotPrintNewlineTest()
    {
        String[] commands = new String[]
        {
            "add journal A,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add keywords to pub a:a;b",
            "add keywords to pub b:b;c",
            "find keywords a;c",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>(
            Collections.nCopies(commands.length - 1, Terminal.ok));

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsInheritKeywords1Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add article to journal A:d,2000,A",
            "add keywords to pub a:a;b;c",
            "add keywords to pub b:b;c;d",
            "add keywords to pub c:a;c;d",
            "add keywords to pub d:a;b;d",
            "add keywords to journal A:e",
            "find keywords b;c;e",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsInheritKeywords2Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add journal B,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add article to journal A:d,2000,A",
            "add article to journal B:e,2000,A",
            "add keywords to pub a:a;b;c",
            "add keywords to pub b:b;c;d",
            "add keywords to pub c:a;c;d",
            "add keywords to pub d:a;b;d",
            "add keywords to journal A:e;f",
            "find keywords e;f",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("b"),
                new Terminal.StringOutput("c"),
                new Terminal.StringOutput("d")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsInheritKeywords3Test()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add journal B,A",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add article to journal A:c,2000,A",
            "add article to journal A:d,2000,A",
            "add article to journal B:e,2000,A",
            "add keywords to pub a:a;b;c",
            "add keywords to pub b:b;c;d",
            "add keywords to pub c:a;c;d",
            "add keywords to pub d:a;b;d",
            "add keywords to pub e:a;f",
            "add keywords to journal A:e;f",
            "add keywords to journal B:e",
            "find keywords e;a;f",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
                new Terminal.StringOutput("e"),
                new Terminal.StringOutput("c"),
                new Terminal.StringOutput("d")
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void FindKeywordsInheritKeywords4Test()
    {
        String[] commands = new String[]
        {
            "add conference series A",
            "add conference A,2000,A",
            "add conference A,2001,A",
            "add article to series A:a,2000,A",
            "add article to series A:b,2001,A",
            "add keywords to series A:a",
            "add keywords to conference A,2000:b",
            "add keywords to conference A,2001:c",
            "add keywords to pub a:d",
            "add keywords to pub b:d",
            "find keywords a;d;b",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertMiddleInAnyOrder(
            Collections.nCopies(commands.length - 2, Terminal.ok).toArray(
                new Terminal.Output[0]),
            new Terminal.Output[] {
                new Terminal.StringOutput("a"),
            },
            new Terminal.Output[] { Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

}

// vim: set expandtab:
