package com.chd.hrp.acc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.hrp.acc.entity.AccYear;
import com.chd.hrp.acc.serviceImpl.AccYearServiceImpl;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccYearController extends BaseController
{
  private static Logger logger = Logger.getLogger(AccYearController.class);

  @Resource(name="accYearService")
  private final AccYearServiceImpl accYearService = null;

  @RequestMapping(value={"/hrp/acc/accyear/accYearMainPage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String accYearMainPage(Model mode)
    throws Exception
  {
    return "hrp/acc/accyear/accYearMain";
  }

  @RequestMapping(value={"/hrp/acc/accyear/accYearAddPage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String accYearAddPage(Model mode)
    throws Exception
  {
    return "hrp/acc/accyear/accYearAdd";
  }

  @RequestMapping(value={"/hrp/acc/accyear/addAccYear"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> addAccYear(@RequestParam Map<String, Object> mapVo, Model mode)
    throws Exception
  {
    String accYearJson = this.accYearService.addAccYear(mapVo);

    return JSONObject.parseObject(accYearJson);
  }

  @RequestMapping(value={"/hrp/acc/accyear/queryAccYear"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> queryAccYear(@RequestParam Map<String, Object> mapVo, Model mode)
    throws Exception
  {
    String accYear = this.accYearService.queryAccYear(getPage(mapVo));

    return JSONObject.parseObject(accYear);
  }

  @RequestMapping(value={"/hrp/acc/accyear/accYearUpdatePage"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String accYearUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)
    throws Exception
  {
    AccYear accYear = new AccYear();
    accYear = this.accYearService.queryAccYearByCode(mapVo);
    mode.addAttribute("group_id", accYear.getGroup_id());
    mode.addAttribute("hos_id", accYear.getHos_id());
    mode.addAttribute("copy_code", accYear.getCopy_code());
    mode.addAttribute("acc_year", accYear.getAcc_year());
    mode.addAttribute("begin_date", accYear.getBegin_date());
    mode.addAttribute("end_date", accYear.getEnd_date());
    return "hrp/acc/accyear/accYearUpdate";
  }

  @RequestMapping(value={"/hrp/acc/accyear/updateAccYear"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> updateAccYear(@RequestParam Map<String, Object> mapVo, Model mode)
    throws Exception
  {
    String accYearJson = this.accYearService.updateAccYear(mapVo);

    return JSONObject.parseObject(accYearJson);
  }

  @RequestMapping(value={"/hrp/acc/accyear/importAccYear"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> importAccYear(@RequestParam Map<String, Object> mapVo, Model mode)
    throws Exception
  {
    String accYearJson = this.accYearService.importAccYear(mapVo);

    return JSONObject.parseObject(accYearJson);
  }
}