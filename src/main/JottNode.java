package main;

public class JottNode implements JottTree{

    private String value;

    @Override
    public String convertToJott() {
        return value;
    }

    @Override
    public String convertToJava() {
        return null;
    }

    @Override
    public String convertToC() {
        return null;
    }

    @Override
    public String convertToPython(int nestLevel) {
        return null;
    }

    @Override
    public boolean validateTree(SymbolTable symbolTable) throws Exception {
        return false;
    }
}
