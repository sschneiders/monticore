/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package de.monticore.groovy

import de.monticore.codegen.cd2java.types.TypeResolverGenerator

debug("--------------------------------", LOG_ID)
debug("MontiCore", LOG_ID)
debug(" - eating your models since 2005", LOG_ID)
debug("--------------------------------", LOG_ID)
debug("Input files    : " + _configuration.getGrammarsAsStrings(), LOG_ID)
debug("Modelpath      : " + _configuration.getModelPathAsStrings(), LOG_ID)
debug("Output dir     : " + out, LOG_ID)
debug("Handcoded path : " + _configuration.getHandcodedPathAsStrings(), LOG_ID)

globalScope = createGlobalScope(modelPath)

while (grammarIterator.hasNext()) {
    // Parse grammar
    astGrammar = parseGrammar(grammarIterator.next())

    if (astGrammar.isPresent()) {
        astGrammar = astGrammar.get();

        astGrammar = createSymbolsFromAST(globalScope, astGrammar)

        // Transform AST-Grammar -> AST-CD
        astClassDiagramWithST = deriveCD(astGrammar, glex, globalScope)

        // Generate Visitor And Type Resolver
        TypeResolverGenerator.generate(glex, globalScope, astClassDiagramWithST, out)
    }
}
