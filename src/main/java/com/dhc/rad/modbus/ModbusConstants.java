package com.dhc.rad.modbus;

/**
 *
 * @author
 */
public class ModbusConstants {

    public static final int ERROR_OFFSET = 0x80;

    public static final int SYNC_RESPONSE_TIMEOUT = 2000; //milliseconds
    public static final int TRANSACTION_IDENTIFIER_MAX = 100; //affects memory usage of library

    public static final int ADU_MAX_LENGTH = 260;
    public static final int MBAP_LENGTH = 7;

    public static final int DEFAULT_MODBUS_PORT = 502;
    public static final int MODBUS_DEFAULT_PORT = 502;
    
    public static final short DEFAULT_PROTOCOL_IDENTIFIER = 0;
    public static final short DEFAULT_UNIT_IDENTIFIER = 255;

    // public static final String MODBUS_DEFAULT_IP = "192.168.0.1";
     public static final String MODBUS_DEFAULT_IP = "127.0.0.1";
}
