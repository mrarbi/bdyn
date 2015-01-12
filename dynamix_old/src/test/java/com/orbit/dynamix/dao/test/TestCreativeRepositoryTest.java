package com.orbit.dynamix.dao.test;

import gui.ava.html.image.generator.HtmlImageGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.orbit.dynamix.service.ICreativeService;
import com.orbit.dynamix.vo.CreativeVO;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations={"file:src/main/resources/spring-context.xml"})
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
// @TransactionConfiguration(transactionManager="txManager",
// defaultRollback=true)
@ActiveProfiles("test")
public class TestCreativeRepositoryTest {
	@Autowired
	ICreativeService creativeService;

	@Test
	public void tstFindClientByNom() {
		// List<Creative> list = creativeRepository.findAll();
		// Assert.assertTrue(list.size()>0);
		try {
			CreativeVO creative = creativeService.getCreativeVO(2);
			System.out.println(creative.getCreativeId());
			
			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
			imageGenerator.loadHtml(creative.getText());
//			imageGenerator.loadUrl("http://dev05.orbit-interactive.fr/adops/mailorama/mailorama.html");
//			imageGenerator.loadUrl("https://www.google.co.ma/");
			imageGenerator.saveAsImage("c:\\creative.png");
//			imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("this is a test with Junit");
	}
}
