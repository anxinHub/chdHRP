package com.chd.hrp.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface DgmService {

	String addRepair(Map<String, Object> map,HttpServletRequest request, HttpServletResponse response);

}
