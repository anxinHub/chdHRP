/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.affi.tran;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedTranRela;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_Tran_Rela
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAffiTranRelaMapper extends SqlMapper{

	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityList
	 * @return List<MedTranRela>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryListForDelete(List<Map<String,Object>> entityList) throws DataAccessException;
}
