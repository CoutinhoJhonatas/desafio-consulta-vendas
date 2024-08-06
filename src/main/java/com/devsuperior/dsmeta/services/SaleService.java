package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.TotalSellerSalesDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<TotalSellerSalesDTO> summarySellerSale(String minDate, String maxDate) {
		LocalDate finalDate = verifyMaxDate(maxDate);
		LocalDate initalDate = verifyMinDate(minDate, finalDate);

		return repository.searchTotalSales(initalDate, finalDate);
	}

	public Page<SalesReportDTO> salesReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate finalDate = verifyMaxDate(maxDate);
		LocalDate initalDate = verifyMinDate(minDate, finalDate);

		if(name == null) {
			name = "";
		}

		return repository.searchSalesReport(initalDate, finalDate, name, pageable);
	}

	private LocalDate verifyMaxDate(String maxDate) {
		LocalDate finalDate;
		if(maxDate == null) {
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finalDate = LocalDate.parse(maxDate);
		}
		return finalDate;
	}
	
	private LocalDate verifyMinDate(String minDate, LocalDate finalDate) {
		LocalDate initalDate;
		if(minDate == null) {
			initalDate = finalDate.minusYears(1L);
		} else {
			initalDate = LocalDate.parse(minDate);
		}
		return initalDate;
	}
	
	
}
