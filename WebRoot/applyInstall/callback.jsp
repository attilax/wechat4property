<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" %>

<%
String js=request.getParameter("callback")+"(\""+request.getParameter("param")+"\")";
 com.focusx.elmt.dwrReX.pushJs(js);
%>
..ok
  