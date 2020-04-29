package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeDrgs;
import com.chd.hrp.htcg.entity.making.HtcgSchemeGeneralRule;
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

public interface HtcgSchemeIcd10RuleMapper extends SqlMapper{
 
    public List<HtcgSchemeDrgs> queryHtcgSchemeDrgsRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeDrgs> queryHtcgSchemeDrgsRule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int addHtcgSchemeIcd10Rule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgSchemeIcd10Rule(List<Map<String,Object>> list)throws DataAccessException;
	
	public HtcgSchemeIcd10Rule queryHtcgSchemeIcd10RuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcgSchemeIcd10Rule> queryHtcgSchemeIcd10Rule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeIcd10Rule> queryHtcgSchemeIcd10Rule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int addHtcgSchemeIcd9Rule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgSchemeIcd9Rule(List<Map<String,Object>> list)throws DataAccessException;
	
	public HtcgSchemeIcd9Rule queryHtcgSchemeIcd9RuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgSchemeIcd9Rule> queryHtcgSchemeIcd9Rule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeIcd9Rule> queryHtcgSchemeIcd9Rule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcgSchemeGeneralRule> queryHtcgSchemeGeneralRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeGeneralRule> queryHtcgSchemeGeneralRule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	 
	
	
}
