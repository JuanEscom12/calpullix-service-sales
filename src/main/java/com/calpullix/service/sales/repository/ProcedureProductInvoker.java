package com.calpullix.service.sales.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.calpullix.db.process.product.model.Product;
import com.calpullix.service.sales.model.SalesPeriodResponseDTO;
import com.calpullix.service.sales.model.SalesResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcedureProductInvoker {

	private final EntityManager entityManager;

	@Autowired
	public ProcedureProductInvoker(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Async
	public CompletableFuture<SalesResponseDTO> executeSalesProductYearlyInformation(
			String nameProcedure,
			Integer idBranch,
			Product idProduct,
			Integer year) {
    	log.info(":: Executing Procedure Product Yearly {} ", nameProcedure);

		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("idBranch", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("year", Integer.class, ParameterMode.IN);   
        storedProcedureQuery.registerStoredProcedureParameter("idProduct", Integer.class, ParameterMode.IN);
        
        storedProcedureQuery.registerStoredProcedureParameter("countProductJanuary", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductFebruary", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductMarch", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductApril", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductMay", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductJune", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductJuly", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductAugust", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductSeptember", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductOctober", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductNovember", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductDecember", Integer.class, ParameterMode.INOUT);
      
        storedProcedureQuery.registerStoredProcedureParameter("amountProductJanuary", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductFebruary", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductMarch", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductApril", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductMay", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductJune", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductJuly", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductAugust", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductSeptember", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductOctober", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductNovember", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductDecember", BigDecimal.class, ParameterMode.INOUT);
        
        
        storedProcedureQuery.setParameter("idBranch", idBranch);
        storedProcedureQuery.setParameter("year", year);
        storedProcedureQuery.setParameter("idProduct", idProduct.getId());
        storedProcedureQuery.setParameter("countProductJanuary", 0);
        storedProcedureQuery.setParameter("countProductFebruary", 0);
        storedProcedureQuery.setParameter("countProductMarch", 0);
        storedProcedureQuery.setParameter("countProductApril", 0);
        storedProcedureQuery.setParameter("countProductMay", 0);
        storedProcedureQuery.setParameter("countProductJune", 0);
        storedProcedureQuery.setParameter("countProductJuly", 0);
        storedProcedureQuery.setParameter("countProductAugust", 0);
        storedProcedureQuery.setParameter("countProductSeptember", 0);
        storedProcedureQuery.setParameter("countProductOctober", 0);
        storedProcedureQuery.setParameter("countProductNovember", 0);
        storedProcedureQuery.setParameter("countProductDecember", 0);
        storedProcedureQuery.setParameter("amountProductJanuary", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductFebruary", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductMarch", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductApril", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductMay", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductJune", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductJuly", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductAugust", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductSeptember", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductOctober", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductNovember", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductDecember", BigDecimal.ZERO);
        storedProcedureQuery.execute();
         
        final Integer countProductJanuary = (Integer) storedProcedureQuery.getOutputParameterValue("countProductJanuary");
        final Integer countProductFebruary = (Integer) storedProcedureQuery.getOutputParameterValue("countProductFebruary");
        final Integer countProductMarch = (Integer) storedProcedureQuery.getOutputParameterValue("countProductMarch");
        final Integer countProductApril = (Integer) storedProcedureQuery.getOutputParameterValue("countProductApril");
        final Integer countProductMay = (Integer) storedProcedureQuery.getOutputParameterValue("countProductMay");
        final Integer countProductJune = (Integer) storedProcedureQuery.getOutputParameterValue("countProductJune");
        final Integer countProductJuly = (Integer) storedProcedureQuery.getOutputParameterValue("countProductJuly");
        final Integer countProductAugust = (Integer) storedProcedureQuery.getOutputParameterValue("countProductAugust");
        final Integer countProductSeptember = (Integer) storedProcedureQuery.getOutputParameterValue("countProductSeptember");
        final Integer countProductOctober = (Integer) storedProcedureQuery.getOutputParameterValue("countProductOctober");
        final Integer countProductNovember = (Integer) storedProcedureQuery.getOutputParameterValue("countProductNovember");
        final Integer countProductDecember = (Integer) storedProcedureQuery.getOutputParameterValue("countProductDecember");
 
        final BigDecimal amountProductJanuary = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductJanuary");
        final BigDecimal amountProductFebruary = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductFebruary");
        final BigDecimal amountProductMarch = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductMarch");
        final BigDecimal amountProductApril = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductApril");
        final BigDecimal amountProductMay = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductMay");
        final BigDecimal amountProductJune = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductJune");
        final BigDecimal amountProductJuly = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductJuly");
        final BigDecimal amountProductAugust = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductAugust");
        final BigDecimal amountProductSeptember = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductSeptember");
        final BigDecimal amountProductOctober = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductOctober");
        final BigDecimal amountProductNovember = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductNovember");
        final BigDecimal amountProductDecember = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductDecember");
       
        SalesResponseDTO result = new SalesResponseDTO();
        final List<SalesPeriodResponseDTO> sales = new ArrayList<>();
        List<Integer> numberItems = new ArrayList<>();
        List<BigDecimal> amount = new ArrayList<>();
        SalesPeriodResponseDTO item = new SalesPeriodResponseDTO();
        item.setName(idProduct.getName());
        sales.add(item);
       
        amount.add(amountProductJanuary == null ? BigDecimal.ZERO : amountProductJanuary);
        amount.add(amountProductFebruary == null ? BigDecimal.ZERO : amountProductFebruary);
        amount.add(amountProductMarch == null ? BigDecimal.ZERO : amountProductMarch);
        amount.add(amountProductApril == null ? BigDecimal.ZERO : amountProductApril);
        amount.add(amountProductMay == null ? BigDecimal.ZERO : amountProductMay);
        amount.add(amountProductJune == null ? BigDecimal.ZERO : amountProductJune);
        amount.add(amountProductJuly == null ? BigDecimal.ZERO : amountProductJuly);
        amount.add(amountProductAugust == null ? BigDecimal.ZERO : amountProductAugust);
        amount.add(amountProductSeptember == null ? BigDecimal.ZERO : amountProductSeptember);
        amount.add(amountProductOctober == null ? BigDecimal.ZERO : amountProductOctober);
        amount.add(amountProductNovember == null ? BigDecimal.ZERO : amountProductNovember);
        amount.add(amountProductDecember == null ? BigDecimal.ZERO : amountProductDecember);
        numberItems.add(countProductJanuary == null ? 0 : countProductJanuary);
        numberItems.add(countProductFebruary == null ? 0 : countProductFebruary);
        numberItems.add(countProductMarch == null ? 0 : countProductMarch);
        numberItems.add(countProductApril == null ? 0 : countProductApril);
        numberItems.add(countProductMay == null ? 0 : countProductMay);
        numberItems.add(countProductJune == null ? 0 : countProductJune);
        numberItems.add(countProductJuly == null ? 0 : countProductJuly);
        numberItems.add(countProductAugust == null ? 0 : countProductAugust);
        numberItems.add(countProductSeptember == null ? 0 : countProductSeptember);
        numberItems.add(countProductOctober == null ? 0 : countProductOctober);
        numberItems.add(countProductNovember == null ? 0 : countProductNovember);
        numberItems.add(countProductDecember == null ? 0 :countProductDecember );
        
        item.setAmmountIncome(amount);
        result.setNumberItems(numberItems);
		result.setBestProduct(sales);
        return CompletableFuture.completedFuture(result);
    }

	@Async
	public CompletableFuture<SalesResponseDTO> executeSalesProductMonthlyInformation(
			String nameProcedure,
			Integer idBranch,
			Product idProduct,
			Integer year,
			Integer month) {
    	log.info(":: Executing Procedure Product Monthly {} ", nameProcedure);
 
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("idBranch", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("year", Integer.class, ParameterMode.IN);   
        storedProcedureQuery.registerStoredProcedureParameter("month", Integer.class, ParameterMode.IN);   
        storedProcedureQuery.registerStoredProcedureParameter("idProduct", Integer.class, ParameterMode.IN);
        
        storedProcedureQuery.registerStoredProcedureParameter("countProductFirstWeek", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductSecondWeek", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductThirdWeek", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductFourthWeek", Integer.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("countProductFifthWeek", Integer.class, ParameterMode.INOUT);
      
        storedProcedureQuery.registerStoredProcedureParameter("amountProductFirstWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductSecondWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductThirdWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductFourthWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("amountProductFifthWeek", BigDecimal.class, ParameterMode.INOUT);
        
        storedProcedureQuery.setParameter("idBranch", idBranch);
        storedProcedureQuery.setParameter("year", year);
        storedProcedureQuery.setParameter("month", month);
        storedProcedureQuery.setParameter("idProduct", idProduct.getId());
        storedProcedureQuery.setParameter("countProductFirstWeek", 0);
        storedProcedureQuery.setParameter("countProductSecondWeek", 0);
        storedProcedureQuery.setParameter("countProductThirdWeek", 0);
        storedProcedureQuery.setParameter("countProductFourthWeek", 0);
        storedProcedureQuery.setParameter("countProductFifthWeek", 0);
       
        storedProcedureQuery.setParameter("amountProductFirstWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductSecondWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductThirdWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductFourthWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("amountProductFifthWeek", BigDecimal.ZERO);
        storedProcedureQuery.execute();
         
        final Integer countProductFirstWeek = (Integer) storedProcedureQuery.getOutputParameterValue("countProductFirstWeek");
        final Integer countProductSecondWeek = (Integer) storedProcedureQuery.getOutputParameterValue("countProductSecondWeek");
        final Integer countProductThirdWeek = (Integer) storedProcedureQuery.getOutputParameterValue("countProductThirdWeek");
        final Integer countProductFourthWeek = (Integer) storedProcedureQuery.getOutputParameterValue("countProductFourthWeek");
        final Integer countProductFifthWeek = (Integer) storedProcedureQuery.getOutputParameterValue("countProductFifthWeek");
      
        final BigDecimal amountProductFirstWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductFirstWeek");
        final BigDecimal amountProductSecondWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductSecondWeek");
        final BigDecimal amountProductThirdWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductThirdWeek");
        final BigDecimal amountProductFourthWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductFourthWeek");
        final BigDecimal amountProductFifthWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("amountProductFifthWeek");
       
       
        SalesResponseDTO result = new SalesResponseDTO();
        final List<SalesPeriodResponseDTO> sales = new ArrayList<>();
        List<Integer> numberItems = new ArrayList<>();
        List<BigDecimal> amount = new ArrayList<>();
        SalesPeriodResponseDTO item = new SalesPeriodResponseDTO();
        item.setName(idProduct.getName());
        sales.add(item);
       
        amount.add(amountProductFirstWeek == null ? BigDecimal.ZERO : amountProductFirstWeek);
        amount.add(amountProductSecondWeek == null ? BigDecimal.ZERO : amountProductSecondWeek);
        amount.add(amountProductThirdWeek == null ? BigDecimal.ZERO : amountProductThirdWeek);
        amount.add(amountProductFourthWeek == null ? BigDecimal.ZERO : amountProductFourthWeek);
        amount.add(amountProductFifthWeek == null ? BigDecimal.ZERO : amountProductFifthWeek);
        
        numberItems.add(countProductFirstWeek == null  ? 0 : countProductFirstWeek);
        numberItems.add(countProductSecondWeek == null  ? 0 : countProductSecondWeek);
        numberItems.add(countProductThirdWeek == null  ? 0 : countProductThirdWeek);
        numberItems.add(countProductFourthWeek == null  ? 0 : countProductFourthWeek);
        numberItems.add(countProductFifthWeek == null  ? 0 : countProductFifthWeek);
       
        
        item.setAmmountIncome(amount);
        result.setNumberItems(numberItems);
		result.setBestProduct(sales);
        return CompletableFuture.completedFuture(result);
    }


}
