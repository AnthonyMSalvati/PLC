package main;

import java.util.HashMap;

public class Symbol {

    private final HashMap<String, String> symbolTable = new HashMap<>();

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

    public String getType (String symbolName){
        if (symbolTable.containsKey(symbolName)){
            return symbolName;
        } else {
            return null;
        }
    }
}
