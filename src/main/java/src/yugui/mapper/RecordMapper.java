package src.yugui.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import src.yugui.common.BaseMapper;
import src.yugui.entity.Record;

import java.util.List;
import java.util.Map;

/**
 * @Description: mapper接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:12
 */
@Mapper
@Repository(value = "recordMapper")
public interface RecordMapper extends BaseMapper<Record> {

    List<Record> getRecordList();

    List<Record> getRecordListOfNotify();

    Boolean addRecord(Map<String, Object> recordMap);

    Boolean deleteNotifyOrEvent(@Param("notifyIds") List<Long> notifyIds);

    Boolean delNotifyOutMoon(@Param("outTime") String outTime, @Param("nowTime") String nowTime);
}
