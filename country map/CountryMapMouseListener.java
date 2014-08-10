import comp102x.ColorImage;
import java.awt.event.*;

/**
 * CountryMapMouseListener is a class listening to mouse events.
 * Typically, the events are triggered from a country map application. 
 * Instance of this class will handle the mouse clicks depending on 
 * the current mode of the application, and perform the cooresponding 
 * actions. Data is manipulated through a model class.
 * 
 * @author twkfung@ust.hk 
 * @version 1.0
 */
public class CountryMapMouseListener implements MouseListener
{
    /** Suggested width of a thumbnail image. **/
    private static final int THUMBNAIL_WIDTH_MAX=100;
    /** Suggested height of a thumbnail image. **/
    private static final int THUMBNAIL_HEIGHT_MAX=75;
    /** Suggested width of an image shown in regular format. **/
    private static final int REGULAR_WIDTH_MAX=400;
    /** Suggested height of an image shown in regular format. **/
    private static final int REGULAR_HEIGHT_MAX=300;
    
    /** The model data to be manipulated by this listener. **/
    private CountryMapModel model;
    
    /**
     * Assocates a data model for this listener. 
     * Other methods of this class rely on the existence of associated model. 
     * User is responsible for providing the valid data model before the listener 
     * instance can be used.
     * 
     * @param model the data model to be associated.
     **/
    public void setModel(CountryMapModel model) {
        this.model = model;
    }

    /**
     * Implements to process a mouse clicked event as defined in MouseListener. 
     * Implementation should check the current mode to determine and perform a suitable action, 
     * such as zooming in/out a photo in browse mode, adding a new photo in pin mode, or remove 
     * an existing photo in detele mode. See also CountryMapModel.MODE_BROWSE, CountryMapModel.MODE_PIN 
     * and CountryMapModel.MODE_DELETE.
     **/
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (model.getMode()) {
            case CountryMapModel.MODE_BROWSE:
            doBrowsePhoto(e);
            break;
            case CountryMapModel.MODE_PIN:
            doPinNewPhoto(e);
            break;
            case CountryMapModel.MODE_DELETE:
            doRemovePhoto(e);
            break;
        }
        // log a mouse click received, for debug only
        // System.out.println("Button #" +e.getButton() +" is clicked.");
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // do nothing as we are not interested in this event
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // do nothing as we are not interested in this event
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // do nothing as we are not interested in this event
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // do nothing as we are not interested in this event
    }
    /**
     * Processes mouse clicks while the program is in browse mode. 
     * This method is called only after a mouse click is received while in browse mode. 
     * If a left mouse button click hits a photo, it enlarges the photo to a regular format. 
     * If a right mouse button click hits a photo, it shrinks the photo to a thumbnail format.
     * 
     * Unknown issue: there is no gurantee to determine the z-order of existing photos. 
     * Canvas provides no accessor to retrieve z-order information which might be altered by the user 
     * through its UI.
     * 
     * Implementation is suggested to loop through all the photos and see if a hit is detected, and 
     * resize the target photo by setting an appropriate scale factor. Users may find the following 
     * methods useful. 
     * Methods from MouseEvent can gather information such as the coordinate of the event, 
     * the button which produces the event. Model.getPhotos() helps to get an array copy of the photos.
     * Helper methods findRegularScale(ColorImage) and findThumbnailScale(ColorImage) calculate
     * the scale factor values. isInsideImage(int, int, ColorImage) tests if a position lies inside 
     * an image.
     **/
    private void doBrowsePhoto(MouseEvent e) {
        
        // please write your code after this line
        
        int x = e.getX();
        int y = e.getY();
        boolean leftClicked = false;
        boolean rightClicked = false;
        if (e.BUTTON1 == e.getButton()  )
        {
            leftClicked = true;
             x = e.getX();
             y = e.getY();
        } else leftClicked = false;
         if (e.BUTTON3 == e.getButton()){
             rightClicked = true;
            x = e.getX();
            y = e.getY();
             
        }else rightClicked = false;

        // code to be executed after the initialization of the variables x, y, leftClicked and rightClicked
        ColorImage[] photos = model.getPhotos();
        ColorImage photo;
        double scale1, scale2, scaleMean;
        for (int i=0; i<photos.length; i++) {
            photo=photos[i];
            if (isInsideImage(x, y, photo)) {
                scale1=findThumbnailScale(photo);
                scale2=findRegularScale(photo);
                if (rightClicked) {
                    photo.setScale(scale1);
                } else if (leftClicked){
                    photo.setScale(scale2);
                }
                break;
            }
        }
    }
    /**
     * Processes mouse clicks while the program is in pin mode. 
     * This method is called only after a mouse click is received while in pin mode. 
     * A new ColorImage will be loaded and pinned at the mouse location. 
     * The photo will be initially scaled to a thumbnail format.
     * Before this method finishes, it updates the model back to browse mode.
     **/
    private void doPinNewPhoto(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        ColorImage photo=new ColorImage();
        photo.setScale(findThumbnailScale(photo));
        model.pinPhoto(photo, x, y);
        model.setMode(CountryMapModel.MODE_BROWSE);
    }
    /**
     * Processes mouse clicks while the program is in  delete mode. 
     * This method is called only after a mouse click is received whlie in delete mode.
     * If the moues click hits an existing photo, the target photo is removed from the 
     * model. This method ends by updaing the model to browse mode.
     **/
    private void doRemovePhoto(MouseEvent e) {
        int x=e.getX();
        int y=e.getY();
        for (ColorImage photo :model.getPhotos()) {
            if (isInsideImage(x, y, photo)) {
                model.removePhoto(photo);
                break;
            }
        }
        model.setMode(CountryMapModel.MODE_BROWSE);
    }
    
    /**
     * Calculates a scale factor to fit the image in thumbnail format.
     */
    private double findThumbnailScale(ColorImage image) {
        return findTargetScale(image.getWidth(), image.getHeight(), THUMBNAIL_WIDTH_MAX, THUMBNAIL_HEIGHT_MAX);
    }
    /**
     * Calculates a scale factor to fit the image in regular format.
     */
    private double findRegularScale(ColorImage image) {
        return findTargetScale(image.getWidth(), image.getHeight(), REGULAR_WIDTH_MAX, REGULAR_HEIGHT_MAX);
    }
    /**
     * Calculates a scale factor to fit the suggested target dimension. 
     **/
    private double findTargetScale(int imgWidth, int imgHeight, int targetWidth, int targetHeight) {
        double scaleW = targetWidth / (double) imgWidth;
        double scaleH = targetHeight / (double) imgHeight;
        return Math.min(scaleW, scaleH);
    }
    /**
     * Tests if a point lies inside the specified image. It also considers the current scale factor of the image.
     * 
     * @return true if the point lies within the scaled bound of the input image.
     **/
    private boolean isInsideImage(int x, int y, ColorImage image) {
        double scale=image.getScale();
        int imgX=image.getX();
        int imgY=image.getY();
        int imgW=(int) (image.getWidth() * scale);
        int imgH=(int) (image.getHeight() * scale);
        return (((imgX <= x) && (x <= imgX+imgW)) && ((imgY <= y) && (y <= imgY+imgH)));
    }
}