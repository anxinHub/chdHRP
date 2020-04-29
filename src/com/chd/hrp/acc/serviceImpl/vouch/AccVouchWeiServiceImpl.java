/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.vouch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.vouch.AccVouchWeiMapper;
import com.chd.hrp.acc.entity.AccVouchWei;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.vouch.AccVouchWeiService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 凭证分册表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accVouchWeiService")
public class AccVouchWeiServiceImpl implements AccVouchWeiService {

	private static Logger logger = Logger.getLogger(AccVouchWeiServiceImpl.class);
	
	@Resource(name = "accVouchWeiMapper")
	private final AccVouchWeiMapper accVouchWeiMapper = null;
   
	/**
	 * @Description 
	 * 凭证分册表<BR> 添加AccVouchWei
	 * @param AccVouchWei entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccVouchWei(Map<String,Object> entityMap)throws DataAccessException{
		
		/*AccVouchWei AccVouchWei = queryAccVouchWeiByCode(entityMap);

		if (AccVouchWei != null) {

			return "{\"error\":\"名称：" + entityMap.get("wei_name").toString() + "重复.\"}";

		}*/
		
		try {
			
			accVouchWeiMapper.addAccVouchWei(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchWei\"}";

		}

	}
	
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 查询AccVouchWei分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccVouchWei(Map<String,Object> entityMap) throws DataAccessException{
		
			List<AccVouchWei> list = accVouchWeiMapper.queryAccVouchWei(entityMap);
			
			return ChdJson.toJson(list);
		
	}

	/**
	 * @Description 
	 * 凭证分册表<BR> 查询AccVouchWeiByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccVouchWei queryAccVouchWeiByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accVouchWeiMapper.queryAccVouchWeiByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 删除AccVouchWei
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccVouchWei(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
				accVouchWeiMapper.deleteAccVouchWei(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccVouchWei\"}";

		}
    }
	
	/**
	 * @Description 
	 * 凭证分册表<BR> 更新AccVouchWei
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccVouchWei(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accVouchWeiMapper.updateAccVouchWei(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccVouchWei\"}";

		}
	}


	@Override
	public String queryAccSubjByVouchWei(Map<String, Object> entityMap)
			throws DataAccessException {
		/*
		SysPage sysPage = new SysPage();
		
		if (sysPage.getTotal()==-1){*/
			
		List<Map<String, Object>> list = accVouchWeiMapper.queryAccSubjByVouchWei(entityMap);
			
			return ChdJson.toJson(list);
			
		/*}else{
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccVouchWei> list = accVouchWeiMapper.queryAccSubjByVouchWei(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, sysPage.getTotal());
		}*/
	}


	@Override
	public String queryAccSubjByVouchBT(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> list = accVouchWeiMapper.queryAccSubjByVouchBT(entityMap);
		
		return ChdJson.toJson(list);
	}


	@Override
	public List<Map<String, Object>> queryAccSubjByVouchWeiPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accVouchWeiMapper.queryAccSubjByVouchWei(entityMap);
		
		return list;
	}
	
	
}
