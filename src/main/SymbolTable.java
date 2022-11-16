package main;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    private String currentScope;
    private final HashMap<String, Symbol> scopes;

    private final ArrayList<String> keywords = new ArrayList<>(){{
        add("while");
        add("if");
        add("elif");
        add("else");
        add("Double");
        add("Integer");
        add("String");
        add("Boolean");
        add("Void");
    }};

    public SymbolTable(){
        this.currentScope = "global";
        this.scopes = new HashMap<>();
        this.scopes.put("global", new Symbol());
        addScope("print");
        addFunction("print", "Void");
        addScope("input");
        addFunction("input", "String");
        this.currentScope = "input";
        addParam("string", "String");
        addParam("length", "Integer");
        addScope("concat");
        addFunction("concat", "String");
        this.currentScope = "concat";
        addParam("s1", "String");
        addParam("s2", "String");
        addScope("length");
        addFunction("length", "Integer");
        this.currentScope = "length";
        addParam("string", "String");
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
        if (scopes.containsKey(currentScope)){
            Symbol symbol = scopes.get(currentScope);
            return symbol.addSymbol(symbolName, type);
        }
        return false;
    }

    public void addParam(String paramName, String type){
        if (scopes.containsKey(currentScope)){
            Symbol symbol = scopes.get(currentScope);
            symbol.addParam(paramName, type);
        }
    }

    public int getParamLength(String functionName){
        if (scopes.containsKey(functionName)){
            Symbol symbol = scopes.get(functionName);
            return symbol.getParamLength();
        }
        return -1;
    }

    public String getParamType(String functionName, int index) {
        if (scopes.containsKey(functionName)){
            Symbol symbol = scopes.get(functionName);
            return symbol.getParamType(index);
        }
        return null;
    }

    public String getType (String symbolName) {
        Symbol symbol = scopes.get(currentScope);
        if (symbol.hasSymbol(symbolName)){
            return symbol.getType(symbolName);
        }
        return null;

    }

    public boolean hasFunction(String functionName) {
        Symbol symbol = scopes.get("global");
        return symbol.hasSymbol(functionName);
    }

    public boolean addFunction(String functionName, String type) {
        Symbol symbol = scopes.get("global");
        return symbol.addSymbol(functionName, type);
    }

    public String getFunctionReturnType(String functionName){
        Symbol symbol = scopes.get("global");
        return symbol.getType(functionName);
    }

    public String getCurrentFunctionReturnType(){
        Symbol symbol = scopes.get("global");
        return symbol.getType(currentScope);
    }

    public boolean hasKeywords () {
        boolean hasKeyword = false;
        for (String scope : scopes.keySet()) {
            Symbol symbol = scopes.get(scope);
            for (String keyword : keywords) {
                if (symbol.hasSymbol(keyword)) {
                    hasKeyword = true;
                }
            }
        }
        return hasKeyword;
    }
}
