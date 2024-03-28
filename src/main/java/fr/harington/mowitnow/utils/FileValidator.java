package fr.harington.mowitnow.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileValidator {

    public static boolean veritfyFileExtension(final String absolutePath, final String extension) {
        return absolutePath.endsWith(extension);
    }
}
