package org.beru.pizzeria.web.controller;

import java.util.List;

import org.beru.pizzeria.persistence.entity.PizzaEntity;
import org.beru.pizzeria.service.PizzaService;
import org.beru.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping()
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int elements){{
        return ResponseEntity.ok(this.pizzaService.getAll(page, elements));
    }}
    @GetMapping("/{id}")
    public ResponseEntity<PizzaEntity> getById(@PathVariable int id){{
        return ResponseEntity.ok(this.pizzaService.getById(id));
    }}
    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getByAvailable(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int elements,
                                                            @RequestParam(defaultValue = "price") String sortBy,
                                                            @RequestParam(defaultValue = "ASC") String direction){{
        return ResponseEntity.ok(this.pizzaService.getByAvailable(page, elements, sortBy, direction));
    }}
    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name){{
        PizzaEntity pizza = this.pizzaService.getByName(name);
        return ResponseEntity.ok(pizza);
    }}
    @GetMapping("/description/{description}")
    public ResponseEntity<List<PizzaEntity>> getByDescription(@PathVariable String description){{
        return ResponseEntity.ok(this.pizzaService.getByDescription(description));
    }}
    @GetMapping("/description/without/{description}")
    public ResponseEntity<List<PizzaEntity>> getWithoutDescription(@PathVariable String description){{
        return ResponseEntity.ok(this.pizzaService.getWithoutDescription(description));
    }}
    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable double price){{
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }}
    @PostMapping
    public ResponseEntity<PizzaEntity> save(@RequestBody PizzaEntity pizza){
        if(pizza.getId() == null || !this.pizzaService.exists(pizza.getId()))
            return ResponseEntity.ok(this.pizzaService.save(pizza));

        return ResponseEntity.badRequest().build();
    }
    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza){
        if(pizza.getId() == null || this.pizzaService.exists(pizza.getId()))
            return ResponseEntity.ok(this.pizzaService.save(pizza));

        return ResponseEntity.badRequest().build();
    }
    @PutMapping("/price")
    public ResponseEntity<PizzaEntity> updatePrice(@RequestBody UpdatePizzaPriceDto dto){
        if(this.pizzaService.exists(dto.getPizzaId())) {
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        return ResponseEntity.ok(this.pizzaService.deleteById(id));
    }
}
