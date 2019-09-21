import com.alibaba.druid.filter.config.ConfigTools;

import java.time.OffsetDateTime;

/**
 * 数据库密码加密
 *
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 10:27
 */
public class TestDruidEncryptPassword {

    public static void main(String[] args) throws Exception {
        String password = ConfigTools.encrypt("123456");
        System.out.println("password = " + password);

        System.out.println(OffsetDateTime.now());

    }

}