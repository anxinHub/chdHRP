/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.serviceImpl.base.budgcostfasset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.budg.dao.base.budgcostfasset.BudgCostFassetTypeMapper;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgCostFassetTypeService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050101 资产分类字典
 * @Table: ASS_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("budgCostFassetTypeService")
public class BudgCostFassetTypeServiceImpl implements BudgCostFassetTypeService {

	private static Logger logger = Logger.getLogger(BudgCostFassetTypeServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "budgCostFassetTypeMapper")
	private final BudgCostFassetTypeMapper budgCostFassetTypeMapper = null;

	// 引入DAO操作
	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	/**
	 * @Description 添加050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("ass_type_code", entityMap.get("ass_type_code"));
		// 判断编码是否重复
		AssTypeDict data_exc_extis_code = budgCostFassetTypeMapper.queryByUniqueness(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}
		Map<String, Object> map_name = new HashMap<String, Object>();
		map_name.put("group_id", entityMap.get("group_id"));
		map_name.put("hos_id", entityMap.get("hos_id"));
		map_name.put("copy_code", entityMap.get("copy_code"));
		map_name.put("ass_type_name", entityMap.get("ass_type_name"));

		AssTypeDict data_exc_extis_name = budgCostFassetTypeMapper.queryByUniqueness(map_name);

		if (data_exc_extis_name != null) {

			return "{\"error\":\"名称重复,请重新添加.\"}";

		}

		// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
		if (StringTool.isNotBlank(entityMap.get("super_code"))) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("ass_type_code", entityMap.get("super_code"));

			AssTypeDict data_exc_extis_super = budgCostFassetTypeMapper.queryByUniqueness(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("ass_naturs", data_exc_extis_super.getAss_naturs());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("ass_type_id", data_exc_extis_super.getAss_type_id());
				update_is_last.put("is_last", "0");
				budgCostFassetTypeMapper.update(update_is_last);
			}

		}
		try {

			int state = budgCostFassetTypeMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addAssTypeDict\"}";

		}

	}

	/**
	 * @Description 批量添加050101 资产分类字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgCostFassetTypeMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchAssTypeDict\"}";

		}

	}

	/**
	 * @Description 更新050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
		if (Strings.isNotBlank((CharSequence) entityMap.get("super_code"))) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("ass_type_code", entityMap.get("super_code"));

			AssTypeDict data_exc_extis_super = budgCostFassetTypeMapper.queryByUniqueness(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("ass_naturs", data_exc_extis_super.getAss_naturs());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}

		}
		try {

			int state = budgCostFassetTypeMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateAssTypeDict\"}";

		}

	}

	/**
	 * @Description 批量更新050101 资产分类字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgCostFassetTypeMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchAssTypeDict\"}";

		}

	}

	/**
	 * @Description 删除050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {


		try {
			// 如果是非末级则先删除下级在删除自己
			AssTypeDict at= budgCostFassetTypeMapper.queryByCode(entityMap);
			Map<String, Object> supper_code_map =new HashMap<String, Object>();
			supper_code_map.put("group_id", at.getGroup_id());
			supper_code_map.put("hos_id", at.getHos_id());
			supper_code_map.put("copy_code", at.getCopy_code());
			supper_code_map.put("super_code", at.getAss_type_code());
			budgCostFassetTypeMapper.deleteAssTypeDictBySuperCode(supper_code_map);
			//删除自身
			int state = budgCostFassetTypeMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteAssTypeDict\"}";

		}

	}

	/**
	 * @Description 批量删除050101 资产分类字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgCostFassetTypeMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchAssTypeDict\"}";

		}
	}

	/**
	 * @Description 查询结果集050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssTypeDict> list = (List<AssTypeDict>) budgCostFassetTypeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssTypeDict> list = (List<AssTypeDict>) budgCostFassetTypeMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050101 资产分类字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgCostFassetTypeMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050101 资产分类字典<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgCostFassetTypeMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryAssTypeDictByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = budgCostFassetTypeMapper.queryAssTypeDictByTree(entityMap);
		return l_map;
	}

	@Override
    public String deleteAssTypeDictBySuperCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			budgCostFassetTypeMapper.deleteAssTypeDictBySuperCode(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteAssTypeDictBySuperCode\"}";

		}
		
    }

	@Override
	public List<AssTypeDict> queryAssTypeDictList(Map<String, Object> entityMap)
			throws DataAccessException {
		return (List<AssTypeDict>) budgCostFassetTypeMapper.query(entityMap);
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
