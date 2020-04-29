<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<%-- <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/> --%>
<jsp:include page="${path}/inc.jsp"/> 
<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var audit_state = {
			Rows : [ 
				{
					"id" : "-1",
					"text" : "请选择"
				},
				{
					"id" : "0",
					"text" : "否"
				},
				{
					"id" : "1",
					"text" : "是"
				}
			],
			Total : 3
	};

    $(function (){
    	
    	autodate("#acct_yearm","yyyymm");
    	
        loadDict();//加载下拉框
    	
    	loadHead(null);//加载数据
    	
    	$('#item_code').bind('change',function(){query();});
    });
    

    function  query(){//根据表字段进行添加查询条件
    	
    	grid.options.parms=[];grid.options.newPage=1;
        
        grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : '' }); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : '' }); 
    	
    	grid.options.parms.push({name:'item_code',value:liger.get("item_code").getValue()}); 
    	
		if(liger.get("is_audit").getValue() != "" && liger.get("is_audit").getValue() != "-1"){
			grid.options.parms.push({name:'is_audit',value:liger.get("is_audit").getValue()}); 
		}    	

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({  
           columns: [
				{ display: '科室编码', name: 'dept_code', align: 'left',width:200},    
				{ display: '科室名称', name: 'dept_name', align: 'left',width:200}, 
				{ display: '项目名称', name: 'item_name', align: 'left',width:200}, 
				/* { display: '奖金合计', name: 'bonus_money', align: 'left',width:200},   */   
				{ display: '是否审核', name: 'is_audit', align: 'left',width:80,render:
					function(rowdata){
						if(rowdata.is_audit == '0'){
							return "未审核";
						}
						
						if(rowdata.is_audit == '1'){   
							return "已审核";
						}
					}
				}/* ,
				{ display: '是否二次分配', name: 'is_grant', align: 'left',width:80,render:
					function(rowdata){
						if(rowdata.is_grant == '0'){
							return "未提交";
						}
						
						if(rowdata.is_grant == '1'){
							return "已提交";
						}
					}
				}  */
           ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmEmpBonusDataDeptState.do',
                     width: '100%',height: '100%',   checkbox: false,rownumbers:true,delayLoad:true,
                     alternatingRow:false,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                                     	{ text: '查询', id:'search', click: query,icon:'search' },
                                     	{ line:true }
                    ]},
        	 		rowAttrRender:function(rowdata,rowid){
        	 			if(rowdata.is_audit== '0' ){
        	 				//return "style='background:rgba(255, 0, 0, 0.31)'"
        	 				return "style='background:rgba(156, 39, 176, 0.69)'"
        	 			}
        	 			
        	 			if(rowdata.is_audit == '1'){
        	 				return "style='background:#8BC34A'"
        	 			}/* else if(rowdata.left_state==2){
        	 				return "style='background:rgba(156, 39, 176, 0.69)'"
        	 			} */
         			}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){//字典下拉框
    	
    	autoCompleteByData("#is_audit",audit_state.Rows,"id","text",true,true);
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	$("#acct_yearm").ligerTextBox({width:160 });
    	
    	autocomplete("#item_code","../queryItemAllDict.do?&isCheck=false","id","text",true,true,'',true);
    }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input name="acct_yearm" type="text" id="acct_yearm" class="Wdate" ltype="text"
				validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否审核：</td>
			<td align="left" class="l-table-edit-td"><input name="is_audit" type="text" id="is_audit" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="item_code" type="text" id="item_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>

</body>
</html>
