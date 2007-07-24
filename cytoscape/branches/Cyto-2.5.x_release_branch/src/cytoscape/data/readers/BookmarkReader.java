/*
 Copyright (c) 2006, 2007, The Cytoscape Consortium (www.cytoscape.org)

 The Cytoscape Consortium is:
 - Institute for Systems Biology
 - University of California San Diego
 - Memorial Sloan-Kettering Cancer Center
 - Institut Pasteur
 - Agilent Technologies

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
*/
package cytoscape.data.readers;

import cytoscape.Cytoscape;

import cytoscape.bookmarks.Bookmarks;
import cytoscape.util.URLUtil;

import java.io.IOException;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


/**
 *
 */
public class BookmarkReader {
	// Package name generated by JAXB based on XGMML schema file
	private static final String BOOKMARK_PACKAGE = "cytoscape.bookmarks";

	// Location of the default bookmark.
	private static final String BOOKMARK_RESOURCE_FILE = "/cytoscape/resources/bookmarks.xml";
	private Bookmarks bookmarks;

	/**
	 * Creates a new BookmarkReader object.
	 */
	public BookmarkReader() {
	}

	/**
	 * Read bookmark from resource.
	 *
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void readBookmarks() throws JAXBException, IOException {
		URL bookmarkSource = getClass().getResource(BOOKMARK_RESOURCE_FILE);
		readBookmarks(bookmarkSource);
	}

	/**
	 * Read bookmarks from the specified location.
	 *
	 * @param bookmarkUrl location of bookmarks.xml as URL
	 *
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void readBookmarks(URL bookmarkUrl) throws JAXBException, IOException {
		// Use JAXB-generated methods to create data structure
		//		final JAXBContext jaxbContext = JAXBContext.newInstance(
		//				BOOKMARK_PACKAGE, this.getClass().getClassLoader());
		//		
		final JAXBContext jaxbContext = JAXBContext.newInstance(BOOKMARK_PACKAGE,
		                                                        Cytoscape.class.getClassLoader());

		// Unmarshall the XGMML file
		final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// bookmarks = (Bookmarks) unmarshaller.unmarshal(bookmarkUrl.openStream());
        // Use URLUtil to get the InputStream since we might be using a proxy server 
		// and because pages may be cached:
		bookmarks = (Bookmarks) unmarshaller.unmarshal(URLUtil.getBasicInputStream (bookmarkUrl));
	}

	/**
	 * Get loaded bookmark.
	 *
	 * @return
	 */
	public Bookmarks getBookmarks() {
		return bookmarks;
	}
}
