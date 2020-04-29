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
var tree;
var setting;
var groupicon = "<%=path %>/lib/ligerUI/skins/icons/communication.gif";
var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
var sheet=dialog.get('data').sheet;
var activeRowIndex = sheet.getActiveRowIndex();
var activeColumnIndex = sheet.getActiveColumnIndex();
var spreadNS=dialog.get('data').spreadNS;
//var comment = new spreadNS.Comment();
var commJson=null;
	$(function (){
    	$("#layout1").ligerLayout({ leftWidth: 210,allowLeftResize: true});
    	setting = {      
 		   		data: {simpleData: {enable: true},key: {title: "title"}},
 		   		treeNode:{open:true},
 		   		callback:{onClick:myClick}
 	 	};
    	mainform = $("form");
    	loadTree();
    	$("#treeDiv").css("height", $(window).height()-28);
    	$("#ele_code").ligerTextBox({ width:460,readonly: true });
    	$("#ele_note").ligerTextBox({ width:460,readonly: true });
    	$(':button').ligerButton({width:100});
    	
    });

	var eleSelCode;
	var eleSelParaArray;
    function loadTree(){
    	var para={
				//mod_code:$("#mod_code").val()
    	};
		ajaxJsonObjectByUrl("../querySuperReportEleByMod.do?isCheck=false",para,function(responseData){	
			  tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
			  formulaType=0;
			 
			  //根据批注加载参数信息
			  var commText=sheet.comments.get(activeRowIndex, activeColumnIndex);
			  if(commText){
				  $("#formula_name").val(commText.text());//公式说明
			  }
				
			  $("#formula").val(sheet.getFormula(activeRowIndex, activeColumnIndex));//公式
			  var formulaVal=$("#formula").val();
			  if(formulaVal==""){
				  return;
			  }
			 
			
			 
			  //系统元素,非计算元素公式
			  if(formulaVal.indexOf("RES(")!=-1){
				  formulaType=1;
				  //考虑公式可以嵌套多种组合，从\REP("后面的字符开始截取，格式：=REP("RF_ACC_QMYE","2","1","001","本年度","本期间","1001","RMB","false")+REP("RF_ACC_NCYE","2","1","212","本年度","RMB","1001")
				  var cellTempStr=formulaVal.substring(formulaVal.indexOf("RES(\"")+5,formulaVal.length);
				  eleSelCode=cellTempStr.substring(0,cellTempStr.indexOf("\""));//格式：RES("group_id")
				  
			  }else if(formulaVal.indexOf("REP(")!=-1){
			  
				  formulaType=2;
				  //考虑公式可以嵌套多种组合，从\REP("后面的字符开始截取，格式：=REP("RF_ACC_QMYE","2","1","001","本年度","本期间","1001","RMB","false")+REP("RF_ACC_NCYE","2","1","212","本年度","RMB","1001")
				  var cellTempStr=formulaVal.substring(formulaVal.indexOf("REP(\"")+5,formulaVal.length);
				  eleSelCode=cellTempStr.substring(0,cellTempStr.indexOf("\""));
				  
				  //默认回写第一个元素的参数值
				  var eleSelParaVal=cellTempStr.substring(cellTempStr.indexOf(",")+1,cellTempStr.indexOf(")"));
				  eleSelParaArray=eleSelParaVal.replace(/\"/g,"").split(",");//替换双引号，根据,号分隔
			  
			  }
			 
			  //默认选择元素节点（/默认选择公式里面的第一个元素）			 
			  var node=tree.getNodeByParam("id",eleSelCode);
			  tree.selectNode(node);
			  //setting.callback.onClick = myClick;
			 
			  myClick();
			  
		},false);
    }
    
    function myClick(){
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null || node.ele_type==null){
    		return;
    	}
    	
    	var para={
    			ele_code:node.id,
    			is_stop:0
    	};
    	ajaxJsonObjectByUrl("../querySuperReportParaByEle.do?isCheck=false",para,function(responseData){
    		//console.log(responseData.Rows)
    		
    		$("#ele_code").val(node.id);
    		$("#ele_note").val(node.note);
    		if(node.ele_type==1 || node.ele_type==2 || node.ele_type==3 || node.ele_type==4 || node.ele_type==5){
    			$("#formula_ele_div").css("display","block");
    			$("#system_ele_div").css("display","none");
    			loadForm(responseData.Rows,node);
    		}else{
    			$("#formula_ele_div").css("display","none");
    			$("#system_ele_div").css("display","block");
    			if(formManager!=null)formManager._setFields({});
    		}
		});
    	
    }
			  
    //添加元素到公式
    var formulaType=0;//1计算公式元素、999系统元素
    function myAdd(op){
    	
    	var node = tree.getSelectedNodes()[0];
    	if(node==null || node.pId==null || node.ele_type==null){
    		return;
    	}
    	
    	if($("#ele_code").val()==""){
    		return;
    	}
  
     	if(formulaType!=0 && formulaType!=node.formula_type){
    		$.ligerDialog.error("系统元素不能与其他类型的元素混合使用！");
	    	return false;
    	} 
    	
    	var paraValueStr="";
    	if(op=="sys"){
    		
    		paraValueStr="RES(\""+$("#ele_code").val()+"\")";
    		$("#formula").val(paraValueStr);
    		
    	}else{
    		
 /*    		$.each(formManager.form[0],function(i,obj){
    			if(obj.name=="subj_id_name"){
    				console.log(i,obj.value)	
    			}
    		}); */
    		
    		var data=formManager.getData();
        	if (mainform.valid()) {
        		
        		if($("#formula").val()!=""){
        			paraValueStr=op;
        		}
        		
        		var eleCode="";
        		if(node.ele_type==1){
        			//函数
        			eleCode="REP(\""+$("#ele_code").val()+"\"";
        		}else if(node.ele_type==2){
        			//存储过程
        			eleCode="REP(\""+$("#ele_code").val()+"\"";
        		}else if(node.ele_type==4){
        			//自定义SQL
        			eleCode="REP(\""+$("#ele_code").val()+"\"";
        		}else if(node.ele_type==5){
        			//系统函数
        			eleCode="REP(\""+$("#ele_code").val()+"\"";
        		}
        		
        		paraValueStr=paraValueStr+eleCode;
        		var isVlidate=true;
    		    for(var d in data){
    		    	
    		    	if(data[d]=="undefined"){
    		    		isVlidate=false;
    		    		return false;
    		    	}
    		    	isdh=true;
    		    	paraValueStr=paraValueStr+",\""+data[d]+"\"";
    		    }
    		    
    		    if(!isVlidate){
    		    	$.ligerDialog.error(d+"，没有获取到下拉框的值，确保该字典的SQL可正确执行！");
    		    	return false;
    		    }
    		   
    		    paraValueStr=paraValueStr+")";
    		   
        	}
        	$("#formula").val($("#formula").val()+paraValueStr);	
    	}
    		
    	formulaType=node.formula_type;	
		formulaNameSet();//更新公式说明
    }
    
    function myClear(){
    	formulaType=0;
    	$("#formula").val("");
    	$("#formula_name").val("");
    }
    
    //保存
    function mySave(){
		
		if($("#formula").val()==""){	
			$.ligerDialog.confirm('公式为空，是否继续保存？', function (yes) {
				if(yes){
					mySaveConfirm();
				}
			});
		}else{
			mySaveConfirm();
		}
    	
    }
    
    function mySaveConfirm(){
    	//系统元素、非计算公式，如果公式为空，不需要点击添加元素按钮，直接选择即可保存  
		var node = tree.getSelectedNodes()[0];
		
		var cellText = $("#formula").val().replace(/\\/g,"");
		
   	  	if(node!=null && node.pId!=null && node.ele_type!=null){
         	if(node.formula_type==1 && cellText==""){
         		$("#formula").val("RES(\""+node.id+"\")");
         		formulaNameSet();//更新公式说明
     		
     		}
         }
      	
      	//计算元素公式校验
      	/* if(cellText!="" && formulaType==2){
  	    	
  	    	var p = /REP\(.*?\)/gi;///^REP\(.*?\)/g;//替换括号里面的内容包括大括号
  	     	validata =cellText.replace(p, '1');
  	     	
  	    	try{
  	        	eval('('+validata.replace(/\REP/g,'')+')');
  	    	}catch(e){
  	    		$.ligerDialog.error("公式不合法！");
  	    		return;
  	    	}
  	    	
      	} */
      	
      	if(cellText!=""){
      		//插入批注
          	//comment.width(200);
      	    //comment.height(200);
            //comment.text($("#formula_name").val());
            //sheet.setComment(activeRowIndex, activeColumnIndex, comment);
      		sheet.comments.add(activeRowIndex, activeColumnIndex, $("#formula_name").val().replace(/\\/g,""));
      	}else{
      		//删除批注
      		sheet.comments.remove(activeRowIndex,activeColumnIndex);
      	}
      	
      	
  	  	//插入单元格文本
      	sheet.setText(activeRowIndex, activeColumnIndex, cellText);
  	  	//插入单元格公式
      	sheet.setFormula(activeRowIndex, activeColumnIndex,cellText);
      	frameElement.dialog.close();
    }
    
    
    //创建表单结构
    var isInitComBox=true;
    function loadForm(json,node){
    	
    	isInitComBox=true;
    	$.metadata.setType("attr", "validate");
    	mainform.validate({
           
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
    	
    	
    	var fieldJosn=[];
    	var filedJsonValue={};
    	//存初始化参数值
    	if(eleSelParaArray && eleSelCode==node.id){
	    	$.each(json,function(i,obj){
	    		filedJsonValue[obj.para_code]=eleSelParaArray[i];
	    	});
    	}
    	//fieldJosn.push({display:"元素编码",name:"ele_code",type: "text",width:460,readonly:true,newline: true,group:"元素信息",groupicon: groupicon});
    	//fieldJosn.push({display:"元素说明",name:"ele_note",type: "text",width:460,readonly:true,newline: true,group:"元素信息",groupicon: groupicon});
    	$.each(json,function(i,obj){
    		//console.log(i)
    		var isNewline=false;
    		var group="";
    		
    		if(i==0){
    			group="参数信息";
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
				isRequired=true;
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
    			
    			if(eleSelParaArray && eleSelCode==node.id && obj.para_type==4){
    				key=eleSelParaArray[i];
    			}
    			
    			if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    				$.each(paraJson.para.split(","),function(i,p){
    					paras=paras+p+"@"+(filedJsonValue[p]==undefined?"":filedJsonValue[p])+",";
    			    });
    				
    				paras=paras.substring(0,paras.length-1)
    			}
    			
    			fieldJosn.push({display:obj.para_name,name:obj.para_code,comboboxName: obj.para_code+"_name",type: "combobox",width:inputWidth,validate: {required:isRequired},
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
    					keySupport: true,
    					delayLoad: false,      //是否延时加载
    				    triggerToLoad : false, //是否在点击下拉按钮时加载
    				    async: false,
    					//data:[{id:"123",text:"aaaaaaaa"}],
    					onSuccess: function (data) {
    					
    						//没有公式的情况下，默认回写参数值，所以不需要默认加载第一个值了
    						if (data.length > 0 && isInitComBox && eleSelCode!=node.id) {
    							if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
    								this.setValue(data[0].id);
    								filedJsonValue[obj.para_code]=data[0].id;
    							}
    						} 
						},
    					onBeforeOpen: function (selectValue){
    		    			if(paraJson!=null && paraJson.para!=null && paraJson.para!=""){
    		    				var $combox=this;
    		    				$.each(paraJson.para.split(","),function(i,p){
    		    					$combox.setParm(p,$("[name="+p+"]").val());
    		    			    });
    		    				$combox.reload();
    	    						
    		    			}
    						
    						
    					},onBeforeSelect: function (selectValue){
    						//console.log(selectValue,$("[name="+obj.para_code+"]").val())
    						if(selectValue==$("[name="+obj.para_code+"]").val()){
    							return;
    						}
    						getParaObj(json,obj);
    						
    					},onSelected: function (selectValue){
    					
	    					
    					}},newline: isNewline,group:group,groupicon: groupicon});
    		}else if(obj.para_type==5){
    			//复选框
        		fieldJosn.push({display:obj.para_name,name:obj.para_code,type: "checkbox",width:20,validate: {required:isRequired},newline: isNewline,group:group,groupicon: groupicon});
    		}else{
    			//文本框
    			fieldJosn.push({display:obj.para_name,name:obj.para_code,type: "text",width:inputWidth,validate: {required:isRequired},newline: isNewline,group:group,groupicon: groupicon});
    		}
    		
    		
      });
       	
      formManager=mainform.ligerForm({
            inputWidth: 170, labelWidth: 80, space: 40,
            fields:fieldJosn
      });
      
      //加载元素值
      //formManager.setData({ele_code:node.id,ele_note:node.note});
      
      //有公式的情况下，默认回写参数值
    if(eleSelCode==node.id){
    	
    	 var count=0;
    	 var paraValJson="";
    	 var data=formManager.getData();
    	 for(var d in data){
		      if(d=="ele_code" || d=="ele_note"){
			   	continue;
			  }
		      if(eleSelParaArray[count]){
		    	  paraValJson=paraValJson+"\""+d+"\":\""+eleSelParaArray[count]+"\",";
		      }
		      count++;
		 }
    	 if(paraValJson!=""){
    		 paraValJson="{"+paraValJson.substr(0,paraValJson.length-1)+"}";
    		//var paraValeMap={"acc_month":"本期间","acc_year":"本年度","con_acc":0,"copy_code":"001","cur_code":"RMB","group_id":"2","hos_id":"1","subj_id":"1001"};
    		 formManager.setData(JSON.parse(paraValJson));
	     }
      
      }
      
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
	<div class="l-layout" id="layout1"  >
		<div position="left" title="报表元素">
		    <div  style="overflow:auto;" id="treeDiv">
		       	<ul class="ztree"  id="tree"></ul>
		    </div>
	    </div>
		
		<div position="center" id="centerReport" style="overflow: auto;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:5px;">元素编码：</td>
					<td align="left" class="l-table-edit-td" style="padding-left:16px;">
						<input type="text" id="ele_code" ltype="text"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:5px;">元素说明：</td>
					<td align="left" class="l-table-edit-td" style="padding-left:16px;">
						<input type="text" id="ele_note" ltype="text"/>
					</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" style="font-size:15px" style="display:none">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:5px;">公式说明：</td>
					<td align="left" class="l-table-edit-td" style="padding-left:16px;">
						<textarea id="formula_name" cols="100" rows="3" class="l-textarea" disabled readonly ltype="text" style="width:800px;font-size:15px"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:5px;">公式：</td>
					<td align="left" class="l-table-edit-td" style="padding-left:16px;">
						<textarea id="formula" cols="100" rows="5" class="l-textarea" style="width:800px;font-size:15px" onpropertychange="formulaNameSet()" oninput="formulaNameSet()"></textarea>
					</td>
				</tr>
			</table>
			<table>
				<tr>
				<td id="system_ele_div" style="display:none">
					&nbsp;&nbsp;<input type="button" value=" 添加元素（I） " accessKey="I" onclick="myAdd('sys');"/>
				</td>
				<td id="formula_ele_div" style="display:none">
					&nbsp;&nbsp;<input type="button" value=" 添加公式（+）" accessKey="+" onclick="myAdd('+');"/>
					&nbsp;&nbsp;<input type="button" value=" 添加公式（-）" accessKey="-" onclick="myAdd('-');"/>
					&nbsp;&nbsp;<input type="button" value=" 添加公式（*）" accessKey="*" onclick="myAdd('*');"/>
					&nbsp;&nbsp;<input type="button" value=" 添加公式（/）" accessKey="/" onclick="myAdd('/');"/>
				</td>
				<td>
					&nbsp;&nbsp;<input type="button" value="  保存（S）  " accessKey="S" onclick="mySave();"/>
					&nbsp;&nbsp;<input type="button" value="  清空（Q）  " accessKey="Q" onclick="myClear();"/>
					&nbsp;&nbsp;<input type="button" value="  关闭（C）  " accessKey="C" onclick="myClose();"/>
				</td>
				</tr>
			</table>
			
			<form></form>
		</div>
	</div>

</body>
</html>
