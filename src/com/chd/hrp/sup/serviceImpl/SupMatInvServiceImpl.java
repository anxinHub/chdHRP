/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sup.dao.SupMatInvMapper;
import com.chd.hrp.sup.entity.SupMatInv;
import com.chd.hrp.sup.service.SupMatInvService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 100003 物资材料表
 * @Table: SUP_MAT_INV
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("supMatInvService")
public class SupMatInvServiceImpl implements SupMatInvService {

	private static Logger logger = Logger.getLogger(SupMatInvServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "supMatInvMapper")
	private final SupMatInvMapper supMatInvMapper = null;

	/**
	 * @Description 添加100003 物资材料表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		//审核状态
		entityMap.put("state", "0");
		// 获取对象100003 物资材料表
		SupMatInv supMatInv = queryByCode(entityMap);
		// 判断材料字典是否存在材料名称
		int inv_name_count=queryExistsByInvName(entityMap);
		if (supMatInv != null || inv_name_count>0) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = supMatInvMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加100003 物资材料表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supMatInvMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新100003 物资材料表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = supMatInvMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新100003 物资材料表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supMatInvMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除100003 物资材料表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			// 获取对象100003 物资材料表
			SupMatInv supMatInv = queryByCode(entityMap);
			
			if(supMatInv.getState()==1){
				
				return "{\"msg\":\"已经启用不能删除.\",\"state\":\"true\"}";
			}else{
				int state = supMatInvMapper.delete(entityMap);

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除100003 物资材料表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supMatInvMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加100003 物资材料表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象100003 物资材料表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<SupMatInv> list = (List<SupMatInv>) supMatInvMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = supMatInvMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = supMatInvMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集100003 物资材料表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<SupMatInv> list = (List<SupMatInv>) supMatInvMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<SupMatInv> list = (List<SupMatInv>) supMatInvMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象100003 物资材料表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return supMatInvMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取100003 物资材料表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return SupMatInv
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return supMatInvMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取100003 物资材料表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<SupMatInv>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return supMatInvMapper.queryExists(entityMap);
	}
	/**
	 * @Description 获取100003 物资材料表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return int
	 * @throws DataAccessException
	 */
	@Override
    public int queryExistsByInvName(Map<String, Object> entityMap) throws DataAccessException {
		return supMatInvMapper.queryExistsByInvName(entityMap);
    }

	@Override
    public <T> T queryMatInvByCode(Map<String, Object> entityMap) throws DataAccessException {
		return supMatInvMapper.queryMatInvByCode(entityMap);
    }

}
