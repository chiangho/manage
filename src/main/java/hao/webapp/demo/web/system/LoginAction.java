package hao.webapp.demo.web.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.auth.shiro.AuthUtils;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.service.system.AccessService;

@Controller
public class LoginAction {

	@Autowired
	AccessService accessService;
	
	/***
	 * 登录
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("login")
	public JSONView login(
			@RequestParam(required=true,name="access") String access,
			@RequestParam(required=true,name="password") String password
			) throws HaoException {
		accessService.login(access,password);
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("logout")
	public JSONView logout() {
		AuthUtils.logout();
		return RequestContext.getJSONView();
	}
	
}
