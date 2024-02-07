package mjc;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import mjc.ast.Program;
import mjc.exception.UnexpectedSymbolException;
import mjc.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.mj.runtime.Run;
import rs.etf.pp1.mj.runtime.disasm;
import rs.etf.pp1.symboltable.Tab;

import java.io.*;

public class MJGenerationTest {
    static {
    	DOMConfigurator.configure("config/log4j.xml");
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    static class ParseException extends Exception {
        ParseException() {
            super("Input program had syntax errors.");
        }
    }

    public static void main(String[] args) throws Exception {
        Logger log = Logger.getLogger(MJGenerationTest.class);

        Reader br = null;
        try {
            File sourceCode = new File("test/programs/test302.mj");
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));
            Yylex lexer = new Yylex(br);

            MJParser parser = new MJParser(lexer);
            Program prog = (Program)parser.parse().value;

            //log.info(prog.toString(""));
            log.info("=".repeat(30));

            MJTab.mjTabInit();
            SemanticVisitor v = new SemanticVisitor();
            prog.traverseBottomUp(v);
            Tab.dump();
            if (v.errorDetected) {
                log.error("Semantic errors detected...");
                System.exit(30);
            }

            log.info("=".repeat(30));
            //Tab.dump();
            log.info("All semantic checks passed");

            log.info("=".repeat(30));
            log.info("Generating code");

            File objFile = new File("program.obj");
            CodeGenerator codeGenerator = new CodeGenerator();
            Code.dataSize = v.staticVars;
            prog.traverseBottomUp(codeGenerator);
            Code.write(new FileOutputStream(objFile));
            log.info("Object file generated.");
            
            disasm.main(new String[] {"program.obj"});
            
            Run.main(new String[] {"program.obj"});
        } catch (mjc.exception.ParseException pe) {
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
