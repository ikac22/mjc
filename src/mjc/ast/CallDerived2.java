// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class CallDerived2 extends Call {

    private CallStart CallStart;
    private CallEnd CallEnd;

    public CallDerived2 (CallStart CallStart, CallEnd CallEnd) {
        this.CallStart=CallStart;
        if(CallStart!=null) CallStart.setParent(this);
        this.CallEnd=CallEnd;
        if(CallEnd!=null) CallEnd.setParent(this);
    }

    public CallStart getCallStart() {
        return CallStart;
    }

    public void setCallStart(CallStart CallStart) {
        this.CallStart=CallStart;
    }

    public CallEnd getCallEnd() {
        return CallEnd;
    }

    public void setCallEnd(CallEnd CallEnd) {
        this.CallEnd=CallEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CallStart!=null) CallStart.accept(visitor);
        if(CallEnd!=null) CallEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CallStart!=null) CallStart.traverseTopDown(visitor);
        if(CallEnd!=null) CallEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CallStart!=null) CallStart.traverseBottomUp(visitor);
        if(CallEnd!=null) CallEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CallDerived2(\n");

        if(CallStart!=null)
            buffer.append(CallStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CallEnd!=null)
            buffer.append(CallEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CallDerived2]");
        return buffer.toString();
    }
}
