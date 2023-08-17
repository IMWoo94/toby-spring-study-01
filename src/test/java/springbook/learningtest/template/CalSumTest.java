package springbook.learningtest.template;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalSumTest {

	Calculator calculator;
	String numFilepath;

	@BeforeAll
	public void setUp() {
		this.calculator = new Calculator();
		this.numFilepath = "/Users/imwoo/study/토비의 스프링/toby-spring-study-01/src/test/resources/numbers.txt";
	}

	@Test
	public void sumOfNumbers() throws IOException {
		assertThat(calculator.calcSum(numFilepath), is(10));
	}

	@Test
	public void multiplyOfNumbers() throws IOException {
		assertThat(calculator.calcMultiply(numFilepath), is(24));
	}

	@Test
	public void concatenateStrings() throws IOException {
		assertThat(calculator.concatenate(numFilepath), is("1234"));
	}

}
