package org.beru.pizzeria.service;

import java.util.List;

import org.beru.pizzeria.persistence.entity.PizzaEntity;
import org.beru.pizzeria.persistence.repository.PizzaPagSortRepository;
import org.beru.pizzeria.persistence.repository.PizzaRepository;
import org.beru.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.beru.pizzeria.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository){
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public Page<PizzaEntity> getAll(int page, int elements){
        Pageable pageRequest = PageRequest.of(page, elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }
    public boolean exists(int id){
        return (getById(id)!=null);
    }
    public PizzaEntity getById(int id){
        return this.pizzaRepository.findById(id).orElse(null);
    }
    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }
    public boolean deleteById(int id){
        this.pizzaRepository.deleteById(id);
        return (getById(id) == null);
    }
    public Page<PizzaEntity> getByAvailable(int page, int elements, String sortBy, String direction){
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        System.out.println(this.pizzaRepository.countByVeganTrue());
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }
    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElse(null);
    }
    public List<PizzaEntity> getCheapest(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }
    public List<PizzaEntity> getByDescription(String description){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContains(description);
    }
    public List<PizzaEntity> getWithoutDescription(String description){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContains(description);
    }
    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice(UpdatePizzaPriceDto updatePizzaPriceDto){
        this.pizzaRepository.updatePrice(updatePizzaPriceDto);
        this.sendEmail();
    }
    private void sendEmail(){
        throw new EmailApiException();
    }
}
