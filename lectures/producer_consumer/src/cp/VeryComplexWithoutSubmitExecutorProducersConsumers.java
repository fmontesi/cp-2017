package cp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class VeryComplexWithoutSubmitExecutorProducersConsumers
{
	private static class Product {
		private final List< Integer > ints;
		public Product( List< Integer > ints )
		{
			this.ints = ints;
		}
		
		public List< Integer > ints()
		{
			return ints;
		}
	}
	
	private static final BlockingDeque< Product > THE_LIST = new LinkedBlockingDeque<>();
	
	private static void produce( BlockingDeque< Product > list, String threadName, ExecutorService executor )
	{
		IntStream.range( 1, 2000 ).forEach( i -> {
//				List< Integer > list = new ArrayList< Integer > ();
//				list.add( i );
//				list.add( i + 1 );
//				list.add( i + 2 );
//				list.add( i + 3 );
				Product prod = new Product( Arrays.asList( i, i + 1, i + 2, i + 3 ) );
				list.add( prod );
				AtomicInteger total = new AtomicInteger();
				CountDownLatch latch = new CountDownLatch( 1 );
				executor.submit( () -> {
					consume( THE_LIST, "Consumer" + i, total, latch );
				} );
				try {
					latch.await();
				} catch( InterruptedException e ) {}
				System.out.println( "Total: " + total.get() );
		} );
	}
	
	private static void consume( BlockingDeque< Product > list, String threadName, AtomicInteger total, CountDownLatch latch )
	{
		try {
			Product prod = list.takeFirst();
			total.set( 0 );
			for( Integer i : prod.ints() ) {
				total.addAndGet( i );
			}
			latch.countDown();
		} catch( InterruptedException e ) {}
	}
	
	private static final int NUM_PRODUCERS = 3;
	
	public static void run()
	{
		ExecutorService executor = Executors.newFixedThreadPool( 3 );
		CountDownLatch latch = new CountDownLatch( NUM_PRODUCERS );
		
		// Proposal 1: Before the consumer waits, it checks if something is in the list.
		// Proposal 2: Before the producer sends the signal, it checks if a consumer is waiting.
		IntStream.range( 0, NUM_PRODUCERS ).forEach(
		i -> {
			new Thread( () -> {
				produce( THE_LIST, "Producer" + i, executor );
				latch.countDown();
			} ).start();
		} );
		
		try {
			latch.await();
			executor.shutdown();
			executor.awaitTermination( 1L, TimeUnit.DAYS );
		} catch( InterruptedException e ) {}
	}
}
