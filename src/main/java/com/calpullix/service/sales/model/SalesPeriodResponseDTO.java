package com.calpullix.service.sales.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SalesPeriodResponseDTO {
	
	private BigDecimal amount;

	private String name;
	
	private List<BigDecimal> ammountIncome;
	
	private List<Integer> numberItems;
	
}
