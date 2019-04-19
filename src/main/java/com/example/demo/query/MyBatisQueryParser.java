package com.example.demo.query;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

public class MyBatisQueryParser extends AbstractDevelopQueryReloadPlugin implements InitializingBean, IQueryParser {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private Configuration configuration;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("mybatis parser configuration init...");
		this.configuration = new Configuration();
		String[] mapperLocationsStr = new String[] { "com/example/demo/query/ArticleQuery.xml" };
		Resource[] mapperLocations = resolveMapperLocations(mapperLocationsStr);
		for (Resource mapperLocation : mapperLocations) {
			if (mapperLocation == null)
				continue;
			try {
				String fileName = FilenameUtils.getBaseName(mapperLocation.getURL().toString());
				if (fileName.indexOf("Query") != -1) {
					fileName = fileName.substring(0, fileName.indexOf("Query"));
				}
				this.addResourceHolder(fileName.toLowerCase(), mapperLocation);
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
						this.configuration, mapperLocation.toString(), this.configuration.getSqlFragments());
				xmlMapperBuilder.parse();
			} catch (Exception e) {
				throw new RuntimeException("Failed to parse mapping resource: '\" + mapperLocation + \"'");
			}
		}
	}

	@Override
	protected boolean isDevelop() {
		return true;
	}

	@Override
	protected void doReload(String queryName) {
		try {
			this.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String executeInternal(String queryName, Object params) {
		MappedStatement statement = this.configuration.getMappedStatement(queryName);
		if (statement != null) {
			try {
				BoundSql boudSql = statement.getBoundSql(params);
				return boudSql.getSql();
			} catch (Exception e) {
				throw e;
			}
		} else {
			throw new RuntimeException("Query statment: [" + queryName + "] not found");
		}
	}

}
