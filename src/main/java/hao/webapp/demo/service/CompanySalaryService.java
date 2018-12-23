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
import hao.webapp.demo.model.CompanySalaryItemModel;


@Service
public class CompanySalaryService  extends BaseService{

	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws HaoException 
	 */
	public List<CompanySalaryItemModel> queryPageList(Page page) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(CompanySalaryItemModel.class);
		sqlSearch.join(Company.class, "companyId", "id");
		sqlSearch.order("id");
		return jdbcDao.queryList(page, sqlSearch);//this.queryPageList(page, sqlSearch);
	}

	/**
	 * 保存
	 * @throws HaoException 
	 */
	public void save() throws HaoException {
		Form form = new Form(CompanySalaryItemModel.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("companyId",ValidateType.REQUIRED);
		form.validate("name",ValidateType.REQUIRED);
		form.validate("script",ValidateType.REQUIRED);
		form.validate("remark",ValidateType.REQUIRED);
		form.validate("operation",ValidateType.REQUIRED);
		form.validate("changeSalaryDiffCalculation", ValidateType.REQUIRED);
		
		String base64Script = Base64Utils.encode(form.getParamValue("script"));
		form.setParamValue("script",base64Script);
		
		if(isInsert) {
			form.setPrimaryValue();
			CompanySalaryItemModel dataItem = form.getBean();
			dao.insert(dataItem);
		}else {
			SqlWhere sqlWhere = new SqlWhere();
			sqlWhere.addCondition(CompanySalaryItemModel.class, "id",form.getParamValue("id"));
			dao.update(CompanySalaryItemModel.class, form.getBeanMap(), sqlWhere);
		}
	}

	/**
	 * 删除
	 * @param id
	 */
	public void del(String id) {
		dao.deleteById(CompanySalaryItemModel.class, id);
	}

	/**
	 * 根据主键查询数据信息
	 * @param id
	 * @return
	 * @throws HaoException 
	 */
	public CompanySalaryItemModel queryById(String id) throws HaoException {
		CompanySalaryItemModel data = this.queryByField(CompanySalaryItemModel.class, "id",id);
		data.setScript(Base64Utils.decode(data.getScript()));
		return data;
	}

	/**
	 * 查询公司下的全部工资项目
	 * @param companyId
	 * @return
	 * @throws HaoException
	 */
	public List<CompanySalaryItemModel> queryByCompanyId(String companyId) throws HaoException {
		return this.jdbcDao.queryList(CompanySalaryItemModel.class,"companyId",companyId);
	}
	
	
}
