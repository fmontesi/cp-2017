package cp;



import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
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
public class CountOccurrencesExecutor
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
	
	private static void produce( BlockingDeque< Product > list, String threadName, ExecutorService executor, Map< Integer, Integer > map )
	{
		IntStream.range( 1, 2000 ).forEach( i -> {
				Product prod = new Product( Arrays.asList( i, i + 1, i + 2, i + 3 ) );
				list.add( prod );
				executor.submit( () -> {
					consume( THE_LIST, "Consumer" + i, map );
				} );
		} );
	}
	
	private static void consume( BlockingDeque< Product > list, String threadName, Map< Integer, Integer > map )
	{
		// k -> v
		// m(k) = v
		// m.get( k ) = v
		try {
			Product prod = list.takeFirst();
			for( Integer i : prod.ints() ) {
				map.compute( i, ( k, v ) -> {
					if ( v == null ) {
						return 1;
					} else {
						return v + 1;
					}
				} );
			}
		} catch( InterruptedException e ) {}
	}
	
	private static final int NUM_PRODUCERS = 3;
	
	public static void run()
	{
		ExecutorService executor = Executors.newFixedThreadPool( 3 );
		CountDownLatch latch = new CountDownLatch( NUM_PRODUCERS );
		ConcurrentHashMap< Integer, Integer > map = new ConcurrentHashMap<>();
		
		// Proposal 1: Before the consumer waits, it checks if something is in the list.
		// Proposal 2: Before the producer sends the signal, it checks if a consumer is waiting.
		IntStream.range( 0, NUM_PRODUCERS ).forEach(
		i -> {
			new Thread( () -> {
				produce( THE_LIST, "Producer" + i, executor, map );
				latch.countDown();
			} ).start();
		} );
		
		try {
			latch.await();
			executor.shutdown();
			executor.awaitTermination( 1L, TimeUnit.DAYS );
			
			for( Entry< Integer, Integer > entry : map.entrySet() ) {
				System.out.println( entry.getKey() + " appeared " + entry.getValue() + " times" );
			}
		} catch( InterruptedException e ) {}
	}
}
