package hao.webapp.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import hao.webapp.demo.model.AppSetting;
import hao.webapp.demo.model.SalaryAppSetting;

@Service
public class SalaryAppSettingService extends BaseService{

	@Autowired
	private AppSettingService appSettingService;
	/**
	 * 查询工资的社保比例
	 * @param salaryId
	 * @return
	 * @throws HaoException 
	 */
	public SalaryAppSetting querySalaryAppSetting(String salaryId) throws HaoException {
		SalaryAppSetting appsetting = this.jdbcDao.queryBean(SalaryAppSetting.class, "salaryId", salaryId);
		return appsetting;
	}
	/**
	 * 插入或更新
	 * @throws HaoException
	 */
	public void settingSave(String salaryId) throws HaoException {
		SalaryAppSetting appsetting = this.jdbcDao.queryBean(SalaryAppSetting.class, "salaryId", salaryId);
		Form form = new Form(SalaryAppSetting.class,RequestContext.getHttpRequest());
		form.validate("sbjs",ValidateType.REQUIRED);
		form.validate("sbblYanglao",ValidateType.REQUIRED);
		form.validate("sbblYiliao",ValidateType.REQUIRED);
		form.validate("sbblShiye",ValidateType.REQUIRED);
		form.validate("sbblGongjijin",ValidateType.REQUIRED);
		SqlWrite sqlWrite = null;
		Map<String,Object> item = form.getBeanMap();
		item.put("salaryId", salaryId);
		if(appsetting==null) {
			//插入
			item.put("id",Seq.getNextId());
			sqlWrite = new SqlWrite(SalaryAppSetting.class,SqlWriterAction.insert);
			sqlWrite.setFields(item);
		}else {
			//更新
			sqlWrite = new SqlWrite(SalaryAppSetting.class,SqlWriterAction.update);
			sqlWrite.setFields(item);
			sqlWrite.addCondition("salaryId", salaryId);
		}
		this.jdbcDao.execute(sqlWrite);
	}
	/**
	 * 将系统的参数覆盖
	 * @param salaryId
	 * @throws Exception 
	 */
	public void coverSalaryAppSetting(String salaryId) throws Exception {
		AppSetting appsetting  = appSettingService.settingLoad();
		SalaryAppSetting salaryAppSetting = this.jdbcDao.queryBean(SalaryAppSetting.class, "salaryId", salaryId);
		boolean isInsert = false;
		if(salaryAppSetting==null) {
			isInsert = true;
		}
		salaryAppSetting = new SalaryAppSetting();
		BeanUtil.copyBean2Bean(salaryAppSetting, appsetting);
		Map<String,Object> map = BeanUtil.copyBean2Map(salaryAppSetting);
		map.put("id",Seq.getNextId());
		map.put("salaryId", salaryId);
		SqlWrite sqlWrite = null;
		if(isInsert) {
			sqlWrite = new SqlWrite(SalaryAppSetting.class,SqlWriterAction.insert);
			sqlWrite.setFields(map);
		}else {
			sqlWrite = new SqlWrite(SalaryAppSetting.class,SqlWriterAction.update);
			sqlWrite.setFields(map);
			sqlWrite.addCondition("salaryId", salaryId);
		}
		this.jdbcDao.execute(sqlWrite);
	}
	
}
