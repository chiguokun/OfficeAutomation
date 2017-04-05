<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>addUI page</title>
	
  </head>
  
  <body>
  
    <s:form action="roleAction_add">
  		<s:textfield name="name" label="岗位名称"></s:textfield>
  		<s:textarea name="description" label="岗位描述"></s:textarea>
  		<s:submit value="提交"></s:submit>
  		
  	</s:form>
  </body>
</html>
