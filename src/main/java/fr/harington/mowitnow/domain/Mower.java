package fr.harington.mowitnow.domain;

import fr.harington.mowitnow.domain.enums.MowerDirection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class Mower {
    private int horizontalPosition;
    private int verticalPosition;
    private MowerDirection mowerDirection;
    private Lawn lawn;

    public MowerDirection getMowerDirection() {
        return mowerDirection;
    }

    public void setMowerDirection(final MowerDirection mowerDirection) {
        this.mowerDirection = mowerDirection;
    }
}
