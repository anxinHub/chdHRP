package com.chd.hrp.mat.serviceImpl.purchase.check;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.purchase.check.MatPurMainCheckMapper;
import com.chd.hrp.mat.service.purchase.check.MatPurMainCheckService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 04114 采购计划审核
 * @Table:
 * MAT_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matPurMainCheckService")
public class MatPurMainCheckServiceImpl implements MatPurMainCheckService{
	
	private static Logger logger = Logger.getLogger(MatPurMainCheckServiceImpl.class);
	
	@Resource(name = "matPurMainCheckMapper")
	private MatPurMainCheckMapper matPurMainCheckMapper = null;
	/**
	 * @Description 
	 * 采购计划审核<BR>查询 采购计划
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPurMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = matPurMainCheckMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = matPurMainCheckMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 采购计划审核<BR>审核
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String checkMatPurMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			
			matPurMainCheckMapper.updateToCheckState(entityMap);
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			
			return "{\"error\":\"审核失败 数据库异常 请联系管理员! 错误编码 checkMatPurMain\"}";
		}
	}
	
	/**
	 * @Description 
	 * 采购计划审核<BR>取消审核
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String cancelCheckMatPurMain(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {
			//生成订单的采购单不允许销审
			String pur_nos = matPurMainCheckMapper.existsInMatOrder(entityMap);
			if(pur_nos == null || "".equals(pur_nos)){
				matPurMainCheckMapper.updateToUnCheckState(entityMap);
				return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"单据：<br>"+pur_nos.toString()+" 已经生成订单，不能销审！\"}";
			}
			
		} catch (Exception e) {

			logger.error(e.getMessage(),e);
			
			return "{\"error\":\"取消审核失败 数据库异常 请联系管理员! 错误编码 cancelCheckMatPurMain\"}";
		}
	}

	/**
	 * @Description 
	 * 采购计划审核<BR>根据主表ID查询采购明细
	 * @param entityMap
	 * @return <T>
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryMatPurMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return matPurMainCheckMapper.queryMatPurMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 采购计划审核<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatPurMainDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = matPurMainCheckMapper.queryMatPurMainDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = matPurMainCheckMapper.queryMatPurMainDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	//入库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatCheckByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		//查询入库主表
		Map<String,Object> map=matPurMainCheckMapper.queryMatCheckPrintTemlateByMain(entityMap);
				
		//查询入库明细表
		List<Map<String,Object>> list=matPurMainCheckMapper.queryMatCheckPrintTemlateByDetail(entityMap);
		return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
	}
	
}
