/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatStoreEmpSetMapper;
import com.chd.hrp.mat.entity.MatStoreEmp;
import com.chd.hrp.mat.entity.MatStoreSet;
import com.chd.hrp.mat.service.info.basic.MatStoreEmpSetService;
import com.chd.hrp.mat.service.info.basic.MatStoreSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 仓库采购员对应关系
 * @Table: MAT_STORE_EMP_MAP
 */
@Service("matStoreEmpSetService")
public class MatStoreEmpSetServiceImpl implements MatStoreEmpSetService {

	private static Logger logger = Logger
			.getLogger(MatStoreEmpSetServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matStoreEmpSetMapper")
	private final MatStoreEmpSetMapper matStoreEmpSetMapper = null;

	// 添加 仓库采购员对应关系
	@Override
	public String addMatStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			entityMap.put("user_id", SessionManager.getUserId());
			
			MatStoreEmp matStoreEmp =  matStoreEmpSetMapper.queryMatStoreEmpSetByCode(entityMap);
			
			if (matStoreEmp != null ){
				
				return "{\"error\":\"添加失败  已存在重复数据\"}";
			}
	
			int state = matStoreEmpSetMapper.addMatStoreEmpSet(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStoreSet\"}";

		}

	}

	@Override
	public String addBatchMatStoreEmpSet(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateMatStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {
		
		MatStoreEmp matStoreEmp =  matStoreEmpSetMapper.queryMatStoreEmpSetByCode(entityMap);
		
			if (matStoreEmp != null ){
				
				return "{\"error\":\"更新失败  已存在重复数据\"}";
			}

			try {
				
				  int state = matStoreEmpSetMapper.updateMatStoreEmpSet(entityMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	
				}
				catch (Exception e) {
	
					logger.error(e.getMessage(), e);
	
					return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatStoreEmpSet\"}";
	
				}	
	}

	@Override
	public String updateBatchMatStoreEmpSet(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteMatStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatchMatStoreEmpSet(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {
				
			   matStoreEmpSetMapper.deleteBatchMatStoreEmpSet(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				throw new SysException(e.getMessage());
	
			}	
	}

	@Override
	public String queryMatStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {

			SysPage sysPage = new SysPage();
	
			sysPage = (SysPage) entityMap.get("sysPage");
	
			if (sysPage.getTotal() == -1) {
	
				List<MatStoreEmp> list = matStoreEmpSetMapper.queryMatStoreEmpSet(entityMap);
	
				return ChdJson.toJson(list);
	
			} else {
	
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
	
				List<MatStoreEmp> list = matStoreEmpSetMapper.queryMatStoreEmpSet(entityMap, rowBounds);
	
				PageInfo page = new PageInfo(list);
	
				return ChdJson.toJson(list, page.getTotal());
	
			}
	}

	@Override
	public MatStoreEmp queryMatStoreEmpSetByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return matStoreEmpSetMapper.queryMatStoreEmpSetByCode(entityMap);
	}

}
