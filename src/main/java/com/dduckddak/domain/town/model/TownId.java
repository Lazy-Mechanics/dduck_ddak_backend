package com.dduckddak.domain.town.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TownId implements Serializable {

    private Long id;
    private Long quarter;
}
