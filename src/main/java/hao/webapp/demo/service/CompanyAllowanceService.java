package hao.webapp.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWhere;
import hao.framework.service.BaseService;
import hao.framework.utils.Base64Utils;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.CompanyAllowanceModel;


@Service
public class CompanyAllowanceService extends BaseService{
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws HaoException 
	 */
	public List<CompanyAllowanceModel> queryPageList(Page page) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(CompanyAllowanceModel.class);
		sqlSearch.join(Company.class,"companyId", "id");
		sqlSearch.order("id");
		return jdbcDao.queryList(page, sqlSearch);//this.queryPageList(page, sqlSearch);
	}

	/**
	 * 保存
	 * @throws HaoException 
	 */
	public void save() throws HaoException {
		Form form = new Form(CompanyAllowanceModel.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("companyId",ValidateType.REQUIRED);
		form.validate("name",ValidateType.REQUIRED);
		form.validate("summayScript",ValidateType.REQUIRED);
		form.validate("remark",ValidateType.REQUIRED);
		
		String base64Script = Base64Utils.encode(form.getParamValue("summayScript"));
		form.setParamValue("summayScript",base64Script);
		
		if(isInsert) {
			form.setPrimaryValue();
			CompanyAllowanceModel dataItem = form.getBean();
			dao.insert(dataItem);
		}else {
			SqlWhere sqlWhere = new SqlWhere();
			sqlWhere.addCondition(CompanyAllowanceModel.class, "id",form.getParamValue("id"));
			dao.update(CompanyAllowanceModel.class, form.getBeanMap(), sqlWhere);
		}
		
	}

	/**
	 * 删除
	 * @param id
	 */
	public void del(String id) {
		dao.deleteById(CompanyAllowanceModel.class, id);
	}

	/**
	 * 根据主键查询数据信息
	 * @param id
	 * @return
	 * @throws HaoException 
	 */
	public CompanyAllowanceModel queryById(String id) throws HaoException {
		CompanyAllowanceModel data = this.queryByField(CompanyAllowanceModel.class, "id",id);
		data.setSummayScript(Base64Utils.decode(data.getSummayScript()));
		return data;
	}


}
