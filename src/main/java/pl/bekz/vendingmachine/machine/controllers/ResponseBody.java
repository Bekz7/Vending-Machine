package pl.bekz.vendingmachine.machine.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBody {

    protected <T> ResponseEntity<T> withOkStatus(T body) {
        return with(body, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> with(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}
