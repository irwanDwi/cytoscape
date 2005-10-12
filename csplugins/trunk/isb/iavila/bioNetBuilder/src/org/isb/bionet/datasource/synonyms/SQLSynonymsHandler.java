
package org.isb.bionet.datasource.synonyms;

import java.util.*;
import java.sql.*;
import org.isb.bionet.datasource.*;
import org.isb.xmlrpc.handler.db.*;

public class SQLSynonymsHandler extends SQLDBHandler implements SynonymsSource {
    
    /**
     * Empty constructor
     */
    public SQLSynonymsHandler() {
        // TODO: Remove, this should be read from somewhere!!!
        this("jdbc:mysql://biounder.kaist.ac.kr/synonym3?user=bioinfo&password=qkdldhWkd");
    }

    /**
     * @param mysql_url
     *            the URL of the mySQL data base
     */
    public SQLSynonymsHandler(String mysql_url) {
        super(mysql_url, SQLDBHandler.MYSQL_JDBC_DRIVER);
    }
    
    //------------- SynonymsSource methods ---------//
    
    /**
     * 
     * @param source_id_type one of the supported id types
     * @param source_ids a Vector of ids of type source_id_type
     * @param target_id_type the type of id that the input ids should be translated to
     * @return a Hashtable from the input source_ids to the resulting translation
     * NOTE: source_id_type cannot be COMMON_NAME.
     */
    // This is not very elegant. For every new ID type, need to add all pairs. Maybe we only care from
    // gi to all others, and back? This would mean Synonyms would need to have a "canonical" id type.
    // Not nice. What is the best way?????
    public Hashtable getSynonyms (String source_id_type, Vector source_ids, String target_id_type){
        
        if(source_ids.size() == 0) return new Hashtable();
        
        if(source_id_type.equals(PROLINKS_ID) && target_id_type.equals(GI_ID)){
            return prolinksToGi(source_ids);
        }
        
        if(source_id_type.equals(GI_ID) && target_id_type.equals(PROLINKS_ID)){
            return giToProlinks(source_ids);
        }
        
        if(source_id_type.equals(KEGG_ID) && target_id_type.equals(GI_ID)){
            return keggToGi(source_ids);
        }
        
        if(source_id_type.equals(GI_ID) && target_id_type.equals(KEGG_ID)){
            return giToKegg(source_ids);
        }
        
        if(source_id_type.equals(KEGG_ID) && target_id_type.equals(PROLINKS_ID)){
            return keggToProlinks(source_ids);
        }
        
        if(source_id_type.equals(PROLINKS_ID) && target_id_type.equals(KEGG_ID)){
            return prolinksToKegg(source_ids);
        }
        
        // Common name to id? Not reliable. User needs to keep track of this map.
        if(source_id_type.equals(GI_ID) && target_id_type.equals(COMMON_NAME)){
            return giToCommonName(source_ids);
        }
        
         if(source_id_type.equals(PROLINKS_ID) && target_id_type.equals(COMMON_NAME)){
             return prolinksToCommonName(source_ids);
         }
         
         if(source_id_type.equals(KEGG_ID) && target_id_type.equals(COMMON_NAME)){
             return keggToCommonName(source_ids);
         }
         
        return new Hashtable();
    
   }
    
    /**
     * 
     * @param id an ID
     * @return one of:<br>
     * PROLINKS_ID, KEGG_ID, GI_ID, or ID_NOT_FOUND
     */
    public String getIdType (String id){
        String [] tokens = id.split(":");
        if(tokens.length == 0) return ID_NOT_FOUND;
        if(tokens[0].equals(PROLINKS_ID)) return PROLINKS_ID;
        if(tokens[0].equals(KEGG_ID)) return KEGG_ID;
        if(tokens[0].equals(GI_ID)) return GI_ID;
        if(tokens[0].equals(COMMON_NAME)) return COMMON_NAME;
        return ID_NOT_FOUND;
    }
    //--------- Helper protected methods -----------//
    protected Hashtable prolinksToGi (Vector prolinks_ids){
        
        Iterator it = prolinks_ids.iterator();
        if(!it.hasNext())
            return new Hashtable();
        
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(PROLINKS_ID + ":");
            if(index >= 0){
                id = id.substring(index+PROLINKS_ID.length() + 1);
                if(or.length() > 0){
                    or += " OR prolinksid = " + id;
                }else{
                    or = " prolinksid = " + id;
                }//else
            }//if
        }//while
        
        if(or.length() == 0) return new Hashtable();
        String sql = "SELECT prolinksid, gi FROM key_prolinks WHERE" + or;
        
        ResultSet rs = query(sql);
        try{
            Hashtable pToG = new Hashtable();
            while(rs.next()){
                pToG.put(PROLINKS_ID + ":" + rs.getString(1), GI_ID + ":" + rs.getString(2));
            }
            return pToG;
        }catch(SQLException e){
            e.printStackTrace();
            return new Hashtable();
        }
           
    }
    
    protected Hashtable giToProlinks (Vector gi_ids){
        
        Iterator it = gi_ids.iterator();
        if(!it.hasNext())
            return new Hashtable();
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(GI_ID + ":");
            if(index >= 0){
                id = id.substring(index + GI_ID.length() + 1);
                if(or.length() > 0)
                    or += " OR gi = " + id;
                else
                    or = " gi = " + id;
            }
         
        }
        
        if(or.length() == 0) return new Hashtable();
        
        String sql = "SELECT gi,prolinksid FROM key_prolinks WHERE" + or;
        
        ResultSet rs = query(sql);
        try{
            Hashtable gToP = new Hashtable();
            while(rs.next()){
                gToP.put(GI_ID + ":" + rs.getString(1), PROLINKS_ID + ":" + rs.getString(2));
            }
            return gToP;
        }catch(SQLException e){
            e.printStackTrace();
            return new Hashtable();
        }
           
    }
   
    protected Hashtable keggToGi (Vector kegg_ids){
        
        Iterator it = kegg_ids.iterator();
        
        if(!it.hasNext()) return new Hashtable();
       
        String or = ""; 
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(KEGG_ID + ":");
            if(index >= 0){
                id = id.substring(index + KEGG_ID.length() + 1);
                if(or.length() > 0)
                    or += " OR kegg_id = \"" + id + "\"";
                else
                    or = " kegg_id = \"" + id + "\"";
            }
            
        }
        
        if(or.length() == 0) return new Hashtable();
        
        String sql = "SELECT kegg_id, gi FROM gi_kegg WHERE " + or;
        ResultSet rs = query(sql);
        
        try{
            Hashtable kToG = new Hashtable();
            while(rs.next()){
                kToG.put(KEGG_ID + ":" + rs.getString(1), GI_ID + ":" + rs.getString(2));
            }
            return kToG;
        }catch(SQLException ex){
            ex.printStackTrace();
            return new Hashtable();
        }
        
    }
    
    protected Hashtable giToKegg (Vector gi_ids){
        
        Iterator it = gi_ids.iterator();
        
        if(!it.hasNext()) return new Hashtable();
        
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(GI_ID + ":");
            if(index >= 0){
                id = id.substring(index + GI_ID.length() + 1);
                if(or.length() > 0)
                    or += " OR gi = " + id;
                else
                    or = " gi = " + id;
            }
            
        }
        if(or.length() == 0) return new Hashtable();
        String sql = "SELECT gi, kegg_id FROM gi_kegg WHERE " + or;
        ResultSet rs = query(sql);
        
        try{
            Hashtable gToK = new Hashtable();
            while(rs.next()){
                gToK.put(GI_ID + ":" + rs.getString(1), KEGG_ID  + ":" + rs.getString(2));
            }
            return gToK;
        }catch(SQLException ex){
            ex.printStackTrace();
            return new Hashtable();
        }
        
    }
    
    protected Hashtable prolinksToKegg (Vector prolinks_ids){
        Iterator it = prolinks_ids.iterator();
        if(!it.hasNext()) return new Hashtable();
       
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(PROLINKS_ID + ":");
            if(index >= 0){
                id = id.substring(index + PROLINKS_ID.length() + 1);
                if(or.length() > 0)
                    or += " OR p.prolinksid = " + id;
                else
                    or = " p.prolinksid = " + id;
            }
        }
        if(or.length() == 0) return new Hashtable();
        String sql = "SELECT p.prolinksid, k.kegg_id FROM xref_kegg AS k, key_prolinks AS p WHERE k.oid = p.oid AND " + or;
        ResultSet rs = query(sql);
        try{
            Hashtable pToK = new Hashtable();
            while(rs.next()){
                pToK.put(SynonymsSource.PROLINKS_ID + ":" + rs.getString(1),
                         SynonymsSource.KEGG_ID + ":" + rs.getString(2));
            }
            return pToK;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return new Hashtable();
    }
    
    protected Hashtable keggToProlinks (Vector kegg_ids){
        Iterator it = kegg_ids.iterator();
        if(!it.hasNext()) return new Hashtable();
        
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(KEGG_ID + ":");
            if(index >= 0){
                id = id.substring(index + KEGG_ID.length() + 1);
                if(or.length() > 0)
                    or += " OR k.kegg_id = \"" + id + "\"";
                else
                    or = " k.kegg_id = \"" + id + "\"";
            }
        }
        if(or.length() == 0) return new Hashtable();
        String sql = "SELECT k.kegg_id, p.prolinksid FROM xref_kegg AS k, key_prolinks AS p WHERE k.oid = p.oid AND " + or;
        ResultSet rs = query(sql);
        try{
            Hashtable kToP = new Hashtable();
            while(rs.next()){
                kToP.put(SynonymsSource.KEGG_ID + ":" + rs.getString(1),
                         SynonymsSource.PROLINKS_ID + ":" + rs.getString(2));
            }
            return kToP;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return new Hashtable();
    }
    
    protected Hashtable giToCommonName (Vector gi_ids){
        Iterator it = gi_ids.iterator();
        if(!it.hasNext()) return new Hashtable();
        
        // transfer gi_ids to oids, and then oids to genename
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(GI_ID + ":");
            if(index >= 0){
                id = id.substring(index + GI_ID.length() + 1);
                if(or.length() > 0)
                    or += " OR gi = " + id;
                else
                    or = " gi = " + id;
            }
        }//while
        
        if(or.length() == 0) return new Hashtable();
        String sql = "SELECT gi, oid FROM xref_gi WHERE " + or;
        ResultSet rs = query(sql);
        
        Hashtable oidToGi = new Hashtable();
        or = "";
        try{
            while(rs.next()){
                int gi = rs.getInt(1);
                int oid = rs.getInt(2);
                oidToGi.put(new Integer(oid), Integer.toString(gi));
                if(or.length() == 0){
                    or = " oid = " + oid;
                }else{
                    or += " OR oid = " + oid;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return new Hashtable();
        }
        
        if(or.length() == 0) return new Hashtable();
        
        sql = "SELECT oid, genename FROM gn_genename WHERE " + or;
        rs = query(sql);
        
        Hashtable giToCn = new Hashtable();
        try{
            while(rs.next()){
                int oid = rs.getInt(1);
                String cn = rs.getString(2);
                String gi = (String)oidToGi.get(new Integer(oid));
                giToCn.put(GI_ID+":"+gi,cn);
            }
        }catch(Exception ex){ex.printStackTrace(); return new Hashtable();}
        System.out.print("Num translated to common names = " + giToCn.size());
        return giToCn;
    } 
    
    
    protected Hashtable prolinksToCommonName (Vector prolinks_ids){
        Hashtable prToCN = new Hashtable();
        
        Iterator it = prolinks_ids.iterator();
        
        if(!it.hasNext()) return prToCN;
        
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(PROLINKS_ID + ":");
            if(index >= 0){
                id = id.substring(index + PROLINKS_ID.length() + 1);
                if(or.length() == 0){
                    or = " prolinksid = " + id;
                }else{
                    or += " OR prolinksid = " + id;
                }//else
            }//index >=0
        }//while
        
        if(or.length() == 0) return prToCN;
        
        String sql = "SELECT prolinksid, genename FROM key_prolinks WHERE " + or;
        ResultSet rs = query(sql);
        
        try{
            while(rs.next()){
                String prolinksid = rs.getString(1);
                String cn = rs.getString(2);
                prToCN.put(PROLINKS_ID + ":" + prolinksid, cn);
            }
        }catch(Exception ex){ex.printStackTrace(); return new Hashtable();}
        
        return prToCN;
    }
    
    protected Hashtable keggToCommonName (Vector kegg_ids){
        
        Hashtable kToCN = new Hashtable();
        
        Iterator it = kegg_ids.iterator();
        
        if(!it.hasNext()) return kToCN;
        
        String or = "";
        while(it.hasNext()){
            String id = (String)it.next();
            int index = id.indexOf(KEGG_ID + ":");
            if(index >= 0){
                id = id.substring(index + KEGG_ID.length() + 1);
                if(or.length() == 0){
                    or = " kegg_id = \"" + id + "\"";
                }else{
                    or += " OR kegg_id = \"" + id + "\"";
                }//else
            }//if index
        }//while
       
        if(or.length() == 0) return kToCN;
        
        String sql = "SELECT oid, kegg_id FROM xref_kegg WHERE " + or;
        ResultSet rs = query(sql);
        Hashtable oidToKegg = new Hashtable();
        or = "";
        try{
            while(rs.next()){
                int oid = rs.getInt(1);
                String kegg = rs.getString(2);
                oidToKegg.put(new Integer(oid), kegg);
                if(or.length() == 0){
                    or = " oid = " + oid;
                }else{
                    or += " OR oid = " + oid;
                }//else
            }//while
        }catch(Exception ex){ ex.printStackTrace(); return kToCN;}
        
        if(or.length() == 0) return kToCN;
        
        sql = "SELECT oid, genename FROM gn_genename WHERE " + or;
        rs = query(sql);
        try{
            while(rs.next()){
                int oid = rs.getInt(1);
                String cn = rs.getString(2);
                String keggid = (String)oidToKegg.get(new Integer(oid));
                kToCN.put(KEGG_ID + ":" + keggid,cn);
            }//while
        }catch(Exception ex){ ex.printStackTrace(); return new Hashtable();}
        
        return kToCN;
    }
    
  
    //------------- DataSource methods -------------//

    /**
     * @return the name of the data source, for example, "KEGG", "Prolinks", etc.
     */
    public String getDataSourceName (){
        return "Synonyms";
    }
    
    /**
     * @return the type of backend implementation (how requests to the data source
     * are implemented) one of WEB_SERVICE, LOCAL_DB, REMOTE_DB, MEMORY, MIXED
     */
    //TODO: Need to figure out if DB is running locally or remotely
    public String getBackendType (){
        return DataSource.REMOTE_DB; // for now
    }

    /**
     * @return an empty Vector
     */
    public Vector getSupportedSpecies (){
        return new Vector();
    }
    
    /**
     * 
     * @return
     */
    public Boolean supportsSpecies (String sp){
        // for now. Maybe move this method from DataSource?
        return Boolean.FALSE;
    }

    /**
     * @return a String denoting the version of the data source (could be a release date,
     * a version number, etc).
     */

    public String getVersion (){
        return ""; //for now
    }

    /**
     * @return false
     */
    public boolean requiresPassword (){
        return false;
    }

    /**
     * Runs tests on the data source
     * @return a vector of results
     */
    public Vector test (){
        return new Vector();
    }
    
}