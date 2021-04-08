package com.dhc.rad.modbus.example;

import com.dhc.rad.modbus.ModbusClient;
import com.dhc.rad.modbus.entity.exception.ConnectionException;
import com.dhc.rad.modbus.entity.exception.ErrorResponseException;
import com.dhc.rad.modbus.entity.exception.NoResponseException;
import com.dhc.rad.modbus.entity.func.response.ReadCoilsResponse;
import com.dhc.rad.modbus.entity.func.response.ReadHoldingRegistersResponse;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author
 *
 */
public class TestModbusSlave {

    public static void main(String[] args) {
        ModbusClient modbusClient = ClientForTests.getInstance().getModbusClient();

        ReadCoilsResponse readCoils = null;
        try {
            readCoils = modbusClient.readCoils(100, 5);
        } catch (NoResponseException | ErrorResponseException | ConnectionException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        }
        System.out.println(readCoils);

        ReadHoldingRegistersResponse readHolding = null; 
        try {
            readHolding = modbusClient.readHoldingRegisters(100,10);
        } catch (NoResponseException | ErrorResponseException | ConnectionException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        }
        System.out.println(readHolding);
        modbusClient.close();
    }
}

