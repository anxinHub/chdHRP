package com.chd.hrp.acc.service.vouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.superVouch.SysPrintPara;
import com.chd.hrp.acc.entity.superVouch.SysPrintTemplate;

public interface SuperPrintService {

	//查询系统参数，打印是否按用户权限控制
	public String querySuperPrintAccPara(Map<String, Object> map)throws DataAccessException;
	
	//下拉框-取科目全称
	public String querySubjNameAllDict(Map<String,Object> map) throws DataAccessException;
	

	//保存打印模板
	public String saveSuperPrintTemplate(Map<String,Object> map) throws DataAccessException;

	
	//查询打印模板
	public SysPrintTemplate querySuperPrintTemplateByCode(Map<String,Object> map) throws DataAccessException;
	
	//打印模板设置页面，查询打印模板参数，返回json字符串
	public String querySuperPrintParaByListJosn(Map<String,Object> map) throws DataAccessException;
	
	//业务调用页面，根据系统参数（打印是否按用户设置模板），查询打印模板参数
	public String querySuperPrintParaByCode(Map<String,Object> map) throws DataAccessException;
	
	//查询打印模板参数，返回Map
	public Map<String,String> querySuperPrintParaByList(Map<String,Object> map) throws DataAccessException;
	
	//查询打印模板参数，返回Map
	public Map<String,String> querySuperPrintParaByFlag(Map<String,Object> map) throws DataAccessException;
	
	//查询打印模板内容
	public String querySuperPrintContentByCode(Map<String,Object> map) throws DataAccessException;
	
	//根据打印参数编码查询参数值,直接从paraList里面检索
	public String getSuperPrintParaValue(List<SysPrintPara> paraList,String paraCode) throws DataAccessException;
	
	//根据打印模板填充主表明细表数据
	public String querySuperPrintfillPage(Map<String, Object> printMap,List<SysPrintPara> paraList,Map<String,Object> mainMap,List<Map<String, Object>> liDetail) throws DataAccessException;
	
	//填充所有明细数据，根据开始行结束行强制分页。
	public String querySuperPrintfillBatch(Map<String,Object> mainMap,List<Map<String, Object>> liDetail) throws DataAccessException;
	
	//根据map、list合并成一个json串
	public String getMapListByPrintTemplateJson(Map<String,Object> paraMap,Map<String,Object> mainMap,List<Map<String, Object>> detailList)throws DataAccessException;
	
	//根据主表list、明细表list合并成一个spreadjson串
	public String getBatchListByPrintTemplateJson(Map<String,Object> paraMap,List<Map<String, Object>> mainList,List<Map<String, Object>> detailList)throws DataAccessException;

	//根据主表list、明细表list合并成一个datajson串
	public String getBatchListByPrintTemplateJson2(Map<String,Object> paraMap,List<Map<String, Object>> mainList,List<Map<String, Object>> detailList)throws DataAccessException;
	
	//保存打印次数
	public String savePrintCount(List<Map<String,Object>> list)throws DataAccessException;
}
