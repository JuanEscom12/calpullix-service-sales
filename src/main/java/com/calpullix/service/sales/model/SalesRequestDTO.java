package com.calpullix.service.sales.model;

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
public class SalesRequestDTO {

	private Integer idBranch;
	
	private String product;
	
	private Integer year;
	
	private Integer month;
	
}
