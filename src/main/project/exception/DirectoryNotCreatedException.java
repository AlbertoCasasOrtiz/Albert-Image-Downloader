package main.project.exception;

/**
 * Exception thrown when a directory has not been created properly.
 */
public class DirectoryNotCreatedException extends Exception {

    /**
     * Constructor for FileNotCreatedException.
     * @param dirName Name of the directory which caused the error.
     */
    public DirectoryNotCreatedException(String dirName){
        super("Error creating the directory " + dirName);
    }
}
