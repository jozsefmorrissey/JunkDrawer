package Cars;

public class WorkerThread implements Runnable {
	private ElectronicPart part;
	private ElectronicSystem sys;
	private boolean isPart = false;
	private Thread thread;

	
	public WorkerThread(ElectronicPart ep){
		isPart = true;
		part = ep;
	}
	
	public WorkerThread(ElectronicSystem s){
		sys = s;
	}

	@Override
	public void run() {
		if(isPart)
			part.run();
		else
			sys.run();
	}	
	
	public void setThread(Thread thr){thread = thr;}
	
	public Thread getThread(){return thread;}
}
