package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Command;
import fr.harington.mowitnow.domain.Lawn;
import fr.harington.mowitnow.domain.LawnMowerFileModel;
import fr.harington.mowitnow.domain.Position;
import fr.harington.mowitnow.services.PilotMowerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProcessorServiceImplTest {

    @Mock
    private PilotMowerService pilotMowerService;

    @InjectMocks
    private ProcessorServiceImpl processorService;

    @Nested
    class TestExecute {
        @ParameterizedTest
        @CsvSource(value = {
                "5 5:1 2 N:GAGAGAGAA:1 3 N",
                "5 5:3 3 E:AADAADADDA:5 1 E"
        }, delimiter = ':')
        @DisplayName("given LawnMowerFileModel when execute then return correct final position")
        void givenLawnMowerFileModel_whenExecute_thenPositionHasCorrectPosition(
                String lawnDimension,
                String initialPosition,
                String movement,
                String expectedPositionString
        ) {
            // given
            var lawnDimensionExpectedSplit = lawnDimension.split(" ");
            var lawnExpected = new Lawn(Integer.parseInt(lawnDimensionExpectedSplit[0]), Integer.parseInt(lawnDimensionExpectedSplit[1]));

            var initialPositionSplit = initialPosition.split(" ");
            var positionSplited = new Position(
                    Integer.parseInt(initialPositionSplit[0]),
                    Integer.parseInt(initialPositionSplit[1]),
                    initialPositionSplit[2].charAt(0)
            );
            var movementExpected = movement.chars().mapToObj(cmd -> (char) cmd).toList();
            var commandsExpected = List.of(new Command(movementExpected, positionSplited));
            var expectedLawnMowerFileModel = new LawnMowerFileModel(lawnExpected, commandsExpected);

            var expectedPositionStringSplit = expectedPositionString.split(" ");
            var expectedPosition = new Position(
                    Integer.parseInt(expectedPositionStringSplit[0]),
                    Integer.parseInt(expectedPositionStringSplit[1]),
                    expectedPositionStringSplit[2].charAt(0)
            );
            Mockito.when(pilotMowerService.move(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(expectedPosition);
            // when
            var actualPosition = processorService.execute(expectedLawnMowerFileModel);
            // then
            assertEquals(List.of(expectedPosition), actualPosition);
        }
    }
}