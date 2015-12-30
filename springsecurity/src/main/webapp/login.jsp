<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body onLoad="document.f.j_username.focus();">
<c:if test="${not empty param.login_error}">
    <font color="red">
        登录失败，请重试.<br/><br/>
        原因:<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
    </font>
</c:if>
<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
    <table>
        <tr>
            <td>用户名:</td>
            <td>
                <input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/>
            </td>
        </tr>
        <tr>
            <td>密     码:</td>
            <td><input type='password' name='j_password'></td>
        </tr>
        <tr>
            <td>
                <input type="checkbox" name="_spring_security_remember_me"></td><td>两周内自动登录
            </td>
        </tr>
        <tr>
            <td colspan='2' align="center">
                <input name="submit" type="submit">  
                <input name="reset" type="reset">
            </td>
        </tr>
    </table>
</form>
</body>
</html>