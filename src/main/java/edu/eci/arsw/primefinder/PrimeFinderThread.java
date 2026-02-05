package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PrimeFinderThread extends Thread{

    @Setter
    @Getter
    private volatile boolean timeToPause;


	int a,b;
	
	private List<Integer> primes;
    private PauseThread pauseThread;
	
	public PrimeFinderThread(int a, int b, PauseThread pauseThread) {
		super();
        this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
        this.pauseThread = pauseThread;
	}

        @Override
	public void run(){
            for (int i= a;i < b;i++){
                synchronized (pauseThread) {
                    while (timeToPause) {
                        try {
                            pauseThread.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (isPrime(i)){
                    primes.add(i);
                    System.out.println(i);
                }
            }
	}
	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

    public void printPrimesNum(){
        System.out.println("El hilo tiene: "+primes.size());
    }


	public List<Integer> getPrimes() {
		return primes;
	}
	
}
