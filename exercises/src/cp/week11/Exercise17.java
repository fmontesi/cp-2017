package cp.week11;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class Exercise17
{
	/*
	! (Exercises marked with ! are challenging, but they will make you feel
	   good when you succeed and are excellent preparation for the exam.
	   Take a deep breath before starting.)
	
	- Modify producers_consumers/BlockingQueue such that:
		* products are lists of numbers to sum up as in Exercise 16.
			(If you used blocking queues in Exercise 16, you can start from there.)
		* There are two queues of products: THE_LIST and THE_OTHER_LIST.
		* When a consumer on THE_LIST consumes a product (and sums up the numbers),
	      it stores the summed up totals in a local list (called totals).
		* When the local list of totals of a consumer on THE_LIST
		  reaches size 4, the consumer puts the list of totals as a product
		  in THE_OTHER_LIST and then empties its own local list.
		  
		  (Beware: you need to be careful of not using the same list 
		  locally in the consumer and in the element that you put in
		  THE_OTHER_LIST, or you risk emptying both when you reset it
		  in the consumer.)
		* There are a few consumers (of another kind) waiting on THE_OTHER_LIST.
		  When one of these consumers gets a product from THE_OTHER_LIST,
		  it sums up the numbers contained within and prints the result on
		  screen.
	*/
}
