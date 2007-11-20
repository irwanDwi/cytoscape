package org.cytoscape.application.widget.vizmap.mapping;


/**
 * Defines visual mapping types.
 * This replaces all hard-coded strings related to mappings.
 *
 * @author Keiichiro Ono
 * @version 0.5
 * @since Cytoscape 2.5
 */
public enum MappingType {DISCRETE("Discrete"), CONTINUOUS("Continuous"), 
    PASSTHROUGH("Passthrough");
    private String name;

    private MappingType(String name) {
        this.name = name;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName() {
        return name + " Mapping";
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getBaseKey() {
        return name + "Mapping";
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getPropertyKey() {
        return name + ".type";
    }

    /**
     * DOCUMENT ME!
     *
     * @param baseKey DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     * @deprecated Will be removed 5/2008
     */
}
