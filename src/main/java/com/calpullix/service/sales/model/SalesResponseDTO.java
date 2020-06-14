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
public class SalesResponseDTO {

	private Integer totalAmountItemsSoldOut;
	
	private BigDecimal totalAmountSoldOut;
	
	private BigDecimal totalAmonuntSales;
	
	private BigDecimal totalAmountSupplies;
	
	private BigDecimal totalAmountLosses;
	
	private BigDecimal totalAmmountCaducousPurchasePrice;
	
	private BigDecimal totalAmmountCaducousSalePrice;
	
	private BigDecimal totalAmmountStolePurchasePrice;
	
	private BigDecimal totalAmmountStoleSalePrice;
	
	private BigDecimal totalAmmountStoredPurchasePrice;
	
	private BigDecimal totalAmmountStoredSalePrice;
	
	private BigDecimal totalAmmountSideboardPurchasePrice;
	
	private BigDecimal totalAmmountSideboardSalePrice;
	
	private String suffix;
	
	private String suffixBest;
	
	private String suffixWorst;
	
	private List<SalesPeriodResponseDTO> monthlySales;
	
	private List<SalesPeriodResponseDTO> yearlySales;
	
	private List<Integer> numberItems;
	
	private List<Integer> numberItemsWorst;
	
	private List<SalesPeriodResponseDTO> bestProduct;
	
	private List<SalesPeriodResponseDTO> worstProduct;
		
}
