package cp;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class SubmitExecutorProducersConsumers
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
				Future< Integer > f = executor.submit( () -> {
					return consume( THE_LIST, "Consumer" + i );
				} );
				try {
					int total = f.get();
					System.out.println( "Total: " + total );
				} catch( ExecutionException e ) {}
				  catch( InterruptedException e ) {}
		} );
	}
	
	private static Integer consume( BlockingDeque< Product > list, String threadName )
	{
		try {
			Product prod = list.takeFirst();
			int total = 0;
			for( Integer i : prod.ints() ) {
				total += i;
			}
			return total;
		} catch( InterruptedException e ) { return 0; }
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
