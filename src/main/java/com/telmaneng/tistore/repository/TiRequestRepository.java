package com.telmaneng.tistore.repository;

import com.telmaneng.tistore.pojo.TiPartRequest;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TiRequestRepository extends JpaRepository<TiPartRequest, Integer> {
}
