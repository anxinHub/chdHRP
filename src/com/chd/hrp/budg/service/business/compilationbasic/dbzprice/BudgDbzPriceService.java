/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.dbzprice;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 单病种收费标准
 * @Table:
 * BUDG_DBZ_PRICE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDbzPriceService extends SqlService {

	/**
	 *  根据医保单病种编码  查询医保类型编码是否存在（查询 医保单病种维护表 BUDG_YB_SINGLE_DISEASE ）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int  queryBudgInsuranceCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询医保单病种编码是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int  queryBudgDiseaseCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医保单病种下拉框 添加页面用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSingleDC(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据主键 查询数据是否存在
	 * @param mapVo
	 * @return
	 */
	public int queryDataExist(Map<String, Object> mapVo);
	
	/**
	 * 查询要生成的数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryDate(Map<String, Object> mapVo);
	
	/**
	 * 医保类型下拉框 添加页面
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public  String queryBudgYBTY(Map<String, Object> mapVo) throws DataAccessException;

	public String budgDbzPriceImportNewPage(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
}
