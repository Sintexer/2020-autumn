package main.java.com.ilyabuglakov.bitstuffing.service;

import main.java.com.ilyabuglakov.bitstuffing.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BitStuffer {

    private final String flag;

    public BitStuffer(String flag) {
        this.flag = flag;
    }

    public Map.Entry<String, List<Integer>> stuffString(String source){
        StringBuilder stuffedString = new StringBuilder(source);
        List<Integer> positions = new ArrayList<>();
        int pos = stuffedString.indexOf(flag);
        while (pos!=-1){
            positions.add(pos+flag.length()-1);
            stuffedString.insert(pos+flag.length()-1, invertBit(flag.charAt(flag.length()-1)));
            pos = stuffedString.indexOf(flag, pos);
        }
        Map.Entry<String, List<Integer>> tuple = Map.entry(stuffedString.toString(), positions);
        return tuple;
    }

    public String destuffString(String source, List<Integer> positions){
        StringBuilder destuffedString = new StringBuilder("");

        int prevPos = 0;
        for(int pos : positions){
            destuffedString.append(source.substring(prevPos, pos));
            prevPos = pos+1;
        }
        destuffedString.append(source.substring(prevPos, source.length()));
        return destuffedString.toString();
    }

    public List<Integer> stuffPositions(String source){
        List<Integer> positions = new ArrayList<>();
        int pos = 0;//flag.length();
        String subSequence = flag.substring(0 ,flag.length()-1);
        pos = source.indexOf(subSequence, pos);
        while (pos!=-1 && pos<source.length()-flag.length()){
            positions.add(pos+subSequence.length());
            pos = source.indexOf(subSequence, pos+1);
        }
        return positions;
    }

    public char invertBit(char bit){
        if(bit!='1' && bit!='0')
            throw new IllegalArgumentException();
        return bit=='1'? '0' : '1';
    }

}
