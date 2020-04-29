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
import com.chd.hrp.hpm.dao.AphiDeptScoreDataMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptScoreData;
import com.chd.hrp.hpm.service.AphiDeptScoreDataService;
/**
 * 科室绩效数据准备
 * @author Administrator
 *
 */
@Service("aphiDeptScoreDataService")
public class AphiDeptScoreDataServiceImpl implements AphiDeptScoreDataService {

	private static Logger logger = Logger.getLogger(AphiDeptDictServiceImpl.class);
	
	@Resource(name = "aphiDeptScoreDataMapper")
	private final AphiDeptScoreDataMapper aphiDeptScoreDataMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	/**
	 * 查询
	 */
	@Override
	public String queryHpmDeptScoreData(Map<String, Object> entityMap)throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AphiDeptScoreData> list = aphiDeptScoreDataMapper.queryDeptScoreData(entityMap, rowBounds);
		
		return JsonListBeanUtil.listToJson(list,sysPage.getTotal());
	}

	/**
	 * 添加保存
	 */
	@Override
	public String addHpmDeptScoreData(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		int state =  aphiDeptScoreDataMapper.addDeptScoreData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		
		} else {
			
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
	
		}
	}

	/**
	 * 修改保存
	 */
	@Override
	public String updateHpmDeptScoreData(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
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
			
			AphiDeptScoreData aphiDeptScoreData = aphiDeptScoreDataMapper.queryDeptScoreDataByCode(entityMap);
			
			if(aphiDeptScoreData != null){
				
				int state = aphiDeptScoreDataMapper.updateDeptScoreData(entityMap);
				
				if (state > 0) {
					
					return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
				}else {
				
					return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
				
				}
			}else{
				return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
			}
			
			
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHpmDeptScoreData\"}");
		}
		
	}

	/**
	 * 删除
	 */
	@Override
	public String deleteHpmDeptScoreData(Map<String, Object> entityMap,String codes)throws DataAccessException {
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
					
					aphiDeptScoreDataMapper.deleteDeptScoreData(entityMap);
					
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			}else{
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码  deleteDeptScoreData\"}");
		}
	}

	/**
	 * 查询编码
	 */
	@Override
	public AphiDeptScoreData queryDeptScoreDataByCode(Map<String, Object> entityMap)throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptScoreDataMapper.queryDeptScoreDataByCode(entityMap);
	}

	/**
	 * 导入
	 */
	@Override
	public String importHpmDeptScoreData(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
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
			
			List<AphiDeptScoreData> aphiDeptScoreDataList = aphiDeptScoreDataMapper.queryDeptScoreData(entityMap);
			
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
					
					List<String> f_score = item.get("财务收益");
					List<String> c_score = item.get("客户关系");
					List<String> p_score = item.get("内部流程");
					List<String> l_score = item.get("学习成长");
					List<String> root_score = item.get("综合得分");
					
					
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
					
					if(f_score.get(1) == null){
						return "{\"warn\":\"财务收益为空！\",\"state\":\"false\",\"row_cell\":\" " + f_score.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((f_score.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + f_score.get(1) + ",财务收益输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + f_score.get(0) + "\"}";
						  }
					}
					
					if(c_score.get(1) == null){
						return "{\"warn\":\"客户关系为空！\",\"state\":\"false\",\"row_cell\":\" " + c_score.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((c_score.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + c_score.get(1) + ",客户关系输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + c_score.get(0) + "\"}";
						  }
					}
					
					if(p_score.get(1) == null){
						return "{\"warn\":\"内部流程为空！\",\"state\":\"false\",\"row_cell\":\" " + p_score.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((p_score.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + p_score.get(1) + ",内部流程输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + p_score.get(0) + "\"}";
						  }
					}
					
					if(l_score.get(1) == null){
						return "{\"warn\":\"学习成长为空！\",\"state\":\"false\",\"row_cell\":\" " + l_score.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((l_score.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + l_score.get(1) + ",学习成长输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + l_score.get(0) + "\"}";
						  }
					}
					
					if(root_score.get(1) == null){
						return "{\"warn\":\"综合得分为空！\",\"state\":\"false\",\"row_cell\":\" " + root_score.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((root_score.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + root_score.get(1) + ",综合得分输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + root_score.get(0) + "\"}";
						  }
					}
					
					
					for(AphiDeptScoreData aphiDeptScoreData:aphiDeptScoreDataList){
						if(Character.isDigit(dept_name.get(1).charAt(0))){
							if(deptMap.get(dept_name.get(1)).getDept_id() == aphiDeptScoreData.getDept_id()){
								return "{\"warn\":\"" + dept_name.get(1) + ",科室编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
							}
						}else if(aphiDeptScoreData.getDept_name().equals(dept_name.get(1))){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					
					//以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acct_year.get(1)+"年度," + acct_month.get(1)+"月份," +dept_name.get(1)+"科室名称 ");
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
					
					returnMap.put("f_score", f_score.get(1));
					returnMap.put("c_score", c_score.get(1));
					returnMap.put("p_score", p_score.get(1));
					returnMap.put("l_score", l_score.get(1));
					returnMap.put("root_score", root_score.get(1));
					returnList.add(returnMap);
					break;
					
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				
				aphiDeptScoreDataMapper.addBatchDeptScoreData(returnList);
				
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"msg\":\"操作失败.\",\"state\":\"true\"}");
		}
	}

	@Override
	public List<Map<String, Object>> queryHpmDeptScoreDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if (!"".equals(entityMap.get("acct_yearm")) && entityMap.get("acct_yearm") != null) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		List<Map<String, Object>> list = aphiDeptScoreDataMapper.queryDeptScoreDataPrint(entityMap);
		
		return list;
	}
}
