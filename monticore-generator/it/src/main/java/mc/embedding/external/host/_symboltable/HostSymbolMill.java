/* generated by template symboltable.SymbolMill*/



package mc.embedding.external.host._symboltable;

import static mc.embedding.external.host._symboltable.HostSymbol.HostSymbolBuilder;

public class HostSymbolMill {

  private static HostSymbolMill getMill() {
    if (mill == null) {
      mill = new HostSymbolMill();
    }
    return mill;
  }

  protected static HostSymbolMill mill = null;

    /* generated by template symboltable.mill.Attribute*/
  
  protected static HostSymbolMill millHost = null;


  protected HostSymbolMill() {}

    /* generated by template symboltable.mill.Method*/

  

  public static HostSymbolBuilder hostSymbolBuilder() {
    if(millHost == null) {
      millHost = getMill();
    }

    return millHost._hostSymbolBuilder();
  }

  protected HostSymbolBuilder _hostSymbolBuilder() {
    return new HostSymbolBuilder();
  }


}