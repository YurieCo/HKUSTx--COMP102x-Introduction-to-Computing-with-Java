
public class Validator
{
   /**
    * Validate the disparity input by the user and return a correct value.
    * 
    * If the input disparity is small than 0, a value of 0 should be returned;
    * If the input disparity is larger than half of the width of the image, return half of the width of the image.
    * Otherwise, just return the input depth.
    * 
    * @param inputDisparity The depth entered by user.
    * @param widthOfImage The width of the football image.
    * @return The adjusted depth value for the football image.
    */
   public int validateDisparity(int inputDisparity, int widthOfImage) {
       /*
        * Complete the definitation of this method by following the
        * instructions above.
        */
       if (inputDisparity > 0.5 * widthOfImage ) return  (int) (0.5 * widthOfImage);
       
       
       return 0; // This line should be REMOVED after completeing the definition.
   }
}
