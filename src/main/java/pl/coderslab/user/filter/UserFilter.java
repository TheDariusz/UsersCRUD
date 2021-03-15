package pl.coderslab.user.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderslab.utils.DateUtil;
import pl.coderslab.utils.DbUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "UserFilter", urlPatterns = "/user/*")
public class UserFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserFilter.class);
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String browser = httpRequest.getHeader("User-Agent");
        String currentTime = DateUtil.getCurrentTime();

        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long stopTime = System.currentTimeMillis();

        long duration = stopTime-startTime;
        DbUtil.writeLogToDatabase(browser, currentTime, "info", "request processing duration time [ms] :" + duration);
    }

}
