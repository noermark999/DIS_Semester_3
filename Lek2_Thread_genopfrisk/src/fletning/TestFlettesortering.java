package fletning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestFlettesortering {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = new ArrayList<Integer>();
		Random r=new Random();
		for (int i=0;i<1000000;i++) {
			list.add(Math.abs(r.nextInt()%10000));
		};
		
		//System.out.println(list);
		myThread myThread1 = new myThread(list.subList(0, list.size()/2));
		myThread myThread2 = new myThread(list.subList(list.size()/2,list.size()));

		FletteSortering fletteSortering = new FletteSortering();

		long l1,l2;
		l1 = System.nanoTime();
		/*
		myThread1.start();
		myThread2.start();
		myThread1.join();
		myThread2.join();
		fletteSortering.merge(list,0,list.size()/2-1, list.size()-1);
		 */
		fletteSortering.mergesort(list,0,list.size()-1);

		l2 = System.nanoTime();
		System.out.println();
		System.out.println("Køretiden var " + (l2-l1)/1000000); //Køretid er ca 2000-2500
		System.out.println();
		//System.out.println(list);
	}

}
