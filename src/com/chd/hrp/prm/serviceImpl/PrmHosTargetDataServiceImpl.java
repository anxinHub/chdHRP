
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.castor.castor.Array2Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.prm.dao.PrmHosTargetDataMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.dao.PrmTargetMethodMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmHosTargetData;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.entity.PrmTargetMethod;
import com.chd.hrp.prm.service.PrmHosTargetDataService;
import com.chd.hrp.sys.dao.InfoDictMapper;
import com.chd.hrp.sys.dao.InfoMapper;
import com.chd.hrp.sys.entity.HosDict;
import com.chd.hrp.sys.entity.Info;
import com.chd.hrp.sys.entity.InfoDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0212 院级绩效指标数据表
 * @Table: PRM_HOS_TARGET_DATA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmHosTargetDataService")
public class PrmHosTargetDataServiceImpl implements PrmHosTargetDataService {

	private static Logger logger = Logger.getLogger(PrmHosTargetDataServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmHosTargetDataMapper")
	private final PrmHosTargetDataMapper prmHosTargetDataMapper = null;

	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper prmTargetMapper = null;

	@Resource(name = "infoMapper")
	private final InfoMapper infoMapper = null;

	/**
	 * @Description 添加0212 院级绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0212 院级绩效指标数据表
		PrmHosTargetData prmHosTargetData = queryPrmHosTargetDataByCode(entityMap);

		if (prmHosTargetData != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = prmHosTargetDataMapper.addPrmHosTargetData(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosTargetData\"}";

		}

	}

	/**
	 * @Description 批量添加0212 院级绩效指标数据表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmHosTargetData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosTargetDataMapper.addBatchPrmHosTargetData(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosTargetData\"}";

		}

	}

	/**
	 * @Description 更新0212 院级绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmHosTargetDataMapper.updatePrmHosTargetData(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosTargetData\"}";

		}

	}

	/**
	 * @Description 批量更新0212 院级绩效指标数据表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmHosTargetData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosTargetDataMapper.updateBatchPrmHosTargetData(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosTargetData\"}";

		}

	}

	@Override
	public String saveBatchPrmHosTargetData(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int state = prmHosTargetDataMapper.updateBatchPrmHosTargetData(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 saveBatchPrmHosTargetData\"}";

		}
	}

	/**
	 * @Description 删除0212 院级绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = prmHosTargetDataMapper.deletePrmHosTargetData(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosTargetData\"}";

		}

	}

	/**
	 * @Description 批量删除0212 院级绩效指标数据表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmHosTargetData(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmHosTargetDataMapper.deleteBatchPrmHosTargetData(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosTargetData\"}";

		}
	}

	/**
	 * @Description 查询结果集0212 院级绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosTargetData> list = prmHosTargetDataMapper.queryPrmHosTargetData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosTargetData> list = prmHosTargetDataMapper.queryPrmHosTargetData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 查询结果集0212 院级绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosTargetPrmTargetData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<PrmHosTargetData> list = prmHosTargetDataMapper.queryPrmHosTargetPrmTargetData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosTargetData> list = prmHosTargetDataMapper.queryPrmHosTargetPrmTargetData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * @Description 获取对象0212 院级绩效指标数据表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmHosTargetData queryPrmHosTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException {

		return prmHosTargetDataMapper.queryPrmHosTargetDataByCode(entityMap);
	}

	@Override
	public String auditPrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub


		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmHosTargetDataMapper.auditPrmHosTargetData(entityMap);
				
			}else{//有选择数据、按选择的数据审核
				
				JSONArray checkDataJson = JSONArray.parseArray((String) entityMap.get("checkedRows"));//获取选择的审核数据
				Iterator iterator = checkDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					mapVo.put("group_id", jsonObj.get("group_id"));
					mapVo.put("hos_id", jsonObj.get("hos_id"));
					mapVo.put("copy_code", jsonObj.get("copy_code"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
					mapVo.put("check_hos_id", jsonObj.get("check_hos_id"));
					mapVo.put("target_code", jsonObj.get("target_code"));
					
					mapVo.put("is_audit", entityMap.get("is_audit"));
					mapVo.put("user_code", entityMap.get("user_code"));
					mapVo.put("audit_date", entityMap.get("audit_date"));
					
					prmHosTargetDataMapper.auditPrmHosTargetData(mapVo);
				}
			}
			
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}

	@Override
	public String reAuditPrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub


		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmHosTargetDataMapper.auditPrmHosTargetData(entityMap);
				
			}else{//有选择数据、按选择的数据审核
				
				JSONArray checkDataJson = JSONArray.parseArray((String) entityMap.get("checkedRows"));//获取选择的审核数据
				Iterator iterator = checkDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					mapVo.put("group_id", jsonObj.get("group_id"));
					mapVo.put("hos_id", jsonObj.get("hos_id"));
					mapVo.put("copy_code", jsonObj.get("copy_code"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
					mapVo.put("check_hos_id", jsonObj.get("check_hos_id"));
					mapVo.put("target_code", jsonObj.get("target_code"));
					
					mapVo.put("is_audit", 0);
					mapVo.put("user_code","");
					mapVo.put("audit_date","");
					
					prmHosTargetDataMapper.auditPrmHosTargetData(mapVo);
				}
			}
			
			
			return "{\"msg\":\"反审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}

	@Override
	public String createPrmHosTargetData(Map<String, Object> entityMap, String paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> mapVo = new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		mapVo.put("acc_year", paramVo.substring(0, 4));
		mapVo.put("acc_month", paramVo.substring(4, 6));

		prmHosTargetDataMapper.cleanPrmHosTargetData(mapVo);
		int state = 0;
		/* 01院级 */
		entityMap.put("target_nature", "01");

		Map<String, List<PrmTarget>> prmTargetMapVo = new HashMap<String, List<PrmTarget>>();

		/* 查询全院手动录入基本指标 */
		List<PrmTarget> prmTarget = prmTargetMapper.queryPrmTargetNatureCreate(entityMap);

		prmTargetMapVo.put("target_code", prmTarget);

		/* 查询考核单位 */

		entityMap.put("hos_id", "");

		List<Info> info = infoMapper.queryInfo(entityMap);

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (Info Info2 : info) {

			for (Iterator<String> iterator = prmTargetMapVo.keySet().iterator(); iterator.hasNext();) {

				String key = iterator.next();

				List<PrmTarget> l = prmTargetMapVo.get(key);

				for (int i = 0; i < l.size(); i++) {

					mapVo.put("target_code", l.get(i).getTarget_code().toString());
					mapVo.put("check_hos_id", Info2.getHos_id());
					mapVo.put("target_value", 0);
					mapVo.put("is_audit", 0);
					mapVo.put("user_code", "");
					mapVo.put("audit_date", "");

					state = prmHosTargetDataMapper.addPrmHosTargetData(mapVo);
				}

			}
		}

		if (state > 0) {
			return "{\"msg\":\"院级指标数据生成成功.\",\"state\":\"true\"}";
		} else {
			return "{\"warn\":\"没有生成数据!\"}";
		}
	}
	
	
	@Override
	public String importPrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> queryMap =new HashMap<String,Object>();
			queryMap.put("group_id", SessionManager.getGroupId());
			queryMap.put("hos_id", SessionManager.getHosId());
			queryMap.put("copy_code", SessionManager.getCopyCode());
			
			// 3.查询 基本指标字典 List
			List<PrmTarget> targetList = prmTargetMapper.queryPrmTarget(entityMap);
			//用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, PrmTarget> targetMap = new HashMap<String, PrmTarget>();
			//将指标List存入Map   键:target_code 值:AphiTarget
			for(PrmTarget target : targetList){
				targetMap.put(target.getTarget_code(), target);
				targetMap.put(target.getTarget_name(), target);
			}
						
			
			//4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的指标
			Map<String,String> targetColumnMap = new HashMap<String,String>();//用于存储表头中的指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					if("年度".equals(key) || "月份".equals(key) || "医院编码".equals(key)){
						continue;
					}
					
					targetColumnMap.put(key, key);
					if(targetMap.get(key) == null){
						sb.append("指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(targetColumnMap == null || targetColumnMap.size() == 0){
				return "{\"error\":\"表头中未存在指标或未填写任何指标\",\"state\":\"false\"}";
			}
			
			
			//表头含有不存在指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			List<PrmHosTargetData> phtdList = prmHosTargetDataMapper.queryPrmHosTargetData(entityMap);
			//5.以年、月、医院id、指标编码作为键,phtd对象作为值,判断数据是否存在
			Map<String,PrmHosTargetData> phtdMap = new HashMap<String,PrmHosTargetData>();
			for(PrmHosTargetData phtd : phtdList){
				
				String key = String.valueOf(phtd.getAcc_year()) +
						String.valueOf(phtd.getAcc_month()) + 
						String.valueOf(phtd.getHos_id()) + 
						String.valueOf(phtd.getTarget_code());
				
				phtdMap.put(key, phtd);
			}
			
			//查询所有医院
			List<HosDict> hosDictList = prmHosTargetDataMapper.queryHosInfoByGroupId(queryMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,HosDict> hosDictMap = new HashMap<String,HosDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(HosDict hosDict : hosDictList){
				hosDictMap.put(hosDict.getHos_code(), hosDict);
			}
			
			
			//6.组装数据
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			//存储添加数据List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			//存储修改数据List
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			for(Map.Entry<String, String> entry:targetColumnMap.entrySet()){//遍历指标
				
				//遍历导入数据
				for(Map<String,List<String>> item : liData ){
					
					List<String> acc_year = item.get("年度") ;
					List<String> acc_month = item.get("月份") ;
					List<String> hos_code = item.get("医院编码") ;
					List<String> target_code = item.get(entry.getKey()) ;//指标
					
					if(acc_year == null || acc_month == null || hos_code == null){
						continue;
					}
					
					if(acc_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" +acc_year.get(0) +"\"\"}";
					}
					
					if(acc_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acc_month.get(0) +"\"}";
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					}
					
					if(hos_code.get(1) == null){
						return "{\"warn\":\"医院编码为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else{
						if(hosDictMap.get(hos_code.get(1)) == null){
							return "{\"warn\":\"" + hos_code.get(1) + ",医院编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + hos_code.get(0) + "\"}";
						}
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\" " + target_code.get(0) + "\"}";
					}else{
						 try{
							 Double.parseDouble((target_code.get(1)));//校验是否为数值
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + target_code.get(1) + ",指标值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
						 }
					}
					
					//以年度|月份|指标编码|医院编码为键值,判断导入数据是否重复
					String key = acc_year.get(1) + "|" +acc_month.get(1) + "|" + targetMap.get(entry.getKey()).getTarget_code() + "|" + hos_code.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acc_year.get(1)+"年度," + acc_month.get(1)+"月份," + hos_code.get(1)+"医院编码," + entry.getKey()+"指标<br/>");
					}else{
						exitMap.put(key, key);
					}
					
					
					//添加数据Map
					Map<String,Object> addMap = new HashMap<String,Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("acc_year", acc_year.get(1));
					addMap.put("acc_month", acc_month.get(1));
					addMap.put("target_code",targetMap.get(entry.getKey()).getTarget_code());
					addMap.put("check_hos_id",hosDictMap.get(hos_code.get(1)).getHos_id());
					
					//addMap.put("target_name", dictMap.get(target_code.get(1)).getTarget_name());
					addMap.put("target_value", target_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("user_code", "");
					addMap.put("audit_date","");
					
					//根据年+月+科室id+科室变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = 
							String.valueOf(addMap.get("acc_year")) +
							String.valueOf(addMap.get("acc_month")) +
							String.valueOf(addMap.get("check_hos_id")) +
							String.valueOf(addMap.get("target_code")) ;
					
					
					PrmHosTargetData phtd = phtdMap.get(is_exit_key);
					if(phtd == null){//不存在,添加
						
						addList.add(addMap);
					}else{
						
						if( !"1".equals(String.valueOf(phtd.getIs_audit())) ){//存在,如果未审核,添加到修改List
							
							updateList.add(addMap);
						}
					}
				}
			}
			
			if( err_sb.length() > 0){//重复数据是否存在
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(addList.size() > 0){
				
				prmHosTargetDataMapper.addBatchPrmHosTargetData(addList);
			}
			
			if(updateList.size() > 0){
				
				prmHosTargetDataMapper.updateBatchPrmHosTargetData(updateList);
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"导入失败.\"}");
		}
		
	}

}
