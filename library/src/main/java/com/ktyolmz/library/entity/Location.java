package com.ktyolmz.library.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    @SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq", allocationSize = 1)
    private Long id;

    private Long bookId;

    private int roomNumber;

    private int shelfNumber;
}
