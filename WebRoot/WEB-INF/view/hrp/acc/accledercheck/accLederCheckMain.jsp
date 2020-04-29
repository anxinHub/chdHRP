<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'check_id',value:$("#check_id").val()}); 
    	grid.options.parms.push({name:'leger_id',value:$("#leger_id").val()}); 
    	grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 
    	grid.options.parms.push({name:'acc_month',value:$("#acc_month").val()}); 
    	grid.options.parms.push({name:'subj_code',value:$("#subj_code").val()}); 
    	grid.options.parms.push({name:'bal_os',value:$("#bal_os").val()}); 
    	grid.options.parms.push({name:'this_od',value:$("#this_od").val()}); 
    	grid.options.parms.push({name:'this_oc',value:$("#this_oc").val()}); 
    	grid.options.parms.push({name:'sum_od',value:$("#sum_od").val()}); 
    	grid.options.parms.push({name:'sum_oc',value:$("#sum_oc").val()}); 
    	grid.options.parms.push({name:'end_os',value:$("#end_os").val()}); 
    	grid.options.parms.push({name:'bal_os_w',value:$("#bal_os_w").val()}); 
    	grid.options.parms.push({name:'this_od_w',value:$("#this_od_w").val()}); 
    	grid.options.parms.push({name:'this_oc_w',value:$("#this_oc_w").val()}); 
    	grid.options.parms.push({name:'sum_od_w',value:$("#sum_od_w").val()}); 
    	grid.options.parms.push({name:'sum_oc_w',value:$("#sum_oc_w").val()}); 
    	grid.options.parms.push({name:'end_os_w',value:$("#end_os_w").val()}); 
    	grid.options.parms.push({name:'check1_id',value:$("#check1_id").val()}); 
    	grid.options.parms.push({name:'check1_no',value:$("#check1_no").val()}); 
    	grid.options.parms.push({name:'check2_id',value:$("#check2_id").val()}); 
    	grid.options.parms.push({name:'check2_no',value:$("#check2_no").val()}); 
    	grid.options.parms.push({name:'check3_id',value:$("#check3_id").val()}); 
    	grid.options.parms.push({name:'check3_no',value:$("#check3_no").val()}); 
    	grid.options.parms.push({name:'check4_id',value:$("#check4_id").val()}); 
    	grid.options.parms.push({name:'check4_no',value:$("#check4_no").val()}); 
    	grid.options.parms.push({name:'check5_id',value:$("#check5_id").val()}); 
    	grid.options.parms.push({name:'check5_no',value:$("#check5_no").val()}); 
    	grid.options.parms.push({name:'check6_id',value:$("#check6_id").val()}); 
    	grid.options.parms.push({name:'check6_no',value:$("#check6_no").val()}); 
    	grid.options.parms.push({name:'check7_id',value:$("#check7_id").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#check_id").val()!=""){
                		return rowdata.check_id.indexOf($("#check_id").val()) > -1;	
                	}
                	if($("#leger_id").val()!=""){
                		return rowdata.leger_id.indexOf($("#leger_id").val()) > -1;	
                	}
                	if($("#acc_year").val()!=""){
                		return rowdata.acc_year.indexOf($("#acc_year").val()) > -1;	
                	}
                	if($("#acc_month").val()!=""){
                		return rowdata.acc_month.indexOf($("#acc_month").val()) > -1;	
                	}
                	if($("#subj_code").val()!=""){
                		return rowdata.subj_code.indexOf($("#subj_code").val()) > -1;	
                	}
                	if($("#bal_os").val()!=""){
                		return rowdata.bal_os.indexOf($("#bal_os").val()) > -1;	
                	}
                	if($("#this_od").val()!=""){
                		return rowdata.this_od.indexOf($("#this_od").val()) > -1;	
                	}
                	if($("#this_oc").val()!=""){
                		return rowdata.this_oc.indexOf($("#this_oc").val()) > -1;	
                	}
                	if($("#sum_od").val()!=""){
                		return rowdata.sum_od.indexOf($("#sum_od").val()) > -1;	
                	}
                	if($("#sum_oc").val()!=""){
                		return rowdata.sum_oc.indexOf($("#sum_oc").val()) > -1;	
                	}
                	if($("#end_os").val()!=""){
                		return rowdata.end_os.indexOf($("#end_os").val()) > -1;	
                	}
                	if($("#bal_os_w").val()!=""){
                		return rowdata.bal_os_w.indexOf($("#bal_os_w").val()) > -1;	
                	}
                	if($("#this_od_w").val()!=""){
                		return rowdata.this_od_w.indexOf($("#this_od_w").val()) > -1;	
                	}
                	if($("#this_oc_w").val()!=""){
                		return rowdata.this_oc_w.indexOf($("#this_oc_w").val()) > -1;	
                	}
                	if($("#sum_od_w").val()!=""){
                		return rowdata.sum_od_w.indexOf($("#sum_od_w").val()) > -1;	
                	}
                	if($("#sum_oc_w").val()!=""){
                		return rowdata.sum_oc_w.indexOf($("#sum_oc_w").val()) > -1;	
                	}
                	if($("#end_os_w").val()!=""){
                		return rowdata.end_os_w.indexOf($("#end_os_w").val()) > -1;	
                	}
                	if($("#check1_id").val()!=""){
                		return rowdata.check1_id.indexOf($("#check1_id").val()) > -1;	
                	}
                	if($("#check1_no").val()!=""){
                		return rowdata.check1_no.indexOf($("#check1_no").val()) > -1;	
                	}
                	if($("#check2_id").val()!=""){
                		return rowdata.check2_id.indexOf($("#check2_id").val()) > -1;	
                	}
                	if($("#check2_no").val()!=""){
                		return rowdata.check2_no.indexOf($("#check2_no").val()) > -1;	
                	}
                	if($("#check3_id").val()!=""){
                		return rowdata.check3_id.indexOf($("#check3_id").val()) > -1;	
                	}
                	if($("#check3_no").val()!=""){
                		return rowdata.check3_no.indexOf($("#check3_no").val()) > -1;	
                	}
                	if($("#check4_id").val()!=""){
                		return rowdata.check4_id.indexOf($("#check4_id").val()) > -1;	
                	}
                	if($("#check4_no").val()!=""){
                		return rowdata.check4_no.indexOf($("#check4_no").val()) > -1;	
                	}
                	if($("#check5_id").val()!=""){
                		return rowdata.check5_id.indexOf($("#check5_id").val()) > -1;	
                	}
                	if($("#check5_no").val()!=""){
                		return rowdata.check5_no.indexOf($("#check5_no").val()) > -1;	
                	}
                	if($("#check6_id").val()!=""){
                		return rowdata.check6_id.indexOf($("#check6_id").val()) > -1;	
                	}
                	if($("#check6_no").val()!=""){
                		return rowdata.check6_no.indexOf($("#check6_no").val()) > -1;	
                	}
                	if($("#check7_id").val()!=""){
                		return rowdata.check7_id.indexOf($("#check7_id").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '供应商名称', name: 'sup_code', align: 'left'
					 },
                     { display: '年初余额', name: 'leger_id', align: 'left'
					 },
                     { display: '累计借方', name: 'sum_od', align: 'left'
					 },
                     { display: '累计贷方', name: 'sum_oc', align: 'left'
					 },
                     { display: '期初余额', name: 'subj_code', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccLederCheck.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
    	                { line:true }
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.check_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
              		$.ligerDialog.open({url: 'accLederCheckAddPage.do?isCheck=false', height: 500,width: 500, title:'添加',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccLederCheck(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
              		return;
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
							this.check_id 
							)
                        });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAccLederCheck.do",{ParamVo : ParamVo},function (responseData){
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
		var parm = 
			vo[0] 
		
    	$.ligerDialog.open({ url : 'deptIncomeUpdatePage.do?isCheck=false&' + parm,data:{}, height: 500,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccLederCheck(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
            
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
     <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年初余额：</td>
                <td align="left" class="l-table-edit-td"><input name="bal_os" type="text" id="bal_os" ltype="text" validate="{required:true,maxlength:20}"  onchange="collEndOs(this)"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计借方：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_od" type="text" id="sum_od" ltype="text" validate="{required:true,maxlength:20}" onchange="collEndOs(this)"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计贷方：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_oc" type="text" id="sum_oc" ltype="text" validate="{required:true,maxlength:20}" onchange="collEndOs(this)"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初余额：</td>
                <td align="left" class="l-table-edit-td"><input name="end_os" type="text" id="end_os" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>

	<div id="maingrid"></div>

</body>
</html>
