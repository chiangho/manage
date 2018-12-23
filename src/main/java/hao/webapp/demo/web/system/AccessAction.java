package hao.webapp.demo.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.utils.ConverterUtils;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.system.Access;
import hao.webapp.demo.service.system.AccessService;

/***
 * 账号管理
 * @author chianghao
 *
 */
@Controller
@RequestMapping("access")
public class AccessAction {

	@Autowired
	AccessService accessService;
	
	/***
	 * 加载账号信息
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("load_access")
	public JSONView loadAccessInfo(
			@RequestParam(value="accessId") String accessId
			) throws HaoException {
		accessService.queryById(accessId);
		return RequestContext.getJSONView();
	}
	
	/***
	 * 保存
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("save_access")
	public JSONView save() throws HaoException {
		accessService.save();
		return RequestContext.getJSONView();	
	}
	
	@RequestMapping("del_access")
	public JSONView del(
			@RequestParam(value="accessId") String accessId
			) {
		accessService.del(accessId);
		return RequestContext.getJSONView();
	}
	
	/***
	 * 查询账号信息
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("query_access_list")
	public JSONView queryAccessList(
			@RequestParam(value=Page.PAGE_NO,required=false) String pageNo,
			@RequestParam(value=Page.PAGE_SIZE,required=false) String pageSize,
			@RequestParam(value="access",required=false) String access,
			@RequestParam(value="mobilePhone",required=false) String mobilePhone,
			@RequestParam(value="email",required=false) String email
			) throws HaoException {
		Page page = new Page(ConverterUtils.stringToInt(pageNo,1),ConverterUtils.stringToInt(pageSize, 15));
		List<Access> list = accessService.getPageList(page, access, mobilePhone, email);
		return RequestContext.getJSONView(page.getPageData(list));
	}
	
}
