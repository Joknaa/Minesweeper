import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Minesweeper extends JFrame implements ActionListener {
    Caretaker caretaker = new Caretaker();
    Originator originator = new Originator();
    int saveBoards = 0, currentBoard = 0;

    private JButton saveBut;
    private JButton undoBut;
    private JButton redoBut;
    JPanel buttonsPanel = new JPanel();

    Board gameBoard;


    public Minesweeper() {
        SetupButtons();

        JLabel statusbar = new JLabel("");

//        add(statusbar, BorderLayout.SOUTH);
        add(buttonsPanel, BorderLayout.SOUTH);
        gameBoard = new Board(statusbar);
        add(gameBoard);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        var game = new Minesweeper();
        game.setVisible(true);
    }

    private void SetupButtons() {
        saveBut = new JButton("Save");
        saveBut.addActionListener(this);
        buttonsPanel.add(saveBut);

        undoBut = new JButton("Undo");
        undoBut.addActionListener(this);
        buttonsPanel.add(undoBut);

        redoBut = new JButton("Redo");
        redoBut.addActionListener(this);
        buttonsPanel.add(redoBut);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBut) {

            originator.set(gameBoard.GetBoard());
            caretaker.addMemento(originator.storeInMemento());
            saveBoards++;
            currentBoard++;
            System.out.println("Save Boards " + saveBoards);

            undoBut.setEnabled(true);
        } else if (e.getSource() == undoBut) {
            if (currentBoard >= 1) {
                currentBoard--;

                int[] newBoard = originator.restoreFromMemento(caretaker.getMemento(currentBoard));
                gameBoard.SetBoard(newBoard);
                redoBut.setEnabled(true);
            } else {
                undoBut.setEnabled(false);
            }
        } else if (e.getSource() == redoBut) {
            if ((saveBoards - 1) > currentBoard) {
                currentBoard++;

                int[] newBoard = originator.restoreFromMemento(caretaker.getMemento(currentBoard));
                gameBoard.SetBoard(newBoard);
                undoBut.setEnabled(true);
            } else {
                redoBut.setEnabled(false);
            }
        }
    }

}
