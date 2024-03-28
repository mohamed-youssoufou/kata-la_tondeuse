package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Lawn;
import fr.harington.mowitnow.domain.Position;
import fr.harington.mowitnow.exceptions.InvalidFileFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ValidatorServiceImpl {
    public Lawn validateAndParseLawnDimensions(final String line) throws InvalidFileFormatException {
        var regex = "^(\\d+)\\s+(\\d+)$";
        var matcher = Pattern.compile(regex).matcher(line.trim());
        if (matcher.matches()) {
            var width = Integer.parseInt(matcher.group(1));
            var height = Integer.parseInt(matcher.group(2));
            return new Lawn(width, height);
        }
        throw new InvalidFileFormatException("Format invalide pour les dimensions : " + line);
    }

    public Position validateAndParsePosition(final String line) throws InvalidFileFormatException {
        var regex = "(\\d+) (\\d+) ([NSWE])";
        var matcher = Pattern.compile(regex).matcher(line.trim());
        if (matcher.matches()) {
            var x = Integer.parseInt(matcher.group(1));
            var y = Integer.parseInt(matcher.group(2));
            var orientation = matcher.group(3).charAt(0);
            return new Position(x, y, orientation);
        }
        throw new InvalidFileFormatException("Format invalide pour la position : " + line);
    }

    public List<Character> validateAndParseCommands(final String line) throws InvalidFileFormatException {
        var regex = "[GAD]+";
        if (line.matches(regex)) {
            List<Character> commands = new ArrayList<>();
            for (char command : line.trim().toCharArray()) {
                commands.add(command);
            }
            return commands;
        }
        throw new InvalidFileFormatException("Format invalide pour les commandes : " + line);
    }
}
