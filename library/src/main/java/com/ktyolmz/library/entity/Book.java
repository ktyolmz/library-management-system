package com.ktyolmz.library.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String title;
    private String author;
    private Long borrowedUserId;
    private LocalDate borrowDate;
    private LocalDate dueDate;

}
