package Threads;

class Producer extends Thread {
	Buffer buffer;
	public Producer(Buffer buffer) {
		this.buffer=buffer;
	}
	
	public void run() {
		for(int item=0;item<10;item++) {
			buffer.putItem(item);
			System.out.println("Producer has produces an item:\t"+item);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread {
	Buffer buffer;
	int item;
	public Consumer(Buffer buffer) {
		this.buffer=buffer;
	}
	
	public void run() {
		for(int item=0;item<10;item++) {
			item=buffer.getItem();
			System.out.println("Consumer has consumed an item:\t"+item);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Buffer {
	int content;
	boolean available=false;
	
	public synchronized void putItem(int item) {
		if(available==true) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		content=item;
		available=true;
		notifyAll();
	}
	
	public synchronized int getItem() {
		if(available==false) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		available=false;
		notifyAll();
		return content;
	}
}

public class ProducerConsumer {

	public static void main(String[] args) {
		Buffer buffer=new Buffer();
		
		Producer producer=new Producer(buffer);
		Consumer consumer=new Consumer(buffer);
		
		producer.start();
		consumer.start();
	}

}

