public class Memento {

    private final int[] board;

    public Memento(int[] savedBoard) { board = savedBoard; }
    public int[] GetSavedBoard() { return board; }
}
