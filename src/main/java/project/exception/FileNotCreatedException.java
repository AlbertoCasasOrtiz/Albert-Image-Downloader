package project.exception;

/**
 * Exception thrown when a file has not been created properly.
 */
public class FileNotCreatedException extends Exception {

    /**
     * Constructor for FileNotCreatedException.
     * @param fileName Name of the file which caused the error.
     */
    public FileNotCreatedException(String fileName){
        super("Error creating the file " + fileName);
    }
}
