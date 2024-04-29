package negocio;

public interface Observable<T extends Observer> {
    void addObserver(T observer);

    void removeObserver(T observer);
}
