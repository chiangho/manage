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
import hao.webapp.demo.model.AppSetting;

@Service
public class AppSettingService extends BaseService{
	
	/**
	 * 保存
	 * @throws HaoException
	 */
	public void settingSave() throws HaoException {
		Form form = new Form(AppSetting.class,RequestContext.getHttpRequest());
		form.validate("sbjs",ValidateType.REQUIRED);
		form.validate("sbblYanglao",ValidateType.REQUIRED);
		form.validate("sbblYiliao",ValidateType.REQUIRED);
		form.validate("sbblShiye",ValidateType.REQUIRED);
		form.validate("sbblGongjijin",ValidateType.REQUIRED);
		SqlWrite sqlWrite = new SqlWrite(AppSetting.class,SqlWriterAction.update);
		sqlWrite.setFields(form.getBeanMap());
		this.jdbcDao.execute(sqlWrite);
	}

	/**
	 * 读取加载
	 * @return
	 * @throws HaoException
	 */
	public AppSetting settingLoad() throws HaoException {
		List<AppSetting> appSettings = this.jdbcDao.queryList(AppSetting.class);
		if(appSettings==null||appSettings.size()==0) {
			return null;
		}
		return appSettings.get(0);
	}
	
}
