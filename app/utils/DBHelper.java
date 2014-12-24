package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.ejb.QueryImpl;
import org.hibernate.transform.Transformers;

import play.Logger;
import play.db.DB;
import play.db.jpa.JPA;
import exception.QueryException;

/**
 * 数据库方法调用
 * @author Lingdong lingdong@golaxy.cn
 * @since  2013-07-02 12:51:54
 */
public class DBHelper { 
	
	/**
	 * 把hql查询结果每一条封装成map
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 12:53:32
	 * @param  hql 查询语句
	 * @return 数据结果集合
	 */
	public static List getSqlQueryListToMap(String hql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createQuery(hql);
		Query hq = jpaQuery.getHibernateQuery();
		hq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return hq.list();
	}
	
	/**
	 * 把sql查询结果每一条封装成map
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 12:53:32
	 * @param  hql 查询语句
	 * @return 数据结果集合
	 */
	public static List getNativeSqlQueryListToMap(String sql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createNativeQuery(sql);
		Query hq = jpaQuery.getHibernateQuery();
		hq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return hq.list();
	}
	
	/**
	 * 根据sql查询结果返回查询数量
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 13:31:42
	 * @param  sql 查询语句
	 * @return 查询结果  数量
	 */
	public static int getNativeSqlQueryCount(String hql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createNativeQuery(hql);
		Query hq = jpaQuery.getHibernateQuery();
		Object obj = hq.uniqueResult();
		int count = Integer.parseInt(String.valueOf(obj));
		return count;
	}
	
	/**
	 * 根据hql查询结果返回查询数量
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 13:31:42
	 * @param  sql 查询语句
	 * @return 查询结果  数量
	 */
	public static int getSqlQueryCount(String hql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createQuery(hql);
		Query hq = jpaQuery.getHibernateQuery();
		Object obj = hq.uniqueResult();
		int count = Integer.parseInt(String.valueOf(obj));
		return count;
	}
	
	/**
	 * 根据hql查询单个结果封装为MAP返回
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 15:34:11
	 * @param  sql 查询语句
	 * @return 查询结果  
	 */
	public static Object getSqlQueryObjectToMap(String hql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createQuery(hql);
		Query hq = jpaQuery.getHibernateQuery();
		hq.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return hq.uniqueResult();
	}
	
	/**
	 * 根据hql查询单个结果返回
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 15:37:11
	 * @param  sql 查询语句
	 * @return 查询结果  
	 */
	public static Object getSqlQueryObject(String hql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createQuery(hql);
		Query hq = jpaQuery.getHibernateQuery();
		return hq.uniqueResult();
	}
	
	/**
	 * 根据hql指定表更新
	 * @author Lingdong lingdong@golaxy.cn
	 * @since  2013-07-02 16:15:03
	 * @param  sql 查询语句
	 * @return 更新影响的行数  
	 */
	public static int executeSqlQueryUpdate(String hql){
		QueryImpl jpaQuery = (QueryImpl) JPA.em().createQuery(hql);
		Query hq = jpaQuery.getHibernateQuery();
		return hq.executeUpdate();
	}
	
	/**
	 * 
	 * @param sql
	 * @return JSONArray
	 * @throws Exception
	 */
	public static JSONArray executeSqlQuery(String sql){
		ResultSet rs = DB.executeQuery(sql);
		JSONArray array;
		try {
			// 取sql语句查询返回列信息
			ResultSetMetaData metaData = rs.getMetaData();
			// 列数
			int columnCount = metaData.getColumnCount();
			array = new JSONArray();
			while(rs.next()) {// 保存每一条数据查询结果
				JSONObject record = new JSONObject();
				// 取各列列名并取各列数据
				for (int i = 0; i < columnCount; i++) {
					// 列名, 全转换成小写的
					String columnName = metaData.getColumnLabel(i + 1).toLowerCase();
					// 数据库中设计的列名, 多个单词用下划线隔开, 所以在此用下划线拆出每一个单词
					String[] words = columnName.split("_");
					// 列名对应的应该换成成的JSON对象键名
					StringBuffer key = new StringBuffer();
					// 转换各列并取各列数据
					for (int j = 0; j < words.length; j++) {
						// 列名中各单词
						String word = words[j];
						// 第一个单词不做转换
						if (0 != j) {
							// 将从第二个单词开始的第个词的第一个字母转换成大写
							word = word.substring(0, 1).toUpperCase() + word.substring(1);
						}
						// 拼接JSON的键名
						key.append(word);
					}
					// 对应列的值
					String value= rs.getString(columnName);
					// 封装数据, 所有列的数据都按字符串对应
					record.put(key.toString(), null == value ? "" : value);
					// 去除分页sql中的rownum, rn
					record.remove("rownum");
					record.remove("rn");
				}
				array.add(record);
			}
		} catch (SQLException e) {
			throw new QueryException(e);
		}
		return array;
	}
	
	/**
	 * 纯sql查询返回单个结果对象
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static JSONObject executeSqlQuery4Object(String sql){
		ResultSet rs = DB.executeQuery(sql);
		JSONObject json;
		try {
			// 取sql语句查询返回列信息
			ResultSetMetaData metaData = rs.getMetaData();
			// 列数
			int columnCount = metaData.getColumnCount();
			json = new JSONObject();
			while(rs.next()) {// 保存每一条数据查询结果
				// 取各列列名并取各列数据
				for (int i = 0; i < columnCount; i++) {
					// 列名
					String columnName = metaData.getColumnLabel(i + 1);
					// 对应列的值
					String value= rs.getString(columnName);
					json.put(columnName, value);//放入json对象
				}
			}
		} catch (SQLException e) {
			throw new QueryException(e);
		}
		return json;
	}
	
	/**
	 * 发送查询单个值的sql
	 * @author rainyhao
	 * @since 2013-7-12 上午10:19:36
	 * @param sql 查询sql
	 * @param param 预处理参数
	 * @return
	 */
	public static Object singleResultBySql(String sql, Object... param) {
		Object out = null;
		try {
			javax.persistence.Query query = JPA.em().createNativeQuery(sql);
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i + 1, param[i]);
			}
			out = query.getSingleResult();
		} catch (Exception e) {
			Logger.error(e, "Error execute query: " + sql);
			throw new QueryException(e);
		}
		return out;
	}
	
	/**
	 * 通过jpa的native sql 查询list
	 * @author rainyhao
	 * @since 2013-8-1 下午5:00:26
	 * @param sql
	 * @param param
	 * @return
	 */
	public static List queryForList(String sql, Object... param) {
		javax.persistence.Query q = JPA.em().createNativeQuery(sql);
		for (int i = 0; i < param.length; i++) {
			q.setParameter(i + 1, param[i]);
		}
		return q.getResultList(); 
	}
	
	/**
	 * 发送查询sql, 将每一条查询结果封装成指定泛型参数的类型
	 * @author rainyhao
	 * @since 2013-7-29 下午4:37:09
	 * @param sql
	 * @param rowMapper
	 * @param param
	 * @return
	 */
	public static <T> List<T> queryForList(String sql, SqlRowMapper<T> rowMapper, Object... param) {
		List<T> lstQueryResult = new ArrayList<T>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DB.getConnection();
			ps = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				T t = rowMapper.mapRow(rs);
				lstQueryResult.add(t);
			}
		} catch (SQLException e) {
			Logger.error("Error execute query SqlHelper.queryForList: " + sql);
			throw new QueryException(e);
		} finally {
			dispose(con, ps, rs);
		}
		return lstQueryResult;
	}
	
	public static List<JSONObject> queryForJSONList(String sql, Object... param) {
		return queryForList(sql, new JSONRowMapper(), param);
	}
	
	public static JSONObject singleJSONBySql(String sql, Object... param) {
		List<JSONObject> json = queryForJSONList(sql, param);
		return (null == json || 0 == json.size()) ? null : json.get(0);
	}
	
	public static <T> T queryForObject(String sql, SqlRowMapper<T> rowMapper, Object... param) {
		List<T> array = queryForList(sql, rowMapper, param);
		return (null == array || 0 == array.size()) ? null : array.get(0);
	}
	
	public static String queryForString(String sql, Object... param) {
		String s = queryForObject(sql, new SqlRowMapper<String>() {
			public String mapRow(ResultSet rs) throws SQLException {
				return rs.getString(1);
			}
		}, param);
		return null == s ? "" : s;
	}
	
	public static int queryForInt(String sql, Object... param) {
		Integer result = queryForObject(sql, new SqlRowMapper<Integer>() {
			public Integer mapRow(ResultSet rs) throws SQLException {
				return rs.getInt(1);
			}
		}, param);
		return null == result ? 0 : result.intValue();
	}
	
	public static long queryForLong(String sql, Object... param) {
		Long result = queryForObject(sql, new SqlRowMapper<Long>() {
			public Long mapRow(ResultSet rs) throws SQLException {
				return rs.getLong(1);
			}
		}, param);
		return null == result ? 0 : result.longValue();
	}
	
	/**
	 * 发送查询语句, 并将每一条数据封装成map
	 * map的key是sql中列被as成的别名, 别名有多个单词的, 用下划线隔开
	 * 此方法会把用下划线隔开的as别名自动转换成以驼峰命名方法转换成成map的key
	 * @author rainyhao
	 * @since 2013-7-16 上午11:12:12
	 * @param sql 查询语句
	 * @param page 分页参数 第几嶥
	 * @param pageSize 颁参数 第页多少条
	 * @param param 预处理参数
	 */
	public static List<JSONObject> queryForJSONList(String sql, Integer page, Integer pageSize, Object... param) {
		return queryForList(sql, page, pageSize, new JSONRowMapper(), param);
	}
	
	/**
	 * @author rainyhao
	 * @since 2013-7-16 上午10:21:51
	 * @param sql
	 * @param rowMapper
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> queryForList(String sql, Integer page, Integer pageSize, SqlRowMapper<T> rowMapper, Object... param) {
		if (null == page || page.intValue() < 1) {
			page = 1;
		}
		if (null == pageSize || pageSize.intValue() < 1) {
			pageSize = 10;
		}
		List<T> lstQueryResult = new ArrayList<T>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DB.getConnection();
			ps = con.prepareStatement(sql + " limit " + (pageSize * (page -1)) + ", " + pageSize);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				T t = rowMapper.mapRow(rs);
				lstQueryResult.add(t);
			}
		} catch (SQLException e) {
			Logger.error("Error execute query SqlHelper.queryForList: " + sql);
			throw new QueryException(e);
		} finally {
			dispose(con, ps, rs);
		}
		return lstQueryResult;
	}
	
	/**
	 * 发送sql的更新语句
	 * @author rainyhao
	 * @since 2013-7-12 上午10:16:27
	 * @param sql 更新sql
	 * @param param 预处理参数
	 * @return
	 */
	public static int updateBySql(String sql, Object... param) {
		int result = -1;
		try {
			javax.persistence.Query query = JPA.em().createNativeQuery(sql);
			for (int i = 0; i < param.length; i++) {
				query.setParameter(i + 1, param[i]);
			}
			result = query.executeUpdate();
		} catch (Exception e) {
			Logger.error("Error execute sql query: " + sql);
			throw new QueryException(e);
		}
		return result;
	}
	
	/**
	 * 发送jpql查单个值
	 * @author rainyhao
	 * @since 2013-8-15 上午11:30:39
	 * @param jpql jpql
	 * @param params jpql预处理参数值
	 * @return
	 */
	public static Object singleResult(String jpql, Object... params) {
		Object result = null;
		try {
			javax.persistence.Query query = JPA.em().createQuery(jpql);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
			result = query.getSingleResult();
		} catch (Exception e) {
			Logger.error("Error execute JPQL: " + jpql);
			throw new QueryException(e);
		}
		return result;
	}
	
//	public static PageData queryForPage(String sql, String count, Integer page, Integer pageSize, SqlRowMapper rowMapper, Object... param) {
//		if (null == page || page.intValue() < 1) {
//			page = 1;
//		}
//		if (null == pageSize || pageSize.intValue() < 1) {
//			pageSize = 10;
//		}
//		List rows = queryForList(sql, page, pageSize, rowMapper, param);
//		Object records = singleResultBySql(count, param);
//		PageData pageData = new PageData();
//		pageData.setRows(rows);
//		pageData.setPage(page);
//		pageData.setPageSize(pageSize);
//		pageData.setRecords(null == records ? 0 : Integer.parseInt(records.toString()));
//		return pageData;
//	}
	
//	public static PageData queryForPage(String sql, String count, Integer page, Integer pageSize, Object... param) {
//		return queryForPage(sql, count, page, pageSize, new JSONRowMapper(), param);
//	}
	
	/**
     * 关闭mysql数据库连接
     * 
     * @param conn
     *            连接对象
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void closeStatement(Statement ps) {
    	if (null != ps) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void closeResultSet(ResultSet rs) {
    	if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    public static void dispose(Connection con, Statement ps, ResultSet rs) {
    	closeResultSet(rs);
    	closeStatement(ps);
    	closeConnection(con);
    }
}
