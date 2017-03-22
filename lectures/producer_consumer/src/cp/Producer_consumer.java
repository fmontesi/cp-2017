package cp;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class Producer_consumer
{
	public static void main( String[] args )
	{
		// Sequential.run();
		// BusyWait.run();
		// GuardedBlocks.run();
		// BlockingQueue.run();
		// Delivery.run();
		// ConveyorBelt.run();
		/* doAndMeasure(
			"1 Thread per Consumer",
			() -> OneThreadEachProducersConsumers.run()
		);
		doAndMeasure(
			"Executors",
			() -> ExecutorProducersConsumers.run()
		);
		doAndMeasure(
			"1 Thread per Consumer",
			() -> OneThreadEachProducersConsumers.run()
		);
		doAndMeasure(
			"Executors",
			() -> ExecutorProducersConsumers.run()
		);*/
		
		/* doAndMeasure(
			"Executors",
			() -> SubmitExecutorProducersConsumers.run()
		); */
		
		doAndMeasure(
			"Executors",
			() -> CountOccurrencesExecutor.run()
		);
	}
	
	public static void doAndMeasure( String caption, Runnable runnable )
	{
		long tStart = System.currentTimeMillis();
		runnable.run();
		System.out.println( caption + " took " + (System.currentTimeMillis() - tStart) + "ms" );
	}
}
