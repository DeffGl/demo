package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Entity
@Table(name = "order", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CashOrder {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

    @Column(name = "sum")
    private double sum;

    @ManyToOne
    private ClientAccount account;

    @Column(name = "result")
    private boolean result;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreate;

    @OneToOne(mappedBy = "order")
    @JsonIgnore
    @ToString.Exclude
    private Transaction transaction;
}
