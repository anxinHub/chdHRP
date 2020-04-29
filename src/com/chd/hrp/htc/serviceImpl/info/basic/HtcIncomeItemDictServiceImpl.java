
package com.chd.hrp.htc.serviceImpl.info.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.info.basic.HtcChargeKindDictMapper;
import com.chd.hrp.htc.dao.info.basic.HtcIncomeItemDictMapper;
import com.chd.hrp.htc.entity.info.basic.HtcChargeKindDict;
import com.chd.hrp.htc.entity.info.basic.HtcIncomeItemDict;
import com.chd.hrp.htc.service.info.basic.HtcIncomeItemDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcIncomeItemDictService")
public class HtcIncomeItemDictServiceImpl implements HtcIncomeItemDictService {

	private static Logger logger = Logger.getLogger(HtcIncomeItemDictServiceImpl.class);
	
	@Resource(name = "htcIncomeItemDictMapper")
	private final HtcIncomeItemDictMapper htcIncomeItemDictMapper = null;
	
	@Resource(name = "htcChargeKindDictMapper")
	private final HtcChargeKindDictMapper htcChargeKindDictMapper = null;
	
	@Override
	public String addHtcIncomeItemDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			HtcIncomeItemDict htcIncomeItemDict = htcIncomeItemDictMapper.queryHtcIncomeItemDictByCode(entityMap);
		
			if(null != htcIncomeItemDict){
				return "{\"error\":\"编码：" + entityMap.get("income_item_code").toString() + "重复.\"}";
			}
		
			htcIncomeItemDictMapper.addHtcIncomeItemDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcIncomeItemDict\"}";
		}
	}

	@Override
	public String queryHtcIncomeItemDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcIncomeItemDict> list = htcIncomeItemDictMapper.queryHtcIncomeItemDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIncomeItemDict> list = htcIncomeItemDictMapper.queryHtcIncomeItemDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcIncomeItemDict queryHtcIncomeItemDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcIncomeItemDictMapper.queryHtcIncomeItemDictByCode(entityMap);
	}
	
	
	@Override
    public String deleteHtcIncomeItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcIncomeItemDictMapper.deleteHtcIncomeItemDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteHtcIncomeItemDict\"}";
		}
    }

	@Override
	public String deleteBatchHtcIncomeItemDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			for(int i = 0 ; i < entityList.size() ; i ++){
				Map<String,Object> map = entityList.get(i);
				List<HtcChargeKindDict> chargeKindArrt = htcChargeKindDictMapper.queryHtcChargeKindDict(map);
				if(chargeKindArrt.size()>0){
					return "{\"error\":\"当前收入项目已被收费类别引用,不能删除.\"}";
				}
			}
			int state = htcIncomeItemDictMapper.deleteBatchHtcIncomeItemDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchHtcIncomeItemDict\"}";
	
		}
	}

	@Override
	public String updateHtcIncomeItemDict(Map<String,Object> entityMap)throws DataAccessException{
	
		try {
			htcIncomeItemDictMapper.updateHtcIncomeItemDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHtcIncomeItemDict\"}";
		}
	}

	

	
}
