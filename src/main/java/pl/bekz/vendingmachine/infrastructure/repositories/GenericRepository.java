package pl.bekz.vendingmachine.infrastructure.repositories;

import pl.bekz.vendingmachine.infrastructure.exceptions.ItemNotFound;

import java.util.Optional;

public interface GenericRepository<T> {

    T save(T t);

    T findById(String name);

    default T findOneOrThrow(String name) {
        return Optional.ofNullable(findById(name)).orElseThrow(() -> new ItemNotFound(name));
    }
}
