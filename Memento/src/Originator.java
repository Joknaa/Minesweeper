import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Originator {
    private Document doc;

    public void set(Document doc) {
        System.out.println("From Originator: Current Version of the board\n");
        try {
            System.out.println("Stored text: " + doc.getText(0,doc.getLength()));
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
        this.doc = doc;
    }

    public Memento storeInMemento() {
        System.out.println("From Originator: Saving to Memento");
        return new Memento(doc);
    }

    public Document restoreFromMemento(Memento memento) {
        System.out.println("From Originator: Previous board Saved in Memento\n");
        return memento.GetSavedDocument();
    }
}
