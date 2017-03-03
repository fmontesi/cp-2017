package cp.week10;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class Exercise13
{
	/*
	- Experiment with different parameters for Exercise12:
		* Produce 100000 elements by using 1 producer. Use 5 consumers.
		* Produce 100000 elements by using 1000 producers. Use 5 consumers.
		* Produce 100000 elements by using 1000 producers. Use 1000 consumers.
	- How do the running times differ? (Hint: System.currentTimeMillis() gives you the current time in milliseconds)
	- Hypothesise why the running times differ.
	
	- Try having that each producer synchronizes on the list for its *entire* production loop (not at every iteration, include the entire loop in the synchronized block).
	- Does the running time differ? If so, why?
	- What is the difference in behaviour? Do we have more or less concurrency?
	*/
}
