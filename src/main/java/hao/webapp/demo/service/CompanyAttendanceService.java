package hao.webapp.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.sql.SqlCommon.SqlSort;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWhere;
import hao.framework.service.BaseService;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.CompanyAttendanceItemModel;


@Service
public class CompanyAttendanceService extends BaseService{

	
	/**
	 * 查询公司考勤导入项目
	 * @param companyId
	 * @return
	 * @throws HaoException
	 */
	public List<CompanyAttendanceItemModel> queryItemByCompanyId(String companyId) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(CompanyAttendanceItemModel.class);
		sqlSearch.addCondition("companyId", companyId);
		sqlSearch.order("id", SqlSort.desc);
		List<CompanyAttendanceItemModel> items = this.queryList(sqlSearch);
		return items;
	}

	/**
	 * 保存导入参数项
	 * @return
	 * @throws HaoException
	 */
	public CompanyAttendanceItemModel saveAttendanceItem() throws HaoException {
		Form form = new Form(CompanyAttendanceItemModel.class,RequestContext.getHttpRequest());
		form.validate("name", ValidateType.REQUIRED);
		form.validate("companyId", ValidateType.REQUIRED);
		boolean isInsert = form.isInsertAction();
		if(isInsert) {
			form.setPrimaryValue();
			CompanyAttendanceItemModel item = form.getBean();
			dao.insert(item);
			return item;
		}else {
			SqlWhere sqlWhere = new SqlWhere();
			sqlWhere.addCondition(CompanyAttendanceItemModel.class, "id", form.getParamValue("id"));
			dao.update(CompanyAttendanceItemModel.class, form.getBeanMap(), sqlWhere);
			return form.getBean();
		}
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void del(String id) {
		dao.deleteById(CompanyAttendanceItemModel.class, id);
	}

	public List<CompanyAttendanceItemModel> queryList() throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(CompanyAttendanceItemModel.class);
		sqlSearch.join(Company.class,"companyId", "id");
		sqlSearch.order("id", SqlSort.desc);
		List<CompanyAttendanceItemModel> list =  queryList(sqlSearch);
		return list;
	}
	
}
