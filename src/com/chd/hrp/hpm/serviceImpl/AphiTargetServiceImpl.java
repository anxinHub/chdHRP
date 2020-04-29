/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.dao.AphiTargetNatureMapper;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.entity.AphiTargetNature;
import com.chd.hrp.hpm.service.AphiTargetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 9901 绩效指标字典表
 * @Table: PRM_TARGET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiTargetService")
public class AphiTargetServiceImpl implements AphiTargetService {

	private static Logger logger = Logger.getLogger(AphiTargetServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;

	@Resource(name = "aphiTargetNatureMapper")
	private final AphiTargetNatureMapper aphiTargetNatureMapper = null;

	/**
	 * @Description 添加9901 绩效指标字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmTarget(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象9901 根据编码查询绩效指标字典表
		AphiTarget prmTarget = queryPrmTargetByCode(entityMap);

		if (prmTarget != null) {

			return "{\"error\":\"指标编码重复,请重新添加.\"}";

		}

		// 获取对象9901 根据名称查询 绩效指标字典表
		AphiTarget pt = aphiTargetMapper.queryPrmTargetByName(entityMap);

		if (pt != null) {

			return "{\"error\":\"指标名称重复,请重新添加.\"}";

		}

		try {

			int state = aphiTargetMapper.addPrmTarget(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmTarget\"}";

		}

	}

	/**
	 * @Description 批量添加9901 绩效指标字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmTarget(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiTargetMapper.addBatchPrmTarget(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmTarget\"}";

		}

	}

	/**
	 * @Description 更新9901 绩效指标字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmTarget(Map<String, Object> entityMap) throws DataAccessException {

		try {

			// 获取对象9901 根据名称查询 绩效指标字典表
//			AphiTarget pt = aphiTargetMapper.queryPrmTargetByName(entityMap);
//
//			if (pt != null) {
//
//				return "{\"error\":\"指标名称重复.\"}";
//
//			}

			int state = aphiTargetMapper.updatePrmTarget(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmTarget\"}";

		}

	}

	/**
	 * @Description 批量更新9901 绩效指标字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmTarget(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiTargetMapper.updateBatchPrmTarget(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmTarget\"}";

		}

	}

	/**
	 * @Description 删除9901 绩效指标字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmTarget(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = aphiTargetMapper.deletePrmTarget(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmTarget\"}";

		}

	}

	/**
	 * @Description 批量删除9901 绩效指标字典表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmTarget(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			aphiTargetMapper.deleteBatchPrmTarget(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmTarget\"}";

		}
	}

	/**
	 * @Description 查询结果集9901 绩效指标字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmTarget(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiTarget> list = aphiTargetMapper.queryPrmTarget(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiTarget> list = aphiTargetMapper.queryPrmTarget(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象9901 绩效指标字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AphiTarget queryPrmTargetByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiTargetMapper.queryPrmTargetByCode(entityMap);
	}

	@Override
	public String queryPrmTargetNature(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiTarget> list = aphiTargetMapper.queryPrmTargetNature(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiTarget> list = aphiTargetMapper.queryPrmTargetNature(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * @Description 获取对象9901 绩效指标字典表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AphiTarget queryPrmTargetNatureGetByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return aphiTargetMapper.queryPrmTargetNatureGetByCode(entityMap);
	}

	@Override
	public String queryTargetTree(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List list = aphiTargetMapper.queryPrmTarget(entityMap);

		List listNature = aphiTargetMapper.queryPrmTargetNatureDanbiao(entityMap);

		// 拼接SQL
		StringBuilder json = new StringBuilder();

		json.append("{Rows:[");

		for (int i = 0; i < listNature.size(); i++) {

			AphiTarget t = (AphiTarget) listNature.get(i);

			json.append("{");

			json.append("\"pid\":\"0\"," + "\"id\":\"" + t.getNature_code() + "\"," + "\"text\":" + "\"" + t.getNature_name() + "\"");

			json.append("},");
		}

		for (int j = 0; j < list.size(); j++) {

			AphiTarget tn = (AphiTarget) list.get(j);

			json.append("{");

			json.append("\"pid\":\"" + tn.getTarget_nature() + "\"," + "\"id\":\"" + tn.getTarget_code() + "\"," + "\"text\":" + "\"" + tn.getTarget_name()
					+ "\"");

			json.append("},");
		}

		json.setCharAt(json.length() - 1, ']');
		json.append("}");

		return json.toString();
		// return JsonListBeanUtil.listToJson();
	}

	// 导入
	@Override
	public String hpmTargetImport(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			String content = map.get("content").toString();

			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);

			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			// new Map查询导入时对应数据信息
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("is_stop", "0");// 查询未停用

			// 查询导入表，防止子弹重复
			List<AphiTarget> aphiTargetList = aphiTargetMapper.queryPrmTargetNature(entityMap);

			List<AphiTargetNature> aphiTargetNatureList = aphiTargetNatureMapper.queryPrmTargetNature(entityMap);

			// 装入
			Map<String, AphiTarget> aphiTargetMap = new HashMap<String, AphiTarget>();

			//
			Map<String, AphiTargetNature> aphiTargetNatureMap = new HashMap<String, AphiTargetNature>();

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();

			// 存储要添加保存的数据List
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();

			// 指标性质存放到map中，验证不存在指标性质
			for (AphiTargetNature aphiTargetNature : aphiTargetNatureList) {
				aphiTargetNatureMap.put(aphiTargetNature.getNature_code(), aphiTargetNature);
			}

			for (Map<String, List<String>> item : liData) {
				for (String st : item.keySet()) {
					if (item.get(st).get(1) == null) {
						break;
					}

					List<String> target_code = item.get("指标编码");
					//List<String> nature_code = item.get("指标性质编码");
					List<String> target_name = item.get("指标名称");
					List<String> target_nature = item.get("指标性质");
					List<String> target_note = item.get("指标描述");

					if (target_code.get(1) == null) {
						return "{\"warn\":\"指标编码为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					} else if (target_name.get(1) == null) {
						return "{\"warn\":\"指标名称为空！\",\"state\":\"false\",\"row_cell\":\"" + target_name.get(0) + "\"}";
					} else if (target_nature.get(1) == null) {
						return "{\"warn\":\"指标性质为空！\",\"state\":\"false\",\"row_cell\":\"" + target_nature.get(0) + "\"}";
					} 
					if (aphiTargetNatureMap.get(target_nature.get(1)) == null) {
						return "{\"warn\":\"指标性质不存在！\",\"state\":\"false\",\"row_cell\":\"" + target_nature.get(0) + "\"}";
					}

					// 判断导入表是否存在
					for (AphiTarget aphiTarget : aphiTargetList) {
						if (target_code.get(1).equals(aphiTarget.getTarget_code())) {
							return "{\"warn\":\"指标编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
						} else if (target_name.get(1).equals(aphiTarget.getTarget_name())) {
							return "{\"warn\":\"指标名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + target_name.get(0) + "\"}";
						}
					}

					// 判断导入数据是否重复
					String key = target_code.get(1);
					if (exitMap.get(key) != null) {
						err_sb.append(target_code.get(1) + "指标名称");
					} else {
						exitMap.put(key, key);
					}

					// 添加数据Map中add到returnList
					Map<String, Object> returnMap = new HashMap<String, Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("target_code", target_code.get(1));
					returnMap.put("nature_code", "");
					returnMap.put("target_nature", target_nature.get(1));
					returnMap.put("target_name", target_name.get(1));
					returnMap.put("target_note", target_note.get(1));
					returnMap.put("spell_code", StringTool.toPinyinShouZiMu(target_name.get(1)));
					returnMap.put("wbx_code", StringTool.toWuBi(target_name.get(1)));
					returnMap.put("is_stop", 0);

					returnList.add(returnMap);
					break;

				}
			}

			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {

				aphiTargetMapper.addBatchPrmTarget(returnList);

				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

}
