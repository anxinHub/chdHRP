/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreColSetMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreQueSetMapper;
import com.chd.hrp.hr.dao.sysstruc.HrTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrTabTypeMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
import com.chd.hrp.hr.service.base.HrTableManagerService;
import com.chd.hrp.hr.service.sysstruc.HrTabStrucService;

/**
 * 
 * @Description: 数据表构建
 * @Table: HR_TAB_STRUC
 * @Author: zn
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrTabStrucService")
public class HrTabStrucServiceImpl implements HrTabStrucService {

	private static Logger logger = Logger.getLogger(HrTabStrucServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrTabStrucMapper")
	private final HrTabStrucMapper hrTabStrucMapper = null;

	@Resource(name = "hrTabTypeMapper")
	private final HrTabTypeMapper hrTabTypeMapper = null;
	
	@Resource(name = "hrColStrucMapper")
	private final HrColStrucMapper hrColStrucMapper = null;
	
	@Resource(name = "hrTableManagerService")
	private final HrTableManagerService hrTableManagerService = null;
	
	@Resource(name = "hrStoreColSetMapper")
	private final HrStoreColSetMapper hrStoreColSetMapper = null;
	
	@Resource(name = "hrStoreQueSetMapper")
	private final HrStoreQueSetMapper hrStoreQueSetMapper = null;

	@Override
	public String addHrTabStruc(Map<String, Object> entityMap) throws DataAccessException {
		
		// 查询表名是否存在
		int count = hrTabStrucMapper.queryByCodeOrName(entityMap);

		if (count > 0 ) {
			throw new SysException("数据表表名或名称已存在");
		}
		
		//如果分类==人事档案 插入内置数据员工编码
		String type_tab_code = String.valueOf(entityMap.get("type_tab_code"));
		String tab_code = String.valueOf(entityMap.get("tab_code"));
		
		//建表
		Map<String,List<Object>> tableMap = new HashMap<String, List<Object>>();
		List<Object> colList = new ArrayList<Object>();

		try {
			int state = hrTabStrucMapper.add(entityMap);
		//	hrTabStrucMapper.addSysPermData(entityMap);//添加到系统权限表
			if(state > 0){
				if(entityMap.get("is_group_id") != null && "1".equals(entityMap.get("is_group_id").toString())){
					HrColStruc groupIdCol = new HrColStruc();
					groupIdCol.setGroup_id(Integer.parseInt(String.valueOf(entityMap.get("group_id"))));
					groupIdCol.setHos_id(Integer.parseInt(String.valueOf(entityMap.get("hos_id"))));
					//groupIdCol.setCopy_code(String.valueOf(entityMap.get("copy_code")));
					groupIdCol.setTab_code(tab_code);
					groupIdCol.setCol_code("GROUP_ID");
					groupIdCol.setCol_name("集团ID");
					groupIdCol.setData_type("NUMBER");
					groupIdCol.setFiled_length(19);
					groupIdCol.setPrec(0);
					groupIdCol.setFileTypeLength(2);
					groupIdCol.setIs_pk(1);
					groupIdCol.setIs_m(1);
					groupIdCol.setIs_innr(1);
					groupIdCol.setSort(1);
					colList.add(groupIdCol);
				}
				
				if(entityMap.get("is_hos_id") != null && "1".equals(entityMap.get("is_hos_id").toString())){
					HrColStruc hosIdCol = new HrColStruc();
					hosIdCol.setGroup_id(Integer.parseInt(String.valueOf(entityMap.get("group_id"))));
					hosIdCol.setHos_id(Integer.parseInt(String.valueOf(entityMap.get("hos_id"))));
					//hosIdCol.setCopy_code(String.valueOf(entityMap.get("copy_code")));
					hosIdCol.setTab_code(tab_code);
					hosIdCol.setCol_code("HOS_ID");
					hosIdCol.setCol_name("医院ID");
					hosIdCol.setData_type("NUMBER");
					hosIdCol.setFiled_length(20);
					hosIdCol.setPrec(0);
					hosIdCol.setFileTypeLength(2);
					hosIdCol.setIs_pk(1);
					hosIdCol.setIs_m(1);
					hosIdCol.setIs_innr(1);
					hosIdCol.setSort(2);
					colList.add(hosIdCol);
				}
				
				/*if(entityMap.get("is_copy_code") != null && "1".equals(entityMap.get("is_copy_code").toString())){
					HrColStruc coypCodeCol = new HrColStruc();
					coypCodeCol.setGroup_id(Integer.parseInt(String.valueOf(entityMap.get("group_id"))));
					coypCodeCol.setHos_id(Integer.parseInt(String.valueOf(entityMap.get("hos_id"))));
					//coypCodeCol.setCopy_code(String.valueOf(entityMap.get("copy_code")));
					coypCodeCol.setTab_code(tab_code);
					coypCodeCol.setCol_code("COPY_CODE");
					coypCodeCol.setCol_name("帐套");
					coypCodeCol.setData_type("VARCHAR2");
					coypCodeCol.setFiled_length(20);
					coypCodeCol.setIs_pk(1);
					coypCodeCol.setIs_m(1);
					coypCodeCol.setIs_innr(1);
					coypCodeCol.setFileTypeLength(1);
					coypCodeCol.setSort(3);
					colList.add(coypCodeCol);
				}*/
				
			}
			
			if("01".equals(type_tab_code)){
				
				HrColStruc idCol = new HrColStruc();
				idCol.setGroup_id(Integer.parseInt(String.valueOf(entityMap.get("group_id"))));
				idCol.setHos_id(Integer.parseInt(String.valueOf(entityMap.get("hos_id"))));
				//idCol.setCopy_code(String.valueOf(entityMap.get("copy_code")));
				idCol.setTab_code(tab_code);
				idCol.setCol_code("EMP_ID");
				idCol.setCol_name("员工ID");
				idCol.setData_type("NUMBER");
				idCol.setFiled_length(19);
				idCol.setPrec(0);
				idCol.setFileTypeLength(2);
				idCol.setIs_innr(1);
				idCol.setIs_pk(1);
				idCol.setIs_m(1);
				idCol.setSort(4);
				
				colList.add(idCol);
				
			}
			
			if(colList.size() > 0){
				
				hrColStrucMapper.addBatch(colList);
				
				tableMap.put(entityMap.get("tab_code").toString().toUpperCase(), colList);
				
				//创建表
				hrTableManagerService.createTableByMap(tableMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String updateHrTabStruc(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrTabStrucMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteHrTabStruc(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象
		HrTabStruc hrTabStruc = hrTabStrucMapper.queryByCode(entityMap);

		if (hrTabStruc != null && hrTabStruc.getIs_innr() == 1) {
			throw new SysException("内置表无法删除");
		}

		try {
			//删除表字段
			hrColStrucMapper.delete(entityMap);
			
			int state = hrTabStrucMapper.delete(entityMap);
			if(state > 0){
			/*	//删除系统权限
				hrTabStrucMapper.deleteSysPermData(entityMap);*/
				//删除档案数据列设置
				hrStoreColSetMapper.delete(entityMap);
				//删除档案库条件设置
				hrStoreQueSetMapper.delete(entityMap);
				//删除表
				hrTableManagerService.dorpTableByName(entityMap.get("tab_code").toString().toUpperCase());
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteBatchHrTabStruc(List<Map<String, Object>> entityList) throws DataAccessException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryHrTabStruc(Map<String, Object> entityMap) throws DataAccessException {
		List<HrTabStruc> list = (List<HrTabStruc>) hrTabStrucMapper.query(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public HrTabStruc queryHrTabStrucByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrTabStrucMapper.queryByCode(entityMap);
	}

	@Override
	public String queryHrTabStrucTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> tree = hrTabStrucMapper.queryHrTabStrucTree(entityMap);
		return JSONArray.toJSONString(tree);
	}

	@Override
	public String queryHrTabCodeSeq() throws DataAccessException {
		return hrTabStrucMapper.queryHrTabCodeSeq();
	}

}
