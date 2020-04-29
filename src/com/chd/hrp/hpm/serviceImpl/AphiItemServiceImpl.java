/**
 * 2015-1-9 SysUserServiceImpl.java author:pengjin
 */
package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiCompanySchemeMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindSchemeMapper;
import com.chd.hrp.hpm.dao.AphiDeptSchemeMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiCompanyScheme;
import com.chd.hrp.hpm.entity.AphiDeptKindScheme;
import com.chd.hrp.hpm.entity.AphiDeptScheme;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiItemService;

@Service("aphiItemService")
public class AphiItemServiceImpl implements AphiItemService {

	private static Logger logger = Logger.getLogger(AphiItemServiceImpl.class);
	
	@Resource(name = "aphiItemMapper")
	private AphiItemMapper aphiItemMapper = null;
	@Resource(name = "aphiCompanySchemeMapper")
	private AphiCompanySchemeMapper aphiCompanySchemeMapper = null;
	
	@Resource(name = "aphiDeptKindSchemeMapper")
	private AphiDeptKindSchemeMapper aphiDeptKindSchemeMapper = null;
	
	@Resource(name = "aphiDeptSchemeMapper")
	private AphiDeptSchemeMapper aphiDeptSchemeMapper = null;
	                             

	/*
	 * @Resource(name = "itemIncreasePercConfMapper") private final
	 * ItemIncreasePercConfMapper itemIncreasePercConfMapper = null;
	 */

	@Override
	public String queryItem(Map<String, Object> mapVo) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) mapVo.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		return JsonListBeanUtil.listToJson(aphiItemMapper.queryItem(mapVo, rowBounds), sysPage.getTotal());

	}

	@Override
	public String addItem(Map<String, Object> mapVo) throws DataAccessException {

		AphiItem ii = queryItemByCode(mapVo);

		if (ii != null) {

			return "{\"error\":\"奖金项目编码：" + mapVo.get("item_code").toString() + "重复.\"}";

		}

		try {

			String item_name = mapVo.get("item_name").toString();

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(item_name));

			mapVo.put("wbx_code", StringTool.toWuBi(item_name));

			aphiItemMapper.addItem(mapVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addItem\"}";

		}
	}

	@Override
	public AphiItem queryItemByCode(Map<String, Object> mapVo) throws DataAccessException {

		return aphiItemMapper.queryItemByCode(mapVo);
	}

	@Override
	public String updateItem(Map<String, Object> mapVo) throws DataAccessException {

		try {

			String item_name = mapVo.get("item_name").toString();

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(item_name));

			mapVo.put("wbx_code", StringTool.toWuBi(item_name));

			aphiItemMapper.updateItem(mapVo);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateItem\"}";

		}

	}

	@Override
	public String deleteItem(Map<String, Object> mapVo, String item_codes) throws DataAccessException {

		try {
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			if (item_codes != null && !item_codes.equals("")) {

				String[] item_codeArray = item_codes.split(",");

				for (String item_code : item_codeArray) {

					mapVo.put("item_code", item_code);

				/*	Item item = aphiItemMapper.queryItemByCode1(mapVo);
					// ItemIncreasePercConf itemIncreasePercConf
					// =itemIncreasePercConfMapper.queryItemIncreasePercConfByCode(mapVo);
					if (item != null) {
						return "{\"msg\":\"奖金增量比例维护引用此项目，不能删除！\",\"state\":\"true\"}";
					}*/
					AphiCompanyScheme comPanyScheme = aphiCompanySchemeMapper.queryCompanySchemeByCode(mapVo);
					List<AphiDeptKindScheme> deptKindScheme = aphiDeptKindSchemeMapper.queryDeptKindScheme(mapVo);
					List<AphiDeptScheme> deptScheme =  aphiDeptSchemeMapper.queryDeptScheme(mapVo);
				 if(comPanyScheme != null){ 
					
					 return "{\"msg\":\"全院奖金核算方案引用此项目，不能删除！\",\"state\":\"true\"}";	 
				 }
				 
				 if (deptKindScheme.size() != 0){
					 
					 return "{\"msg\":\"科室分类奖金核算方案引用此项目，不能删除！\",\"state\":\"true\"}";	 
					 
				 }
				 
				 if(deptScheme.size() != 0) {
					 
					 return "{\"msg\":\"科室奖金核算方案引用此项目，不能删除！\",\"state\":\"true\"}";
					 
				   }
				 
				 
				 
					aphiItemMapper.deleteItem(mapVo);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}

	}

}
