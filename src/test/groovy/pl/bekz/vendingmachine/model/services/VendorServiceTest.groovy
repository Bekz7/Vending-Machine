package pl.bekz.vendingmachine.model.services


import spock.lang.Specification

class VendorServiceTest extends Specification {
    private VendorService service

    void setup() {
        service = new VendorService()
    }

    def "As a Vendor I want check machine balance"(){
        given:
        service.machineBalance()

    }
}
