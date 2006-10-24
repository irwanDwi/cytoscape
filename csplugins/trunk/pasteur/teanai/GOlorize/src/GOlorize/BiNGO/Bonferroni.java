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
 * * Description: Class implementing the Bonferroni multiple testing correction.         
 **/


import java.math.BigInteger;
import java.math.BigDecimal;

import java.io.*; 
import java.util.*;

/*******************************************************************
 * Bonferroni.java:         Steven Maere & Karel Heymans (c) March 2005
 * ----------------
 *
 * Class implementing the Bonferroni multiple testing correction. 
 *******************************************************************/


public class Bonferroni implements CalculateCorrectionTask{
    
    
   	/*--------------------------------------------------------------
      FIELDS.
      --------------------------------------------------------------*/

    /** the GO labels that have been tested (constructor input). */
    private static String [] goLabels;
    /** the raw p-values that were given as input for the constructor, order corresponds to String [] goLabels. */
    private static String [] pvalues;
    /** the goLabels ordened according to the ordened pvalues. */
    private static String [] ordenedGOLabels;   
    /** the raw p-values ordened in ascending order. */
    private static String [] ordenedPvalues;
	/** the adjusted p-values ordened in ascending order. */
	private static String [] adjustedPvalues;
    /** the significance level. */
    private static BigDecimal alpha;
    /** the number of tests. */
    private static int m;
    /** scale for the division in de method 'runBonferroni'. */
    private static final int RESULT_SCALE = 100;
	// Keep track of progress for monitoring:
    protected int currentProgress;
    protected int lengthOfTask;
    protected String statusMessage;
    protected boolean done;
    protected boolean canceled;
    
    
    
    
    
    /*--------------------------------------------------------------
      CONSTRUCTOR.
      --------------------------------------------------------------*/
    /**
     * Constructor.
     *
     * @param pvalues array of Strings with the p-values.
     * @param goLabels array of Strings with the goLabels.
     * @param alpha String with the alpha-level.
     */
    public Bonferroni (String [] pvalues, String [] goLabels, String alpha){
			this.pvalues = pvalues;
			this.goLabels = goLabels;
			this.alpha = new BigDecimal(alpha);
			this.m = pvalues.length;
			this.adjustedPvalues = new String[m] ; 
			this.currentProgress = 0;
        	this.lengthOfTask = pvalues.length;
        	this.done = false;
        	this.canceled = false;
			
    }
    
    
    
    
    
    /*--------------------------------------------------------------
      METHODS.
      --------------------------------------------------------------*/
      
	  /**
	   * method that calculates the bonferroni procedure
     * p< alpha/m
     * i* (istar) first i such that the inequality is correct. 
     * reject hypotheses for i=1...i*
		 * adjusted p-value = m*p	
     */
    public void calculate (){

			this.currentProgress = 0;
        	this.lengthOfTask = pvalues.length ;
        	this.done = false;
        	this.canceled = false;
			
    		// ordening the pvalues.
			this.ordenedPvalues = ordenArrayStrings(pvalues);
		
    	/*
			// implementation of the search for i* .
			boolean stop = false;
			int istar = 0 ;
			BigDecimal imalpha;
			for (int i = m; !stop && i > 0; i--){
		  	imalpha = alpha.divide(new BigDecimal(""+m), RESULT_SCALE, BigDecimal.ROUND_HALF_UP) ;
		    if (new BigDecimal(ordenedPvalues[i-1]).compareTo(imalpha) <= 0){
			  	stop = true; 
			  	istar = i ;
				}
			}
			*/
		
			// calculating adjusted p-values.
			BigDecimal min = new BigDecimal(""+1) ;
			BigDecimal mp;
			for(int i = 0; i < m; i++){
				mp = new BigDecimal(""+m).multiply(new BigDecimal(ordenedPvalues[i])) ;
				if(mp.compareTo(min) < 0)
					adjustedPvalues[i] = mp.toString() ;
		 	 	else
					adjustedPvalues[i] = min.toString();
				
				/*this.currentProgress++;
				double percentDone = (this.currentProgress * 100) / this.lengthOfTask;
            	this.statusMessage = "Completed " + percentDone + "%.";*/
			}	
			this.done = true;
        	this.currentProgress = this.lengthOfTask;							
		}
	
	
	

    /**
     * sort array of Strings using insertion sort algorithm (ascending order)
     * pre: 0 <= n <= data.length
     * post: values in data[0..n-1] in ascending order
     *
     * @param data array of Strings that need to be ordened (the Strings must be decimal values).
     * @return String[] with ordened array of Strings (ascending order).
     */
			
    public String [] ordenArrayStrings(String [] data){
    	
		String [] tempGOLabels = goLabels;
    	
		for (int i = 1; i < data.length; i++) {
			int j = i ; 
			this.currentProgress++;
			double percentDone = (this.currentProgress * 100) / this.lengthOfTask;
			this.statusMessage = "Completed " + percentDone + "%.";
			// get the first unsorted value ...
			BigDecimal val = new BigDecimal(data[i]);
			String valGOLabel = tempGOLabels[i];
			// ... and insert it among the sorted
			while ((j > 0) && (val.compareTo(new BigDecimal(data[j-1])) < 0)) {	
				data[j] = data[j-1];
				tempGOLabels[j] = tempGOLabels[j-1] ;
				j-- ;
			}
			// reinsert value
			data[j] = val.toString();
			tempGOLabels[j] = valGOLabel;
		}
		ordenedGOLabels = tempGOLabels;
		return data;
	}
	



	/*--------------------------------------------------------------
		GETTERS.
      --------------------------------------------------------------*/	
	
	/**
	 * getter for the ordened p-values.
	 *
	 * @return String[] with the ordened p-values.
	 */
	public String[] getOrdenedPvalues(){
		return ordenedPvalues ;
	}
	
	/**	
	 * getter for the adjusted p-values.
	 *
	 * @return String[] with the adjusted p-values.
	 */
	public String[] getAdjustedPvalues(){
		return adjustedPvalues ;
	}

	/**	
	 * getter for the ordened GOLabels.
	 *
	 * @return String[] with the ordened GOLabels.
	 */
	public String[] getOrdenedGOLabels(){
		return ordenedGOLabels;
	}

		/**
     * @return the current progress
     */
    public int getCurrentProgress() {
        return this.currentProgress;
    }

    /**
     * @return the total length of the task
     */
    public int getLengthOfTask() {
        return this.lengthOfTask;
    }//getLengthOfTask

    /**
     * @return a <code>String</code> describing the task being performed
     */
    public String getTaskDescription() {
        return "Calculating Bonferroni Corrections";
    }//getTaskDescription

    /**
     * @return a <code>String</code> status message describing what the task
     *         is currently doing (example: "Completed 23% of total.", "Initializing...", etc).
     */
    public String getCurrentStatusMessage() {
        return this.statusMessage;
    }//getCurrentStatusMessage

    /**
     * @return <code>true</code> if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.done;
    }//isDone

    /**
     * Stops the task if it is currently running.
     */
    public void stop() {
        this.canceled = true;
        this.statusMessage = null;
    }//stop

    /**
     * @return <code>true</code> if the task was canceled before it was done
     *         (for example, by calling <code>MonitorableSwingWorker.stop()</code>,
     *         <code>false</code> otherwise
     */
    // TODO: Not sure if needed
    public boolean wasCanceled() {
        return this.canceled;
    }//wasCanceled
	
	public void start(boolean return_when_done) {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                return new DoTask();
            }//construct
        };
        worker.start();
        if (return_when_done) {
            worker.get(); // maybe use finished() instead
        }
    }//start

    class DoTask {
        DoTask() {
            calculate();
        }
    }

    
}
