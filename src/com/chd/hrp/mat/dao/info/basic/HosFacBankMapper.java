/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * 生产厂商银行信息表
 * @Table:
 * HOS_FAC_BANK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface HosFacBankMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 生产厂商银行信息表结果集<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public List<?> queryHosFacBank(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增生产厂商和银行信息对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int addHosFacBank(List<Map<String,Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 删除生产厂商和银行信息对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteHosFacBank(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 批量删除生产厂商和银行信息对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteHosFacBankBatch(List<Map<String,Object>> entityMap)throws DataAccessException;
	
}
