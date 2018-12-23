package hao.webapp.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.sql.SqlCommon.SqlSort;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWrite;
import hao.framework.db.sql.SqlWrite.SqlWriterAction;
import hao.framework.service.BaseService;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.model.CompanyBaseProject;
import hao.webapp.demo.model.CompanyDepartment;
import hao.webapp.demo.model.CompanyPoint;
import hao.webapp.demo.model.CompanyProductionLine;
import hao.webapp.demo.model.CompanyProject;

@Service
public class CompanySettingService extends BaseService{

	/////////////////////////////////////////////大项目/////////////////////////////////////////////////////////
	public List<CompanyBaseProject> queryCompanyBaseProjectList(String companyId) throws HaoException{
		SqlSearch search = new SqlSearch(CompanyBaseProject.class);
		search.addCondition("companyId", companyId);
		search.order("id",SqlSort.desc);
		List<CompanyBaseProject> list = this.jdbcDao.queryList(search);
		return list;
	}
	
	public void saveBaseProject() throws HaoException {
		Form form = new Form(CompanyBaseProject.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("name", ValidateType.REQUIRED);
		form.validate("companyId", ValidateType.REQUIRED);
		SqlWrite sqlWrite = null;
		if(isInsert) {
			form.setPrimaryValue();
			sqlWrite = new SqlWrite(CompanyBaseProject.class,SqlWriterAction.insert);
			sqlWrite.setFields(form.getBeanMap());
		}else {
			sqlWrite = new SqlWrite(CompanyBaseProject.class,SqlWriterAction.update);
			sqlWrite.setFields(form.getBeanMap());
			sqlWrite.addCondition("id", form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWrite);
	}
	
	public void delBaseProject(String id) throws HaoException {
		SqlWrite sqlWrite =new SqlWrite(CompanyBaseProject.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
	
	///////////////////////////////////////项目////////////////////////////////////////////////////
	public List<CompanyProject> queryCompanyProjectList(String companyId) throws HaoException{
		SqlSearch search = new SqlSearch(CompanyProject.class);
		search.addCondition("companyId", companyId);
		search.order("id",SqlSort.desc);
		List<CompanyProject> list = this.jdbcDao.queryList(search);
		return list;
	}
	
	public void saveProject() throws HaoException {
		Form form = new Form(CompanyProject.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("name", ValidateType.REQUIRED);
		form.validate("companyId", ValidateType.REQUIRED);
		SqlWrite sqlWrite = null;
		if(isInsert) {
			form.setPrimaryValue();
			sqlWrite = new SqlWrite(CompanyProject.class,SqlWriterAction.insert);
			sqlWrite.setFields(form.getBeanMap());
		}else {
			sqlWrite = new SqlWrite(CompanyProject.class,SqlWriterAction.update);
			sqlWrite.setFields(form.getBeanMap());
			sqlWrite.addCondition("id", form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWrite);
	}
	
	public void delProject(String id) throws HaoException {
		SqlWrite sqlWrite =new SqlWrite(CompanyProject.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
	///////////////////////////////////职务////////////////////////////////////////////////////
	public List<CompanyPoint> queryCompanyPoint(String companyId) throws HaoException{
		SqlSearch search = new SqlSearch(CompanyPoint.class);
		search.addCondition("companyId", companyId);
		search.order("id",SqlSort.desc);
		List<CompanyPoint> list = this.jdbcDao.queryList(search);
		return list;
	}
	public void savePoint() throws HaoException {
		Form form = new Form(CompanyPoint.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("name", ValidateType.REQUIRED);
		form.validate("companyId", ValidateType.REQUIRED);
		SqlWrite sqlWrite = null;
		if(isInsert) {
			form.setPrimaryValue();
			sqlWrite = new SqlWrite(CompanyPoint.class,SqlWriterAction.insert);
			sqlWrite.setFields(form.getBeanMap());
		}else {
			sqlWrite = new SqlWrite(CompanyPoint.class,SqlWriterAction.update);
			sqlWrite.setFields(form.getBeanMap());
			sqlWrite.addCondition("id", form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWrite);
	}
	
	public void delPoint(String id) throws HaoException {
		SqlWrite sqlWrite =new SqlWrite(CompanyPoint.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
	
	//////////////////////////////////部门//////////////////////////////////////////////
	public List<CompanyDepartment> queryCompanyDepartment(String companyId) throws HaoException{
		SqlSearch search = new SqlSearch(CompanyDepartment.class);
		search.addCondition("companyId", companyId);
		search.order("id",SqlSort.desc);
		List<CompanyDepartment> list = this.jdbcDao.queryList(search);
		return list;
	}
	public void saveDepartment() throws HaoException {
		Form form = new Form(CompanyDepartment.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("name", ValidateType.REQUIRED);
		form.validate("companyId", ValidateType.REQUIRED);
		SqlWrite sqlWrite = null;
		if(isInsert) {
			form.setPrimaryValue();
			sqlWrite = new SqlWrite(CompanyDepartment.class,SqlWriterAction.insert);
			sqlWrite.setFields(form.getBeanMap());
		}else {
			sqlWrite = new SqlWrite(CompanyDepartment.class,SqlWriterAction.update);
			sqlWrite.setFields(form.getBeanMap());
			sqlWrite.addCondition("id", form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWrite);
	}
	
	public void delDepartment(String id) throws HaoException {
		SqlWrite sqlWrite =new SqlWrite(CompanyDepartment.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
	
	/////////////////////////////////////产线////////////////////////////////////////
	public List<CompanyProductionLine> queryProductionLine(String companyId) throws HaoException{
		SqlSearch search = new SqlSearch(CompanyProductionLine.class);
		search.addCondition("companyId", companyId);
		search.order("id",SqlSort.desc);
		List<CompanyProductionLine> list = this.jdbcDao.queryList(search);
		return list;
	}
	public void saveProductionLine() throws HaoException {
		Form form = new Form(CompanyProductionLine.class,RequestContext.getHttpRequest());
		boolean isInsert = form.isInsertAction();
		form.validate("name", ValidateType.REQUIRED);
		form.validate("companyId", ValidateType.REQUIRED);
		SqlWrite sqlWrite = null;
		if(isInsert) {
			form.setPrimaryValue();
			sqlWrite = new SqlWrite(CompanyProductionLine.class,SqlWriterAction.insert);
			sqlWrite.setFields(form.getBeanMap());
		}else {
			sqlWrite = new SqlWrite(CompanyProductionLine.class,SqlWriterAction.update);
			sqlWrite.setFields(form.getBeanMap());
			sqlWrite.addCondition("id", form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWrite);
	}
	
	public void delProductionLine(String id) throws HaoException {
		SqlWrite sqlWrite =new SqlWrite(CompanyProductionLine.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
}
