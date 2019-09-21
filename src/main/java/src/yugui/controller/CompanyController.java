package src.yugui.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import src.yugui.common.ResponseMsg;
import src.yugui.common.TimeTool;
import src.yugui.entity.Company;
import src.yugui.entity.UserInfo;
import src.yugui.service.CompanyService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 企业管理控制层
 * @Author: XiaoPanPan
 * @Date: 2019/8/15 10:24
 */
@Api(tags = "企业管理控制层", description = "CompanyController")
@RestController
@RequestMapping("/report")
@Validated
public class CompanyController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserNotifyController.class);

    @Resource(name = "companyService")
    private CompanyService companyService;

    @ApiOperation(value = "输入单位名后自动显示公司地址，联系人，电话等信息接口", response = ResponseMsg.class)
    @RequestMapping(value = "/companyInfo", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseMsg getCompanyInfo(@RequestParam(defaultValue = "", value = "companyUse") String companyUse) {
        List<Company> company = companyService.getCompanyByCompanyUse(companyUse);
        logger.info("company: ---" + company);
        return ResponseMsg.ok(company);
    }

    @ApiOperation(value = "企业列表", response = ResponseMsg.class)
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getCompanyList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                      @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
                                      @RequestParam(defaultValue = "", value = "companyUse") String companyUse,
                                      @RequestParam(defaultValue = "", value = "companyContacts") String companyContacts) {
        Map<String, Object> companyMap = new HashMap<>();
        companyMap.put("companyUse", companyUse);
        companyMap.put("companyContacts", companyContacts);

        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<Company> companyList = companyService.getCompanyList(companyMap);
        //用PageInfo对结果进行包装
        PageInfo<Company> page = new PageInfo<Company>(companyList);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "添加企业", response = ResponseMsg.class)
    @RequestMapping(value = "/addCompany", method = {RequestMethod.POST})
    public ResponseMsg addCompany(@RequestBody(required = true) Map<String, Object> companyMap) {
        UserInfo userInfo = getLoginUser();
        String realName = userInfo.getRealName();
        if (!realName.equals("超级管理员")) {
            return ResponseMsg.error("您没有操作权限，请联系超级管理员！");
        }

        logger.info("companyMap--" + companyMap);
        String companyUse = (String) companyMap.get("companyUse");
        //判断企业是否已经存在
        Company companyList = companyService.getCompanyUseListByCompanyUse(companyUse);
        if (companyList != null) {
            return ResponseMsg.error("企业名已存在，请勿重复添加！");
        }
        //不存在则添加
        String time = TimeTool.getCurrentTime();//当前时间
        companyMap.put("createTime", time);
        companyMap.put("enable", 1);
        Boolean insertCompany = companyService.addCompany(companyMap);
        if (!insertCompany) {
            return ResponseMsg.error("添加企业失败！");
        }
        return ResponseMsg.ok("添加成功！");
    }

    @ApiOperation(value = "删除企业/可批量删除", response = ResponseMsg.class)
    @RequestMapping(value = "/deleteCompany", method = {RequestMethod.POST})
    public ResponseMsg deleteCompany(@RequestBody(required = true) Map<String, Object> companyMap) {
        UserInfo userInfo = getLoginUser();
        String realName = userInfo.getRealName();
        if (!realName.equals("超级管理员")) {
            return ResponseMsg.error("您没有操作权限，请联系超级管理员！");
        }

        Object ids = companyMap.get("ids");
        logger.info(ids);
        if (ids == null || ids == "") {
            return ResponseMsg.error("未选择企业");
        }
        List<Long> companyIds = (List<Long>) ids;
        String time = TimeTool.getCurrentTime();//当前时间
        Boolean delete = companyService.deleteCompany(companyIds, time);
        return ResponseMsg.ok("删除企业成功！" + delete);
    }

    @ApiOperation(value = "修改企业", response = ResponseMsg.class)
    @RequestMapping(value = "/updateCompany", method = {RequestMethod.POST})
    public ResponseMsg updateCompany(@RequestBody(required = true) Map<String, Object> companyMap) {
        UserInfo userInfo = getLoginUser();
        String realName = userInfo.getRealName();
        if (!realName.equals("超级管理员")) {
            return ResponseMsg.error("您没有操作权限，请联系超级管理员！");
        }

        if (companyMap.isEmpty() || StringUtils.isEmpty(companyMap.get("id").toString())) {
            return ResponseMsg.error("未提交数据");
        }
        String time = TimeTool.getCurrentTime();//当前时间
        companyMap.put("updateTime", time);
        Boolean update = companyService.updateCompany(companyMap);
        if (!update) {
            return ResponseMsg.error("修改企业失败！");
        }
        return ResponseMsg.ok("修改企业成功！");
    }
}
