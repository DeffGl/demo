package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Entity
@Table(name = "transaction", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Transaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_of_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreate;

    @Column(name = "sum")
    private double sum;

    @Column(name = "type")
    private String type;

    @ManyToOne
    private ClientAccount account;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private CashOrder order;

    @ManyToOne
    private ClientAccount outgoingAccount;

    @Column(name = "result")
    private boolean result;

}
