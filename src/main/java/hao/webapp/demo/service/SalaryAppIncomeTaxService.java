package hao.webapp.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.core.sequence.Seq;
import hao.framework.db.sql.SqlWrite;
import hao.framework.db.sql.SqlWrite.SqlWriterAction;
import hao.framework.service.BaseService;
import hao.framework.utils.BeanUtil;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.web.form.validate.ValidateType;
import hao.webapp.demo.model.AppIncomeTax;
import hao.webapp.demo.model.SalaryAppIncomeTax;

/**
 * 个税比例
 * @author chianghao
 *
 */
@Service
public class SalaryAppIncomeTaxService extends BaseService{
	/**
	 * 保存
	 * @throws HaoException 
	 */
	public SalaryAppIncomeTax save() throws HaoException {
		Form form = new Form(SalaryAppIncomeTax.class,RequestContext.getHttpRequest());
		form.validate("proportion", ValidateType.REQUIRED);//比例不能为空
		String lowNum   = form.getParamValue("lowNum");
		String upNum    = form.getParamValue("upNum");
		String salaryId = RequestContext.getParam("salaryId");
		if(lowNum.equals("")&&upNum.equals("")) {
			throw new HaoException("999999","上线和下线不能同时为空");
		}
		SqlWrite sqlWriter = null;//new SqlWrite(AppIncomeTax.class,SqlWriterAction.insert);
		boolean isInsert  = form.isInsertAction();
		Map<String,Object> map = form.getBeanMap();
		map.put("salaryId", salaryId);
		if(isInsert) {
			map.put("id", Seq.getNextId());
			sqlWriter = new SqlWrite(SalaryAppIncomeTax.class,SqlWriterAction.insert);
			sqlWriter.setFields(map);
		}else {
			sqlWriter = new SqlWrite(SalaryAppIncomeTax.class,SqlWriterAction.update);
			sqlWriter.setFields(map);
			sqlWriter.addCondition("id",form.getParamValue("id"));
		}
		this.jdbcDao.execute(sqlWriter);
		SalaryAppIncomeTax tax =form.getBean();
		return tax;
	}
	
	/**
	 * 覆盖
	 * @param salaryId
	 * @throws HaoException 
	 */
	public void coverSalaryAppIncomeTax(long salaryId) throws HaoException {
		//查询系统的个税比例
		List<AppIncomeTax> list = this.jdbcDao.queryList(AppIncomeTax.class);
		if(list!=null&&list.size()>0) {
			//BatchUpdate batchUpdate = BatchUpdate.createBatchUpdate();
			List<SqlWrite> sqlWrites = new ArrayList<SqlWrite>();
			//删除原有的
			SqlWrite del = new SqlWrite(SalaryAppIncomeTax.class,SqlWriterAction.delete);
			del.addCondition("salaryId",salaryId);
			sqlWrites.add(del);
			
			for(AppIncomeTax tax:list) {
				SalaryAppIncomeTax salaryAppIncomeTac = new SalaryAppIncomeTax();
				try {
					BeanUtil.copyBean2Bean(salaryAppIncomeTac, tax);
					salaryAppIncomeTac.setSalaryId(salaryId);
					salaryAppIncomeTac.setId(Seq.getNextId());
					Map<String,Object> map = BeanUtil.copyBean2Map(salaryAppIncomeTac);
					SqlWrite insert = new SqlWrite(SalaryAppIncomeTax.class,SqlWriterAction.insert);
					insert.setFields(map);
					sqlWrites.add(insert);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.jdbcDao.executeBatch(sqlWrites);
		}
	}
	
	/**
	 * 查询列表信息
	 * @return
	 * @throws HaoException 
	 */
	public List<SalaryAppIncomeTax> load_list(String salaryId) throws HaoException{
		List<SalaryAppIncomeTax> list = this.jdbcDao.queryList(SalaryAppIncomeTax.class,"salaryId",salaryId);
		return list;
	}
	
	/**
	 * 删除
	 * @param id
	 * @throws HaoException
	 */
	public void  del(String id) throws HaoException {
		SqlWrite sqlWrite = new SqlWrite(SalaryAppIncomeTax.class,SqlWriterAction.delete);
		sqlWrite.addCondition("id",id);
		this.jdbcDao.execute(sqlWrite);
	}
	
}
