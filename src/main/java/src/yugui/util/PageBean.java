package src.yugui.util;

import lombok.Data;

import java.util.List;

/**
 * @Description:  分页Bean
 * @Author:       XiaoPanPan
 * @Date:         2019/8/3 16:27
 */
@Data
public class PageBean<T> {

    private Pagination pagination;

    // 分页结果
    private List<T> list;

    public PageBean() {
        super();
    }

}
