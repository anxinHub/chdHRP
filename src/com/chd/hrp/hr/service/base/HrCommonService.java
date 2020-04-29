package com.chd.hrp.hr.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.Parameter;

/**
 * 
 * @ClassName: HrCommonService
 * @Description: hr动态表格、表单增删改查
 * @author zn
 * @date 2017年11月5日 上午10:53:57
 * 
 *
 */
public interface HrCommonService {

	/**
	 * 查询表格信息 包含表格列信息、数据
	 * 
	 * @param entityMap 包含：group_id,hos_id,copy_code,tab_code,is_list or is_insert,
	 * @param isEdit
	 *            是否为可编辑表格
	 * @param hasData
	 *            是否包含数据
	 * @return {columns:[{display:'',name:'',width:'',editor:''},{},...],data:{Rows:[],Total:}}
	 * @throws DataAccessException
	 */
	//String queryListGrid(Map<String, Object> entityMap, boolean hasData) throws DataAccessException;
	
	/**
	 * 查询表格信息 包含表格列信息、数据
	 * 
	 * @param entityMap 包含：group_id,hos_id,copy_code,tab_code,is_list or is_insert,
	 * @param isEdit
	 *            是否为可编辑表格
	 * @param hasData
	 *            是否包含数据
	 * @return {columns:[{display:'',name:'',width:'',editor:''},{},...],data:{Rows:[],Total:}}
	 * @throws DataAccessException
	 */
	//String queryEditGrid(Map<String, Object> entityMap, boolean hasData) throws DataAccessException;
	
	
	/**
	 * 查询表格头列表
	 * @param entityMap
	 * @return [{display:'',name:'',width:'',editor:''},{},...]
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryListGridHeader(Map<String, Object> entityMap) throws DataAccessException;
	
	List<Map<String, Object>> queryEditGridHeader(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询表格数据列表
	 * @param entityMap
	 * @return {Rows:[],Total:}
	 * @throws DataAccessException
	 */
	Map<String, Object> queryGridData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 自定义条件查询表格数据列表
	 * @param entityMap
	 * @return {Rows:[],Total:}
	 * @throws DataAccessException
	 */
	Map<String, Object> queryGridDataByCustom(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询Form信息
	 * 
	 * @param entityMap
	 * @param colNum 表单页面每行显示列数
	 * @param isUpdate
	 *            是否更新页面，如果isUpdate为true 则返回更新数据
	 * @return {colNum:'每行几列',photo:'头像',fieldItems:{id:'',name:'',text:'',type:'',width:'',place:'占列数',required:'',OPTIONS:{option:[{id:'',text:''},...]}}}
	 * 
	 * @throws DataAccessException
	 */
	//String queryForm(Map<String, Object> entityMap,int colNum) throws DataAccessException;
	String queryEditForm(Map<String, Object> entityMap,int colNum) throws DataAccessException;
	String queryQueForm(Map<String, Object> entityMap,int colNum) throws DataAccessException;

	/**
	 * 查询数据列表
	 * @param entityMap 参数列表
	 * @param tab_code 表名
	 * @return 
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> findList(String tab_code, Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 保存表格数据
	 * 
	 * @param tab_code 表名
	 * @param addList 添加列表
	 * @param modList 更新列表
	 * @return
	 * @throws DataAccessException
	 */
	String saveGrid(String tab_code, List<Parameter> addList, List<Parameter> modList) throws DataAccessException;

	/**
	 * 保存Form数据
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String saveFrom(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量删除数据
	 * 
	 * @param tab_code
	 * @param paramList
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatchItem(String tab_code, List<Parameter> paramList) throws DataAccessException;
	
	/**
	 * 根据自定义SQL查询数据
	 * 
	 * @param tab_code 表名
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryListByCustomSql(String tab_code, Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据部门ID查询该部门所有子部门
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String[] queryChildDeptById(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 档案库表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrStoreTabStrucByStoreType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 个人信息一览统计表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> totalDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 简单统计查询
	 * @param entityMap
	 * @return
	 */
	String queryHrStatisticCustom(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据用户ID获取权限
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryHrUserPermByUserId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据角色ID获取权限
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryHrRolePermByRoleId(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 简单统计页面 查询条件FORM
	 * @param mapVo
	 * @param i
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrStatisticCustomQueForm(Map<String, Object> mapVo, int i) throws DataAccessException;

	List<Map<String, Object>> queryGridDataPrint(Map<String, Object> entityMap);

	String importFromDate(Map<String, Object> mapVo);
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrChildDataByPrint(Map<String, Object> entityMap) throws DataAccessException;
	String queryExcelColumn(Map<String, Object> mapVo);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryHrStatisticCustomPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询主集、表格表头
	 * @param entityMap
	 * @return [{display:'',name:'',width:'',editor:''},{},...]
	 * @throws DataAccessException
	 */
	String queryEmpHead(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询主集、子集 表格表头
	 * @param entityMap
	 * @return [{display:'',name:'',width:'',editor:''},{},...]
	 * @throws DataAccessException
	 */
	String queryEmpChildHead(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询主集数据 主集分页查询
	 * @param entityMap
	 * @return [{display:'',name:'',width:'',editor:''},{},...]
	 * @throws DataAccessException
	 */
	String queryEmpGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询子集数据 子集不分页
	 * @param entityMap
	 * @return [{display:'',name:'',width:'',editor:''},{},...]
	 * @throws DataAccessException
	 */
	String queryEmpChildGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询简单统计表头
	 * @param entityMap
	 * @return [{display:'',name:'',width:'',editor:''},{},...]
	 * @throws DataAccessException
	 */
	String queryHrStatisticCustomHead(Map<String, Object> entityMap) throws DataAccessException;
    /**
                * 外部引用下拉框
     * @param page
     * @return
     * @throws DataAccessException
     */
	String queryHrHosSelectCite(Map<String, Object> page) throws DataAccessException;
     /**
                   * 内置数据下拉框
      * @param page
      * @return
      * @throws DataAccessException
      */
	String queryHrHosSelect(Map<String, Object> page)throws DataAccessException;

	String batchEmpUpate(Map<String, Object> mapVo) throws DataAccessException;
    /**
            * 查询左侧表单
     * @param mapVo
     * @param colNum
     * @return
     * @throws DataAccessException
     */
	String queryQueFormLeft(Map<String, Object> mapVo, int colNum)throws DataAccessException;
    /**
     * 查询页面表单
     * @param mapVo
     * @param colNum
     * @return
     * @throws DataAccessException
     */
	String queryFormSearchPage(Map<String, Object> mapVo, int colNum)throws DataAccessException;


	

}
