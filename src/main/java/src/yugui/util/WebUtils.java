package src.yugui.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;
import src.yugui.common.ResponseMsg;
import src.yugui.exception.BusinessException;
import src.yugui.filter.BodyReaderRequestWrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * WebUtils工具类
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Slf4j
public final class WebUtils {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private WebUtils() {

    }

    /**
     * 获取body参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getBody(HttpServletRequest request) {
        if (request instanceof BodyReaderRequestWrapper) {
            String contentType = request.getContentType();
            BodyReaderRequestWrapper requestWrapper = (BodyReaderRequestWrapper) request;
            try {
                String bodyStr = IOUtils.toString(requestWrapper.getBody(), requestWrapper.getCharacterEncoding());
                if (!StringUtils.hasText(bodyStr)) {
                    return Collections.emptyMap();
                }
                return ObjectMapperUtils.jsonToMap(bodyStr);
            } catch (Exception e) {
                log.error("获取body失败", e);
                throw new BusinessException(ApiCode.NOT_READABLE, "JSON解析失败");
            }
        }
        log.warn("request={}", request);
        return Collections.emptyMap();
    }

    /**
     * 获取url
     *
     * @param url
     * @param urlParams
     * @return
     */
    public static URI getUri(String url, Map<String, Object> urlParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        urlParams.forEach((k, v) -> {
            builder.queryParam(k, v);
        });
        return builder.build().toUri();
    }

    /**
     * 响应json
     *
     * @param response
     * @param servletResponse
     */
    public static void writerJson(ResponseMsg response, HttpServletResponse servletResponse) {
        try (ServletOutputStream output = servletResponse.getOutputStream()) {
            servletResponse.setCharacterEncoding(DEFAULT_CHARSET);
            servletResponse.setStatus(HttpStatus.OK.value());
            servletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            output.write(ObjectMapperUtils.writeValueAsString(response).getBytes(DEFAULT_CHARSET));
            output.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void writer(HttpStatusCodeException e, HttpServletResponse response) {
        try (ServletOutputStream output = response.getOutputStream()) {
            writerHttpHeaders(e.getResponseHeaders(), response);
            response.setCharacterEncoding(DEFAULT_CHARSET);
            response.setStatus(e.getRawStatusCode());
            String errorMessage = e.getResponseBodyAsString();
            if (!StringUtils.hasLength(errorMessage)) {
                errorMessage = e.getStatusText();
            }
            if (StringUtils.hasLength(errorMessage)) {
                output.write(errorMessage.getBytes(DEFAULT_CHARSET));
                output.flush();
            }

        } catch (IOException ie) {
            log.error(ie.getMessage(), ie);
        }
    }

    private static void writerHttpHeaders(HttpHeaders httpHeaders, HttpServletResponse response) {
        MediaType contentType = httpHeaders.getContentType();
        if (contentType != null) {
            response.setContentType(contentType.toString());
        }
        if (!httpHeaders.isEmpty()) {
            httpHeaders.forEach((key, values) -> {
                values.forEach(value -> {
                    response.addHeader(key, value);
                });
            });
        }
    }
}


