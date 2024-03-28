package fr.harington.mowitnow.domain;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LawnMowerFileModel {
    private Lawn lawn;
    private List<Command> commands;
}
