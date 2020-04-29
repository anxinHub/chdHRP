<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script >
    var grid;
    
 	var wagegrid;
    
    var wageGridManager = null;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var item_code_list=[];
    
    var is_jc_Data = [{ is_jc: 1, text: '是' }, { is_jc: 0, text: '否'}];
    
    var is_sum_Data = [{ is_sum: 1, text: '是' }, { is_sum: 0, text: '否'}];
    
    var is_stop_Data = [{ is_stop: 0, text: '启用'},{ is_stop: 1, text: '停用' }];
    
    var item_cal_dict = [{ item_cal: 1, text: '输入项'},{ item_cal: 2, text: '计算项' },{ item_cal: 3, text: '合并计算项'}];
    
    var data_type_dict = [{ data_type: 0, text: '数值型'},{ data_type: 1, text: '字符型' }];
    
    $(function ()
    {
    	
    	$("#layout1").ligerLayout({ leftWidth: 400,centerWidth:750,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){}});
    	
		loadDict();
		
		$("#item_code").ligerTextBox({width:160});
    	
		$("#item_name").ligerTextBox({width:160});
		$("#is_stop").ligerTextBox({width:160});
		loadHead(null);	//加载数据
    	
		loadHotkeys();
    	
    	//is_addRow();
    	
    });
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    		
    	grid.options.newPage=1;
    	
    	var wage_code = liger.get("wage_code1").getValue();
    	
		if(wage_code == ""){
    		
    		$.ligerDialog.error('请选择工资套！');
    		
    		return;
    		
    	}
    	
    	var acc_year=$("#acc_year").val();
    	
    	if(acc_year=="0000"||acc_year==null||acc_year==""){
    		
			$.ligerDialog.error('请选择有效时间进行查询！');
    		
    		return;
    	}
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'wage_code',value:wage_code}); 
    	
        grid.options.parms.push({name:'acc_year',value:acc_year}); 
        
        grid.options.parms.push({name:'is_stop',value:$("#is_stop").val()}); 
    	
        grid.options.parms.push({name:'item_code',value:$("#item_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	
    	is_addRow();

     }
    
  	//查询
    function  btn_query(){
    	
    	wagegrid.options.parms=[];
    		
    	wagegrid.options.newPage=1;
    	
    	var wage_code = liger.get("wage_code").getValue();
    	
        //根据表字段进行添加查询条件
        wagegrid.options.parms.push({name:'wage_code',value:wage_code}); 
    	
    	//加载查询条件
    	wagegrid.loadData(wagegrid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '工资项编码', name: 'item_code', align: 'left',editor: { type: 'text' },width:'10%'
					 
                     },
                     { display: '工资项名称', name: 'item_name', align: 'left',editor: { type: 'text' },width:'15%'
					 },
                     { display: '工资项类型', name: 'item_type', align: 'left',width:'10%',valueField : 'type_code',textField:'type_name',
						 editor: { 
							 type: 'select',
							 keySupport : true,
							 autocomplete : true, 
							 url:'../queryAccWageItemType.do?isCheck=false',
							 valueField :'type_code',
							 textField:'type_name',
							 onSuccess : function(data) {
								 /* if (initvalue != undefined && initvalue != '') {
									 this.setValue(initvalue);
								 	} */
							  }
						 }
					 },
					 { display: '工资项性质', name: 'item_nature', align: 'left',width:'10%',valueField : 'nature_code',textField:'nature_name',
						 editor: { 
							 type: 'select',
							 keySupport : true,
							 autocomplete : true, 
							 url:'../queryAccWageItemNature.do?isCheck=false',
							 valueField :'id',
							 textField:'text',
							 onSuccess : function(data) {
								 /* if (initvalue != undefined && initvalue != '') {
									 this.setValue(initvalue);
								 	} */
							  }
						 }
					 },
					 { display: '计算方式', name: 'item_cal', align: 'left',width:'8%',editor: { type: 'select', data: item_cal_dict, valueField: 'item_cal',keySupport : true,autocomplete : true },
		                    render: function (item)
		                    {
		                        if (item.item_cal == 2){
		                        	
		                        	return '计算项';
		                        	
		                        }else if(item.item_cal==1){
		                        	
		                        	return '输入项';
		                        }else if(item.item_cal==3){
		                        	
		                        	return '合并计算项';
		                        }
		                        
		                    }
					 },
					 { display: '数据类型', name: 'data_type', align: 'left',width:'8%',editor: { type: 'select', data: data_type_dict, valueField: 'data_type',keySupport : true,autocomplete : true },
		                    render: function (item)
		                    {
		                        if (item.data_type == 0){
		                        	
		                        	return '数值型';
		                        	
		                        }else if(item.data_type==1){
		                        	
		                        	return '字符型';
		                        }
		                        
		                    }
					 },
                     { display: '是否继承', name: 'is_jc', align: 'left',width:'8%',editor: { type: 'select', data: is_jc_Data, valueField: 'is_jc',keySupport : true,autocomplete : true },
		                    render: function (item)
		                    {
		                        if (item.is_jc == 1){
		                        	
		                        	return '是';
		                        	
		                        }else if(item.is_jc==0){
		                        	
		                        	return '否';
		                        }
		                        
		                    }
					 },
                     { display: '参与合计', name: 'is_sum', align: 'left',width:'8%',editor: { type: 'select', data: is_sum_Data, valueField: 'is_sum',keySupport : true,autocomplete : true },
		                    render: function (item)
		                    {
		                    	 if (item.is_sum == 1){
			                        	
			                        	return '是';
			                        }else if(item.is_sum==0){
			                        	return '否';
			                        }
		                    }
					 },
                     { display: '状态', name: 'is_stop', align: 'left',width:'8%',editor: { type: 'select', data: is_stop_Data, valueField: 'is_stop',keySupport : true,autocomplete : true },
		                    render: function (item)
		                    {
		                    	 if (item.is_stop == 1){
			                        	
			                        	return '停用';
			                        }else if(item.is_stop==0){
			                        	return '启用';
			                        }
		                    }
					 },{
						 display: '说明', name: 'note',width:'16%' ,align: 'left',editor: { type: 'text' },render:function(rowdata){
							 
							 return rowdata.note==null?"":rowdata.note;
						 }
					 }
                     ],
                    
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccWageItems.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,rowDraggable:true,
                     selectRowButtonOnly:true,enabledEdit: true,//heightDiff: -10,
 					 onChangeRow:onselectrow,
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    	                { line:true },
    	                { text: '保存（<u>A</u>）', id:'add', click: add_wageitem, icon:'save' },
    	                { line:true },
    	                { text: '继承（<u>A</u>）', id:'extend', click: extend_wageitem, icon:'extend' },
    	                { line:true },
    	                { text: '下载导入模板', id:'downTemplate', click: down_wageitem, icon:'down' },
    	                { line:true },
    	                { text: '导入（<u>D</u>）', id:'imp', click: imp_wageitem,icon:'import' },
    	                { line:true }/* ,
    	                { text: '保存序列', click: updateSortcodeByItemcode },
    	                { line:true } */
    				]},
    				lodop:{
    	         		title:"工资项目",
    	      			fn:{
    	          			debit:function(value){//借方
    	          				if(value == 0){return "";}
    	                 			else{return formatNumber(value, 2, 1);}
    	          			},
    	          			credit:function(value){//贷方
    	          				if(value == 0){return "";}
    	                			else{return formatNumber(value, 2, 1);}
    	         				},
    	         				end_os:function(value){//余额
    	      	   				 if(value==0){return "Q";}
    	      					 else{return formatNumber(value, 2, 1);}
    	        				}
    	          		}
    	      		}
    			
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        wagegrid = $("#wagegrid").ligerGrid({
            columns: [ 
                      { display: '工资套编码', name: 'wage_code', align: 'left',width:'29%'
 					 },
                      { display: '工资套名称', name: 'wage_name', align: 'left',width:'70%'
 					 }],
                      dataAction: 'server',dataType: 'server',usePager:false,url:'../accwage/queryAccWage.do',
                      width: '100%', height: '100%', rownumbers:true, checkbox: true,
                      selectRowButtonOnly:true,allowUnSelectRow:true,
                      isChecked:checkFirst,isSingleCheck:true,
                      onSelectRow:function(data, rowindex, rowobj)
                      {
                    	  liger.get("wage_code1").setValue(data.wage_code);
                 		 
                     	  liger.get("wage_code1").setText(data.wage_name);
                     	  
                     	  $("#wage_code1").ligerTextBox({ disabled: true });
                     	  
                     	  $("#wage_code1").attr('disabled','disabled');
                     	  
                     	  query();
                     	  
                     	 //is_addRow();
                      },
                      onDblClickRow:function(data, rowindex, rowobj) {
                    	  wageGridManager.select(rowindex)
                    	  liger.get("wage_code1").setValue(data.wage_code);
                 		 
                     	  liger.get("wage_code1").setText(data.wage_name);
                     	  
                     	  $("#wage_code1").ligerTextBox({ disabled: true });
                     	  
                     	  $("#wage_code1").attr('disabled','disabled');
                     	  
                     	  query();
                     	  
                     	 //is_addRow();
                      },lodop:{
    	         		title:"工资项目",
    	      			fn:{
    	          			debit:function(value){//借方
    	          				if(value == 0){return "";}
    	                 			else{return formatNumber(value, 2, 1);}
    	          			},
    	          			credit:function(value){//贷方
    	          				if(value == 0){return "";}
    	                			else{return formatNumber(value, 2, 1);}
    	         				},
    	         				end_os:function(value){//余额
    	      	   				 if(value==0){return "Q";}
    	      					 else{return formatNumber(value, 2, 1);}
    	        				}
    	          		}
    	      		}
                    });

        wageGridManager  = $("#wagegrid").ligerGetGridManager();
        
    }
    
    function onselectrow(e){
   	 var keycode = event.keyCode;
   	 if(keycode==13){
   		 var paramData={};
   		 paramData=e.record;
  			 var wage_code = liger.get("wage_code1").getValue();	 
     		 var acc_year = $("#acc_year").val();
   		 paramData.wage_code=wage_code;
   		 paramData.acc_year=acc_year;
   		 var dataAll=grid.getData();
   		 for(var j=0;j<dataAll.length;j++){
   			 if(dataAll[j].item_code!==undefined){
   				 if(e.record.__index!=j){
   					 if(e.record.item_code==dataAll[j].item_code||e.record.item_name==dataAll[j].item_name){
   						 //alert("***************")
   						 //console.log(e.record.item_code+"第二个if"+dataAll[j].item_code);
   						 setTimeout(function(){
   							$.ligerDialog.error("第"+(e.record.__index+1)+"行数据填写的工资项编码为【"+e.record.item_code+"】，该编码已存在，请修改数据后在保存");
   						 },10) 
   						
   						 return false;
   					 }
   				 }
   			 }
   		 }
      	  ajaxJsonObjectByUrl("updateoraddByItemcode.do?isCheck=false",paramData, function(responseData){
   		 
    	})  
   	 }
    }
    //打印回调方法
    function lodopPrint(){
    /* 	var accStr="不包含未记账"
       	if($("#is_state").prop("checked")){
       		accStr="包含未记账"
       	} */
    	var head="<table class='head' width='100%'><tr><td>日期："+$("#acc_year").val()+"</td>";
 		grid.options.lodop.head=head;
    }

    function updateSortcodeByItemcode(){
    	
    	var datalist=grid.getData()
    	
    	var	ParamVo=[];
    	
    	$(datalist).each(function (i,v){	
    		
			ParamVo.push(
			//表的主键
			this.item_code   +"@"+ 
			
			this.wage_code   +"@"+ 
			
			(i+1)*10 +"@"+ 
			
			this.acc_year
			
			);
      });
    	
    	ajaxJsonObjectByUrl("updateSortcodeByItemcode.do?isCheck=false",{ParamVo:ParamVo.toString()},function (responseData){
      		if(responseData.state=="true"){
      			query();
      		}
      	});
    	
    }
    
    function imp_wageitem(){
    	
    	$.ligerDialog.open({url: 'importAccWageItemPage.do?isCheck=false', height: 410,width: 960, title:'工资项目导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [  { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    	
    }
    
    function down_wageitem(){
    	
        location.href = "downTemplate.do?isCheck=false";

        return;
    	
    }
    
    function extend_wageitem(){
    	
    	var paramData={
    			
    			wage_code:liger.get("wage_code").getValue(),//liger.get("wage_code").getValue(),
        		
    	         acc_year:$("#acc_year").val()
    			
    	}
    	
    	ajaxJsonObjectByUrl("extendAccWageItems.do",paramData,function (responseData){
      		if(responseData.state=="true"){
      			query();
      		}
      	});
    	
    }
	function checkFirst(e){
	
		if(e.__index == 0){
			return true ;
		}
	}
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
 
		hotkeys('A', add_wageitem);
		hotkeys('D', remove);

		/*hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
  
  function add_wageitem(){
	
	 /*  $.ligerDialog.open({url: 'accWageItemsAddPage.do?isCheck=false', height: 400,width: 800, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccWageItems(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] }); */
	  
	 var data = gridManager.getCheckedRows();
	 var dataAll=grid.getData()
	 
	 for(var i=0;i<data.length;i++){
		 for(var j=0;j<dataAll.length;j++){
			 if(dataAll[j].item_code!==undefined){
				 if(data[i].__index!=j){
					 if(data[i].item_code===dataAll[j].item_code){
						 $.ligerDialog.error("第"+(data[i].__index+1)+"行数据填写的工资项编码为【"+data[i].item_code+"】，该编码已存在，请修改数据后在保存");
						 return false;
					 }
				 }
			 }
		 }
	 }
	
	 var wage_code = liger.get("wage_code1").getValue();
	 
	 var acc_year = $("#acc_year").val();
	 
	 /* if (data.length == 0){
      	
		 $.ligerDialog.error('请选择行');
      
	 }else{ */
         
		 var ParamVo =[];
         
		 $(dataAll).each(function (i,v){					
					
			 if(v.item_code!=null &&v.item_name!=null){
				 ParamVo.push(
							wage_code+"@"+
							acc_year   +"@"+ 
							v.item_code +"@"+
							v.item_name +"@"+
							v.item_nature +"@"+
							v.item_type +"@"+
							v.item_cal+"@"+
							v.is_jc+"@"+
							v.is_sum+"@"+
							v.is_stop+"@"+
							v.note+"@"+(i+1)*10+"@"+v.data_type
							);
				 //alert(JSON.stringify(ParamVo));
			 }
          });
		 if(ParamVo.length > 0){
			 ajaxJsonObjectByUrl("addAccWageItems.do",{ParamVo:ParamVo.toString()},function (responseData){
	          		if(responseData.state=="true"){
	          			query();
	          		}
	          	});
		 }else{
			 $.ligerDialog.warn('没有可保存数据.');
		 }
      //}
    
  }
  
  function remove(){
	  var wage_code = liger.get("wage_code1").getValue();
	  var data = gridManager.getCheckedRows();
      if (data.length == 0){
      	$.ligerDialog.error('请选择行');
      }else{
          var ParamVo =[];
          $(data).each(function (){					
				if(this.group_id!=""&& this.group_id != null){
					ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.acc_year   +"@"+ 
							wage_code  +"@"+ 
							this.item_code
							);
				}else{
					gridManager.deleteSelectedRow();
				}
          });
          if(ParamVo.length>0){
        	  $.ligerDialog.confirm('确定删除?', function (yes){
                	if(yes){
                    	ajaxJsonObjectByUrl("deleteAccWageItems.do?ParamVo="+ParamVo,{},function (responseData){
                    		if(responseData.state=="true"){
                    			query();
                    		}
                    	});
                	}
                }); 
          }
      }
      
   }
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                /* case "add":
              		$.ligerDialog.open({url: 'accWageItemsAddPage.do?isCheck=false', height: 400,width: 800, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccWageItems(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return; */
                case "modify":
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							//表的主键
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.acc_year   +"@"+ 
							this.item_id 
							);
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccWageItems.do",{ParamVo:ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "Excel":
                case "Word":
                case "PDF":
                case "TXT":
                case "XML":
                    $.ligerDialog.waitting('导出中，请稍候...');
                    setTimeout(function ()
                    {
                        $.ligerDialog.closeWaitting();
                        if (item.id == "Excel")
                            $.ligerDialog.success('导出成功');
                        else
                            $.ligerDialog.error('导出失败');
                    }, 1000);
                    return;
            }   
        }
        
    }
    function openUpdate(obj){
    	
		var vo = obj.split("|"); 
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&copy_code="+ 
			vo[2]   +"&acc_year="+ 
			vo[3]   +"&item_code="+ 
			vo[4]   +"&item_id="+ 
			vo[5]; 
    	$.ligerDialog.open({ url : 'accWageItemsUpdatePage.do?isCheck=false&' + parm,data:{}, height: 400,width: 800, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccWageItems(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
    	 
    	autocomplete("#wage_code1","../queryAccWage.do?isCheck=false","id","text",true,true);
            
        var year_Month = '${wage_year_month}';
        
        $("#acc_year").val(year_Month.substring(0,4));
     } 
    
    function is_addRow()
    {
    	
    	setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 2000);
    	
    } 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
            <div position="left" title="  ">
            
		            <table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套名称：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input   name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="left"><input class="l-button" type="button" style="width: 80px;" id="query" value="查询(Q)" onclick="btn_query();" /></td>
			        </tr>
			        
	   			  </table>
            	<div id="wagegrid"></div>
            </div>
            <div position="center"  title="  ">
            	<table cellpadding="0" cellspacing="0" class="l-table-edit">
			   	 	<tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套名称：</td>
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_code1" type="text" id="wage_code1" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
			            <td align="left" class="l-table-edit-td" ><input  class="Wdate" name="acc_year" type="text" id="acc_year" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项：</td>
			            <td align="left" class="l-table-edit-td" ><input name="item_code" type="text" id="item_code"  ltype="text" validate="{required:true,maxlength:18}" />
			            </td>
			        </tr>
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
			            <td align="left" class="l-table-edit-td">
			            <select id="is_stop" name="is_stop">
			            <option value="">全部</option>
			            <option value="0">启用</option>
			            <option value="1">停用</option>
			            </select>
			              
			            </td>
			        </tr>
	   			  </table>
	   		<div id="maingrid"></div>
            </div>
        </div> 
	
</body>
</html>
