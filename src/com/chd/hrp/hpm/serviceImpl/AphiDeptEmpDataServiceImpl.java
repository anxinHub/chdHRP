package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptEmpDataMapper;
import com.chd.hrp.hpm.dao.AphiEmpDictMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptEmpData;
import com.chd.hrp.hpm.entity.AphiEmpDict;
import com.chd.hrp.hpm.service.AphiDeptEmpDataService;

/**
 * 科室职工数据准备
 * @author Administrator
 *
 */
@Service("aphiDeptEmpDataService")
public class AphiDeptEmpDataServiceImpl implements AphiDeptEmpDataService{

	private static Logger logger = Logger.getLogger(AphiDeptDictServiceImpl.class);
	
	@Resource(name = "aphiDeptEmpDataMapper")
	private final AphiDeptEmpDataMapper aphiDeptEmpDataMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	@Resource(name = "aphiEmpDictMapper")
	private final AphiEmpDictMapper aphiEmpDictMapper = null;

	/**
	 * 查询
	 */
	@Override
	public String queryHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AphiDeptEmpData> list = aphiDeptEmpDataMapper.queryDeptEmpData(entityMap,rowBounds);
		
		return JsonListBeanUtil.listToJson(list,sysPage.getTotal());
	}

	/**
	 * 添加
	 */
	@Override
	public String addHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		int state = aphiDeptEmpDataMapper.addDeptEmpData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		
		} else {
			
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
	
		}
	}

	/**
	 * 修改
	 */
	@Override
	public String updateHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException {
		
//		AphiDeptBonusAudit  deptBonusAudit = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//		
//		if(deptBonusAudit !=null){
//			 
//			 if( deptBonusAudit.getIs_audit()== 1){
//				 
//				 return "{\"msg\":\"本月奖金已审核后。数据不能操作.\",\"state\":\"true\"}";
//				 
//			 }
//		 }
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		try {
			
			AphiDeptEmpData aphiDeptEmpData = aphiDeptEmpDataMapper.queryDeptEmpDataByCode(entityMap);
			
			if(aphiDeptEmpData != null){
				
				int state = aphiDeptEmpDataMapper.updateDeptEmpData(entityMap);
				
				if (state > 0) {
					
					return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
				}else {
				
					return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
				
				}
			}else{
				
				return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHpmDeptEmpData\"}");
		}
		
		
	}

	/**
	 * 删除
	 */
	@Override
	public String deleteHpmDeptEmpData(Map<String, Object> entityMap, String codes)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			if(codes != null && !codes.equals("")){
				String[] codes_array = codes.split(",");
				
				for (String code : codes_array) {
					
					String[] code_array = code.split(";");
					
					entityMap.put("dept_id", code_array[0]);
					
					entityMap.put("dept_no", code_array[1]);
					
					entityMap.put("acct_year", code_array[2].substring(0,4));
					
					entityMap.put("acct_month", code_array[2].substring(4));
					
					aphiDeptEmpDataMapper.deleteDeptEmpData(entityMap);
				}
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			}else{
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
		
	}

	/**
	 * 生成
	 * 生成：直接根据核算年月及【职工字典表aphi_emp_DICT】，
	 * 按科室分组汇总人数，生成【科室人数数据表aphi_dept_emp_data】，
	 * 支持增量生成，当本月奖金已审核后，不能生成本月数据，人数写入列emp_amount中。
	 */
	@Override
	public String initHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException {

//		AphiDeptBonusAudit  deptBonusAudit = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//		
//		if(deptBonusAudit !=null){
//			 
//			 if( deptBonusAudit.getIs_audit()== 1){
//				 
//				 return "{\"msg\":\"本月奖金已审核后。数据不能生成.\",\"state\":\"true\"}";
//				 
//			 }
//			 if(deptBonusAudit.getIs_grant()== 1){
//					
//					return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
//					
//			 }
//		 }
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String rbtnl = (String)entityMap.get("rbtnl");
		
		//重新生成(涉及删除)
		if(rbtnl.equals("all")){
			try {
				Map<Object, Object> empMap = new HashMap<Object, Object>();
				
				List<AphiEmpDict> allEmp = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
				//
				List<AphiDeptEmpData> deptEmpdataList = aphiDeptEmpDataMapper.getEmp(entityMap);
				
				aphiDeptEmpDataMapper.deleteDeptEmpData(entityMap);
				
				for(AphiDeptEmpData aphiDeptEmpData :deptEmpdataList){
					empMap.put(aphiDeptEmpData.getDept_id(),aphiDeptEmpData.getEmp_amount());
				}
				
				if(deptEmpdataList != null){
					for(AphiEmpDict aphiEmpDict :allEmp){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("acct_year", entityMap.get("acct_year"));
						map.put("acct_month", entityMap.get("acct_month"));
						map.put("dept_id", aphiEmpDict.getDept_id());
						map.put("dept_no", aphiEmpDict.getDept_no());
						map.put("emp_amount", empMap.get(aphiEmpDict.getDept_id()));
						aphiDeptEmpDataMapper.initDeptEmpData(map);//生成数据(添加)
					}
						
					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
				}else{
					return "{\"msg\":\"没有要生成数据.\",\"state\":\"false\"}";
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptEmpData\"}");
			}
		}else{//增量生成
			try {
				
				Map<Object, AphiDeptEmpData> map = new HashMap<Object, AphiDeptEmpData>();
				
				Map<Object, Object> empMap = new HashMap<Object, Object>();
				
				List<AphiDeptEmpData> deptEmpdataList = aphiDeptEmpDataMapper.queryDeptEmpData(entityMap);
				
				for(AphiDeptEmpData aphiDeptEmpData :deptEmpdataList){
					map.put(aphiDeptEmpData.getDept_id(), aphiDeptEmpData);
				}
				
				//生成数据
				List<AphiDeptEmpData> getEmpList = aphiDeptEmpDataMapper.getEmp(entityMap);
				
				for(AphiDeptEmpData aphiDeptEmpData :getEmpList){
					empMap.put(aphiDeptEmpData.getDept_id(),aphiDeptEmpData.getEmp_amount());
				}
				
				List<AphiEmpDict> aphiEmpDictList = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
				
				for(AphiEmpDict aphiEmpDict : aphiEmpDictList){
					if(map.get(aphiEmpDict.getDept_id()) == null){
						Map<String, Object> incrementMap = new HashMap<String, Object>();

						incrementMap.put("group_id", entityMap.get("group_id"));
						
						incrementMap.put("hos_id", entityMap.get("hos_id"));

						incrementMap.put("copy_code", entityMap.get("copy_code"));

						incrementMap.put("acct_year", entityMap.get("acct_year"));

						incrementMap.put("acct_month", entityMap.get("acct_month"));
						
						incrementMap.put("dept_id", aphiEmpDict.getDept_id());
						
						incrementMap.put("dept_no", aphiEmpDict.getDept_no());
						
						incrementMap.put("emp_amount", empMap.get(aphiEmpDict.getDept_id()));
						
						aphiDeptEmpDataMapper.initDeptEmpData(incrementMap);//合计科室变更表职工人数，更新至AphiDeptEmpData表
						
						
					}
						
				}
				
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initDeptEmpData\"}");
			}
		}
		
		
	}

	/**
	 * 计算
	 * 计算：根据【职工字典表aphi_emp_dict】，
	 * 计算每个科室人数。并将计算结果更新至【科室人数数据表aphi_dept_emp_data】中。
	 */
	@Override
	public String collectHpmDeptEmpData(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<AphiDeptEmpData> deptEmpdataList = aphiDeptEmpDataMapper.getEmp(entityMap);
		
		if(deptEmpdataList != null){
			for (int i = 0; i < deptEmpdataList.size(); i++) {
				
				AphiDeptEmpData deptEmpdata = deptEmpdataList.get(i);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("acct_year", entityMap.get("acct_year"));
				map.put("acct_month", entityMap.get("acct_month"));
				map.put("dept_id", deptEmpdata.getDept_id());
				map.put("dept_no", deptEmpdata.getDept_no());
				map.put("emp_amount", deptEmpdata.getEmp_amount());
				
				aphiDeptEmpDataMapper.collectDeptEmpData(map);//计算、执行updata语句
			}
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
		}else{
			return "{\"msg\":\"没有数据.\",\"state\":\"false\"}";
		}
		
		
		
	}

	
	/**
	 * 查询编码
	 */
	@Override
	public AphiDeptEmpData queryDeptEmpDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}		
		return aphiDeptEmpDataMapper.queryDeptEmpDataByCode(entityMap);
	}
	
	
	
	/**
	 * 导入
	 */
	@Override
	public String hpmDeptEmpDataImport(Map<String, Object> map)throws DataAccessException {
		try {
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			//new Map查询导入时对应数据信息
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("is_stop", "0");// 查询未停用
			
			//查询科室字典 List
			List<AphiDeptDict> deptList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			
			//以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptDict> deptMap = new HashMap<String, AphiDeptDict>();
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			
			//存储要添加保存的数据List
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(AphiDeptDict dept : deptList){
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}
			
			
			//遍历导入数据-遍历单一导入数据,得到名称-判断是否为空-跳出循环
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					
					List<String> acct_year = item.get("核算年度");
					List<String> acct_month = item.get("核算月份");
					List<String> dept_name = item.get("科室名称");
					List<String> emp_amount = item.get("职工人数");
					
					
					
					if(acct_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" +acct_year.get(0) +"\"\"}";
					}
					
					if(acct_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) +"\"}";
					}
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室名称单元格为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else if(Character.isDigit(dept_name.get(1).charAt(0))){
						if(deptMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}else if(deptMap.get(dept_name.get(1)) == null){
						return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}
					
					
					if(emp_amount.get(1) == null){
						return "{\"warn\":\"职工人数为空！\",\"state\":\"false\",\"row_cell\":\" " + emp_amount.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((emp_amount.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + emp_amount.get(1) + ",职工人数输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + emp_amount.get(0) + "\"}";
						  }
					}
					
					
					//以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + emp_amount.get(1) + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acct_year.get(1)+"年度," + acct_month.get(1)+"月份," +emp_amount.get(1)+"职工人数," +dept_name.get(1)+"科室名称 ");
					}else{
						exitMap.put(key, key);
					}
					
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("acct_year", acct_year.get(1));
					returnMap.put("acct_month", acct_month.get(1));
					returnMap.put("dept_name", dept_name.get(1));
					returnMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
					returnMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					returnMap.put("emp_amount", emp_amount.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiDeptEmpDataMapper.addBatchDeptEmpData(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public List<Map<String, Object>> queryHpmDeptEmpDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		List<Map<String, Object>> list = aphiDeptEmpDataMapper.queryDeptEmpDataPrint(entityMap);
		
		return list;
	}
	
	
}
