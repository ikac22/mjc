// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class ArrayVar extends Variable {

    private ArrVar ArrVar;

    public ArrayVar (ArrVar ArrVar) {
        this.ArrVar=ArrVar;
        if(ArrVar!=null) ArrVar.setParent(this);
    }

    public ArrVar getArrVar() {
        return ArrVar;
    }

    public void setArrVar(ArrVar ArrVar) {
        this.ArrVar=ArrVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrVar!=null) ArrVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrVar!=null) ArrVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrVar!=null) ArrVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayVar(\n");

        if(ArrVar!=null)
            buffer.append(ArrVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayVar]");
        return buffer.toString();
    }
}
