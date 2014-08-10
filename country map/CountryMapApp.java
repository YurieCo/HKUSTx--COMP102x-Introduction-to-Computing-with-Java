import comp102x.Canvas;
import comp102x.ColorImage;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * It is a demo application illustrating a country map with the ability to 
 * pin travel pictures. It manages a CountryMapModel and cooperates with a 
 * CountryMapMouseListener to provide major functionalities of this application.
 * Users can pin new photos, view/zoom pinned photos, and remove photos.
 * It implements ActionListener to handle button events, implements 
 * PropertyChangeListener to track mode change of the underlying model and 
 * update the status bar accordingly.
 * 
 * @author twkfung@ust.hk 
 * @version 1.0
 */
public class CountryMapApp implements ActionListener, PropertyChangeListener
{
    /** Scale factor to adjust the map and canvas size. **/
    private static final double COUNTRYMAP_SCALE=1.16;
    /** Country map width. **/
    private static final int COUNTRYMAP_WIDTH=800;
    /** Country map height. **/
    private static final int COUNTRYMAP_HEIGHT=600;
    /** Font size of major UI components. **/
    private static final int UI_FONT_SIZE=20;
    /** The underlying data model. **/
    private CountryMapModel model;
    /** The canvas which displays a countrymap and travel photos. **/
    private Canvas canvas;
    /** The status bar component. **/
    private JLabel statusBar;

    /** Action command for a pin action. **/
    private static final String CMD_PIN="Pin";
    /** Action command for a browse action. **/
    private static final String CMD_BROWSE="Browse";
    /** Action command for a delete action. **/
    private static final String CMD_DELETE="Delete";

    /** Constructs a country map application instance. **/
    public CountryMapApp() {
        model = new CountryMapModel();
        canvas = createCanvas();
        model.setCanvas(canvas);
        model.addPropertyChangeListener(this);
        statusBar = new JLabel("Status: Now in browse mode.");
        statusBar.setFont(statusBar.getFont().deriveFont((float) UI_FONT_SIZE));
        initComponents();
    }

    /** Initializes and lays out UI components. **/
    private void initComponents() {
        JComponent top = createTopComponent();
        canvas.setComponentAtTop(top);
        canvas.setComponentAtBottom(statusBar);
    }

    /** Creates and configures the canvas instance. **/
    private Canvas createCanvas() {
        Canvas c = new Canvas((int) (COUNTRYMAP_WIDTH * COUNTRYMAP_SCALE), (int) (COUNTRYMAP_HEIGHT * COUNTRYMAP_SCALE));
        c.setResizable(false);
        ColorImage countrymap = new ColorImage("images/map.jpg");
        countrymap.setMovable(false);
        countrymap.setScale(COUNTRYMAP_WIDTH * COUNTRYMAP_SCALE / 800);
        c.add(countrymap);

        CountryMapMouseListener mlistener = new CountryMapMouseListener();
        mlistener.setModel(model);
        c.addMouseListener(mlistener);

        return c;
    }

    /** Creates and configures the top panel of buttons. **/
    private JComponent createTopComponent() {
        JPanel panel = new JPanel();
        JButton button;
        button = new JButton("Browse mode");
        button.setActionCommand(CMD_BROWSE);
        button.addActionListener(this);
        Font font=button.getFont().deriveFont((float) UI_FONT_SIZE);
        button.setFont(font);
        panel.add(button);
        button = new JButton("Pin mode");
        button.setActionCommand(CMD_PIN);
        button.addActionListener(this);
        button.setFont(font);
        panel.add(button);
        button = new JButton("Delete mode");
        button.setActionCommand(CMD_DELETE);
        button.addActionListener(this);
        button.setFont(font);
        panel.add(button);
        return panel;
    }

    /**
     * Implements ActionListener.
     * ActionEvents are received from UI buttons. 
     * It changes the execution mode of the underlying model
     * depending on which button action is received.
     **/
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case CMD_PIN:
            model.setMode(CountryMapModel.MODE_PIN);
            break;
            case CMD_DELETE:
            model.setMode(CountryMapModel.MODE_DELETE);
            break;
            case CMD_BROWSE:
            default:
            model.setMode(CountryMapModel.MODE_BROWSE);
        }
    }
    
    /**
     * Implements PropertyChangeListener. 
     * It listens to updates on a bound property "mode" which 
     * are issued by the underlying data model. This methods 
     * updates the status bar to indicate the active execution mode.
     **/
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("mode".equals(event.getPropertyName())) {
            int mode = (Integer) event.getNewValue();
            String s="Status: ";
            s+= new String[] {"Now in browse mode.", 
                "Now in pin mode.", 
                "Now in delete mode."
            }[mode];
            statusBar.setText(s);
        }
    }

    public static void main(String[] args) {
        // creates and runs the applicatoin
        CountryMapApp app = new CountryMapApp();
    }
}
