package com.vadim.sneakerstore.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sizes")
public class Size {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "size")
    private Integer size;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
//    @JoinTable(name = "products_sizes",
//            joinColumns = @JoinColumn(name = "size_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
//    @Cascade({org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Product product;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Size size = (Size) o;
        return id != null && Objects.equals(id, size.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
