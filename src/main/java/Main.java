import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolViewer;
import org.jmol.util.Logger;
import org.openscience.jmol.app.jmolpanel.AppConsole;

public class Main {

    /*
     * Demonstrates a simple way to include an optional console along with the applet.
     *
     */
    public static void main(String[] argv) {
        JFrame frame = new JFrame("Визуализатор");
        frame.addWindowListener(new ApplicationCloser());
        frame.setSize(410, 700);
        Container contentPane = frame.getContentPane();
        JmolPanel jmolPanel = new JmolPanel();
        jmolPanel.setPreferredSize(new Dimension(400, 400));

        // main panel -- Jmol panel on top

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(jmolPanel);

        // main panel -- console panel on bottom

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setPreferredSize(new Dimension(400, 300));
        AppConsole console = new AppConsole(jmolPanel.viewer, panel2,
                "History State Clear");

        // You can use a different JmolStatusListener or JmolCallbackListener interface
        // if you want to, but AppConsole itself should take care of any console-related callbacks
        jmolPanel.viewer.setJmolCallbackListener(console);

        panel.add("South", panel2);

        contentPane.add(panel);
        frame.setVisible(true);

        // sample start-up script

//        String strError = jmolPanel.viewer.openFile("http://www.rcsb.org/pdb/download/downloadFile.do?fileFormat=pdb&compression=NO&structureId=3GMU");
        String strError = jmolPanel.viewer.openFile("C:\\Users\\Evgeni Developer\\Desktop\\ЧМИ\\1100118.cif");
        if (strError == null){
            /*HERE IS WHERE THE ACTUAL JMOL COMMANDS TAKE PLACE*/
            jmolPanel.viewer.evalString(strScript);
            /*HERE IS THE ACTUAL MEASURE COMMAND WHOSE RESULT I WANT TO CAPTURE AS A JAVA VAR*/
            jmolPanel.viewer.evalString("measure 3 4");

        }else
            Logger.error(strError);
    }

    final static String strScript = "hide sidechain;select backbone;ribbon ON;color white;wireframe OFF;spacefill OFF;set frank OFF;";

    static class ApplicationCloser extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    static class JmolPanel extends JPanel {

        JmolViewer viewer;

        private final Dimension currentSize = new Dimension();

        JmolPanel() {
            viewer = JmolViewer.allocateViewer(this, new SmarterJmolAdapter(),
                    null, null, null, null, null);
        }

        @Override
        public void paint(Graphics g) {
            getSize(currentSize);
            viewer.renderScreenImage(g, currentSize.width, currentSize.height);
        }
    }
}