package mjc;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import mjc.ast.Program;
import mjc.exception.ParseException;
import mjc.exception.UnexpectedSymbolException;
//import mjc.ast.Program;
//import mjc.exception.ParseException;
//import mjc.exception.UnexpectedSymbolException;
import mjc.util.Log4JUtils;
import mjc.util.MJDumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.Tab;

import java.io.*;

public class MJParserTest {
    static {
        DOMConfigurator.configure("config/log4j.xml");
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    
    public static void main(String[] args) throws Exception {
        Logger log = Logger.getLogger(MJParserTest.class);

        Reader br = null;
        try {
            File sourceCode = new File("test/programs/program.mj");
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));
            Yylex lexer = new Yylex(br);

            MJParser parser = new MJParser(lexer);
            
            Symbol s = parser.parse();
         
            
            if(parser.errorDetected) {
            	log.warn("Ther is a syntax error!");
            	return;
            }
            
            MJTab.mjTabInit();
            Program prog = (Program)(s.value);

            log.info("=".repeat(30));

            SemanticVisitor v = new SemanticVisitor();
            prog.traverseBottomUp(v);
            
            MJTab.dump(new MJDumpSymbolTableVisitor());
        } catch (ParseException pe) {
            System.exit(20);
        } catch (UnexpectedSymbolException us) {
            System.exit(10);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    System.exit(2);
                }
            }
        }
    }
}
