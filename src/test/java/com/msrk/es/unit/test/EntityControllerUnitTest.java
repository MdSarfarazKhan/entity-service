package com.msrk.es.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.msrk.es.controller.EntityController;
import com.msrk.es.model.Entity;
import com.msrk.es.model.EntityMetaData;
import com.msrk.es.service.EntityService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;



@WebMvcTest(controllers = EntityController.class,secure=true)
public class EntityControllerUnitTest extends EntityServiceControllerBaseTestClass {

	@MockBean
	private EntityService entityService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private WebApplicationContext context;

	

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity()) 
				.build();
	}

	/*@org.junit.Before
	public void setUp()
	{
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "test");
		Authentication auth = authManager.authenticate(token);

		SecurityContext ctx = new SecurityContextImpl();
		ctx.setAuthentication(auth);
		SecurityContextHolder.setContext(ctx);
	}
	
	
	@After
	public void tearDown()
	{
		SecurityContextHolder.clearContext();
	}
	*/
	@Test
	@WithMockUser(roles="test")
	public void testCreateEntityType() throws Exception {
		String data = getFile("entity/cricketerEntityMetaData.json");
		EntityMetaData metaData = objectMapper.readValue(data, EntityMetaData.class);
		Map<String, String> uriVariables = new HashMap<>(1);
		uriVariables.put("type", metaData.getName());
		String url = "/entity/collection/{type}";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		doNothing().when(entityService).createEntityMetaData(isA(String.class), any(EntityMetaData.class));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(builder.buildAndExpand(uriVariables).toUriString())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("test","test"));
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(entityService).createEntityMetaData(isA(String.class), any(EntityMetaData.class));
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	@WithMockUser(roles="test")
	public void testCreateEntity() throws Exception {
		String data = getFile("entity/cricketer.json");
		Map<String, String> uriVariables = new HashMap<>(1);
		uriVariables.put("type", "cricketer");
		String url = "/entity/{type}";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		when(entityService.addEntity(isA(Entity.class))).thenReturn(UUID.randomUUID().toString());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(builder.buildAndExpand(uriVariables).toUriString())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("test","test"));
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(entityService).addEntity(isA(Entity.class));
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	@WithMockUser(roles="test")
	public void testUpdateEntity() throws Exception {
		String data = getFile("entity/cricketer.json");
		String url = "/entity/{type}/{id}";
		Map<String, String> uriVariables = new HashMap<>(2);
		uriVariables.put("type", "cricketer");
		uriVariables.put("id", UUID.randomUUID().toString());
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		
		doNothing().when(entityService).updateEntity(isA(String.class),isA(String.class), any(Entity.class));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(builder.buildAndExpand(uriVariables).toUriString())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(data)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("test","test"));
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(entityService).updateEntity(isA(String.class),isA(String.class), any(Entity.class));
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
	
	@Test
	@WithMockUser(roles="test")
	public void testGetEntity() throws Exception {
		String data = getFile("entity/cricketer.json");
		String url = "/entity/{type}/{id}";
		Map<String, String> uriVariables = new HashMap<>(2);
		uriVariables.put("type", "cricketer");
		uriVariables.put("id", UUID.randomUUID().toString());
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		
		when(entityService.getEntity(isA(String.class),isA(String.class))).thenReturn(data);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(builder.buildAndExpand(uriVariables).toUriString())
				.accept(MediaType.APPLICATION_JSON_VALUE).with(httpBasic("test","test"));
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(entityService).getEntity(isA(String.class),isA(String.class));
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	@WithMockUser(roles="test")
	public void testDeleteEntity() throws Exception {
		String url = "/entity/{type}/{id}";
		Map<String, String> uriVariables = new HashMap<>(2);
		uriVariables.put("type", "cricketer");
		uriVariables.put("id", UUID.randomUUID().toString());
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		
		doNothing().when(entityService).deleteEntity(isA(String.class),isA(String.class));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(builder.buildAndExpand(uriVariables).toUriString())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.with(httpBasic("test","test"));;
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		verify(entityService).deleteEntity(isA(String.class),isA(String.class));
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
	
	
}
