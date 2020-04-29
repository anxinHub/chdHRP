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
import com.chd.hrp.hpm.dao.AphiDeptBalancePercConfMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.entity.AphiDeptBalancePercConf;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.service.AphiDeptBalancePercConfService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptBalancePercConfService")
public class AphiDeptBalancePercConfServiceImpl implements AphiDeptBalancePercConfService {

	private static Logger logger = Logger.getLogger(AphiDeptBalancePercConfServiceImpl.class);

	@Resource(name = "aphiDeptBalancePercConfMapper")
	private final AphiDeptBalancePercConfMapper aphiDeptBalancePercConfMapper = null;
	
	@Resource(name = "aphiDeptDictMapper") 
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			AphiDeptBalancePercConf dbpc = aphiDeptBalancePercConfMapper.queryDeptBalancePercConfByCode(entityMap);
			
			if(dbpc !=null){
				
				return "{\"msg\":\"已存在该科室配置.\",\"state\":\"error\"}";
				
			}
			
			aphiDeptBalancePercConfMapper.addDeptBalancePercConf(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addDeptBalancePercConf\"}";
			
		}
	}

	/**
	 * 
	 */
	@Override
	public String queryDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiDeptBalancePercConfMapper.queryDeptBalancePercConf(entityMap, rowBounds), sysPage.getTotal());
		
	}

	/**
	 * 
	 */
	@Override
	public AphiDeptBalancePercConf queryDeptBalancePercConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiDeptBalancePercConfMapper.queryDeptBalancePercConfByCode(entityMap);
		
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptBalancePercConf(Map<String, Object> entityMap,String codes) throws DataAccessException {

		try{
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {	
					
					entityMap.put("dept_id", code.split("@")[0]);
					entityMap.put("dept_no", code.split("@")[1]);
					aphiDeptBalancePercConfMapper.deleteDeptBalancePercConf(entityMap);
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} else {
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
				
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptBalancePercConf\"}";
			
		}
		
	}

	/**
	 * 
	 */
	@Override
	public String updateDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			
			aphiDeptBalancePercConfMapper.updateDeptBalancePercConf(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  updateDeptBalancePercConf\"}";
			
		}
	}

	@Override
	public String createDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		//先清空数据库
		aphiDeptBalancePercConfMapper.clearDeptBalancePercConf(entityMap);
		
		//生成数据
		List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
		try{
		
			for(int i = 0 ;i < allDept.size(); i++){
				
				AphiDeptDict dept = allDept.get(i);
				
				entityMap.put("dept_id", dept.getDept_id());
				entityMap.put("dept_no", dept.getDept_no());
				//entityMap.put("dept_percent", "");
				
				aphiDeptBalancePercConfMapper.addDeptBalancePercConf(entityMap);
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";
			
		}

	}

	@Override
    public String fastDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String rbtnl = (String)entityMap.get("rbtnl");
		
		if("all".equals(rbtnl)){
			// 生成数据
			List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			try {

				for (int i = 0; i < allDept.size(); i++) {

					AphiDeptDict dept = allDept.get(i);

					entityMap.put("dept_id", dept.getDept_id());
					entityMap.put("dept_no", dept.getDept_no());
					entityMap.put("dept_percent", entityMap.get("dept_percent"));

					aphiDeptBalancePercConfMapper.updateDeptBalancePercConf(entityMap);
				}

				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}
		}else{
			
			String codes= (String)entityMap.get("checkIds");
			
			String[] code_array = codes.split(",");
			
			try {

				for(int i=0; i <code_array.length;i++){
					
					entityMap.put("dept_id",code_array[i].toString().split("@")[0]);
					entityMap.put("dept_no",code_array[i].toString().split("@")[1]);
	
					entityMap.put("dept_percent", entityMap.get("dept_percent"));
	
					aphiDeptBalancePercConfMapper.updateDeptBalancePercConf(entityMap);
					
				}
				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}
			
		}

    }

	@Override
	public String addBatchDeptBalancePercConf(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiDeptBalancePercConfMapper.addBatchDeptBalancePercConf(entityMap);
		if(state>0){
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	//导入
	@Override
	public String hpmdeptBalancePercConfImport(Map<String, Object> map)throws DataAccessException {
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
			
			//判断科室已经存在计提比例
			List<AphiDeptBalancePercConf> deptBalancePercConfList = aphiDeptBalancePercConfMapper.queryDeptBalancePercConf(entityMap);
			
			//以键值对的形式存储,用于判断
			Map<String,AphiDeptBalancePercConf> deptBalancePercConfMap = new HashMap<String, AphiDeptBalancePercConf>();
			
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
			
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					List<String> dept_name = item.get("科室名称");
					List<String> dept_percent = item.get("计提比例");
					
					
					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室名称单元格为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else if(Character.isDigit(dept_name.get(1).charAt(0))){
						if(deptMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}else if(deptMap.get(dept_name.get(1)) == null){
						return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					}
					
					//比对要插入的表，判断科室已经存在(dept_name可能是名称或Long类型ID)
					for(AphiDeptBalancePercConf deptBalance :deptBalancePercConfList){
						if(Character.isDigit(dept_name.get(1).charAt(0))){
							if(deptMap.get(dept_name.get(1)).getDept_id() == deptBalance.getDept_id()){
								return "{\"warn\":\"" + dept_name.get(1) + ",科室编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
							}
						}else if(dept_name.get(1).equals(deptBalance.getDept_name())){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					
					}
					
					if(dept_percent.get(1) == null){
						return "{\"warn\":\"计提比例为空！\",\"state\":\"false\",\"row_cell\":\" " + dept_percent.get(0) + "\"}";
					}else{
						try{
							 Double.parseDouble((dept_percent.get(1)));
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + dept_percent.get(1) + ",计提比例输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + dept_percent.get(0) + "\"}";
						  }
					}
					
					//以收入科室名称为键值,判断导入数据是否重复
					String key = dept_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(dept_name.get(1)+"科室名称 ");
					}else{
						exitMap.put(key, key);
					}
					
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
					returnMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					returnMap.put("dept_percent",dept_percent.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiDeptBalancePercConfMapper.addBatchDeptBalancePercConf(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			
		}
	}
}
