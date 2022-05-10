package com.example.jpashop.repository;

import com.example.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Item item) {
        try{
            if (item.getId() == null) {
                em.persist(item);
            } else {
                em.merge(item);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }
}