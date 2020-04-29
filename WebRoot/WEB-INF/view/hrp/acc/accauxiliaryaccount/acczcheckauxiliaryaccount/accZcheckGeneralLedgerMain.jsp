<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript" src="<%=path%>/lib/hrp/acc/accBookZcheck.js"></script>
	<script>
    var grid,acc_year_month1;
    
    var gridManager = null;
    
    var userUpdateStr; 
    
    var query_subj_code;
    
    var subj_box_data = "";
    var year_month1="";
    var change_col = 0;
    
    $(function ()
    {
    	//loadForm();
		loadDict();
    	loadHead(null);	//加载数据
    	type_count = 1;
    });
  
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
        	columns: [ 
				{ display: '期间', align: 'left',
				columns:[
					{ display: '年', isSort:false, name: 'acc_year', align: 'left', width: '40'},
			        { display: '月', isSort:false, name: 'acc_month', align: 'left', width: '40'}
				]},
				 /* { display: '凭证号', isSort:false, name: 'vouch_no', align: 'left',width: '8%'
				 }, */
				{ display: '科目', isSort:false, name: 'subj_name', align: 'left',width: '200',
					render : function(rowdata, rowindex,
							value) {
						return rowdata.subj_name;
					}
				}, { 
					display: '摘要', isSort:false, name: 'summary', align: 'left',width: '200',
				}, { 
					display: '借方', isSort:false, name: 'debit',align: 'right',width: '120',formatter:'###,##0.00',
					render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.debit, 2, 1);
					}
				}, { 
					display: '贷方', isSort:false, name: 'credit', align: 'right',width: '120',formatter:'###,##0.00',
					render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.credit, 2, 1);
					}
				}, { 
					display: '方向', isSort:false, name: 'subj_dire', align: 'left', width: '50'
				}, { 
					display: '余额', isSort:false, name: 'end_os', align: 'right',width: '120',formatter:'###,##0.00',reg:'0=Q,0.00=Q',
					render : function(rowdata, rowindex, value) {  
						if(rowdata.end_os==0)
           			 	return "Q";
           		 		else
  					    return formatNumber(rowdata.end_os, 2, 1);
			     	}
				}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'collectAccZcheckGeneralLedger.do',
			width: '100%', height: '98%', checkbox: false,rownumbers:true,
			delayLoad:true,pageSize: 100, pageSizeOptions:[100, 200, 500],
			groupColumnName:'subj_name',groupColumnDisplay:'科目',selectRowButtonOnly:true,
			toolbar: { items: [
				{ text: '查询', id:'search', click: query,icon:'search' },
				{ line:true } ,
				{ text: '打印', id:'print', click: printDate,icon:'print' },
				{ line:true } 
			]} 
		});

        gridManager = $("#maingrid").ligerGetGridManager(); 
    };
    
    var para,paraDatil;
 
    function loadDict(){
    	
     	para="";//清空参数
		para = {
			is_sys : 0
		}; 
		autocomplete("#sch_id", "../queryAccBookSch.do?isCheck=false", "id", "text", true, true, '', true);
		
		autocompleteObj({
			id:  '#subj_code',                   
			urlStr: 	"../querySubjCode.do?isCheck=false&is_check="+1,							
			valueField:  'id',            
			textField:    'text' ,            
			autocomplete: true,			
			highLight: true,
			parmsStr: para,
			defaultSelect:  false,
		});
		
		autocompleteObj({
			id: '#check_item_type1',                   
			urlStr:	"../queryCheckTypeBySubjCode.do?isCheck=false",							
			valueField: 'id',            
			textField:   'text' ,            
			autocomplete:true,			
			highLight:true,
			defaultSelect:false,
			selectEvent:function(value){

				var param = {
					check_type_id:  value
	       		};
					
				liger.get("check_item_code1_b").clear();
				liger.get("check_item_code1_b").set("parms", param);
				liger.get("check_item_code1_b").reload();
				liger.get("check_item_code1_e").clear();
				liger.get("check_item_code1_e").set("parms", param);
				liger.get("check_item_code1_e").reload();
		    }
		}); 
		autocomplete("#check_item_code1_b","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
    	autocomplete("#check_item_code1_e","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true);
   	 
    	//会计期间
    	acc_year_month1 = $("#acc_year_month1").etDatepicker({
       		range: true,
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: ['${yearMonth}', '${yearMonth}'],
            onShow:function(picker)  {
                picker.update({
                    minDate: new Date(parent.sessionJson["min_date"]),
                    maxDate: new Date(parent.sessionJson["max_date"]),
                })
            },
   		});
    };   
    
    function loadForm(){
		$("form").ligerForm();
   }; 
   
   function onCheckTypeChange(){
    	para="";//清空参数
		para = {
			check_type_id :  liger.get("check_item_type1").getValue()
		};
    	
		$("#check_item_code1_b").val('');
		$("#check_item_code1_e").val('');
		//方案
		autocomplete("#check_item_code1_b",
				"../queryCheckItem.do?isCheck=false", "id", "text",
				true, true, para);
		$("#subj_code").val('');
		$("#subj_code").ligerComboBox({
			parms: para,
	      	url:  '../querySubj.do?isCheck=false',
	      	valueField:  'id',
	       	textField:  'text', 
	       	selectBoxWidth:  160,
	      	autocomplete:  true,
	      	width:  160,
	      	
		 });
    };
  	
   //打印数据
	function printDate(){ 
		
	   if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		};
		var heads={
			//"isAuto": true/false 默认true，页眉右上角默认显示页码
			"rows": [
          		{"cell":0,"value": "会计期间："+year_month1[0]+"至"+year_month1[1],"colSpan": "4"} ,
	          	{"cell": 4,"value": "科目："+liger.get("subj_code").getText(),"from": "right","align": "right","colSpan": "5"}
			]
    	}; 
		 
   		var printPara={
   			title: "核算项科目总账",//标题
   			columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
   			class_name: "com.chd.hrp.acc.service.books.auxiliaryaccount.AccZcheckAuxiliaryAccountService",
			method_name: "collectAccZcheckGeneralLedgerPrint",
			bean_name: "accZcheckAuxiliaryAccountService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空

			/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
   		};
	   		
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
	};
 	
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">

<input type="hidden" id="subj_flag" name="subj_flag"/>
	<div id="pageloading" class="l-loading" style="display:none"></div>

	<div id="toptoolbar"></div>
	<form name="form1" method="post" id="form1" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
    	<tr>
            <td align="right" class="l-table-edit-td" width="70px;"><font size="2" color="red">*</font>会计期间：</td>
            <td align="left" class="l-table-edit-td"><input id="acc_year_month1" name="acc_year_month1" style='width: 162px'/></td>
           
            <td align="right" class="l-table-edit-td" width="60px;">方案：</td> 
            <td align="left" class="l-table-edit-td">
            	<table>
					<tr>
            			<td><input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
            			<td align="left" class="l-table-edit-td"><input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();"/></td>
					</tr>
				</table>
			</td>
			
            <td align="right" class="l-table-edit-td" >科目：</td>
            <td align="left" class="l-table-edit-td"><input id="subj_code" name="subj_code"  /></td>
        </tr>
         
        <tr>
        	<td align="right" class="l-table-edit-td" ><font size="2" color="red">*</font>核算类1：</td>
            <td align="left" class="l-table-edit-td"><input id="check_item_type1" name="check_item_type1" /></td>
            
        	<td align="right" class="l-table-edit-td">核算项1：</td>
        	<td align="left" class="l-table-edit-td" colspan="3">
            	<table>
					<tr>
						<td><input id="check_item_code1_b" name="check_item_code1_b"  /></td>
						<td align="left" class="l-table-edit-td">&nbsp;至：</td>
						<td><input id="check_item_code1_e" name="check_item_code1_e" /></td>
						<td align="left" class="l-table-edit-td" width="160px">
							<input class="l-button l-button-test"  id="add1" type="button" value="增加" onclick="addLine1()"/>
						</td>
            		</tr>
				</table>
            </td>
        </tr>
        
        <tr id="attend2" style="display: none">
        	<td align="right" class="l-table-edit-td" ><font size="2" color="red">*</font>核算类2：</td>
            <td align="left" class="l-table-edit-td"  ><input id="check_item_type2" name="check_item_type2" /></td>
            
        	<td align="right" class="l-table-edit-td">核算项2：</td>
        	<td align="left" class="l-table-edit-td" colspan="3">
        		<table>
					<tr>
						<td><input id="check_item_code2_b" name="check_item_code2_b"  /></td>
						<td align="left" class="l-table-edit-td">&nbsp;至：</td>
						<td><input id="check_item_code2_e" name="check_item_code2_e" /></td>
						<td align="left" class="l-table-edit-td" width="160px">
							<input class="l-button l-button-test"  id="add2" type="button" value="增加" onclick="addLine2()"/>
							<input class="l-button l-button-test"  id="delete1" type="button" value="移除" onclick="deleteLine1()"/>
						</td>
            		</tr>
				</table>
			</td>
        </tr>
        
		<tr id="attend3" style="display: none">
        	<td align="right" class="l-table-edit-td" ><font size="2" color="red">*</font>核算类3：</td>
            <td align="left" class="l-table-edit-td"><input id="check_item_type3" name="check_item_type3" /></td>
            
        	<td align="right" class="l-table-edit-td">核算项3：</td>
        	<td align="left" class="l-table-edit-td" colspan="3">
	        	<table>
					<tr>
	        			<td><input id="check_item_code3_b" name="check_item_code3_b"  /></td>
			        	<td align="left" class="l-table-edit-td">&nbsp;至：</td>
			        	<td><input id="check_item_code3_e" name="check_item_code3_e" /></td>
						<td align="left" class="l-table-edit-td" width="160px">
							<input class="l-button l-button-test"  id="add3" type="button" value="增加" onclick="addLine3()"/>
							<input class="l-button l-button-test"  id="delete2" type="button" value="移除" onclick="deleteLine2()"/>
						</td>
	        		</tr>
				</table>
			</td>
        </tr>
        
        <tr id="attend4" style="display: none">
        	<td align="right" class="l-table-edit-td" ><font size="2" color="red">*</font>核算类4：</td>
            <td align="left" class="l-table-edit-td"><input id="check_item_type4" name="check_item_type4" /></td>
            
        	<td align="right" class="l-table-edit-td">核算项4：</td>
        	<td align="left" class="l-table-edit-td" colspan="3">
        		<table>
					<tr> 
        				<td><input id="check_item_code4_b" name="check_item_code4_b"  /></td>
			        	<td align="left" class="l-table-edit-td">&nbsp;至：</td>
			        	<td><input id="check_item_code4_e" name="check_item_code4_e" /></td>
						<td align="left" class="l-table-edit-td" width="160px">
							<input class="l-button l-button-test"  id="delete3" type="button" value="移除" onclick="deleteLine3()"/>
						</td>
        			</tr>
				</table>
			</td>
        </tr>
	 
    </table>
	</form>
	<div id="maingrid"></div>
</body>
</html>
