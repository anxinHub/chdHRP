<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<link href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript" ></script>
<script src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript" ></script>


<script type="text/javascript">
	var mainform=null;
	var formManager=null;
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var dsNames='${dsNames}';
	var ds_code = dialog.get('data').ds_code; 
	var sheet=dialog.get('data').sheet;
	var spreadNS=dialog.get('data').spreadNS;
	//var comment = new spreadNS.Comment();
	var commJson=null;
	var eleSelParaArray;
	$(function (){
    	mainform = $("form");
    	$(':button').ligerButton({width:100});
	    var dsCodes=dsNames.split(",");
	    for(var m=0;m<dsCodes.length-1;m++){
	    	if(dsCodes[m]!="")
	    		getData(dsCodes[m]);
	    }
    });
    
    function getData(dscode){
    	var para={
    			ds_code: dscode,
    			is_stop: 0,
    			para_tm:1
    	};
    	ajaxJsonObjectByUrl("../querySuperReportParaByDs.do?isCheck=false",para,function(responseData){
    		loadForm(responseData.Rows,dscode);
		});
    	
    }
    
    //保存
    function mySave(){
			$.ligerDialog.confirm('是否保存？', function (yes) {
				if(yes){
					mySaveConfirm();
				}
			});
    }
    
    function mySaveConfirm(){
    	var data = formManager.getData();
    	 var params=JSON.stringify(data);
    	 var para={
     			params: params
     	};
    	 ajaxJsonObjectByUrl("../saveSuperReportDsPara.do?isCheck=false",para,function(data){
     		
 		});
    	
    	 
      
    }
    
    
    //创建表单结构
    var isInitComBox=true;
    var fieldJosn=[];
	var filedJsonValue={};
    
    function loadForm(json,dscode){
    	debugger;
    	isInitComBox=true;
    	$.metadata.setType("attr", "validate");
    	mainform.validate({
            //调试状态，不会提交数据的
            debug: false,
            errorPlacement: function (lable, element)
            {

                if (element.hasClass("l-textarea"))
                {
                    element.addClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field"))
                {
                    element.parent().addClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
                $(element).attr("title", lable.html()).ligerTip();
            },
            success: function (lable)
            {
                var element = $("#" + lable.attr("for"));
                if (element.hasClass("l-textarea"))
                {
                    element.removeClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field"))
                {
                    element.parent().removeClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
            },
            submitHandler: function ()
            {
                alert("Submitted!");
            }
        }); 
    	
    	
		
    	//存初始化参数值
    	if(eleSelParaArray){
	    	$.each(json,function(i,obj){
	    		filedJsonValue[obj.para_code + "@@" + dscode]=eleSelParaArray[i];
	    	});
    	}
    	//fieldJosn.push({display:"元素编码",name:"ele_code",type: "text",width:460,readonly:true,newline: true,group:"元素信息",groupicon: groupicon});
    	//fieldJosn.push({display:"元素说明",name:"ele_note",type: "text",width:460,readonly:true,newline: true,group:"元素信息",groupicon: groupicon});
    	$.each(json,function(i,obj){
    		var isNewline=false;
    		var group="";
    		
    		if(i==0){
    			group="参数信息"+dscode;
    		}
    		if(i%2==0){
    			isNewline=true;
    		}

			var paraJson=JSON.parse(obj.para_json);
			var inputWidth=170; 
			var isRequired=false;
			//定义宽度
			if(paraJson!=null && paraJson.width!=null && paraJson.width!=""){
				inputWidth=paraJson.width;
			}
			//是否必填
			if(paraJson!=null && paraJson.required!=null && paraJson.required=="true"){
				isRequired=false;
			}
    		/*if(obj.para_type==3){
    			//下拉框
    			var paraJsoin=JSON.parse(obj.para_json);
    			if(paraJsoin!=null){
    				//指定下拉框的options
    				if(paraJsoin.dictionary!=null){
    					fieldJosn.push({display:obj.para_name,name:obj.para_code,type:"select",ltype: "autocomplete",dictionary:paraJsoin.dictionary,
    						editor: {
    							onSuccess : function (data){
    	    						if(data.length >0 ){
    	    							this.setValue(data[0].id);
    	    						
    	    						}
    	    					}
    						},newline: newLine,group:group,groupicon: groupicon});
    				}
    			}else{
    				fieldJosn.push({display:obj.para_name,name:obj.para_code,type:"select",ltype: "autocomplete",dictionary: "001|002|003|004",newline: newLine,group:group,groupicon: groupicon});
    			}
    			
    		}else */
    		
    		if(obj.para_type==3 || obj.para_type==4){
    			//下拉框、检索框
    			var key="";
    			var paras="";
    			
    			if(eleSelParaArray && obj.para_type==4){
    				key=eleSelParaArray[i];
    			}
    			if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    				$.each(paraJson.para.split(","),function(i,p){
    					paras=paras+p+"@"+(filedJsonValue[p+"#"+dscode+"@@"+dscode]==undefined?"":filedJsonValue[p+"#"+dscode+"@@"+dscode])+",";
    			    });
    				
    				paras=paras.substring(0,paras.length-1)
    			}
    			fieldJosn.push({display:obj.para_name,name:obj.para_code,comboboxName:obj.para_code+"_name",type: "combobox",width:inputWidth,validate: {required:isRequired},
    				editor: {
    					parms : {dict_code:obj.dict_code,key:key,paras:paras},
    					url : "../querySuperReportParaSelectData.do?isCheck=false",
    					valueField : "id",
    					textField : "text",
    					selectBoxWidth: inputWidth,
    					setTextBySource:true,
    					//width: 300,
    					autocomplete : true,
    					highLight : true,
    					keySupport:true,
    					keySupport:true,
    					cancelable:false,
    					delayLoad: false,      //是否延时加载
    				    triggerToLoad : false, //是否在点击下拉按钮时加载
    				    async:false,
    					//data:[{id:"123",text:"aaaaaaaa"}],
    					onSuccess: function (data) {
    						debugger;
    						//没有公式的情况下，默认回写参数值，所以不需要默认加载第一个值了
    						if (data.length > 0 && isInitComBox ) {
    							if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
    								this.setValue(obj.para_value);
    								filedJsonValue[obj.para_code+"@@"+dscode]=data[0].id;
    							}
    						}else {
    							this.setValue(obj.para_value);
    							this.setText(obj.para_value);
    						}
						},
    					onBeforeOpen: function (selectValue){
    		    			if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    		    				var $combox=this;
    		    				$.each(paraJson.para.split(","),function(i,p){
    		    					$combox.setParm(p,$("[name="+p+"#"+dscode+"]").val());
    		    			    });
    		    				$combox.reload();
    		    			}   						
    						
    					},onBeforeSelect: function (selectValue){
    						if(selectValue==$("[name="+obj.para_code+"]").val()){
    							return;
    						}
    						filedJsonValue[obj.para_code+"@@"+dscode]=selectValue;
    						if(!isInitComBox)
    							getParaObj(json,obj);
    						
    					},onSelected: function (selectValue){
    					
	    					
    					}},newline: isNewline,group:group});
    			
    			
    		}else if(obj.para_type==5){
    			//复选框
        		fieldJosn.push({display:obj.para_name,name:obj.para_code,type: "checkbox",width:20,validate: {required:isRequired},newline: isNewline,group:group});
    		}else{
    			//文本框
    			fieldJosn.push({display:obj.para_name,name:obj.para_code,type: "text",width:inputWidth,validate: {required:isRequired},newline: isNewline,group:group});
    		}
    		
      });
       	
      formManager=mainform.ligerForm({
            inputWidth: 170, labelWidth: 80, space: 40,
            fields:fieldJosn
	});
      
      isInitComBox=false;
    }
    
    //根据当前参数编码，查找影响的级联，并且清空数据。
    function getParaObj(json,obj){
    	$.each(json,function(i,j){
    		var paraJson=JSON.parse(j.para_json);
    		if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    			$.each(paraJson.para.split(","),function(z,p){
    				if(p==obj.para_code){
    					if($("[name="+j.para_code+"]").val()=="本医院" || $("[name="+j.para_code+"]").val()=="本账套" 
    							|| $("[name="+j.para_code+"]").val()=="本年度" || $("[name="+j.para_code+"]").val()=="RMB"){
    						return true;
    					}
    			
    					$("[name="+j.para_code+"]").val("");
    					$("[name="+j.para_code+"_name]").val("");
    				}
    		    });
    		}
    	});
    }
    
   //更新公式说明 
   function formulaNameSet(){
	   var cellText = $("#formula").val();
	 	if(cellText==""){
	 		return;
	 	}
	 //   var p = /\(.*?\)/g;//替换括号里面的内容包括括号
	 //   cellText = $("#formula").val().replace(p, '');
	   	//取name
	   	var nodes = tree.getNodes();
	   	var nodeArray=tree.transformToArray(nodes);
	   
	   	for(var i in nodeArray){
	   		if(nodeArray[i].ele_type!=null){
	       		cellText=cellText.replace(new RegExp(nodeArray[i].id, 'g'),nodeArray[i].name);	
	   		}
	   	}
	   	
	   	$("#formula_name").val(cellText);
   }
   
   function myClose(){
   	dialog.close();
   }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input id="mod_code" type="text"  value="${mod_code}" style="display:none"/>
		
	<div id="centerReport" style="overflow: auto;">
		<table>
			<td><br><br></br>
				&nbsp;&nbsp;<input type="button" value="  保存（S）  " accessKey="S" onclick="mySave();"/>
				&nbsp;&nbsp;<input type="button" value="  清空（Q）  " accessKey="Q" onclick="myClear();"/>
				&nbsp;&nbsp;<input type="button" value="  关闭（C）  " accessKey="C" onclick="myClose();"/>
			</td>
			</tr>
		</table>
		
		<form></form>
	</div>

</body>
</html>
