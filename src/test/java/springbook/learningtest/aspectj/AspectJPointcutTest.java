package springbook.learningtest.aspectj;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import springbook.user.pointcut.Bean;
import springbook.user.pointcut.Target;

public class AspectJPointcutTest {

	@Test
	public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		// pointcut.setExpression("execution(public int " +
		// 	"springbook.user.pointcut.Target.minus(int,int) " +
		// 	"throws java.lang.RuntimeException)");
		pointcut.setExpression("execution(int minus(int,int))");

		assertThat(pointcut.getClassFilter().matches(Target.class) &&
			pointcut.getMethodMatcher().matches(
				Target.class.getMethod("minus", int.class, int.class), null), is(true)
		);

		assertThat(pointcut.getClassFilter().matches(Target.class) &&
			pointcut.getMethodMatcher().matches(
				Target.class.getMethod("plus", int.class, int.class), null), is(false)
		);

		assertThat(pointcut.getClassFilter().matches(Bean.class) &&
			pointcut.getMethodMatcher().matches(
				Target.class.getMethod("method"), null), is(false)
		);

	}

	@Test
	public void pointcut() throws Exception {
		targeClassPointcutMatches("execution(*  *(..))", true, true, true, true, true, true);

	}

	public void pointcutMathes(String expression, Boolean expected, Class<?> clazz, String methodName,
		Class<?>... args) throws Exception {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);

		assertThat(pointcut.getClassFilter().matches(clazz) &&
			pointcut.getMethodMatcher().matches(
				clazz.getMethod(methodName, args), null), is(expected)
		);
	}

	public void targeClassPointcutMatches(String expression, boolean... expectead) throws Exception {
		pointcutMathes(expression, expectead[0], Target.class, "hello");
		pointcutMathes(expression, expectead[1], Target.class, "hello", String.class);
		pointcutMathes(expression, expectead[2], Target.class, "plus", int.class, int.class);
		pointcutMathes(expression, expectead[3], Target.class, "minus", int.class, int.class);
		pointcutMathes(expression, expectead[4], Target.class, "method");
		pointcutMathes(expression, expectead[5], Bean.class, "method");

	}
}
