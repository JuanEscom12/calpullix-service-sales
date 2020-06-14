package com.calpullix.service.sales.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.calpullix.service.sales.model.SalesPeriodResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ProcedurePeriodInvoker {

	private static final String FEBRUARY = "02";
	
	private final EntityManager entityManager;

	@Autowired
	public ProcedurePeriodInvoker(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Async
	public CompletableFuture<List<SalesPeriodResponseDTO>> executeSalesInformationPeriod(
			String nameProcedure,
			Integer idBranch, 
			Integer idProduct, 
			Integer year) {
    	log.info(":: Executing Procedure Period {} ", nameProcedure);
    	
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("idBranch", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("idProduct", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("year", Integer.class, ParameterMode.IN);   
        storedProcedureQuery.registerStoredProcedureParameter("januaryAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("februaryAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("marchAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("aprilAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("mayAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("juneAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("julyAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("augustAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("septemberAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("octoberAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("novemberAmount", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("decemberAmount", BigDecimal.class, ParameterMode.INOUT);
     
        storedProcedureQuery.setParameter("idBranch", idBranch);
        storedProcedureQuery.setParameter("idProduct", idProduct);
        storedProcedureQuery.setParameter("year", year);  
        storedProcedureQuery.setParameter("januaryAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("februaryAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("marchAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("aprilAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("mayAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("juneAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("julyAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("augustAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("septemberAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("octoberAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("novemberAmount", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("decemberAmount", BigDecimal.ZERO);
        
        storedProcedureQuery.execute();
        
        final BigDecimal januaryAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("januaryAmount");
        final BigDecimal februaryAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("februaryAmount");
        final BigDecimal marchAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("marchAmount");  
        final BigDecimal aprilAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("aprilAmount");      
        final BigDecimal mayAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("mayAmount");    
        final BigDecimal juneAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("juneAmount");       
        final BigDecimal julyAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("julyAmount");      
        final BigDecimal augustAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("augustAmount");      
        final BigDecimal septemberAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("septemberAmount");    
        final BigDecimal octoberAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("octoberAmount");       
        final BigDecimal novemberAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("novemberAmount");      
        final BigDecimal decemberAmount = (BigDecimal) storedProcedureQuery.getOutputParameterValue("decemberAmount");      

        
        final List<SalesPeriodResponseDTO> result = new ArrayList<>();
        SalesPeriodResponseDTO salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(januaryAmount == null ? BigDecimal.ZERO : januaryAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(februaryAmount == null ? BigDecimal.ZERO : februaryAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(marchAmount == null ? BigDecimal.ZERO : marchAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(aprilAmount == null ? BigDecimal.ZERO : aprilAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(mayAmount == null ? BigDecimal.ZERO : mayAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(juneAmount == null ? BigDecimal.ZERO : juneAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(julyAmount == null ? BigDecimal.ZERO : julyAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(augustAmount == null ? BigDecimal.ZERO : augustAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(septemberAmount == null ? BigDecimal.ZERO : septemberAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(octoberAmount == null ? BigDecimal.ZERO : octoberAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(novemberAmount == null ? BigDecimal.ZERO : novemberAmount);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(decemberAmount == null ? BigDecimal.ZERO : decemberAmount);
        result.add(salesPeriod);
        
       return CompletableFuture.completedFuture(result);
    }

	@Async
	public CompletableFuture<List<SalesPeriodResponseDTO>> executeSalesInformationMonthlyPeriod(
			String nameProcedure,
			String month,
			Integer idBranch, 
			Integer idProduct, 
			Integer year) {
    	log.info(":: Executing Procedure Period {} ", nameProcedure);
		final StoredProcedureQuery storedProcedureQuery = entityManager.
				createStoredProcedureQuery(nameProcedure);
        storedProcedureQuery.registerStoredProcedureParameter("idBranch", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("idProduct", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("year", Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("month", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("firstWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("secondWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("thirdWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("fourthWeek", BigDecimal.class, ParameterMode.INOUT);
        storedProcedureQuery.registerStoredProcedureParameter("fifthWeek", BigDecimal.class, ParameterMode.INOUT);
        
     
        storedProcedureQuery.setParameter("idBranch", idBranch);
        storedProcedureQuery.setParameter("idProduct", idProduct);
        storedProcedureQuery.setParameter("year", year);  
        storedProcedureQuery.setParameter("month", month);  
        storedProcedureQuery.setParameter("firstWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("secondWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("thirdWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("fourthWeek", BigDecimal.ZERO);
        storedProcedureQuery.setParameter("fifthWeek", BigDecimal.ZERO);
        storedProcedureQuery.execute();
        
        final BigDecimal firstWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("firstWeek");
        final BigDecimal secondWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("secondWeek");
        final BigDecimal thirdWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("thirdWeek");  
        final BigDecimal fourthWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("fourthWeek");
        final BigDecimal fifthWeek;
        if (BooleanUtils.negate(month.equals(FEBRUARY))) {
        	fifthWeek = (BigDecimal) storedProcedureQuery.getOutputParameterValue("fifthWeek");
        } else {
        	fifthWeek = BigDecimal.ZERO;
        }
        log.info(":: Weeks {} {} {} {} ", firstWeek, secondWeek, thirdWeek, fourthWeek);
        
        final List<SalesPeriodResponseDTO> result = new ArrayList<>();
        SalesPeriodResponseDTO salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(firstWeek == null ? BigDecimal.ZERO : firstWeek);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(secondWeek == null ? BigDecimal.ZERO : secondWeek);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(thirdWeek == null ? BigDecimal.ZERO : thirdWeek);
        result.add(salesPeriod);
        salesPeriod = new SalesPeriodResponseDTO();
        salesPeriod.setAmount(fourthWeek == null ? BigDecimal.ZERO : fourthWeek);
        result.add(salesPeriod);
        
        if (BooleanUtils.negate(month.equals(FEBRUARY))) {
        	salesPeriod = new SalesPeriodResponseDTO();
        	salesPeriod.setAmount(fifthWeek == null ? BigDecimal.ZERO :  fifthWeek);
            result.add(salesPeriod);
        }  
        return CompletableFuture.completedFuture(result);
    }



}
