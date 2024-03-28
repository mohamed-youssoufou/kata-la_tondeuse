package fr.harington.mowitnow.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Position {
    private int horizontalPosition;
    private int verticalPosition;
    private char orientation;

    @Override
    public String toString() {
        return horizontalPosition + " " + verticalPosition + " " + orientation;
    }
}
