package com.chd.hrp.acc.serviceImpl;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccYearMapper;
import com.chd.hrp.acc.entity.AccYear;
import com.chd.hrp.acc.service.AccYearService;
import com.chd.hrp.sys.dao.InitProcMapper;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service("accYearService")
public class AccYearServiceImpl
  implements AccYearService
{
  private static Logger logger = Logger.getLogger(AccYearServiceImpl.class);

  @Resource(name="accYearMapper")
  private final AccYearMapper accYearMapper = null;

  @Resource(name="initProcMapper")
  private final InitProcMapper initProcMapper = null;

  public String addAccYear(Map<String, Object> entityMap)
    throws DataAccessException
  {
    AccYear accYear = queryAccYearByCode(entityMap);

    if (accYear != null)
    {
      return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
    }

    try
    {
      this.accYearMapper.addAccYear(entityMap);

      this.initProcMapper.saveInitYearProc(entityMap);
      return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccYear\"}";
  }

  public String addBatchAccYear(List<Map<String, Object>> entityList)
    throws DataAccessException
  {
    try
    {
      this.accYearMapper.addBatchAccYear(entityList);

      return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccYear\"}";
  }

  public String queryAccYear(Map<String, Object> entityMap)
    throws DataAccessException
  {
    SysPage sysPage = new SysPage();

    sysPage = (SysPage)entityMap.get("sysPage");

    RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
    return ChdJson.toJson(this.accYearMapper.queryAccYear(entityMap, rowBounds), sysPage.getTotal());
  }

  public AccYear queryAccYearByCode(Map<String, Object> entityMap)
    throws DataAccessException
  {
    return this.accYearMapper.queryAccYearByCode(entityMap);
  }

  public String deleteBatchAccYear(Map<String, Object> entityList)
    throws DataAccessException
  {
    try
    {
      int state = this.accYearMapper.deleteBatchAccYear(entityList);
      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccYear\"}";
  }

  public String deleteAccYear(Map<String, Object> entityMap)
    throws DataAccessException
  {
    try
    {
      this.accYearMapper.deleteAccYear(entityMap);
      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccYear\"}";
  }

  public String updateAccYear(Map<String, Object> entityMap)
    throws DataAccessException
  {
    try
    {
      this.accYearMapper.updateAccYear(entityMap);

      return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccYear\"}";
  }

  public String updateBatchAccYear(List<Map<String, Object>> entityList)
    throws DataAccessException
  {
    try
    {
      this.accYearMapper.updateBatchAccYear(entityList);

      return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccYear\"}";
  }

  public String importAccYear(Map<String, Object> entityMap)
    throws DataAccessException
  {
    return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
  }
}