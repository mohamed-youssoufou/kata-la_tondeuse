package fr.harington.mowitnow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class AutoMowerIntegrationTest {

    @Nested
    class TestMain {
        @ParameterizedTest
        @CsvSource(value = {
                "src/test/resources/files/testFileWith1Command.txt:1 3 N",
                "src/test/resources/files/testFileWith2Command.txt:5 1 E"
        }, delimiter = ':')
        void givenTestFile_whenRunMain_thenPrintFinalPositionHasCorrectPosition(final String fileLocation, final String partialOutputText) {
            // given
            var fileInputStream = new ByteArrayInputStream((fileLocation + "\nN\n").getBytes());
            System.setIn(fileInputStream);
            var outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            // when
            AutoMower.main(new String[]{});
            // then
            Assertions.assertTrue(outputStream.toString().contains(partialOutputText));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "src/test/resources/files/invalidTestFileAtCommandLine.txt,Une erreur est survenue lors du traitement de votre fichier: Format invalide pour les commandes : GAGUAGAGAAA",
                "src/test/resources/files/invalidTestFileAtLawnDimensionLine.txt,Une erreur est survenue lors du traitement de votre fichier: Format invalide pour les dimensions : 5 U",
                "src/test/resources/files/invalidTestFileAtMowerInitialLine.txt,Une erreur est survenue lors du traitement de votre fichier: Format invalide pour la position : 1 2 Z",
        }, delimiter = ',')
        void givenTestFile_whenRunMain_thenThrowInvalidFileFormatException(final String fileLocation, final String partialOutputText) {
            // given
            var fileInputStream = new ByteArrayInputStream((fileLocation + "\nN\n").getBytes());
            System.setIn(fileInputStream);

            var outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            // when
            AutoMower.main(new String[]{});
            var oki = outputStream.toString();
            // then
            Assertions.assertTrue(outputStream.toString().contains(partialOutputText));
        }
    }
}