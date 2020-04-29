/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.matprepay;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.matpay.MatPayMainMapper;
import com.chd.hrp.mat.dao.matprepay.MatPrePayDetailMapper;
import com.chd.hrp.mat.dao.matprepay.MatPrePayMainMapper;
import com.chd.hrp.mat.entity.MatPrePayMain;
import com.chd.hrp.mat.service.matprepay.MatPrePayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存预付款单的主要信息
 * @Table:
 * MAT_Pre_Pay_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matPrePayMainService")
public class MatPrePayMainServiceImpl implements MatPrePayMainService {

	private static Logger logger = Logger.getLogger(MatPrePayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matPrePayMainMapper")
	private final MatPrePayMainMapper matPrePayMainMapper = null;
	
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	
	/**
	 * @Description 
	 * 添加保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存预付款单的主要信息
		long count = matPrePayMainMapper.queryMatPrePayMainExists(entityMap);

		if (count>0) {

			return "{\"error\":\"预付款单号重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matPrePayMainMapper.addMatPrePayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加保存预付款单的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatPrePayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPrePayMainMapper.addBatchMatPrePayMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"添加失败\"}");
		}
		
	}
	
		/**
	 * @Description 
	 * 更新保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matPrePayMainMapper.updateMatPrePayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"更新失败\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 批量更新保存预付款单的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatPrePayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matPrePayMainMapper.updateBatchMatPrePayMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatPrePayMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matPrePayMainMapper.deleteMatPrePayMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除保存预付款单的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatPrePayMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matPrePayMainMapper.deleteBatchMatPrePayMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"eeror\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存预付款单的主要信息
		MatPrePayMain matPrePayMain = queryMatPrePayMainByCode(entityMap);

		if (matPrePayMain != null) {

			int state = matPrePayMainMapper.updateMatPrePayMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matPrePayMainMapper.addMatPrePayMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatPrePayMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集保存预付款单的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPrePayMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatPrePayMain> list = matPrePayMainMapper.queryMatPrePayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatPrePayMain> list = matPrePayMainMapper.queryMatPrePayMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象保存预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatPrePayMain queryMatPrePayMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matPrePayMainMapper.queryMatPrePayMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存预付款单的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatPrePayMain
	 * @throws DataAccessException
	*/
	@Override
	public MatPrePayMain queryMatPrePayMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matPrePayMainMapper.queryMatPrePayMainByUniqueness(entityMap);
	}
	/**
	 * 入库单查询 （没有被预付款单引用过的采购入库单）
	 * @param page
	 * @return
	 */
	public String queryMatCommonIn(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matPrePayMainMapper.queryMatCommonIn(entityMap);
			for(Map<String,Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MAT_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList= matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}else{
					item.put("table", "MAT_IN_DETAIL");
					// 明细数据 查询
					List<Map<String,Object>> detailList = matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matPrePayMainMapper.queryMatCommonIn(entityMap, rowBounds);
			//附加  明细数据
			for(Map<String,Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MAT_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList= matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}else{
					item.put("table", "MAT_IN_DETAIL");
					// 明细数据 查询
					List<Map<String,Object>> detailList = matPrePayMainMapper.queryMatInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 入库单明细查询
	 * @param entityMap
	 * @return
	 */
	public String queryMatInDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matPrePayMainMapper.queryMatInDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matPrePayMainMapper.queryMatInDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 新增时查询预付款单明细对应的预付款单主表的Id
	 * @return
	 */
	public Long queryMatPrePayMainMaxId() throws DataAccessException {
		return matPrePayMainMapper.queryMatPrePayMainMaxId();
	}
	/**
	 * 根据 入库单Id 查询入库单明细Id
	 * @param mapVo
	 * @return
	 */
	public List<Long> queryIn_detail_id(Map<String, Object> mapVo) throws DataAccessException {
		return matPrePayMainMapper.queryIn_detail_id(mapVo);
	}
	
	/**
	 * 多表联合查询 预付款单详细信息（修改页面回值用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatPrePayMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException {
		return matPrePayMainMapper.queryMatPrePayMainReturnUpdate(mapVo);
	}
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 */
	public String updateMatPrePayMainState(List<Map<String, Object>> entityList) {
		try {
			
			  matPrePayMainMapper.updateMatPrePayMainState(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"操作过程出错，操作失败 请联系管理员! 方法 updateMatPrePayMainState\"}";

			}	
	}
	/**
	 * 生成预付款单号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setMatPrePayMainNo(Map<String, Object> mapVo) throws DataAccessException {
		
		mapVo.put("table_code", "MAT_PRE_PAY_MAIN");
		mapVo.put("prefixe", "YF");
		
		// 判断该 类型 协议编码是否存在（存在取 max_no,不存在 则max_no=1 添加新记录 ）
		int count = matNoManageMapper.queryIsExists(mapVo);
		String max_no = "";
		if(count == 0){
			max_no = "1";
			mapVo.put("max_no", max_no);
			matNoManageMapper.add(mapVo);
		}else{
			//更新该业务流水码+1
			matNoManageMapper.updateMaxNo(mapVo);
			//取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(mapVo);
		}
		//补充流水号为4位（如：0001）
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		mapVo.put("max_no", max_no);
		return ChdJson.toJson(mapVo);
	}
	
	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryPrePayByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matPrePayMainMapper.queryPrePayPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				
				//查询入库主表
				Map<String,Object> map=matPrePayMainMapper.queryPrePayPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	public Map<String, Object> queryPrePayByPrintTemlateNew(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatPrePayMainMapper matPrePayMainMapper = (MatPrePayMainMapper)context.getBean("matPrePayMainMapper");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matPrePayMainMapper.queryPrePayPrintTemlateByMainBatch(entityMap);
				//查询入库明细表
				List<Map<String,Object>> list=matPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				mapVo.put("main", map);
				mapVo.put("detail", list);
				return mapVo;
			}else{
				
				//查询入库主表
				Map<String,Object> map=matPrePayMainMapper.queryPrePayPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matPrePayMainMapper.queryPrePayPrintTemlateByDetail(entityMap);
				mapVo.put("main", map);
				mapVo.put("detail", list);
				return mapVo;
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
}
