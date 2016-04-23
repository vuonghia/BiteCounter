package sustainablehealthsolutionsllc.bitecounter;

/**
 * Created by john on 3/12/15.
 */
/**
 * Exception dealing with an error while autowiring an android view
 *
 * @author Jacob Kanipe-Illig (jkanipe-illig@cardinalsolutions.com)
 * Copyright (c) 2013
 */
public class AndroidAutowireException extends RuntimeException {

    private static final long serialVersionUID = 7445208526652970323L;

    public AndroidAutowireException(String message){
        super(message);
    }
}