package ra.shopping.service;

public interface IGenericService<T, E> {
    Iterable<T> findAll();
    boolean save( T t);
    boolean delete(E e);
    T findById(E e);
}
