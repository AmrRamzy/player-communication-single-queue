package players;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class PlayerTest {

	private static final int TEST_TIME_OUT = 3000;
	private Queue<String> queue;
	private ReentrantLock lock;
	private Player player;

	@BeforeEach
	void init() {
		queue = new LinkedList<String>();
		lock = new ReentrantLock();
		player = new Player(queue, lock);
		queue.add("0");

	}

	@Test
	@DisplayName("initPlayer should handling NullPointerExceptions")
	void test_initPlayer() {
		player = new Player(null, null);
		assertFalse(() -> player.initPlayer());
	}

	@Test
	@DisplayName("Player thread should be terminated if interrupted")
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void test_thread_interrupt() {
		queue.clear();
		Thread testThread = new Thread(player);
		assertAll(() -> testThread.start());
		testThread.interrupt();
	}

	@Test()
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	@DisplayName("lock should be released after method execution")
	void test_player_lock_release() {
		assertAll(() -> player.play());
		assertEquals(0, player.lock.getHoldCount());
	}

	@Test
	@DisplayName("number of sent messages should be 1")
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void test_player_sent_messages() {
		assertAll(() -> player.play());
		assertEquals(1, player.numberOfSentMessages);
	}

	@Test
	@DisplayName("number of received messages should be 1")
	@Timeout(value = TEST_TIME_OUT, unit = TimeUnit.MILLISECONDS)
	void test_player_received_messages() {
		assertAll(() -> player.play());
		assertEquals(1, player.numberOfReceivedMessages);
	}
}
