package players;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * represents a single player who can send and receive messages
 *  
 * @author Amr Ramzy
 *  
 */
public class Player implements Runnable {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * maximum number of messages received
	 */
	private static final int MAX_NUMBER_RECEIVED_MESSAGES = 10;

	/**
	 * maximum number of messages sent
	 */
	private static final int MAX_NUMBER_SENT_MESSAGES = 10;

	/**
	 * number of messages sent
	 */
	protected int numberOfSentMessages = 0;
	
	/**
	 * number of messages sent
	 */
	protected int numberOfReceivedMessages = 0;
	
	/**
	 * queue used to store messages
	 */
	protected Queue<String> messageQueue;
	
	/**
	 * ReentrantLock used to lock a block of code for multithreaded execution
	 */
	protected ReentrantLock lock;

	/**
	 * @param messageQueue queue used to store messages
	 * @param lock ReentrantLock used to lock a block of code for multithreaded execution
	 */
	public Player(Queue<String> messageQueue, ReentrantLock lock) {
		this.messageQueue = messageQueue;
		this.lock = lock;
	}

	/**
	 * method is used to initialize player
	 * @return True if both message queue and the lock are not null
	 */
	protected boolean initPlayer() {

		log.info("[{}] started.", Thread.currentThread().getName());
		return (messageQueue != null && lock != null);
	}

	/**
	 * code to be run when the thread starts
	 * send and receive messages until 
	 * player sent MAX_NUMBER_SENT_MESSAGES and received MAX_NUMBER_RECEIVED_MESSAGES
	 * or thread is interrupted 
	 *  
	 */
	@Override
	public void run() {
		if (initPlayer()) {
			while (!Thread.interrupted() && (numberOfSentMessages < MAX_NUMBER_SENT_MESSAGES || numberOfReceivedMessages < MAX_NUMBER_RECEIVED_MESSAGES)) {
				play();
			}
			log.info("[{}] ended.", Thread.currentThread().getName());
		}

	}

	/**
	 * method used to send and receive messages 
	 */
	protected void play() {
		lock.lock();
		try {
			StringBuilder receivedMessageBuilder = new StringBuilder();
			if (numberOfReceivedMessages < MAX_NUMBER_RECEIVED_MESSAGES && !messageQueue.isEmpty()) {
				String receivedMessage = messageQueue.poll();
				numberOfReceivedMessages++;
				log.info("[{}] received {}.", Thread.currentThread().getName(), receivedMessage);
				receivedMessageBuilder.append(receivedMessage).append(" ");
			}

			if (receivedMessageBuilder.length() > 0 && numberOfSentMessages < MAX_NUMBER_SENT_MESSAGES) {
				receivedMessageBuilder.append(numberOfSentMessages);
				messageQueue.add(receivedMessageBuilder.toString());
				log.info("[{}] sent {}.", Thread.currentThread().getName(), receivedMessageBuilder);
				numberOfSentMessages++;
			}

		} finally {
			lock.unlock();
		}

	}

}
