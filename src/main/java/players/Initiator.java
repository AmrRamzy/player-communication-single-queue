package players;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * represents initiator player who will initiate the first message 
 * 
 * @author Amr Ramzy
 *
 */
public class Initiator extends Player {

	/**
	 * @param messageQueue queue used to store messages
	 * @param lock ReentrantLock used to lock a block of code for multithreaded execution
	 */
	public Initiator(Queue<String> messageQueue, ReentrantLock lock) {
		super(messageQueue, lock);
	}
	
	/**
	 * method is used to initialize player and send the first message
	 * @return True if both message queue and the lock are not null
	 */
	@Override
	protected boolean initPlayer() {
		
		log.info("[{}] started.", Thread.currentThread().getName());
		if(messageQueue ==null || lock == null) {
			return false;
		}
		lock.lock();
		try {
			StringBuilder receivedMessageBuilder = new StringBuilder();
			receivedMessageBuilder.append(numberOfSentMessages);
			messageQueue.add(receivedMessageBuilder.toString());
			log.info("[{}] sent {}.", Thread.currentThread().getName(),receivedMessageBuilder);
			numberOfSentMessages++;
			
		} finally {
			lock.unlock();
		}

		return true;
	}

}
