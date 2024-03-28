package fr.harington.mowitnow.services.impl;

import fr.harington.mowitnow.domain.*;
import fr.harington.mowitnow.domain.enums.MowerDirection;
import fr.harington.mowitnow.services.PilotMowerService;
import fr.harington.mowitnow.services.ProcessorService;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {
    private PilotMowerService pilotService;

    @Override
    public List<Position> execute(final LawnMowerFileModel fileStructure) {
        var positions = new ArrayList<Position>();
        var lawn = fileStructure.getLawn();
        for (Command command : fileStructure.getCommands()) {
            var mower = createMower(command, lawn);
            positions.add(pilotService.move(mower, command.getMovement()));
        }
        return positions;
    }

    private Mower createMower(final Command command, final Lawn lawn) {
        var horizontalPosition = command.getPosition().getHorizontalPosition();
        var verticalPosition = command.getPosition().getVerticalPosition();
        var direction = MowerDirection.valueOf(String.valueOf(command.getPosition().getOrientation()));
        return new Mower(horizontalPosition, verticalPosition, direction, lawn);
    }
}
