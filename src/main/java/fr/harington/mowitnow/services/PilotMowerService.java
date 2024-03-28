package fr.harington.mowitnow.services;

import fr.harington.mowitnow.domain.Mower;
import fr.harington.mowitnow.domain.Position;

import java.util.List;

public interface PilotMowerService {
    Position move(final Mower mower, final List<Character> command);
}
