package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.Mower;
import fr.harington.mowitnow.domain.Position;
import fr.harington.mowitnow.services.PilotMowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PilotMowerServiceImpl implements PilotMowerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PilotMowerServiceImpl.class);

    public Position move(final Mower mower, final List<Character> commands) {
        LOGGER.info("----- Begin executeTaskCommand with commande %s ------ {} ", commands);
        for (Character command : commands) {
            switch (command) {
                case 'A' -> moveOnCurrentDirection(mower);
                case 'D' -> mower.setMowerDirection(mower.getMowerDirection().rotateRight());
                case 'G' -> mower.setMowerDirection(mower.getMowerDirection().rotateLeft());
            }
        }
        LOGGER.info("----- End executeTaskCommand with commande %s ------ {} ", commands);
        return new Position(mower.getHorizontalPosition(), mower.getVerticalPosition(), mower.getMowerDirection().name().charAt(0));
    }

    private void moveOnCurrentDirection(final Mower mower) {

        LOGGER.info("--- Begin moveInCurrentDirection ---");
        var newX = mower.getHorizontalPosition();
        var newY = mower.getVerticalPosition();

        switch (mower.getMowerDirection()) {
            case N -> newY += 1;
            case E -> newX += 1;
            case S -> newY -= 1;
            case W -> newX -= 1;
        }
        updatePosition(newX, newY, mower);
        LOGGER.info("--- End moveInCurrentDirection ---");
    }

    private void updatePosition(final int newX, final int newY, final Mower mower) {
        mower.setHorizontalPosition(newX);
        mower.setVerticalPosition(newY);
    }
}
