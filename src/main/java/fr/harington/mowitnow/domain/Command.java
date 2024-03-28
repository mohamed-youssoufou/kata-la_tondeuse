package fr.harington.mowitnow.domain;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Command {
    List<Character> movement;
    Position position;
}
