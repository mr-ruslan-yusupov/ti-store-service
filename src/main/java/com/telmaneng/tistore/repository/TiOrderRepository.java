package com.telmaneng.tistore.repository;

import com.telmaneng.tistore.pojo.TiPartOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiOrderRepository extends JpaRepository<TiPartOrder, Integer> {
    @Query(value = "select distinct tiPartOrderTbl.customerEmail from ti_part_order_tbl tiPartOrderTbl where tiPartOrderTbl.isInStock = 0", nativeQuery = true)
    List<String> getAllCustomerEmailsWithActiveOrders();

    @Query(value = "select tiPartOrderTbl.tiPartNumber from ti_part_order_tbl tiPartOrderTbl where tiPartOrderTbl.customerEmail = :customerEmail and tiPartOrderTbl.isInStock = 0", nativeQuery = true)
    List<String> getTiPartNumbersNotInStockByCustomerEmail(@Param("customerEmail") String customerEmail);
    @Query(value = "select distinct tiPartOrderTbl.customerName from ti_part_order_tbl tiPartOrderTbl where tiPartOrderTbl.customerEmail = :customerEmail", nativeQuery = true)
    String getCustomerNameByCustomerEmail(String customerEmail);
}
