//ReverseAjaxOoo=false;// for close for test

function errhOae(msg, exc) {
  alert("Error message is: " + msg + " - Error Details: " + dwr.util.toDescriptiveString(exc, 2));
}

//dwr.engine.setErrorHandler(errhOae);


function fileChangeEvent()
{
//	alert("fileChangeEvent");
	elmtC.fileChangeEvent($("#filePath").val(),function(data)	
	{
	//	dwr.util.setValues (data);
	try{
		processJavaEX(data);
	
	}catch(e)
	{
	
		logx(e);
		//	logx(data.message);
		return;	
	}
		//if())
//		{
//		
//		return ;	
//		}
		var plt_s=secs2str(data.Duration);
		$('#playtime').timespinner('setValue',plt_s); 
		
	});
	
	
}
//function checkMtrlDescExist()
//{
//
//
//}
 function recvFilepath(file)
 {
	 	$("#filePath").val(file);
		fileChangeEvent();
 }
function formatDateTextO7(date)
{
return	date.Format("yyyy-MM-dd hh:mm:ss");
}


function editIniLoad()
{
var id=UrlParm.parm('id') ;	
if(id==undefined)  //add mod
	return;
	elmtC.findById(id,function(data)
	
	{
		dwr.util.setValues (data);
		
		var plt_s=secs2str(data.playtime);
		$('#playtime').timespinner('setValue',plt_s); 
	//	alert();
	try{
		if(data.effectieTime)
			$('#effectieTime').datetimebox('setValue',formatDateTextO7(data.effectieTime)); 
		//	alert(formatDateTextO7(data.effectieTime));
			if(data.failureTime)
				$('#failureTime').datetimebox('setValue',formatDateTextO7(data.failureTime)); 
			}catch(e){}
		//	$('#effectieTime').datetimebox('setValue',"2014-12-12 12:12:13"); 
	//	$('#dt').datetimebox('setValue', '6/1/2012 12:30:56')
	$("#pre_a_id").attr("href",$(boxObj).val());
	});
}
/*   ooo todox easyui-datetimebox check empty in js dwr ajax submit */
function alertErr(thisobj)
{
		var name=$(thisobj).attr("id");
					var timeboxVal=$("input[name='"+name+"']").val();
					
								
								
	  var ops=(  $(thisobj).attr("data-options") );
							var ops_json=eval( "({"+ops+"})" );
							if(ops_json.required)
							{
								if(timeboxVal=="")
								{
									//alertErr(this,timeboxVal);
									//throw "err";
								 
									alert(ops_json.missingMessage);
									throw "err";
								}
									
							}
}
function checkMtrlDescExist(checkParamName,backEvent)
{	
	//alert("check");
	var mp={};
	mp[checkParamName]=$("#"+checkParamName).val();
	mp.materialId=$("#materialId").val();
	elmtC.checkMtrlDescExist(mp ,function(data){
		if(data==true)
		{
			alert("素材名有重复");
			return;
		//	throw "素材名有重复";
		}
		backEvent();
			//alert("保存成功");
			//saveOkEvent();
		});	
}
//todox o7g validate
function save()
		{
		//	alert("保存成功");
		//	return;
		//	$("")
		//alert($(".ati-validatebox"));
			$(".ati-validatebox").each(function(){
                //  if($(this).attr("height")>高度)
				//ooL
			
				//alert(name);
			//	if(name=="effectieTime")
				//	alert($(this).hasClass("easyui-datetimebox"));
				 if($(this).hasClass("easyui-datetimebox"))
				 {
					 alertErr(this);
				 }
				
				
                            var ops=(  $(this).attr("data-options") );
							var ops_json=eval( "({"+ops+"})" );
							if(ops_json.required)
							{
								if( $(this).val()=="")
								{
									alert(ops_json.missingMessage);
									throw "err";
								}
									
							}
							
							
							 
              });//end each
			  //oab check rmt valide
			  $(".ati-validatebox").each(function(){
				    var ops=(  $(this).attr("validType") );
					//if(ops.startWith("remote")
					
					if(ops!=undefined)
					{
						ops=ops.substr(6);
					//	alert("validtype is:"+ops);
						var arr=eval( ops );
						var event_str=arr[0];
					//	 alert("event_str::"+event_str);
						var evHdl=eval(event_str);
						evHdl(arr[1],function()
						{
							//alert("resume");
							save_core();
						});
						
					}
					
			  });
		//	var jqmp=$("form").serializeArray();
	//$('#materialDescription').validatebox("disableValidation");	
//		var boo=$('#materialDescription').validatebox("isValid");
//	alert(boo	 );
//	if(!boo)
//	{
//		alert($('#materialDescription').validatebox('options').missingMessage);
//		return;
//	}
//return;
	//		alert("resume save op");
		
		
	}
function _save()
{
	
	var mp=	dwr.util.getValues("formx");
		//mp.effectieTime= $("input[name='effectieTime']").val();
	//	mp.failureTime= $("input[name='failureTime']").val();
		//if(1>3)
	//	mp.meth="";
		dwrC.execOb(mp ,function(){
			//alert("保存成功");
			saveOkEvent();
			});	
}
function saveOkEvent()
{
	//alert("");
	// if (confirm("保存成功，转往列表查看？？"))
//	alert(localflag);
	//return;
//	validOafa(true);
	 if (confirm("保存成功,确认关闭窗口？"))
		 window.close();
		 else 	  if(submitAftJmp)	 
	 	link_back();
}	


function form_load(){}	
$(function () {
 

// printLog("--o4a ini form ajax ret event:");
	//$('#formx').form({
//			success:function(data){
//					$.messager.alert('Info', data, 'info');
//			}
//	});
editIniLoad();
try{
 $("#materialType").val("1"); 
 $("#applicationType").val("1");
}catch(e)
{
	alert(e);
	}

//save();	
});
if(ReverseAjaxOoo)
dwr.engine.setActiveReverseAjax(true);

//alert(localflag);


