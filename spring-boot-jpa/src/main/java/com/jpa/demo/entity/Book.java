package com.jpa.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author", insertable = true, updatable = true)
    private List<Author> author;

    @OneToOne
    @JoinColumn(name = "id_publising_company", insertable = true, updatable = true)
    private PublishingCompany publishingCompany;

}
