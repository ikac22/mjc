// generated with ast extension for cup
// version 0.8
// 6/1/2024 19:38:8


package mjc.ast;

public class ForUnmatched extends ForLoopUnmatched {

    private ForStart ForStart;
    private OptDesignStmtList OptDesignStmtList;
    private ForCond ForCond;
    private OptCondFact OptCondFact;
    private OptDesignStmtList OptDesignStmtList1;
    private ForBodyStart ForBodyStart;
    private Unmatched Unmatched;

    public ForUnmatched (ForStart ForStart, OptDesignStmtList OptDesignStmtList, ForCond ForCond, OptCondFact OptCondFact, OptDesignStmtList OptDesignStmtList1, ForBodyStart ForBodyStart, Unmatched Unmatched) {
        this.ForStart=ForStart;
        if(ForStart!=null) ForStart.setParent(this);
        this.OptDesignStmtList=OptDesignStmtList;
        if(OptDesignStmtList!=null) OptDesignStmtList.setParent(this);
        this.ForCond=ForCond;
        if(ForCond!=null) ForCond.setParent(this);
        this.OptCondFact=OptCondFact;
        if(OptCondFact!=null) OptCondFact.setParent(this);
        this.OptDesignStmtList1=OptDesignStmtList1;
        if(OptDesignStmtList1!=null) OptDesignStmtList1.setParent(this);
        this.ForBodyStart=ForBodyStart;
        if(ForBodyStart!=null) ForBodyStart.setParent(this);
        this.Unmatched=Unmatched;
        if(Unmatched!=null) Unmatched.setParent(this);
    }

    public ForStart getForStart() {
        return ForStart;
    }

    public void setForStart(ForStart ForStart) {
        this.ForStart=ForStart;
    }

    public OptDesignStmtList getOptDesignStmtList() {
        return OptDesignStmtList;
    }

    public void setOptDesignStmtList(OptDesignStmtList OptDesignStmtList) {
        this.OptDesignStmtList=OptDesignStmtList;
    }

    public ForCond getForCond() {
        return ForCond;
    }

    public void setForCond(ForCond ForCond) {
        this.ForCond=ForCond;
    }

    public OptCondFact getOptCondFact() {
        return OptCondFact;
    }

    public void setOptCondFact(OptCondFact OptCondFact) {
        this.OptCondFact=OptCondFact;
    }

    public OptDesignStmtList getOptDesignStmtList1() {
        return OptDesignStmtList1;
    }

    public void setOptDesignStmtList1(OptDesignStmtList OptDesignStmtList1) {
        this.OptDesignStmtList1=OptDesignStmtList1;
    }

    public ForBodyStart getForBodyStart() {
        return ForBodyStart;
    }

    public void setForBodyStart(ForBodyStart ForBodyStart) {
        this.ForBodyStart=ForBodyStart;
    }

    public Unmatched getUnmatched() {
        return Unmatched;
    }

    public void setUnmatched(Unmatched Unmatched) {
        this.Unmatched=Unmatched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForStart!=null) ForStart.accept(visitor);
        if(OptDesignStmtList!=null) OptDesignStmtList.accept(visitor);
        if(ForCond!=null) ForCond.accept(visitor);
        if(OptCondFact!=null) OptCondFact.accept(visitor);
        if(OptDesignStmtList1!=null) OptDesignStmtList1.accept(visitor);
        if(ForBodyStart!=null) ForBodyStart.accept(visitor);
        if(Unmatched!=null) Unmatched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForStart!=null) ForStart.traverseTopDown(visitor);
        if(OptDesignStmtList!=null) OptDesignStmtList.traverseTopDown(visitor);
        if(ForCond!=null) ForCond.traverseTopDown(visitor);
        if(OptCondFact!=null) OptCondFact.traverseTopDown(visitor);
        if(OptDesignStmtList1!=null) OptDesignStmtList1.traverseTopDown(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseTopDown(visitor);
        if(Unmatched!=null) Unmatched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForStart!=null) ForStart.traverseBottomUp(visitor);
        if(OptDesignStmtList!=null) OptDesignStmtList.traverseBottomUp(visitor);
        if(ForCond!=null) ForCond.traverseBottomUp(visitor);
        if(OptCondFact!=null) OptCondFact.traverseBottomUp(visitor);
        if(OptDesignStmtList1!=null) OptDesignStmtList1.traverseBottomUp(visitor);
        if(ForBodyStart!=null) ForBodyStart.traverseBottomUp(visitor);
        if(Unmatched!=null) Unmatched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForUnmatched(\n");

        if(ForStart!=null)
            buffer.append(ForStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptDesignStmtList!=null)
            buffer.append(OptDesignStmtList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForCond!=null)
            buffer.append(ForCond.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptCondFact!=null)
            buffer.append(OptCondFact.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptDesignStmtList1!=null)
            buffer.append(OptDesignStmtList1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForBodyStart!=null)
            buffer.append(ForBodyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Unmatched!=null)
            buffer.append(Unmatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForUnmatched]");
        return buffer.toString();
    }
}
