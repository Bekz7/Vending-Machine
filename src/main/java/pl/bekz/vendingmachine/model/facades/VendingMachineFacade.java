package pl.bekz.vendingmachine.model.facades;

public interface VendingMachineFacade<T> {

    T add(T t);
    T show(String dto);
    T changeAmount(String name, int amount);

}
