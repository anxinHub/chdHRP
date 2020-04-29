/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.depre.acc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitGeneralMapper;
import com.chd.hrp.ass.entity.depre.acc.AssDepreAccInitGeneral;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccInitGeneralService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产折旧_一般设备
 * @Table:
 * ASS_DEPRE_ACC_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assDepreAccInitGeneralService")
public class AssDepreAccInitGeneralServiceImpl implements AssDepreAccInitGeneralService {

	private static Logger logger = Logger.getLogger(AssDepreAccInitGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assDepreAccInitGeneralMapper")
	private final AssDepreAccInitGeneralMapper assDepreAccInitGeneralMapper = null;
    
	/**
	 * @Description 
	 * 添加资产折旧_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		return null;
		
	}
	/**
	 * @Description 
	 * 批量添加资产折旧_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		return null;
		
	}
	
		/**
	 * @Description 
	 * 更新资产折旧_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		return null;
		
	}
	/**
	 * @Description 
	 * 批量更新资产折旧_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		return null;
		
	}
	/**
	 * @Description 
	 * 删除资产折旧_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		return null;
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产折旧_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		return null;	
	}
	
	/**
	 * @Description 
	 * 添加资产折旧_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		return null;
		
	}
	/**
	 * @Description 
	 * 查询结果集资产折旧_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssDepreAccInitGeneral> list = (List<AssDepreAccInitGeneral>)assDepreAccInitGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssDepreAccInitGeneral> list = (List<AssDepreAccInitGeneral>)assDepreAccInitGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产折旧_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return null;
	}
	
	/**
	 * @Description 
	 * 获取资产折旧_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssDepreAccGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return null;
	}
	
	/**
	 * @Description 
	 * 获取资产折旧_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssDepreAccGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return null;
	}
	
}
