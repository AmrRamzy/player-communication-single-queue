package communication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import players.Initiator;
import players.Player;

/**
 * application entry point
 * 
 * @author Amr Ramzy
 *
 */
public class PlayersCommunicationTask {

	/**
	 * timeout value for Executor in milliseconds
	 */
	private static final int TIME_OUT = 5000;
	private static final Logger log = LoggerFactory.getLogger(PlayersCommunicationTask.class);

	/**
	 * application main method, creates and start the players threads
	 * 
	 * @param args arguments for application execution
	 */
	public static void main(String[] args) {

		log.info("application started successfully");
		Queue<String> messageQueue = new LinkedList<>();

		ReentrantLock lock = new ReentrantLock(true);

		Player initiator = new Initiator(messageQueue, lock);
		Player secondPlayer = new Player(messageQueue, lock);

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

		executorService.execute(secondPlayer);
		// 300 milisecond delay to make sure that secondPlayer start first and ready to
		// receive messages once initiator start
		executorService.schedule(initiator, 300, TimeUnit.MILLISECONDS);

		executorService.shutdown();
		try {
			// set timeout to make sure the application terminate properly
			if (executorService.awaitTermination(TIME_OUT, TimeUnit.MILLISECONDS)) {
				log.info("application ended successfully");
			} else {
				executorService.shutdownNow();
				log.info("application ended due to timeout");
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			log.error("application ended due to timeout", e);
			Thread.currentThread().interrupt();
		}

	}
}
