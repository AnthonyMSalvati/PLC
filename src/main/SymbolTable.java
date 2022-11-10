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

    public void changeScope (String functionName) {
        currentScope = functionName;
    }

    public boolean addSymbol (String symbolName, String type) {
        if (!scopes.containsKey(currentScope)){
            Symbol symbol = scopes.get(currentScope);
            return symbol.addSymbol(symbolName, type);
        }
        return false;
    }

    public boolean addParam(String paramName, String type){
        if (!scopes.containsKey(currentScope)){
            Symbol symbol = scopes.get(currentScope);
            return symbol.addParam(paramName, type);
        }
        return false;
    }

    public int getParamLength(String functionName){
        if (!scopes.containsKey(functionName)){
            Symbol symbol = scopes.get(functionName);
            return symbol.getParamLength();
        }
        return -1;
    }

    public String getType (String symbolName) {
        Symbol symbol = scopes.get(currentScope);
        if (symbol.hasSymbol(symbolName)){
            return symbol.getType(symbolName);
        }
        symbol = scopes.get("global");
        if(symbol.hasSymbol(symbolName)){
            return symbol.getType(symbolName);
        }
        return null;

    }

    public boolean addFunction(String functionName, String type) {
        Symbol symbol = scopes.get("global");
        return symbol.addSymbol(functionName, type);
    }

    public HashMap<String, Symbol> getSymbolTable()
    {
        return this.scopes;
    }
}
