/* (c) https://github.com/MontiCore/monticore */

package mc.embedding.composite._symboltable;

import de.monticore.symboltable.resolving.SymbolAdapter;
import mc.embedding.embedded._symboltable.TextSymbol;
import mc.embedding.host._symboltable.ContentSymbol;

public class Text2ContentAdapter extends ContentSymbol implements SymbolAdapter<TextSymbol> {

  final TextSymbol adaptee;

  public Text2ContentAdapter(TextSymbol adaptee) {
    super(adaptee.getName());
    this.adaptee = adaptee;
  }

  @Override
  public TextSymbol getAdaptee() {
    return adaptee;
  }
}
