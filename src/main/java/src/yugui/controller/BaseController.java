package src.yugui.controller;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import src.yugui.common.YgConstants;
import src.yugui.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BaseController {
    //pagination
    private static final String PAGE_RAW_DATA = "pagination";
    private static final String PAGE_RECORDS_TOTAL = "total";
    private static final String PAGE_NUM = "pageNum";
    private static final String PAGE_SIZE = "pageSize";

    private static final String UPLOAD = "/upload/";
    private static final Logger baseLogger = Logger.getLogger(BaseController.class);
    protected static final String SYS_E_MSG = "系统故障，请稍后重试";
    protected String fileBasePath;

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected UserInfo getLoginUser() {
        UserInfo loginUser = (UserInfo) getRequest().getSession().getAttribute(YgConstants.LOGIN_USER);
        return loginUser;
    }

    /**
     * 获取新编号
     */
    protected String getNewReportNumber(String olderReportNumber) {
        if (StringUtils.isEmpty(olderReportNumber) || !olderReportNumber.contains("-")) {
            olderReportNumber = "FD/L-2019-000000";//旧编号为空的情况下初始化编号
        }
        String[] newReportNumbers = olderReportNumber.split("-");
        StringBuilder newReportNum = new StringBuilder();
        newReportNum.append(newReportNumbers[0]);
        newReportNum.append("-");
        //获取当前年份
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        newReportNum.append(year);
        newReportNum.append("-");
        String lastStr = newReportNumbers[newReportNumbers.length - 1];
        int no = Integer.parseInt(lastStr);
        no++;
        String noStr = String.valueOf(no);
        String yeStr = "";
        if (noStr.length() == 1) {
            yeStr = "00000" + noStr;

        }
        if (noStr.length() == 2) {
            yeStr = "0000" + noStr;

        }
        if (noStr.length() == 3) {
            yeStr = "000" + noStr;

        }
        if (noStr.length() == 4) {
            yeStr = "00" + noStr;

        }
        if (noStr.length() == 5) {
            yeStr = "0" + noStr;

        }
        if (noStr.length() > 5) {
            yeStr = noStr;

        }
        newReportNum.append(yeStr);
        return newReportNum.toString();
    }


    protected Map<String, Object> makePageData(Collection data, long total, int pageNum, int pageSize) {
        Map<String, Object> pageResult = new HashMap<>(4);
        pageResult.put(PAGE_RAW_DATA, data);
        pageResult.put(PAGE_RECORDS_TOTAL, total);
        pageResult.put(PAGE_NUM, pageNum);
        pageResult.put(PAGE_SIZE, pageSize);
        return pageResult;
    }


    protected HttpServletResponse getResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected String getParam(String name) {
        return getRequest().getParameter(name);
    }


    /**
     * 新建报告 检验参数
     */
    protected Map<String, Object> checkParm(Map<String, Object> valveMap) {
        //检验参数factoryTime出厂日期
        Object factoryDate = valveMap.get("factoryTime");
        if (factoryDate == null || factoryDate == "") {
            valveMap.remove("factoryTime");
        } else {
            String oldDate = (String) factoryDate;
            Date date1 = null;
            DateFormat df2 = null;
            try {
                oldDate = oldDate.replace("Z", " UTC");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                Date date = df.parse(oldDate);
                SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                date1 = df1.parse(date.toString());
                df2 = new SimpleDateFormat("yyyy年MM月dd日");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String factoryTime = df2.format(date1);
            valveMap.put("factoryTime", factoryTime);
        }

        //检验参数checkEffectiveTime有效日期
        Object checkEffectiveTimeOlder = valveMap.get("checkEffectiveTime");
        if (checkEffectiveTimeOlder == null || checkEffectiveTimeOlder == "") {
            valveMap.remove("checkEffectiveTimeOlder");
        } else {
            String oldDate = (String) checkEffectiveTimeOlder;
            DateFormat df2 = null;
            Date date1 = null;
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                oldDate = oldDate.replace("Z", " UTC");
                SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
                Date date = df.parse(oldDate);
                date1 = df1.parse(date.toString());
                df2 = new SimpleDateFormat("yyyy年MM月dd日");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String checkEffectiveTime = df2.format(date1);
            valveMap.put("checkEffectiveTime", checkEffectiveTime);
        }

        //校验参数checkExplain
        Object checkExplains = valveMap.get("checkExplain");
        List<String> aa;
        if (checkExplains == null || checkExplains == "" || checkExplains.toString().equals("[]")) {
            valveMap.put("checkExplain", "未修理");
        } else {
            aa = (List<String>) valveMap.get("checkExplain");
            StringBuilder newCheckExplain = new StringBuilder();
            for (String a : aa) {
                newCheckExplain.append(a).append(" ");
            }
            valveMap.put("checkExplain", newCheckExplain.toString());
        }

        //参数settingPressure 和 sealTestPressure 即取值为第三次试验结果
        valveMap.put("settingPressure", valveMap.get("thirdSettingPressure"));
        valveMap.put("sealTestPressure", valveMap.get("thirdSealTestPressure"));
        return valveMap;
    }

    /**
     * 修改报告 检验参数
     */
    protected Map<String, Object> updateParm(Map<String, Object> valveMap) {
        //校验参数checkExplain
        Object checkExplains = valveMap.get("checkExplain");
        if (checkExplains == null || checkExplains == "") {
            valveMap.remove("checkExplain");
        } else {
            List<String> aa = (List<String>) valveMap.get("checkExplain");
            StringBuffer newCheckExplain = new StringBuffer();
            for (String a : aa) {
                newCheckExplain.append(a).append(" ");
            }
            valveMap.put("checkExplain", newCheckExplain.toString());
        }
        return valveMap;
    }

}
