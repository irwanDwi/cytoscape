/**  Copyright (c) 2003 Institute for Systems Biology
 **  This program is free software; you can redistribute it and/or modify
 **  it under the terms of the GNU General Public License as published by
 **  the Free Software Foundation; either version 2 of the License, or
 **  any later version.
 **
 **  This program is distributed in the hope that it will be useful,
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  The software and
 **  documentation provided hereunder is on an "as is" basis, and the
 **  Institute for Systems Biology has no obligations to provide maintenance, 
 **  support, updates, enhancements or modifications.  In no event shall the
 **  Institute for Systems Biology be liable to any party for direct, 
 **  indirect, special,incidental or consequential damages, including 
 **  lost profits, arising out of the use of this software and its 
 **  documentation, even if the Institute for Systems Biology 
 **  has been advised of the possibility of such damage. See the
 **  GNU General Public License for more details.
 **   
 **  You should have received a copy of the GNU General Public License
 **  along with this program; if not, write to the Free Software
 **  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 **/
/**
 * A container of a MutualInfo.
 * It contains two <code>String</code>s for the node names, an <code>ArrayList</code>
 * of common neighbors, and the <code>double</code> score.
 *
 * @author Greg Carter
 * @author Iliana Avila (transfered to C2.0)
 */
package phenotypeGenetics;

import java.util.*;

public class MutualInfo{

  /**
   * The <code>String</code> canonical names of nodes 1 and 2.
   */
  protected String[] nodeName = new String[2];

  /**
   * The <code>String</code> alleleForms of nodes 1 and 2.
   */
  protected String[] alleleForm = new String[2];

  /**
   * The <code>ArrayList</code> of common neighbors and their alleleForms.
   */
  protected ArrayList commonNeighbors;

  /**
   * The <code>ArrayList</code> of edges to those neighbors
   */
  protected ArrayList edges;

  /**
   * The <code>int</code> number of common tests.
   */
  protected int commonTests;

  /**
   * The score from <code>MutualInfoCalculator</code>.
   */
  protected double score;

  /**
   * The mean score and standard deviation of the random samples.
   */
  protected double scoreRS;
  protected double sdRS;

  /**
   * The p-value from <code>MutualInfoCalculator</code>.
   */
  protected double pValue;

  /**
   * Emtpy Constructor.
   */
  public MutualInfo(){}

  /**
   * Constructor.
   * 
   * @param name1
   * @param name2
   * @param neighbors
   * @param score
   */
  public MutualInfo(String name1, String allele1, String name2, String allele2, 
			ArrayList neighbors, ArrayList theEdges, int commonTests, 
			double score, double pVal,
			double mean, double sd ){
    setNodeName1(name1);
    setAlleleForm1(allele1);
    setNodeName2(name2);
    setAlleleForm2(allele2);
    setCommonNeighbors(neighbors);
    setEdges(theEdges);
    setCommonTests( commonTests );
    setScore(score);
    setPValue(pVal);
    setScoreRS(mean);
    setSdRS(sd);
  }//MutualInfo

  /**
   * Sets the <code>String</code> name of Node 1
   */
  public void setNodeName1 (String name){
    this.nodeName[0] = name;
  }//setNodeName1

  /**
   * Sets the <code>String</code> alleleForm of Node 1
   */
  public void setAlleleForm1 (String allele){
    this.alleleForm[0] = allele;
  }//setAlleleForm1

  /**
   * Sets the <code>String</code> name of Node 2
   */
  public void setNodeName2 (String name){
    this.nodeName[1] = name;
  }//setNodeName2

  /**
   * Sets the <code>String</code> alleleForm of Node 1
   */
  public void setAlleleForm2 (String allele){
    this.alleleForm[1] = allele;
  }//setAlleleForm2

  /**
   * Sets the <code>ArrayList</code> of common nearest neighbors
   */
  public void setCommonNeighbors (ArrayList neighbors){
    this.commonNeighbors = neighbors;
  }//setCommonNeighbors

  /** * Sets the score for this <code>MutualInfo</code>.
   */
  public void setScore (double aScore){
    this.score = aScore;
  }//setScore

  /**
   * Sets the mean random score for this <code>MutualInfo</code>.
   */
  public void setScoreRS (double aScore){
    this.scoreRS = aScore;
  }//setScoreRS

  /**
   * Sets the random sd for this <code>MutualInfo</code>.
   */
  public void setSdRS (double aSd){
    this.sdRS = aSd;
  }//setSdRS

  /**
   * Sets the p-value for this <code>MutualInfo</code>.
   */
  public void setPValue (double aPVal){
    this.pValue = aPVal;
  }//setPValue

  /**
   * Sets the number of common tests.
   */
  public void setCommonTests(int common){
    this.commonTests = common;
  }//setCommonTests

  /**
   * Gets the <code>String</code> name of Node index
   */
  public String getNodeName(int index){
    String name = null;
    if ( index == 1 | index == 2 ) { name = this.nodeName[index-1];};
    return name;
  }//getNodeName

  /**
   * Gets the <code>String</code> alleleForm of Node index
   */
  public String getAlleleForm(int index){
    String name = null;
    if ( index == 1 | index == 2 ) { name = this.alleleForm[index-1];};
    return name;
  }//getAlleleForm

  /**
   * Gets the <code>String</code> name of Node 1
   */
  public String getNodeName1(){
    return this.nodeName[0];
  }//getNodeName1

  /**
   * Gets the <code>String</code> alleleForm of Node 1
   */
  public String getAlleleForm1(){
    return this.alleleForm[0];
  }//getAlleleForm1

  /**
   * Gets the <code>String</code> name of Node 2
   */
  public String getNodeName2(){
    return this.nodeName[1];
  }//getNodeName2

  /**
   * Gets the <code>String</code> alleleForm of Node 2
   */
  public String getAlleleForm2(){
    return this.alleleForm[1];
  }//getAlleleForm2

  /**
   * Gets the <code>ArrayList</code> of common nearest neighbors
   */
  public ArrayList getCommonNeighbors(){
    return this.commonNeighbors;
  }//getCommonNeighbors

  /**
   * Gets the <code>ArrayList</code> of edges
   */
  public ArrayList getEdges(){
    return this.edges;
  }//getEdges

  /**
   * Sets the <code>ArrayList</code> of edges
   */
  public void setEdges( ArrayList theEdges ){
    this.edges = theEdges;
  }//setEdges

  /**
   * Gets the number of common tests.
   */
  public int getCommonTests(){
    return this.commonTests;
  }//getCommonTests

  /**
   * Gets the <code>int</code> number of nearest neighbors
   */
  public int getSize(){
    return this.commonNeighbors.size();
  }//getSize

  /**
   * @return the score for this <code>MutualInfo</code>.
   */
  public double getScore (){
    return this.score;
  }//getScore

  /**
   * @return the mean random score for this <code>MutualInfo</code>.
   */
  public double getScoreRS (){
    return this.scoreRS;
  }//getScoreRS

  /**
   * @return the random sd for this <code>MutualInfo</code>.
   */
  public double getSdRS (){
    return this.sdRS;
  }//get

  /**
   * @return the pval for this <code>MutualInfo</code>.
   */
  public double getPValue(){
    return this.pValue;
  }//getPValue

  
}//MutualInfo
