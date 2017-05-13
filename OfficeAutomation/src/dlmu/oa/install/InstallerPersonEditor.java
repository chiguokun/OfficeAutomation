package dlmu.oa.install;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.domain.Privilege;
import dlmu.oa.domain.User;

@Component
public class InstallerPersonEditor {

	@Resource
	private SessionFactory sessionFactory;

	@Transactional
	public void install() {
		Session session = sessionFactory.getCurrentSession();

		// ===================================================
		/*// 一、超级管理员
		User user = new User();
		user.setName("超级管理员");
		user.setLoginName("admin");
		user.setPassword(DigestUtils.md5Hex("admin")); // 要使用MD5摘要
		session.save(user); // 保存
*/
		// ===================================================
		// 二、权限数据
		Privilege menu;
		
		menu = new Privilege("个人设置", null, "FUNC20077.gif", null);
		

		session.save(menu);
		
		session.save(new Privilege("个人信息", "personalAction_editImageUI", null, menu));
		session.save(new Privilege("密码修改", "personalAction_editPasswordUI", null, menu));
		

		// -------------------------

		

		// -------------------------

		
	}

	public static void main(String[] args) {
		System.out.println("正在执行安装...");

		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		InstallerPersonEditor installer = (InstallerPersonEditor) ac.getBean("installerPersonEditor");
		installer.install();

		System.out.println("== 安装完毕 ==");
	}
}
