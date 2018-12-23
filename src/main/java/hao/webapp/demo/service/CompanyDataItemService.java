package hao.webapp.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.db.sql.SqlCommon.SqlOperator;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWhere;
import hao.framework.service.BaseService;
import hao.framework.utils.Base64Utils;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.CompanyAttendanceItemModel;
import hao.webapp.demo.model.CompanyDataItemModel;


@Service
public class CompanyDataItemService extends BaseService{
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws HaoException 
	 */
	public List<CompanyDataItemModel> queryPageList(Page page) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(CompanyDataItemModel.class);
		sqlSearch.join(Company.class, "companyId", "id");
		sqlSearch.order("id");
		return jdbcDao.queryList(page, sqlSearch);//this.queryPageList(page, sqlSearch);
	}

	/**
	 * 保存
	 * @throws HaoException 
	 */
	public void save() throws HaoException {
		Form form = new Form(CompanyDataItemModel.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("companyId",ValidateType.REQUIRED);
		form.validate("name",ValidateType.REQUIRED);
		form.validate("script",ValidateType.REQUIRED);
		form.validate("remark",ValidateType.REQUIRED);
		
		String base64Script = Base64Utils.encode(form.getParamValue("script"));
		form.setParamValue("script",base64Script);
		
		if(isInsert) {
			form.setPrimaryValue();
			CompanyDataItemModel dataItem = form.getBean();
			dao.insert(dataItem);
		}else {
			SqlWhere sqlWhere = new SqlWhere();
			sqlWhere.addCondition(CompanyDataItemModel.class, "id",form.getParamValue("id"));
			dao.update(CompanyDataItemModel.class, form.getBeanMap(), sqlWhere);
		}
		
	}

	/**
	 * 删除
	 * @param id
	 */
	public void del(String id) {
		dao.deleteById(CompanyDataItemModel.class, id);
	}

	/**
	 * 根据主键查询数据信息
	 * @param id
	 * @return
	 * @throws HaoException 
	 */
	public CompanyDataItemModel queryById(String id) throws HaoException {
		CompanyDataItemModel data = this.queryByField(CompanyDataItemModel.class, "id",id);
		data.setScript(Base64Utils.decode(data.getScript()));
		return data;
	}

	/**
	 * 获取此公司的计算因子，排除自己
	 * @param companyId
	 * @return
	 * @throws HaoException 
	 */
	public List<String> querySumDateItem(String companyId,String selfId) throws HaoException {
		List<String> items = new ArrayList<String>();
		SqlSearch sqlSearch = new SqlSearch(CompanyDataItemModel.class);
		sqlSearch.addCondition("companyId", companyId);
		if(selfId!=null&&!selfId.equals("")) {
			sqlSearch.addCondition("id",SqlOperator.notEqual, selfId);
		}
		List<CompanyDataItemModel> list = this.queryList(sqlSearch);
		if(list!=null&&list.size()>0) {
			for(CompanyDataItemModel item:list) {
				if(item.getName()!=null&&!item.getName().equals("")) {
					items.add(item.getName());
				}
			}
		}
		List<CompanyAttendanceItemModel> summaryItem = this.queryListByField(CompanyAttendanceItemModel.class, "companyId", companyId);
		if(summaryItem!=null&&summaryItem.size()>0) {
			for(CompanyAttendanceItemModel item:summaryItem) {
				if(item.getName()!=null&&!item.getName().equals("")) {
					items.add(item.getName());
				}
			}
		}
		items.add("全国每月平均工");
		return items;
	}

}
