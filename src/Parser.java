/* Parser.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. Parser.java */
// Java Code to invoke the parser


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class Parser/*@bgen(jjtree)*/implements ParserTreeConstants, ParserConstants {/*@bgen(jjtree)*/
  protected static JJTParserState jjtree = new JJTParserState();
    static int errors = 0;

    public static void main(String args[]) throws ParseException, FileNotFoundException {

        if(args.length != 1 || !Pattern.matches(".*\\.yal$", args[0])){
            System.out.println("Usage: java Parser <file.yal>");
            System.exit(-1);
        }

        Parser parser = new Parser(new FileInputStream(args[0]));
        try{
            SimpleNode root = parser.Module();
            root.dump("");
            }
        catch (Exception e){
            e.printStackTrace(System.out);
        }

        System.out.println(".yal file parsed with " + errors + " errors");
    }

//funcao que nao consome o caracter passado por argumento
  static void error_skipto(int kind, String msg) throws ParseException {/*@bgen(jjtree) error_skipto */
ASTerror_skipto jjtn000 = new ASTerror_skipto(JJTERROR_SKIPTO);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);
try {ParseException e = generateParseException();  // generate the exception object.
  System.out.println(msg + " - " + e.toString());  // print the error message
  errors++;
  Token t;
  while(true)
  {
    t = getToken(1);
    if(t.kind == kind | t.kind == EOF){
        return;}
    getNextToken();
  }/*@bgen(jjtree)*/
} finally {
  if (jjtc000) {
    jjtree.closeNodeScope(jjtn000, true);
  }
}
  }

//funcao que consome o carater passado por argumento
  static void error_skipto_andEat(int kind, String msg) throws ParseException {/*@bgen(jjtree) error_skipto_andEat */
ASTerror_skipto_andEat jjtn000 = new ASTerror_skipto_andEat(JJTERROR_SKIPTO_ANDEAT);
boolean jjtc000 = true;
jjtree.openNodeScope(jjtn000);
try {ParseException e = generateParseException();  // generate the exception object.
  System.out.println(msg + " - " + e.toString());  // print the error message
  errors++;
  Token t;
  do {
    t = getNextToken();
  } while (t.kind != kind);/*@bgen(jjtree)*/
} finally {
  if (jjtc000) {
    jjtree.closeNodeScope(jjtn000, true);
  }
}
  }

  static final public SimpleNode Module() throws ParseException {/*@bgen(jjtree) MOD */
                            ASTMOD jjtn000 = new ASTMOD(JJTMOD);
                            boolean jjtc000 = true;
                            jjtree.openNodeScope(jjtn000);Token t;
    try {
      try {
        jj_consume_token(MODULE);
        t = jj_consume_token(ID);
jjtn000.value = t.image;
        jj_consume_token(LCHAVETA);
        label_1:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case ID:{
            ;
            break;
            }
          default:
            jj_la1[0] = jj_gen;
            break label_1;
          }
          Declaration();
        }
        label_2:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case FUNCTION:{
            ;
            break;
            }
          default:
            jj_la1[1] = jj_gen;
            break label_2;
          }
          Function();
        }
        jj_consume_token(RCHAVETA);
      } catch (ParseException e) {
error_skipto(RCHAVETA, "MODULE");
      }
jjtree.closeNodeScope(jjtn000, true);
       jjtc000 = false;
{if ("" != null) return jjtn000;}
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
    throw new Error("Missing return statement in function");
}

  static final public void Declaration() throws ParseException {/*@bgen(jjtree) DECL */
                           ASTDECL jjtn000 = new ASTDECL(JJTDECL);
                           boolean jjtc000 = true;
                           jjtree.openNodeScope(jjtn000);Token t;
    try {
      try {
        Element();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ASSIGN:{
          t = jj_consume_token(ASSIGN);
jjtn000.value = t.image;
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case 31:{
            ArrayDeclaration();
            break;
            }
          case ADDSUB_OP:
          case INTEGER:{
            ScalarDeclaration();
            break;
            }
          default:
            jj_la1[2] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
          break;
          }
        default:
          jj_la1[3] = jj_gen;
          ;
        }
      } catch (ParseException e) {
error_skipto(PVIRG, "Declaration");
      }
      jj_consume_token(PVIRG);
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static final public void Element() throws ParseException {/*@bgen(jjtree) ELEM */
                       ASTELEM jjtn000 = new ASTELEM(JJTELEM);
                       boolean jjtc000 = true;
                       jjtree.openNodeScope(jjtn000);Token t1;
    try {
      t1 = jj_consume_token(ID);
jjtn000.value = t1.image;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 31:{
        ArrayElement();
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        ;
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static final public void ArrayElement() throws ParseException {
    jj_consume_token(31);
    jj_consume_token(32);
}

  static final public void ScalarDeclaration() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ADDSUB_OP:{
      jj_consume_token(ADDSUB_OP);
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    jj_consume_token(INTEGER);
}

  static final public void ArrayDeclaration() throws ParseException {
    jj_consume_token(31);
    ArraySize();
    jj_consume_token(32);
}

  static final public void Function() throws ParseException {/*@bgen(jjtree) FUNCT */
                         ASTFUNCT jjtn000 = new ASTFUNCT(JJTFUNCT);
                         boolean jjtc000 = true;
                         jjtree.openNodeScope(jjtn000);Token t1, t2;
    try {
      t1 = jj_consume_token(FUNCTION);
jjtn000.value = t1.image;
      try {
        t2 = jj_consume_token(ID);
jjtn000.value = t2.image;
      } catch (ParseException e) {
error_skipto(LPAR, "FunctionID");
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ASSIGN:
      case 31:{
        FunctionAssign();
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        ;
      }
      try {
        FunctionDeclaration();
      } catch (ParseException e) {
error_skipto(LCHAVETA, "Function");
      }
      FunctionContent();
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static final public void FunctionAssign() throws ParseException {/*@bgen(jjtree) FUNCT_ASSI */
                                    ASTFUNCT_ASSI jjtn000 = new ASTFUNCT_ASSI(JJTFUNCT_ASSI);
                                    boolean jjtc000 = true;
                                    jjtree.openNodeScope(jjtn000);Token t1, t2;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 31:{
        ArrayElement();
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        ;
      }
      t1 = jj_consume_token(ASSIGN);
jjtn000.value = t1.image;
      t2 = jj_consume_token(ID);
jjtree.closeNodeScope(jjtn000, true);
                                                                            jjtc000 = false;
jjtn000.value = t2.image;
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

  static final public void FunctionDeclaration() throws ParseException {
    jj_consume_token(LPAR);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      Varlist();
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      ;
    }
    jj_consume_token(RPAR);
}

  static final public void FunctionContent() throws ParseException {
    jj_consume_token(LCHAVETA);
    Stmtlst();
}

  static final public void Varlist() throws ParseException {
    Element();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VIRG:{
        ;
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        break label_3;
      }
      jj_consume_token(VIRG);
      Element();
    }
}

  static final public void Stmtlst() throws ParseException {
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case WHILE:
      case IF:
      case ID:{
        ;
        break;
        }
      default:
        jj_la1[10] = jj_gen;
        break label_4;
      }
      Stmt();
    }
    jj_consume_token(RCHAVETA);
}

  static final public void Stmt() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WHILE:{
      While();
      break;
      }
    case IF:{
      If();
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      if (jj_2_1(3)) {
        Assign();
      } else {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ID:{
          try {
            Call();
          } catch (ParseException e) {
error_skipto(PVIRG, "Stmt");
          }
          jj_consume_token(PVIRG);
          break;
          }
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
}

  static final public void Assign() throws ParseException {
    Lhs();
    jj_consume_token(ASSIGN);
    try {
      Rhs();
    } catch (ParseException e) {
error_skipto(PVIRG, "Assign");
    }
    try {
      jj_consume_token(PVIRG);
    } catch (ParseException e) {
error_skipto_andEat(PVIRG, "Assign");
    }
}

  static final public void Lhs() throws ParseException {
    Access();
}

  static final public void Rhs() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ADDSUB_OP:
    case INTEGER:
    case ID:{
      Term();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ADDSUB_OP:
      case ARITH_OP:
      case BITWISE_OP:{
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ARITH_OP:{
          jj_consume_token(ARITH_OP);
          break;
          }
        case BITWISE_OP:{
          jj_consume_token(BITWISE_OP);
          break;
          }
        case ADDSUB_OP:{
          jj_consume_token(ADDSUB_OP);
          break;
          }
        default:
          jj_la1[13] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        Term();
        break;
        }
      default:
        jj_la1[14] = jj_gen;
        ;
      }
      break;
      }
    case 31:{
      jj_consume_token(31);
      ArraySize();
      jj_consume_token(32);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void ArrayAccess() throws ParseException {
    jj_consume_token(31);
    Index();
    jj_consume_token(32);
}

  static final public void ScalarAccess() throws ParseException {
    jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case 33:{
      jj_consume_token(33);
      jj_consume_token(SIZE);
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      ;
    }
}

  static final public void Access() throws ParseException {/*@bgen(jjtree) ACC */
                     ASTACC jjtn000 = new ASTACC(JJTACC);
                     boolean jjtc000 = true;
                     jjtree.openNodeScope(jjtn000);Token t1, t2;
    try {
      t1 = jj_consume_token(ID);
jjtn000.value = t1.image;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 31:{
        jj_consume_token(31);
        Index();
        jj_consume_token(32);
        break;
        }
      default:
        jj_la1[18] = jj_gen;
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case 33:{
          jj_consume_token(33);
          t2 = jj_consume_token(SIZE);
jjtn000.value = t2.image;
          break;
          }
        default:
          jj_la1[17] = jj_gen;
          ;
        }
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static final public void Index() throws ParseException {/*@bgen(jjtree) IND */
                    ASTIND jjtn000 = new ASTIND(JJTIND);
                    boolean jjtc000 = true;
                    jjtree.openNodeScope(jjtn000);Token t1, t2;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        t1 = jj_consume_token(ID);
jjtree.closeNodeScope(jjtn000, true);
                jjtc000 = false;
jjtn000.value = t1.image;
        break;
        }
      case INTEGER:{
        t2 = jj_consume_token(INTEGER);
jjtree.closeNodeScope(jjtn000, true);
                                                             jjtc000 = false;
jjtn000.value = t2.image;
        break;
        }
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

  static final public void Term() throws ParseException {/*@bgen(jjtree) TERM */
                    ASTTERM jjtn000 = new ASTTERM(JJTTERM);
                    boolean jjtc000 = true;
                    jjtree.openNodeScope(jjtn000);Token t1, t2;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ADDSUB_OP:{
        t1 = jj_consume_token(ADDSUB_OP);
jjtn000.value = t1.image;
        break;
        }
      default:
        jj_la1[20] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INTEGER:{
        t2 = jj_consume_token(INTEGER);
jjtree.closeNodeScope(jjtn000, true);
                                                                      jjtc000 = false;
jjtn000.value = t2.image;
        break;
        }
      default:
        jj_la1[21] = jj_gen;
        if (jj_2_2(3)) {
          Call();
        } else {
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case ID:{
            Access();
            break;
            }
          default:
            jj_la1[22] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
          }
        }
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

  static final public void ArraySize() throws ParseException {/*@bgen(jjtree) ARR_SIZE */
                             ASTARR_SIZE jjtn000 = new ASTARR_SIZE(JJTARR_SIZE);
                             boolean jjtc000 = true;
                             jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        ScalarAccess();
        break;
        }
      case INTEGER:{
        t = jj_consume_token(INTEGER);
jjtree.closeNodeScope(jjtn000, true);
                                     jjtc000 = false;
jjtn000.value = t.image;
        break;
        }
      default:
        jj_la1[23] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

  static final public void Call() throws ParseException {/*@bgen(jjtree) CALL */
                   ASTCALL jjtn000 = new ASTCALL(JJTCALL);
                   boolean jjtc000 = true;
                   jjtree.openNodeScope(jjtn000);Token t1, t2;
    try {
      t1 = jj_consume_token(ID);
jjtn000.value = t1.image;
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 33:{
        jj_consume_token(33);
        t2 = jj_consume_token(ID);
jjtn000.value = t2.image;
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        ;
      }
      jj_consume_token(LPAR);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INTEGER:
      case ID:
      case STRING:{
        ArgumentList();
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        ;
      }
      jj_consume_token(RPAR);
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static final public void ArgumentList() throws ParseException {
    Argument();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case VIRG:{
        ;
        break;
        }
      default:
        jj_la1[26] = jj_gen;
        break label_5;
      }
      jj_consume_token(VIRG);
      Argument();
    }
}

  static final public void Argument() throws ParseException {/*@bgen(jjtree) ARG */
                      ASTARG jjtn000 = new ASTARG(JJTARG);
                      boolean jjtc000 = true;
                      jjtree.openNodeScope(jjtn000);Token t1, t2, t3;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ID:{
        t1 = jj_consume_token(ID);
jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
jjtn000.value = t1.image;
        break;
        }
      case STRING:{
        t2 = jj_consume_token(STRING);
jjtree.closeNodeScope(jjtn000, true);
                                                             jjtc000 = false;
jjtn000.value = t2.image;
        break;
        }
      case INTEGER:{
        t3 = jj_consume_token(INTEGER);
jjtree.closeNodeScope(jjtn000, true);
                                                                                                          jjtc000 = false;
jjtn000.value = t3.image;
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

  static final public void While() throws ParseException {/*@bgen(jjtree) WHILE */
                     ASTWHILE jjtn000 = new ASTWHILE(JJTWHILE);
                     boolean jjtc000 = true;
                     jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(WHILE);
jjtn000.value = t.image;
      Exprtest();
      jj_consume_token(LCHAVETA);
      Stmtlst();
    } catch (Throwable jjte000) {
if (jjtc000) {
        jjtree.clearNodeScope(jjtn000);
        jjtc000 = false;
      } else {
        jjtree.popNode();
      }
      if (jjte000 instanceof RuntimeException) {
        {if (true) throw (RuntimeException)jjte000;}
      }
      if (jjte000 instanceof ParseException) {
        {if (true) throw (ParseException)jjte000;}
      }
      {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
        jjtree.closeNodeScope(jjtn000, true);
      }
    }
}

  static final public void Exprtest() throws ParseException {/*@bgen(jjtree) EXPR_TEST */
                            ASTEXPR_TEST jjtn000 = new ASTEXPR_TEST(JJTEXPR_TEST);
                            boolean jjtc000 = true;
                            jjtree.openNodeScope(jjtn000);Token t1;
    try {
      jj_consume_token(LPAR);
      try {
        Lhs();
        t1 = jj_consume_token(RELA_OP);
jjtn000.value = t1.image;
        Rhs();
      } catch (ParseException e) {
error_skipto(RPAR, "Exprtest");
      }
      try {
        jj_consume_token(RPAR);
      } catch (ParseException e) {
error_skipto_andEat(RPAR, "Exprtest");
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static final public void If() throws ParseException {/*@bgen(jjtree) IF */
               ASTIF jjtn000 = new ASTIF(JJTIF);
               boolean jjtc000 = true;
               jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(IF);
jjtn000.value = t.image;
      Exprtest();
      jj_consume_token(LCHAVETA);
      Stmtlst();
      try {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case ELSE:{
          jj_consume_token(ELSE);
          jj_consume_token(LCHAVETA);
          Stmtlst();
          break;
          }
        default:
          jj_la1[28] = jj_gen;
          ;
        }
      } catch (ParseException e) {
error_skipto(RCHAVETA, "If");
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
      jjtree.clearNodeScope(jjtn000);
      jjtc000 = false;
    } else {
      jjtree.popNode();
    }
    if (jjte000 instanceof RuntimeException) {
      {if (true) throw (RuntimeException)jjte000;}
    }
    if (jjte000 instanceof ParseException) {
      {if (true) throw (ParseException)jjte000;}
    }
    {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
      jjtree.closeNodeScope(jjtn000, true);
    }
    }
}

  static private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_3_1()
 {
    if (jj_3R_6()) return true;
    return false;
  }

  static private boolean jj_3R_14()
 {
    if (jj_scan_token(31)) return true;
    return false;
  }

  static private boolean jj_3R_28()
 {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3R_20()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_28()) {
    jj_scanpos = xsp;
    if (jj_3R_29()) return true;
    }
    return false;
  }

  static private boolean jj_3R_6()
 {
    if (jj_3R_8()) return true;
    if (jj_scan_token(ASSIGN)) return true;
    if (jj_3R_9()) return true;
    return false;
  }

  static private boolean jj_3R_29()
 {
    if (jj_scan_token(INTEGER)) return true;
    return false;
  }

  static private boolean jj_3R_12()
 {
    if (jj_scan_token(ID)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_16()) {
    jj_scanpos = xsp;
    if (jj_3R_17()) return true;
    }
    return false;
  }

  static private boolean jj_3R_16()
 {
    if (jj_scan_token(31)) return true;
    if (jj_3R_20()) return true;
    return false;
  }

  static private boolean jj_3R_24()
 {
    if (jj_3R_12()) return true;
    return false;
  }

  static private boolean jj_3R_25()
 {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3R_9()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) return true;
    }
    return false;
  }

  static private boolean jj_3R_13()
 {
    if (jj_3R_18()) return true;
    return false;
  }

  static private boolean jj_3R_19()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_25()) {
    jj_scanpos = xsp;
    if (jj_3R_26()) {
    jj_scanpos = xsp;
    if (jj_3R_27()) return true;
    }
    }
    return false;
  }

  static private boolean jj_3R_8()
 {
    if (jj_3R_12()) return true;
    return false;
  }

  static private boolean jj_3R_26()
 {
    if (jj_scan_token(STRING)) return true;
    return false;
  }

  static private boolean jj_3R_15()
 {
    if (jj_3R_19()) return true;
    return false;
  }

  static private boolean jj_3R_27()
 {
    if (jj_scan_token(INTEGER)) return true;
    return false;
  }

  static private boolean jj_3R_7()
 {
    if (jj_scan_token(ID)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_10()) jj_scanpos = xsp;
    if (jj_scan_token(LPAR)) return true;
    xsp = jj_scanpos;
    if (jj_3R_11()) jj_scanpos = xsp;
    if (jj_scan_token(RPAR)) return true;
    return false;
  }

  static private boolean jj_3R_11()
 {
    if (jj_3R_15()) return true;
    return false;
  }

  static private boolean jj_3R_23()
 {
    if (jj_scan_token(INTEGER)) return true;
    return false;
  }

  static private boolean jj_3R_22()
 {
    if (jj_scan_token(ADDSUB_OP)) return true;
    return false;
  }

  static private boolean jj_3R_21()
 {
    if (jj_scan_token(33)) return true;
    if (jj_scan_token(SIZE)) return true;
    return false;
  }

  static private boolean jj_3_2()
 {
    if (jj_3R_7()) return true;
    return false;
  }

  static private boolean jj_3R_18()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_22()) jj_scanpos = xsp;
    xsp = jj_scanpos;
    if (jj_3R_23()) {
    jj_scanpos = xsp;
    if (jj_3_2()) {
    jj_scanpos = xsp;
    if (jj_3R_24()) return true;
    }
    }
    return false;
  }

  static private boolean jj_3R_10()
 {
    if (jj_scan_token(33)) return true;
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3R_17()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_21()) jj_scanpos = xsp;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[29];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x8000000,0x800000,0x84000100,0x8000,0x80000000,0x100,0x80008000,0x80000000,0x8000000,0x80000,0x8003000,0x3000,0x8000000,0x700,0x700,0x8c000100,0x0,0x0,0x80000000,0xc000000,0x100,0x4000000,0x8000000,0xc000000,0x0,0x4c000000,0x80000,0x4c000000,0x4000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x2,0x2,0x0,0x0,0x0,0x0,0x0,0x0,0x2,0x0,0x0,0x0,0x0,};
	}
  static final private JJCalls[] jj_2_rtns = new JJCalls[2];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser.  ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new ParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new ParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jjtree.reset();
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  static private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[34];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 29; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 34; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  static private int trace_indent = 0;
  static private boolean trace_enabled;

/** Trace enabled. */
  static final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 2; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

}
