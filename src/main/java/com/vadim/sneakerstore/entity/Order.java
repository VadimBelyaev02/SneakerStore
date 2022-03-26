package com.vadim.sneakerstore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "ordered_date")
    private LocalDate orderedDate;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    private Product product;

    @Column(name = "payment")
    private String payment;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "groupId")
    private UUID groupId;
    /*
        private UUID id;

    @Schema(description = "When ordered")
    @NotBlank
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDate date;

    @Schema(description = "Order status")
    @NotBlank
    private String status;

    @Schema(description = "Who ordered email")
    @NotBlank
    private String customerEmail;

    private String payment;

    private UUID productId;

    private Integer amount;

    private UUID groupId;
     */

//    @OneToMany
//    private List<Product> products;


    //   @OneToMany
  //  private List<Size> sizes;
}
