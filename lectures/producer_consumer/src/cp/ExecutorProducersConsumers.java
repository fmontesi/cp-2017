package cp;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ExecutorProducersConsumers
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
	
	private static final BlockingDeque< Product > THE_LIST = new LinkedBlockingDeque<>();
	
	private static void produce( BlockingDeque< Product > list, String threadName )
	{
		IntStream.range( 1, 2000 ).forEach( i -> {
				Product prod = new Product( "Water Bottle", "Liters: " + i + ". By thread: " + threadName );
				list.add( prod );
				EXECUTOR.submit( () -> {
					consume( THE_LIST, "Consumer" + i );
				} );
//				new Thread( () -> {
//					consume( THE_LIST, "Consumer" + i );
//				} ).start();
				//System.out.println( threadName + " producing " + prod );
		} );
	}
	
	private static void consume( BlockingDeque< Product > list, String threadName )
	{
		try {
			Product prod = list.takeFirst();
			//System.out.println( threadName + " consuming " + prod.toString() );
		} catch( InterruptedException e ) {}
	}
	
	private static final int NUM_PRODUCERS = 3;
	private static final CountDownLatch LATCH = new CountDownLatch( NUM_PRODUCERS );
	//private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool( 3 );
	
	public static void run()
	{
		// Proposal 1: Before the consumer waits, it checks if something is in the list.
		// Proposal 2: Before the producer sends the signal, it checks if a consumer is waiting.
		IntStream.range( 0, NUM_PRODUCERS ).forEach(
		i -> {
			new Thread( () -> {
				produce( THE_LIST, "Producer" + i );
				LATCH.countDown();
			} ).start();
		} );
		
		try {
			LATCH.await();
			EXECUTOR.shutdown();
			EXECUTOR.awaitTermination( 1L, TimeUnit.DAYS );
		} catch( InterruptedException e ) {}
	}
}
