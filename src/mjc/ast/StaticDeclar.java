// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class StaticDeclar extends StaticDecl {

    private StaticDecl StaticDecl;
    private Static Static;
    private VarDecl VarDecl;

    public StaticDeclar (StaticDecl StaticDecl, Static Static, VarDecl VarDecl) {
        this.StaticDecl=StaticDecl;
        if(StaticDecl!=null) StaticDecl.setParent(this);
        this.Static=Static;
        if(Static!=null) Static.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public StaticDecl getStaticDecl() {
        return StaticDecl;
    }

    public void setStaticDecl(StaticDecl StaticDecl) {
        this.StaticDecl=StaticDecl;
    }

    public Static getStatic() {
        return Static;
    }

    public void setStatic(Static Static) {
        this.Static=Static;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StaticDecl!=null) StaticDecl.accept(visitor);
        if(Static!=null) Static.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StaticDecl!=null) StaticDecl.traverseTopDown(visitor);
        if(Static!=null) Static.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StaticDecl!=null) StaticDecl.traverseBottomUp(visitor);
        if(Static!=null) Static.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StaticDeclar(\n");

        if(StaticDecl!=null)
            buffer.append(StaticDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Static!=null)
            buffer.append(Static.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StaticDeclar]");
        return buffer.toString();
    }
}
