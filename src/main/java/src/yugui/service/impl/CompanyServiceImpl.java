package src.yugui.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.yugui.mapper.CompanyMapper;
import src.yugui.service.CompanyService;
import src.yugui.entity.Company;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 实现类
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:20
 */
@Service(value = "companyService")
public class CompanyServiceImpl implements CompanyService {

    @Resource(name = "companyMapper")
    private CompanyMapper companyMapper;

    @Override
    public List<Company> getCompanyByCompanyUse(String companyUse) {
        return companyMapper.getCompanyByCompanyUse(companyUse);
    }

    @Override
    public Company getCompanyUseListByCompanyUse(String companyUse) {
        return companyMapper.getCompanyUseListByCompanyUse(companyUse);
    }

    @Override
    public List<Company> getCompanyList(Map<String, Object> companyMap) {
        return companyMapper.getCompanyList(companyMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addCompany(Map<String, Object> companyMap) {
        return companyMapper.addCompany(companyMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteCompany(List<Long> companyIds, String updateTime) {
        return companyMapper.deleteCompany(companyIds, updateTime);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateCompany(Map<String, Object> companyMap) {
        return companyMapper.updateCompany(companyMap);
    }

}


