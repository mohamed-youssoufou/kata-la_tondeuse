package fr.harington.mowitnow.domain.enums;

/**
 * Enum representing the cardinal directions in which a mower can face.
 * <p>
 * N - North
 * E - East
 * S - South
 * W - West
 */
public enum MowerDirection {
    N,
    E,
    S,
    W;

    public MowerDirection rotateLeft() {
        int position = ordinal() - 1 >= 0 ? ordinal() - 1 : W.ordinal();
        return values()[position];
    }

    public MowerDirection rotateRight() {
        int position = (ordinal() + 1) > 3 ? 0 : (ordinal() + 1);
        return values()[position];
    }
}

