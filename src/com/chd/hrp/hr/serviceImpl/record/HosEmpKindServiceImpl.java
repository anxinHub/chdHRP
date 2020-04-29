package com.chd.hrp.hr.serviceImpl.record;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.record.HosEmpKindMapper;
import com.chd.hrp.hr.entity.record.HosEmpKind;
import com.chd.hrp.hr.entity.record.HrEmpType;
import com.chd.hrp.hr.entity.sysstruc.HrTabType;
import com.chd.hrp.hr.service.record.HosEmpKindService;
import com.github.pagehelper.PageInfo;

@Service("hosEmpKindService")
public class HosEmpKindServiceImpl implements HosEmpKindService {
	private static Logger logger = Logger.getLogger(HosEmpKindServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hosEmpKindMapper")
	private final HosEmpKindMapper hosEmpKindMapper = null;

	/**
	 * @Description 添加<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
	/*	entityMap.put("group_id ", SessionManager.getGroupId());
		entityMap.put("hos_id ", SessionManager.getHosId());
		entityMap.put("copy_code ", SessionManager.getCopyCode());*/

		// 获取对象

		List<HosEmpKind> list = hosEmpKindMapper.queryEmpKindById(entityMap);

		if (list.size() > 0) {

			for (HosEmpKind hrDutyLevel : list) {
				if (hrDutyLevel.getKind_code().equals(entityMap.get("kind_code"))) {
					return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() + "已存在.\"}";
				}
				if (hrDutyLevel.getKind_name().equals(entityMap.get("kind_name"))) {
					return "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}";
				}
			}
		}
		try {

			 hosEmpKindMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	
	/**
	 * @Description 更新<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			if("1".equals(entityMap.get("is_change").toString())){
				Map<String,Object> empKind = hosEmpKindMapper.queryEmpKindByName(entityMap);
				if(empKind != null){
					if(empKind.get("kind_name").toString().equals(entityMap.get("kind_name").toString())){
						return "{\"error\":\"名称：" + entityMap.get("kind_name").toString() + "已存在.\"}";
					}
				}
			}
			
			int state = hosEmpKindMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}

	}


	
	

	/**
	 * @Description 查询结果集<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HosEmpKind> list = (List<HosEmpKind>) hosEmpKindMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HosEmpKind> list = (List<HosEmpKind>) hosEmpKindMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public HosEmpKind queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hosEmpKindMapper.queryByCode(entityMap);
	}

	
	// 删除人员分类
	@Override
	public String deleteBatchHosEmpKind(List<HosEmpKind> entityList) throws DataAccessException {

		try {

			hosEmpKindMapper.deleteBatchHosEmpKind(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * 左侧树形
	 */
	@Override
	public String queryHosEmpKinkTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HosEmpKind> storeTypeList = (List<HosEmpKind>) hosEmpKindMapper.query(entityMap);
		for (HosEmpKind storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");

		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 导入数据
	 */
	@Override
	public String importDate(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					
					//过滤空行
					if("".equals(map.get("kind_code").get(1)) || "".equals(map.get("kind_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("kind_code").get(1)) || "null".equals(map.get("kind_name").get(1))){
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("kind_code", map.get("kind_code").get(1));
					saveMap.put("kind_name", map.get("kind_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("kind_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("kind_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					HosEmpKind type = hosEmpKindMapper.queryByCode(saveMap);
					
					if(type != null){
						String kc = type.getKind_code();
						// type不是null,kind_code、kind_name一定有一个与当前循环体中map的kind_code、kind_name相同
						if(kc.equals(saveMap.get("kind_code"))){
							failureMsg.append("<br/>类别代码 " + kc + " 已存在;");
						}else{
							failureMsg.append("<br/>类别名称 " + type.getKind_name() + " 已存在;");
						}
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hosEmpKindMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public List<Map<String, Object>> queryEmpKind(Map<String, Object> mapVo) throws DataAccessException {
		try{
			List<Map<String, Object>> resultMap = hosEmpKindMapper.queryEmpKind(mapVo);
			return resultMap;
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}
	
}
