package cp;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class GuardedBlocks
{
	private static class Product {
		private final String name;
		private final String attributes;
		public Product( String name, String attributes )
		{
			this.name = name;
			this.attributes = attributes;
		}
		
		public String toString()
		{
			return name + ". " + attributes;
		}
	}
	private static final Deque< Product > THE_LIST = new LinkedList<>();
	
	private static void produce( Deque< Product > list, String threadName )
	{
		IntStream.range( 1, 200 ).forEach( i -> {
				Product prod = new Product( "Water Bottle", "Liters: " + i + ". By thread: " + threadName );
				synchronized( list ) {
					list.add( prod );
					list.notify();
				}
				System.out.println( threadName + " producing " + prod );
		} );
	}
	
	private static void consume( Deque< Product > list, String threadName, CountDownLatch latch )
	{
		boolean receivedNotify = false;
		boolean keepRun = true;
		while( keepRun ) {
			synchronized( list ) {
				// Wait for a signal from a producer
				if ( !list.isEmpty() ) {
					receivedNotify = false;
					Product prod = list.removeFirst();
					System.out.println( threadName + " consuming " + prod.toString() );
				} else if ( latch.getCount() == 0 ) {
					keepRun = false;
				} else {
					if ( receivedNotify ) {
						System.out.println( "DUH! Wasted iteration." );
					}
					try {
						list.wait();
						receivedNotify = true;
					} catch( InterruptedException e ) {}
				}
			}
		}
	}
	
	private static final int NUM_PRODUCERS = 3;
	
	public static void run()
	{
		// Proposal 1: Before the consumer waits, it checks if something is in the list.
		// Proposal 2: Before the producer sends the signal, it checks if a consumer is waiting.
		
		CountDownLatch latch = new CountDownLatch( NUM_PRODUCERS );
		IntStream.range( 0, NUM_PRODUCERS ).forEach(
		i -> {
			new Thread( () -> {
				produce( THE_LIST, "Producer" + i );
				latch.countDown();
			} ).start();
			new Thread( () -> {
				consume( THE_LIST, "Consumer" + i, latch );
			} ).start();
		} );
		try {
			latch.await();
		} catch( InterruptedException e ) {}
		synchronized( THE_LIST ) {
			THE_LIST.notifyAll();
		}
	}
}
