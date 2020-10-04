package main.com.ilyabuglakov.comport;

import jssc.SerialPortException;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.util.function.Consumer;

public class ComMessenger extends JFrame {
    private JPanel contentPanel;
    private JTextArea output;
    private JTextArea properties;
    private JLabel outputLabel;
    private JLabel propertiesLabel;
    private JTextField input;
    private JLabel inputLabel;
    private JButton send;
    private JScrollPane outputScrollPane;

    private ComPorts comPorts;

    public ComMessenger(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        input.addKeyListener(new InputListener());
        send.addActionListener(e -> reflectInput());
        setContentPane(contentPanel);
        pack();
        setTitle("Com Messenger");
        setSize(500, 300);
        setResizable(false);
        comPorts = new ComPorts();
        comPorts.setOnChange(new Output());
        preparePorts();
        outputScrollPane.getVerticalScrollBar().setAutoscrolls(true);
    }

    private class Output implements Consumer<Optional<String>>{
        @Override
        public void accept(Optional<String> s) {
            if(!s.isPresent()) {
                warnUser("An error occurred while receiving information through COM2");
                System.exit(1);
            }
            if(!output.getText().isEmpty())
                output.setText("");
            output.append( s.get());

        }
    }

    private void preparePorts(){
        try {
            comPorts.openConnection();
        } catch (SerialPortException e) {
            warnUser("An error occurred. Can't open port\n" + e.getMessage().split(";")[0]);
            System.exit(1);
        }
        properties.setText(comPorts.getComParameters());
    }

    private void reflectInput(){
        if(!input.getText().isEmpty()){
            if(!comPorts.sendInformation(input.getText())){
                warnUser("An error occurred while sending information");
                criticalExit();
            }
            input.setText("");
        }
    }

    private void criticalExit(){
        comPorts.closePorts();
        System.exit(1);
    }

    private class InputListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                reflectInput();
            }
        }
    }

    private void warnUser(String warnMessage){
        JOptionPane.showMessageDialog(this, warnMessage);
    }

}
