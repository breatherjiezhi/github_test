package com.dhc.rad.modbus.entity.exception;

import com.dhc.rad.modbus.entity.func.ModbusError;

/**
 *
 * @author
 */
public class ErrorResponseException extends Exception {

    int exceptionCode;

    public ErrorResponseException(ModbusError function) {
        super(function.toString());
    }
}
