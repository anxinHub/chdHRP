/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.check.special;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.check.special.AssCheckDdetailSpecialMapper;
import com.chd.hrp.ass.entity.check.special.AssCheckDdetailSpecial;
import com.chd.hrp.ass.service.check.special.AssCheckDdetailSpecialService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051101 科室盘点单明细_医用设备
 * @Table:
 * ASS_CHECK_D_DETAIL_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assCheckDdetailSpecialService")
public class AssCheckDdetailSpecialServiceImpl implements AssCheckDdetailSpecialService {

	private static Logger logger = Logger.getLogger(AssCheckDdetailSpecialServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCheckDdetailSpecialMapper")
	private final AssCheckDdetailSpecialMapper assCheckDdetailSpecialMapper = null;
    
	/**
	 * @Description 
	 * 添加051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051101 科室盘点单明细_医用设备
		AssCheckDdetailSpecial assCheckDdetailSpecial = queryByCode(entityMap);

		if (assCheckDdetailSpecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assCheckDdetailSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckDdetailSpecialMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCheckDdetailSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCheckDdetailSpecialMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCheckDdetailSpecialMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckDdetailSpecialMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051101 科室盘点单明细_医用设备
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssCheckDdetailSpecial> list = (List<AssCheckDdetailSpecial>)assCheckDdetailSpecialMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assCheckDdetailSpecialMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assCheckDdetailSpecialMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCheckDdetailSpecial> list = (List<AssCheckDdetailSpecial>)assCheckDdetailSpecialMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCheckDdetailSpecial> list = (List<AssCheckDdetailSpecial>)assCheckDdetailSpecialMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckDdetailSpecialMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCheckDdetailSpecial
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckDdetailSpecialMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 科室盘点单明细_医用设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCheckDdetailSpecial>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckDdetailSpecialMapper.queryExists(entityMap);
	}
	/**
	 * @Description 复制账面数量
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String copyAmount(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		// 拼接字符串
		mapVo.put("ass_card_no", "");
		String vr = vo_split(mapVo.get("ass_card_no_all").toString());
		mapVo.put("ass_card_no_all", vr);
		//根据勾选的卡片复制账面数量
		assCheckDdetailSpecialMapper.copyAmount(mapVo);

		return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 删除盘点卡片
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@Override
	public String delCard(@RequestParam Map<String, Object> mapVo) throws DataAccessException {

		// 拼接字符串
		mapVo.put("ass_card_no", "");
		String vr = vo_split(mapVo.get("ass_card_no_all").toString());
		mapVo.put("ass_card_no_all", vr);
		//根据勾选的卡片复制账面数量
		assCheckDdetailSpecialMapper.delete(mapVo);

		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 拼接字符串 每个业务类型单独处理，暂不共用
	 * @param str
	 * @return
	 */
	private String vo_split(String str) {
		StringBuilder vo_no = new StringBuilder();

		for (String vo : str.split(",")) {
			vo_no.append("'");
			vo_no.append(vo);
			vo_no.append("',");
		}

		String sp = vo_no.toString().substring(0, vo_no.length() - 1);

		return "(" + sp + ")";
	}
	//新版打印  调用的方法
	@Override
	public Map<String, Object> queryAssInSpecialByPrintTemlatePrints(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssCheckDdetailSpecialMapper assCheckDdetailSpecialMapper = (AssCheckDdetailSpecialMapper)context.getBean("assCheckDdetailSpecialMapper");
			 
		/*	//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 专用设备 入库主表
				List<Map<String,Object>> map= assCheckSdetailSpecialMapper.queryAssInSpecialPrintTemlateByMainBatch(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assCheckSdetailSpecialMapper.queryAssInSpecialPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ *///修改页面 打印查询
				//
				Map<String,Object> map= assCheckDdetailSpecialMapper.queryAssInSpecialPrintTemlateByMains(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assCheckDdetailSpecialMapper.queryAssInSpecialPrintTemlateByDetails(entityMap);
				
			
				reMap.put("main", map);
				
				reMap.put("detail", list);
				
				return reMap;
				
			/*}*/
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	/**
	 * 入库单状态查询  （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssInSpecialStates(Map<String, Object> mapVo) throws DataAccessException {
		
		return assCheckDdetailSpecialMapper.queryAssInSpecialStates(mapVo);
	}
	@Override
	public String saveAssCheckDDetailSpecial(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> listVo = new ArrayList<Map<String,Object>>();
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		try{	
			
			for(Map map : detail){    
				Map<String,Object> mapVo = new HashMap<String, Object>();
				if (map.get("ass_id") == null || "".equals(map.get("ass_id"))) {
					continue;
				}
				String ass_id_no = map.get("ass_id").toString();
				
				mapVo.put("group_id",entityMap.get("group_id").toString());
				mapVo.put("hos_id",entityMap.get("hos_id").toString());
				mapVo.put("copy_code",entityMap.get("copy_code").toString());
				mapVo.put("check_plan_no",entityMap.get("check_plan_no").toString());
				mapVo.put("check_no",entityMap.get("check_no").toString());
				mapVo.put("dept_id",map.get("dept_id").toString());
				mapVo.put("dept_no",map.get("dept_no").toString());
				
				mapVo.put("ass_id", map.get("ass_id").toString());
				mapVo.put("ass_no", map.get("ass_no").toString());
				
				
				if(map.get("ass_card_no") != null && !map.get("ass_card_no").equals("")){
					mapVo.put("ass_card_no",map.get("ass_card_no").toString());
				}else{
					mapVo.put("ass_card_no","");
				}
				
				if(map.get("acc_amount") != null && !map.get("acc_amount").equals("")){
					mapVo.put("acc_amount",map.get("acc_amount").toString());
				}else{
					mapVo.put("acc_amount","0");
				}
				
				if(map.get("check_amount") != null && !map.get("check_amount").equals("")){
					mapVo.put("check_amount",map.get("check_amount").toString());
				}else{
					mapVo.put("check_amount","0");
				}
			
				if(map.get("pl_amount") != null && !map.get("pl_amount").equals("")){
					mapVo.put("pl_amount",map.get("pl_amount").toString());
				}else{
					mapVo.put("pl_amount","");
				}
				
				if(map.get("pl_reason") != null && !map.get("pl_reason").equals("")){
					mapVo.put("pl_reason",map.get("pl_reason").toString());
				}else{
					mapVo.put("pl_reason","");
				}
				
				listVo.add(mapVo);
			}
			assCheckDdetailSpecialMapper.deleteBatch(listVo);
			assCheckDdetailSpecialMapper.addBatch(listVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
