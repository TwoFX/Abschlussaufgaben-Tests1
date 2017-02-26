package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import java.util.*;

public class JaccardTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void JaccardInvalidArgumentCountTest()
    {
        String[] commands = new String[]
        {
            "jaccard",
            "jaccard ",
            "jaccard  ",
            "jaccard   ",
            "jaccard    ",
            "jaccard a",
            "jaccard a;b",
            "jaccard a b c",
            "jaccard a;b c;d e;f",
            "jaccard a;b c;d ",
            "jaccard  a;b c;d",
            "jaccard a;b c;d  ",
            "jaccard a;b  c;d",
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
    public void JaccardInvalidArgumentFormatTest()
    {
        String[] commands = new String[]
        {
            "jaccard a,b",
            "jaccard a,b c,d",
            "jaccard a;b,c;d",
            "jaccard A;b c;d",
            "jaccard a;% c;d",
            "jaccard a;b c;1;d",
            "jaccard a; c",
            "jaccard ;a c",
            "jaccard a;;b c",
            "jaccard a; ;b c",
            "jaccard c a;",
            "jaccard c ;a",
            "jaccard c a;;b",
            "jaccard c a; ;b",
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
    public void JaccardValidArgumentsTest()
    {
        // FIXME: Add more things here once what names are allowed is well-defined.
        String[] commands = new String[]
        {
            "jaccard a a",
            "jaccard a b",
            "jaccard x y",
            "jaccard a;b x;y",
            "jaccard a;b a;b",
            "jaccard a;b a;c",
            "jaccard a;b;c;d;e b;c;d;e;f",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "1.000", "0.000", "0.000", "0.000", "1.000", "0.333", "0.666", "Ok")
            , Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void SimilarityValidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "add journal A,B",
            "add article to journal A:a,2000,A",
            "add article to journal A:b,2000,A",
            "add keywords to journal A:c",
            "add keywords to pub a:a;b;d;e",
            "add keywords to pub b:b;d;e;f",
            "similarity a,b",
            "quit"
        };

        Terminal.initialize(commands);

        Console.main(null);

        assertArrayEquals(Terminal.strs("Ok", "Ok", "Ok", "Ok", "Ok", "Ok",
            "0.666", "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void JaccardRoundingCornerCaseTest()
    {
        // This checks that a jaccard coefficient of 0.49...9 gets cutoff to
        // 0.499
        long num = 1000;
        long cutoff = 499;

        for (int i = 0; i < 3; i++)
        {
            StringBuilder left = new StringBuilder("a");
            StringBuilder right = new StringBuilder("a");

            for (long j = 1; j < cutoff; j++)
            {
                left.append(';');
                right.append(';');

                long jj = j;
                while (jj != 0)
                {
                    char toAppend = jj % 2 == 0 ? 'a' : 'b';
                    left.append(toAppend);
                    right.append(toAppend);
                    jj /= 2;
                }
            }

            for (long j = cutoff; j < num; j++)
            {
                right.append(';');

                long jj = j;
                while (jj != 0)
                {
                    char toAppend = jj % 2 == 0 ? 'a' : 'b';
                    right.append(toAppend);
                    jj /= 2;
                }
            }

            //System.out.println("jaccard " + left.toString() + " " + right.toString());

            Terminal.initialize(
                "jaccard " + left.toString() + " " + right.toString(),
                "quit");

            Console.main(null);

            assertArrayEquals(Terminal.strs("0.499", "Ok"), Terminal.getOutput());
            Terminal.finish();

            num *= 10;
            cutoff = 10 * cutoff + 9;
        }
    }

}

// vim: set expandtab:
