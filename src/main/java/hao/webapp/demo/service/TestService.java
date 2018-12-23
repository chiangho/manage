package hao.webapp.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.db.sql.SqlCommon.SqlOperator;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWhere;
import hao.framework.service.BaseService;
import hao.framework.utils.StringUtils;
import hao.webapp.demo.model.system.Access;

@Service
public class TestService extends BaseService{

	
	/***
	 * 添加
	 * @throws HaoException
	 */
	public void insert() throws HaoException {
		// TODO Auto-generated method stub
		Access access = new Access();
		//access.setId(Seq.getNextId());
		access.setAccess("chianghao");
		access.setPassword(StringUtils.md5("111111"));
		access.setMobilePhone("13115091781");
		access.setUserName("蒋昊");
		access.setEmail("chianghao@126.com");
		dao.insert(access);
	}

	/***
	 * 查询
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	public Object access(long id) throws HaoException{
			SqlWhere sqlWhere = new SqlWhere();
			sqlWhere.addCondition(Access.class, "id", "417319441471246336");
			this.dao.delete(Access.class, sqlWhere);
		
		    SqlSearch sqlSearch = new SqlSearch(Access.class);
			//sqlSearch.addCondition("id", id);
			sqlSearch.addCondition("id",SqlOperator.in,new Object[] {
					"417319441471246336",
					 "417319441764847616",
					 "417319441764847617",
					 "417319441806790656",
					 "417319442071031808",
					 "417319442092003328",
					 "417319442096197632",
					 "417319442096197633",
					 "417319442100391936",
					 "417319442104586240",
					 "417319442108780544"
			});
			List<Access> access = queryList(sqlSearch);
			return access;
	}

	
	
	
}
