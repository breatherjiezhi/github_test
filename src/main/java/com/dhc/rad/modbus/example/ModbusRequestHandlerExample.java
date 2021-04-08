package com.dhc.rad.modbus.example;

import com.dhc.rad.modbus.handler.ModbusRequestHandler;
import com.dhc.rad.modbus.entity.func.request.ReadCoilsRequest;
import com.dhc.rad.modbus.entity.func.response.ReadCoilsResponse;
import com.dhc.rad.modbus.entity.func.request.ReadDiscreteInputsRequest;
import com.dhc.rad.modbus.entity.func.response.ReadDiscreteInputsResponse;
import com.dhc.rad.modbus.entity.func.request.ReadHoldingRegistersRequest;
import com.dhc.rad.modbus.entity.func.response.ReadHoldingRegistersResponse;
import com.dhc.rad.modbus.entity.func.request.ReadInputRegistersRequest;
import com.dhc.rad.modbus.entity.func.response.ReadInputRegistersResponse;
import com.dhc.rad.modbus.entity.func.request.WriteMultipleCoilsRequest;
import com.dhc.rad.modbus.entity.func.response.WriteMultipleCoilsResponse;
import com.dhc.rad.modbus.entity.func.request.WriteMultipleRegistersRequest;
import com.dhc.rad.modbus.entity.func.response.WriteMultipleRegistersResponse;
import com.dhc.rad.modbus.entity.func.WriteSingleCoil;
import com.dhc.rad.modbus.entity.func.WriteSingleRegister;
import java.util.BitSet;

/**
 *
 * @author
 */
public class ModbusRequestHandlerExample extends ModbusRequestHandler {

    @Override
    protected WriteSingleCoil writeSingleCoil(WriteSingleCoil request) {
        return request;
    }

    @Override
    protected WriteSingleRegister writeSingleRegister(WriteSingleRegister request) {
        return request;
    }

    @Override
    protected ReadCoilsResponse readCoilsRequest(ReadCoilsRequest request) {
        BitSet coils = new BitSet(request.getQuantityOfCoils());
        coils.set(0);
        coils.set(5);
        coils.set(8);

        return new ReadCoilsResponse(coils);
    }

    @Override
    protected ReadDiscreteInputsResponse readDiscreteInputsRequest(ReadDiscreteInputsRequest request) {
        BitSet coils = new BitSet(request.getQuantityOfCoils());
        coils.set(0);
        coils.set(5);
        coils.set(8);

        return new ReadDiscreteInputsResponse(coils);
    }

    @Override
    protected ReadInputRegistersResponse readInputRegistersRequest(ReadInputRegistersRequest request) {
        int[] registers = new int[request.getQuantityOfInputRegisters()];
        registers[0] = 0xFFFF;
        registers[1] = 0xF0F0;
        registers[2] = 0x0F0F;

        return new ReadInputRegistersResponse(registers);
    }

    @Override
    protected ReadHoldingRegistersResponse readHoldingRegistersRequest(ReadHoldingRegistersRequest request) {
        int[] registers = new int[request.getQuantityOfInputRegisters()];
        registers[0] = 0xFFFF;
        registers[1] = 0xF0F0;
        registers[2] = 0x0F0F;

        return new ReadHoldingRegistersResponse(registers);
    }

    @Override
    protected WriteMultipleRegistersResponse writeMultipleRegistersRequest(WriteMultipleRegistersRequest request) {
        return new WriteMultipleRegistersResponse(request.getStartingAddress(), request.getQuantityOfRegisters());
    }

    @Override
    protected WriteMultipleCoilsResponse writeMultipleCoilsRequest(WriteMultipleCoilsRequest request) {
        return new WriteMultipleCoilsResponse(request.getStartingAddress(), request.getQuantityOfOutputs());
    }

}
