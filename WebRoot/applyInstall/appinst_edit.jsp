<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@page import="java.io.File"%>  
<html >
  <head>
  <script>var localflag=false;
 var   ReverseAjaxOoo=<%=!new File("C:\\NotReverseAjax").exists()%>;
 ReverseAjaxOoo=false;
 var submitAftJmp=false;
  </script>
   <%--@include file="../com.attilax/util/localflag.jsp" --%>
   
    <title></title>
    <!--set ie8设定要用IE8标准模式渲染网页，而不会使用兼容的模式。-->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />

    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    	<meta name="viewport" 	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=yes;" />

    <link rel="stylesheet" href="../resources/css/style.css" type="text/css"></link>
      <%@include file="../common/global_v.jsp" %>

   <script type="text/javascript" src="../com.attilax/time/time.js"></script>
   <script src="date.js"></script>
    <script type="text/javascript">
function link_back(){
						location.href = "elemt_list.jsp";
					}
    </script>
    
    
   <script language="JavaScript" type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>



 <link rel="stylesheet" type="text/css" href="../js/themes/default/easyui.css"/>  
    <link rel="stylesheet" type="text/css" href="../js/themes/icon.css"/>  
	    <link rel="stylesheet" type="text/css" href="../js/demo/demo.css" />


 
<script language="JavaScript" type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script language="JavaScript" type="text/javascript" src="../js/locale/easyui-lang-zh_CN.js"></script>
 <!--dwr start -->

  <script type='text/javascript' src='<%=path%>/dwr/engine.js'></script>
  <script type='text/javascript' src='<%=path%>/dwr/interface/elmtC.js'></script>
      <script type='text/javascript' src='<%=path%>/dwr/interface/dwrC.js'></script>
    <script type='text/javascript' src="<%=path%>/dwr/util.js"></script>
 <!-- /// dwr --> 
  
  <!--o7f-->
   <script type='text/javascript' src='js/req.js'></script>
   
   <!--o81  add tips-->
      <script type="text/javascript" src="<%=path%>/comm/Jquery notice JBox_files/jBox.js"></script>
         <link type="text/css" rel="stylesheet" href="<%=path%>/comm/Jquery notice JBox_files/c74f71-921494.css"/>
  
  <!--
      
  <script type="text/javascript" src="<%=path%>/comm/jBox/jquery.jBox-2.3.min.jsxkkkkk00000"></script>
 
  <script type="text/javascript" src="<%=path%>/comm/jBox/i18n/jquery.jBox-zh-CN.js"></script>
 
  <link type="text/css" rel="stylesheet" href="<%=path%>/comm/jBox/Skins/Green/jbox.css000"/>

  <link type="text/css" rel="stylesheet" href="<%=path%>/comm/jBox/Skins2/Blue/jbox.css——-"/>
  
  -->
  
  <!---->
     <%--@include file="../com.attilax/web/dwrExCptr.html" --%>
     
</head>
  
  <body leftmargin="0" topmargin="0" scrolling="no" style="padding:0px; margin:0px">
		<table width="100%" height="100%" id="center-table">
        <tr>
          <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="0%" align="left" class="f1"><img src="../resources/images/center-lt.gif" /></td>
              <td width="0%" align="right" class="f1"><img src="../resources/images/center-rt.gif" /></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td colspan="3" valign="top" class="f2"><div class="current">
            <div class="l">
              <div class="r">
                <p>&nbsp;</p>
              </div>
            </div>
          </div>
            <div class="form">
              <div class="l">
                <div class="r"><span>...</span> 
                <script type="text/javascript" src="../com.attilax/core/core.js"></script>
                </div>
              </div>
            </div>
            <form action="" method="post" enctype="multipart/form-data" name="formx" id="formx">
            <table width="100%" class="tab01" id="demo1_table">
            <tbody>
              <tr id="upTr" style="display:none">
                <td><span class="required">*</span>
                ：</td>
                <td class="tdr"><input name="openid" type="hidden" id="openid" value="atti_openid" />
                <input name="user_id" type="hidden" id="user_id" value="1" />
                <input name="_metadata" type="hidden" id="metadata" value='{
                "table":"mall_order_info","op":"insert"
                }' />
                <input name="_meth" type="hidden" id="_meth" value="orm" /></td>
              </tr>
              <tr>
                <td><span class="required"></span><span class="required">*</span>姓名：</td>
                <td class="tdr"><input type="text" name="consignee" id="consignee" class="input-text" /></td>
              </tr>
              <tr style="display:none0">
                <td><span class="required">*</span>手机号码：</td>
                <td class="tdr"> 
                <input type="text" name="mobile" id="mobile" class="input-text" /></td>
              </tr>
              <tr>
                <td><span class="required">*</span>地址：</td>
                <td class="tdr"><input name="address" type="text"   id="address" class="ati-validatebox input-text" data-options="required:true,novalidate:true,missingMessage:'描述不能为空的',deltaX:50,delay:20000" validType="remote['checkMtrlDescExist','materialDescription']"></td>
              </tr>
<tr>
  <td><span class="required"></span><span class="required">*</span>报装日期：</td>
  <td class="tdr"><input name="effectieTime" type="text" required  class="input-text easyui-datetimebox  ati-validatebox" id="effectieTime"  data-options="required:true,formatter:formatDateTextO7,missingMessage:'生效时间不能为空的'"  formatter="formatDateTextO7"></td>
</tr>
<tr>
  <td><span class="required">*</span>服务站：</td>
  <td class="tdr"><input name="materialDescription2" type="text"   id="materialDescription2" class="ati-validatebox input-text" data-options="required:true,novalidate:true,missingMessage:'描述不能为空的',deltaX:50,delay:20000" validtype="remote['checkMtrlDescExist','materialDescription']" /></td>
</tr>
<tr>
  <td colspan="2" class="tdb tdr"><table cellpadding=0 cellspacing=0 width="100%"  border="0"  class="toolBar">
    <tr>
      <td><table id="toolBar" border="0">
        <tr>
          <td class='coolButton' align='left' width='0'></td>
          <td class="coolButton" ><div style=''>
            <input  type='button' id='id_ev_add' name='ev_add' class='bt00' value='提交' onClick=" _save()" />
            </div></td>
          <td class="coolButton" ><div style=''>
            <input  type='button' id='id_ev_edit' name='ev_edit' class='bt00' value='取消' onclick="link_back()"  />
            </div></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
</tr>
</tbody>
</table>
</form>
</td>
</tr>
<tr>
  <td><table width="100%">
    <tr>
      <td align="left" class="f3"><img src="../resources/images/center-lb.gif" /></td>
      <td align="right" class="f3"><img src="../resources/images/center-rb.gif" /></td>
    </tr>
  </table></td>
</tr>
</table>
  <script src="elemt_edit.js" type="text/javascript"></script>
  <script >
$(function () {
	try{
$('#dbgW').window('close');
	}catch(e){}
});
</script>
</body>
</html>
