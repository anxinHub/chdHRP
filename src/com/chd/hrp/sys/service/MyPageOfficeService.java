package com.chd.hrp.sys.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;




public interface MyPageOfficeService {

	
	public Map<String,Object> printFormBatch(Map<String,Object> mapVo)throws DataAccessException;
	
	
	public String saveFormPrintPara(Map<String, Object> mapVo)throws DataAccessException;
	
	//PageOffice保存打印次数
	public void savePrintCountPage(Map<String,Object> map)throws DataAccessException;
	
	public void createReportFile(String templateFilePath,String toFilePath)throws DataAccessException;
	
	public void setReportFileFormula(String toFilePath)throws DataAccessException;
	
	public void saveReportTemplateFile(String fromFilePath,String toFilePath)throws DataAccessException;
	
	public String printTableAcc(Map<String, Object> map)throws DataAccessException;
	
	public String printGrid(Map<String, Object> map)throws DataAccessException;
	
	public Map<String,Object> savePrintGridPre(Map<String, Object> map)throws DataAccessException;
}
