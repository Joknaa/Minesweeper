import javax.swing.text.Document;

public class Memento {

    private final Document doc;

    public Memento(Document SavedDoc) { this.doc = SavedDoc; }
    public Document GetSavedDocument() { return doc; }
}
