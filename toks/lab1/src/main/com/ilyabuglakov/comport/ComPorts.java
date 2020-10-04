package main.com.ilyabuglakov.comport;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

public class ComPorts {

    private SerialPort com1;
    private SerialPort com2;

    Consumer<Optional<String>> onChange;

    private final String PORT1_NAME = "COM1";
    private final String PORT2_NAME = "COM2";
    private final int BAUD_RATE = SerialPort.BAUDRATE_115200;
    private final int DATA_BITS = SerialPort.DATABITS_8;
    private final int STOP_BITS = SerialPort.STOPBITS_2;
    private final int PARITY = SerialPort.PARITY_EVEN;

    public void openConnection() throws SerialPortException {
        com1 = createPort(PORT1_NAME);
        com1.openPort();
        com2 = createPort(PORT2_NAME);
        com2.openPort();
        for(SerialPort com : Arrays.asList(com1, com2)) {
            com.setParams(BAUD_RATE, DATA_BITS, STOP_BITS, PARITY);
        }
        com2.addEventListener(e -> onChange.accept(receiveInformation()),
                SerialPort.MASK_RXCHAR);
    }

    public boolean sendInformation(String info){
        try {
            com1.writeString(info);
        } catch (SerialPortException e) {
            return false;
        }
        return true;
    }

    public void closePorts(){
        if(com1.isOpened()) {
            try {
                com1.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
        if(com2.isOpened()){
            try {
                com2.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    public String getComParameters(){
        return "COM initialisation parameters: \n" +
                "Port 1 name: " + PORT1_NAME + "\n" +
                "Port 2 name: " + PORT2_NAME + "\n" +
                "Baud rate: " + BAUD_RATE + " bods" + "\n" +
                "Data bits amount: " + DATA_BITS + "\n" +
                "Stop bits amount: " + STOP_BITS + "\n" +
                "Parity: even";
    }

    public void setOnChange(Consumer<Optional<String>> onChange){
        this.onChange = onChange;
    }

    private Optional<String> receiveInformation(){
        String info;
        try {
            info = com2.readString();
        } catch (SerialPortException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(info);
    }

    private SerialPort createPort(String name){
        return new SerialPort(name);
    }

}
