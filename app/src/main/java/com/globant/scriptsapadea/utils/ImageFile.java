package com.globant.scriptsapadea.utils;

import java.io.File;
import java.net.URI;

/**
 * With this class we handle image parameters.
 *
 * @author nicolas.quartieri
 */
public class ImageFile extends File {

    public static final long IMAGE_SIZE_LIMIT = 1024;

    public ImageFile(URI uri) throws ImageException {
        super(uri);
        checkImageSize();
    }


    public ImageFile(String path) throws ImageException {
        super(path);
        checkImageSize();
    }

    /**
     * Check the size of the image.
     *
     * @throws ImageException
     */
    private void checkImageSize() throws ImageException {
        long length = length() / 1024;
        if (length > IMAGE_SIZE_LIMIT) { //in KB
            throw new ImageException("Image Size Limit exceeded!");
        }
    }

    /**
     * Throw this exception when limit size exceeded.
     */
    public class ImageException extends Exception {
        public ImageException(String message) {
            super(message);
        }
    }
}
