package edu.eci.arsw.primefinder;

import java.util.Arrays;
import java.util.Scanner;

public class PauseThread extends Thread {
    private int miliseconds;
    private PrimeFinderThread pft[];

    public PauseThread(int miliseconds, PrimeFinderThread[] pft) {
        this.miliseconds = miliseconds;
        this.pft = pft;
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        while(threadsIsAlive()) {
            try {
                Thread.sleep(miliseconds);
                synchronized (this) {
                    Arrays.stream(pft).forEach(t -> t.setTimeToPause(true));
                    Arrays.stream(pft).forEach(PrimeFinderThread::printPrimesNum);
                }
                sc.nextLine();

                synchronized (this) {
                    Arrays.stream(pft).forEach(t -> t.setTimeToPause(false));
                    this.notifyAll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean threadsIsAlive() {
        int num = 0;
        for(Thread t : pft ) {
            if(t.getState() == PrimeFinderThread.State.TERMINATED) {
                num++;
            }
        }
        return num != pft.length;
    }



}
