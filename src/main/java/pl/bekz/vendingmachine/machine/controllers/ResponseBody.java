package pl.bekz.vendingmachine.machine.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBody {

    public <T> ResponseEntity<T> withOkStatus(T body) {
        return with(body, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> with(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}
