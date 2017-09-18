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
package de.monticore.mccommon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.monticore.cardinality._ast.ASTCardinality;
import de.monticore.completeness._ast.ASTCompleteness;
import de.monticore.testmccommon._parser.TestMCCommonParser;
import de.monticore.umlmodifier._ast.ASTModifier;
import de.monticore.umlstereotype._ast.ASTStereoValue;
import de.monticore.umlstereotype._ast.ASTStereotype;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;


public class MCCommonUnitTest {
    
  // setup the language infrastructure
  TestMCCommonParser parser = new TestMCCommonParser() ;
  
  @BeforeClass
  public static void init() {
    // replace log by a sideffect free variant
    LogStub.init();
  }
  

  @Before
  public void setUp() { 
    Log.getFindings().clear();
  }
  

  // --------------------------------------------------------------------
  // UMLStereotype
  // --------------------------------------------------------------------

  // --------------------------------------------------------------------
  @Test
  public void testModifier() throws IOException {
    ASTModifier ast = parser.parseString_Modifier( "# final" ).get();
    assertEquals(true, ast.isProtected());
    assertEquals(true, ast.isFinal());
    assertEquals(false, ast.isLocal());
  }


  // --------------------------------------------------------------------
  @Test
  public void testModifierStereo() throws IOException {
    ASTModifier ast = parser.parseString_Modifier( "<<bla=\"x1\">>#+?" ).get();
    assertEquals(true, ast.isProtected());
    assertEquals(true, ast.isPublic());
    assertEquals(true, ast.isReadonly());
    assertEquals(false, ast.isFinal());
    assertEquals(true, ast.getStereotype().isPresent());
    ASTStereotype sty = ast.getStereotype().get();
    assertEquals("x1", sty.getValue("bla"));
  }



  // --------------------------------------------------------------------
  // UMLStereotype
  // --------------------------------------------------------------------

  // --------------------------------------------------------------------
  @Test
  public void testStereoValue() throws IOException {
    ASTStereoValue ast = parser.parseString_StereoValue( "bla=\"17\"" ).get();
    assertEquals("bla", ast.getName());
    Optional<String> os = ast.getSource();
    assertEquals(true, os.isPresent());
    assertEquals("17", os.get());
    assertEquals("17", ast.getValue());
  }


  // --------------------------------------------------------------------
  @Test
  public void testStereoValue2() throws IOException {
    ASTStereoValue ast = parser.parseString_StereoValue( "cc" ).get();
    assertEquals("cc", ast.getName());
    Optional<String> os = ast.getSource();
    assertEquals(false, os.isPresent());
    try {
      assertEquals("", os.get());
      fail("Expected an Exception to be thrown");
    } catch (java.util.NoSuchElementException ex) { }
    assertEquals("", ast.getValue());
  }


  // --------------------------------------------------------------------
  @Test
  public void testStereotype() throws IOException {
    ASTStereotype ast = parser.parseString_Stereotype( "<< a1 >>" ).get();
    List<ASTStereoValue> svl = ast.getValues();
    assertEquals(1, svl.size());
    assertEquals(true, ast.contains("a1"));
    assertEquals(false, ast.contains("bla"));
    assertEquals(true, ast.contains("a1",""));
    assertEquals(false, ast.contains("a1","wert1"));
  }


  // --------------------------------------------------------------------
  @Test
  public void testStereotype2() throws IOException {
    ASTStereotype ast = parser.parseString_Stereotype(
    	"<< bla, a1=\"wert1\" >>" ).get();
    List<ASTStereoValue> svl = ast.getValues();
    assertEquals(2, svl.size());
    assertEquals(true, ast.contains("a1"));
    assertEquals(false, ast.contains("a1",""));
    assertEquals(true, ast.contains("a1","wert1"));
  }


  // --------------------------------------------------------------------
  @Test
  public void testGetValue() throws IOException {
    ASTStereotype ast = parser.parseString_Stereotype(
        "<< bla, a1=\"wert1\" >>" ).get(); 
    assertEquals("wert1", ast.getValue("a1"));
    try {
      assertEquals("", ast.getValue("foo"));
      fail("Expected an Exception to be thrown");
    } catch (java.util.NoSuchElementException ex) { }
    assertEquals("", ast.getValue("bla"));
  }


  @Test
  public void testEnding() throws IOException {
    Optional<ASTStereotype> oast = parser.parseString_Stereotype(
        "<< bla, a1=\"wert1\" > >" ); 
    assertEquals(false, oast.isPresent());
  }


  // --------------------------------------------------------------------
  // Completeness
  // --------------------------------------------------------------------

  // --------------------------------------------------------------------
  @Test
  public void testBasics() throws IOException {
    ASTCompleteness ast = parser.parseString_Completeness( "(c)"  ).get();
    assertEquals(true, ast.isComplete());
    assertEquals(false, ast.isIncomplete());
  }


  // --------------------------------------------------------------------
  @Test
  public void testBasics2() throws IOException {
    ASTCompleteness ast = parser.parseString_Completeness( "(  ... )"  ).get();
    assertEquals(false, ast.isComplete());
    assertEquals(true, ast.isIncomplete());
    assertEquals(false, ast.isRightComplete());
    assertEquals(false, ast.isLeftComplete());
  }


  // --------------------------------------------------------------------
  @Test
  public void testBasics3() throws IOException {
    ASTCompleteness ast = parser.parseString_Completeness( "(...,c)"  ).get();
    assertEquals(false, ast.isComplete());
    assertEquals(false, ast.isIncomplete());
    assertEquals(true, ast.isRightComplete());
    assertEquals(false, ast.isLeftComplete());
  }


  // --------------------------------------------------------------------
  @Test
  public void testIllegalComplete() throws IOException {
    Optional<ASTCompleteness> ast = 
    		parser.parseString_Completeness( "(...,d)"  );
    assertEquals(false, ast.isPresent());
  }

  // --------------------------------------------------------------------
  // Cardinality
  // --------------------------------------------------------------------

  // --------------------------------------------------------------------
  @Test
  public void testMany() throws IOException {
    ASTCardinality ast = parser.parseString_Cardinality("[*]").get();
    assertEquals(true, ast.isMany());
    assertEquals(0, ast.getLowerBound());
    assertEquals(0, ast.getUpperBound());
  }


  // --------------------------------------------------------------------
  @Test
  public void testLowAndStar() throws IOException {
    ASTCardinality ast = parser.parseString_Cardinality("[7..*]").get();
    assertEquals(false, ast.isMany());
    assertEquals(true, ast.isNoUpperLimit());
    assertEquals(7, ast.getLowerBound());
    assertEquals(0, ast.getUpperBound());
  }


  // --------------------------------------------------------------------
  @Test
  public void testLowAndUp() throws IOException {
    ASTCardinality ast = parser.parseString_Cardinality("[17..235]").get();
    assertEquals(false, ast.isMany());
    assertEquals(17, ast.getLowerBound());
    assertEquals(235, ast.getUpperBound());
  }


  // --------------------------------------------------------------------
  @Test
  public void testSpace() throws IOException {
    ASTCardinality ast = parser.parseString_Cardinality(" [ 34 .. 15 ] ").get();
    assertEquals(false, ast.isMany());
    assertEquals(34, ast.getLowerBound());
    assertEquals(15, ast.getUpperBound());
  }


  // --------------------------------------------------------------------
  // Nachweis dass Cardinality zZ auch Hex und negatives als Integer akzeptiert
  // XXX BUG, BR -- zu beheben
  @Test
  public void testHex() throws IOException {
    Optional<ASTCardinality> oast = parser.parseString_Cardinality(
    		"[0x34..0x15]");
    // XXX SOLL:  assertEquals(false, oast.isPresent());
    ASTCardinality ast = oast.get();
    assertEquals(false, ast.isMany());
    assertEquals(3*16+4, ast.getLowerBound());
    assertEquals(1*16+5, ast.getUpperBound());
  }


}