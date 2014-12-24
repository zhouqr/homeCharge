package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC查询模版方法中
 * 查询结果封装的回调函数接口
 * @author rainyhao haojz@golaxy.cn
 * @since 2013-7-16 上午9:57:51
 */
public interface SqlRowMapper<T> {
	
	/**
	 * 映射每一条jdbc ResultSet中的查询结果到指定的泛型参数对象中
	 * @author rainyhao
	 * @since 2013-7-16 上午9:59:19
	 * @param rs
	 * @return
	 */
	public T mapRow(ResultSet rs) throws SQLException;
}
