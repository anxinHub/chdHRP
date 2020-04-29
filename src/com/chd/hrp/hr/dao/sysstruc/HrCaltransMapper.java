/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrCaltrans;
import com.chd.hrp.hr.entity.sysstruc.HrFun;
/**
 * 
 * @Description:
 * 计算事务表
 * @Table:
 * HR_CALTRANS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrCaltransMapper extends SqlMapper{

	int deleteBatchHrCaltrans(List<HrCaltrans> listVo);

	int startFuncHrCaltrans(Map map);

	int stopFuncHrCaltrans(Map map);

	List<Map<String, Object>> queryCaltransFunTypeTree(Map<String, Object> entityMap);
    /**
     * 动态表单
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryPrmFunByCode(Map<String, Object> entityMap);
   /**
    * 查询部件数据
    * @param mapVo
    * @return
    */
	Object queryHrFunParaByDict(Map<String, Object> mapVo);

   List<Map<String, Object>> queryParaMethod(Map<String, Object> entityMap);
   /**
    * 查询数据
    * @param entityMap
    * @return
    */
  List<Map<String, Object>> queryHrData(Map<String, Object> entityMap);
  /**
   * 查询数据表编码和数据列是否存在
   * @param entityMap
   * @return
   */
  HrCaltrans queryByTabCode(Map<String, Object> entityMap);

}
