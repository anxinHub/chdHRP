/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostServeItemDictMapper;
import com.chd.hrp.cost.entity.CostServeItemDict;
import com.chd.hrp.cost.service.CostServeItemDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 内部服务<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costServeItemDictService")
public class CostServeItemDictServiceImpl implements CostServeItemDictService {

	private static Logger logger = Logger.getLogger(CostServeItemDictServiceImpl.class);

	@Resource(name = "costServeItemDictMapper")
	private final CostServeItemDictMapper costServeItemDictMapper = null;

	@Override
	public String addCostServeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		CostServeItemDict costServeItemDict = costServeItemDictMapper.queryCostServeItemDictByCode(entityMap);

		if (costServeItemDict != null) {

			return "{\"error\":\"服务项目编码：" + costServeItemDict.getServer_item_code().toString() + "重复.\"}";
		}

		try {

			costServeItemDictMapper.addCostServeItemDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostServeItemDict\"}";
		}

	}

	@Override
	public String queryCostServeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostServeItemDict> list = costServeItemDictMapper.queryCostServeItemDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostServeItemDict> list = costServeItemDictMapper.queryCostServeItemDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}
	 @Override
	 public List<Map<String, Object>> queryCostServeItemDictPrint(Map<String, Object> entityMap) throws DataAccessException {
			
			
			List<Map<String, Object>> list=costServeItemDictMapper.queryCostServeItemDictPrint(entityMap);
			
			return list;

	 }
	@Override
	public CostServeItemDict queryCostServeItemDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return costServeItemDictMapper.queryCostServeItemDictByCode(entityMap);
	}

	@Override
	public String deleteBatchCostAssDetail(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			int state = costServeItemDictMapper.deleteBatchCostServeItemDict(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostServeItemDict\"}";

		}
	}

	@Override
	public String updateCostAssDetail(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			costServeItemDictMapper.updateCostServeItemDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostServeItemDict\"}";

		}
	}

}
