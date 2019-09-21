package src.yugui.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.yugui.entity.Record;
import src.yugui.mapper.RecordMapper;
import src.yugui.service.RecordService;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: XiaoPanPan
 * @Date: 2019/9/17 12:48
 */
@Service(value = "recordService")
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Record> getRecordList() {
        return recordMapper.getRecordList();
    }

    @Override
    public List<Record> getRecordListOfNotify() {
        return recordMapper.getRecordListOfNotify();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addRecord(Map<String, Object> recordMap) {
        return recordMapper.addRecord(recordMap);
    }

    @Override
    public Boolean deleteNotifyOrEvent(List<Long> notifyIds) {
        return recordMapper.deleteNotifyOrEvent(notifyIds);
    }
}
