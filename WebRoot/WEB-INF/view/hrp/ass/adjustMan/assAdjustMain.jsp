
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var show_detail=0; 
    $(function ()
    {
        loadDict();//加载下拉框     
    	//加载数据
    	loadHead(null);	
    	showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
    });
    
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			
		}
		loadHead();
		//query();
	 }
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'creBegin_date',value:$("#creBegin_date").val()});//编制日期：开始日期
    	  grid.options.parms.push({name:'creEnd_date',value:$("#creEnd_date").val()});//编制日期：结束日期
    	  grid.options.parms.push({name:'adjust_code',value:$("#adjust_code").val()});//调价单编码
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()});//状态
    	  grid.options.parms.push({name:'adjBegin_date',value:$("#adjBegin_date").val()});//调价日期：开始日期
    	  grid.options.parms.push({name:'adjEnd_date',value:$("#adjEnd_date").val()});//调价日期：结束日期
    	  grid.options.parms.push({name:'note',value:$("#note").val()});
    	  //grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});//编制部门
    	 
    	//加载查询条件
    	grid.loadData(grid.where);
		
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#begin_date").val()!=""){
                		return rowdata.type_code.indexOf($("#begin_date").val()) > -1;	
                	}
                	if($("#end_date").val()!=""){
                		return rowdata.type_name.indexOf($("#end_date").val()) > -1;	
                	}
                	if($("#dept_id").val()!=""){
                		return rowdata.type_name.indexOf($("#dept_id").val()) > -1;	
                	}
                	if($("#state").val()!=""){
                		return rowdata.type_name.indexOf($("#state").val()) > -1;	
                	}
                	if($("#brif").val()!=""){
                		return rowdata.type_name.indexOf($("#brif").val()) > -1;	
                	}
                	if($("#pur_type").val()!=""){
                		return rowdata.type_name.indexOf($("#pur_type").val()) > -1;	
                	}
                	
        };
        return clause; 
    }

    function loadHead(){
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '调价单号', name: 'adjust_code', align: 'left',render:
    	                    	 function(rowdata,rowindex,value){
    		                    	 return '<a href=javascript:openUpdate("' 
    									+ rowdata.adjust_id
    									+','
    									+ rowdata.state
    									+ '")>'+rowdata.adjust_code+'</a>';

    	                     	 }
    						 },
    	                     { display: '编制日期', name: 'create_date', align: 'left'
    						 		},
    						 { display: '资产编码', name: 'ass_code', align: 'left',minWidth : 120},
    						 { display: '资产名称', name: 'ass_name', align: 'left',minWidth : 120},
    						 { display: '规格型号', name: 'ass_mondl', align: 'left',minWidth : 80
	  					 		},
	  					   { display: '计量单位', name: 'unit_name', align: 'left',minWidth : 80
						 		},
						   { display: '生产厂商', name: 'fac_name', align: 'left',minWidth : 100
								},
						   { display: '供应商', name: 'sup_name', align: 'left',minWidth : 100
								},
						   { display: '原计划价', name: 'old_price', align: 'right',minWidth : 80,render:
								function(rowdata){
									return formatNumber(rowdata.old_price,'4',1);
							   	} 
						   },
						   { display: '新计划价(E)', name: 'new_price', align: 'right',minWidth : 80,
								editor : {
									type : 'numberbox',
									precision : '${ass_05006}'
								},
								render : function(rowdata, rowindex, value) {
									rowdata.new_price = value == null ? "" : formatNumber(value, '${ass_05006}', 0);
									return value == null ? "" : formatNumber(value, '${ass_05006}', 1);
								}
						   },
						   { display: '调价原因(E)', name: 'adjust_reason', align: 'left',minWidth : 80,type: 'string', editor: { type: 'string'}
							}
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssAdjust.do?isCheck=true&show_detail=1',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     toolbar: { items: [
    	                     	{ text: '查询', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    	        				{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	        				{ line:true },
    	        				{ text: '审核', id:'check', click: itemclick,icon:'audit' },
    	        				{ line:true },
    	        				{ text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                     	]},
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    							openUpdate(
    									rowdata.adjust_id
    									+','
    									+ rowdata.state
    								);
    	    				} 
    	                   });
    	}else{
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '调价单号', name: 'adjust_code', align: 'left',render:
    	                    	 function(rowdata,rowindex,value){
    		                    	 return '<a href=javascript:openUpdate("' 
    									+ rowdata.adjust_id
    									+','
    									+ rowdata.state
    									+ '")>'+rowdata.adjust_code+'</a>';

    	                     	 }
    						 		},
    	                     { display: '编制日期', name: 'create_date', align: 'left'
    						 		},
    						 { display: '备注', name: 'note', align: 'left'
    						 		},
    						 { display: '状态', name: 'state', align: 'left',render:
    							 	function(rowdata){
    							 		if(rowdata.state == '0'){
    							 			return "未审核";
    							 		}else{
    							 			return "已审核";
    							 		}
    						 		}
    							 	},
    	                     { display: '制单人', name: 'make_name', align: 'left'
    						 		},
    						 { display: '审核人', name: 'checker_name', align: 'left'
    						 },
    						 { display: '调价日期', name: 'adjust_date', align: 'left'
    						 }
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssAdjust.do?isCheck=true&show_detail=0',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     toolbar: { items: [
    	                     	{ text: '查询', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    	        				{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	        				{ line:true },
    	        				{ text: '审核', id:'check', click: itemclick,icon:'audit' },
    	        				{ line:true },
    	        				{ text: '删除', id:'delete', click: itemclick,icon:'delete' }
    	                     	]},
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    							openUpdate(
    									rowdata.adjust_id
    									+','
    									+ rowdata.state
    								);
    	    				} 
    	                   });
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function itemclick(item){ 
        if(item.id){
            switch (item.id){
                case "add":
              		$.ligerDialog.open({url: 'assAdjustAddPage.do?isCheck=false',height: 500,top:1,width: 1100, title:'添加',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
               
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                    	 var Param =[];
                    	 var flag;
                         $(data).each(function (){
                        	if(this.state != '0'){
                        		flag = false;
                        	}
 							Param.push(
 							//表的主键
 							this.group_id+"@"+
 							this.hos_id+"@"+
 							this.copy_code+"@"+
 							this.adjust_id
 							)
                         });
                         if(flag == false){
                        	 $.ligerDialog.error('只有未审核状态单据允许删除');
                        	 return ;
                         }
                       $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBatchAssAdjust.do?paramVo="+Param,{},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        });  
                    }
                    return;
                    
                case "check":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                    	 var Param =[];
                    	 var flag;
                    	 $(data).each(function (){
                         	if(this.state != '0'){
                         		flag = false;
                         	}
  							Param.push(
  							//表的主键
  							this.group_id+"@"+
  							this.hos_id+"@"+
  							this.copy_code+"@"+
  							this.adjust_id+"@"+
  							this.state
  							)
                          });
                         if(flag == false){
                        	 $.ligerDialog.error('只能审核未审核的数据');
                        	 return ;
                         }
                       $.ligerDialog.confirm('审核将修改资产计划价,确定修改吗?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("updateAssAdjustState.do?paramVo="+Param,{},function (responseData){
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
    	
   
    function openUpdate(obj){
    	
    	var adjust_id = obj.split(",")[0];
    	
    	var state = obj.split(",")[1];
    	
		$.ligerDialog.open({ url : 'updateAssAdjustPage.do?isCheck=false&adjust_id='+adjust_id+'&state='+state,data:{}, height: 480,top:1,width: 1100, title:'修改',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,buttons: [ { text: '保存', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
    
    }
   
	 function loadDict(){
         //字典下拉框
		 autoCompleteByData("#state", matAdjust_state.Rows, "id", "text", true, true);//状态下拉框
		 autodate("#creBegin_date","yyyy-MM-dd","month_first");
		 autodate("#creEnd_date","yyyy-MM-dd","month_last");
		//返回当前年,当前月,当前日期,当前月第一天,当前月最后一天,上个月,上月第一天，上月最后一天
		var date = getCurrentDate();
        var aa = date.split(';');
       	thisDateB=aa[3];
       	thisDateE=aa[4];
       	lastDateB = aa[6];
       	lastDateE = aa[7];
		$("#begin_date").val(aa[3]);
   		$("#end_date").val(aa[4]);
	   		
   		$("#creBegin_date").ligerTextBox({width:160});
		$("#creEnd_date").ligerTextBox({width:160});
		$("#adjust_code").ligerTextBox({width:160});
		
		$("#state").ligerTextBox({width:160});
		$("#adjBegin_date").ligerTextBox({width:160});
		$("#adjEnd_date").ligerTextBox({width:160});
		
		$("#note").ligerTextBox({width:160});
		 
      }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  >
				编制日期：
	        </td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="creBegin_date" id="creBegin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  >
				至：
			</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="creEnd_date" id="creEnd_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">调价单号：</td>
            <td align="left" class="l-table-edit-td"><input name="adjust_code" type="text" id="adjust_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
        		<input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr>
        
         <tr>
        	<td align="right" class="l-table-edit-td"  >
				调价日期：
	        </td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="adjBegin_date" id="adjBegin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  >
				至：
			</td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="adjEnd_date" id="adjEnd_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td">
                <input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" />
             </td>
            <td align="left"></td>
           <!--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
            </td> -->
        </tr>  
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
