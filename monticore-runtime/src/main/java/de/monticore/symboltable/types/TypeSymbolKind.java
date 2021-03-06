/* (c) https://github.com/MontiCore/monticore */

package de.monticore.symboltable.types;

import de.monticore.symboltable.SymbolKind;

/**
 * @author Pedram Mir Seyed Nazari
 *
 */
public class TypeSymbolKind implements SymbolKind {
  private static final String NAME = "de.monticore.symboltable.types.TypeSymbolKind";

  protected TypeSymbolKind() {
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public boolean isKindOf(SymbolKind kind) {
    return NAME.equals(kind.getName()) || SymbolKind.super.isKindOf(kind);
  }
}
