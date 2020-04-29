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

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var rightgrid;
    
    var gridManager = null;
    
    var rightgridManager = null;
    
    var userUpdateStr;
    
    var para;
    
    var acc_time;
    
    $(function ()
    {
    	$("#layout1").ligerLayout({ leftWidth: 350,centerWidth:800,onLeftToggle: function (isColl){grid._onResize();},onRightToggle: function (isColl){}});
    	
    	loadDict(null);
    	loadHead(null);	//加载数据
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'wage_code',value:liger.get("wage_code1").getValue()});
    	grid.options.parms.push({name:'acc_year',value:acc_time.getValue().split(".")[0]});
    	grid.options.parms.push({name:'acc_month',value:acc_time.getValue().split(".")[1]});
    	grid.options.parms.push({name:'wage_item_code',value:liger.get("wage_item_code").getValue()});
    	grid.options.parms.push({name:'emp_kind',value:liger.get("emp_kind").getValue()});
    	grid.options.parms.push({name:'emp_code',value:liger.get("emp_code").getValue()});
    	grid.options.parms.push({name:'dept_code',value:liger.get("dept_code").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
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
    		
		columns:[ 
			{ display: '职工编码', name: 'emp_code', align: 'left', width : '12%'},
			{ display: '职工名称', name: 'emp_name', align: 'left', width : '12%'},
			{ display: '职工分类', name: 'kind_name', align: 'left', width : '12%' },
			{ display: '部门名称', name: 'dept_name', align: 'left', width : '12%' },
			{ display: '调整说明', name: 'note', align: 'left', width : '12%' },
			{ display: '调整前金额', name: 'old_money', align: 'right', width : '12%',
				render : function(rowData){
					return formatNumber(rowData.old_money, 2, 1);
				}
			},
			{ display: '调整比例', name: 'rate', align: 'left', width : '12%' },
			{ display: '当前金额', name: 'new_money', align: 'right', width : '12%',
				render : function(rowData){
					return formatNumber(rowData.old_money, 2, 1);
				}
			}
		],
           
           dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccWagePayModi.do',
                     
           width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
           
           selectRowButtonOnly:true,//heightDiff: -10,
           
           toolbar: { items: [
						
						{ text: '查询', id:'search', click: query,icon:'search' },
						
						{ line:true },
                     	
						{ text: '工资调整', id:'add', click: itemclick,icon:'add' },
                     	
						{ line:true }/* ,
                     	
						{ text: '导出', id:'delete', click: itemclick,icon:'delete' },
                     	
						{ line:true } *//* ,
						
						{ text: '打印', id:'delete', click: itemclick,icon:'delete' },
                     	
						{ line:true } */
						
    				]}
    	
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
        
        wagegrid = $("#wagegrid").ligerGrid({
            columns: [ 
                      { display: '工资套编码', name: 'wage_code', align: 'left',width:'48%'
 					 },
                      { display: '工资套名称', name: 'wage_name', align: 'left',width:'51%'
 					 }],
                      dataAction: 'server',dataType: 'server',usePager:false,url:'../accwage/queryAccWage.do',
                      width: '100%', height: '100%', rownumbers:true,
                      selectRowButtonOnly:true,allowUnSelectRow:true,
                      onSelectRow:function(data, rowindex, rowobj)
                      {
                    	  liger.get("wage_code1").setValue(data.wage_code);
                 		 
                     	  liger.get("wage_code1").setText(data.wage_name);
                     	  
                     	  $("#wage_code1").ligerTextBox({ disabled: true });
                     	  
                     	  $("#wage_code1").attr('disabled','disabled');
                      }
                    });

        wageGridManager  = $("#wagegrid").ligerGetGridManager();
        
       
    }
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	
                	var wage_code = liger.get("wage_code1").getValue();
                	
                	var wage_name= $("#wage_code1").val();
                	
                	var emp_id = liger.get("emp_code").getValue();
                	
                	var wage_item_code = liger.get("wage_item_code").getValue();
                	
                	var wage_item_name = $("#wage_item_code").val();
                	
                	var year_month = acc_time.getValue();
                	
                	if(wage_code ==""){
                		
                		$.ligerDialog.error('请选择工资套！');
                		
                    	return;
                		
                	}
                	
					if(year_month ==""){
                		
                		$.ligerDialog.error('请选择期间！');
                		
                    	return;
                		
                	}
					
					if(wage_item_code ==""){
                		
                		$.ligerDialog.error('请选择工资项目！');
                		
                    	return;
                		
                	}
                	
                	var parm = 'wage_code='+wage_code+'&emp_id='+emp_id+'&wage_item_code='+wage_item_code+'&year_month='+year_month+'&wage_item_name='+wage_item_name+'&wage_name='+wage_name;
                		
              		$.ligerDialog.open({url: 'accWagePayModiAddPage.do?'+ parm, height: 350,width: 335, title:'工资调整',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
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
							this.type_id +"@"+
							this.group_id +"@"+
							this.hos_id +"@"+
							this.copy_code 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccWageType.do",{ParamVo : ParamVo},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
            }   
        }
        
    }
    
    function openUpdate(obj,group_id,hos_id,copy_code){
    	
    	var vo = obj.split("|");
		var parm = "type_id="+
			vo[0]   +"&group_id="+ 
			vo[1]   +"&hos_id="+ 
			vo[2]   +"&copy_code="+ 
			vo[3];

    	$.ligerDialog.open({ url : 'accWageTypeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccBank(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    
    function loadDict(){
        //字典下拉框
         $("#wage_scheme").ligerComboBox({
              url:'../queryAccWageScheme.do',
              emptyText: '  ', 
              width: 160,//空行
              addRowButton: '添加',           //新增按钮
              addRowButtonClick: function ()  //新增事件
              {
            	  $.ligerDialog.open({
            		  url : 'accWageSchemeUpdate.do?isCheck=false',
            		  data:{}, height: 500,width: 1100, title:'方案维护',
            		  modal:true,showToggle:false,showMax:false,showMin: false,
            		  isResize:true,buttons: 
            			  [ {
            				  text: '确定', 
            				  onclick: function (item, dialog) { 
            					  dialog.frame.save(); },
            					  cls:'l-dialog-btn-highlight' }, 
            				{ text: '取消', 
            				  onclick: function (item, dialog) { 
            					  dialog.close(); } } ] });

              }
          });
		autocomplete("#wage_code","../queryAccWage.do?isCheck=false","id","text",true,true);
		
		//工资项目根据工资套改变而改变  
		autocomplete("#wage_code1","../queryAccWage.do?isCheck=false","id","text",true,true,'',true);
		
		$("#wage_code1").bind("change",function(){
    		
    		autocomplete("#wage_item_code","../queryAccWageItem.do?isCheck=false","id","text",true,true,{wage_code : liger.get("wage_code1").getValue()},true);
    		
		})
		
        autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
        
        autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false&is_stop=0","id","text",true,true);
        
        autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&is_stop=0","id","text",true,true);
		
    	
		
/* 		 $("#acc_time").ligerComboBox({
	          	url: '../queryYearMonth.do?isCheck=false',
	          	valueField: 'id',
	           	textField: 'text', 
	           	selectBoxWidth: 160,
	          	autocomplete: true,
	          	width: 160
	 	}); */
		 
	       //期间
	     	acc_time = $("#acc_time").etDatepicker({
	            view: "months",
	            minView: "months",
	            dateFormat: "yyyy.mm",
	            defaultDate: '${wage_year_month}'
	   		});
		 
		 var year_Month = '${wage_year_month}';
			
			if(year_Month.toString()=="000000"){
				
				var date=new Date;
				
				 var year=date.getFullYear();
				 
				 var month=date.getMonth()+1;
				 
				 month =(month<10 ? "0"+month:month); 
				 
				 year_Month = (year.toString()+month.toString());
				
			}
			
     } 
    
    function openWindow(){
    	
    	$.ligerDefaults.Filter.operators['string'] =
    	    $.ligerDefaults.Filter.operators['text'] =
    	    ["like" , "equal", "notequal", "startwith", "endwith" ];
    	
    	grid.showFilter();

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
			            <td align="left" class="l-table-edit-td" colspan="2"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:18}" /></td>
			            <td align="left"><input class="l-button" type="button" style="width: 80px;" id="query" value="查询(Q)" onclick="btn_query();" /></td>
			        </tr>
	   			  </table>
            	<div id="wagegrid"></div>
            </div>
	    <div position="center"  title="  ">
	    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
       	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_code1" type="text" id="wage_code1" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" style="width: 162px;"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资项目：</td>
            <td align="left" class="l-table-edit-td"><input name="wage_item_code " type="text" id="wage_item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_kind" type="text" id="emp_kind" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>  
    </table>
	    	<div id="maingrid"></div>
	    </div>
    </div>
	
	
</body>
</html>
