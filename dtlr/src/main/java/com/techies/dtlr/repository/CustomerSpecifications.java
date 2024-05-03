package com.techies.dtlr.repository;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.techies.dtlr.entity.Customer;

public interface CustomerSpecifications {

    static Specification<Customer> searchCustomers(Customer customer) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = null;
            if (customer.getId() != null) {
                predicate = criteriaBuilder.equal(root.get("id"), customer.getId());
            }
            if (customer.getCif() != null) {
                Predicate cifPredicate = criteriaBuilder.equal(root.get("cif"), customer.getCif());
                // Combine with previous predicate if exists
                predicate = (predicate != null) ? criteriaBuilder.and(predicate, cifPredicate) : cifPredicate;
            }
            // Repeat this for other columns as needed
            return predicate;
        };
    }
}
