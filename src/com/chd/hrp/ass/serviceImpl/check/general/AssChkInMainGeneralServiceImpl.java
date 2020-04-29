/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.check.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.ass.dao.accessory.AssAccessoryGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.check.general.AssChkInDetailGeneralMapper;
import com.chd.hrp.ass.dao.check.general.AssChkInMainGeneralMapper;
import com.chd.hrp.ass.dao.check.special.AssChkInMainSpecialMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccGeneralMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageGeneralMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.dao.file.AssFileGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptGeneralMapper;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.check.general.AssChkInMainGeneral;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.entity.file.AssFileGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoGeneral;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.general.AssChkInMainGeneralService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(一般设备)
 * @Table:
 * ASS_CHK_IN_MAIN_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assChkInMainGeneralService")
public class AssChkInMainGeneralServiceImpl implements AssChkInMainGeneralService {  
   
	private static Logger logger = Logger.getLogger(AssChkInMainGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assChkInMainGeneralMapper")
	private final AssChkInMainGeneralMapper assChkInMainGeneralMapper = null;
	
	//引入DAO操作
	@Resource(name = "assChkInDetailGeneralMapper")
	private final AssChkInDetailGeneralMapper assChkInDetailGeneralMapper = null;
	
	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;

	@Resource(name = "assResourceGeneralMapper")
	private final AssResourceGeneralMapper assResourceGeneralMapper = null;

	@Resource(name = "assShareDeptGeneralMapper")
	private final AssShareDeptGeneralMapper assShareDeptGeneralMapper = null;

	@Resource(name = "assShareDeptRGeneralMapper")
	private final AssShareDeptRGeneralMapper assShareDeptRGeneralMapper = null;

	@Resource(name = "assFileGeneralMapper")
	private final AssFileGeneralMapper assFileGeneralMapper = null;

	@Resource(name = "assPhotoGeneralMapper")
	private final AssPhotoGeneralMapper assPhotoGeneralMapper = null;

	@Resource(name = "assAccessoryGeneralMapper")
	private final AssAccessoryGeneralMapper assAccessoryGeneralMapper = null;

	@Resource(name = "assDepreAccGeneralMapper")
	private final AssDepreAccGeneralMapper assDepreAccGeneralMapper = null;

	@Resource(name = "assDepreManageGeneralMapper")
	private final AssDepreManageGeneralMapper assDepreManageGeneralMapper = null;

	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050701 资产盘盈入库主表(一般设备)
		AssChkInMainGeneral assChkInMainGeneral = queryByCode(entityMap);

		if (assChkInMainGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assChkInMainGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assChkInMainGeneralMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assChkInMainGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assChkInMainGeneralMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assChkInMainGeneralMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			for(int i = 0; i < entityList.size(); i ++){
				List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
				listMap.add(entityList.get(i));
				delCard(listMap);
			}
			assChkInDetailGeneralMapper.deleteBatch(entityList);
			assChkInMainGeneralMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * 删除卡片相关信息
	 * @param entityList
	 */
	private void delCard(List<Map<String, Object>> entityMap) {
		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityMap.get(0).get("group_id"));
			inMapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			inMapVo.put("ass_in_no", entityMap.get(0).get("ass_in_no"));
			inMapVo.put("ass_id", entityMap.get(0).get("ass_id"));
			inMapVo.put("ass_no", entityMap.get(0).get("ass_no"));

			List<Map<String, Object>> cardList = assCardGeneralMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				// 删除条码文件
				for (Map<String, Object> map : cardList) {
					AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(map);
					if (assCardGeneral.getBar_url() != null && !assCardGeneral.getBar_url().equals("")) {
						String file_name = assCardGeneral.getBar_url().substring(
								assCardGeneral.getBar_url().lastIndexOf("/") + 1, assCardGeneral.getBar_url().length());
						String path = assCardGeneral.getBar_url().substring(0,
								assCardGeneral.getBar_url().lastIndexOf("/"));
						FtpUtil.deleteFile(path, file_name);
					}
				}

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileGeneral> assFileGeneralList = (List<AssFileGeneral>) assFileGeneralMapper
							.queryExists(fileMap);
					if (assFileGeneralList.size() > 0 && assFileGeneralList != null) {
						for (AssFileGeneral assFileGeneral : assFileGeneralList) {
							if (assFileGeneral.getFile_url() != null && !assFileGeneral.getFile_url().equals("")) {
								String file_name = assFileGeneral.getFile_url().substring(
										assFileGeneral.getFile_url().lastIndexOf("/") + 1,
										assFileGeneral.getFile_url().length());
								String path = assFileGeneral.getFile_url().substring(0,
										assFileGeneral.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileGeneral.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoGeneral> assPhotoGeneralList = (List<AssPhotoGeneral>) assPhotoGeneralMapper
							.queryExists(photoMap);
					if (assPhotoGeneralList.size() > 0 && assPhotoGeneralList != null) {
						for (AssPhotoGeneral assPhotoGeneral : assPhotoGeneralList) {
							if (assPhotoGeneral.getFile_url() != null && !assPhotoGeneral.getFile_url().equals("")) {
								String file_name = assPhotoGeneral.getFile_url().substring(
										assPhotoGeneral.getFile_url().lastIndexOf("/") + 1,
										assPhotoGeneral.getFile_url().length());
								String path = assPhotoGeneral.getFile_url().substring(0,
										assPhotoGeneral.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoGeneral.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRGeneralMapper.deleteBatch(cardList);
				assShareDeptGeneralMapper.deleteBatch(cardList);
				assResourceGeneralMapper.deleteBatch(cardList);
				assFileGeneralMapper.deleteBatch(cardList);
				assPhotoGeneralMapper.deleteBatch(cardList);
				assAccessoryGeneralMapper.deleteBatch(cardList);
				assPayStageGeneralMapper.deleteBatch(cardList);
				assDepreAccGeneralMapper.deleteBatch(cardList);
				assDepreManageGeneralMapper.deleteBatch(cardList);
				assCardGeneralMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	
	/**
	 * @Description 
	 * 添加050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		//判断是否存在对象050701 资产盘盈入库主表(一般设备)
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("ass_in_no", entityMap.get("ass_in_no"));
		
		List<AssChkInMainGeneral> list = (List<AssChkInMainGeneral>)assChkInMainGeneralMapper.queryExists(mapVo);
		try {
			entityMap.put("state", "0");
			if (list.size()>0) {
				assChkInMainGeneralMapper.update(entityMap);
			}else{
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_CHK_IN_MAIN_GENERAL");
				String ass_in_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("ass_in_no", ass_in_no);
				assChkInMainGeneralMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			//要添加的明细列表
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			List<Map<String, Object>> cardList = assCardGeneralMapper.queryByAssInNo(mapVo);
			List<Map<String, Object>> delCardList = new ArrayList<Map<String,Object>>();
			
			for (Map<String, Object> det : cardList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("ass_id") == null || "".equals(temp.get("ass_id"))) {
						continue;
					}
					if (det.get("ass_id").toString().equals(temp.get("ass_id").toString().split("@")[0])) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					delCardList.add(det);
				}
			}
			if(delCardList.size() > 0){
				delCard(delCardList);
			}
			
			Double in_money = 0.0;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_in_no", entityMap.get("ass_in_no"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);

				detailVo.put("in_amount", Integer.parseInt(detailVo.get("in_amount").toString()));
				detailVo.put("price", Double.parseDouble(detailVo.get("price").toString()));
				
				//累计折旧
				if (detailVo.get("add_depre") != null && !detailVo.get("add_depre").equals("")) {
					detailVo.put("add_depre", detailVo.get("add_depre"));
				} else {
					detailVo.put("add_depre", 0);
				}
				//累计折旧月份
				if (detailVo.get("add_depre_month") != null && !detailVo.get("add_depre_month").equals("")) {
					detailVo.put("add_depre_month", detailVo.get("add_depre_month"));
				} else {
					detailVo.put("add_depre_month", 0);
				}
				
				//预留残值
				if (detailVo.get("fore_money") != null && !detailVo.get("fore_money").equals("")) {
					detailVo.put("fore_money", detailVo.get("fore_money"));
				} else {
					detailVo.put("fore_money", 0);
				}
				
				Double cur_money = Double.parseDouble(detailVo.get("price").toString())
				- Double.parseDouble(detailVo.get("add_depre").toString()) - Double.parseDouble(detailVo.get("fore_money").toString());
				//资产净值
				detailVo.put("cur_money", cur_money);

				in_money = in_money + Double.parseDouble(detailVo.get("price").toString())
						* Integer.parseInt(detailVo.get("in_amount").toString());

				if (detailVo.get("fac_id") != null && !detailVo.get("fac_id").equals("") && !detailVo.get("fac_id").toString().equals("@")) {
					String fac_id_no = detailVo.get("fac_id").toString();
					detailVo.put("fac_id", fac_id_no.split("@")[0]);
					detailVo.put("fac_no", fac_id_no.split("@")[1]);
				} else {
					detailVo.put("fac_id", "");
					detailVo.put("fac_no", "");
				}

				if (detailVo.get("unit_code") != null && !detailVo.get("unit_code").equals("")) {
					detailVo.put("unit_code", detailVo.get("unit_code"));
				} else {
					detailVo.put("unit_code", "");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				if (detailVo.get("ass_spec") != null && !detailVo.get("ass_spec").equals("")) {
					detailVo.put("ass_spec", detailVo.get("ass_spec"));
				} else {
					detailVo.put("ass_spec", "*");
				}

				if (detailVo.get("ass_model") != null && !detailVo.get("ass_model").equals("")) {
					detailVo.put("ass_model", detailVo.get("ass_model"));
				} else {
					detailVo.put("ass_model", "*");
				}

				if (detailVo.get("ass_brand") != null && !detailVo.get("ass_brand").equals("")) {
					detailVo.put("ass_brand", detailVo.get("ass_brand"));
				} else {
					detailVo.put("ass_brand", "");
				}

				listVo.add(detailVo);
			}

			assChkInDetailGeneralMapper.delete(entityMap);
			assChkInDetailGeneralMapper.addBatch(listVo);
			entityMap.put("in_money", in_money);
			assChkInMainGeneralMapper.updateInMoney(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_in_no\":\"" + entityMap.get("ass_in_no").toString()
					+ "\",\"in_money\":\"" + in_money + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssChkInMainGeneral> list = (List<AssChkInMainGeneral>)assChkInMainGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssChkInMainGeneral> list = (List<AssChkInMainGeneral>)assChkInMainGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInMainGeneralMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssChkInMainGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInMainGeneralMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050701 资产盘盈入库主表(一般设备)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssChkInMainGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assChkInMainGeneralMapper.queryExists(entityMap);
	}
	
	private String formatNumberZero(Object obj) {
		if (obj != null) {
			if (obj.toString().equals("0")) {
				return "0";
			} else {
				return obj.toString();
			}
		} else {
			return "";
		}
	}
	
	@Override
	public String initAssChkInCardGeneral(Map<String, Object> map) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reSourceListVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = map.get("group_id").toString();
			String hos_id = map.get("hos_id").toString();
			String copy_code = map.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assTwoPath + "/03/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assOnePath + "/03/";// 资产性质
			List<Map<String, Object>> list = assChkInDetailGeneralMapper.queryByInit(map);
			
			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			
			for (Map<String, Object> temp : list) {
				for (int i = 0; i < Integer.parseInt(temp.get("in_amount").toString()); i++) {
					Map<String, Object> entityMap = new HashMap<String, Object>();
					entityMap.put("bill_table", "ASS_CARD_GENERAL");
					entityMap.put("store_code", temp.get("store_code"));
					entityMap.put("year", temp.get("year"));
					entityMap.put("month", temp.get("month"));
					entityMap.put("day", temp.get("day"));
					entityMap.put("group_id", temp.get("group_id"));
					entityMap.put("hos_id", temp.get("hos_id"));
					entityMap.put("copy_code", temp.get("copy_code"));
					String ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
					
					entityMap.put("group_id", map.get("group_id"));
					entityMap.put("hos_id", map.get("hos_id"));
					entityMap.put("copy_code", map.get("copy_code"));
					entityMap.put("ass_card_no", ass_card_no);
					entityMap.put("ass_id", temp.get("ass_id"));
					entityMap.put("ass_no", temp.get("ass_no"));
					entityMap.put("gb_code", temp.get("gb_code"));
					entityMap.put("ass_spec", temp.get("ass_spec"));
					entityMap.put("ass_mondl", temp.get("ass_model"));
					entityMap.put("ass_brand", temp.get("ass_brand"));
					entityMap.put("ass_amount", "1");
					entityMap.put("unit_code", temp.get("unit_code"));
					entityMap.put("fac_no", temp.get("fac_no"));
					entityMap.put("fac_id", temp.get("fac_id"));
					entityMap.put("ven_id", temp.get("ven_id"));
					entityMap.put("ven_no", temp.get("ven_no"));
					entityMap.put("is_dept", "0");
					entityMap.put("dept_id", temp.get("dept_id"));
					entityMap.put("dept_no", temp.get("dept_no"));
					entityMap.put("store_id", temp.get("store_id"));
					entityMap.put("store_no", temp.get("store_no"));
					
					entityMap.put("proc_store_id", temp.get("store_id"));
					entityMap.put("proc_store_no", temp.get("store_no"));
					
					if(temp.get("dept_id") == null || temp.get("dept_id").equals("")){
						Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);
						if(null == deptMap.get("dept_id") || "".equals(deptMap.get("dept_id"))){
							return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
						}
						if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
							Map<String, Object> deptNoMap = new HashMap<String, Object>();
							deptNoMap.put("group_id", deptMap.get("group_id"));
							deptNoMap.put("hos_id", deptMap.get("hos_id"));
							deptNoMap.put("dept_id", deptMap.get("dept_id"));
							DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
							entityMap.put("dept_id", deptDict.getDept_id());
							entityMap.put("dept_no", deptDict.getDept_no());
						}
					}
					
					entityMap.put("price", formatNumberZero(temp.get("price")));
					entityMap.put("depre_money", "0");
					entityMap.put("manage_depre_money", "0");
					entityMap.put("cur_money", formatNumberZero(temp.get("price")));
					entityMap.put("fore_money", "0");
					entityMap.put("buy_type", temp.get("bus_type_code"));
					entityMap.put("use_state", "0");
					entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
					entityMap.put("depr_method", temp.get("ass_depre_code"));
					entityMap.put("acc_depre_amount", formatNumberZero(temp.get("depre_years")));
					entityMap.put("is_manage_depre", formatNumberZero(temp.get("is_manage_depre")));
					entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
					entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
					entityMap.put("is_measure", formatNumberZero(temp.get("is_measure")));
					entityMap.put("is_throw", "0");
					entityMap.put("pact_code", null);
					entityMap.put("ins_money", null);
					entityMap.put("ins_date", null);
					entityMap.put("accept_emp", null);
					entityMap.put("accept_date", null);
					entityMap.put("service_date", null);
					entityMap.put("ass_seq_no", null);
					entityMap.put("location", null);
					entityMap.put("note", temp.get("note"));
					entityMap.put("ass_in_no", temp.get("ass_in_no"));
					entityMap.put("in_date", null);
					entityMap.put("run_date", null);
					entityMap.put("is_bar", formatNumberZero(temp.get("is_bar")));
					entityMap.put("bar_type", temp.get("bar_type"));
					entityMap.put("bar_code", ass_card_no);
					entityMap.put("is_init", "0");
					entityMap.put("dispose_type", null);
					entityMap.put("dispose_cost", null);
					entityMap.put("dispose_income", null);
					entityMap.put("dispose_tax", null);
					entityMap.put("ass_purpose", temp.get("ass_purpose"));
					entityMap.put("proj_id", temp.get("proj_id"));
					entityMap.put("proj_no", temp.get("proj_no"));
					entityMap.put("source_id", 1);
					entityMap.put("pay_money", "0");
					entityMap.put("unpay_money", formatNumberZero(temp.get("price")));
					if (temp.get("is_bar") != null && !temp.get("is_bar").equals("")) {
						if (temp.get("is_bar").toString().equals("1")) {
							if (temp.get("bar_type") != null && !temp.get("bar_type").equals("")) {
								if (temp.get("bar_type").toString().equals("1")) {
									FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
									entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

								} else if (temp.get("bar_type").toString().equals("2")) {
									FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
									entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
								}
							}
						}

					}

					if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
							&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
						entityMap.put("area", 1);
						entityMap.put("ass_year", getAssYear());
						entityMap.put("ass_month", getAssMonth());
					}

					listVo.add(entityMap);
				}
			}
			
			String is_source = map.get("is_source").toString();
			List<Map> source_data = null;
			if(is_source.equals("1")){
				source_data = ChdJson.fromJsonAsList(Map.class, map.get("source_data").toString());
			}
			
			for(Map<String, Object> mapPrePay : listVo){
				if(is_source.equals("1")){
					for(Map source : source_data){
						if(source.get("source_id") == null || source.get("source_id").equals("")){
							continue;
						}
						if(source.get("point") == null || source.get("point").equals("") || source.get("point").equals("0")){
							continue;
						}
						Double point = Double.parseDouble(source.get("point").toString());
						Double source_price = point;
						Map<String, Object> sourceMap = new HashMap<String, Object>(); 
						sourceMap.put("group_id", mapPrePay.get("group_id"));
						sourceMap.put("hos_id", mapPrePay.get("hos_id"));
						sourceMap.put("copy_code", mapPrePay.get("copy_code"));
						sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						sourceMap.put("source_id", source.get("source_id").toString());
						sourceMap.put("price", source_price + "");
						sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
						sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
						sourceMap.put("cur_money", source_price + "");
						sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
						sourceMap.put("pay_money", "0");
						sourceMap.put("unpay_money", source_price + "");
						reSourceListVo.add(sourceMap);
					}
				}else{
					Map<String, Object> sourceMap = new HashMap<String, Object>(); 
					sourceMap.put("group_id", mapPrePay.get("group_id"));
					sourceMap.put("hos_id", mapPrePay.get("hos_id"));
					sourceMap.put("copy_code", mapPrePay.get("copy_code"));
					sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
					sourceMap.put("source_id", "1");
					sourceMap.put("price", mapPrePay.get("price") + "");
					sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
					sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
					sourceMap.put("cur_money", mapPrePay.get("cur_money") + "");
					sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
					sourceMap.put("pay_money", "0");
					sourceMap.put("unpay_money", mapPrePay.get("price") + "");
					reSourceListVo.add(sourceMap);
				}
			}

			assCardGeneralMapper.addBatch(listVo);
			assResourceGeneralMapper.addBatch(reSourceListVo);
			assShareDeptRGeneralMapper.addBatch(listVo);
			assShareDeptGeneralMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String getAssYear() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}

	private String getAssMonth() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(4, 6);
	}

	// 引入DAO操作
	@Resource(name = "assBillNoMapper")
	private final AssBillNoMapper assBillNoMapper = null;

	public AssBillNo queryAssBillNoByName(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_table") != null) {
			entityMap.put("bill_table", entityMap.get("bill_table").toString().toUpperCase());
		}
		return assBillNoMapper.queryAssBillNoByCode(entityMap);
	}
	@Override
	public String updateConfirmChkInGeneral(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			
			assCardGeneralMapper.updateConfirm(listVo);
			assChkInMainGeneralMapper.updateConfirmChkInGeneral(listVo);
			
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public List<String> queryState(Map<String, Object> entityMap) throws DataAccessException {
		 return assChkInMainGeneralMapper.queryState(entityMap);
		  
	}
	
	//出库模板打印（包含主从表）
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public Map<String,Object> assChkInMainGeneralByPrintTemlate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		Map<String,Object> reMap=new HashMap<String,Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssChkInMainGeneralMapper assChkInMainGeneralMapper = (AssChkInMainGeneralMapper)context.getBean("assChkInMainGeneralMapper");
		
		
		if("1".equals(String.valueOf(entityMap.get("p_num")))){
			//查询移库主表批量
			List<Map<String,Object>> map = assChkInMainGeneralMapper.queryAssChkInMainGeneralPrintTemlateByMainBatch(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assChkInMainGeneralMapper.queryAssChkInMainGeneralPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}else{
			//查询移库主表
			Map<String,Object> map = assChkInMainGeneralMapper.queryAssChkInMainGeneralPrintTemlateByMain(entityMap);
			//查询移库明细表
			List<Map<String,Object>> list = assChkInMainGeneralMapper.queryAssChkInMainGeneralPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list);
			
			return reMap;
		}
		
	}
	@Override
	public String initAssChkInBatchCardGeneral(Map<String, Object> map) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reSourceListVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = map.get("group_id").toString();
			String hos_id = map.get("hos_id").toString();
			String copy_code = map.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assTwoPath + "/03/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assOnePath + "/03/";// 资产性质
			List<Map<String, Object>> list = assChkInDetailGeneralMapper.queryByInit(map);
			
			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			
			for (Map<String, Object> temp : list) {
				for (int i = 0; i < Integer.parseInt(temp.get("in_amount").toString()); i++) {
					Map<String, Object> entityMap = new HashMap<String, Object>();
					entityMap.put("bill_table", "ASS_CARD_GENERAL");
					entityMap.put("store_code", temp.get("store_code"));
					entityMap.put("year", temp.get("year"));
					entityMap.put("month", temp.get("month"));
					entityMap.put("day", temp.get("day"));
					entityMap.put("group_id", temp.get("group_id"));
					entityMap.put("hos_id", temp.get("hos_id"));
					entityMap.put("copy_code", temp.get("copy_code"));
					String ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
					entityMap.put("group_id", map.get("group_id"));
					entityMap.put("hos_id", map.get("hos_id"));
					entityMap.put("copy_code", map.get("copy_code"));
					entityMap.put("ass_card_no", ass_card_no);
					entityMap.put("ass_id", temp.get("ass_id"));
					entityMap.put("ass_no", temp.get("ass_no"));
					entityMap.put("gb_code", temp.get("gb_code"));
					entityMap.put("ass_spec", temp.get("ass_spec"));
					entityMap.put("ass_mondl", temp.get("ass_model"));
					entityMap.put("ass_brand", temp.get("ass_brand"));
					entityMap.put("ass_amount", "1");
					entityMap.put("unit_code", temp.get("unit_code"));
					entityMap.put("fac_no", temp.get("fac_no"));
					entityMap.put("fac_id", temp.get("fac_id"));
					entityMap.put("ven_id", temp.get("ven_id"));
					entityMap.put("ven_no", temp.get("ven_no"));
					entityMap.put("is_dept", "0");
					entityMap.put("dept_id", temp.get("dept_id"));
					entityMap.put("dept_no", temp.get("dept_no"));
					entityMap.put("store_id", temp.get("store_id"));
					entityMap.put("store_no", temp.get("store_no"));
					
					entityMap.put("proc_store_id", temp.get("store_id"));
					entityMap.put("proc_store_no", temp.get("store_no"));
					
					if(temp.get("dept_id") == null || temp.get("dept_id").equals("")){
						Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);
						if(null == deptMap.get("dept_id") || "".equals(deptMap.get("dept_id"))){
							return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
						}
						if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
							Map<String, Object> deptNoMap = new HashMap<String, Object>();
							deptNoMap.put("group_id", deptMap.get("group_id"));
							deptNoMap.put("hos_id", deptMap.get("hos_id"));
							deptNoMap.put("dept_id", deptMap.get("dept_id"));
							DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
							entityMap.put("dept_id", deptDict.getDept_id());
							entityMap.put("dept_no", deptDict.getDept_no());
						}
					}
					
					entityMap.put("price", formatNumberZero(temp.get("price")));
					entityMap.put("depre_money", "0");
					entityMap.put("manage_depre_money", "0");
					entityMap.put("cur_money", formatNumberZero(temp.get("price")));
					entityMap.put("fore_money", "0");
					entityMap.put("buy_type", temp.get("bus_type_code"));
					entityMap.put("use_state", "0");
					entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
					entityMap.put("depr_method", temp.get("ass_depre_code"));
					entityMap.put("acc_depre_amount", formatNumberZero(temp.get("depre_years")));
					entityMap.put("is_manage_depre", formatNumberZero(temp.get("is_manage_depre")));
					entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
					entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
					entityMap.put("is_measure", formatNumberZero(temp.get("is_measure")));
					entityMap.put("is_throw", "0");
					entityMap.put("pact_code", null);
					entityMap.put("ins_money", null);
					entityMap.put("ins_date", null);
					entityMap.put("accept_emp", null);
					entityMap.put("accept_date", null);
					entityMap.put("service_date", null);
					entityMap.put("ass_seq_no", null);
					entityMap.put("location", null);
					entityMap.put("note", temp.get("note"));
					entityMap.put("ass_in_no", temp.get("ass_in_no"));
					entityMap.put("in_date", null);
					entityMap.put("run_date", null);
					entityMap.put("is_bar", formatNumberZero(temp.get("is_bar")));
					entityMap.put("bar_type", temp.get("bar_type"));
					entityMap.put("bar_code", ass_card_no);
					entityMap.put("is_init", "0");
					entityMap.put("dispose_type", null);
					entityMap.put("dispose_cost", null);
					entityMap.put("dispose_income", null);
					entityMap.put("dispose_tax", null);
					entityMap.put("ass_purpose", temp.get("ass_purpose"));
					entityMap.put("proj_id", temp.get("proj_id"));
					entityMap.put("proj_no", temp.get("proj_no"));
					entityMap.put("source_id", 1);
					entityMap.put("pay_money", "0");
					entityMap.put("unpay_money", formatNumberZero(temp.get("price")));
					if (temp.get("is_bar") != null && !temp.get("is_bar").equals("")) {
						if (temp.get("is_bar").toString().equals("1")) {
							if (temp.get("bar_type") != null && !temp.get("bar_type").equals("")) {
								if (temp.get("bar_type").toString().equals("1")) {
									FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
									entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

								} else if (temp.get("bar_type").toString().equals("2")) {
									FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
									entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
								}
							}
						}

					}

					if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
							&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
						entityMap.put("area", 1);
						entityMap.put("ass_year", getAssYear());
						entityMap.put("ass_month", getAssMonth());
					}

					listVo.add(entityMap);
				}
			}
			
			String is_source = map.get("is_source").toString();
			Double total_price = 0.0;
			List<Map> source_data = null;
			if(is_source.equals("1")){
				source_data = ChdJson.fromJsonAsList(Map.class, map.get("source_data").toString());
				total_price = Double.parseDouble(map.get("total_price").toString());
			}
			
			for(Map<String, Object> mapPrePay : listVo){
				if(is_source.equals("1")){
					Double price = Double.parseDouble(mapPrePay.get("price").toString());
					for(Map source : source_data){
						if(source.get("source_id") == null || source.get("source_id").equals("")){
							continue;
						}
						if(source.get("point") == null || source.get("point").equals("") || source.get("point").equals("0")){
							continue;
						}
						Double point = Double.parseDouble(source.get("point").toString());
						Double source_price = ( price * point )/ total_price;
						Map<String, Object> sourceMap = new HashMap<String, Object>(); 
						sourceMap.put("group_id", mapPrePay.get("group_id"));
						sourceMap.put("hos_id", mapPrePay.get("hos_id"));
						sourceMap.put("copy_code", mapPrePay.get("copy_code"));
						sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
						sourceMap.put("source_id", source.get("source_id").toString());
						sourceMap.put("price", source_price + "");
						sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
						sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
						sourceMap.put("cur_money", source_price + "");
						sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
						sourceMap.put("pay_money", "0");
						sourceMap.put("unpay_money", source_price + "");
						reSourceListVo.add(sourceMap);
					}
				}else{
					Map<String, Object> sourceMap = new HashMap<String, Object>(); 
					sourceMap.put("group_id", mapPrePay.get("group_id"));
					sourceMap.put("hos_id", mapPrePay.get("hos_id"));
					sourceMap.put("copy_code", mapPrePay.get("copy_code"));
					sourceMap.put("ass_card_no", mapPrePay.get("ass_card_no"));
					sourceMap.put("source_id", "1");
					sourceMap.put("price", mapPrePay.get("price") + "");
					sourceMap.put("depre_money", mapPrePay.get("depre_money") + "");
					sourceMap.put("manage_depre_money", mapPrePay.get("manage_depre_money") + "");
					sourceMap.put("cur_money", mapPrePay.get("cur_money") + "");
					sourceMap.put("fore_money", mapPrePay.get("fore_money") + "");
					sourceMap.put("pay_money", "0");
					sourceMap.put("unpay_money", mapPrePay.get("price") + "");
					reSourceListVo.add(sourceMap);
				}
			}

			assCardGeneralMapper.addBatch(listVo);
			assResourceGeneralMapper.addBatch(reSourceListVo);
			assShareDeptRGeneralMapper.addBatch(listVo);
			assShareDeptGeneralMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}
