import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

public class RedDress extends Observable{
	
	ArrayList<Observer> users = new ArrayList<Observer>();
	
	private boolean inStock = true;
	
	public boolean inStock() {
		return inStock;
	}
	
	public void setInStock(boolean inStock) {
		this.inStock = inStock;
		notifyObservers();
		
	}
	
	@Override
	public synchronized void addObserver(Observer o) {
		users.add(o);
	}
	

	@Override
	public synchronized void deleteObserver(Observer o) {
		users.remove(o);
	}
	
	@Override
	public void notifyObservers() {
		for (Observer user : users) {
			user.update(null, user);
		}
	}

	public RedDress() {
		// TODO Auto-generated constructor stub
	}

}
