package fr.harington.mowitnow.utils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileValidatorTest {

    @Nested
    class TestVeritfyFileExtension {
        @ParameterizedTest
        @CsvSource(value = {
                "resources/files/testFileWith2Command.txt:txt",
                "resources/files/testFileWith1Command.txt:txt"
        }, delimiter = ':')
        void givenFilePath_whenRunVeritfyFileExtension_thenTrue(final String filePah, final String fileExtension) {
            assertTrue(FileValidator.veritfyFileExtension(filePah, fileExtension));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "resources/files/testFile.csv:txt",
                "resources/files/testFile2.csv:txt"
        }, delimiter = ':')
        void givenFilePath_whenRunVeritfyFileExtension_thenFalse(final String filePah, final String fileExtension) {
            assertFalse(FileValidator.veritfyFileExtension(filePah, fileExtension));
        }
    }
}