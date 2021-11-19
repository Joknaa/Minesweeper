import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Demo extends JFrame implements ActionListener {

    private final Caretaker caretaker = new Caretaker();
    private final Originator originator = new Originator();
    private int totalSavedDocs = 0, currentDocID = 0;
    private final JButton saveBut;
    private final JButton undoBut;
    private final JButton redoBut;
    private final JTextPane textPane = new JTextPane();
    private final Document document = new DefaultStyledDocument();
    private JScrollPane textScroller = new JScrollPane();

    public Demo() {
        this.setSize(400, 400);
        this.setTitle("Memento Design Pattern");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel();

        textPane.setDocument(document);
        textScroller.setViewportView(textPane);
        add(textScroller);

        saveBut = new JButton("Save");
        saveBut.addActionListener(this);

        undoBut = new JButton("Undo");
        undoBut.addActionListener(this);

        redoBut = new JButton("Redo");
        redoBut.addActionListener(this);

        panel1.add(saveBut);
        panel1.add(undoBut);
        panel1.add(redoBut);

        add(panel1, BorderLayout.SOUTH);
        setVisible(true);
    }


    public static void main(String[] args) {
        new Demo();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBut) {
            originator.set(textPane.getStyledDocument());
            caretaker.addMemento(originator.storeInMemento());
            totalSavedDocs++;
            currentDocID++;
            System.out.println("Total saved Docs: " + totalSavedDocs);
            Document doc = textPane.getStyledDocument();
            try {
                System.out.println("Stored text: " + doc.getText(0, doc.getLength()));
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
            undoBut.setEnabled(true);

        } else if (e.getSource() == undoBut) {
            if (currentDocID >= 1) {
                currentDocID--;
                Document doc = originator.restoreFromMemento(caretaker.getMemento(currentDocID));
                try {
                    System.out.println("Stored text: " + doc.getText(0, doc.getLength()));
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
                textPane.setDocument(doc);
                textPane.setCaretPosition(doc.getLength());
                redoBut.setEnabled(true);

            } else undoBut.setEnabled(false);

        } else if (e.getSource() == redoBut) {
            if ((totalSavedDocs - 1) > currentDocID) {
                currentDocID++;
                Document doc = originator.restoreFromMemento(caretaker.getMemento(currentDocID));
                textPane.setDocument(doc);
                textPane.setCaretPosition(doc.getLength());
                undoBut.setEnabled(true);

            } else redoBut.setEnabled(false);
        }
    }
}