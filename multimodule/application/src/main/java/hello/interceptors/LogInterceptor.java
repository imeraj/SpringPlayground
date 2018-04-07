package hello.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
		
		logger.debug("\\n-------- LogInterception.preHandle --- ");
		logger.debug("Request URL: " + request.getRequestURL());
		logger.debug("Start Time: " + System.currentTimeMillis());
		
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime",  startTime);
		
		return true;
	}
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, //
            Object handler, ModelAndView modelAndView) throws Exception {
		
		logger.debug("\n-------- LogInterception.postHandle --- ");
		logger.debug("Request URL: " + request.getRequestURL());
	}
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, //
            Object handler, Exception ex) throws Exception {
		
		long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        
		logger.debug("\n-------- LogInterception.afterCompletion --- ");
		logger.debug("Request URL: " + request.getRequestURL());
		logger.debug("End Time: " + endTime);
		
		logger.debug("Time Taken: " + (endTime - startTime) + " msec");
	}
}
