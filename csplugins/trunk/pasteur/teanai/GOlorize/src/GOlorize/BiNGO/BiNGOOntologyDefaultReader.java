package GOlorize.BiNGO ;

// Adapted from : OntologyFlatFileReader.java in Cytoscape
//------------------------------------------------------------------------------
// $Revision: 1.5 $  $Date: 2003/07/16 00:16:43 $
//------------------------------------------------------------------------------
// Copyright (c) 2002 Institute for Systems Biology and the Whitehead Institute

/* * Copyright (c) 2005 Flanders Interuniversitary Institute for Biotechnology (VIB)
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
 **/


/* * Modified Date: Mar.25.2005
 * * by : Steven Maere
 * * Copyright (c) 2005 Flanders Interuniversitary Institute for Biotechnology (VIB)
 * * Changes : 1) correction for GO labels with multiple GO identifiers, these should be included only once in the ontology.
 * * synonymous identifiers are remapped on a unique identifier for each GO label through a user-defined synonyms HashMap. 
 * * 2) ensure that root node (Gene_ontology 003673 in the case of GO) gets read in as well
 * * 3) correction to avoid incorrect category names if they contain [ in the name
 * *
 * */


import java.io.*; 
import java.util.*;

import cytoscape.data.readers.*;
import cytoscape.data.annotation.OntologyTerm;
import cytoscape.data.annotation.Ontology;
//-------------------------------------------------------------------------
public class BiNGOOntologyDefaultReader { 
  Ontology ontology;
  String curator = "unknown";
  String ontologyType = "unknown";
  String filename;
  String fullText;
  String [] lines;
  HashMap synonymHash ;	
  HashMap goMap ;
//-------------------------------------------------------------------------
public BiNGOOntologyDefaultReader (File file, HashMap synonymHash) throws IllegalArgumentException, IOException, Exception
{
   this (file.getPath (), synonymHash);	
}
//-------------------------------------------------------------------------
public BiNGOOntologyDefaultReader (String filename, HashMap synonymHash) throws IllegalArgumentException, IOException, Exception
{
  this.filename = filename;
  this.synonymHash = synonymHash ;	
  try {
    if (filename.trim().startsWith ("jar://")) {
      TextJarReader reader = new TextJarReader (filename);
      reader.read ();
      fullText = reader.getText ();
      }
    else if (filename.trim().startsWith ("http://")) {
      TextHttpReader reader = new TextHttpReader (filename);      
      reader.read ();
      fullText = reader.getText ();
      }
    else {
      TextFileReader reader = new TextFileReader (filename);
      reader.read ();
      fullText = reader.getText ();
      }
    }
  catch (IOException e0) {
    System.err.println ("-- Exception while reading ontology default file " + filename);
    System.err.println (e0.getMessage ());
	throw e0 ;
    //return;
  }
  this.goMap = new HashMap() ;
  lines = fullText.split ("\n");
  parseHeader ();	
  parse ();

} // ctor
//-------------------------------------------------------------------------
private int stringToInt (String s)
{
  try {
    return Integer.parseInt (s);
    }
  catch (NumberFormatException nfe) {
    return -1;
    }
}
//-------------------------------------------------------------------------
private void parseHeader () throws Exception
{
  String firstLine = lines [0].trim ();
  String [] tokens = firstLine.split ("\\)");

  String errorMsg = "error in OntologyDefaultReader.parseHeader ().\n";
  errorMsg += "First line of " + filename + " must have form:\n";
  errorMsg += "   (curator=GO) (type=all) \n";
  errorMsg += "instead found:\n";
  errorMsg += "   " + firstLine + "\n";

  if (tokens.length !=2) throw new IllegalArgumentException (errorMsg);

  String [] curatorRaw = tokens [0].split ("=");
  if (curatorRaw.length != 2) throw new IllegalArgumentException (errorMsg);
  curator = curatorRaw [1].trim();

  String [] typeRaw = tokens [1].split ("=");
  if (typeRaw.length != 2) throw new IllegalArgumentException (errorMsg);
  ontologyType = typeRaw [1].trim();

} // parseHeader
//-------------------------------------------------------------------------

private void parse () throws Exception
{
  ontology = new Ontology (curator, ontologyType);
  for (int i=1; i < lines.length; i ++) {
    String line = lines [i];
    int equals = line.indexOf ("=");
    String idString = line.substring (0,equals).trim ();
    int id = stringToInt (idString);
    String value = line.substring (equals + 1);
	
	//adjusted : to avoid incorrect names if they contain [ in the name...
    int firstLeftBracket = value.indexOf ("[isa: ");
	if (firstLeftBracket < 0){
		firstLeftBracket = value.indexOf ("[partof: ");
	}	
    
	if (firstLeftBracket < 0){
		String name = value.substring (0).trim();
		Integer id2 = new Integer (id) ;
    	OntologyTerm term = new OntologyTerm (name, ((Integer) synonymHash.get(id2)).intValue());
		ontology.add (term);
		continue;
	}
    String name = value.substring (0, firstLeftBracket).trim();
	Integer id2 = new Integer (id) ;
    OntologyTerm term = new OntologyTerm (name, ((Integer) synonymHash.get(id2)).intValue());

   	int isaStart = value.indexOf ("[isa: ");
   	if (isaStart >= 0) {
    	int isaEnd = value.indexOf ("]", isaStart);
    	String rawIsa = value.substring (isaStart + 6, isaEnd).trim();
    	String [] allIsas = rawIsa.split (" ");
	
    	for (int j=0; j < allIsas.length; j++){
			Integer id3 = new Integer(stringToInt(allIsas [j])) ;			
     		term.addParent (((Integer) synonymHash.get(id3)).intValue());
    	}
    } // found "[isa: "

    int partofStart = value.indexOf ("[partof: ");
   	if (partofStart >= 0) {
      	int partofEnd = value.indexOf ("]", partofStart);
      	String rawPartof = value.substring (partofStart + 9, partofEnd).trim();
      	String [] allPartofs = rawPartof.split (" ");
      	for (int j=0; j < allPartofs.length; j++){
        	Integer id3 = new Integer(stringToInt(allPartofs [j])) ;
			term.addContainer (((Integer) synonymHash.get(id3)).intValue());
		}	
    } // if
    ontology.add (term);	
  } // for i

} // read
//-------------------------------------------------------------------------
public Ontology getOntology ()
{
  return ontology;
}

public HashMap getSynonymHash ()
{
  return synonymHash;
}

//-------------------------------------------------------------------------
} // class BiNGOOntologyDefaultReader
