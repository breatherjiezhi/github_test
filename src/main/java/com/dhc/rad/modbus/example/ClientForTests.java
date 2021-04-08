package com.dhc.rad.modbus.example;

import com.dhc.rad.modbus.ModbusClient;
import com.dhc.rad.modbus.ModbusConstants;
import com.dhc.rad.modbus.entity.exception.ConnectionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class ClientForTests {

    private final ModbusClient modbusClient;

    private ClientForTests() {
        modbusClient = new ModbusClient(ModbusConstants.MODBUS_DEFAULT_IP /*
                 * "192.168.1.55"
                 */, ModbusConstants.MODBUS_DEFAULT_PORT); //ModbusConstants.MODBUS_DEFAULT_PORT);

        try {
            modbusClient.setup();
        } catch (ConnectionException ex) {
            Logger.getLogger(ClientForTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModbusClient getModbusClient() {
        return modbusClient;
    }

    public static ClientForTests getInstance() {
        return ClientAndServerHolder.INSTANCE;
    }

    private static class ClientAndServerHolder {

        private static final ClientForTests INSTANCE = new ClientForTests();
    }
}
