package com.msrk.es.unit.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
public abstract class EntityServiceControllerBaseTestClass extends CommonBaseTest {
	
	
	protected MockMvc mockMvc;
	

}
