package main.java.com.ilyabuglakov.bitstuffing;

import main.java.com.ilyabuglakov.bitstuffing.service.BitStuffer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainView extends JFrame {
    private JTextArea output;
    private JTextArea parameters;
    private JPanel contentPanel;
    private JTextField input;

    private final String flag = "01101000";
    private BitStuffer stuffer = new BitStuffer(flag);

    public MainView() {
        setContentPane(contentPanel);
        pack();
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n') {
                    transformInput();
                    input.setText("");
                } else if(e.getKeyChar()!='1' && e.getKeyChar()!='0') {
                    e.consume();
                }
            }
        });
        input.setSize(input.getWidth(), 100);
        setTitle("Bit stuffer");
        setSize(700, 500);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Arrays.asList(output, parameters).forEach(comp ->
                comp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5)));
        parameters.setText(stuffInfo());
    }

    private void transformInput(){
        if(input.getText().isEmpty())
            return;
        parameters.setText("");
        String info = stuffInfo() + "\nStuffed information: ";
        int startPos = info.length();
        String inputInfo = input.getText();
        Map.Entry<String, List<Integer>> tuple = stuffer.stuffString(inputInfo);
        String stuffedString = tuple.getKey();
        parameters.setText(info + stuffedString);
        Highlighter highlighter = parameters.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
        for(int pos: tuple.getValue()){
            try {
                pos = pos+startPos;
                highlighter.addHighlight(pos, pos+1, painter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        output.setText(stuffer.destuffString(stuffedString, tuple.getValue()));
    }

    private String stuffInfo(){
        return "Package flag: "+ flag;
    }
}
