package fr.harington.mowitnow.services;

import fr.harington.mowitnow.domain.LawnMowerFileModel;
import fr.harington.mowitnow.domain.Position;

import java.util.List;


public interface ProcessorService {
    List<Position> execute(final LawnMowerFileModel fileStructure);
}
