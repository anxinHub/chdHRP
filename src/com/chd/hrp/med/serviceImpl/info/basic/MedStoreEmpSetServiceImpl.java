/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.basic.MedStoreEmpSetMapper;
import com.chd.hrp.med.entity.MedStoreEmp;
import com.chd.hrp.med.entity.MedStoreSet;
import com.chd.hrp.med.service.info.basic.MedStoreEmpSetService;
import com.chd.hrp.med.service.info.basic.MedStoreSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 仓库采购员对应关系
 * @Table: MED_STORE_EMP_MAP
 */
@Service("medStoreEmpSetService")
public class MedStoreEmpSetServiceImpl implements MedStoreEmpSetService {

	private static Logger logger = Logger
			.getLogger(MedStoreEmpSetServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "medStoreEmpSetMapper")
	private final MedStoreEmpSetMapper medStoreEmpSetMapper = null;

	// 添加 仓库采购员对应关系
	@Override
	public String addMedStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {
		
		MedStoreEmp medStoreEmp =  medStoreEmpSetMapper.queryMedStoreEmpSetByCode(entityMap);
		
		if (medStoreEmp != null ){
			
			return "{\"error\":\"添加失败  已存在重复数据\"}";
		}
		
		try {

			int state = medStoreEmpSetMapper.addMedStoreEmpSet(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStoreSet\"}";

		}

	}

	@Override
	public String addBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateMedStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {
		
		MedStoreEmp medStoreEmp =  medStoreEmpSetMapper.queryMedStoreEmpSetByCode(entityMap);
		
			if (medStoreEmp != null ){
				
				return "{\"error\":\"更新失败  已存在重复数据\"}";
			}

			try {
				
				  int state = medStoreEmpSetMapper.updateMedStoreEmpSet(entityMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	
				}
				catch (Exception e) {
	
					logger.error(e.getMessage(), e);
	
					return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedStoreEmpSet\"}";
	
				}	
	}

	@Override
	public String updateBatchMedStoreEmpSet(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteMedStoreEmpSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatchMedStoreEmpSet(List<Map<String, Object>> entityList)
			throws DataAccessException {
		try {
				
			   medStoreEmpSetMapper.deleteBatchMedStoreEmpSet(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				throw new SysException(e.getMessage());
	
			}	
	}

	@Override
	public String queryMedStoreEmpSet(Map<String, Object> entityMap) throws DataAccessException {
		
		
		List<MedStoreEmp> list = medStoreEmpSetMapper.queryMedStoreEmpSet(entityMap);
		
		return ChdJson.toJson(list);

		/*SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<MedStoreEmp> list = medStoreEmpSetMapper.queryMedStoreEmpSet(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());

			List<MedStoreEmp> list = medStoreEmpSetMapper.queryMedStoreEmpSet(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}*/
	}

	@Override
	public MedStoreEmp queryMedStoreEmpSetByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return medStoreEmpSetMapper.queryMedStoreEmpSetByCode(entityMap);
	}

}
