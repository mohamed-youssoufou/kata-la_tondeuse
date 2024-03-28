package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Command;
import fr.harington.mowitnow.domain.Lawn;
import fr.harington.mowitnow.domain.LawnMowerFileModel;
import fr.harington.mowitnow.exceptions.InvalidFileFormatException;
import fr.harington.mowitnow.services.FileParserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileParserServiceImpl implements FileParserService {
    @Override
    public LawnMowerFileModel parse(final Path path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            Lawn lawn = null;
            var commands = new ArrayList<Command>();
            var validator = new ValidatorServiceImpl();
            String currentLine;
            var currentLineNumber = 0;
            while ((currentLine = bufferedReader.readLine()) != null) {
                currentLineNumber++;
                if (currentLineNumber == 1) {
                    lawn = validator.validateAndParseLawnDimensions(currentLine);
                    if (lawn == null) {
                        throw new InvalidFileFormatException("Erreur sur le format des dimensions à la ligne " + currentLineNumber);
                    }
                } else if (currentLineNumber % 2 == 0) {
                    var position = validator.validateAndParsePosition(currentLine);
                    if (position == null) {
                        throw new InvalidFileFormatException("Erreur sur le format de la position à la ligne " + currentLineNumber);
                    }
                    commands.add(new Command(null, position));
                } else {
                    var movement = validator.validateAndParseCommands(currentLine);
                    if (movement == null) {
                        throw new InvalidFileFormatException("Erreur de format dans les instructions à la ligne " + currentLineNumber);
                    }
                    commands.get(commands.size() - 1).setMovement(movement);
                }
            }

            return new LawnMowerFileModel(lawn, commands);
        } catch (IOException e) {
            throw new IOException("Erreur lors de la lecture du fichier.", e);
        }
    }
}
