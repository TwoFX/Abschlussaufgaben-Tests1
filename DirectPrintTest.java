package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.bibliography.TrackedEntity;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

public class DirectPrintTest
{
    @Before
    public void setUp()
    {
        TrackedEntity.reset();
    }

    @Test
    public void DirectPrintInvalidArgumentsTest()
    {
        String[] commands = new String[]
        {
            "direct print conference ieee:A,A,A,2000",
            "direct print conference ieee:A B,A,A,A,2000",
            "direct print conference ieee:A B,A B,A,A,A,2000",
            "direct print conference ieee:A B,,A,A,A,2000",
            "direct print conference ieee:A B,A B,A B,A B,A,A,A,2000",
            "direct print journal ieee:A,A,2000",
            "direct print journal ieee:A B,A,A,2000",
            "direct print journal ieee:A B,,A,A,2000",
            "direct print journal ieee:A B,A B,A,A,2000",
            "direct print journal ieee:A B,A B,A B,A B,A,A,A,2000",
            "direct print conference ieee:A,,,A,A,A,2000",
            "direct print journal ieee:A,,,A,A,2000",
            "direct print conference ieee,A B,,,A,A,A,2000",
            "direct print journal ieee,A B,,,A,A,2000",
            "direct print conference ieee:A B;A B;A B,A,A,A,2000",
            "direct print journal ieee:A B;A B;A B,A,A,2000",
            "direct print conference ieee:,,,A,A,A,2000",
            "direct print journal ieee:,,,A,A,A,2000",
            "direct print conference ieee:A 5,,,A,A,A,2000",
            "direct print conference ieee:5 A,,,A,A,A,2000",
            "direct print journal ieee:A 5,,,A,A,2000",
            "direct print journal ieee:5 A,,,A,A,2000",
            "direct print journal bla:A A,B A,C A,A,A,A,2000",
            "direct print conference bla:A A,B A,C A,A,A,2000",
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
    public void DirectPrintValidArgumentsTest()
    {
        Terminal.initialize(
            "direct print conference ieee:Sergey Brin,Lawrence Page,,The Anatomy of a Large-Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998",
            "direct print conference chicago:Sergey Brin,Lawrence Page,,The Anatomy of a Large-Scale Hypertextual Web Search Engine,WWW,Brisbane Australia,1998",
            "direct print journal ieee:Edsger Dijkstra,,,Go To Statement Considered Harmful,Communications of the ACM,1968",
            "direct print journal chicago:Edsger Dijkstra,,,Go To Statement Considered Harmful,Communications of the ACM,1968",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "[1] S. Brin and L. Page, \"The Anatomy of a Large-Scale Hypertextual Web Search Engine,\" in Proceedings of WWW, Brisbane Australia, 1998.",
            "(Brin, 1998) Brin, Sergey, and Page, Lawrence. \"The Anatomy of a Large-Scale Hypertextual Web Search Engine.\" Paper presented at WWW, 1998, Brisbane Australia.",
            "[1] E. Dijkstra, \"Go To Statement Considered Harmful,\" Communications of the ACM, 1968.",
            "(Dijkstra, 1968) Dijkstra, Edsger. \"Go To Statement Considered Harmful.\" Communications of the ACM (1968).",
            "Ok"),
            Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void DirectPrintIEEETransformToUppercaseTest()
    {
        Terminal.initialize(
            "direct print conference ieee:aB Bb,,,A,A,A,2000",
            "direct print conference ieee:aB Bb,cC dX,,A,A,A,2000",
            "direct print journal ieee:aB Bb,,,A,A,2000",
            "direct print journal ieee:aV Bb,cC dX,,A,A,2000",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "[1] A. Bb, \"A,\" in Proceedings of A, A, 2000.",
            "[1] A. Bb and C. dX, \"A,\" in Proceedings of A, A, 2000.",
            "[1] A. Bb, \"A,\" A, 2000.",
            "[1] A. Bb and C. dX, \"A,\" A, 2000.",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void DirectPrintDoNotReorderAuthorsTest()
    {
        Terminal.initialize(
            "direct print conference ieee:ZZ ZZ,yy YY,aa aa,A,A,A,2000",
            "direct print conference chicago:ZZ ZZ,yy YY,aa aa,A,A,A,2000",
            "direct print journal ieee:ZZ ZZ,yy YY,aa aa,A,A,2000",
            "direct print journal chicago:ZZ ZZ,yy YY,aa aa,A,A,2000",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "[1] Z. ZZ et al., \"A,\" in Proceedings of A, A, 2000.",
            "(ZZ, 2000) ZZ, ZZ, YY, yy, and aa, aa. \"A.\" Paper presented at A, 2000, A.",
            "[1] Z. ZZ et al., \"A,\" A, 2000.",
            "(ZZ, 2000) ZZ, ZZ, YY, yy, and aa, aa. \"A.\" A (2000).",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void DirectPrintAuthorFormatsTest()
    {
        Terminal.initialize(
            "direct print conference ieee:Aa Bb,,,A,A,A,2000",
            "direct print conference ieee:Aa Bb,Cc Dd,,A,A,A,2000",
            "direct print conference ieee:Aa Bb,,Cc Dd,A,A,A,2000",
            "direct print conference ieee:Aa Bb,Cc Dd,Ee Ff,A,A,A,2000",
            "direct print conference chicago:Aa Bb,,,A,A,A,2000",
            "direct print conference chicago:Aa Bb,Cc Dd,,A,A,A,2000",
            "direct print conference chicago:Aa Bb,,Cc Dd,A,A,A,2000",
            "direct print conference chicago:Aa Bb,Cc Dd,Ee Ff,A,A,A,2000",
            "direct print journal ieee:Aa Bb,,,A,A,2000",
            "direct print journal ieee:Aa Bb,Cc Dd,,A,A,2000",
            "direct print journal ieee:Aa Bb,,Cc Dd,A,A,2000",
            "direct print journal ieee:Aa Bb,Cc Dd,Ee Ff,A,A,2000",
            "direct print journal chicago:Aa Bb,,,A,A,2000",
            "direct print journal chicago:Aa Bb,Cc Dd,,A,A,2000",
            "direct print journal chicago:Aa Bb,,Cc Dd,A,A,2000",
            "direct print journal chicago:Aa Bb,Cc Dd,Ee Ff,A,A,2000",
            "quit");

        Console.main(null);

        assertArrayEquals(Terminal.strs(
            "[1] A. Bb, \"A,\" in Proceedings of A, A, 2000.",
            "[1] A. Bb and C. Dd, \"A,\" in Proceedings of A, A, 2000.",
            "[1] A. Bb and C. Dd, \"A,\" in Proceedings of A, A, 2000.",
            "[1] A. Bb et al., \"A,\" in Proceedings of A, A, 2000.",
            "(Bb, 2000) Bb, Aa. \"A.\" Paper presented at A, 2000, A.",
            "(Bb, 2000) Bb, Aa, and Dd, Cc. \"A.\" Paper presented at A, 2000, A.",
            "(Bb, 2000) Bb, Aa, and Dd, Cc. \"A.\" Paper presented at A, 2000, A.",
            "(Bb, 2000) Bb, Aa, Dd, Cc, and Ff, Ee. \"A.\" Paper presented at A, 2000, A.",
            "[1] A. Bb, \"A,\" A, 2000.",
            "[1] A. Bb and C. Dd, \"A,\" A, 2000.",
            "[1] A. Bb and C. Dd, \"A,\" A, 2000.",
            "[1] A. Bb et al., \"A,\" A, 2000.",
            "(Bb, 2000) Bb, Aa. \"A.\" A (2000).",
            "(Bb, 2000) Bb, Aa, and Dd, Cc. \"A.\" A (2000).",
            "(Bb, 2000) Bb, Aa, and Dd, Cc. \"A.\" A (2000).",
            "(Bb, 2000) Bb, Aa, Dd, Cc, and Ff, Ee. \"A.\" A (2000).",
            "Ok"), Terminal.getOutput());
        Terminal.finish();
    }

}

// vim: set expandtab:
