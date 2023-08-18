package springbook.learningtest.spring.factorybean;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springbook.user.factorybean.Message;
import springbook.user.factorybean.MessageFactoryBean;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/FactoryBeanTest-context.xml")
public class FactoryBeanTest {
	@Autowired
	ApplicationContext context;

	@Autowired
	Message messageDi;

	@Test
	public void getMessageFromFactoryBean() {
		Object message = context.getBean("message");
		assertThat(message.getClass(), is(Message.class));
		assertThat(((Message)message).getText(), is("Factory Bean"));

		Object factory = context.getBean("&message");
		assertThat(factory.getClass(), is(MessageFactoryBean.class));

		assertThat(messageDi.getClass(), is(Message.class));
		assertThat(messageDi.getText(), is("Factory Bean"));

	}
}
