
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

package cytoscape.plugin;

import cytoscape.CytoscapeInit;

import java.util.ArrayList;
import java.util.List;


/**
 * @author skillcoy Object describes a plugin
 */
public class PluginInfo {
	/**
	 * Jar and Zip files currently supported
	 *
	 * @author skillcoy
	 *
	 */
	public enum FileType {
		JAR("jar"),
		ZIP("zip");

		private String typeText;

		private FileType(String type) {
			this.typeText = type;
		}

		public String toString() {
			return typeText;
		}
	}

	private FileType fileType;
	private String uniqueID;
	private String pluginClassName;
	private String pluginName;
	private List<AuthorInfo> authors;
	private String pluginDescription;
	private String pluginVersion;
	private String cytoscapeVersion;
	private String pluginUrl;
	private String projectUrl;
	private String pluginCategory;
	private List<String> pluginFiles;
	protected String enclosingJar;

	/**
	 * Initializes a PluginInfo object with the following defaults:
	 * setName("Unknown"); setDescription("No pluginDescription");
	 * setPluginVersion("0.1"); setCytoscapeVersion(
	 * cytoscape.cytoscapeVersion.version ); setCategory("Uncategorized");
	 * setProjectUrl(CytoscapeInit.getProperties().getProperty("defaultPluginUrl"));
	 */
	public PluginInfo() {
		init();
	}

	/**
	 * See PluginInfo()
	 *
	 * @param UniqueID
	 *            Additionally this sets the unique identifier that will be used
	 *            to find a new version of the plugin at the given project url.
	 */
	public PluginInfo(String UniqueID) {
		this.uniqueID = UniqueID;
		init();
	}

	/*
	 * Sets all the fields that are required to a default value in case it is
	 * not called
	 */
	private void init() {
		pluginFiles = new ArrayList<String>();
		authors = new ArrayList<AuthorInfo>();
		setName("Unknown");
		setDescription("No pluginDescription");
		setPluginVersion("0.1");
		setCytoscapeVersion(cytoscape.CytoscapeVersion.version);
		setCategory("Uncategorized");
		setProjectUrl(CytoscapeInit.getProperties().getProperty("defaultPluginUrl"));
	}

	// TODO These maybe should check to be sure nothing is being set to null
	/* SET */

	/**
	 * Sets name of plugin. This will be displayed to users.
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.pluginName = name;
	}

	/**
	 * Sets the plugin class name. Used for tracking plugins.
	 *
	 * @param className
	 */
	public void setPluginClassName(String className) {
		this.pluginClassName = className;
	}

	/**
	 * Sets a description of the plugin. This will be displayed to users.
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.pluginDescription = description;
	}

	/**
	 * Sets the version of the plugin. Defaults to 0.1
	 *
	 * @param version
	 */
	public void setPluginVersion(String version) {
		this.pluginVersion = version;
	}

	/**
	 * Sets the Cytoscape version this plugin is compatible with.
	 *
	 * @param version
	 */
	public void setCytoscapeVersion(String version) {
		this.cytoscapeVersion = version;
	}

	/**
	 * pluginUrl this plugin was downloaded from. It is presumed this can be
	 * used for update later.
	 *
	 * @param url
	 */
	public void setUrl(String url) {
		this.pluginUrl = url;
	}

	/**
	 * pluginUrl for the xml file describing all plugins from any given project
	 * (ex. http://cytoscape.org/plugins/plugin.xml)
	 *
	 * @param url
	 */
	public void setProjectUrl(String url) {
		this.projectUrl = url;
	}

	/**
	 * Jar or Zip are currently supported. Use PluginInfo.JAR or PluginInfo.ZIP.
	 *
	 * @param type
	 */
	public void setFiletype(FileType type) {
		this.fileType = type;
	}

	/**
	 * Sets a list of files (prefer full paths) installed with this plugin.
	 * Includes the jar file.
	 *
	 * @param list
	 */
	public void setFileList(List<String> list) {
		this.pluginFiles = list;
	}

	/**
	 * category for the plugin. Defaults to "Uncategorized"
	 *
	 * @param category
	 */
	public void setCategory(String category) {
		this.pluginCategory = category;
	}

	/**
	 * Adds a file to the list of installed files.
	 *
	 * @param fileName
	 */
	public void addFileName(String fileName) {
		this.pluginFiles.add(fileName);
	}

	/**
	 * Adds an author to the list of authors.
	 *
	 * @param authorName
	 * @param institution
	 */
	public void addAuthor(String authorName, String institution) {
		authors.add(new AuthorInfo(authorName, institution));
	}

	/* GET */

	/**
	 * @return The unique id for this object.
	 */
	public String getID() {
		return this.uniqueID;
	}

	/**
	 * @return FileType of file type for plugin. PluginInfo.JAR or
	 *         PluginInfo.ZIP
	 */
	public FileType getFileType() {
		return this.fileType;
	}

	/**
	 * @return pluginName of plugin
	 */
	public String getName() {
		return this.pluginName;
	}

	/**
	 * @return Java class name
	 */
	public String getPluginClassName() {
		return this.pluginClassName;
	}

	/**
	 * @return List of authors.
	 */
	public List<AuthorInfo> getAuthors() {
		return this.authors;
	}

	/**
	 * @return Plugin pluginDescription.
	 */
	public String getDescription() {
		return this.pluginDescription;
	}

	/**
	 * @return Plugin version.
	 */
	public String getPluginVersion() {
		return this.pluginVersion;
	}

	/**
	 * @return Compatible Cytoscape version
	 */
	public String getCytoscapeVersion() {
		return this.cytoscapeVersion;
	}

	/**
	 * @return Url to download plugin from
	 */
	public String getUrl() {
		return this.pluginUrl;
	}

	/**
	 * @return Url that returns the document of available plugins this plugin
	 *         came from
	 */
	public String getProjectUrl() {
		return this.projectUrl;
	}

	/**
	 * @return Plugin category.
	 */
	public String getCategory() {
		return this.pluginCategory;
	}

	/**
	 * @return List of files installed with this plugin (includes plugin jar
	 *         file).
	 */
	public List<String> getFileList() {
		return this.pluginFiles;
	}

	/**
	 * @return Returns String of plugin name and version
	 */
	public String toString() {
		return getName() + " " + getPluginVersion();
	}

	/**
	 * @return String of nice output for the information contained by the
	 *         PluginInfo object.
	 */
	public String prettyOutput() {
		String Text = getName() + "\n\n";
		Text += ("Version: " + getPluginVersion() + "\n\n");
		Text += ("category: " + getCategory() + "\n\n");

		// don't current have a release date, might be nice
		Text += (getDescription() + "\n\n");
		Text += "Released By: ";

		java.util.Iterator<AuthorInfo> aI = getAuthors().iterator();

		while (aI.hasNext()) {
			AuthorInfo Info = aI.next();

			if (Info.getAuthor() != null)
				Text += (Info.getAuthor() + ", ");

			Text += (Info.getInstitution() + "\n");
		}

		return Text;
	}

	/**
	 * Describes an author for a given plugin.
	 *
	 * @author skillcoy
	 */
	public class AuthorInfo {
		private String authorName;
		private String institutionName;

		public AuthorInfo(String Name, String Institution) {
			this.authorName = Name;
			this.institutionName = Institution;
		}

		public String getAuthor() {
			return this.authorName;
		}

		public String getInstitution() {
			return this.institutionName;
		}
	}
}
