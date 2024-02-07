// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class CasualNewFactor extends NewFactor {

    private Type Type;
    private OptParen OptParen;

    public CasualNewFactor (Type Type, OptParen OptParen) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.OptParen=OptParen;
        if(OptParen!=null) OptParen.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public OptParen getOptParen() {
        return OptParen;
    }

    public void setOptParen(OptParen OptParen) {
        this.OptParen=OptParen;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(OptParen!=null) OptParen.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(OptParen!=null) OptParen.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(OptParen!=null) OptParen.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CasualNewFactor(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptParen!=null)
            buffer.append(OptParen.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CasualNewFactor]");
        return buffer.toString();
    }
}
