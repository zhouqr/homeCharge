package utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 把jdbc查询结果封装成JSON对象
 * @author rainyhao haojz@golaxy.cn
 * @since 2013-7-16 上午11:03:36
 */
public class JSONRowMapper implements SqlRowMapper<JSONObject> {

	@Override
	public JSONObject mapRow(ResultSet rs) throws SQLException {
		// 保存每一条数据查询结果
		JSONObject record = new JSONObject();
		// 取sql语句查询返回列信息
		ResultSetMetaData metaData = rs.getMetaData();
		// 列数
		int columnCount = metaData.getColumnCount();
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
			String value = rs.getString(columnName);
			// 封装数据, 所有列的数据都按字符串对应
			record.put(key.toString(), null == value ? "" : value);
		}
		return record;
	}

}
