/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.serviceImpl.dict;

import java.util.ArrayList;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssDictMapper;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050102 资产变更字典
 * @Table: ASS_NO_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */

@Service("assNoDictService")
public class AssNoDictServiceImpl implements AssNoDictService {

	private static Logger logger = Logger.getLogger(AssNoDictServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	@Resource(name = "assDictMapper")
	private final AssDictMapper assDictMapper = null;

	/**
	 * @Description 添加050102 资产变更字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssNoDict(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050102 资产变更字典
		AssNoDict assNoDict = queryAssNoDictByCode(entityMap);

		if (assNoDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assNoDictMapper.addAssNoDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050102 资产变更字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssNoDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assNoDictMapper.addBatchAssNoDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050102 资产变更字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssNoDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assNoDictMapper.updateAssNoDict(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050102 资产变更字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssNoDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assNoDictMapper.updateBatchAssNoDict(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050102 资产变更字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssNoDict(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assNoDictMapper.deleteAssNoDict(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050102 资产变更字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssNoDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assNoDictMapper.deleteBatchAssNoDict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050102 资产变更字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssNoDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssNoDict> list = assNoDictMapper.queryAssNoDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssNoDict> list = assNoDictMapper.queryAssNoDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050102 资产变更字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssNoDict queryAssNoDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assNoDictMapper.queryAssNoDictByCode(entityMap);
	}

	@Override
	public AssNoDict queryAssNoDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assNoDictMapper.queryAssNoDictByUniqueness(entityMap);
	}

	@Override
	public String updateAssNoDictCharge(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assNoDictMapper.updateAssNoDictCharge(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public List<AssNoDict> queryAssNoDictList(Map<String, Object> entityMap) throws DataAccessException {
		return assNoDictMapper.queryAssNoDict(entityMap);
	}

	@Override
	public String queryAssNoDictTree(Map<String, Object> entityMap) throws DataAccessException {

		List<?> list = assDictMapper.queryAssDictTree(entityMap);

		return ChdJson.toJson(list);
	}

	/**
	 * 校验字典的准确性，字段是否符合要求
	 */
	@SuppressWarnings("unused")
	@Override
	public String queryAssDictByCheck(Map<String, Object> entityMap) throws DataAccessException {
		// 校验默认值字段是否为空
		List<Map<String, Object>> check = (List<Map<String, Object>>) assNoDictMapper.queryAssNoDictIsCheckData(entityMap);
		// 存在需要赋默认值的 自动更新为默认值
		if (check != null) {
			assNoDictMapper.updateAssNoDictCheckData(entityMap);
		}

		// 字典表 排查 字段不能为空 是否有默认值 字段关联关系是否匹配
		List<Map<String, Object>> IsDepre = (List<Map<String, Object>>) assNoDictMapper.queryAssNoDictIsDepre(entityMap);
		// 变更表 排查 字段不能为空 是否有默认值 字段关联关系是否匹配
		List<Map<String, Object>> IsManageDepre = (List<Map<String, Object>>) assNoDictMapper.queryAssNoDictIsManageDepre(entityMap);
		// 处理

		List<Map<String, Object>> info = new ArrayList<Map<String, Object>>();

		// 检查折旧相关数据
		for (Map<String, Object> id : IsDepre) {
			Map<String, Object> m = new HashMap<String, Object>();
			StringBuilder id_error = new StringBuilder();
			m.put("ass_code", id.get("ass_code"));
			m.put("ass_name", id.get("ass_name"));
			// 字典表
			if (id.get("is_change").toString().equals("0")) {
				if (id.get("is_depre").toString().equals("0")) {
					if (StringTool.isNotBlank(id.get("depre_years")) || StringTool.isNotBlank(id.get("ass_depre_code"))) {
						id_error.append(" 字典表中 【是否折旧】 为 否  【折旧年限】 与【折旧方法】  不匹配  应该为空！");
					}
				} else {
					if (!StringTool.isNotBlank(id.get("depre_years")) || !StringTool.isNotBlank(id.get("ass_depre_code"))) {
						id_error.append(" 字典表中 【是否折旧】 为 是  【折旧年限】 与【折旧方法】  不匹配  不允许为空！");
					}

				}
			}
			// 变更表
			if (id.get("is_change").toString().equals("1")) {
				if (id.get("is_depre").toString().equals("0")) {
					if (StringTool.isNotBlank(id.get("depre_years")) || StringTool.isNotBlank(id.get("ass_depre_code"))) {
						id_error.append(" 变更表中 【是否折旧】 为 否  【折旧年限】 与【折旧方法】  不匹配  应该为空！");
					}

				} else {
					if (!StringTool.isNotBlank(id.get("depre_years")) || !StringTool.isNotBlank(id.get("ass_depre_code"))) {
						id_error.append(" 变更表中 【是否折旧】 为 是  【折旧年限】 与【折旧方法】  不匹配  不允许为空！");
					}
				}

				if (StringTool.isNotBlank(id_error)) {
					m.put("ass_error", id_error.toString());
					info.add(m);
				}
			}

			// 检查分摊相关数据
			for (Map<String, Object> im : IsManageDepre) {
				Map<String, Object> v_map = new HashMap<String, Object>();
				StringBuilder im_error = new StringBuilder();
				v_map.put("ass_code", im.get("ass_code"));
				v_map.put("ass_name", im.get("ass_name"));
				// 字典表
				if (im.get("is_change").toString().equals("0")) {
					if (im.get("is_manage_depre").toString().equals("0")) {
						if (StringTool.isNotBlank(im.get("manage_depre_amount")) || StringTool.isNotBlank(im.get("manage_depr_method"))) {
							im_error.append(" 字典表中 【是否分摊】 为 否  【分摊年限】 与【分摊方法】  不匹配  应该为空！");
						}
					} else {
						if (!StringTool.isNotBlank(im.get("manage_depre_amount")) || !StringTool.isNotBlank(im.get("manage_depr_method"))) {
							im_error.append(" 字典表中 【是否分摊】 为 是  【分摊年限】 与【分摊方法】  不匹配  不允许为空！");
						}
					}
				}
				// 变更表
				if (im.get("is_change").toString().equals("1")) {
					if (im.get("is_manage_depre").toString().equals("0")) {
						if (StringTool.isNotBlank(im.get("manage_depre_amount")) || StringTool.isNotBlank(im.get("manage_depr_method"))) {
							im_error.append(" 变更表中 【是否分摊】 为 否  【分摊年限】 与【分摊方法】  不匹配  应该为空！");
						}
					} else {
						if (!StringTool.isNotBlank(im.get("manage_depre_amount")) || !StringTool.isNotBlank(im.get("manage_depr_method"))) {
							im_error.append(" 变更表中 【是否分摊】 为 是  【分摊年限】 与【分摊方法】  不匹配不允许为空！");
						}
					}
				}

				if (StringTool.isNotBlank(im_error)) {
					v_map.put("ass_error", im_error.toString());
					info.add(v_map);
				}
			}
		}

		if (info == null) {
			return "{\"msg\":\"未发现异常信息！.\",\"state\":\"true\"}";
		} else {
			return ChdJson.toJson(info);
		}

	}

}
