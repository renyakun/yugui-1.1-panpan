package src.yugui.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件工具类
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Slf4j
public final class IOUtils extends org.apache.commons.io.IOUtils {

    /**
     * buffer size 大小(1MB)
     */
    public static final int BUFFER_SIZE = 1024 * 1024;
}
