package org.beru.pizzeria.persistence.audit;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.beru.pizzeria.persistence.entity.PizzaEntity;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {
    private PizzaEntity currentValue;
    @PostLoad
    public void postLoad(PizzaEntity pizza){
        System.out.println("Post Load");
        this.currentValue = SerializationUtils.clone(pizza);
    }
    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity pizza){
        System.out.println("Post persist update");
        System.out.println("Old value: " + currentValue);
        System.out.println("New Value: " + pizza.toString());
    }
    @PreRemove
    public void onPreDelete(PizzaEntity pizza){
        System.out.println(pizza.toString());
    }
}
