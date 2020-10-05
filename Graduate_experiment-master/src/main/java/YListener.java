import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;


public class YListener extends JavaScriptParserBaseListener{
    public static int id = 0;
    class Node{
        String interval;
        int id;
        String type_;
    };
    public static int expression_ele = 0;
    public static int variable_decl = 0;
    public static int assign = 0;
    public static int formalparameterarg = 0;
    public static int expression = 0;
    public static int identifier = 0;
    public static int memberindex = 0;
    public static int expression_seq = 0;
    public static int numeric = 0;
    HashMap<String, Integer> map = new HashMap<String, Integer>(){
        {
            put("[",0);put("]",0);put("(",0);put(")",0);put("{",0);put("}",0);put(";",0);
            put(",",0);put("=",0);put("?",0);put("...",0);put(".",0);put("++",0);put("--",0);
            put("+",0);put("-",0);put("~",0);put("!",0);put("*",0);put("/",0);put("%",0);
            put("**",0);put("??",0);put("#",0);put(">>",0);put("<<",0);put(">>>",0);put("<",0);
            put(">",0);put("<=",0);put(">=",0);put("==",0);put("!=",0);put("===",0);put("!==",0);
            put("&",0);put("^",0);put("|",0);put("&&",0);put("||",0);put("*=",0);put("/=",0);
            put("%=",0);put("+=",0);put("-=",0);put("<<=",0);put(">>=",0);put(">>>=",0);put("&=",0);
            put("^=",0);put("|=",0);put("**=",0);put("=>",0);put("null",0);put("true",0);put("false",0);
            put("break",0);put("do",0);put("instanceof",0);put("typeof",0);put("case",0);put("else",0);
            put("new",0);put("var",0);put("catch",0);put("finally",0);put("return",0);put("void",0);put("continue",0);
            put("for",0);put("switch",0);put("while",0);put("debugger",0);put("function",0);put("this",0);put("with",0);
            put("default",0);put("if",0);put("throw",0);put("delete",0);put("in",0);put("try",0);put("as",0);
            put("from",0);put("class",0);put("enum",0);put("extends",0);put("super",0);put("const",0);put("export",0);
            put("import",0);put("async",0);put("await",0);put("implements",0);put("let",0);put("private",0);
            put("public",0);put("interface",0);put("package",0);put("protected",0);put("static",0);put("yield",0);
        }
    };
    ArrayList<Node> list = new ArrayList<>();
    @Override public void visitTerminal(TerminalNode node){
        String type_ = node.getText();
        if(numeric > 0){
            return ;
        }
        if(type_.startsWith("\"")||type_.startsWith("\'")){
            add_node(node,"StringLiteral");
            return ;
        }
        if(map.containsKey(type_)){
            add_node(node,type_);
            if(type_ == "function"){
                map.put("function",map.get("function")+1);
            }else if(type_ == "("&&map.get("function") > 0){
                map.put("function",map.get("function")-1);
            }
            return ;
        }
        if(map.get("function")==1){
            add_node(node,"function_name");
            map.put("function",0);
        }else if(expression > 0&& identifier > 0&&memberindex > 0&&expression_seq==memberindex){
            add_node(node,"array_name");
        }else if(expression > 0&& identifier > 0&&memberindex > 0&&expression_seq > memberindex) {
            add_node(node,"index_para");
        }else if(variable_decl > 0&&assign > 0){
            add_node(node,"variable_name");
        }else if(formalparameterarg > 0&&assign > 0){
            add_node(node,"formalparameter");
        }else if(expression > 0&& identifier > 0){
            add_node(node,"expression_para");
        }


    }

    private void add_node(TerminalNode node, String type_) {
        Node node_temp = new Node();
        node_temp.interval = node.getSourceInterval().toString();
        node_temp.type_ = type_;
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }

    @Override public void enterProgram(JavaScriptParser.ProgramContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Program";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }

    @Override public void exitProgram(JavaScriptParser.ProgramContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSourceElement(JavaScriptParser.SourceElementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "SourceElement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSourceElement(JavaScriptParser.SourceElementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterStatement(JavaScriptParser.StatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Statement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitStatement(JavaScriptParser.StatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBlock(JavaScriptParser.BlockContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Block";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBlock(JavaScriptParser.BlockContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterStatementList(JavaScriptParser.StatementListContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "StatementList";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitStatementList(JavaScriptParser.StatementListContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImportStatement(JavaScriptParser.ImportStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ImportStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImportStatement(JavaScriptParser.ImportStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImportFromBlock(JavaScriptParser.ImportFromBlockContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ImportFromBlock";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImportFromBlock(JavaScriptParser.ImportFromBlockContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterModuleItems(JavaScriptParser.ModuleItemsContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ModuleItems";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitModuleItems(JavaScriptParser.ModuleItemsContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImportDefault(JavaScriptParser.ImportDefaultContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ImportDefault";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImportDefault(JavaScriptParser.ImportDefaultContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImportNamespace(JavaScriptParser.ImportNamespaceContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ImportNamespace";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImportNamespace(JavaScriptParser.ImportNamespaceContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImportFrom(JavaScriptParser.ImportFromContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ImportFrom";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImportFrom(JavaScriptParser.ImportFromContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAliasName(JavaScriptParser.AliasNameContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AliasName";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAliasName(JavaScriptParser.AliasNameContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExportDeclaration(JavaScriptParser.ExportDeclarationContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ExportDeclaration";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExportDeclaration(JavaScriptParser.ExportDeclarationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExportDefaultDeclaration(JavaScriptParser.ExportDefaultDeclarationContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ExportDefaultDeclaration";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExportDefaultDeclaration(JavaScriptParser.ExportDefaultDeclarationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExportFromBlock(JavaScriptParser.ExportFromBlockContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ExportFromBlock";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExportFromBlock(JavaScriptParser.ExportFromBlockContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDeclaration(JavaScriptParser.DeclarationContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Declaration";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDeclaration(JavaScriptParser.DeclarationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVariableStatement(JavaScriptParser.VariableStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "VariableStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVariableStatement(JavaScriptParser.VariableStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVariableDeclarationList(JavaScriptParser.VariableDeclarationListContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "VariableDeclarationList";
        node_temp.id = id;
        id += 1;
        variable_decl += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVariableDeclarationList(JavaScriptParser.VariableDeclarationListContext ctx) {
        variable_decl -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "VariableDeclaration";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVariableDeclaration(JavaScriptParser.VariableDeclarationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEmptyStatement(JavaScriptParser.EmptyStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "EmptyStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEmptyStatement(JavaScriptParser.EmptyStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ExpressionStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExpressionStatement(JavaScriptParser.ExpressionStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIfStatement(JavaScriptParser.IfStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "IfStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIfStatement(JavaScriptParser.IfStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDoStatement(JavaScriptParser.DoStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "DoStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDoStatement(JavaScriptParser.DoStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWhileStatement(JavaScriptParser.WhileStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "WhileStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitWhileStatement(JavaScriptParser.WhileStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterForStatement(JavaScriptParser.ForStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ForStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitForStatement(JavaScriptParser.ForStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterForInStatement(JavaScriptParser.ForInStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ForInStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitForInStatement(JavaScriptParser.ForInStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterForOfStatement(JavaScriptParser.ForOfStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ForOfStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitForOfStatement(JavaScriptParser.ForOfStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVarModifier(JavaScriptParser.VarModifierContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "VarModifier";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVarModifier(JavaScriptParser.VarModifierContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterContinueStatement(JavaScriptParser.ContinueStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ContinueStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitContinueStatement(JavaScriptParser.ContinueStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBreakStatement(JavaScriptParser.BreakStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BreakStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBreakStatement(JavaScriptParser.BreakStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterReturnStatement(JavaScriptParser.ReturnStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ReturnStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitReturnStatement(JavaScriptParser.ReturnStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterYieldStatement(JavaScriptParser.YieldStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "YieldStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitYieldStatement(JavaScriptParser.YieldStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterWithStatement(JavaScriptParser.WithStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "WithStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitWithStatement(JavaScriptParser.WithStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "SwitchStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSwitchStatement(JavaScriptParser.SwitchStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCaseBlock(JavaScriptParser.CaseBlockContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "CaseBlock";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCaseBlock(JavaScriptParser.CaseBlockContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCaseClauses(JavaScriptParser.CaseClausesContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "CaseClauses";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCaseClauses(JavaScriptParser.CaseClausesContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCaseClause(JavaScriptParser.CaseClauseContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "CaseClause";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCaseClause(JavaScriptParser.CaseClauseContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDefaultClause(JavaScriptParser.DefaultClauseContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "DefaultClause";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDefaultClause(JavaScriptParser.DefaultClauseContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "LabelledStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLabelledStatement(JavaScriptParser.LabelledStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterThrowStatement(JavaScriptParser.ThrowStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ThrowStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitThrowStatement(JavaScriptParser.ThrowStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTryStatement(JavaScriptParser.TryStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "TryStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTryStatement(JavaScriptParser.TryStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCatchProduction(JavaScriptParser.CatchProductionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "CatchProduction";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCatchProduction(JavaScriptParser.CatchProductionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FinallyProduction";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFinallyProduction(JavaScriptParser.FinallyProductionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "DebuggerStatement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDebuggerStatement(JavaScriptParser.DebuggerStatementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FunctionDeclaration";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFunctionDeclaration(JavaScriptParser.FunctionDeclarationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ClassDeclaration";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitClassDeclaration(JavaScriptParser.ClassDeclarationContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterClassTail(JavaScriptParser.ClassTailContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ClassTail";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitClassTail(JavaScriptParser.ClassTailContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterClassElement(JavaScriptParser.ClassElementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ClassElement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitClassElement(JavaScriptParser.ClassElementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "MethodDefinition";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMethodDefinition(JavaScriptParser.MethodDefinitionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FormalParameterList";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFormalParameterList(JavaScriptParser.FormalParameterListContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FormalParameterArg";
        node_temp.id = id;
        formalparameterarg += 1;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFormalParameterArg(JavaScriptParser.FormalParameterArgContext ctx) {
        formalparameterarg -= 1;

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "LastFormalParameterArg";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLastFormalParameterArg(JavaScriptParser.LastFormalParameterArgContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFunctionBody(JavaScriptParser.FunctionBodyContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FunctionBody";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFunctionBody(JavaScriptParser.FunctionBodyContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSourceElements(JavaScriptParser.SourceElementsContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "SourceElements";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSourceElements(JavaScriptParser.SourceElementsContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrayLiteral(JavaScriptParser.ArrayLiteralContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArrayLiteral";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrayLiteral(JavaScriptParser.ArrayLiteralContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterElementList(JavaScriptParser.ElementListContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ElementList";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitElementList(JavaScriptParser.ElementListContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrayElement(JavaScriptParser.ArrayElementContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArrayElement";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrayElement(JavaScriptParser.ArrayElementContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ObjectLiteral";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitObjectLiteral(JavaScriptParser.ObjectLiteralContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPropertyExpressionAssignment(JavaScriptParser.PropertyExpressionAssignmentContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PropertyExpressionAssignment";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPropertyExpressionAssignment(JavaScriptParser.PropertyExpressionAssignmentContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterComputedPropertyExpressionAssignment(JavaScriptParser.ComputedPropertyExpressionAssignmentContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ComputedPropertyExpressionAssignment";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitComputedPropertyExpressionAssignment(JavaScriptParser.ComputedPropertyExpressionAssignmentContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFunctionProperty(JavaScriptParser.FunctionPropertyContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FunctionProperty";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFunctionProperty(JavaScriptParser.FunctionPropertyContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPropertyGetter(JavaScriptParser.PropertyGetterContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PropertyGetter";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPropertyGetter(JavaScriptParser.PropertyGetterContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPropertySetter(JavaScriptParser.PropertySetterContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PropertySetter";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPropertySetter(JavaScriptParser.PropertySetterContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPropertyShorthand(JavaScriptParser.PropertyShorthandContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PropertyShorthand";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPropertyShorthand(JavaScriptParser.PropertyShorthandContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPropertyName(JavaScriptParser.PropertyNameContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PropertyName";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPropertyName(JavaScriptParser.PropertyNameContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArguments(JavaScriptParser.ArgumentsContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Arguments";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArguments(JavaScriptParser.ArgumentsContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArgument(JavaScriptParser.ArgumentContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Argument";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArgument(JavaScriptParser.ArgumentContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterExpressionSequence(JavaScriptParser.ExpressionSequenceContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ExpressionSequence";
        node_temp.id = id;
        expression_seq += 1;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitExpressionSequence(JavaScriptParser.ExpressionSequenceContext ctx) {
        expression_seq -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTemplateStringExpression(JavaScriptParser.TemplateStringExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "TemplateStringExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTemplateStringExpression(JavaScriptParser.TemplateStringExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTernaryExpression(JavaScriptParser.TernaryExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "TernaryExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTernaryExpression(JavaScriptParser.TernaryExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLogicalAndExpression(JavaScriptParser.LogicalAndExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "LogicalAndExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLogicalAndExpression(JavaScriptParser.LogicalAndExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPowerExpression(JavaScriptParser.PowerExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PowerExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPowerExpression(JavaScriptParser.PowerExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPreIncrementExpression(JavaScriptParser.PreIncrementExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PreIncrementExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPreIncrementExpression(JavaScriptParser.PreIncrementExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterObjectLiteralExpression(JavaScriptParser.ObjectLiteralExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ObjectLiteralExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitObjectLiteralExpression(JavaScriptParser.ObjectLiteralExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMetaExpression(JavaScriptParser.MetaExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "MetaExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMetaExpression(JavaScriptParser.MetaExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterInExpression(JavaScriptParser.InExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "InExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitInExpression(JavaScriptParser.InExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLogicalOrExpression(JavaScriptParser.LogicalOrExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "LogicalOrExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLogicalOrExpression(JavaScriptParser.LogicalOrExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNotExpression(JavaScriptParser.NotExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "NotExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNotExpression(JavaScriptParser.NotExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPreDecreaseExpression(JavaScriptParser.PreDecreaseExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PreDecreaseExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPreDecreaseExpression(JavaScriptParser.PreDecreaseExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArgumentsExpression(JavaScriptParser.ArgumentsExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArgumentsExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArgumentsExpression(JavaScriptParser.ArgumentsExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAwaitExpression(JavaScriptParser.AwaitExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AwaitExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAwaitExpression(JavaScriptParser.AwaitExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterThisExpression(JavaScriptParser.ThisExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ThisExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitThisExpression(JavaScriptParser.ThisExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FunctionExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFunctionExpression(JavaScriptParser.FunctionExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterUnaryMinusExpression(JavaScriptParser.UnaryMinusExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "UnaryMinusExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitUnaryMinusExpression(JavaScriptParser.UnaryMinusExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssignmentExpression(JavaScriptParser.AssignmentExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AssignmentExpression";
        node_temp.id = id;
        expression += 1;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssignmentExpression(JavaScriptParser.AssignmentExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPostDecreaseExpression(JavaScriptParser.PostDecreaseExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PostDecreaseExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPostDecreaseExpression(JavaScriptParser.PostDecreaseExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterTypeofExpression(JavaScriptParser.TypeofExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "TypeofExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitTypeofExpression(JavaScriptParser.TypeofExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterInstanceofExpression(JavaScriptParser.InstanceofExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "InstanceofExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitInstanceofExpression(JavaScriptParser.InstanceofExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterUnaryPlusExpression(JavaScriptParser.UnaryPlusExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "UnaryPlusExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitUnaryPlusExpression(JavaScriptParser.UnaryPlusExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterDeleteExpression(JavaScriptParser.DeleteExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "DeleteExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitDeleteExpression(JavaScriptParser.DeleteExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterImportExpression(JavaScriptParser.ImportExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ImportExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitImportExpression(JavaScriptParser.ImportExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEqualityExpression(JavaScriptParser.EqualityExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "EqualityExpression";
        expression += 1;
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEqualityExpression(JavaScriptParser.EqualityExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBitXOrExpression(JavaScriptParser.BitXOrExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BitXOrExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBitXOrExpression(JavaScriptParser.BitXOrExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSuperExpression(JavaScriptParser.SuperExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "SuperExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSuperExpression(JavaScriptParser.SuperExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMultiplicativeExpression(JavaScriptParser.MultiplicativeExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "MultiplicativeExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMultiplicativeExpression(JavaScriptParser.MultiplicativeExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBitShiftExpression(JavaScriptParser.BitShiftExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BitShiftExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBitShiftExpression(JavaScriptParser.BitShiftExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterParenthesizedExpression(JavaScriptParser.ParenthesizedExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ParenthesizedExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitParenthesizedExpression(JavaScriptParser.ParenthesizedExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAdditiveExpression(JavaScriptParser.AdditiveExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AdditiveExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAdditiveExpression(JavaScriptParser.AdditiveExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterRelationalExpression(JavaScriptParser.RelationalExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "RelationalExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitRelationalExpression(JavaScriptParser.RelationalExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterPostIncrementExpression(JavaScriptParser.PostIncrementExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "PostIncrementExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitPostIncrementExpression(JavaScriptParser.PostIncrementExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterYieldExpression(JavaScriptParser.YieldExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "YieldExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitYieldExpression(JavaScriptParser.YieldExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBitNotExpression(JavaScriptParser.BitNotExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BitNotExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBitNotExpression(JavaScriptParser.BitNotExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNewExpression(JavaScriptParser.NewExpressionContext ctx) {
        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "NewExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNewExpression(JavaScriptParser.NewExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLiteralExpression(JavaScriptParser.LiteralExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "LiteralExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLiteralExpression(JavaScriptParser.LiteralExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrayLiteralExpression(JavaScriptParser.ArrayLiteralExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArrayLiteralExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrayLiteralExpression(JavaScriptParser.ArrayLiteralExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMemberDotExpression(JavaScriptParser.MemberDotExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "MemberDotExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMemberDotExpression(JavaScriptParser.MemberDotExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterClassExpression(JavaScriptParser.ClassExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ClassExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitClassExpression(JavaScriptParser.ClassExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterMemberIndexExpression(JavaScriptParser.MemberIndexExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "MemberIndexExpression";
        node_temp.id = id;
        id += 1;
        memberindex += 2;
        expression_seq += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitMemberIndexExpression(JavaScriptParser.MemberIndexExpressionContext ctx) {
        memberindex -= 2;
        expression_seq -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIdentifierExpression(JavaScriptParser.IdentifierExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "IdentifierExpression";
        node_temp.id = id;
        identifier += 1;
//        System.out.println(expression);
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIdentifierExpression(JavaScriptParser.IdentifierExpressionContext ctx) {
        identifier -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBitAndExpression(JavaScriptParser.BitAndExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BitAndExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBitAndExpression(JavaScriptParser.BitAndExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBitOrExpression(JavaScriptParser.BitOrExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BitOrExpression";
        node_temp.id = id;
        id += 1;
        expression += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBitOrExpression(JavaScriptParser.BitOrExpressionContext ctx) {
        expression -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssignmentOperatorExpression(JavaScriptParser.AssignmentOperatorExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AssignmentOperatorExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssignmentOperatorExpression(JavaScriptParser.AssignmentOperatorExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterVoidExpression(JavaScriptParser.VoidExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "VoidExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitVoidExpression(JavaScriptParser.VoidExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterCoalesceExpression(JavaScriptParser.CoalesceExpressionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "CoalesceExpression";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitCoalesceExpression(JavaScriptParser.CoalesceExpressionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssignable(JavaScriptParser.AssignableContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Assignable";
        node_temp.id = id;
        id += 1;
        assign += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssignable(JavaScriptParser.AssignableContext ctx) {
        assign -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "FunctionDecl";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitFunctionDecl(JavaScriptParser.FunctionDeclContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAnoymousFunctionDecl(JavaScriptParser.AnoymousFunctionDeclContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AnoymousFunctionDecl";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAnoymousFunctionDecl(JavaScriptParser.AnoymousFunctionDeclContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArrowFunction";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrowFunction(JavaScriptParser.ArrowFunctionContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrowFunctionParameters(JavaScriptParser.ArrowFunctionParametersContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArrowFunctionParameters";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrowFunctionParameters(JavaScriptParser.ArrowFunctionParametersContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterArrowFunctionBody(JavaScriptParser.ArrowFunctionBodyContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ArrowFunctionBody";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitArrowFunctionBody(JavaScriptParser.ArrowFunctionBodyContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterAssignmentOperator(JavaScriptParser.AssignmentOperatorContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "AssignmentOperator";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitAssignmentOperator(JavaScriptParser.AssignmentOperatorContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterLiteral(JavaScriptParser.LiteralContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Literal";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitLiteral(JavaScriptParser.LiteralContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterNumericLiteral(JavaScriptParser.NumericLiteralContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "NumericLiteral";
        node_temp.id = id;
        numeric += 1;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitNumericLiteral(JavaScriptParser.NumericLiteralContext ctx) {
        numeric -= 1;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterBigintLiteral(JavaScriptParser.BigintLiteralContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "BigintLiteral";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitBigintLiteral(JavaScriptParser.BigintLiteralContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterIdentifierName(JavaScriptParser.IdentifierNameContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "IdentifierName";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitIdentifierName(JavaScriptParser.IdentifierNameContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterReservedWord(JavaScriptParser.ReservedWordContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "ReservedWord";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitReservedWord(JavaScriptParser.ReservedWordContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterKeyword(JavaScriptParser.KeywordContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Keyword";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitKeyword(JavaScriptParser.KeywordContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterGetter(JavaScriptParser.GetterContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Getter";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitGetter(JavaScriptParser.GetterContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterSetter(JavaScriptParser.SetterContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Setter";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitSetter(JavaScriptParser.SetterContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void enterEos(JavaScriptParser.EosContext ctx) {

        Node node_temp = new Node();
        node_temp.interval = ctx.getSourceInterval().toString();
        node_temp.type_ = "Eos";
        node_temp.id = id;
        id += 1;
        list.add(node_temp);

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEos(JavaScriptParser.EosContext ctx) { }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
//    @Override public void enterEveryRule(ParserRuleContext ctx) {
//
//        Node node_temp = new Node();
//        node_temp.interval = ctx.getSourceInterval().toString();
//        node_temp.type_ = "EveryRule";
//        node_temp.id = id;
//        id += 1;
//        list.add(node_temp);
//
//    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */
    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.</p>
     */

    @Override public void visitErrorNode(ErrorNode node) {
        String type_ = node.getText();
        if(numeric > 0){
            return ;
        }
        if(type_.startsWith("\"")||type_.startsWith("\'")){
            add_node(node,"StringLiteral");
            return ;
        }
        if(map.containsKey(type_)){
            add_node(node,type_);
            if(type_ == "function"){
                map.put("function",map.get("function")+1);
            }else if(type_ == "("&&map.get("function") > 0){
                map.put("function",map.get("function")-1);
            }
            return ;
        }
        if(map.get("function")==1){
            add_node(node,"function_name");
            map.put("function",0);
        }else if(expression > 0&& identifier > 0&&memberindex > 0&&expression_seq==memberindex){
            add_node(node,"array_name");
        }else if(expression > 0&& identifier > 0&&memberindex > 0&&expression_seq > memberindex) {
            add_node(node,"index_para");
        }else if(variable_decl > 0&&assign > 0){
            add_node(node,"variable_name");
        }else if(formalparameterarg > 0&&assign > 0){
            add_node(node,"formalparameter");
        }else if(expression > 0&& identifier > 0){
            add_node(node,"expression_para");
        }

    }
}
