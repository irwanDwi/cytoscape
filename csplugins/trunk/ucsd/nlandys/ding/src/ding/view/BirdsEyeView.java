package ding.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class BirdsEyeView extends Component
{

  private final double[] m_extents = new double[4];
  private final DGraphView m_view;
  private final ContentChangeListener m_cLis;
  private final ViewportChangeListener m_vLis;
  private Image m_img = null;
  private boolean m_contentChanged = false;
  private double m_myXCenter;
  private double m_myYCenter;
  private double m_myScaleFactor;
  private int m_viewWidth;
  private int m_viewHeight;
  private double m_viewXCenter;
  private double m_viewYCenter;
  private double m_viewScaleFactor;

  public BirdsEyeView(DGraphView view)
  {
    super();
    m_view = view;
    m_cLis = new InnerContentChangeListener();
    m_vLis = new InnerViewportChangeListener();
    m_view.addContentChangeListener(m_cLis);
    m_view.addViewportChangeListener(m_vLis);
    // I'd love to figure out how to remove these listeners when the
    // component is "destroyed".
    m_viewWidth = m_view.getComponent().getWidth();
    m_viewHeight = m_view.getComponent().getHeight();
    final Point2D pt = m_view.getCenter();
    m_viewXCenter = pt.getX();
    m_viewYCenter = pt.getY();
    m_viewScaleFactor = m_view.getZoom();
  }

  public void reshape(int x, int y, int width, int height)
  {
    super.reshape(x, y, width, height);
    if (width > 0 && height > 0) {
      m_img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); }
    m_contentChanged = true;
  }

  public void update(Graphics g)
  {
    if (m_img == null) { return; }
    if (m_contentChanged) {
      if (m_view.getExtents(m_extents)) {
        m_myXCenter = (m_extents[0] + m_extents[2]) / 2.0d;
        m_myYCenter = (m_extents[1] + m_extents[3]) / 2.0d;
        m_myScaleFactor = 0.9d * Math.min
          (((double) getWidth()) / (m_extents[2] - m_extents[0]),
           ((double) getHeight()) / (m_extents[3] - m_extents[1])); }
      else {
        m_myXCenter = 0.0d; m_myYCenter = 0.0d; m_myScaleFactor = 1.0d; }
      m_view.drawSnapshot(m_img, m_view.getGraphLOD(),
                          m_view.getBackgroundPaint(),
                          m_myXCenter, m_myYCenter, m_myScaleFactor);
      m_contentChanged = false; }
    g.drawImage(m_img, 0, 0, null);
    final double rectWidth =
      m_myScaleFactor * (((double) m_viewWidth) / m_viewScaleFactor);
    final double rectHeight =
      m_myScaleFactor * (((double) m_viewHeight) / m_viewScaleFactor);
    final double rectXCenter =
      (((double) getWidth()) / 2.0d) +
      (m_myScaleFactor * (m_viewXCenter - m_myXCenter));
    final double rectYCenter =
      (((double) getHeight()) / 2.0d) -
      (m_myScaleFactor * (m_viewYCenter - m_myYCenter));
    g.setColor(Color.blue);
    g.drawRect((int) (rectXCenter - (rectWidth / 2)),
               (int) (rectYCenter - (rectHeight / 2)),
               (int) rectWidth, (int) rectHeight);
  }

  public void paint(Graphics g)
  {
    update(g);
  }

  private final class InnerContentChangeListener
    implements ContentChangeListener
  {

    public void contentChanged()
    {
      m_contentChanged = true;
      repaint();
    }

  }

  private final class InnerViewportChangeListener
    implements ViewportChangeListener
  {

    public void viewportChanged(int w, int h,
                                double newXCenter, double newYCenter,
                                double newScaleFactor)
    {
      m_viewWidth = w;
      m_viewHeight = h;
      m_viewXCenter = newXCenter;
      m_viewYCenter = newYCenter;
      m_viewScaleFactor = newScaleFactor;
      repaint();
    }

  }

}
