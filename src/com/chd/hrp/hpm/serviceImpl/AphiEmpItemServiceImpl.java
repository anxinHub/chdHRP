/**
 * 2015-1-9 SysUserServiceImpl.java author:pengjin
 */
package com.chd.hrp.hpm.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiEmpItemMapper;
import com.chd.hrp.hpm.entity.AphiEmpItem;
import com.chd.hrp.hpm.service.AphiEmpItemService;

@Service("aphiEmpItemService")
public class AphiEmpItemServiceImpl implements AphiEmpItemService {

	private static Logger logger = Logger.getLogger(AphiEmpItemServiceImpl.class);

	@Resource(name = "aphiEmpItemMapper")
	private AphiEmpItemMapper aphiEmpItemMapper = null;

	@Override
	public String queryEmpItem(Map<String, Object> mapVo) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) mapVo.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiEmpItemMapper.queryEmpItem(mapVo, rowBounds), sysPage.getTotal());

	}

	@Override
	public String addEmpItem(Map<String, Object> mapVo) throws DataAccessException {

		AphiEmpItem ii = aphiEmpItemMapper.queryEmpItemByCode(mapVo);

		if (ii != null) {

			return "{\"error\":\"奖金项目编码：" + mapVo.get("item_code").toString() + "重复.\"}";

		}

		try {

			String item_name = mapVo.get("item_name").toString();

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(item_name));

			mapVo.put("wbx_code", StringTool.toWuBi(item_name));

			aphiEmpItemMapper.addEmpItem(mapVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addItem\"}";

		}
	}

	@Override
	public AphiEmpItem queryEmpItemByCode(Map<String, Object> mapVo) throws DataAccessException {

		return aphiEmpItemMapper.queryEmpItemByCode(mapVo);
	}

	@Override
	public String updateEmpItem(Map<String, Object> mapVo) throws DataAccessException {

		try {

			String item_name = mapVo.get("item_name").toString();

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(item_name));

			mapVo.put("wbx_code", StringTool.toWuBi(item_name));

			aphiEmpItemMapper.updateEmpItem(mapVo);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateItem\"}";

		}

	}

	@Override
	public String deleteEmpItem(Map<String, Object> mapVo, String item_codes) throws DataAccessException {

		try {

			if (item_codes != null && !item_codes.equals("")) {

				String[] item_codeArray = item_codes.split(",");

				for (String item_code : item_codeArray) {

					mapVo.put("item_code", item_code);

					aphiEmpItemMapper.deleteEmpItem(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}

	}

}
