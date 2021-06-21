import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    List<Memento> savedBoards = new ArrayList<>();

    public void addMemento(Memento m) { savedBoards.add(m); }
    public Memento getMemento(int index) { return savedBoards.get(index); }
}
