package cytoscape.cytopanel.util;

import javax.swing.*;
import java.awt.*;

public class CytoPanelUtil {

    /**
     * Inset between owner frame and floating window.
     */
    private static final int INSET = 5;

    /**
     * Gets Location of External Frame, based on current UI Dimensions.
     *
     * @param screenDimension  Current Screen Dimensions.
     * @param containerBounds  Container Bounds Rectangle.
     * @param frameDimension   Current Frame Dimensions.
     * @param compassDirection Compass Direction, SwingConstants.
     * @return Point Object.
     */
    public static Point getLocationOfExternalFrame(Dimension screenDimension,
            Rectangle containerBounds, Dimension frameDimension,
            int compassDirection, boolean outputDiagnostics) {
        if (outputDiagnostics) {
            outputDiagnostics(screenDimension, containerBounds,
                    frameDimension);
        }

        //  Get Location and Dimension of Container
        Point containerLocation = containerBounds.getLocation();
        int containerWidth = (int) containerBounds.getWidth();
        int containerHeight = (int) containerBounds.getHeight();

        //  Get Screen Dimensions
        int screenWidth = (int) screenDimension.getWidth();
        int screenHeight = (int) screenDimension.getHeight();

        //  Initialize Point
        Point p = new Point(containerLocation.x, containerLocation.y);

        //  Set Point Based on Compass Direction
        if (compassDirection == SwingConstants.WEST) {
            p.x = containerLocation.x - INSET -
                    (int) frameDimension.getWidth();
        } else if (compassDirection == SwingConstants.EAST) {
            p.x = containerLocation.x + INSET + (int) containerWidth;
        } else if (compassDirection == SwingConstants.SOUTH_WEST) {
            p.y = containerLocation.y + INSET + (int) containerHeight;
        } else if (compassDirection == SwingConstants.SOUTH_EAST) {
            p.x = containerLocation.x + containerWidth -
                    (int) frameDimension.getWidth();
            p.y = containerLocation.y + INSET + (int) containerHeight;
        }

        //  Remove any negative coordinates
        p.x = Math.max(0, p.x);
        p.y = Math.max(0, p.y);

        if (p.x + frameDimension.getWidth() > screenWidth) {
            //  Adjust for right most case
            p.x = screenWidth - (int) frameDimension.getWidth();
        }
        if (p.y + frameDimension.getHeight() > screenHeight) {
            //  Adjust for bottom-most case
            p.y = screenHeight - (int) frameDimension.getHeight();
        }
        return p;
    }

    /**
     * Outputs Diagnostics Related to Screen/Frame Dimensions.
     */
    private static void outputDiagnostics(Dimension screenDimension,
            Rectangle containerBounds, Dimension preferredSizeOfPanel) {
        System.err.println("Screen Dimension:  " + screenDimension);
        System.err.println("Container Bounds:  " + containerBounds.toString());
        System.err.println("Preferred Size of Panel:  "
                + preferredSizeOfPanel.toString());
    }
}