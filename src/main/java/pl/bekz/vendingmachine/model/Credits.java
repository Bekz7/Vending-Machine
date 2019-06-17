package pl.bekz.vendingmachine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credits {

    private Money credits;
}
