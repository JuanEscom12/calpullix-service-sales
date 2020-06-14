package com.calpullix.service.sales.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.calpullix.db.process.branch.model.Branch;
import com.calpullix.db.process.catalog.model.ProductBranchStatus;
import com.calpullix.db.process.product.model.Product;
import com.calpullix.db.process.product.repository.ProductRepository;
import com.calpullix.service.sales.model.SalesPeriodResponseDTO;
import com.calpullix.service.sales.model.SalesRequestDTO;
import com.calpullix.service.sales.model.SalesResponseDTO;
import com.calpullix.service.sales.repository.ProcedureInvoker;
import com.calpullix.service.sales.repository.ProcedurePeriodInvoker;
import com.calpullix.service.sales.repository.ProcedureProductInvoker;
import com.calpullix.service.sales.service.SalesService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SalesServiceImpl implements SalesService {

	private static final String SALES_INFORMATION_PROCEDURE = "calpullix_sales_information";

	private static final String SALES_INFORMATION_PERIOD_PROCEDURE = "calpullix_sales_information_period";

	private static final String SALES_INFORMATION_MONTHLY_PERIOD_PROCEDURE = "calpullix_sales_information_monthly_period";

	private static final String SALES_PRODUCT_YEARLY_PROCEDURE = "calpullix_sales_information_number_product_yearly";

	private static final String SALES_PRODUCT_MONTHLY_PROCEDURE = "calpullix_sales_information_number_product_monthly";

	private static final String PADDING_LEFT = "0";

	private static final String FIRST_DAY = "01";

	private static final String LAST_DAY = "30";

	private static final String LAST_DAY_LONG = "31";

	private static final String DASH = "-";

	private static final int ONE = 1;

	@Autowired
	private ProcedureInvoker procedureInvoker;

	@Autowired
	private ProcedurePeriodInvoker procedurePeriodInvoker;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProcedureProductInvoker procedureProductInvoker;

	@Override
	public SalesResponseDTO getSales(SalesRequestDTO request) {
		log.info(":: Get Sales Service {} ", request);
		final SalesResponseDTO result = new SalesResponseDTO();
		StringBuilder initDate = new StringBuilder();
		StringBuilder endDate = new StringBuilder();
		setDateProcedure(request, initDate, endDate);
		log.info(":: Dates SP {} {} ", initDate, endDate);
		Integer idProduct;
		if (StringUtils.isEmpty(request.getProduct())) {
			idProduct = null;
		} else {
			final StringTokenizer token = new StringTokenizer(request.getProduct(), " ");
			idProduct = Integer.valueOf(token.nextToken());
		}
		final CompletableFuture<Boolean> salesInformation = procedureInvoker.executeSalesInformation(result,
				SALES_INFORMATION_PROCEDURE, request.getIdBranch(), idProduct, initDate.toString(),
				endDate.toString());

		CompletableFuture<List<SalesPeriodResponseDTO>> salesInformationPeriod;
		if (BooleanUtils.negate(request.getMonth() == null)) {
			salesInformationPeriod = procedurePeriodInvoker.executeSalesInformationMonthlyPeriod(
					SALES_INFORMATION_MONTHLY_PERIOD_PROCEDURE,
					StringUtils.leftPad(request.getMonth().toString(), 2, PADDING_LEFT), request.getIdBranch(),
					idProduct, request.getYear());
		} else {
			salesInformationPeriod = procedurePeriodInvoker.executeSalesInformationPeriod(
					SALES_INFORMATION_PERIOD_PROCEDURE, request.getIdBranch(), idProduct, request.getYear());
		}
		final Branch idBranch = new Branch();
		idBranch.setId(request.getIdBranch());
		Pageable topFiveProductsLimit = PageRequest.of(0, 5);

		CompletableFuture<List<Product>> topProduct = null;
		CompletableFuture<List<Product>> topDownProduct = null;
		if (StringUtils.isEmpty(request.getProduct())) {
			topProduct = productRepository.getProductTopFiveBySaledateAndIdbranchAndStatus(initDate.toString(),
					endDate.toString(), idBranch, ProductBranchStatus.SOLD.getId(), topFiveProductsLimit);
			topDownProduct = productRepository.getProductTopDownFiveBySaledateAndIdbranchAndStatus(initDate.toString(),
					endDate.toString(), idBranch, ProductBranchStatus.SOLD.getId(), topFiveProductsLimit);
			CompletableFuture.allOf(salesInformation, salesInformationPeriod, topProduct, topDownProduct);

		} else {
			CompletableFuture.allOf(salesInformation, salesInformationPeriod);
		}
		try {

			if (StringUtils.isEmpty(request.getProduct())) {
				log.info(":: Response {} {} {} {} ", salesInformation.get(), salesInformationPeriod.get(),
						topProduct.get(), topDownProduct.get());
			} else {
				log.info(":: Response {} {} ", salesInformation.get(), salesInformationPeriod.get());
			}

			if (request.getMonth() == null) {
				result.setYearlySales(salesInformationPeriod.get());
			} else {
				result.setMonthlySales(salesInformationPeriod.get());
			}
			if (StringUtils.isEmpty(request.getProduct())) {

				setProductsInformation(request, result, topProduct, topDownProduct);
			}
			result.setSuffix(""); // -> m barras - ventas
			result.setSuffixBest("");// -> m productos
			result.setSuffixWorst("");// -> k productos
		} catch (InterruptedException | ExecutionException e) {
			log.error(":: Error future response ", e);
		}
		return result;
	}

	private void setDateProcedure(SalesRequestDTO request, StringBuilder initDate, StringBuilder endDate) {
		if (BooleanUtils.negate(request.getMonth() == null)) {
			initDate.append(request.getYear());
			initDate.append(DASH);
			initDate.append(StringUtils.leftPad(request.getMonth().toString(), 2, PADDING_LEFT));
			initDate.append(DASH);
			initDate.append(FIRST_DAY);
			endDate.append(request.getYear());
			endDate.append(DASH);
			endDate.append(StringUtils.leftPad(request.getMonth().toString(), 2, PADDING_LEFT));
			endDate.append(DASH);
			if (request.getMonth().equals(Calendar.APRIL + ONE) || request.getMonth().equals(Calendar.JUNE + ONE)
					|| request.getMonth().equals(Calendar.SEPTEMBER + ONE)
					|| request.getMonth().equals(Calendar.NOVEMBER + ONE)) {
				endDate.append(LAST_DAY);
			} else {
				endDate.append(LAST_DAY_LONG);
			}
		} else {
			initDate.append(request.getYear());
			initDate.append(DASH);
			initDate.append(StringUtils.leftPad(String.valueOf((Calendar.JANUARY + 1)), 2, PADDING_LEFT));
			initDate.append(DASH);
			initDate.append(FIRST_DAY);
			endDate.append(request.getYear());
			endDate.append(DASH);
			endDate.append(StringUtils.leftPad(String.valueOf((Calendar.DECEMBER + 1)), 2, PADDING_LEFT));
			endDate.append(DASH);
			endDate.append(LAST_DAY_LONG);
		}
	}

	private void setProductsInformation(SalesRequestDTO request, SalesResponseDTO result,
			CompletableFuture<List<Product>> topProduct, CompletableFuture<List<Product>> topDownProduct)
			throws InterruptedException, ExecutionException {

		CompletableFuture<SalesResponseDTO> productOne;
		CompletableFuture<SalesResponseDTO> productTwo;
		CompletableFuture<SalesResponseDTO> productThree;
		CompletableFuture<SalesResponseDTO> productFour;
		CompletableFuture<SalesResponseDTO> productFive;

		CompletableFuture<SalesResponseDTO> productOneDown;
		CompletableFuture<SalesResponseDTO> productTwoDown;
		CompletableFuture<SalesResponseDTO> productThreeDown;
		CompletableFuture<SalesResponseDTO> productFourDown;
		CompletableFuture<SalesResponseDTO> productFiveDown;
		if (BooleanUtils.negate(request.getMonth() == null)) {
			
				productOne = procedureProductInvoker.executeSalesProductMonthlyInformation(
						SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topProduct.get().get(0),
						request.getYear(), request.getMonth());
				productTwo = procedureProductInvoker.executeSalesProductMonthlyInformation(
						SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topProduct.get().get(1),
						request.getYear(), request.getMonth());
				productThree = procedureProductInvoker.executeSalesProductMonthlyInformation(
						SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topProduct.get().get(2),
						request.getYear(), request.getMonth());
				productFour = procedureProductInvoker.executeSalesProductMonthlyInformation(
						SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topProduct.get().get(3),
						request.getYear(), request.getMonth());
				productFive = procedureProductInvoker.executeSalesProductMonthlyInformation(
						SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topProduct.get().get(4),
						request.getYear(), request.getMonth());
			

			productOneDown = procedureProductInvoker.executeSalesProductMonthlyInformation(
					SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(0),
					request.getYear(), request.getMonth());
			productTwoDown = procedureProductInvoker.executeSalesProductMonthlyInformation(
					SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(1),
					request.getYear(), request.getMonth());
			productThreeDown = procedureProductInvoker.executeSalesProductMonthlyInformation(
					SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(2),
					request.getYear(), request.getMonth());
			productFourDown = procedureProductInvoker.executeSalesProductMonthlyInformation(
					SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(3),
					request.getYear(), request.getMonth());
			productFiveDown = procedureProductInvoker.executeSalesProductMonthlyInformation(
					SALES_PRODUCT_MONTHLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(4),
					request.getYear(), request.getMonth());

		} else {
			productOne = procedureProductInvoker.executeSalesProductYearlyInformation(SALES_PRODUCT_YEARLY_PROCEDURE,
					request.getIdBranch(), topProduct.get().get(0), request.getYear());
			productTwo = procedureProductInvoker.executeSalesProductYearlyInformation(SALES_PRODUCT_YEARLY_PROCEDURE,
					request.getIdBranch(), topProduct.get().get(1), request.getYear());
			productThree = procedureProductInvoker.executeSalesProductYearlyInformation(SALES_PRODUCT_YEARLY_PROCEDURE,
					request.getIdBranch(), topProduct.get().get(2), request.getYear());
			productFour = procedureProductInvoker.executeSalesProductYearlyInformation(SALES_PRODUCT_YEARLY_PROCEDURE,
					request.getIdBranch(), topProduct.get().get(3), request.getYear());
			productFive = procedureProductInvoker.executeSalesProductYearlyInformation(SALES_PRODUCT_YEARLY_PROCEDURE,
					request.getIdBranch(), topProduct.get().get(4), request.getYear());

			productOneDown = procedureProductInvoker.executeSalesProductYearlyInformation(
					SALES_PRODUCT_YEARLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(0),
					request.getYear());
			productTwoDown = procedureProductInvoker.executeSalesProductYearlyInformation(
					SALES_PRODUCT_YEARLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(1),
					request.getYear());
			productThreeDown = procedureProductInvoker.executeSalesProductYearlyInformation(
					SALES_PRODUCT_YEARLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(2),
					request.getYear());
			productFourDown = procedureProductInvoker.executeSalesProductYearlyInformation(
					SALES_PRODUCT_YEARLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(3),
					request.getYear());
			productFiveDown = procedureProductInvoker.executeSalesProductYearlyInformation(
					SALES_PRODUCT_YEARLY_PROCEDURE, request.getIdBranch(), topDownProduct.get().get(4),
					request.getYear());
		}

		CompletableFuture.allOf(productOne, productTwo, productThree, productFour, productFive, productOneDown,
				productTwoDown, productThreeDown, productFourDown, productFiveDown);
		
		log.info(":: Products {} {} ", productOne.get(), productTwo.get(), productThree.get(), productFour.get(),
				productFive.get(), productOneDown.get(), productTwoDown.get(), productThreeDown.get(),
				productFourDown.get(), productFiveDown.get());

		final List<SalesPeriodResponseDTO> bestProduct = new ArrayList<>();
		bestProduct.addAll(productOne.get().getBestProduct());
		bestProduct.addAll(productTwo.get().getBestProduct());
		bestProduct.addAll(productThree.get().getBestProduct());
		bestProduct.addAll(productFour.get().getBestProduct());
		bestProduct.addAll(productFive.get().getBestProduct());

		final List<SalesPeriodResponseDTO> worstProduct = new ArrayList<>();
		worstProduct.addAll(productOneDown.get().getBestProduct());
		worstProduct.addAll(productTwoDown.get().getBestProduct());
		worstProduct.addAll(productThreeDown.get().getBestProduct());
		worstProduct.addAll(productFourDown.get().getBestProduct());
		worstProduct.addAll(productFiveDown.get().getBestProduct());
		result.setBestProduct(bestProduct);
		result.setWorstProduct(worstProduct);

		final List<Integer> numberItems = new ArrayList<>();
		final List<Integer> numberItemsWorst = new ArrayList<>();
		Integer numberItemOne = 0;
		Integer numberItemTwo = 0;
		Integer numberItemThree = 0;
		Integer numberItemFour = 0;
		Integer numberItemFive = 0;

		Integer numberItemOneWorst = 0;
		Integer numberItemTwoWorst = 0;
		Integer numberItemThreeWorst = 0;
		Integer numberItemFourWorst = 0;
		Integer numberItemFiveWorst = 0;
		for (int index = 0; index < productOne.get().getNumberItems().size(); index++) {
			numberItemOne += productOne.get().getNumberItems().get(index);
			numberItemTwo += productTwo.get().getNumberItems().get(index);
			numberItemThree += productThree.get().getNumberItems().get(index);
			numberItemFour += productFour.get().getNumberItems().get(index);
			numberItemFive += productFive.get().getNumberItems().get(index);

			numberItemOneWorst += productOneDown.get().getNumberItems().get(index);
			numberItemTwoWorst += productTwoDown.get().getNumberItems().get(index);
			numberItemThreeWorst += productThreeDown.get().getNumberItems().get(index);
			numberItemFourWorst += productFourDown.get().getNumberItems().get(index);
			numberItemFiveWorst += productFiveDown.get().getNumberItems().get(index);

		}
		numberItems.add(numberItemOne);
		numberItems.add(numberItemTwo);
		numberItems.add(numberItemThree);
		numberItems.add(numberItemFour);
		numberItems.add(numberItemFive);

		numberItemsWorst.add(numberItemOneWorst);
		numberItemsWorst.add(numberItemTwoWorst);
		numberItemsWorst.add(numberItemThreeWorst);
		numberItemsWorst.add(numberItemFourWorst);
		numberItemsWorst.add(numberItemFiveWorst);

		result.setNumberItems(numberItems);
		result.setNumberItemsWorst(numberItemsWorst);

	}

}
