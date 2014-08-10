import comp102x.ColorImage;
import comp102x.Canvas;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.EventListenerList;
import javax.swing.SwingUtilities;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import javax.swing.event.SwingPropertyChangeSupport;

/**
 * It is a model class maintaining core data and state of the application.
 * 
 * @author twkfung@ust.hk 
 * @version 1.0
 */
public class CountryMapModel
{
    /** Indicates the model is running in browse mode. **/
    public static final int MODE_BROWSE=0;
    /** Indicates the model is running in pin mode. **/
    public static final int MODE_PIN=1;
    /** Indicates the model is running in delete mode. **/
    public static final int MODE_DELETE=2;

    /** Maintains the current mode. Initially it is in browse mode. **/
    private int mode=MODE_BROWSE;
    /** The canvas instance to which photos can be added or removed. **/
    private Canvas canvas;
    /** 
     * The list of photos added.  
     * For convenience and efficiency, the current implementation 
     * is using an ArrayList<>. Arbitrary number of photos can be maintained. 
     * In fact, a student may choose to replaced it with 
     * an ordinary ColorImage[].
     **/
    private Collection<ColorImage> photos = new ArrayList<>();
    
    /** Default constructor which starts the model in browse with an empty photo list. **/
    public CountryMapModel() {
    }
    /** 
     * Inquires the current executiion mode.
     * 
     * @return the current execution mode, which is one of the MODE_XXX constants.
     **/
    public int getMode() {
        return mode;
    }
    /**
     * Changes the current execution mode to a new mode. 
     * 
     * @param m the new mode value.
     * @throw IllegalArgumentException if m is not a legal mode value.
     **/
    public void setMode(int m) {
        final int modeFirst = MODE_BROWSE;
        final int modeLast = MODE_DELETE;
        if ((m < modeFirst) || (m > modeLast)) {
            throw new IllegalArgumentException("Illegal mode value.");
        }
        final int oldMode=this.mode;
        this.mode=m;
        pcs.firePropertyChange("mode", oldMode, mode);
    }
    /**
     * Assigns a Canvas instance for hosting the photos which are ColorImage instances.
     * A valid Canvas should be associated to this object before this model can be used.
     * @param c the target Canvas
     **/
    public void setCanvas(Canvas c) {
        canvas=c;
    }
    
    /**
     * Returns an array copy of the photos.
     * @return an array copy of the photos.
     **/
    public ColorImage[] getPhotos() {
        return photos.toArray(new ColorImage[0]);
    }
    
    /**
     * Pins the photo at position (geoX, geoY) on the map. 
     * The newly pinned photo will also be added to a canvas for display.
     * @param photo the photo to be added/pinned.
     * @param geoX x-coordinate of the pin location.
     * @param geoY y-coordinate of the pin location.
     **/
    public void pinPhoto(ColorImage photo, int geoX, int geoY) {
        photo.setX(geoX);
        photo.setY(geoY);
        if (!photos.contains(photo)) {
            photos.add(photo);
            canvas.add(photo);
        }
    }
    
    /**
     * Removes a photo from internal store and canvas.
     * @param photo the photo to be removed.
     **/
    public void removePhoto(ColorImage photo) {
        canvas.remove(photo);
        photos.remove(photo);
    }
    
    /** 
     * Support instance for managing property change events. 
     * This class has a bound property "mode". See getMode().
     **/
    private final SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(this, true);
    /** Registers a PropertyChangeListener for the bound property "mode". **/
    public void addPropertyChangeListener(PropertyChangeListener l) {
        this.pcs.addPropertyChangeListener(l);
    }
    /** Unregisters the listener from this instance. **/
    public void removePropertyChangeListener(PropertyChangeListener l) {
        this.pcs.removePropertyChangeListener(l);
    }
}
