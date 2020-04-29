package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.HtcgSchemeIcd10Rule;
import com.chd.hrp.htcg.entity.making.HtcgSchemeIcd9Rule;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcgSchemeIcd10RuleService {
	
	/**
	 * 核算方案病种入组规则查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgSchemeDrgsRule(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 核算方案病种入组规则（诊断）保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String addHtcgSchemeIcd10Rule(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 核算方案病种入组规则（诊断）删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String deleteBatchHtcgSchemeIcd10Rule(List<Map<String,Object>> entityList)throws DataAccessException;
	/**
	 * 核算方案病种入组规则（诊断）查询ByCode
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public HtcgSchemeIcd10Rule queryHtcgSchemeIcd10RuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 核算方案病种入组规则（诊断）查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgSchemeIcd10Rule(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 核算方案病种入组规则（手术）保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String addHtcgSchemeIcd9Rule(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 核算方案病种入组规则（手术）删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String deleteBatchHtcgSchemeIcd9Rule(List<Map<String,Object>> entityList)throws DataAccessException;
	/**
	 * 核算方案病种入组规则（手术）查询ByCode
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public HtcgSchemeIcd9Rule queryHtcgSchemeIcd9RuleByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 核算方案病种入组规则（手术）查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgSchemeIcd9Rule(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	public String queryHtcgSchemeGeneralRule(Map<String,Object> entityMap) throws DataAccessException;
	  
}
