/* (c) https://github.com/MontiCore/monticore */

/* generated from model null*/
/* generated by template symboltable.ModelLoader*/




package mc.embedding.external.host._symboltable;

import de.monticore.symboltable.ArtifactScope;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.logging.Log;
import mc.embedding.external.host._ast.ASTHost;

public class HostModelLoader extends de.monticore.modelloader.ModelingLanguageModelLoader<ASTHost> {

  public HostModelLoader(HostLanguage language) {
    super(language);
  }

  @Override
  protected void createSymbolTableFromAST(final ASTHost ast, final String modelName,
    final MutableScope enclosingScope, final ResolvingConfiguration resolvingConfiguration) {
    final HostSymbolTableCreator symbolTableCreator =
            getModelingLanguage().getSymbolTableCreator(resolvingConfiguration, enclosingScope).orElse(null);

    if (symbolTableCreator != null) {
      Log.debug("Start creation of symbol table for model \"" + modelName + "\".",
          HostModelLoader.class.getSimpleName());
      final Scope scope = symbolTableCreator.createFromAST(ast);

      if (!(scope instanceof ArtifactScope)) {
        Log.warn("0xA7001_587 Top scope of model " + modelName + " is expected to be an artifact scope, but"
          + " is scope \"" + scope.getName() + "\"");
      }

      Log.debug("Created symbol table for model \"" + modelName + "\".", HostModelLoader.class.getSimpleName());
    }
    else {
      Log.warn("0xA7002_587 No symbol created, because '" + getModelingLanguage().getName()
        + "' does not define a symbol table creator.");
    }
  }

  @Override
  public HostLanguage getModelingLanguage() {
    return (HostLanguage) super.getModelingLanguage();
  }
}
