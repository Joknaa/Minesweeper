import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Minesweeper extends JFrame {

    public Minesweeper() {
        JLabel statusbar = new JLabel("");

        add(statusbar, BorderLayout.SOUTH);
        add(new Board(statusbar));

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
}
