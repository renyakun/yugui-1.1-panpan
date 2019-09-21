package src.yugui.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import src.yugui.exception.BusinessException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ObjectMapper工具类
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Slf4j
@Component
public final class ObjectMapperUtils {

    @Autowired
    private ObjectMapper objectMapper;

    private static ObjectMapper mapper;

    private ObjectMapperUtils() {

    }

    @PostConstruct
    public void init() {
        this.mapper = objectMapper;
    }

    /**
     * 对象转字符串
     *
     * @param value
     * @return
     */
    public static String writeValueAsString(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("对象转字符串失败", e);
        }
        throw new BusinessException(ApiCode.ERROR, "对象转字符串失败");
    }


    /**
     * 对象转字符串
     *
     * @param fromValue
     * @param toValueType
     * @return
     */
    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        return new ObjectMapper().convertValue(fromValue, toValueType);
    }

    /**
     * 字符串转对象
     *
     * @param fromValue
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> List<T> readValueList(String fromValue, Class<T> toValueType) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, toValueType);
        try {
            return (List<T>) mapper.readValue(fromValue, javaType);
        } catch (IOException e) {
            log.error("字符串转对象", e);
        }
        throw new BusinessException(ApiCode.ERROR, "json转list失败");
    }

    /**
     * json转map
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static Map<String, Object> jsonToMap(String input) {
        try {
            return mapper.readValue(input, Map.class);
        } catch (IOException e) {
            log.error("json转map失败", e);
        }
        throw new BusinessException(ApiCode.ERROR, "json转map失败");
    }


    /**
     * bean转map
     *
     * @param t
     * @return
     * @throws IOException
     */
    public static <T> Map<String, Object> beanToMap(T t) {
        try {
            String json = ObjectMapperUtils.writeValueAsString(t);
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            log.error("bean转map失败", e);
        }
        throw new BusinessException(ApiCode.ERROR, "bean转map失败");
    }
}
