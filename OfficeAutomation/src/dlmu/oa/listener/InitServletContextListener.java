package dlmu.oa.listener;

import java.util.List;

import javax.faces.application.Application;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import dlmu.oa.domain.Privilege;
import dlmu.oa.service.PrivilegeService;

public class InitServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		
		//得到Servlet实例对象
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");
		
		//准备顶级集合（顶级菜单）
		List<Privilege> topPrivilegeList = privilegeService.getTopPrivilege();
		application.setAttribute("topPrivilegeList", topPrivilegeList);
		
		//准备所有权限名称的集合
		List<Privilege> allPrivilegeUrl = privilegeService.getAllPrivilegeUrl();
		application.setAttribute("allPrivilegeUrl", allPrivilegeUrl);
		
		System.out.println("----------------顶层数据已经准备好---------------");
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}


}
