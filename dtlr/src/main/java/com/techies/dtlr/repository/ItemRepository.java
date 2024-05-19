package com.techies.dtlr.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.techies.dtlr.entity.Items;

public interface ItemRepository extends JpaRepository<Items, Long>{

}
