/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.autovouch.his;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.his.HisAccAssDeptMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccAssDept;
import com.chd.hrp.acc.service.autovouch.his.HisAccAssDeptService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hisAccAssDeptService")
public class HisAccAssDeptServiceImpl implements HisAccAssDeptService {

	private static Logger logger = Logger.getLogger(HisAccAssDeptServiceImpl.class);

	@Resource(name = "hisAccAssDeptMapper")
	private final HisAccAssDeptMapper hisAccAssDeptMapper = null;

	@Override
	public String queryHisAccAssDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = hisAccAssDeptMapper.queryHisAccAssDept(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = hisAccAssDeptMapper.queryHisAccAssDept(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HisAccAssDept queryHisAccAssDeptByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hisAccAssDeptMapper.queryHisAccAssDeptByCode(entityMap);
	}

	@Override
	public String updateHisAccAssDept(Map<String, Object> entityMap) throws DataAccessException {
		try {

			hisAccAssDeptMapper.updateHisAccAssDept(entityMap);

			return "{\"msg\":\"更改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更改失败 数据库异常 请联系管理员! 方法 updateHisAccAssDept\"}";

		}
	}

	@Override
	public String addHisAccAssDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			String store_code = ((String) entityMap.get("store_code")).trim();
			String store_name = ((String) entityMap.get("store_name")).trim();
			if ("".equals(store_code)||	"".equals(store_name)) {
				return "{\"warn\":\"库房编码与库房名称不能为空！\",\"state\":\"error\"}";
			}
			
			
			String saveFlag = (String)entityMap.get("saveFlag");
			
			if(!"1".equals(saveFlag)){
				
				HisAccAssDept apt = hisAccAssDeptMapper.queryHisAccAssDeptByCode(entityMap);
				 
				if(apt != null){
					
					return "{\"msg\":\"编码重复 请修改编码.\",\"state\":\"error\"}";
					
				}
				
			}

			hisAccAssDeptMapper.addHisAccAssDept(entityMap);
				
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"保存失败 数据库异常 请联系管理员! 方法 addHisAccAssDept\"}";

		}
	}

	@Override
	public String delHisAccAssDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			hisAccAssDeptMapper.deleteHisAccAssDept(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delHisAccAssDept\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryHisAccAssDeptPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = hisAccAssDeptMapper.queryHisAccAssDept(entityMap);

		return list;
	}

}
