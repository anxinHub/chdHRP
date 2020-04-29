package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiItemIncreasePercConfMapper;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
import com.chd.hrp.hpm.service.AphiItemIncreasePercConfService;

/**
 * alfred
 */

@Service("aphiItemIncreasePercConfService")
public class AphiItemIncreasePercConfServiceImpl implements AphiItemIncreasePercConfService {

	private static Logger logger = Logger.getLogger(AphiItemIncreasePercConfServiceImpl.class);

	@Resource(name = "aphiItemIncreasePercConfMapper")
	private final AphiItemIncreasePercConfMapper aphiItemIncreasePercConfMapper = null;

	/**
	 * 
	 */
	@Override
	public String addItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException {

		AphiItemIncreasePercConf iipc = queryItemIncreasePercConfByCode(entityMap);

		if (iipc != null) {

			return "{\"error\":\"奖金项目编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}

		try {

			aphiItemIncreasePercConfMapper.addItemIncreasePercConf(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addItemIncreasePercConf\"}";
		}
	}

	/**
	 * 
	 */
	@Override
	public String queryItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiItemIncreasePercConfMapper.queryItemIncreasePercConf(entityMap, rowBounds), sysPage.getTotal());
	}

	/**
	 * 
	 */
	@Override
	public AphiItemIncreasePercConf queryItemIncreasePercConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiItemIncreasePercConfMapper.queryItemIncreasePercConfByCode(entityMap);
	
	}

	/**
	 * 
	 */
	@Override
	public String deleteItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiItemIncreasePercConfMapper.deleteItemIncreasePercConf(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			
		}
	}

	/**
	 * 
	 */
	@Override
	public String updateItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiItemIncreasePercConfMapper.updateItemIncreasePercConf(entityMap);
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String deleteBatchItemIncreasePercConf(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiItemIncreasePercConfMapper.deleteBatchItemIncreasePercConf(entityMap);
		if (state > 0) {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String addBatchItemIncreasePercConf(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		int state= aphiItemIncreasePercConfMapper.addBatchItemIncreasePercConf(entityMap);
		if(state>0){
			return  "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return  "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}
}
