package src.yugui.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * mapper基础类,实体统一继承该类
 *
 * @author XiaoPanPan
 * @date 2018/11/28
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
