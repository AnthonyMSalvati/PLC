package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Symbol {

    private final ArrayList<String> paramList;
    private final HashMap<String, String> symbolTable;

    public Symbol() {
        paramList = new ArrayList<>();
        symbolTable = new HashMap<>();
    }

    public boolean hasSymbol (String symbolName) {
        return symbolTable.containsKey(symbolName);
    }

    public boolean addSymbol (String symbolName, String type) {
        if (!symbolTable.containsKey(symbolName)) {
            symbolTable.put(symbolName, type);
            return true;
        }
        return false;
    }

    public boolean addParam(String symbolName, String type) {
        if (!symbolTable.containsKey(symbolName)) {
            if (addSymbol(symbolName, type)) {
                paramList.add(symbolName);
            }
            return true;
        }
        return false;
    }

    public int getParamLength(){
        return paramList.size();
    }

    public String getParamType(int index){
        return paramList.get(index);
    }

    public String getType (String symbolName){
        if (symbolTable.containsKey(symbolName)){
            return symbolName;
        } else {
            return null;
        }
    }
}
