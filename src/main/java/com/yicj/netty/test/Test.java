package com.yicj.netty.test;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {
	
	final BlockingQueue<Integer> answer = new LinkedBlockingQueue<Integer>();
	final Random rand = new Random() ;
    public Integer getFactorial() {
        boolean interrupted = false;
        try {
            for (;;) {
                try {
                    return answer.take();
                } catch (InterruptedException ignore) {
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }
	
	public static void main(String[] args) {
		Test test = new Test() ;
		new Thread(()->test.putData()).start();
		Integer ret = test.getFactorial() ;
		System.out.println("ret : " + ret);
	}
	
	public void putData() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("放入数据");
				try {
					answer.put(rand.nextInt(1000)) ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 3000,2000);
	}
	
	
}
