package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.superVouch.SysPrintPara;
import com.chd.hrp.acc.entity.superVouch.SysPrintTemplate;

public interface SuperPrintMapper extends SqlMapper{

	//查询系统参数，打印是否按用户权限控制
	public String querySuperPrintAccPara(Map<String, Object> map)throws DataAccessException;
	
	//下拉框-取科目全称 
	public List<HrpAccSelect> querySubjNameAllDict(Map<String, Object> map) throws DataAccessException;
	
	//添加打印模板
	public int insertSuperPrintTemplate(Map<String, Object> map) throws DataAccessException;
	
	//修改打印模板
	public int updateSuperPrintTemplate(Map<String, Object> map) throws DataAccessException;
	
	//查询打印次数
	public int querySuperPrintCount(Map<String, Object> map) throws DataAccessException;
	
	//查询所有打印次数
	public List<Map<String, Object>> querySuperPrintCountBatch(Map<String, Object> map) throws DataAccessException;
	
	
	//删除打印次数
	public int deleteSuperPrintCount(Map<String, Object> map) throws DataAccessException;
	
	//添加打印次数
	public int insertSuperPrintCount(Map<String, Object> map) throws DataAccessException;
	
	//保存打印次数
	public int savePrintCount(List<Map<String,Object>> list) throws DataAccessException;
	
	//PageOffice保存打印次数
	public int savePrintCountPage(Map<String,Object> map) throws DataAccessException;
	
	//保存打印参数
	public int updateSuperPrintPara(@Param("list") List<Map<String, Object>> liMap) throws DataAccessException;
	
	//删除打印模板
	public int deleteSuperPrintTemplate(Map<String, Object> map) throws DataAccessException;
	
	//查询打印模板
	public SysPrintTemplate querySuperPrintTemplateByCode(Map<String, Object> map) throws DataAccessException;

	//初始化参数
	public int copySuperPrintParaByCode(Map<String, Object> map) throws DataAccessException;
	
	//查询打印模板参数
	public List<SysPrintPara> querySuperPrintParaByCode(Map<String, Object> map) throws DataAccessException;
	
	//查询打印模板内容
	public String querySuperPrintContentByCode(Map<String, Object> map) throws DataAccessException;
	
	
}
