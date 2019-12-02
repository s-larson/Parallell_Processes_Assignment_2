import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.MaterialForAssemblyCounter;

class materialForAssemblyCounter {
	private MaterialForAssemblyCounter materialForAssemblyCounter;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.materialForAssemblyCounter = new MaterialForAssemblyCounter();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAmountOfAssembly() {

		// check that get amount returns correctly, increase one step for each assembly configuration
		// repeat 3 times
		for (int j=0; j<3; ++j) {
			final int tst = j;
			IntStream.range(0,3).forEach(i->assertTrue(this.materialForAssemblyCounter.getAmountOfAssembly(i) == tst));
			IntStream.range(0, 3).forEach(i->this.materialForAssemblyCounter.increaseAmountOfAssembly(i));
		}
		
		// check out that the index works
		for (int j=0; j<2; ++j) {
			for (int i=0; i<3; ++i) {
				try {
					this.materialForAssemblyCounter.getAmountOfAssembly(j==0?-1:4);
					fail("No exception");
				} catch (IllegalArgumentException iae) {
					assertTrue(true);
				}
			}
		}
	}

	@Test
	void testSetAmountOfAssembly() {
		for (int j=0; j<3; ++j) {
			final int tst = j;
			IntStream.range(0,3).forEach(i->this.materialForAssemblyCounter.setAmountOfAssembly(i, i*tst));
			IntStream.range(0, 3).forEach(i->assertTrue(this.materialForAssemblyCounter.getAmountOfAssembly(i)==i*tst));
		}
		// check  out that the index works
		for (int j=0; j<2; ++j) {
			for (int i=0; i<3; ++i) {
				try {
					this.materialForAssemblyCounter.setAmountOfAssembly(j==0?-1:4,0);
					fail("No exception");
				} catch (IllegalArgumentException iae) {
					assertTrue(true);
				}
			}
		}
	}

	@Test
	void testIncreaseAmountOfAssembly() {
		for (int j=0; j<3; ++j) {
			final int tst = j;
			IntStream.range(0,3).forEach(i->this.materialForAssemblyCounter.increaseAmountOfAssembly(i));
			IntStream.range(0, 3).forEach(i->assertTrue(this.materialForAssemblyCounter.getAmountOfAssembly(i)==tst+1));
		}
		// check that out of index works
		for (int j=0; j<2; ++j) {
			for (int i=0; i<3; ++i) {
				try {
					this.materialForAssemblyCounter.increaseAmountOfAssembly(j==0?-1:4);
					fail("No exception");
				} catch (IllegalArgumentException iae) {
					assertTrue(true);
				}
			}
		}
	}

	@Test
	void testToString() {
		final String tmp = this.materialForAssemblyCounter.toString();
		final String expected = "MaterialForAssemblyCounter [part=[0, 0, 0]]";
		for (int i=0; i<tmp.length(); ++i) {
			if (tmp.charAt(i) != expected.charAt(i)) {
				fail("Bugger");
			}
			assertTrue(tmp.charAt(i) == expected.charAt(i));

		}
	}

}
