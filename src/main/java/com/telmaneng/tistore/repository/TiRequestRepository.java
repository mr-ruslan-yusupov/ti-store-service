package com.telmaneng.tistore.repository;

import com.telmaneng.tistore.pojo.TiPartOrder;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TiRequestRepository extends JpaRepository<TiPartOrder, Integer> {
    List<String> findDistinctByCustomerEmail(String customerEmail);
}
