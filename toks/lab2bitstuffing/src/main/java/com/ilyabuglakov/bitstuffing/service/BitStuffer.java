package main.java.com.ilyabuglakov.bitstuffing.service;

import java.util.ArrayList;
import java.util.List;

public class BitStuffer {

    private final String flag;

    public BitStuffer(String flag) {
        this.flag = flag;
    }

    public String stuffString(String source) {
        StringBuilder stuffedString = new StringBuilder(source);
        String subflag = flag.substring(0, flag.length() - 1);
        int pos = stuffedString.indexOf(subflag);
        while (pos != -1) {
            stuffedString.insert(pos + subflag.length(), invertBit(flag.charAt(flag.length() - 1)));
            pos = stuffedString.indexOf(subflag, pos + subflag.length() - 1);
        }
        return stuffedString.toString();
    }

    public String destuffString(String source) {
        StringBuilder destuffedString = new StringBuilder("");
        String subflag = flag.substring(0, flag.length() - 1);
        int prevPos = 0;
        int pos = source.indexOf(subflag, prevPos);
        while (pos != -1) {
            destuffedString.append(source, prevPos, pos + subflag.length());
            prevPos = pos + subflag.length();
            pos = source.indexOf(subflag, prevPos);
        }

        destuffedString.append(source.substring(prevPos + 1));
        return destuffedString.toString();
    }

    public List<Integer> stuffPositions(String source) {
        List<Integer> positions = new ArrayList<>();
        int pos = 0; //flag.length();
        String subSequence = flag.substring(0, flag.length() - 1);
        pos = source.indexOf(subSequence, pos);
        while (pos != -1 && pos < source.length() - flag.length()) {
            positions.add(pos + subSequence.length());
            pos = source.indexOf(subSequence, pos + 1);
        }
        return positions;
    }

    public char invertBit(char bit) {
        if (bit != '1' && bit != '0')
            throw new IllegalArgumentException();
        return bit == '1' ? '0' : '1';
    }

}
