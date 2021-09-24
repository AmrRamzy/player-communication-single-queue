package players;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitiatorTest {
	

	private Queue<String> queue;
	private ReentrantLock lock;
	private Player player;

	@BeforeEach
	void init() {
		queue = new LinkedList<String>();
		lock = new ReentrantLock();
		player = new Initiator(queue, lock);

	}
	
	@Test
	@DisplayName("initPlayer should handling NullPointerExceptions")
	void test_initPlayer_null_values() {
		player = new Initiator(null,null);
		assertFalse(() -> player.initPlayer());
	}
	
	@Test
	@DisplayName("initPlayer should handling NullPointerExceptions")
	void test_initPlayer_send_first_message() {
		assertTrue(() -> player.initPlayer());
		assertEquals(1, player.numberOfSentMessages);
	}
}
