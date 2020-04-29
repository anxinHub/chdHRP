<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var wage_code;
    
    var acc_year="";
    
    var select_disable=true;
    
    $(function ()
    {
    	loadDict(null);
    	
    	loadHead(null);	//加载数据
    	
    	$("#note").ligerTextBox({width:425});
    	
    	$("#layout1").ligerLayout({ leftWidth: 550,centerWidth:600,allowLeftCollapse:false,allowRightCollapse:false}); 
    
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        var acc_year = $("#acc_year").val();
        
        var wage_code = liger.get("wage_code").getValue();
        
        if(acc_year == "" ||wage_code==""){
        	
        	$.ligerDialog.error('工资套名称和会计年度为必填项！');
        	
        	return;
        	
        }
        
		if(acc_year == "0000"){
        	
        	$.ligerDialog.error('请选择有效日期进行查询！');
        	
        	return;
        	
        }
        
        grid.options.parms.push({name:'acc_year',value:acc_year}); 
        grid.options.parms.push({name:'wage_code',value:wage_code}); 
    	grid.options.parms.push({name:'kind_code',value:liger.get("emp_kind").getValue()}); 
    	grid.options.parms.push({name:'item_code',value:liger.get("wage_item").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    var sbf="";
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '工资项编码', name: 'item_code', align: 'left',width:'20%'
					 },
                     { display: '工资项名称', name: 'item_name', align: 'left',width:'30%'
					 },
					 { display: '职工分类', name: 'kind_name', align: 'left',width:'30%'
					 },
	                 { display: '公式', name: 'rate', align: 'center',width:'10%',render:function(rowdata){
	                	 
	                	 return "<a href=javascript:set_wage_cal('"+rowdata.item_code+"|"+rowdata.item_name+"|"+rowdata.kind_code+"|"+rowdata.wage_code+"|"+rowdata.wage_name+"|"+rowdata.kind_name+"|"+rowdata.cal_id+"|"+$.trim(rowdata.note)+"|"+rowdata.acc_year+"')>编辑<a/>";
	                 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccWageItemCalList.do',
                     width: '600px', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	{ text: '删除', id:'del', click: remove,icon:'delete' },
                     	{ line:true },
                     	{ text: '继承', id:'extend', click: extend,icon:'extend' },
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    
    function set_wage_cal(obj){
		
    	select_disable=false;
    	
    	$("#acc_wage").ligerTextBox({disabled:true,cancelable: false});
    	
    	var res = obj.split("|");
    	
		liger.get("acc_wage").setText(res[4]);
    	
    	liger.get("acc_wage").setValue(res[3]);

    	liger.get("item_name").setValue(res[0]);
    	
	    liger.get("item_name").setText(res[1]);
		
    	liger.get("kind_code").setValue(res[2]);
    	
	    liger.get("kind_code").setText(res[5]);
    	
	    $("#note").val(res[7]=="null"?" ":res[7]);
	    
		$("#kind_code").ligerTextBox({disabled:true,cancelable: false});
	    
	    $("#item_name").ligerTextBox({disabled:true,cancelable: false});
	    
	    $("#acc_wage").attr("disabled",'disabled');
	    
	    $("#kind_code").attr("disabled",'disabled');
	    
	    $("#item_name").attr("disabled",'disabled');
	    
	    var formData={
	    		
	    		cal_id:res[6]
	    };
	    
	    ajaxJsonObjectByUrl("queryAccWageItemCalById.do?isCheck=false",formData,function (responseData){
    		
	    	$("#cal_name").val(responseData.rest);
	    	
	    });
	    
	    acc_year=res[8];
	   	
    }
    
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
    	
        if (data.length == 0){
        	
        	$.ligerDialog.error('请选择行');
        	
        }else{
        	
            var ParamVo =[];
            
            $(data).each(function (){	

				ParamVo.push(
				//表的主键
				this.cal_id+"@"+$("#acc_year").val()
				)
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteAccWageItemCal.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    
	function extend(){
		
		var wage_code= liger.get("wage_code").getValue();
		
		var acc_year = $("#acc_year").val();
		
		if(wage_code==""||acc_year==""){
			
			$.ligerDialog.error('工资套名称和会计年度为必填项！');
			
			return;
		}
    	
	var paramData={
    			
    			wage_code:liger.get("wage_code").getValue(),//liger.get("wage_code").getValue(),
        		
    			year_month:$("#acc_year").val()
    			
    	}
    	
    	ajaxJsonObjectByUrl("extendAccWageItemCal.do?isCheck=false",paramData,function (responseData){
      		if(responseData.state=="true"){
      			query();
      		}
      	});
    	
    }
   
    function setWageItemCal(obj){
    	
    	var vo = obj.split("|");
		var parm = "wage_code="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : 'accWageItemCalUpdatePage.do?isCheck=false&' + parm,data:{}, height: 515,width: 800, title:'设置公式',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveWageItemCal(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
	function showWindow(){
    	
    	var cal_name=$("#cal_name").val();

    	$.ligerDialog.open({ 
    		url : "../accwagetaxcal/accWageTaxCalSetPage.do?cal_name="+escape(encodeURIComponent(cal_name))/* cal_name.replace(/\+/g, "%2B") */+"&wage_code="+ liger.get("acc_wage").getValue()+"&acc_year="+ $("#acc_year").val(),
    		data:{}, height: 520,width: 1150, title:'修改',modal:true,showToggle:false,showMax:false,
    		showMin: false,isResize:true,buttons: [ { 
    			text: '确定', onclick: function (item, dialog) { 
					
    				var cal_name =dialog.frame.saveAccWageTaxCal();
    				
    				$("#cal_name").val(cal_name.split("#")[0]);
    				$("#cal_eng").val(cal_name.split("#")[1]);
    				dialog.close();
    				},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    function saveData(){
    	
      var kind_code = liger.get("kind_code").getValue();
      
      var wage_code = liger.get("acc_wage").getValue();
      
      var item_code = liger.get("item_name").getValue();
      
      var kind_name =$("#kind_code").val();

      if(kind_name =="全部"){
    	 
    	  kind_code ="0";
    	  
      }
     
      if(kind_code ==""||wage_code==""||item_code==""||$.trim($("#cal_name").val())==""){
    	  
    	  $.ligerDialog.error('请完善信息！');
    	  
    	  return;
      }
      
      var formPara={
        		
	         wage_code:wage_code,
	         
	         item_code:item_code,
	         
	         kind_code :kind_code,
	         
	         acc_year :$("#acc_year").val(),
	         
	         cal_name:$.trim($("#cal_name").val()),
	         
	         note:$.trim($("#note").val()),
	         
	         cal_eng:$.trim($("#cal_eng").val())
	          
	       };
    	        
  	        ajaxJsonObjectByUrl("addAccWageItemCal.do",formPara,function(responseData){
  	        
  	        grid.reload();
  	        
  	        });
    	
    }
    
	function clearContent(){
		select_disable=true;
		liger.get("acc_wage").setValue("");
	    liger.get("acc_wage").setText("");
	    
	    liger.get("kind_code").setText("");
    	liger.get("kind_code").setValue("");
    	
    	liger.get("item_name").setText("");
    	liger.get("item_name").setValue("");
    	
    	$("#note").val("");
    	
    	$("#cal_name").val("");
    	
		$("#acc_wage").ligerTextBox({disabled:false,cancelable: false});
		
		$("#kind_code").ligerTextBox({disabled:false,cancelable: false});
	    
	    $("#item_name").ligerTextBox({disabled:false,cancelable: false});
	    
	    $("#acc_wage").removeAttr("disabled",'disabled');
	    
	    $("#kind_code").removeAttr("disabled",'disabled');
	    
	    $("#item_name").removeAttr("disabled",'disabled');
	    selectWage();
	}
	
    function loadDict(){
        //字典下拉框

		$("#item_name").ligerComboBox({width:160});
        
        selectWage(1);
        
	     autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);

	     var combobox=$("#kind_code").ligerComboBox({
    		
        	url: '../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0',
        	
            emptyText: '全部',
        	
            valueField: 'id',
        	
         	textField: 'text', 
         	
         	selectBoxWidth: 160,
         	
        	autocomplete: true,
        	
        	width: 160
        	
		  });
	     
	     var year_Month = '${wage_year_month}';
	        
	     $("#acc_year").val(year_Month.substring(0,4));
	     
	     $("#acc_year").ligerTextBox({width:160});
    }
    
    function selectWage(obj){
    	
    	autocompleteObj({
    		id:"#wage_code",
    		urlStr:"../queryAccWage.do?isCheck=false",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:true,
    		initvalue:null,
    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:function (value){
			
    			var fromData={
                		
                		wage_code:liger.get("wage_code").getValue(),
                		
                		item_cal:'2',
                		
                		acc_year:$("#acc_year").val()
                
            	}
    		
    			autocomplete("#wage_item","../queryAccWageItem.do?isCheck=false","id","text",true,true,fromData,false);
            
			}
    	});
	    
	     autocompleteObj({
	    		id:"#acc_wage",
	    		urlStr:"../queryAccWage.do?isCheck=false",
	    		valueField:"id",
	    		textField:"text",
	    		autocomplete:true,
	    		highLight:true,
	    		parmsStr:null,
	    		defaultSelect:false,
	    		initvalue:null,
	    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:function (value){
				//liger.get("item_name").getValue()==""&&
	    				
	    				if($('#item_name').prop('disabled')==false&&select_disable==true){
		    				
		    				var fromData={
			                		
			                		wage_code:liger.get("acc_wage").getValue()==""?"null":liger.get("acc_wage").getValue(),
			                		
			                		item_cal:'2',
			                		
			                		acc_year:$("#acc_year").val()
			                
			            	}
		    				
		    				liger.get("item_name").setText("");
		    		    	liger.get("item_name").setValue("");
			    		
			    			autocomplete("#item_name","../queryAccWageItem.do?isCheck=false","id","text",true,true,fromData,false);
			            
		    			}
	            
				}
	    	});
    }
     
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

		<div id="layout1">
            <div position="left" title="  ">
            
		            <table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>工资套名称：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:50}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>会计年度：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input class="Wdate"  name="acc_year" type="text" id="acc_year" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
			            <td align="left"></td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职 工 分 类：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_item" type="text" id="wage_item" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr>
	   			  </table>
            	<div id="maingrid"></div>
            </div>
            <div position="center"  title="  ">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
            		<tr>
            			<td><input name="cal_eng" type="hidden" id="cal_eng"  ltype="text"  /></td>
            		</tr>
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>工资套名称：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="acc_wage" type="text" id="acc_wage" ltype="text" /></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>工资项名称：</td>
			            <td align="left" class="l-table-edit-td" ><input name="item_name" type="text" id="item_name"  ltype="text" validate="{required:true,maxlength:50}" /></td>
			        </tr>
			        <tr>    
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>职 工 分 类：</td>
			            <td align="left" class="l-table-edit-td" ><input name="kind_code" type="text" id="kind_code"  ltype="text"  />
			            </td>
			        </tr>
			        <tr>    
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">公 式 说 明：</td>
			            <td align="left" class="l-table-edit-td" colspan="6"><input name="note" type="text" id="note"  ltype="text" validate="{required:true,maxlength:50}" />
			            </td>
			        </tr>
			        <tr>     
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-bottom: 360px">计 算 公 式：</td>
			            <td align="left" class="l-table-edit-td" colspan="6"><textarea class="liger-textarea" style="height: 350px;width: 100%;resize: none; " validate="{required:true}" name ="cal_name" id ="cal_name" disabled="disabled"></textarea></td>
			            <td align="left"></td>
			        </tr> 
			        <tr>   
			           <td align="left" style="padding:0px 0px 10px 190px;" colspan="2" class="l-table-edit-td">
			              <input  class="liger-button" style="width: 80px" type="button" value="重置"  onClick="clearContent();">
			           </td>
			           <td colspan="2" style="padding:0px 0px 10px 20px;">
			              <input class="liger-button" style="width: 80px" type="button" value="公式设置"  onClick="showWindow();">
			           </td>
			           <td colspan="2" style="padding:0px 0px 10px 20px;">
			              <input class="liger-button" style="width: 80px" type="button" value="保存"  onClick="saveData();">
			           </td>
			        </tr>
	   			  </table>
            </div>
        </div> 
</body>
</html>
