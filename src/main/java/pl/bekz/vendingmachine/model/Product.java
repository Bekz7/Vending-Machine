package pl.bekz.vendingmachine.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "product")
@Data
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Integer amount;
  private BigDecimal price;

}
