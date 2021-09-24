package communication;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class PlayersCommunicationTaskTest {

	private static final long TEST_TIME_OUT = 10000;

	@Test
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void mainTest() {
		assertAll(() -> PlayersCommunicationTask.main(new String[0]));
	}
}
