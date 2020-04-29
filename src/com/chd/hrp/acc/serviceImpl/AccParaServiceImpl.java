/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 系统参数<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accParaService")
public class AccParaServiceImpl implements AccParaService {

	private static Logger logger = Logger.getLogger(AccParaServiceImpl.class);
	
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;
	
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询AccPara分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccPara(Map<String,Object> entityMap) throws DataAccessException{
		

			List<AccPara> list = accParaMapper.queryAccPara(entityMap);
			
			return ChdJson.toJson(list);
		
		
	}
	
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量更新AccPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccPara(Map<String,Object> mapVo)throws DataAccessException{
		
		try {
			List<Map<String,Object>> listVo  = new ArrayList<Map<String,Object>>();
			Map<String, Object> mapVo1=null;
			String formPara=mapVo.get("formPara").toString();
			for ( String id: formPara.split(",")) {
				mapVo1 = new HashMap<String, Object>();
				if (id != null && !id.equals("")) {
					mapVo1.put("para_code", id.split("@")[0]);
					mapVo1.put("para_value",id.split("@").length>1?id.split("@")[1]:"");
				}
	            listVo.add(mapVo1);
	        }
		
			accParaMapper.updateBatchAccPara(mapVo,listVo);
			
			//加载系统参数
			sysConfigService.querySysParaInit(mapVo);
			
			/*//会计参数
			SessionManager.getSession().setAttribute("acc_para_map", sysBaseService.getSysParaMap("01"));
			// 成本系统参数
			SessionManager.getSession().setAttribute("cost_para_map", sysBaseService.getSysParaMap("03"));
			//物流参数
			SessionManager.getSession().setAttribute("mat_para_map", sysBaseService.getSysParaMap("04"));
			//物流参数
			SessionManager.getSession().setAttribute("med_para_map", sysBaseService.getSysParaMap("08"));
			//固定资产参数
			SessionManager.getSession().setAttribute("ass_para_map", sysBaseService.getSysParaMap("05"));
			//奖金系统参数
			SessionManager.getSession().setAttribute("ass_para_map", sysBaseService.getSysParaMap("09"));*/

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);

		}
		
	}
	
}
