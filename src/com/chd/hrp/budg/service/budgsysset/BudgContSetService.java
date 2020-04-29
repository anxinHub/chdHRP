/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.budg.service.budgsysset;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccPara;

/**
* @Title. @Description.
* 系统参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgContSetService {
	/**
	 * 预算节点设置    保存
	 */
	public String saveBudgContSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询
	 * 预算节点设置
	 */
	public String queryBudgContSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 *预算节点设置   删除
	 */
	public String deleteBudgContSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *预算控制节点 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgContNote(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *根据控制节点 查询反向节点code  name
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryReNodeByCode(Map<String, Object> mapVo) throws DataAccessException;
	
}
