package com.example.demo.query;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private MyBatisQueryParser queryParser;

	public String getFile() throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("courseId", "courseId");
		String sql = queryParser.getQueryString("article.list", map);
		return getTotalQueryString(sql);
//		return sql;
	}

	protected String getTotalQueryString(String queryString) {
		// FIXME mybatis 的from 空格问题
		queryString = "select count(*) as total " + queryString.substring(shallowIndexOfWord(queryString, "from", 0));
		queryString = queryString.replaceAll("(?i)\\sorder(\\s)+by.+", " "); // 若存在order by则去除
		return queryString;
	}

	private final int shallowIndexOfWord(final String sb, final String search, int fromIndex) {
		final int index = shallowIndexOf(sb, ' ' + search + ' ', fromIndex);
		return index != -1 ? (index + 1) : -1; // In case of match adding one
		// because of space placed in
		// front of search term.
	}

	private final int shallowIndexOf(String sb, String search, int fromIndex) {
		final String lowercase = sb.toLowerCase(); // case-insensitive match
		final int len = lowercase.length();
		final int searchlen = search.length();
		int pos = -1, depth = 0, cur = fromIndex;
		do {
			pos = lowercase.indexOf(search, cur);
			if (pos != -1) {
				for (int iter = cur; iter < pos; iter++) {
					char c = sb.charAt(iter);
					if (c == '(') {
						depth = depth + 1;
					} else if (c == ')') {
						depth = depth - 1;
					}
				}
				cur = pos + searchlen;
			}
		} while (cur < len && depth != 0 && pos != -1);
		return depth == 0 ? pos : -1;
	}
}
