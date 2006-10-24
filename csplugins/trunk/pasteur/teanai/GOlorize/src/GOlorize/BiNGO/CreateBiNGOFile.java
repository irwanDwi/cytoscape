package GOlorize.BiNGO;

/* * Copyright (c) 2005 Flanders Interuniversitary Institute for Biotechnology (VIB)
 * *
 * * Authors : Steven Maere, Karel Heymans
 * *
 * * This program is free software; you can redistribute it and/or modify
 * * it under the terms of the GNU General Public License as published by
 * * the Free Software Foundation; either version 2 of the License, or
 * * (at your option) any later version.
 * *
 * * This program is distributed in the hope that it will be useful,
 * * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * * The software and documentation provided hereunder is on an "as is" basis,
 * * and the Flanders Interuniversitary Institute for Biotechnology
 * * has no obligations to provide maintenance, support,
 * * updates, enhancements or modifications.  In no event shall the
 * * Flanders Interuniversitary Institute for Biotechnology
 * * be liable to any party for direct, indirect, special,
 * * incidental or consequential damages, including lost profits, arising
 * * out of the use of this software and its documentation, even if
 * * the Flanders Interuniversitary Institute for Biotechnology
 * * has been advised of the possibility of such damage. See the
 * * GNU General Public License for more details.
 * *
 * * You should have received a copy of the GNU General Public License
 * * along with this program; if not, write to the Free Software
 * * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * *
 * * Authors: Steven Maere, Karel Heymans
 * * Date: Mar.25.2005
 * * Description: Class which creates a file with information about the selected
 * * cluster: ontology type and curator, time of creation, alpha,
 * * sort of test and correction, p-values and corrected 
 * * p-values, term id and name, x, X, n, N.     
 **/


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

import cytoscape.data.annotation.Ontology;
import cytoscape.data.annotation.Annotation;
import cytoscape.Cytoscape ;
import cytoscape.view.CytoscapeDesktop ;
import javax.swing.* ;

/********************************************************************
 * CreateBiNGOFile.java     Steven Maere & Karel Heymans (c) March 2005
 * --------------------
 *
 * Class which creates a file with information about the selected
 * cluster: ontology type and curator, time of creation, alpha,
 * sort of test and correction, p-values and corrected 
 * p-values, term id and name, x, X, n, N.
 ********************************************************************/


public class CreateBiNGOFile{
    
    
   	/*--------------------------------------------------------------
      FIELDS.
      --------------------------------------------------------------*/

	/** hashmap with key termID and value pvalue.*/
	private HashMap testMap;
	/** hashmap with key termID and value corrected pvalue.*/
	private HashMap correctionMap;
	/** hashmap with key termID and value x.*/
	private HashMap mapSmallX;
	/** hashmap with key termID and value n.*/
	private HashMap mapSmallN;
	/** integer with X.*/
	private int bigX;
	/** integer with N.*/
	private int bigN;
	/** String with alpha value.*/
	private String alphaString;
	/** String with used test.*/
	private String testString;
	/** String with used correction.*/
	private String correctionString;
	/** String for over- or underrepresentation.*/
	private String overUnderString;
	/** the annotation (remapped, i.e. including all parent annotations)*/
	private Annotation annotation;
	/** the ontology.*/
	private Ontology ontology;
	/** the annotation file path.*/
	private String annotationFile ;
	/** the ontology file path.*/
	private String ontologyFile ;
	/** the dir for saving the data file.*/
	private String dirName;
	/** the file name for the data file.*/
	private String fileName;
	/** the clusterVsString.*/
	private String clusterVsString;
	/** the categoriesString.*/
	private String catString;
	/** vector with the names of the selected nodes.*/
	private Vector selectedCanonicalNameVector;
	/** hashmap with keys the GO categories and values HashSets of test set genes annotated to that category*/
	private HashMap annotatedGenes ;
	
	private final String NONE = "---";
	/** constant string for the checking of numbers of categories, all categories.*/
  	private final String CATEGORY_ALL = "All categories";
  	/** constant string for the checking of numbers of categories, before correction.*/
  	private final String CATEGORY_BEFORE_CORRECTION = "Overrepresented categories before correction";
  	/** constant string for the checking of numbers of categories, after correction.*/
 	private final String CATEGORY_CORRECTION = "Overrepresented categories after correction";
	

    
    
    
    
	/*--------------------------------------------------------------
      CONSTRUCTORS.
      --------------------------------------------------------------*/
    
	/**
	 * Constructor for a simple count, no test, no correction.
	 *
	 * @param testMap HashMap with key: termID and value: pvalue.
	 * @param mapSmallX HashMap with key: termID and value: x.
	 * @param mapSmallN HashMap with key: termID and value: n.
	 * @param bigX int with value of X.
	 * @param bigN int with value of N.
	 * @param alphaString String with value for the significance level.
	 * @param ontology the Ontology.
	 * @param testString String with the name of the test.
	 * @param fileName String with the name for the data-file.
	 * @param clusterVsString String with option against what cluster must be tested.
	 * @param selectedCanonicalNameVector Vector with the selected genes.
	 */
/*	CreateBiNGOFile(HashMap mapSmallX, 
					HashMap mapSmallN,
					int bigX,
					int bigN,
					String alphaString,
					Annotation annotation,
					Ontology ontology,
					String annotationFile,
					String ontologyFile,
					String testString,
					String dirName,
					String fileName,
					String clusterVsString,
					String catString,
					Vector selectedCanonicalNameVector){
						
		this.testMap = null;
		this.correctionMap = null;
		this.mapSmallX = mapSmallX;
		this.mapSmallN = mapSmallN;
		this.bigX = bigX;
		this.bigN = bigN;
		this.alphaString = alphaString;
		this.annotation = annotation ;
		this.ontology = ontology;
		this.annotationFile = annotationFile ;
		this.ontologyFile = ontologyFile ;
		this.testString = testString;
		this.correctionString = NONE ;
		this.dirName = dirName;
		this.fileName = fileName;
		this.clusterVsString = clusterVsString;
		this.catString = catString ;
		this.selectedCanonicalNameVector = selectedCanonicalNameVector;
		this.annotatedGenes = new HashMap() ;
	}
	*/
		/**
	 * Constructor for an overrepresentation calculation without correction.
	 *
	 * @param testMap HashMap with key: termID and value: pvalue.
	 * @param mapSmallX HashMap with key: termID and value: x.
	 * @param mapSmallN HashMap with key: termID and value: n.
	 * @param bigX int with value of X.
	 * @param bigN int with value of N.
	 * @param alphaString String with value for the significance level.
	 * @param ontology the Ontology.
	 * @param testString String with the name of the test.
	 * @param fileName String with the name for the data-file.
	 * @param clusterVsString String with option against what cluster must be tested.
	 * @param selectedCanonicalNameVector Vector with the selected genes.
	 */
/*	CreateBiNGOFile(HashMap testMap,
					HashMap mapSmallX, 
					HashMap mapSmallN,
					int bigX,
					int bigN,
					String alphaString,
					Annotation annotation,
					Ontology ontology,
					String annotationFile,
					String ontologyFile,
					String testString,
					String dirName,
					String fileName,
					String clusterVsString,
					String catString,
					Vector selectedCanonicalNameVector){
						
		this.testMap = testMap;
		this.correctionMap = null;
		this.mapSmallX = mapSmallX;
		this.mapSmallN = mapSmallN;
		this.bigX = bigX;
		this.bigN = bigN;
		this.alphaString = alphaString;
		this.annotation = annotation ;
		this.ontology = ontology;
		this.annotationFile = annotationFile ;
		this.ontologyFile = ontologyFile ;
		this.testString = testString;
		this.correctionString = NONE ;
		this.dirName = dirName;
		this.fileName = fileName;
		this.clusterVsString = clusterVsString;
		this.catString = catString ;
		this.selectedCanonicalNameVector = selectedCanonicalNameVector;
		this.annotatedGenes = new HashMap() ;
	}
*/	
	
	/**
	 * Constructor for an overrepresentation calculation with a correction.
	 *
	 * @param testMap HashMap with key: termID and value: pvalue.
	 * @param correctionMap HashMap with key: termID and value:  corrected pvalue.
	 * @param mapSmallX HashMap with key: termID and value: #.
	 * @param mapSmallN HashMap with key: termID and value: n.
	 * @param bigX int with value of X.
	 * @param bigN int with value of N.
	 * @param alphaString String with value for significance level.
	 * @param ontology  the Ontology.
	 * @param testString String with the name of the test.
	 * @param correctionString String with the name of the correction.
	 * @param fileName String with the name for the data-file.
	 * @param clusterVsString String with option against what cluster must be tested.
	 * @param selectedCanonicalNameVector Vector with the selected genes.
	 */
	CreateBiNGOFile(HashMap testMap,
					HashMap correctionMap, 
					HashMap mapSmallX, 
					HashMap mapSmallN,
					int bigX,
					int bigN,
					String alphaString,
					Annotation annotation,
					Ontology ontology,
					String annotationFile,
					String ontologyFile,
					String testString,
					String correctionString,
					String overUnderString,
					String dirName,
					String fileName,
					String clusterVsString,
					String catString,
					Vector selectedCanonicalNameVector){
					
		this.testMap = testMap;
		this.correctionMap = correctionMap;
		this.mapSmallX = mapSmallX;
		this.mapSmallN = mapSmallN;
		this.bigX = bigX;
		this.bigN = bigN;
		this.alphaString = alphaString;
		this.annotation = annotation ;
		this.ontology = ontology;
		this.annotationFile = annotationFile ;
		this.ontologyFile = ontologyFile ;
		this.testString = testString;
		this.correctionString = correctionString;
		this.overUnderString = overUnderString ;
		this.dirName = dirName;
		this.fileName = fileName;		
		this.clusterVsString = clusterVsString;
		this.catString = catString;
		this.selectedCanonicalNameVector = selectedCanonicalNameVector;
		this.annotatedGenes = new HashMap() ;
	}
    
 
	/*--------------------------------------------------------------
      METHODS.
      --------------------------------------------------------------*/

	/**
	 * Method that creates the file with information about the cluster.
	 *
	 * without correction:
	 * -------------------
	 * termID <tab> pvalue <tab> x <tab> n <tab> X <tab> N <tab> description <tab> test set genes in GO category <\n>
	 *
	 * with correction:
	 * ----------------
	 * termID <tab> pvalue <tab> corrected pvalues <tab> x <tab> n <tab> X <tab> N <tab> description <tab> test set genes in GO category <\n>
	 */
	void makeFile(){
		
		// date and time for filename uniqueness.
		String dateString = DateFormat.getDateInstance().format(new Date());
		String timeString = DateFormat.getTimeInstance().format(new Date());
		
		
		// actual writing of the file.		

		
		try {
			File results = new File(dirName,fileName) ;
			BufferedWriter output = new BufferedWriter (new FileWriter(results));
			System.out.println("BiNGO results file : " + results.getPath()) ;
			//try{
			output.write("File created with BiNGO (c) on " + dateString + " at " + timeString + "\n");
			output.write("\n");
			output.write(ontology.toString());
			output.write("\n");	
			output.write("Selected ontology file : " + ontologyFile + "\n");
			output.write("Selected annotation file : " + annotationFile + "\n");
			output.write(overUnderString + "\n") ;
			output.write("Selected statistical test : " + testString + "\n");
			output.write("Selected correction : " + correctionString + "\n");
			output.write("Selected significance level : " + alphaString + "\n");			
			output.write("\n");
			output.write("Testing option : " + clusterVsString + "\n");
			output.write("\n");
			output.write("The selected cluster :\n");
			int j = 1 ;
			
			for (int i = 0; i < selectedCanonicalNameVector.size(); i++){
				int [] nodeClassifications = annotation.getClassifications(selectedCanonicalNameVector.get(i).toString()) ;
				for (int k = 0 ; k < nodeClassifications.length ; k++){
					String cat = new Integer(nodeClassifications[k]).toString() ;
					if(!annotatedGenes.containsKey(cat)){
						HashSet catset = new HashSet() ; 
						annotatedGenes.put(cat, catset) ;
					}
					((HashSet) annotatedGenes.get(cat)).add(selectedCanonicalNameVector.get(i).toString()) ;
				}	
				output.write(selectedCanonicalNameVector.get(i).toString()+"\t");
				if(j == 255){
					output.write("\n") ;
					j = 0 ;
				}	
				j++ ;
			}
			output.write("\n");
			output.write("\n");
			output.write("Number of genes selected : " + bigX + "\n") ;
			output.write("Total number of genes in annotation : " + bigN + "\n") ;
			output.write("\n");
			if (testString.equals(NONE)){
				output.write("GO-ID" + "\t" +"# selected" +"\t" + "# total" + "\t" + "Description" + "\t" + "Genes in test set" + "\n");
			}	
			else if (correctionString.equals(NONE)){
				output.write("GO-ID" + "\t" +  "p-value" + "\t" +"# selected" +"\t" + "# total" + "\t" + "Description" + "\t" + "Genes in test set" + "\n");
			}
			else {
				output.write("GO-ID" + "\t" +  "p-value" + "\t" + "corr p-value" +"# selected" +"\t" + "# total" + "\t" + "Description" + "\t" + "Genes in test set" + "\n");
			}
/*}			
catch (Exception e){
	System.out.println("Error: " + e);
}*/

			//orden GO labels by increasing corrected p-value or increasing smallX
			
			HashSet keySet ;
			if (!testString.equals(NONE)){
				keySet = new HashSet(testMap.keySet());
			}
			else{
				keySet = new HashSet(mapSmallX.keySet());
			}	
			Iterator it = keySet.iterator();
			String [] keyLabels = new String [keySet.size()];
			for(int i = 0; it.hasNext(); i++){
				keyLabels[i] = it.next().toString();
			}
			String[] ordenedKeySet ;
			if (!testString.equals(NONE)){
				ordenedKeySet = ordenKeysByPvalues(keyLabels) ;
			}
			else{
			 	ordenedKeySet = ordenKeysBySmallX(keyLabels) ;
			}	
			boolean ok = true ;
			
			for (int i = 0; (i < ordenedKeySet.length)&&(ok == true) ; i++){
				
				String termID = ordenedKeySet[i] ;
				String pvalue = "";
				String correctedPvalue = "";
				String smallX;
				String smallN;
				String description;
				// pvalue
				if (!testString.equals(NONE)){
					try{
						pvalue = SignificantFigures.sci_format(testMap.get(new Integer(termID)).toString(), 5 );
					}
					catch (Exception e){
						pvalue = "N/A";
					}
				}	
				else{pvalue = "N/A";}
				// corrected pvalue
				if (!correctionString.equals(NONE)){
					try{
					  correctedPvalue = SignificantFigures.sci_format(correctionMap.get(termID).toString(), 5 );
					}
					catch (Exception e){
						correctedPvalue = "N/A";
					}
				}
				else{correctedPvalue = "N/A";}
				// x
				try{
					smallX = mapSmallX.get(new Integer(termID)).toString();
				}
				catch (Exception e){
					smallX = "N/A";
				}
				// n
				try{
					smallN = mapSmallN.get(new Integer(termID)).toString();
				}
				catch (Exception e){
					smallN = "N/A";
				}
				// name
				try{			
					description = ontology.getTerm(Integer.parseInt(termID)).getName();
				}
				catch (Exception e){
					description = "?";
				}
				
				if(testString.equals(NONE)){
					output.write(termID + "\t" + smallX + "\t" + smallN + "\t" + description + "\t");
					if(annotatedGenes.containsKey(termID)){	
						Iterator k = ((HashSet) annotatedGenes.get(termID)).iterator() ;
						while(k.hasNext()){
							output.write(k.next().toString()) ;
							if(k.hasNext()){output.write('|') ;}
						}	
					}	
					output.write("\n") ;	
				}	
				else if (correctionString.equals(NONE)){
					if(catString.equals(CATEGORY_BEFORE_CORRECTION)){
						if((new BigDecimal(testMap.get(new Integer(ordenedKeySet[i])).toString())).compareTo(new BigDecimal(alphaString)) < 0){
							output.write(termID + "\t" + pvalue + "\t" + smallX + "\t" + smallN + "\t" + description + "\t");
							if(annotatedGenes.containsKey(termID)){
								Iterator k = ((HashSet) annotatedGenes.get(termID)).iterator() ;
								while(k.hasNext()){
									output.write(k.next().toString()) ;
									if(k.hasNext()){output.write('|') ;}
								}	
							}
							output.write("\n") ;	
						}	
						else{ok = false ;}
					}
					else{   /////// PASSAGE INUTILE VU QU'IL PEUT PAS Y AVOIR A LA FOIS catstring=aftercorrection et correctionstring=none !!!!!
						output.write(termID + "\t" + pvalue + "\t" + smallX + "\t" + smallN + "\t" + description + "\t");
						if(annotatedGenes.containsKey(termID)){
							Iterator k = ((HashSet) annotatedGenes.get(termID)).iterator() ;
							while(k.hasNext()){
								output.write(k.next().toString()) ;
								if(k.hasNext()){output.write('|') ;}
							}	
						}
						output.write("\n") ;	
					}	
				}
				else {
					if(catString.equals(CATEGORY_CORRECTION)){
						if((new BigDecimal(correctionMap.get(ordenedKeySet[i]).toString())).compareTo(new BigDecimal(alphaString)) < 0){
							output.write(termID + "\t" + pvalue + "\t" + correctedPvalue + "\t" + smallX + "\t" + smallN + "\t" + description + "\t");
							if(annotatedGenes.containsKey(termID)){
								Iterator k = ((HashSet) annotatedGenes.get(termID)).iterator() ;
								while(k.hasNext()){
									output.write(k.next().toString()) ;
									if(k.hasNext()){output.write('|') ;}
								}
							}	
							output.write("\n") ;
						}	
						else{ok = false ;}
					}
					else if(catString.equals(CATEGORY_BEFORE_CORRECTION)){
						if((new BigDecimal(testMap.get(new Integer(ordenedKeySet[i])).toString())).compareTo(new BigDecimal(alphaString)) < 0){
							output.write(termID + "\t" + pvalue + "\t" + correctedPvalue + "\t" + smallX + "\t" + smallN + "\t" + description + "\t");
							if(annotatedGenes.containsKey(termID)){
								Iterator k = ((HashSet) annotatedGenes.get(termID)).iterator() ;
								while(k.hasNext()){
									output.write(k.next().toString()) ;
									if(k.hasNext()){output.write('|') ;}
								}
							}	
							output.write("\n") ;
						}	
						else{ok = false ;}
					}
					else{
						output.write(termID + "\t" + pvalue + "\t" + correctedPvalue + "\t" + smallX + "\t" + smallN + "\t" + description + "\t");
						if(annotatedGenes.containsKey(termID)){
							Iterator k = ((HashSet) annotatedGenes.get(termID)).iterator() ;
							while(k.hasNext()){
								output.write(k.next().toString()) ;
								if(k.hasNext()){output.write('|') ;}
							}
						}	
						output.write("\n") ;
					}		
				}
			}
				
			output.close();
		}
		
		catch (Exception e){
			System.out.println("Error: " + e);
			
		}
	
	}
	
	public String [] ordenKeysByPvalues(String[] labels){

		for (int i = 1; i < labels.length; i++) {
			int j = i ; 
			// get the first unsorted value ...
			String insert_label = labels[i] ;
			BigDecimal val = new BigDecimal(testMap.get(new Integer(labels[i])).toString());	
			// ... and insert it among the sorted
			while ((j > 0) && (val.compareTo(new BigDecimal(testMap.get(new Integer(labels[j-1])).toString())) < 0)) {	
				labels[j] = labels[j-1];
				j-- ;
			}
			// reinsert value
			labels[j] = insert_label;
		}
		return labels ;
    }
	
	public String [] ordenKeysBySmallX(String[] labels){

		for (int i = 1; i < labels.length; i++) {
			int j = i ; 
			// get the first unsorted value ...
			String insert_label = labels[i] ;
			BigDecimal val = new BigDecimal(mapSmallX.get(new Integer(labels[i])).toString());	
			// ... and insert it among the sorted
			while ((j > 0) && (val.compareTo(new BigDecimal(mapSmallX.get(new Integer(labels[j-1])).toString())) > 0)) {	
				labels[j] = labels[j-1];
				j-- ;
			}
			// reinsert value
			labels[j] = insert_label;
		}
		return labels ;
    }


}


