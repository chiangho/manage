package hao.webapp.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWhere;
import hao.framework.service.BaseService;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.AppConstant;
import hao.webapp.demo.CommonData;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.CompanyAttendanceItemModel;
import hao.webapp.demo.model.CompanyDataItemModel;


@Service
public class CompanyService extends BaseService{
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws HaoException 
	 */
	public List<Company> queryPageList(Page page) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(Company.class);
		sqlSearch.addCondition("isDel",  AppConstant.DLE_STATE_NO);
		return jdbcDao.queryList(page, sqlSearch);//this.queryPageList(page, sqlSearch);
	}

	/**
	 * 保存公司信息
	 * @param name
	 * @throws HaoException 
	 */
	public void saveCompany() throws HaoException {
		Form form = new Form(Company.class,RequestContext.getHttpRequest());
		form.validate("name", ValidateType.REQUIRED);//必须填写
		form.validate("name", ValidateType.ONLYONE);//公司名称不能重复
		boolean isInsert = form.isInsertAction();
		if(isInsert) {
			form.setPrimaryValue();
			dao.insert(form.getBean());
		}else {
			Map<String,Object> fields = form.getBeanMap();
			SqlWhere sqlWhere = new SqlWhere();
			sqlWhere.addCondition(Company.class, "id", form.getParamValue("id"));
			dao.update(Company.class, fields, sqlWhere);
		}
	}
	/**
	 * 销毁公司
	 */
	public void destroy(String id) {
		dao.deleteById(Company.class, id);
	}
	
	/**
	 * 删除公司.将公司设置为删除
	 * @throws HaoException 
	 */
	public void del(String id) throws HaoException {
		SqlWhere sqlWhere = new SqlWhere();
		sqlWhere.addCondition(Company.class, "id", id);
		Map<String,Object> fields = new HashMap<String,Object>();
		fields.put("isDel", AppConstant.DLE_STATE_YES);
		dao.update(Company.class, fields, sqlWhere);
	}

	public Company queryBean(String id) throws HaoException {
		return this.queryByField(Company.class, "id", id);
	}

	/**
	 * 查询全部
	 * @return
	 * @throws HaoException
	 */
	public List<Company> queryAll() throws HaoException {
		return this.queryAll(Company.class);
	}
	
	/**
	 * 获取此公司的计算因子，排除自己
	 * @param companyId
	 * @return
	 * @throws HaoException 
	 */
	public List<String> queryCompanyDateItem(String companyId) throws HaoException {
		List<String> items = new ArrayList<String>();
		SqlSearch sqlSearch = new SqlSearch(CompanyDataItemModel.class);
		sqlSearch.addCondition("companyId", companyId);
		
		//计算因子
//		List<CompanyDataItemModel> list = this.queryList(sqlSearch);
//		if(list!=null&&list.size()>0) {
//			for(CompanyDataItemModel item:list) {
//				if(item.getName()!=null&&!item.getName().equals("")) {
//					items.add(item.getName());
//				}
//			}
//		}
		//考情倒入项目
		List<CompanyAttendanceItemModel> summaryItem = this.queryListByField(CompanyAttendanceItemModel.class, "companyId", companyId);
		if(summaryItem!=null&&summaryItem.size()>0) {
			for(CompanyAttendanceItemModel item:summaryItem) {
				if(item.getName()!=null&&!item.getName().equals("")) {
					items.add(item.getName());
				}
			}
		}
		//常两项
		Set<String> computinItem = CommonData.getGlobleBasicComputingItem();
		for(String s:computinItem) {
			items.add(s);
		}
		return items;
	}
	

}
