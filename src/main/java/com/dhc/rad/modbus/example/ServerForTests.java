package com.dhc.rad.modbus.example;

import com.dhc.rad.modbus.ModbusConstants;
import com.dhc.rad.modbus.ModbusServer;
import com.dhc.rad.modbus.entity.exception.ConnectionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class ServerForTests {

    private final ModbusServer modbusServer;

    private ServerForTests() {
        modbusServer = new ModbusServer(ModbusConstants.MODBUS_DEFAULT_PORT); //ModbusConstants.MODBUS_DEFAULT_PORT);
        try {
            modbusServer.setup(new ModbusRequestHandlerExample());
        } catch (ConnectionException ex) {
            Logger.getLogger(ServerForTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModbusServer getModbusServer() {
        return modbusServer;
    }

    public static ServerForTests getInstance() {
        return ServerForTestsHolder.INSTANCE;
    }

    private static class ServerForTestsHolder {

        private static final ServerForTests INSTANCE = new ServerForTests();
    }
}
