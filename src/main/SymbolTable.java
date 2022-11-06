package main;

import java.util.HashMap;

public class SymbolTable {

    private String currentScope;
    private final HashMap<String, Symbol> scopes;

    public SymbolTable(){
        this.currentScope = "global";
        this.scopes = new HashMap<>();
        this.scopes.put("global", new Symbol());
    }

    public boolean addScope (String functionName) {
        if (!scopes.containsKey(functionName)){
            scopes.put(functionName, new Symbol());
            return true;
        }
        return false;
    }

    public void removeScope (String functionName) {
        scopes.remove(functionName);
    }

    public boolean changeScope (String functionName) {
        if (!scopes.containsKey(functionName)){
            currentScope = functionName;
            return true;
        }
        return false;
    }

    public boolean addSymbol (String symbolName, String type) {
        if (!scopes.containsKey(currentScope)){
            Symbol symbol = scopes.get(currentScope);
            return symbol.addSymbol(symbolName, type);
        }
        return false;
    }

    public String getType (String symbolName) {
        if (!scopes.containsKey(currentScope)){
            Symbol symbol = scopes.get(currentScope);
            if (symbol.hasSymbol(symbolName)){
                return symbol.getType(symbolName);
            }
            return null;
        }
        return null;
    }

    public HashMap<String, Symbol> getSymbolTable()
    {
        return this.scopes;
    }
}
