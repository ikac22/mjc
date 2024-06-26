package mjc;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import mjc.ast.Program;
import mjc.exception.*;
import mjc.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;

import java.io.*;

public class MJSemanticTest {
    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    static class ParseException extends Exception {
        ParseException() {
            super("Input program had syntax errors.");
        }
    }

    public static void main(String[] args) throws Exception {
        Logger log = Logger.getLogger(MJSemanticTest.class);

        Reader br = null;
        try {
            File sourceCode = new File("test/programs/program.mj");
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));
            Yylex lexer = new Yylex(br);

            MJParser parser = new MJParser(lexer);
            Program prog = parser.parse_or_throw();

            log.info(prog.toString(""));
            log.info("=".repeat(30));

            Tab.init();
            SemanticVisitor v = new SemanticVisitor();
            if (v.containsErrors(prog)) {
                log.error("Semantic errors detected...");
                System.exit(30);
            }

            log.info("=".repeat(30));
            Tab.dump();

            log.info("All semantic checks passed");
        } catch (ParseException pe) {
            System.exit(20);
        } catch (UnexpectedSymbolException us) {
            System.exit(10);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
