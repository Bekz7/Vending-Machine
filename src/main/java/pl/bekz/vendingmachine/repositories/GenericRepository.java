package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.exceptions.ItemNotFound;

import java.util.Optional;

public interface GenericRepository<T> {

    T save(T t);

    T findById(String name);

    default T findOneOrThrow(String name) {
        T t = findById(name);
        if (t == null){
            throw new ItemNotFound(name);
        }
        return t;
//        return Optional.ofNullable(findById(name)).orElseThrow(() -> new ItemNotFound(name));
    }
}
