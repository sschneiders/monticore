/* (c) https://github.com/MontiCore/monticore */

package de.monticore;

import de.monticore.mcliterals._ast.ASTBooleanLiteral;
import de.monticore.mcliterals._ast.ASTCharLiteral;
import de.monticore.mcliterals._ast.ASTDoubleLiteral;
import de.monticore.mcliterals._ast.ASTFloatLiteral;
import de.monticore.mcliterals._ast.ASTIntLiteral;
import de.monticore.mcliterals._ast.ASTLongLiteral;
import de.monticore.mcliterals._ast.ASTMCLiteralsNode;
import de.monticore.mcliterals._ast.ASTNullLiteral;
import de.monticore.mcliterals._ast.ASTSignedDoubleLiteral;
import de.monticore.mcliterals._ast.ASTSignedFloatLiteral;
import de.monticore.mcliterals._ast.ASTSignedIntLiteral;
import de.monticore.mcliterals._ast.ASTSignedLongLiteral;
import de.monticore.mcliterals._ast.ASTStringLiteral;
import de.monticore.mcliterals._visitor.MCLiteralsVisitor;
import de.monticore.prettyprint.IndentPrinter;

public class MCLiteralsPrettyPrinterConcreteVisitor implements MCLiteralsVisitor {
  
  private MCLiteralsVisitor realThis = this;
  
  // printer to use
  protected IndentPrinter printer = null;
  
  /**
   * Constructor for de.monticore.literals.prettyprint.LiteralsPrettyPrinterConcreteVisitor.
   * @param printer
   */
  public MCLiteralsPrettyPrinterConcreteVisitor(IndentPrinter printer) {
    super();
    this.printer = printer;
  }

  /**
   * @return the printer
   */
  public IndentPrinter getPrinter() {
    return this.printer;
  }

  /**
   * Prints a "null" literal
   * 
   * @param a null literal
   */
  @Override
  public void visit(ASTNullLiteral a) {
    printer.print("null");
  }
  
  /**
   * Prints a boolean literal
   * 
   * @param a boolean literal
   */
  @Override
  public void visit(ASTBooleanLiteral a) {
    printer.print(a.getValue());
  }
  
  /**
   * Prints a char literal
   * 
   * @param a char literal
   */
  @Override
  public void visit(ASTCharLiteral a) {
    printer.print("'" + a.getSource() + "'");
  }
  
  /**
   * Prints a string literal
   * 
   * @param a string literal
   */
  @Override
  public void visit(ASTStringLiteral a) {
    printer.print("\"" + a.getSource() + "\"");
  }
  
  /**
   * Prints a int literal
   * 
   * @param a int literal
   */
  @Override
  public void visit(ASTIntLiteral a) {
    printer.print(a.getSource());
  }
  
  /**
   * Prints a long literal
   * 
   * @param a long literal
   */
  @Override
  public void visit(ASTLongLiteral a) {
    printer.print(a.getSource());
  }
  
  /**
   * Prints a float literal
   * 
   * @param a float literal
   */
  @Override
  public void visit(ASTFloatLiteral a) {
    printer.print(a.getSource());
  }
  
  /**
   * Prints a double literal
   * 
   * @param a double literal
   */
  @Override
  public void visit(ASTDoubleLiteral a) {
    printer.print(a.getSource());
  }
  
  /**
   * Prints a signed double literal.
   * 
   * @param ast a signed double literal
   */
  @Override
  public void visit(ASTSignedDoubleLiteral ast) {
    printer.print(ast.getSource());
  }
  
  /**
   * Prints a signed float literal.
   * 
   * @param ast a signed float literal
   */
  @Override
  public void visit(ASTSignedFloatLiteral ast) {
    printer.print(ast.getSource());
  }
  
  /**
   * Prints a signed int literal.
   * 
   * @param ast a signed int literal
   */
  @Override
  public void visit(ASTSignedIntLiteral ast) {
    printer.print(ast.getSource());
  }
  
  /**
   * Prints a signed long literal.
   * 
   * @param ast a signed long literal
   */
  @Override
  public void visit(ASTSignedLongLiteral ast) {
    printer.print(ast.getSource());
  }
  
  /**
   * This method prettyprints a given node from literal grammar.
   * 
   * @param a A node from literal grammar.
   * @return String representation.
   */
  public String prettyprint(ASTMCLiteralsNode a) {
    a.accept(getRealThis());
    return printer.getContent();
  }

  /**
   * @see de.monticore.literals.literals._visitor.LiteralsVisitor#setRealThis(de.monticore.literals.literals._visitor.LiteralsVisitor)
   */
  @Override
  public void setRealThis(MCLiteralsVisitor realThis) {
    this.realThis = realThis;
  }

  /**
   * @see de.monticore.literals.literals._visitor.LiteralsVisitor#getRealThis()
   */
  @Override
  public MCLiteralsVisitor getRealThis() {
    return realThis;
  }

}
