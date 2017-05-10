package dlmu.oa.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private Log log = LogFactory.getLog(getClass());
	
	
	
	//@Test
	public void testLog() throws Exception{
		log.debug("debug信息");
		log.info("info信息");
		log.warn("warn信息");
		log.error("error信息");
	}
	
	// 测试SessionFactory
	//@Test
	public void testSessionFactory() throws Exception {
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}
	
	// 测试事务
	//@Test
	public void testTransaction() throws Exception {
		TestService testService  = (TestService) ac.getBean("testService");
		testService.saveTwoUsers();
	}
	
	// 测试事务
	@Test
		public void testSave_15() throws Exception {
			TestService testService  = (TestService) ac.getBean("testService");
			testService.saveUsers_15();
		}
}
