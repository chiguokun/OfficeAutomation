<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>roleList page</title>
	
  </head>
  
  <body>
   
	<s:iterator value="#roleList">
		<%-- <s:property value="id"/>
		<s:property value="name"/>
		<s:property value="description"/> --%>
		<!-- 采用el表达式  -->
		${id },
		${name },
		${description}
		
	<%-- <a href="${pageContext.request.contextPath }/roleAction_delete.action?id=${id}" onclick="return windows.confirm('确定删除')">删除</a> --%>
		
	 <%-- 以下注释采用ognl表达式%{}，因为ognl表达式要结合Struts2使用，故用<s:a href>标签 --%> 
	 <s:a action="roleAction_delete?id=%{id}" onclick="return window.confirm('确定删除')">
	 	删除
	 </s:a>
	 <s:a action="roleAction_editUI?id=%{id}">修改</s:a>
	</br>
		
	</s:iterator>
	<s:a action="roleAction_addUI?id=%{id}">添加</s:a>
  </body>
</html>
