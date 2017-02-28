package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class PrintBibliographyTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void PrintBibliographyInvalidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add author A,B",
            "written-by a,A B",
            "written-by b,A B",
            " print bibliography ieee:a",
            "print bibliography ieee",
            "print bibliography ieee:",
            "print bibliography ieee: ",
            "print bibliography",
            "print bibliography ",
            "print bibliography  ",
            "print bibliography a",
            "print bibliography ieee:;",
            "print bibliography ieee:; ",
            "print bibliography ieee: ;",
            "print bibliography ieee: ; ",
            "print bibliography ieee:a;",
            "print bibliography ieee:a ",
            "print bibliography ieee: a",
            "print bibliography ieee,a",
            "print bibliography ieee a",
            "print bibliography ieee;a",
            "print bibliography ieee:;a",
            "print bibliography ieee:a;b ",
            "print bibliography ieee: a;b",
            "print bibliography ieee:a ;b",
            "print bibliography ieee:a; b",
            "print bibliography ieee:a ; b",
            "print bibliography ieee:a,b",
            "print bibliopgrahy iee:a",
            "print bibliography ieeee:a",
            "print bibliography chicag:a",
            "print bibliography chicagoo:a",
            "print bibliography ieee :a",
            "print bibliography ieee::a",
            "print bibliography chicago::a",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        ArrayList<Terminal.Output> expected = new ArrayList<>();
        expected.addAll(Collections.nCopies(6, Terminal.ok));
        expected.addAll(Collections.nCopies(commands.length - 7, Terminal.error));
        expected.add(Terminal.ok);

        assertArrayEquals(expected.toArray(), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyValidArguments1Test()
    {
        Terminal.initialize(
            "add journal TSE,Elsevier",
            "add conference series ICSA",
            "add conference ICSA,2017,Gothenburg",
            "add author Shashi,Afolabi",
            "add author Richard,Rhinelander",
            "add author Emiola,Lowry",
            "add article to journal TSE:mvp2015,2015,Model Consistency",
            "add article to journal TSE:mvp2016,2016,Better Model Consistency",
            "add article to series ICSA:rr2017,2017,Components still have no interfaces",
            "written-by mvp2015,Shashi Afolabi;Richard Rhinelander",
            "written-by mvp2016,Shashi Afolabi;Emiola Lowry",
            "written-by rr2017,Richard Rhinelander",
            "print bibliography ieee:rr2017;mvp2016;mvp2015",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] S. Afolabi and E. Lowry, \"Better Model Consistency,\" TSE, 2016.",
            "[2] S. Afolabi and R. Rhinelander, \"Model Consistency,\" TSE, 2015.",
            "[3] R. Rhinelander, \"Components still have no interfaces,\" in Proceedings of ICSA, Gothenburg, 2017.",
            "Ok"),
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyValidArguments2Test()
    {
        Terminal.initialize(
            "add journal TSE,Elsevier",
            "add conference series ICSA",
            "add conference ICSA,2017,Gothenburg",
            "add author Shashi,Afolabi",
            "add author Richard,Rhinelander",
            "add author Emiola,Lowry",
            "add article to journal TSE:mvp2015,2015,Model Consistency",
            "add article to journal TSE:mvp2016,2016,Better Model Consistency",
            "add article to series ICSA:rr2017,2017,Components still have no interfaces",
            "written-by mvp2015,Shashi Afolabi;Richard Rhinelander",
            "written-by mvp2016,Shashi Afolabi;Emiola Lowry",
            "written-by rr2017,Richard Rhinelander",
            "print bibliography chicago:rr2017;mvp2016;mvp2015",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "(Afolabi, 2016) Afolabi, Shashi, and Lowry, Emiola. \"Better Model Consistency.\" TSE (2016).",
            "(Afolabi, 2015) Afolabi, Shashi, and Rhinelander, Richard. \"Model Consistency.\" TSE (2015).",
            "(Rhinelander, 2017) Rhinelander, Richard. \"Components still have no interfaces.\" Paper presented at ICSA, 2017, Gothenburg.",
            "Ok"),
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyOrderByFirstLastName1Test()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author Z,A",
            "add author A,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,Z A",
            "written-by a,A Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] Z. A, \"Z,\" A, 2001.",
            "[2] A. Z, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyOrderByFirstLastName2Test()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author Z,A",
            "add author A,Z",
            "add author A,A",
            "add author Z,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,Z A;Z Z",
            "written-by a,A Z;A A",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] Z. A and Z. Z, \"Z,\" A, 2001.",
            "[2] A. Z and A. A, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }
    @Test
    public void PrintBibliographyUppercaseBeforeLowercaseTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author z,Z",
            "add author Z,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,Z Z",
            "written-by a,z Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] Z. Z, \"Z,\" A, 2001.",
            "[2] Z. Z, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenByFirstFirstNameTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,Z",
            "add author Z,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,A Z",
            "written-by a,Z Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. Z, \"Z,\" A, 2001.",
            "[2] Z. Z, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenBySecondLastNameTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author Z,A",
            "add author A,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,A A",
            "written-by z,Z A",
            "written-by a,A A;A Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A and Z. A, \"Z,\" A, 2001.",
            "[2] A. A and A. Z, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenBySecondFirstNameTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author A,Z",
            "add author Z,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,A A",
            "written-by z,A Z",
            "written-by a,A A;Z Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A and A. Z, \"Z,\" A, 2001.",
            "[2] A. A and Z. Z, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenByThirdLastNameTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author B,B",
            "add author Z,A",
            "add author A,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,A A;B B",
            "written-by z,Z A",
            "written-by a,A A;B B;A Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A et al., \"Z,\" A, 2001.",
            "[2] A. A et al., \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenByThirdFirstNameTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author B,B",
            "add author Z,Z",
            "add author A,Z",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,A A;B B",
            "written-by z,A Z",
            "written-by a,A A;B B;Z Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A et al., \"Z,\" A, 2001.",
            "[2] A. A et al., \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenByListLengthTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author B,B",
            "add author Z,Z",
            "add author C,C",
            "add article to journal A:z,2001,Z",
            "add article to journal A:a,2000,A",
            "written-by z,A A;B B;Z Z",
            "written-by a,A A;B B;Z Z;C C",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A et al., \"Z,\" A, 2001.",
            "[2] A. A et al., \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenByTitleTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author B,B",
            "add author Z,Z",
            "add article to journal A:z,2001,A",
            "add article to journal A:a,2000,Z",
            "written-by z,A A;B B;Z Z",
            "written-by a,A A;B B;Z Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A et al., \"A,\" A, 2001.",
            "[2] A. A et al., \"Z,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyThenByYearTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,A",
            "add author B,B",
            "add author Z,Z",
            "add article to journal A:z,2000,A",
            "add article to journal A:a,2001,A",
            "written-by z,A A;B B;Z Z",
            "written-by a,A A;B B;Z Z",
            "print bibliography ieee:a;z",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A et al., \"A,\" A, 2000.",
            "[2] A. A et al., \"A,\" A, 2001.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyMoreThanThreeAuthorsTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add author A,B",
            "add author C,D",
            "add author E,F",
            "add author G,H",
            "add article to journal A:a,2000,A",
            "written-by a,A B;C D;E F;G H",
            "print bibliography ieee:a",
            "print bibliography chicago:a",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. B et al., \"A,\" A, 2000.",
            "(B, 2000) B, A, D, C, F, E, and H, G. \"A.\" A (2000).",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyWeirdConferenceNameTest()
    {
        Terminal.initialize(
            "add conference series :::",
            "add conference :::,2000,::::",
            "add article to series ::::a,2000,::",
            "add author A,A",
            "written-by a,A A",
            "print bibliography ieee:a",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. A, \"::,\" in Proceedings of :::, ::::, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyAllowDuplicatesTest()
    {
        Terminal.initialize(
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,B",
            "add author A,B",
            "written-by a,A B",
            "written-by b,A B",
            "print bibliography ieee:a;b;b;b;b;a;a;a;a;a;a;a;a;a;a;b",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "[1] A. B, \"A,\" A, 2000.",
            "[2] A. B, \"B,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void PrintBibliographyArticleMustExistTest()
    {
        Terminal.initialize(
            "print bibliography ieee:a",
            "quit");

        Console.main(null);

        assertArrayEquals(new Terminal.Output[] { Terminal.error, Terminal.ok },
            Terminal.getOutput());
        Terminal.finish();
    }

}

// vim: set expandtab:
