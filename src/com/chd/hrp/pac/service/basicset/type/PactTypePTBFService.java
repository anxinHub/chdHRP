package com.chd.hrp.pac.service.basicset.type;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public interface PactTypePTBFService {
	
	/**
	 *查询方法
	 * @author lh0225
	 * @return
	 * @throws DataAccessException
	 */
   public String queryPactPtbf(Map<String ,Object>mapVo) throws DataAccessException;
   
   
   /**
    * 查询方法根据ID
    * @author lh0225
    * @return
    */
   
   public String queryPactPtbfId(Map<String ,Object>mapVo) throws DataAccessException;
   
   /**
    * @author lh0225
    * @return
    * @throws DataAccessExcepiton
    * 保存方法
    */
   
   public String savePacPtbfAction   (Map<String ,Object>mapVo) throws DataAccessException;
   
   /**
    * @author lh0225
    * 更新方法
    * 
    * @return
    * @throws DataAccessException
    */
   
   
   public String updatePacPtbfAction (Map<String ,Object>mapVo) throws DataAccessException;
   
   
   /**
    * 
    * @author lh0225
    * 删除方法
    * @return
    * @throws DataAccessExcepiton
    */
   
   
   public String deletePacPtbfAction(Map<String ,Object> mapVo) throws DataAccessException;
}
