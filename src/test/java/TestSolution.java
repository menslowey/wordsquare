import main.Solver;
import main.WordsManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSolution
{
	@Test
	void testKnownSolution()
	{
		WordsManager wordsManager = new WordsManager("testfile1.txt", 4, "eeeeddoonnnsssrv");

		Solver solver = new Solver(4, wordsManager, 0, wordsManager.getAvailableWordCount());
		ArrayList<String> solution = solver.runSolver();

		assertAll(
				() -> assertEquals("rose", solution.get(0)),
				() -> assertEquals("oven", solution.get(1)),
				() -> assertEquals("send", solution.get(2)),
				() -> assertEquals("ends", solution.get(3))
		);
	}
}
