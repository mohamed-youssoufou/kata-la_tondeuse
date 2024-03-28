package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Lawn;
import fr.harington.mowitnow.domain.Mower;
import fr.harington.mowitnow.domain.enums.MowerDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PilotMowerServiceImplTest {

    @Nested
    class TestMove {
        @ParameterizedTest
        @CsvSource(value =
                {
                        "1 2 N:G:1 2 W",
                        "1 2 N:GA:0 2 W",
                        "1 2 N:GAG:0 2 S",
                        "1 2 N:GAGA:0 1 S",
                        "1 2 N:GAGAG:0 1 E",
                        "1 2 N:GAGAGA:1 1 E",
                        "1 2 N:GAGAGAG:1 1 N",
                        "1 2 N:GAGAGAGA:1 2 N",
                        "1 2 N:GAGAGAGAA:1 3 N",
                        "1 2 N:GAGAGAGAAD:1 3 E",
                },
                delimiter = ':')
        @DisplayName("given mover and relative instructions when move a mower then it return correct position")
        void givenMowerAndCommands_whenMoveMower_thenMowerHasCorrectPosition(String input, String movement, String output) {
            // given
            var splitedInput = input.split(" ");
            var movementExpected = movement.chars().mapToObj(cmd -> (char) cmd).toList();
            var spliedOutput = output.split(" ");

            var lawn = new Lawn(5, Integer.parseInt(splitedInput[1]));
            var mower = new Mower(
                    Integer.parseInt(splitedInput[0]),
                    Integer.parseInt(splitedInput[1]),
                    MowerDirection.valueOf(splitedInput[2]),
                    lawn);
            var mowerService = new PilotMowerServiceImpl();
            // when
            mowerService.move(mower, movementExpected);
            // then
            assertEquals(Integer.parseInt(spliedOutput[0]), mower.getHorizontalPosition());
            assertEquals(Integer.parseInt(spliedOutput[1]), mower.getVerticalPosition());
            assertEquals(MowerDirection.valueOf(spliedOutput[2]), mower.getMowerDirection());
        }
    }
}