/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

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
import com.chd.hrp.htc.dao.info.basic.HtcCostItemDictNoMapper;
import com.chd.hrp.htc.entity.info.basic.HtcCostItemDictNo;
import com.chd.hrp.htc.service.info.basic.HtcCostItemDictNoService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本项目变更表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("htcCostItemDictNoService")
public class HtcCostItemDictNoServiceImpl implements HtcCostItemDictNoService {

	private static Logger logger = Logger.getLogger(HtcCostItemDictNoServiceImpl.class);
	
	@Resource(name = "htcCostItemDictNoMapper")
	private final HtcCostItemDictNoMapper htcCostItemDictNoMapper = null;
    
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryHtcCostItemDictNo(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HtcCostItemDictNo> list = htcCostItemDictNoMapper.queryHtcCostItemDictNo(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcCostItemDictNo> list = htcCostItemDictNoMapper.queryHtcCostItemDictNo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	
}
