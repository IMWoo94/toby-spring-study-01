package springbook.learningtest.jdk.jaxb;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

public class JaxbTest {

	@Test
	public void readSqlmap() throws JAXBException, IOException {
		String contextPath = Sqlmap.class.getPackage().getName();
		JAXBContext context = JAXBContext.newInstance(contextPath);

		// 언마샬러 생성
		// 언마샬링 -> XML 문서를 읽어서 자바의 오브젝트로 변환
		// 마샬링 -> 바인딩 오브젝트를 XML문서로 변환하는 것
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(getClass().getResourceAsStream(
			"/sqlmap.xml"));

		List<SqlType> sqlList = sqlmap.getSql();

		assertThat(sqlList.size(), is(3));
		assertThat(sqlList.get(0).getKey(), is("add"));
		assertThat(sqlList.get(0).getValue(), is("insert"));
		assertThat(sqlList.get(1).getKey(), is("get"));
		assertThat(sqlList.get(1).getValue(), is("select"));
		assertThat(sqlList.get(2).getKey(), is("delete"));
		assertThat(sqlList.get(2).getValue(), is("delete"));
	}
}
