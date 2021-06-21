public class Originator {
    private int[] board;

    public void set(int[] newBoard) {
        System.out.println("From Originator: Current Version of the board\n");
        this.board = newBoard;
    }

    public Memento storeInMemento() {
        System.out.println("From Originator: Saving to Memento");
        return new Memento(board);
    }

    public int[] restoreFromMemento(Memento memento) {
        System.out.println("From Originator: Previous board Saved in Memento\n");
        return memento.GetSavedBoard();
    }
}
