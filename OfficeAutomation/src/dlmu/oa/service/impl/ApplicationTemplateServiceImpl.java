package dlmu.oa.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;

import dlmu.oa.base.BaseDaoImpl;
import dlmu.oa.domain.ApplicationTemplate;
import dlmu.oa.domain.PageBean;
import dlmu.oa.service.ApplicationTemplateService;
import dlmu.oa.util.HqlHelper;

@Service
public class ApplicationTemplateServiceImpl extends BaseDaoImpl<ApplicationTemplate> implements ApplicationTemplateService {
	
	@Override
	public void delete(Long id) {
		//删除数据库记录
		ApplicationTemplate applicationTemplate = getById(id);
		getSession().delete(applicationTemplate);
		
		//同时删除文件
		File file = new File(applicationTemplate.getPath());
		if(file.exists())
		{
			file.delete();
		}
		
	}

}
