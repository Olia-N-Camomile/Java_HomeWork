
public abstract class AbstractDAO <T,N> {
    public abstract boolean  addNewEntry(T t);
    public abstract boolean createTable();
    public abstract T findEntryByNumber(N n, String query_Text);
    public abstract T findAll(String query_Text);
}
