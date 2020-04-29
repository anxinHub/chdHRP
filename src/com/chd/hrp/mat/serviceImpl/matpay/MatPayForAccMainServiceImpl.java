/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.serviceImpl.matpay;

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
import com.chd.hrp.acc.dao.autovouch.AccAutoVouchMaintainMapper;
import com.chd.hrp.mat.dao.matpay.MatPayForAccMainMapper;
import com.chd.hrp.mat.entity.MatPayMain;
import com.chd.hrp.mat.service.matpay.MatPayForAccMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
 * @Table: MAT_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("matPayForAccMainService")
public class MatPayForAccMainServiceImpl implements MatPayForAccMainService {

	private static Logger logger = Logger.getLogger(MatPayForAccMainServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "matPayForAccMainMapper")
	private final MatPayForAccMainMapper matPayForAccMainMapper = null;
	
	@Resource(name = "accAutoVouchMaintainMapper")
	private final AccAutoVouchMaintainMapper accAutoVouchMaintainMapper = null;

	/**
	 * @Description 查询结果集state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatPayForAccMain(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<MatPayMain> list = matPayForAccMainMapper.queryMatPayForAccMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<MatPayMain> list = matPayForAccMainMapper.queryMatPayForAccMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 审核、消审
	 * 
	 * @param entityList
	 * @return
	 */
	public String updatePayState(List<Map<String, Object>> entityList) {
		try {
			
			//先删除凭证
			
			Map<String,String> vouchIdMap = new HashMap<String,String>();
			
			for(Map<String,Object> map : entityList){
				
				if("1".equals(map.get("state"))){
					
					if( !"null".equals(map.get("vouch_id").toString()) && !"undefined".equals(map.get("vouch_id").toString()) && !"".equals(map.get("vouch_id").toString())){
						
						vouchIdMap.put(map.get("vouch_id").toString(), map.get("vouch_id").toString());
					
					}
					
				}
				
			}
			
			Map<String,Object> mapVo = entityList.get(0);
			
			for(String key : vouchIdMap.keySet()){
				
				mapVo.put("vouch_id", key);
				
				accAutoVouchMaintainMapper.deleteAccAutoVouch(mapVo);
				
				if(mapVo.get("reNote")!=null && !mapVo.get("reNote").toString().equalsIgnoreCase("ok")){
					
					throw new SysException(mapVo.get("reNote").toString());
					
				}
				
			}

			//更新状态
			matPayForAccMainMapper.updatePayState(entityList);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");

		}
	}

}
