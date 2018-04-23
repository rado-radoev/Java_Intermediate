import java.util.Observable;
import java.util.Observer;

class MessageBoard  extends Observable
{
    public void changeMessage(String message) 
    {
        setChanged();
        notifyObservers(message);
    }

}

class Student implements Observer 
{
	private String name;
	
	public Student(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
    @Override
    public void update(Observable o, Object arg) 
    {
        System.out.println("Message board changed: " + arg + " for " + getName());
    }
}

public class MessageBoardTest 
{
        public static void main(String[] args) 
    {
        MessageBoard board = new MessageBoard();
        Student bob = new Student("Bob");
        Student joe = new Student("Joe");
        board.addObserver(bob);
        board.addObserver(joe);
        board.changeMessage("More Homework!");
    }
}