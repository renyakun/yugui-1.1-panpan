package src.yugui.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取body参数
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Component
@WebFilter(urlPatterns = "/*")
public class BodyReaderHttpServletRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        BodyReaderRequestWrapper requestWrapper = new BodyReaderRequestWrapper(request);
        chain.doFilter(requestWrapper, response);
    }
}
