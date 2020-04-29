/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 凭证制单流程<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchFlowMapper extends SqlMapper{
	//列表查询
	public List<Map<String, Object>> queryList(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询初始化数据
	public List<Map<String, Object>> queryInitData(Map<String,Object> entityMap) throws DataAccessException;
	
	//添加
	public int addAccVouchFlow(List<Map<String,Object>> entityList) throws DataAccessException;
	
	//删除
	public int deleteAccVouchFlow(Map<String, Object> entityMap) throws DataAccessException;
	
	//校验是否含未记账凭证
	public int existsAcc(Map<String, Object> entityMap) throws DataAccessException;
}
