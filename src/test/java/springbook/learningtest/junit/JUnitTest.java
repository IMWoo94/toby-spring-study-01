package springbook.learningtest.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/junit.xml")
public class JUnitTest {

//    static JUnitTest testObject;
    static Set<JUnitTest> testObjects = new HashSet<>();

    @Autowired
    ApplicationContext context;

    static ApplicationContext contextObject = null;

    @Test
    public void test1(){
//        assertThat(this, is(not(sameInstance(testObject))));
//        testObject = this;

        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        assertThat(contextObject==null || contextObject == this.context, is(true));
        contextObject = this.context;
    }

    @Test
    public void test2(){
//        assertThat(this, is(not(sameInstance(testObject))));
//        testObject = this;

        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        assertTrue(contextObject==null || contextObject == this.context);
        contextObject = this.context;
    }

    @Test
    public void test3(){
//        assertThat(this, is(not(sameInstance(testObject))));
//        testObject = this;

        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);

        assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
        contextObject = this.context;
    }
}
