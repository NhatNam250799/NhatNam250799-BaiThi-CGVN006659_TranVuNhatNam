package services;

import java.util.List;

public interface IService<T> {
    List<T> getAll();

    void save(T s);

    void update(int soDienThoai, T s);

    void remove(int soDienThoai);

    T findById(int soDienThoai);

    List<T> findByName(String hoVaTen);
}
