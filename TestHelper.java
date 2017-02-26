package edu.kit.informatik.bibliography.tests;

import static org.junit.Assert.*;
import java.util.*;
import edu.kit.informatik.Terminal;

public class TestHelper
{
    public static void assertMiddleInAnyOrder(Terminal.Output[] left,
        Terminal.Output[] mid, Terminal.Output[] right,
        Terminal.Output[] actual)
    {
        assertEquals(left.length + mid.length + right.length, actual.length);

        LinkedList<Terminal.Output> actualQueue = new LinkedList<>(Arrays.asList(actual));

        for (Terminal.Output elem : left)
        {
            assertEquals(elem, actualQueue.remove());
        }

        ArrayList<Terminal.Output> middle = new ArrayList<>(Arrays.asList(mid));

        while (!middle.isEmpty())
        {
            Terminal.Output found = actualQueue.remove();
            assertTrue("Found: " + found, middle.remove(found));
        }

        for (Terminal.Output elem : right)
        {
            assertEquals(elem, actualQueue.remove());
        }
    }

}

// vim: set expandtab:
