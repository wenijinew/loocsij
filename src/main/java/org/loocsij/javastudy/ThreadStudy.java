package org.loocsij.javastudy;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Thread study class.
 * @author wengm
 *
 */
public class ThreadStudy{
	/**
	 * Prime thread.
	 * @author wengm
	 *
	 */
	private static class PrimeThread extends Thread{
		public static final int MAX_PRIMES = 1000000;
		public static final int TEN_SECONDS = 10000;
		
		public volatile boolean finished = false;
		
		public void run() {
			int[] primes = new int[MAX_PRIMES];
			int count = 0;
			
			for (int i=2; count<MAX_PRIMES; i++) {
				
				// Check to see if the timer has expired
				if (finished) {
					break;
				}
				
				boolean prime = true;
				for (int j=0; j<count; j++) {
					if (i % primes[j] == 0) {
						prime = false;
						break;
					}
				}
				
				if (prime) {
					primes[count++] = i;
					System.out.println("Found prime["+count+"]: " + i);
				}
			}
		}
	}
	
	private static class JoinThread extends Thread {
		private String firstName;
		
		public JoinThread(String firstName) {
			this.firstName = firstName;
		}

		public void run() {
			int total = 0;
			while (total++ < 10) {
//				try {
//					sleep(500);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				Thread.yield();
				System.out.println(total+":"+firstName);
			}
			System.out.print(firstName + " stoped.\n");
		}

	}
	
	/**
	 * Prime exercise.
	 */
	public static void primeExercise(){
		final PrimeThread calculator = new PrimeThread();
        calculator.start();
//        try {
//            Thread.sleep(PrimeThread.TEN_SECONDS);
//        }
//        catch (InterruptedException e) {
//            // fall through
//        }
//        calculator.finished = true;
        new Timer().schedule(
        new TimerTask() {
            public void run()
            {
            	calculator.finished = true;
            }
        }, PrimeThread.TEN_SECONDS);
	}
	
	public static void joinExercise(){
		Thread first = new JoinThread("First");
	    Thread second = new JoinThread("Second");
	    Thread third = new JoinThread("Third");
	    first.start();
	    second.start();
	    third.start();
	}

    public static void main(String[] args) {
    	//prime exercise
//    	primeExercise();
    	//join exercise
    	joinExercise();
    }

}
