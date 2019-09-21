package src.yugui.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.log4j.Logger;
import src.yugui.controller.ValveHistoryController;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 忽略枚举中不存在类型的异常
 *
 * @author XiaoPanPan
 * @date 2019/8/6 10:27
 */
public class IgnoreNotExistEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
    private static final Logger logger = Logger.getLogger(ValveHistoryController.class);

    private final Class<E> type;

    public IgnoreNotExistEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.name());
        } else {
            ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE); // see r3589
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return getEnumResult(s);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return getEnumResult(s);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return getEnumResult(s);
    }

    private E getEnumResult(String s) {
        try {
            return s == null ? null : Enum.valueOf(type, s);
        } catch (IllegalArgumentException e) {
            logger.error("{}存在未定义类型:{}");
        }
        return null;
    }
}