package com.arch.exceptions.customs;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModularException extends RuntimeException {
    public ModularException(String message) {
        super(message);
    }
}
