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
<style>
    .form {
        padding: 40px;
        box-sizing: border-box;
        margin-top: 20px;
        margin-bottom: 50px;
    }
    .button-group {
        margin: 10px 0;
        padding-left: 10px;
        text-align: center;
    }
    .button-group > button {
        padding: 2px 15px;
        min-width: 90px;
        height: 30px;
        box-sizing: border-box;
        margin-right: 40px;
        cursor: pointer;
        font-weight: bold;
        background: #e0edff;
        border: 1px solid #a3c0e8;
    }
    .button-group > button:hover {
        background: #ffbe76;
    }
</style>
<script>
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var is_zero=0;
    var sum =  0 ;
    $(function() {
        loadDict();
        loadHead();
        
        $("body").keydown(function() {
            if (event.keyCode == "9") {//keyCode=9是Tab
	              grid.addRowEdited({
	            	  /* proj_name: '' ,
	            	  source_name: '' , */
	            	  remain_amount:'0',
	            	  usable_amount:'0',
	            	  type_name: '' ,
	            	  level_name: '' ,
	            	  con_emp_name : '' ,
	            	  set_up_date : '',
	            	  complete_date : '',
	            	  pay_end_date : '',
	            	  sespend_date : '',
	            	  proj_state_name : ''
	          	  });
            }
        });
        
        $("#proj_id").change(function(){
			query();
		}) 
        $("#type_code").change(function(){
			query();
		})
        $("#level_code").change(function(){
			query();
		}) 
        $("#proj_state").change(function(){
			query();
		})  
        $("#con_emp_id").change(function(){
			query();
		})  
        $("#source_id").change(function(){
			query();
		})
    });
	
  //查询
    function  query(){
    	  grid.options.parms=[];
    	  grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()}); 
    	  grid.options.parms.push({name:'level_code',value:liger.get("level_code").getValue()});
    	  grid.options.parms.push({name:'proj_state',value:liger.get("proj_state").getValue()});
    	  grid.options.parms.push({name:'con_emp_id',value:liger.get("con_emp_id").getValue()});
    	  grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead () {
        grid = $("#maingrid").ligerGrid({
        	columns: [ 
 					 
                      { display: '项目名称', name: 'proj_name', align: 'left',width:"400"
 					 		},
                      { display: '资金来源', name: 'source_id',textField:'source_name', align: 'left',width:"92"
 					 		},
                      { display: '预算余额', name: 'remain_amount', align: 'right',width:"92",
 					 			render:function(rowdata,rowindex,value){
					 					return formatNumber(value,2,1);
				 				}
 					 		},
                      { display: '可用余额', name: 'usable_amount', align: 'right',width:"92",
 					 			render:function(rowdata,rowindex,value){
					 					return formatNumber(value,2,1);
				 				}
 					 		},
                      { display: '经费余额调整', name: 'remain_adj', align: 'right',width:"92",
 					 			render:function(rowdata,rowindex,value){
 					 				rowdata.remain_adj = rowdata.usable_amount;
				 					return formatNumber(rowdata.remain_adj,2,1);
				 				}
 					 		},
                      { display: '项目类型', name: 'type_name', align: 'left',width:"92"
 					 		},
                      { display: '项目级别', name: 'level_name', align: 'left',width:"92"
 					 		},
                      { display: '项目负责人', name: 'con_emp_name', align: 'left',width:"92"
 					 		},
                      { display: '立项日期', name: 'set_up_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'},width:"92"
 					 		},
                      { display: '结项日期', name: 'complete_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'},width:"92"
 					 		},
                      { display: '报销截止日期', name: 'pay_end_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'},width:"92"
 					 		},
                      { display: '中止日期', name: 'sespend_date', align: 'left',eidtor:{type:'date',format:'yyyy-MM-dd'},width:"92"
 					 		},
                      { display: '项目状态', name: 'state_name', align: 'left',width:"92"
 					 		}
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,isAddRow:false,url:'queryProjDetailByCondition.do?isCheck=false&is_zero='+is_zero,
                      width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit :true,
                      onBeforeShowData : loadBalance,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
						{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
						{ line:true },
                      	{ text: '确认（<u>S</u>）', id:'add', click: save, icon:'add' },
    	                { line:true },
		                { text: '关闭（<u>R</u>）', id:'close', click: close,icon:'close' },
		                { line:true }
     				]},
        });
        
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
  //计算调整资金
    function loadBalance (rowdata) {
 		$(rowdata.Rows).each(function(){
 			
 			this.con_emp_name = this.emp_name ;
 			this.proj_name = this.proj_id +','+this.proj_name;
 			sum += Number(this.usable_amount);
 		})
   		
   		$("#remain_adj_sum").val(sum);
        
    }
  	//保存
    function  save() {
    	var data = grid.getSelectedRows();
    	if(data.length>0){
    		parentFrameUse().grid.addRows(data); 
    		close();
    	}else{
    		$.ligerDialog.error('请选择数据!');
    	}
       
    }
    
  
	 //删除行
    function removeRows(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
        	gridManager.deleteRange(data);
        }
    }
  	
	 //是否显示零余额
    function changeColumn(){
   	 is_zero = $("#is_zero").is(":checked") ? 0 : 1 ;
   	 
   	 query();
   			 
   	}
	 
  	//关闭
    function close(){
    	frameElement.dialog.close();
    }	
    
    function loadDict () {
        //字典下拉框
    	//项目 下拉框
        autocomplete("#proj_id","../../queryProjName.do?isCheck=false","id","text",true,true,"",false,"",530);
      	//项目类型 下拉框
        autocomplete("#type_code","../../queryBudgProjType.do?isCheck=false","id","text",true,true);
      	//项目级别 下拉框
        autocomplete("#level_code","../../queryBudgProjLevel.do?isCheck=false","id","text",true,true);
      	//项目状态
        autocomplete("#proj_state","../../qureyProjStateSelect.do?isCheck=false","id","text",true,true);
      	//项目负责人 下拉框
        autocomplete("#con_emp_id","../../queryConEmpId.do?isCheck=false","id","text",true,true);
      	//资金来源 下拉框
        autocomplete("#source_id","../../queryBudgSource.do?isCheck=false","id","text",true,true);
      	
    	$("#proj_id").ligerTextBox({width:530});  
        $("#type_code").ligerTextBox({width:160});  
        $("#level_code").ligerTextBox({width:160}); 
        $("#proj_state").ligerTextBox({width:160});  
        $("#con_emp_id").ligerTextBox({width:160});  
        $("#source_id").ligerTextBox({width:160}); 
    }
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
 		
       <input name="adj_code" id="adj_code" type="hidden" value="系统生成" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}"/>
       <input name="remain_adj_sum" id="remain_adj_sum" type="hidden" value="${remain_adj_sum}" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}"/>

    <table class="form" cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        
            <td align="right" class="l-table-edit-td" colspan="1">项目名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3" >
                <input name="proj_id" id="proj_id" type="text" ltype="text" validate="{required:true,maxlength:40}"/>
            </td>
            <td align="right" class="l-table-edit-td" colspan="1" style="padding-left:50px;">项目类型：</td>
            <td align="left" class="l-table-edit-td" colspan="1">
                <input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}"/>
            </td>
            
            <td align="right" class="l-table-edit-td" colspan="1" style="padding-left:50px;">项目级别：</td>
            <td align="left" class="l-table-edit-td" colspan="1">
            	<input name="level_code" style = "width:200px" type="text" id="level_code"  ltype="text"  validate="{required:true,maxlength:20}" />
            </td>
        </tr>
        <tr>
            
            <td align="right" class="l-table-edit-td" colspan="1"> 项目负责人：</td>
            <td align="left" class="l-table-edit-td" colspan="1">
                <input name="con_emp_id" id="con_emp_id" type="text" ltype="text" validate="{required:true,maxlength:20}"/>
            </td>
            
            <td align="right" class="l-table-edit-td" colspan="1">项目状态：</td>
            <td align="left" class="l-table-edit-td" colspan="1">
                <input name="proj_state" type="text" id="proj_state" ltype="text"  validate="{required:true,maxlength:20}"/>
            </td>
            <td align="right" class="l-table-edit-td" colspan="1" style="padding-left:50px;">资金来源：</td>
            <td align="left" class="l-table-edit-td" colspan="1">
            	<input name="source_id" style = "width:200px" type="text" id="source_id"  ltype="text"  validate="{required:true,maxlength:20}" />
            </td>
            <td align="right" class="l-table-edit-td" colspan="1" style="padding-left:50px;">不显示零余额经费：</td>
            <td align="left" class="l-table-edit-td" colspan="1">
            	<input name="is_zero" type="checkbox" id="is_zero" ltype="text" onclick="changeColumn()" checked="checked" />
            </td>
        </tr>
    </table>

   <div id="maingrid"></div>
</body>
</html>
