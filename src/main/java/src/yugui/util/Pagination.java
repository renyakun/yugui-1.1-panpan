package src.yugui.util;

import lombok.Data;

/**
 * @Description: 分页信息
 * @Author: XiaoPanPan
 * @Date: 2019/8/14 11:02
 */
@Data
public class Pagination {
    // 当前页
    private Integer currentPage = 1;
    // 每页显示的总条数
    private Integer pageSize = 10;
    // 总条数
    private Integer total;
    // 总页数
    private Integer totalPage;

    public Pagination(Integer currentPage, Integer pageSize, Integer total) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPage = (this.currentPage+this.pageSize-1)/this.pageSize;
    }

}
