package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account", schema = "public")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ClientAccount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Client client;

    @Column(name = "number")
    private String number;

    @Column(name = "sum")
    private double sum;

    @Column(name = "type")
    private String type;

    @Column(name = "date_of_opening")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfOpening;

    @Column(name = "duration_of_years")
    private int duration_of_years;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private List<Transaction> transactions;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private List<CashOrder> order;

    @OneToMany
    @JoinColumn(name = "outgoing_account_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private List<Transaction> transactionsOutgoingAccount;
}
