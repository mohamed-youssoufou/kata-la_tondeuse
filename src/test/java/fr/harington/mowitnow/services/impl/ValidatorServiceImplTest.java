package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Lawn;
import fr.harington.mowitnow.domain.Position;
import fr.harington.mowitnow.exceptions.InvalidFileFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorServiceImplTest {

    private ValidatorServiceImpl validatorServiceImpl;

    @BeforeEach
    void setUp() {
        validatorServiceImpl = new ValidatorServiceImpl();
    }

    @Nested
    class TestValidateAndParseLawnDimensions {
        @ParameterizedTest
        @CsvSource(value =
                {
                        "5 5:5:5",
                        "12 11:12:11",
                        "2 2:2:2",
                },
                delimiter = ':')
        @DisplayName("given line of LawnDimensions when validateAndParseLawnDimensions then return correct final lawn dimensions")
        void givenLineOfLawnDimensions_whenCallValidateAndParseLawnDimensions_thenNewLawnHasCorrectDimensions(String line, String expectedHorizontalDimension, String expectedVeriticalDimension) {
            // given
            var expectedLawn = new Lawn(Integer.parseInt(expectedHorizontalDimension), Integer.parseInt(expectedVeriticalDimension));
            // when
            var actualLawn = validatorServiceImpl.validateAndParseLawnDimensions(line);
            // then
            assertEquals(expectedLawn, actualLawn);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "5,Format invalide pour les dimensions : 5", "12 12 12, " +
                "Format invalide pour les dimensions : 12 12 12"
        }, delimiter = ',')
        @DisplayName("given line of LawnDimensions when validateAndParseLawnDimensions then ThrowInvalidFileFormatException")
        void givenLineOfLawnDimensions_whenCallValidateAndParseLawnDimensions_thenThrowInvalidFileFormatException(final String line, final String expectedMessage) {
            var exception = assertThrows(InvalidFileFormatException.class, () -> validatorServiceImpl.validateAndParseLawnDimensions(line));
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Nested
    class TestValidateAndParsePosition {
        @ParameterizedTest
        @CsvSource(value =
                {
                        "1 2 N:1:2:N",
                        "0 3 S:0:3:S",
                        "2 4 E:2:4:E",
                        "2 0 W:2:0:W",
                },
                delimiter = ':')
        @DisplayName("given line of mower initial position when validateAndParsePosition then return correct mower position")
        void givenLineOfMowerInitialPosition_whenCallValidateAndParsePosition_thenNewPositionHasCorrectPosition(String line, String expectedHorizontalPosition, String expecedVerticalPosition, String expectedOrientation) {
            // given
            var expectedPosition = new Position(Integer.parseInt(expectedHorizontalPosition), Integer.parseInt(expecedVerticalPosition), expectedOrientation.charAt(0));
            // when
            var actualPosition = validatorServiceImpl.validateAndParsePosition(line);
            // then
            assertEquals(expectedPosition, actualPosition);
        }

        @ParameterizedTest
        @CsvSource(value =
                {
                        "S 2 U,Format invalide pour la position : S 2 U",
                        "0 3 Z,Format invalide pour la position : 0 3 Z",
                        "2 K W,Format invalide pour la position : 2 K W"
                }, delimiter = ',')
        @DisplayName("given line of mower initial position when validateAndParsePosition then return InvalidFileFormatException")
        void givenLineOfMowerInitialPosition_whenCallValidateAndParsePosition_thenThrowInvalidFileFormatException(String line, final String expectedMessage) {
            var exception = assertThrows(InvalidFileFormatException.class, () -> validatorServiceImpl.validateAndParsePosition(line));
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Nested
    class TestValidateAndParseCommands {
        @ParameterizedTest
        @CsvSource(value =
                {
                        "GAG:G A G",
                        "D:D",
                        "AAA:A A A",
                        "G:G",
                },
                delimiter = ':')
        @DisplayName("given line of mower commands when validateAndParseCommands then return correct list of Characters who represent all command")
        void givenLineOfCommands_whenCallValidateAndParseCommands_thenNewListOfCharacterHasCorrectCommands(String line, String expectedCommand) {
            // given
            var exepectedListOfCharacter = Arrays.stream(expectedCommand.split(" ")).map(cmd -> cmd.charAt(0)).toList();
            // when
            var actualListOfCharacter = validatorServiceImpl.validateAndParseCommands(line);
            // then
            assertEquals(exepectedListOfCharacter, actualListOfCharacter);
        }


        @ParameterizedTest
        @CsvSource(value =
                {
                        "OFG,Format invalide pour les commandes : OFG",
                        "NNNNN,Format invalide pour les commandes : NNNNN",
                        "QRS1,Format invalide pour les commandes : QRS1"
                }, delimiter = ',')
        @DisplayName("given line of mower commands when validateAndParseCommands then throw InvalidFileFormatException")
        void givenLineOfCommands_whenCallValidateAndParseCommands_thenThrowInvalidFileFormatException(String line, final String expectedMessage) {
            var exception = assertThrows(InvalidFileFormatException.class, () -> validatorServiceImpl.validateAndParseCommands(line));
            assertEquals(expectedMessage, exception.getMessage());
        }
    }
}