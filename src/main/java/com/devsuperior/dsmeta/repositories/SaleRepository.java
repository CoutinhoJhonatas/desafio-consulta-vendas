package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.TotalSellerSalesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.TotalSellerSalesDTO(s.seller.name, SUM(s.amount)) " +
            "FROM Sale s " +
            "WHERE s.date >= :initialDate AND s.date <= :finalDate " +
            "GROUP BY s.seller.name")
    List<TotalSellerSalesDTO> searchTotalSales(LocalDate initialDate, LocalDate finalDate);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SalesReportDTO(s.id, s.date, s.amount, s.seller.name) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :initialDate AND :finalDate " +
            "AND UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<SalesReportDTO> searchSalesReport(LocalDate initialDate, LocalDate finalDate, String name, Pageable pageable);

}
