package pl.bekz.vendingmachine.infrastructure.repositories;

import pl.bekz.vendingmachine.infrastructure.exceptions.ItemNotFound;

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
