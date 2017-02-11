package edu.kit.informatik.bibliography.tests;

import edu.kit.informatik.bibliography.Console;
import edu.kit.informatik.Terminal;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class CompleteTest
{

	@Test
	public void immediatelyQuitTest()
	{
		Terminal.initialize("quit");

		Console.main(null);

		assertArrayEquals(new Terminal.Output[] { new Terminal.StringOutput("Ok") }, Terminal.getOutput().toArray(new Terminal.Output[0]));
		Terminal.finish();
	}
}
