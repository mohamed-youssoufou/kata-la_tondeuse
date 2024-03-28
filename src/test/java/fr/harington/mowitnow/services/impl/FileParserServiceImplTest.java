package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Command;
import fr.harington.mowitnow.domain.Lawn;
import fr.harington.mowitnow.domain.LawnMowerFileModel;
import fr.harington.mowitnow.domain.Position;
import fr.harington.mowitnow.exceptions.InvalidFileFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class FileParserServiceImplTest {

    @Mock
    private ValidatorServiceImpl validatorService;

    @Nested
    class TestParse {
        @ParameterizedTest
        @CsvSource(value = {
                "src/test/resources/files/testFileWith1Command.txt:5 5:1 2 N:GAGAGAGAA",
                "src/test/resources/files/testFileWith1Command_2.txt:10 10:1 3 N:GAD"
        }, delimiter = ':')
        @DisplayName("given file path when call parse then create new LawnMowerFileModel")
        void givenFilePath_whenCallParse_thenCreateNewLawnMowerFileModel(
                final String fileLocation,
                final String lawnDimension,
                final String initialPosition,
                final String movement
        ) throws IOException {
            // given
            var path = Paths.get(fileLocation);
            var lawnDimensionExpectedSplit = lawnDimension.split(" ");
            var lawnExpected = new Lawn(Integer.parseInt(lawnDimensionExpectedSplit[0]), Integer.parseInt(lawnDimensionExpectedSplit[1]));

            var initialPositionSplit = initialPosition.split(" ");
            var positionExpected = new Position(
                    Integer.parseInt(initialPositionSplit[0]),
                    Integer.parseInt(initialPositionSplit[1]),
                    initialPositionSplit[2].charAt(0)
            );
            var movementExpected = movement.chars().mapToObj(cmd -> (char) cmd).toList();
            var commandsExpected = List.of(new Command(movementExpected, positionExpected));
            var expectedLawnMowerFileModel = new LawnMowerFileModel(lawnExpected, commandsExpected);
            Mockito.lenient().when(validatorService.validateAndParseLawnDimensions(ArgumentMatchers.any())).thenReturn(lawnExpected);
            // when
            var actualLawnMowerFileModel = new FileParserServiceImpl().parse(path);
            // then
            assertEquals(expectedLawnMowerFileModel, actualLawnMowerFileModel);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "src/test/resources/files/invalidTestFileAtCommandLine.txt,Format invalide pour les commandes : GAGUAGAGAAA",
                "src/test/resources/files/invalidTestFileAtLawnDimensionLine.txt,Format invalide pour les dimensions : 5 U",
                "src/test/resources/files/invalidTestFileAtMowerInitialLine.txt,Format invalide pour la position : 1 2 Z",
        }, delimiter = ',')
        @DisplayName("given empty file path when call parse then throw InvalidFileFormatException")
        void givenEmptyFilePath_whenCallParse_thenThrowInvalidFileFormatException(final String fileLocation, final String expectedExceptionMessage) {
            // given
            var path = Paths.get(fileLocation);
            // when and then
            var exception = assertThrows(InvalidFileFormatException.class, () -> new FileParserServiceImpl().parse(path));
            assertEquals(expectedExceptionMessage, exception.getMessage());
        }
    }
}