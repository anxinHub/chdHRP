/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.sysstruc.HrStoreColSetMapper;
import com.chd.hrp.hr.entity.sysstruc.HrStoreColSet;
import com.chd.hrp.hr.service.sysstruc.HrStoreColSetService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_STORE_COL_SET
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrStoreColSetService")
public class HrStoreColSetServiceImpl implements HrStoreColSetService {

	private static Logger logger = Logger.getLogger(HrStoreColSetServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrStoreColSetMapper")
	private final HrStoreColSetMapper hrStoreColSetMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		List<HrStoreColSet> list = hrStoreColSetMapper.queryHrStoreColSet(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveHrStoreColSet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("data") != null && StringUtils.isNotBlank(entityMap.get("data").toString())) {
				hrStoreColSetMapper.delete(entityMap);
				List<HrStoreColSet> dataList = JSONArray.parseArray(entityMap.get("data").toString(),
						HrStoreColSet.class);
				
				for (HrStoreColSet hrStoreColSet : dataList) {
					if(hrStoreColSet.getCol_name_show() == null || StringUtils.isBlank(hrStoreColSet.getCol_name_show())){
						throw new SysException("列显示名称不能为空!");
					}
					
					if(hrStoreColSet.getIs_change() != null && hrStoreColSet.getIs_change() == 1){
						if(hrStoreColSet.getChange_col_code() == null || StringUtils.isBlank(hrStoreColSet.getChange_col_code())){
							throw new SysException("如果列存在变更，变更列不能为空!");
						}
					}
					hrStoreColSet.setStore_type_code(entityMap.get("store_type_code").toString());
					if(hrStoreColSet.getTab_code() == null){
						hrStoreColSet.setTab_code(entityMap.get("tab_code").toString());
					}
					if(hrStoreColSet.getGroup_id() == null){
						hrStoreColSet.setGroup_id(Long.parseLong(entityMap.get("group_id").toString()));
					}
					if(hrStoreColSet.getHos_id() == null){
						hrStoreColSet.setHos_id(Long.parseLong(entityMap.get("hos_id").toString()));
					}

				}
				hrStoreColSetMapper.addBatch(dataList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String addHrStoreColSetBatch(Map<String, Object> mapVo) {
		
		try {
			
			List<String> arrList = JSONArray.parseArray(mapVo.get("arrid").toString(),String.class);
			
			//先删除之前的老数据
			hrStoreColSetMapper.deleteHrStoreColSetBatch(mapVo,arrList);
			
			//把选中的表数据列同步到目标的档案库中
			hrStoreColSetMapper.addHrStoreColSetBatch(mapVo,arrList);
			
			return "{\"msg\":\"同步成功！\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+e.getMessage()+"\"}");
		}
	}

}
