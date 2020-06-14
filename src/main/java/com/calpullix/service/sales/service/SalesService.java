package com.calpullix.service.sales.service;

import com.calpullix.service.sales.model.SalesRequestDTO;
import com.calpullix.service.sales.model.SalesResponseDTO;

public interface SalesService {

	SalesResponseDTO getSales(SalesRequestDTO request);
	
}
