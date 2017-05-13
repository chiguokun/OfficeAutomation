package dlmu.oa.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;

public interface ProcessDefinitionService {
	
	/**
	 * 查询所有最新版本
	 * @return
	 */
	List<ProcessDefinition> findAllLatestVersions();
	
	/**
	 * 删除对应key的所有流程定义
	 * @param key
	 */
	void deleteByKey(String key);
	
	/**
	 * 部署流程定义(zip)
	 * @param zipInputStream
	 */
	void deploy(ZipInputStream zipInputStream);

	/**
	 * 获取流程定义的图片资源流
	 * @param id
	 * @return
	 */
	InputStream getProcessImageResourceAsStream(String id);

}
