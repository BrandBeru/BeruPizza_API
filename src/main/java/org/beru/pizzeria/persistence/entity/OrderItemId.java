package org.beru.pizzeria.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable{
    private Integer id;
    private Integer idItem;

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof OrderItemId that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(idItem, that.idItem);
    }
    @Override
    public int hashCode(){
        return Objects.hash(id, idItem);
    }
}
