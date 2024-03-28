package fr.harington.mowitnow.services;

import fr.harington.mowitnow.domain.LawnMowerFileModel;

import java.io.IOException;
import java.nio.file.Path;

public interface FileParserService {
    LawnMowerFileModel parse(final Path file) throws IOException;
}
