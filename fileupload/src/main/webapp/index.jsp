<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2020-10-15
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>文件的上传</title>
</head>
<body>
<h1>上传文件：</h1>

<form action="${pageContext.request.contextPath}/file/fileupload" method="post" enctype="multipart/form-data">
    <input type="file" name="file"> <br>
    <input type="submit" value="上传文件">
</form>

<h1>测试下载文件处理</h1>
<a href="${pageContext.request.contextPath}/file/download?fileName=小黑.txt">小黑.txt</a>
<a href="${pageContext.request.contextPath}/file/download?fileName=init.sql">init.sql</a>
<a href="${pageContext.request.contextPath}/file/download?fileName=web.xml">web.xml</a>


</body>
</html>
