package com.dhc.rad.modbus.example;

import com.dhc.rad.modbus.ModbusClient;
import com.dhc.rad.modbus.ModbusServer;
import com.dhc.rad.modbus.entity.exception.ConnectionException;
import com.dhc.rad.modbus.entity.exception.ErrorResponseException;
import com.dhc.rad.modbus.entity.exception.NoResponseException;
import com.dhc.rad.modbus.entity.func.response.ReadCoilsResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class Example {

    public static void main(String[] args) {
        ModbusServer modbusServer = ServerForTests.getInstance().getModbusServer();
        ModbusClient modbusClient = ClientForTests.getInstance().getModbusClient();

        ReadCoilsResponse readCoils = null;
        try {
            readCoils = modbusClient.readCoils(12321, 10);
        } catch (NoResponseException | ErrorResponseException | ConnectionException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
        }

        System.out.println(readCoils);

        modbusClient.close();
        modbusServer.close();
    }
}
