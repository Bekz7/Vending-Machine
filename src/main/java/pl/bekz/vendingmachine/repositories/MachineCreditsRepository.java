package pl.bekz.vendingmachine.repositories;

import java.util.concurrent.ConcurrentHashMap;

public interface MachineCreditsRepository extends CreditsRepository {

    ConcurrentHashMap getAllCreditsMap();
}
