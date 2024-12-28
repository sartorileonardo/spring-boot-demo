package com.jpa.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author", insertable = true, updatable = true)
    private List<Author> authors;

    @OneToOne
    @JoinColumn(name = "id_publising_company", insertable = true, updatable = true)
    private PublishingCompany publishingCompany;

}
