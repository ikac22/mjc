package mjc;


import org.apache.log4j.Logger;
import mjc.ast.*;

public class RuleVisitor extends VisitorAdaptor {

    int printCallCount = 0;
    int varDeclCount = 0;

    Logger log = Logger.getLogger(getClass());

    public void visit(VarDecl vardecl) {
        varDeclCount++;
        log.info("Variable declaration detected!");
    }

    public void visit(PrintStmt print) {
        printCallCount++;
        log.info("Print call detected!");
    }

}
