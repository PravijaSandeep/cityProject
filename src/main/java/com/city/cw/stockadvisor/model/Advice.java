package com.city.cw.stockadvisor.model;

import java.util.List;

import lombok.Data;

@Data
public class Advice {
	
	private String shareName;
	
	private List<String> adviceList;

}
