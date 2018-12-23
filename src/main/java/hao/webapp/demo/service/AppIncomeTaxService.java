package hao.webapp.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.sql.SqlWrite;
import hao.framework.db.sql.SqlWrite.SqlWriterAction;
import hao.framework.service.BaseService;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.model.AppIncomeTax;

@Service
public class AppIncomeTaxService extends BaseService{
	
	/**
	 * 保存
	 * @throws HaoException 
	 */
	public AppIncomeTax save() throws HaoException {
		Form form = new Form(AppIncomeTax.class,RequestContext.getHttpRequest());
		form.validate("proportion", ValidateType.REQUIRED);//比例不能为空
		String lowNum = form.getParamValue("lowNum");
		String upNum = form.getParamValue("upNum");
		if(lowNum.equals("")&&upNum.equals("")) {
			throw new HaoException("999999","上线和下线不能同时为空");
		}
		SqlWrite sqlWriter = null;//new SqlWrite(AppIncomeTax.class,SqlWriterAction.insert);
		boolean isInsert  = form.isInsertAction();
		if(isInsert) {
			form.setPrimaryValue();
			sqlWriter = new SqlWrite(AppIncomeTax.class,SqlWriterAction.insert);
			sqlWriter.setFields(form.getBeanMap());
		}else {
			sqlWriter = new SqlWrite(AppIncomeTax.class,SqlWriterAction.update);
			sqlWriter.setFields(form.getBeanMap());
			sqlWriter.addCondition("id",form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWriter);
		AppIncomeTax tax =form.getBean();
		return tax;
	}
	
	/**
	 * 查询列表信息
	 * @return
	 * @throws HaoException 
	 */
	public List<AppIncomeTax> load_list() throws HaoException{
		List<AppIncomeTax> list = this.jdbcDao.queryList(AppIncomeTax.class);
		return list;
	}
	
	/**
	 * 删除
	 * @param id
	 * @throws HaoException
	 */
	public void  del(String id) throws HaoException {
		SqlWrite sqlWrite = new SqlWrite(AppIncomeTax.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
	
}
