package com.Snowhite.repository;

import com.Snowhite.domain.GetInventoriesRequest;
import com.Snowhite.domain.Inventory;
import com.Snowhite.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InventorySpesificationImpl implements Specification<Inventory> {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Specification<Inventory> and(Specification<Inventory> other) {
        return null;
    }

    @Override
    public Specification<Inventory> or(Specification<Inventory> other) {
        return null;
    }


    public List<Inventory> findByCriteria(Status status, Date startDate, Date endDate, Pageable pageable){
        return inventoryRepository.findAll(new Specification<Inventory>()  {

            @Override
            public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(status != null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), status)));
                }

                 if(startDate != null && endDate != null) {
                     predicates.add(criteriaBuilder.between(root.get("updateDate"), startDate, endDate));
                 }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
