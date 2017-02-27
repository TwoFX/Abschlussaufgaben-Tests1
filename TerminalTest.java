package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;

public class TerminalTest
{
    @Test
    public void TerminalNothingProcudesNothingTest()
    {
        Terminal.initialize();

        assertArrayEquals(Terminal.strs(), Terminal.getOutput());
    }

    @Test
    public void TerminalEmptyStringProcudesOneLineTest()
    {
        Terminal.initialize();

        Terminal.printLine("");

        assertArrayEquals(Terminal.strs(""), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void TerminalLineSeparatorProcudesTwoLines1Test()
    {
        Terminal.initialize();

        Terminal.printLine(System.lineSeparator());

        assertArrayEquals(Terminal.strs("", ""), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void TerminalEscapeNProcudesTwoLines1Test()
    {
        Terminal.initialize();

        Terminal.printLine("\n");

        assertArrayEquals(Terminal.strs("", ""), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void TerminalLineSeparatorProducesTwoLines2Test()
    {
        Terminal.initialize();

        Terminal.printLine("A" + System.lineSeparator() + "B");

        assertArrayEquals(Terminal.strs("A", "B"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void TerminalEscapeNProcudesTwoLines2Test()
    {
        Terminal.initialize();

        Terminal.printLine("A\nB");

        assertArrayEquals(Terminal.strs("A", "B"), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void TerminalLineSeparatorProducesTwoLines3Test()
    {
        Terminal.initialize();

        Terminal.printLine("A" + System.lineSeparator());

        assertArrayEquals(Terminal.strs("A", ""), Terminal.getOutput());
        Terminal.finish();
    }

    @Test
    public void TerminalEscapeNProcudesTwoLines3Test()
    {
        Terminal.initialize();

        Terminal.printLine("A\n");

        assertArrayEquals(Terminal.strs("A", ""), Terminal.getOutput());
        Terminal.finish();
    }
}

// vim: set expandtab:
