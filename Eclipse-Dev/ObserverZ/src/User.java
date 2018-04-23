import java.util.Observable;
import java.util.Observer;

public class User implements Observer {
	
	private Observable observable = null;

	public User(Observable observable) {
		this.observable = observable;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		buyDress();
		unsubscribe();
	}
	
	public void buyDress() {
		System.out.println("Got my new red dress");
	}
	
	public void unsubscribe() {
		observable.deleteObserver(this);
	}

}
