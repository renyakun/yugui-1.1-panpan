package src.yugui.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import src.yugui.common.BaseMapper;
import src.yugui.entity.Company;

import java.util.List;
import java.util.Map;

/**
 * @Description: mapper接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:12
 */
@Mapper
@Repository(value = "companyMapper")
public interface CompanyMapper extends BaseMapper<Company> {

    List<Company> getCompanyByCompanyUse(@Param("companyUse") String companyUse);


    Company getCompanyUseListByCompanyUse(@Param("companyUse") String companyUse);

    List<Company> getCompanyList(Map<String, Object> companyMap);

    Boolean addCompany(Map<String, Object> companyMap);

    Boolean deleteCompany(@Param("companyIds") List<Long> companyIds, @Param("updateTime") String updateTime);

    Boolean updateCompany(Map<String, Object> companyMap);

}
