<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<style type="text/css">
.ccc .l-grid-row-cell{background: #F1D3F7;}
</style>
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    
    //页面初始化
    $(function (){
    	
    	$("#acct_yearm").ligerTextBox({width:160 });autodate("#acct_yearm","yyyymm");
        loadDict();//加载字典
    	loadHead(null);//加载数据
    	
    	
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
    });
    
    //查询
    function  query(){
    	
        //根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'target_code',value:liger.get("target_code").getValue()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[0] : ''});
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue() ? liger.get("dept_id").getValue().split(",")[1] : ''}); 
    	grid.options.parms.push({name:'dept_kind_code',value:liger.get("dept_kind_code").getValue()}); 
    	grid.options.parms.push({name:'is_audit',value:$("#is_audit").val()});
    	
    	grid.options.parms.push({name:'is_show_zero',value:${is_show_zero} == true ? 1 : 0 }); 
    	
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    }
	
    //加载grid
	function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
            	{ display: '科室名称', name: 'dept_name', align: 'left',frozen:true},
            	{ display: '指标编码', name: 'target_code', align: 'left'},
				{ display: '指标名称', name: 'target_name', align: 'left'},
            	{ display: '指标值', name: 'target_value', align: 'right',editor:{type : 'float'}},
                { display: '状态', name: 'is_audit', align: 'left',width:80,render: 
                	function (rowdata, rowindex, value){
                    	if(rowdata.dept_name == "合计"){
                    		return "" ; 
                    	}
                    	
                    	if(rowdata.is_audit == 1){
                      		return "审核";
                      	}
                    		 
                    	if(rowdata.is_audit == 2){
                      		return "";
                      	}else{
                      		return "未审核";
                      	} 
  			        }
				},
                { display: '审核人', name: 'check_name', align: 'left'},
                { display: '审核时间', name: 'audit_time', align: 'left',render: 
                	function (rowdata, rowindex, value){
                    	
                		if(rowdata.is_audit == 2){
                      		return "";
                      	}else{
                      			 
                      		return rowdata.audit_time;
                      	} 
  			    	}
				}
                ],dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptTargetDataReport.do',
                width: '100%',height: '100%',   checkbox: false,rownumbers:true,delayLoad:true,checkBoxDisplay: isCheckDisplay,
                rowClsRender:  function (rowdata,rowid){
		        	if (rowdata.is_audit == 1){
		            	return "";
		            }else if(rowdata.is_audit == 2){
		                return "";
		            }else{
		                return "ccc";
		            }
		        }
        });

        gridManager = $("#maingrid").ligerGetGridManager();
        formatTrueFalse();
    }
    
    
	//工具栏
	function toolbar(){
      	var obj = [];
      	
      	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
      	obj.push({ line:true });
      	
      	obj.push({ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' });
      	obj.push({ line:true });
      	
      	$("#toptoolbar").ligerToolBar({ items: obj});
	}
 	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
		hotkeys('P',print);
	}
    
    
	function print() {

		if (grid.getData().length == 0) {
			$.ligerDialog.warn("请先查询数据！");
			return;
		}

		/* var heads={
		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
		  "rows": [
	      {"cell":0,"value":"科室名称："+liger.get("dept_id").getText(),"colSpan":"5"},
	      {"cell":3,"value":"项目名称："+liger.get("proj_id").getText(),"from":"right","align":"right","colSpan":"4"}
			  ]
		}; */
	
		var printPara={
				title: "科室指标数据采集结果",//标题
				columns: JSON.stringify(grid.getPrintColumns()),//表头
				class_name: "com.chd.hrp.hpm.service.report.AphiDeptTargetDataReportService",
				method_name: "queryDeptTargetDataReportPrint",
				bean_name: "aphiDeptTargetDataReportService"/* ,
				heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
				};
		
		$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara);
	}
    
    
    function isCheckDisplay(rowdata) {
    	
    	if(rowdata.dept_name == '合计' && rowdata.target_code == null){
    		return false;
    	} 
        
    	return true;
    }
    
    //字典下拉框
    function loadDict(){
    	var param={
    		target_nature:"03",
    		method_code:"01"
    	}
           
    	autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_kind_code","../queryDeptKindDict.do?isCheck=false","id","text",true,true);   

    	$("#is_audit").ligerComboBox({width:160 });
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 	<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acct_yearm"  class="Wdate"  type="text" id="acct_yearm" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室分类：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_kind_code" type="text" id="dept_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>    
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标名称：</td>
            <td align="left" class="l-table-edit-td"><input name="target_code" type="text" id="target_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="is_audit" id="is_audit">
						<option value="">请选择</option>
						<option value="0">否</option>
						<option value="1">是</option>
				</select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="toptoolbar" ></div>
	<div id="maingrid"></div>
</body>
</html>
