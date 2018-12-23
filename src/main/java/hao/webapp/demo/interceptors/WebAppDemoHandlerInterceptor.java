package hao.webapp.demo.interceptors;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import hao.framework.web.HaoSession;
import hao.webapp.demo.AppConstant;

/***
 * web app spring 连接器
 * @author chianghao
 *
 */
public class WebAppDemoHandlerInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView) throws UnsupportedEncodingException {
		if(HaoSession.getAccessId()!=null&&!HaoSession.getAccessId().equals("")) {
			String accessId = HaoSession.getAccessId();
			String key = AppConstant.SESSION_KEY_MENU_PREFIX+accessId;
			Object menulist = HaoSession.find(key);
			request.setAttribute("menuList", menulist);
			request.setAttribute("sessionUser", HaoSession.getAccess());
		}

	}
}
