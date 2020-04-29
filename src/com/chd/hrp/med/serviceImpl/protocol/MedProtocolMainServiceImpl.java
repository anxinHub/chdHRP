/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.protocol.MedProtocolDetailMapper;
import com.chd.hrp.med.dao.protocol.MedProtocolFileMapper;
import com.chd.hrp.med.dao.protocol.MedProtocolMainMapper;
import com.chd.hrp.med.entity.MedProtocolMain;
import com.chd.hrp.med.service.protocol.MedProtocolMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 *state：0：新建 1：审核 2：确认<BR> 
 * @Table:
 * MED_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medProtocolMainService")
public class MedProtocolMainServiceImpl implements MedProtocolMainService {

	private static Logger logger = Logger.getLogger(MedProtocolMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medProtocolMainMapper")
	private final MedProtocolMainMapper medProtocolMainMapper = null;
    
	@Resource(name = "medProtocolDetailMapper")
	private final MedProtocolDetailMapper medProtocolDetailMapper = null;
	
	@Resource(name = "medProtocolFileMapper")
	private final MedProtocolFileMapper medProtocolFileMapper = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
	/**
	 * @Description 
	 * 添加state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state：0：新建 1：审核 2：确认

		List<MedProtocolMain> list = medProtocolMainMapper.queryMedProtocolMainByID(entityMap);

		if (list.size()>0) {

			return "{\"error\":\"协议名称"+entityMap.get("protocol_name").toString()+"重复,请重新添加.\",\"state\":\"false\"}";

		}
		entityMap.put("creat_emp", null);
		entityMap.put("check_emp", null);
		entityMap.put("confirm_emp", null);
		entityMap.put("state", 1);
		entityMap.put("spell", StringTool.toPinyinShouZiMu(entityMap.get("protocol_name").toString()));
		entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("sign_date").toString(), "yyyy-MM-dd"));
		
		
		//判断签订日期是否在系统启用之前，用来判断是否是期初协议还是其他协议
		int count = 0;
		count = medCommonMapper.existsDateCheckBefore(entityMap);
		if(count > 0){
			entityMap.put("is_init", "1");//期初
		}else{
			entityMap.put("is_init", "0");//采购
		}
		
		try {
			
			int state = medProtocolMainMapper.addMedProtocolMain(entityMap);
			
			Long protocol_id = medProtocolMainMapper.qureyCurrval();
		
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+"|"+
				entityMap.get("hos_id").toString()+"|"+
				entityMap.get("copy_code").toString()+"|"+
				protocol_id+"|"+
				entityMap.get("state").toString()+"|"
				+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedProtocolMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedProtocolMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medProtocolMainMapper.addBatchMedProtocolMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedProtocolMain\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException{
		
		List<MedProtocolMain> list = medProtocolMainMapper.queryMedProtocolMainByID(entityMap);

		if (list.size()>0) {

			return "{\"error\":\"协议名称"+entityMap.get("protocol_name").toString()+"重复,请重新添加.\"}";

		}
		try {
			
		  int state = medProtocolMainMapper.updateMedProtocolMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedProtocolMain\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedProtocolMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medProtocolMainMapper.updateBatchMedProtocolMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedProtocolMain\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedProtocolMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			int state = medProtocolMainMapper.deleteMedProtocolMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedProtocolMain\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedProtocolMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			Long stateExists = medProtocolMainMapper.existsMedProtocolMainStateBatch(entityList);
			
			if(stateExists > 0){
				return "{\"error\":\"删除失败 !只有新建状态的数据允许删除 deleteBatchMedProtocolMain\"}";
			}
			
			medProtocolDetailMapper.deleteBatchMedProtocolDetail(entityList);
			medProtocolFileMapper.deleteBatchMedProtocolFile(entityList);
			medProtocolMainMapper.deleteBatchMedProtocolMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedProtocolMain\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedProtocolMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象state：0：新建 1：审核 2：确认
		
		MedProtocolMain medProtocolMain = queryMedProtocolMainByCode(entityMap);

		if (medProtocolMain != null) {

			int state = medProtocolMainMapper.updateMedProtocolMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medProtocolMainMapper.addMedProtocolMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedProtocolMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedProtocolMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedProtocolMain> list = medProtocolMainMapper.queryMedProtocolMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedProtocolMain> list = medProtocolMainMapper.queryMedProtocolMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 多表联合查询 采购协议详细信息 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedProtocolMainInfo(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedProtocolMainInfo(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedProtocolMainInfo(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedProtocolMain queryMedProtocolMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medProtocolMainMapper.queryMedProtocolMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取state：0：新建 1：审核 2：确认<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedProtocolMain
	 * @throws DataAccessException
	*/
	@Override
	public MedProtocolMain queryMedProtocolMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medProtocolMainMapper.queryMedProtocolMainByUniqueness(entityMap);
	}
	/**
	 * 据协议类型Id 查询其开始年月 、协议前缀 ,同时生成 协议编号
	 * @param mapVo
	 * @return
	 */
	public String queryMedProtocolTypePre(Map<String, Object> mapVo) throws DataAccessException {
		
		Map<String,Object> preMap = medProtocolMainMapper.queryMedProtocolTypePre(mapVo);
		
		Map<String,Object> codeMap = new HashMap<String,Object>();
		codeMap.put("group_id", preMap.get("group_id"));
		codeMap.put("hos_id", preMap.get("hos_id"));
		codeMap.put("copy_code", preMap.get("copy_code"));
		codeMap.put("year", mapVo.get("year"));
		codeMap.put("table_code", "MED_PROTOCOL_MAIN");
		codeMap.put("prefixe", preMap.get("pre"));
		// 判断该 类型 协议编码是否存在（存在取 max_no,不存在 则max_no=1 添加新记录 ）
		int count = medNoManageMapper.queryIsExists(codeMap);
		String max_no = "";
		if(count == 0){
			max_no = "1";
			codeMap.put("max_no", max_no);
			medNoManageMapper.add(codeMap);
		}else{
			//更新该业务流水码+1
			medNoManageMapper.updateMaxNo(codeMap);
			//取出该业务更新后的流水码
			max_no = medNoManageMapper.queryMaxCode(codeMap);
		}
		//补充流水号为4位（如：0001）
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		codeMap.put("max_no", max_no);
		return ChdJson.toJson(codeMap);
	}
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * grid明细编辑  文档类别下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedFileType(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(medProtocolMainMapper.queryMedFileType(mapVo, rowBounds));
	}
	/**
	 * grid明细编辑  管理科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryManaDept(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(medProtocolMainMapper.queryManaDept(mapVo, rowBounds));
	}
	/**
	 * grid明细编辑  管理员下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryManaEmp(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(medProtocolMainMapper.queryManaEmp(mapVo, rowBounds));
	}
	/**
	 * 物资药品明细列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedInvDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedInvDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	/**
	 * 根据ID 多表联合查询采购协议（修改页面回值用）
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryMedProtocolMainUnit(Map<String, Object> mapVo) throws DataAccessException{
		return medProtocolMainMapper.queryMedProtocolMainUnit(mapVo);
	}
	/**
	 * 协议明细
	 * @param entityMap
	 * @return
	 */
	public String queryMedProtocolDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedProtocolDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedProtocolDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 协议文档信息
	 * @param entityMap
	 * @return
	 */
	public String queryMedProtocolFile(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedProtocolFile(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medProtocolMainMapper.queryMedProtocolFile(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 查询协议主表的当前protocol_id
	 * @return
	 */
	public Long qureyCurrval() throws DataAccessException {
		 
		return medProtocolMainMapper.qureyCurrval();
	}
	
	/**
	 * 审核、消审、确认、取消确认、终止、取消终止
	 * @param listVo
	 * @return
	 */
	public String updateState(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
				
				int state = medProtocolMainMapper.updateState(listVo);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	
			}
			catch (Exception e) {
	
				logger.error(e.getMessage(), e);
	
				return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addOrUpdateMedProtocolMain\"}";
	
			}
	}
	/**
	 * 查询  指定采购协议的协议类型的开始年月 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryTypePre(Map<String, Object> mapVo) throws DataAccessException {
		return medProtocolMainMapper.queryMedProtocolTypePre(mapVo);
	}
	@Override
	public String queryMedProtocolMainPur(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list  = medProtocolMainMapper.queryMedProtocolMainPur(entityMap);
		
		if(list.size() > 0){
		
		for (Map<String, Object> map : list) {
			
			
			return "{\"msg\":\"\",\"state\":\"true\",\"price\":\""+map.get("price")+"\"}";
			
		   }
			
		}
		
		return "{\"error\":\"\",\"state\":\"false\"}";
	}
}
