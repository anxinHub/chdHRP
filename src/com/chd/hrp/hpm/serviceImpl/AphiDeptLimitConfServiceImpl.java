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
import com.chd.hrp.hpm.dao.AphiDeptLimitConfMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptLimitConf;
import com.chd.hrp.hpm.service.AphiDeptLimitConfService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptLimitConfService")
public class AphiDeptLimitConfServiceImpl implements AphiDeptLimitConfService {

	private static Logger logger = Logger.getLogger(AphiDeptLimitConfServiceImpl.class);

	@Resource(name = "aphiDeptLimitConfMapper")
	private final AphiDeptLimitConfMapper aphiDeptLimitConfMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	/**
	 * 
	 */
	@Override
	public String addDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		AphiDeptLimitConf dlc = queryDeptLimitConfByCode(entityMap);

		if (dlc != null) {

			return "{\"error\":\"科室编码：" + entityMap.get("dept_id").toString() + "重复.\"}";

		}

		try {

			aphiDeptLimitConfMapper.addDeptLimitConf(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addDeptLimitConf\"}");

			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addDeptLimitConf\"}";
		}

	}

	/**
	 * 
	 */
	@Override
	public String queryDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiDeptLimitConfMapper.queryDeptLimitConf(entityMap, rowBounds), sysPage.getTotal());

	}

	/**
	 * 
	 */
	@Override
	public AphiDeptLimitConf queryDeptLimitConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptLimitConfMapper.queryDeptLimitConfByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptLimitConf(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_array = codes.split(",");

				for (int i = 0; i < code_array.length; i++) {

					entityMap.put("dept_id", code_array[i].toString().split("@")[0]);
					entityMap.put("dept_no", code_array[i].toString().split("@")[1]);
					aphiDeptLimitConfMapper.deleteDeptLimitConf(entityMap);

				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptLimitConf\"}");

			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptLimitConf\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException {

		try {

			aphiDeptLimitConfMapper.updateDeptLimitConf(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptLimitConf\"}");

			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptLimitConf\"}";
		}
	}

	@Override
	public String createDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 先清空数据库
		aphiDeptLimitConfMapper.clearDeptLimitConf(entityMap);

		// 生成数据
		List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

		try {

			for (int i = 0; i < allDept.size(); i++) {

				AphiDeptDict dept = allDept.get(i);

				entityMap.put("dept_id", dept.getDept_id());
				entityMap.put("dept_no", dept.getDept_no());

				entityMap.put("is_limit", "0");

				aphiDeptLimitConfMapper.addDeptLimitConf(entityMap);
			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"msg\":\"生成失败.\",\"state\":\"false\"}");
			//return "{\"msg\":\"生成失败.\",\"state\":\"false\"}";

		}

	}

	@Override
	public String fastDeptLimitConf(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {
			// 生成数据
			List<AphiDeptDict> allDept = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			try {

				for (int i = 0; i < allDept.size(); i++) {

					AphiDeptDict dept = allDept.get(i);

					entityMap.put("dept_id", dept.getDept_id());
					entityMap.put("dept_no", dept.getDept_no());
					entityMap.put("lower_money", entityMap.get("lower_money"));
					entityMap.put("upper_money", entityMap.get("upper_money"));

					aphiDeptLimitConfMapper.updateDeptLimitConf(entityMap);
				}

				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}
		} else {

			String codes = (String) entityMap.get("checkIds");

			String[] code_array = codes.split(",");

			try {

				for (int i = 0; i < code_array.length; i++) {

					entityMap.put("dept_id", code_array[i].toString().split("@")[0]);
					entityMap.put("dept_no", code_array[i].toString().split("@")[1]);
					entityMap.put("lower_money", entityMap.get("lower_money"));
					entityMap.put("upper_money", entityMap.get("upper_money"));

					aphiDeptLimitConfMapper.updateDeptLimitConf(entityMap);

				}
				return "{\"msg\":\"快速填充成功.\",\"state\":\"true\"}";

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"msg\":\"生成失败.\",\"state\":\"false\"}");
				//return "{\"msg\":\"快速填充失败.\",\"state\":\"false\"}";

			}

		}

	}

	@Override
	public String addBatchDeptLimitConf(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiDeptLimitConfMapper.addBatchDeptLimitConf(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	@Override
	public String hpmDeptLimitConfImport(Map<String, Object> map)throws DataAccessException {
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
				
				//以键值对的形式存储,用于判断科室是否存在
				Map<String,AphiDeptDict> deptMap = new HashMap<String, AphiDeptDict>();
			
				//查询存在科室核对已存在科室
				List<AphiDeptLimitConf> aphiDeptLimitCofList = aphiDeptLimitConfMapper.queryDeptLimitConf(entityMap);
				
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
						List<String> is_limit  = item.get("是否控制");
						List<String> lower_money  = item.get("保底线");
						List<String> upper_money = item.get("封顶线");
						
						
						if(dept_name.get(1) == null){
							return "{\"warn\":\"科室名称单元格为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
						}else if(Character.isDigit(dept_name.get(1).charAt(0))){
							if(deptMap.get(dept_name.get(1)) == null){
								return "{\"warn\":\"" + dept_name.get(1) + ",科室编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
							}
						}else if(deptMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
						//判断科室已经存在
						for(AphiDeptLimitConf aphiDeptLimitConf :aphiDeptLimitCofList){
							if(Character.isDigit(dept_name.get(1).charAt(0))){
								if(deptMap.get(dept_name.get(1)).getDept_id() == aphiDeptLimitConf.getDept_id()){
									return "{\"warn\":\"" + dept_name.get(1) + ",科室编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
								}
							}else if(dept_name.get(1).equals(aphiDeptLimitConf.getDept_name())){
								return "{\"warn\":\"" + dept_name.get(1) + ",科室名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
							}
							
						}
						
						if (is_limit.get(1) == null) {
							return "{\"warn\":\"是否控制为空！\",\"state\":\"false\",\"row_cell\":\" " + is_limit.get(0) + "\"}";
						}else if(Character.isDigit(is_limit.get(1).charAt(0))){
							is_limit.add(1,(is_limit.get(1).equals("1")?is_limit.get(1).replace("1","是"):is_limit.get(1).replace("0","否")));
						}
						
						if(lower_money.get(1) == null){
							return "{\"warn\":\"保底线为空！\",\"state\":\"false\",\"row_cell\":\" " + lower_money.get(0) + "\"}";
						}else{
							try{
								 Double.parseDouble((lower_money.get(1)));
							 }catch(NumberFormatException e){
								 return "{\"warn\":\"" + lower_money.get(1) + ",保底线输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + lower_money.get(0) + "\"}";
							  }
						}
						
						if(upper_money.get(1) == null){
							return "{\"warn\":\"封顶线为空！\",\"state\":\"false\",\"row_cell\":\" " + upper_money.get(0) + "\"}";
						}else{
							try{
								 Double.parseDouble((upper_money.get(1)));
							 }catch(NumberFormatException e){
								 return "{\"warn\":\"" + upper_money.get(1) + ",封顶线输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + upper_money.get(0) + "\"}";
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
						returnMap.put("is_limit", (is_limit.get(1).equals("是")?1:0));
						returnMap.put("lower_money", lower_money.get(1));
						returnMap.put("upper_money", upper_money.get(1));
						
						returnList.add(returnMap);
						break;
					}
				}
				
				if (err_sb.toString().length() > 0 ) {
					return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
				}else{
					aphiDeptLimitConfMapper.addBatchDeptLimitConf(returnList);
					return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
				}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
