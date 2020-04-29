/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.bid;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.bid.AssBidGroupHos;
/**
 * 
 * @Description:
 * 050501 集团与医院资产招标关系表
 * @Table:
 * ASS_BID_GROUP_HOS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssBidGroupHosMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssBidGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssBidGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssBidGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap
	 * @return AssBidGroupHos
	 * @throws DataAccessException
	*/
	public int updateBatchAssBidGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssBidGroupHos(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssBidGroupHos(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 集团与医院资产招标关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidGroupHos> queryAssBidGroupHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050501 集团与医院资产招标关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssBidGroupHos> queryAssBidGroupHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssBidGroupHos
	 * @throws DataAccessException
	*/
	public AssBidGroupHos queryAssBidGroupHosByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidGroupHos
	 * @throws DataAccessException
	*/
	public AssBidGroupHos queryAssBidGroupHosByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050501 集团与医院资产招标关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidGroupHos>
	 * @throws DataAccessException
	*/
	public List<AssBidGroupHos> queryAssBidGroupHosExists(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
