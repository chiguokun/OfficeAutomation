package dlmu.oa.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dlmu.oa.service.ProcessDefinitionService;

@Service
@Transactional
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

	@Resource
	private ProcessEngine processEngine;

	@Override
	public void deploy(ZipInputStream zipInputStream) {
		processEngine.getRepositoryService()//
				.createDeployment()//
				.addResourcesFromZipInputStream(zipInputStream)//
				.deploy();
	}

	@Override
	public List<ProcessDefinition> findAllLatestVersions() {
		// 查询所有的流程定义列表，把最新的版本排到最后面
		List<ProcessDefinition> allDefinitions = processEngine
				.getRepositoryService()//
				.createProcessDefinitionQuery()//
				.orderAsc(ProcessDefinitionQuery.PROPERTY_KEY)//
				.list();
		// 通过Map对同一个key的顺序覆盖，过滤出最新的版本
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : allDefinitions) {
			map.put(pd.getKey(), pd);
		}

		return new ArrayList<ProcessDefinition>(map.values());
	}

	@Override
	public void deleteByKey(String key) {
		// 查询出指定key的所有版本的流程定义
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
				.createProcessDefinitionQuery()//
				.processDefinitionKey(key)//
				.list();
		// 循环删除
		for (ProcessDefinition pd : list) {

			processEngine.getRepositoryService().deleteDeploymentCascade(
					pd.getDeploymentId());

		}

	}

	@Override
	public InputStream getProcessImageResourceAsStream(String id) {

		// 根据id取出对应流程定义对象
		ProcessDefinition pd = processEngine.getRepositoryService()//
				.createProcessDefinitionQuery()//
				.processDefinitionId(id)//
				.uniqueResult();
		//返回图片资源
		return processEngine.getRepositoryService().getResourceAsStream(pd.getDeploymentId(), pd.getImageResourceName());
	}

}
