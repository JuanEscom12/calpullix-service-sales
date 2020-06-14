package com.calpullix.service.sales.repository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.calpullix.service.sales.model.SalesResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProcedureInvoker {
	
	private final EntityManager entityManager;

	@Autowired
	public ProcedureInvoker(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Async
	public CompletableFuture<Boolean> executeSalesInformation(
			SalesResponseDTO result,
			String nameProcedure,
			Integer idBranch, 
			Integer idProduct, 
			String initDate, 
			String endDate) {
    	log.info(":: Executing Procedure Sales {} ", nameProcedure);
 
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("idBranch", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("idProduct", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("initDate", String.class, ParameterMode.IN);   
        storedProcedureQuery.registerStoredProcedureParameter("endDate", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("salesAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("purchasesAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("losesAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("caducousAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("shelfPurchaseAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("shelfSaleAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("cellarPurchaseAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("cellarSaleAmount", BigDecimal.class, ParameterMode.INOUT);
     
        storedProcedureQuery.setParameter("idBranch", idBranch);
        storedProcedureQuery.setParameter("idProduct", idProduct);
        storedProcedureQuery.setParameter("initDate", initDate);
        storedProcedureQuery.setParameter("endDate", endDate);      
        storedProcedureQuery.setParameter("salesAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("purchasesAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("losesAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("caducousAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("shelfPurchaseAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("shelfSaleAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("cellarPurchaseAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("cellarSaleAmount", BigDecimal.ZERO);
        storedProcedureQuery.execute();

        final BigDecimal salesAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("salesAmount");
        final BigDecimal purchasesAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("purchasesAmount");
        final BigDecimal losesAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("losesAmount");  
        final BigDecimal caducousAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("caducousAmount");      
        final BigDecimal shelfPurchaseAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("shelfPurchaseAmount");    
        final BigDecimal shelfSaleAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("shelfSaleAmount");       
        final BigDecimal cellarPurchaseAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("cellarPurchaseAmount");      
        final BigDecimal cellarSaleAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("cellarSaleAmount");      
     
        result.setTotalAmountSoldOut(salesAmount == null ? BigDecimal.ZERO : salesAmount);
        result.setTotalAmountSupplies(purchasesAmount == null ? BigDecimal.ZERO : purchasesAmount);
        result.setTotalAmountLosses(losesAmount == null ? BigDecimal.ZERO : losesAmount);
        result.setTotalAmmountCaducousPurchasePrice(caducousAmount == null ? BigDecimal.ZERO : caducousAmount);
        log.info(":: Sales information {} {} {} {} ", salesAmount, purchasesAmount, losesAmount, caducousAmount);
        result.setTotalAmmountStoredPurchasePrice(cellarPurchaseAmount == null ? BigDecimal.ZERO : cellarPurchaseAmount);
        result.setTotalAmmountStoredSalePrice(cellarSaleAmount == null ? BigDecimal.ZERO : cellarSaleAmount);
        result.setTotalAmmountSideboardPurchasePrice(shelfPurchaseAmount == null ? BigDecimal.ZERO : shelfPurchaseAmount);
        result.setTotalAmmountSideboardSalePrice(shelfSaleAmount == null ? BigDecimal.ZERO : shelfSaleAmount);
        result.setTotalAmonuntSales(result.getTotalAmountSoldOut().subtract(result.getTotalAmountSupplies(), MathContext.DECIMAL128)
        		.subtract(result.getTotalAmountLosses(), MathContext.DECIMAL128).subtract(
        				result.getTotalAmmountCaducousPurchasePrice(), MathContext.DECIMAL128)
        		.setScale(2, RoundingMode.HALF_UP));
        
        return CompletableFuture.completedFuture(Boolean.TRUE);
    }

}
