package com.chd.hrp.ass.serviceImpl.tend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.apply.AssApplyDeptMapper;
import com.chd.hrp.ass.dao.tend.AssTendMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.tend.AssTendDetail;
import com.chd.hrp.ass.entity.tend.AssTendFile;
import com.chd.hrp.ass.entity.tend.AssTendMain;
import com.chd.hrp.ass.service.tend.AssTendService;
import com.github.pagehelper.PageInfo;
/**
 * 招标管理
 * @author cyw
 *
 */
@Service("assTendService")
public class AssTendServiceImpl implements AssTendService {
	 private static Logger logger = Logger.getLogger(AssTendServiceImpl.class);
	 
	@Resource(name="assTendMapper") 
	private final AssTendMapper assTendMapper=null;
	
	@Resource(name = "assApplyDeptMapper")
	private final AssApplyDeptMapper assApplyDeptMapper = null;
	
	@Override
	public String queryTendMethod(Map<String, Object> mapvo)
			throws DataAccessException {
		return JSON.toJSONString(assTendMapper.queryTendMethod(mapvo));
	}
	@Override
	/**
	 * 查询主方法
	 */
	public String queryAsstendMain(Map<String, Object> mapvo)
			throws DataAccessException {
		SysPage sysPage=new SysPage();
		 sysPage = (SysPage)mapvo.get("sysPage");
		 
		RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AssTendMain> list=assTendMapper.queryAsstendMain(mapvo, rowBounds);
		
		PageInfo page=new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	@Override
	public String deleteTend(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		 try
		    {
			 
		      int state = assTendMapper.deleteTend(entityMap);
		     // System.out.println(state);
		      
		      if(state==1){
		    	int res=  assTendMapper.deleteTendDetailm(entityMap);

		    		assTendMapper.deletemap(entityMap);
		    		

		      }
		      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		    }
		    catch (Exception e)
		    {
		      logger.error(e.getMessage(), e);
		    }
		    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteTend\"}";
	}
	/**
	 * 明细查询
	 */
	public String queryAsstendDetail(Map<String, Object> mapvo){
	SysPage sysPage=new SysPage();
	 sysPage = (SysPage)mapvo.get("sysPage");
	 
	RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<AssTendDetail> list=assTendMapper.queryAsstendDetail(mapvo, rowBounds);
	
	PageInfo page=new PageInfo(list);
	
	return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 明细删除方法
	 */
	public String deleteTendDetail(List<Map<String, Object>> mapVo)
			throws DataAccessException {
		 try
		    {
		      int state = assTendMapper.deleteTendDetail(mapVo);
		    //成功后更新主表金额（删除明细方法）
		     for (Map<String, Object> map : mapVo) {
		    	 int res=assTendMapper.updateBidValue(map);
			}
		     // int res=assTendMapper.updateBidValue(mapVo);
		      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		    }
		    catch (Exception e)
		    {
		      logger.error(e.getMessage(), e);
		      throw new SysException(e.getMessage());
		    }
		   // return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteTendDetail\"}";
	}
	/**
	 * 新增方法
	 */
	public String savetendInfomation(Map<String, Object> mapVo) throws DataAccessException {
	try{
		List<Map> listmap = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVoDetail").toString());
		//System.out.println(listmap);
		String user_id=mapVo.get("user_id").toString();
		for (Map<String, Object> tendMap : listmap) {
			tendMap.put("user_id", user_id);  //获取制单人
			///过滤空数据
			if (tendMap.get("bid_code") == null || "".equals(tendMap.get("bid_code"))) {
				continue;
			}
		     String bid_id="";
	
			///如果存在ID就更新，不存在新增
			if((tendMap.get("bid_id") != null )&& (!"".equals(tendMap.get("bid_id")))){
			///下面走更新方法
				if (tendMap.get("group_id") == null) {
					  tendMap.put("group_id", SessionManager.getGroupId());
				    }
				    if (tendMap.get("hos_id") == null) {
				    	tendMap.put("hos_id", SessionManager.getHosId());
				    }
				    if (tendMap.get("copy_code") == null) {
				    	tendMap.put("copy_code", SessionManager.getCopyCode());
				    }
				    
				AssTendMain listTend=assTendMapper.queryAsstendMainExit(tendMap);
				
				//首先判断编码是否存在
				if((listTend!=null)&&(!listTend.getBid_id().toString().equals(tendMap.get("bid_id").toString()))){
					  return "{\"error\":\"更新失败，编码重复\",\"state\":\"false\"}";
				}else{
				
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_noticedate"))) {
						tendMap.put("bid_noticedate", DateUtil.stringToDate(tendMap.get("bid_noticedate").toString(), "yyyy-MM-dd"));
					}
				
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_purstart"))) {
				tendMap.put("bid_purstart", DateUtil.stringToDate(tendMap.get("bid_purstart").toString(), "yyyy-MM-dd"));
				}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_purend"))) {
				tendMap.put("bid_purend", DateUtil.stringToDate(tendMap.get("bid_purend").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_answerdate"))) {
				tendMap.put("bid_answerdate", DateUtil.stringToDate(tendMap.get("bid_answerdate").toString(), "yyyy-MM-dd"));
			}
			
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_calibratedate"))) {
				tendMap.put("bid_calibratedate", DateUtil.stringToDate(tendMap.get("bid_calibratedate").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_start"))) {
				tendMap.put("bid_start", DateUtil.stringToDate(tendMap.get("bid_start").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_end"))) {
				tendMap.put("bid_end", DateUtil.stringToDate(tendMap.get("bid_end").toString(), "yyyy-MM-dd"));
			}
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_makertime"))) {
				tendMap.put("bid_makertime", DateUtil.stringToDate(tendMap.get("bid_makertime").toString(), "yyyy-MM-dd"));
			}
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_checkdate"))) {
				tendMap.put("bid_checkdate", DateUtil.stringToDate(tendMap.get("bid_checkdate").toString(), "yyyy-MM-dd"));
			}
			
			
				assTendMapper.updateTendMain(tendMap);
				}
			}else{
				if (tendMap.get("group_id") == null) {
					  tendMap.put("group_id", SessionManager.getGroupId());
				    }
				    if (tendMap.get("hos_id") == null) {
				    	tendMap.put("hos_id", SessionManager.getHosId());
				    }
				    if (tendMap.get("copy_code") == null) {
				    	tendMap.put("copy_code", SessionManager.getCopyCode());
				    }
				    AssTendMain listTend=assTendMapper.queryAsstendMainExit(tendMap);
				//System.out.println(tendMap);
				//首先判断编码是否存在
				if(listTend!=null){
					  return "{\"error\":\"添加失败，编码重复\",\"state\":\"false\"}";
				}else{
				
			     //循环存储
				 
				if (StringUtils.isNotEmpty((String) tendMap.get("bid_noticedate"))) {
					tendMap.put("bid_noticedate", DateUtil.stringToDate(tendMap.get("bid_noticedate").toString(), "yyyy-MM-dd"));
				}
				
				if (StringUtils.isNotEmpty((String) tendMap.get("bid_purstart"))) {
					tendMap.put("bid_purstart", DateUtil.stringToDate(tendMap.get("bid_purstart").toString(), "yyyy-MM-dd"));
				}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_purend"))) {
				tendMap.put("bid_purend", DateUtil.stringToDate(tendMap.get("bid_purend").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_answerdate"))) {
				tendMap.put("bid_answerdate", DateUtil.stringToDate(tendMap.get("bid_answerdate").toString(), "yyyy-MM-dd"));
			}
			
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_calibratedate"))) {
				tendMap.put("bid_calibratedate", DateUtil.stringToDate(tendMap.get("bid_calibratedate").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_start"))) {
				tendMap.put("bid_start", DateUtil.stringToDate(tendMap.get("bid_start").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_end"))) {
				tendMap.put("bid_end", DateUtil.stringToDate(tendMap.get("bid_end").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) tendMap.get("bid_makertime"))) {
				tendMap.put("bid_makertime", DateUtil.stringToDate(tendMap.get("bid_makertime").toString(), "yyyy-MM-dd"));
			}
			
				Date make_date=new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				//Date make_date=new Date();
				tendMap.put("make_date", format.format(make_date));

				int state=  assTendMapper.savetendInfomation(tendMap);  
				bid_id=String.valueOf(assTendMapper.queryAssTendSequence());
				tendMap.put("bid_id", bid_id);
				//下面开始插入明细表
				int statedetail=assTendMapper.saveAssTendDetail(tendMap);
				
				//插入采购招标勾稽关系
				Map<String, Object> tendApply=new HashMap<String, Object>();
				tendApply.put("group_id", SessionManager.getGroupId());
				tendApply.put("hos_id", SessionManager.getHosId());
				tendApply.put("copy_code", SessionManager.getCopyCode());
				tendApply.put("bid_id", bid_id);
				String apply_ids=tendMap.get("apply_id").toString();
				int len=apply_ids.split(",").length;
				for(int i=0;i<len;i++){
					String apply_id=apply_ids.split(",")[i];
					tendApply.put("apply_id", apply_id);
					int rest=assTendMapper.saveTendApplyMap(tendApply);
				}
			}
		}
			
			
		}
		  return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
	}catch(Exception e) {

		logger.error(e.getMessage(), e);

		throw new SysException(e.getMessage());
		
	}
	}
	/**
	 * 返回主页面信息
	 */
	public AssTendMain queryassTendMain(Map<String, Object> mapVo)
			throws DataAccessException {
		AssTendMain assTendMain=assTendMapper.queryAsstendMainPage(mapVo);
		return assTendMain;
	}
	/**
	 * 设备招标更新方法
	 */
	public String updateAssTendMain(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
		AssTendMain listTend=assTendMapper.queryAsstendMainExit(mapVo);
		
		//首先判断编码是否存在
		if((listTend!=null)&&(!listTend.getBid_id().toString().equals(mapVo.get("bid_id").toString()))){
			  return "{\"error\":\"更新失败，编码重复\",\"state\":\"false\"}";
		}else{
		
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_noticedate"))) {
				mapVo.put("bid_noticedate", DateUtil.stringToDate(mapVo.get("bid_noticedate").toString(), "yyyy-MM-dd"));
			}
			
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_purstart"))) {
				mapVo.put("bid_purstart", DateUtil.stringToDate(mapVo.get("bid_purstart").toString(), "yyyy-MM-dd"));
			}
		
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_purend"))) {
			mapVo.put("bid_purend", DateUtil.stringToDate(mapVo.get("bid_purend").toString(), "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_answerdate"))) {
			mapVo.put("bid_answerdate", DateUtil.stringToDate(mapVo.get("bid_answerdate").toString(), "yyyy-MM-dd"));
		}
		
		
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_calibratedate"))) {
			mapVo.put("bid_calibratedate", DateUtil.stringToDate(mapVo.get("bid_calibratedate").toString(), "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_start"))) {
			mapVo.put("bid_start", DateUtil.stringToDate(mapVo.get("bid_start").toString(), "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_end"))) {
			mapVo.put("bid_end", DateUtil.stringToDate(mapVo.get("bid_end").toString(), "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_makertime"))) {
			mapVo.put("bid_makertime", DateUtil.stringToDate(mapVo.get("bid_makertime").toString(), "yyyy-MM-dd"));
		}
		if (StringUtils.isNotEmpty((String) mapVo.get("bid_checkdate"))) {
			mapVo.put("bid_checkdate", DateUtil.stringToDate(mapVo.get("bid_checkdate").toString(), "yyyy-MM-dd"));
		}
		
		assTendMapper.updateTendMain(mapVo);
		}
	  return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
		}catch(Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
			
		}
	}
	@Override
	/**
	 * 明细添加方法
	 */
	public String addAssTendDetail(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bidd_budgreachdate"))) {
				mapVo.put("bidd_budgreachdate", DateUtil.stringToDate(mapVo.get("bidd_budgreachdate").toString(), "yyyy-MM-dd"));
			}
			
			int state=assTendMapper.addAssTendDetail(mapVo);
			
			//成功后更新主表金额（待完善）
			int res=assTendMapper.updateBidValue(mapVo);
			  return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	
	}
	/**
	 * 资产下拉框
	 */
	public String queryAssDict(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return JSON.toJSONString(assTendMapper.queryAssDict(mapVo));
	}
	@Override
	public String queryAssFac(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(assTendMapper.queryAssFac(mapVo));
	}
	/**
	 * 查询明细表，用来返回更新页面的值
	 */
	public AssTendDetail queryAsstendDetaill(Map<String, Object> mapVo)
			throws DataAccessException {
	  AssTendDetail assTendDetail=assTendMapper.queryAsstendDetail(mapVo);
		return assTendDetail;
	}
	/**
	 * 更新明细
	 */
	public String updateAssTendDetail(Map<String, Object> mapVo)
			throws DataAccessException {
	try{
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bidd_budgreachdate"))) {
				mapVo.put("bidd_budgreachdate", DateUtil.stringToDate(mapVo.get("bidd_budgreachdate").toString(), "yyyy-MM-dd"));
			}
			
			int state=assTendMapper.updateAssTendDetail(mapVo);
			//成功后更新主表金额（待完善）
			int res=assTendMapper.updateBidValue(mapVo);
			  return "{\"msg\":\"更新成功\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 附件上传会写文件表
	 */
	public String addAssTendFile(Map<String, Object> mapVo)
			throws DataAccessException {
		assTendMapper.addAssTendFile(mapVo);
		return null;
	}
	/**
	 * 查询附件（判断是否存在）
	 */
	public List<AssTendFile> queryAssTendFileExit(Map<String, Object> mapVo)
			throws DataAccessException {
		List<AssTendFile> list=assTendMapper.queryAssTendFile(mapVo);
		return list;
	}
	
	/**
	 * 
	 */
	public String queryAssTendFile(Map<String, Object> mapvo) {
		SysPage sysPage=new SysPage();
		 sysPage = (SysPage)mapvo.get("sysPage");
		 
		RowBounds rowBounds=new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AssTendFile> list=assTendMapper.queryAssTendFile(mapvo, rowBounds);
		
		PageInfo page=new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	/**
	 * 删除文件
	 */
	public String deleteTendFile(Map<String, Object> mapVo)
			throws DataAccessException {
		 try
		    {
			 
		    int state=assTendMapper.deleteTendFile(mapVo);
		      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		    }
		    catch (Exception e)
		    {
		      logger.error(e.getMessage(), e);
		    }
		    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteTend\"}";

	}
	@Override
	public String addAssTendMain(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			AssTendMain listTend=assTendMapper.queryAsstendMainExit(mapVo);
			String bid_id="";
			//首先判断编码是否存在
			if((listTend!=null)){
				  return "{\"error\":\"更新失败，编码重复\",\"state\":\"false\"}";
			}else{
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_noticedate"))) {
					mapVo.put("bid_noticedate", DateUtil.stringToDate(mapVo.get("bid_noticedate").toString(), "yyyy-MM-dd"));
				}
				
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_purstart"))) {
					mapVo.put("bid_purstart", DateUtil.stringToDate(mapVo.get("bid_purstart").toString(), "yyyy-MM-dd"));
				}
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_purend"))) {
				mapVo.put("bid_purend", DateUtil.stringToDate(mapVo.get("bid_purend").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_answerdate"))) {
				mapVo.put("bid_answerdate", DateUtil.stringToDate(mapVo.get("bid_answerdate").toString(), "yyyy-MM-dd"));
			}
			
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_calibratedate"))) {
				mapVo.put("bid_calibratedate", DateUtil.stringToDate(mapVo.get("bid_calibratedate").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_start"))) {
				mapVo.put("bid_start", DateUtil.stringToDate(mapVo.get("bid_start").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_end"))) {
				mapVo.put("bid_end", DateUtil.stringToDate(mapVo.get("bid_end").toString(), "yyyy-MM-dd"));
			}
			
			if (StringUtils.isNotEmpty((String) mapVo.get("bid_checkdate"))) {
				mapVo.put("bid_checkdate", DateUtil.stringToDate(mapVo.get("bid_checkdate").toString(), "yyyy-MM-dd"));
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date make_date=new Date();
			mapVo.put("bid_makertime", format.format(make_date));
			
			int sta=assTendMapper.addAssTendMain(mapVo);
			 bid_id=String.valueOf(assTendMapper.queryAssTendSequence());
			}
		  return "{\"msg\":\"添加成功\",\"state\":\"true\",\"bid_id\":\""+bid_id+"\"}";
			}catch(Exception e) {

				logger.error(e.getMessage(), e);
				
			}
		   return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAssTendMain\"}";
	}
	/**
	 * 提交
	 */
	public String updateSubmit(Map<String, Object> mapVo)
			throws DataAccessException {
		try
	    {
			
	      int state = assTendMapper.updateSubmit(mapVo);
	      
	      return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
	    }
	    catch (Exception e)
	    {
	      logger.error(e.getMessage(), e);
	    }
	    return "{\"error\":\"提交失败 数据库异常 请联系管理员! 错误编码 updateSubmit\"}";
	}
	/**
	 * 全选按钮功能
	 */
	public String queryassTendcheckAll(Map<String, Object> mapVo)
			throws DataAccessException {
     try{
		List<AssApplyDept> list = assApplyDeptMapper.queryAssApplyDept(mapVo);
		double apply_money=0;
		String apply_id="";
		for (AssApplyDept assApplyDept : list) {
			if(assApplyDept!=null){
				if(assApplyDept.getApply_id()!=0){
			 apply_money=apply_money+assApplyDept.getApply_money();
			 String apply_ids=String.valueOf(assApplyDept.getApply_id());
			 
			 if("".equals(apply_id)){
				 apply_id=apply_ids; 
			 }else{
				 apply_id=apply_id+","+apply_ids;	 
			 }
				}
			}
		}
		return "{\"msg\":\"添加成功\",\"state\":\"true\",\"apply_id\":\""+apply_id+"\",\"apply_money\":\""+apply_money+"\"}";
     }catch(Exception e){
    	 
     }
     return "{\"error\":\"操作失败! 错误编码 queryassTendcheckAll\"}";
	}


}
