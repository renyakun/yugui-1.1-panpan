package src.yugui.service;

import src.yugui.entity.Company;

import java.util.List;
import java.util.Map;


/**
 * @Description: CompanyService接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:19
 */

public interface CompanyService {
    /**
     * 根据CompanyUse获取Company
     */
    List<Company> getCompanyByCompanyUse(String companyUse);

    /**
     * 根据CompanyUse查询CompanyUse
     */
    Company getCompanyUseListByCompanyUse(String companyUse);

    /**
     * 获取Company所有列表
     */
    List<Company> getCompanyList(Map<String, Object> companyMap);

    /**
     * 增加企业
     */
    Boolean addCompany(Map<String, Object> companyMap);

    /**
     * 删除企业
     */
    Boolean deleteCompany(List<Long> companyIds, String updateTime);

    /**
     * 修改企业
     */
    Boolean updateCompany(Map<String, Object> companyMap);

}
