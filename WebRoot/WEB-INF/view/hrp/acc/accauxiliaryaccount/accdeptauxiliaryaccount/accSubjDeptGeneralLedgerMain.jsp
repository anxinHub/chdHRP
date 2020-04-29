<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
	<script>
    var grid,acc_year_month1;
    var gridManager = null; 
    var userUpdateStr;
	var query_obj_code;
    var obj_box_data = "";
	var query_subj_code;
    var subj_box_data = "";
    var year_month1="";
    
    $(function (){
		loadDict();
    	loadHead(null);	//加载数据
    });
    
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	year_month1 = acc_year_month1.getValue();
    	if(year_month1 =="" ){
        	$.ligerDialog.error('起始年月为必填项');
        	return ;
        }
        	
        /* if(liger.get("subj_code").getValue() == '' || liger.get("subj_code").getValue() == undefined){
        	$.ligerDialog.error('科目为必填项');
        	return ;
        }  */
        
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'acc_year_b',value : year_month1[0].split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_b',value : year_month1[0].split(".")[1]}); 
    	grid.options.parms.push({name:'acc_year_e',value : year_month1[1].split(".")[0]}); 
    	grid.options.parms.push({name:'acc_month_e',value : year_month1[1].split(".")[1]}); 
    	
    	var subj_code = liger.get("subj_code").getValue();
    	var sch_id = liger.get("sch_id").getValue();
    	grid.options.parms.push({name:'sch_id',value: sch_id}); 
       	grid.options.parms.push({name:'subj_code',value: subj_code.split(".")[1]});
    	//grid.options.parms.push({name:'check_item_code1_b',value: liger.get("dept_code").getValue()}); 
    	
    	var subj_code = liger.get("subj_code").getValue();
    	var sch_id = liger.get("sch_id").getValue();
    	grid.options.parms.push({name:'sch_id',value: sch_id}); 
       	grid.options.parms.push({name:'subj_code',value: subj_code}); 
       	
        var check_item1_b = liger.get("check_item_code1_b") == null ? "" :  liger.get("check_item_code1_b").getValue();
   	    var check_item1_e = liger.get("check_item_code1_b") == null ? "" :  liger.get("check_item_code1_e").getValue();
   	    
       	grid.options.parms.push({name:'check_item_type',value: '${check_type_id}'});
       	grid.options.parms.push({name:'check_item_code1_b',value: check_item1_b});
       	grid.options.parms.push({name:'check_item_code1_e',value: check_item1_e});
    	//加载查询条件
    	grid.loadData(grid.where);
     }
  

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ { display: '期间', align: 'left',columns:[
             							{ display: '年', isSort:false, name: 'acc_year', align: 'left', width: '4%'},
            					        { display: '月', isSort:false, name: 'acc_month', align: 'left', width: '4%'}
            						]
					 },{ 
						 display: '部门', isSort:false, name: 'check1_name', align: 'left',
							render : function(rowdata, rowindex,value) {
								return rowdata.check1_name;
							}
					 },{ 
						 display: '摘要', isSort:false, name: 'summary', align: 'left'
					 },{ 
						 display: '借方', isSort:false, name: 'debit', align: 'right',width: '10%',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex, value) {
           					return formatNumber(rowdata.debit, 2, 1);
           				}
					 },{ 
						 display: '贷方', isSort:false, name: 'credit', align: 'right',width: '10%',formatter:'###,##0.00',
                    	 render : function(rowdata, rowindex, value) {
           					return formatNumber(rowdata.credit, 2, 1);
           				}
					 },{ 
						 display: '方向', isSort:false, name: 'subj_dire', align: 'left', width: '2%'
					 },{ 
						 display: '余额', isSort:false, name: 'end_os', align: 'right',width: '10%',formatter:'###,##0.00',reg:'0.00=Q,0=Q',
                    	 render : function(rowdata, rowindex, value) {
                    		 if(rowdata.end_os==0)
                    			 return "Q";
                    		 else
           					    return formatNumber(rowdata.end_os, 2, 1);
           				}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccSubjDeptGeneralLedger.do',
                     width: '100%', height: '98%', checkbox: false,rownumbers:true,
                     delayLoad:true,pageSize: 100, pageSizeOptions:[100, 200, 500],
                     selectRowButtonOnly:true,
                     toolbar: { items: [
                     		{ text: '查询',id:'search',icon:'search', click: query },
							{ line:true }, 
							{ text: '打印', id:'print', click: print,icon:'print' },
							{ line:true } ,
							{ text: '模板打印', id:'print', click: printDate,icon:'print' },
							{ line:true } ,
							{ text: '模板设置', id:'settings', click: myPrintSet,icon:'settings' },
							{ line:true }
    				]} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function loadDict(){
    	autocomplete("#sch_id", "../queryAccBookSch.do?isCheck=false", "id", "text", true, true, '', true,false,160);
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
    	 
    	 $("#subj_code").ligerComboBox({
    	      	url: '../querySubjByAccount.do?isCheck=false&sign=1',
    	      	valueField: 'id',
    	       	textField: 'text', 
    	       	selectBoxWidth: 200,
    	      	autocomplete: true,
    	      	width: 200
    		 });
    	 autocomplete("#check_item_code1_b","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true,{check_type_id:'${check_type_id}'},false,false,180);
      	autocomplete("#check_item_code1_e","../queryCheckItemByTypeFy.do?isCheck=false","id","text",true,true,{check_type_id:'${check_type_id}'},false,false,180);
    	 
    }   
    
    function loadForm(){
    	 $("form").ligerForm();
     }  
    
	//普通打印数据
	function print(){
			if(grid.getData().length==0){
				$.ligerDialog.error("请先查询数据！");
				return;
			}
			var heads={
			  	//"isAuto": true/false 默认true，页眉右上角默认显示页码
			  	"rows": [
					{"cell":0,"value":"会计期间："+year_month1[0]+"至"+year_month1[1],"colSpan":"5"}  
			  	]
			}; 
				 
			var printPara={
			 	title: "科目部门总账",//标题
			 	columns: JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
			 	class_name: "com.chd.hrp.acc.service.books.auxiliaryaccount.AccDeptAuxiliaryAccountService",
				method_name: "collectAccSubjDeptGeneralLedgerPrint",
				bean_name: "accDeptAuxiliaryAccountService",
				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
				/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
			 };
			 //执行方法的查询条件
			 $.each(grid.options.parms,function(i,obj){
			 	printPara[obj.name]=obj.value;
			 });
			 		
			 officeGridPrint(printPara);
			
		}
		
		//模板打印数据
		function printDate(){
			
			if(grid.getData().length==0){
				$.ligerDialog.error("请先查询数据！");
				return;
			}
	    
	      	var selPara={};
	    	$.each(grid.options.parms,function(i,obj){
	    		selPara[obj.name]=obj.value;
	    	});
	 
	    	selPara["template_code"]="01010";
	       	selPara["class_name"]="com.chd.hrp.acc.service.books.auxiliaryaccount.AccDeptAuxiliaryAccountService";
	       	selPara["method_name"]="collectAccSubjDeptGeneralLedgerPrintDate";
	       	selPara["bean_name"]="accDeptAuxiliaryAccountService";
	       	if(year_month1!=""){
	       		selPara["p_acc_year"]=year_month1[0].split(".")[0];	
	       	}
	       	selPara["p_year_month_b"]=year_month1[0];
	       	selPara["p_year_month_e"]=year_month1[1];
	       	//selPara["obj_code"]=liger.get("dept_code").getValue();
	       	selPara["p_subj_code"]=liger.get("subj_code").getText();
	       	/*selPara["p_cur_code"]=liger.get("cur_code").getText();
	       	var isAccStr="不含未记账";
	       	if($("#is_state").is(":checked")){
	       		isAccStr="包含未记账";
	    	}
	       	selPara["p_is_acc"]=isAccStr; */
	       	selPara["source_id"]="0";
	       	selPara["table_flag"]="1";
	       	officeTablePrint(selPara);
		}
		
		//打印维护
		function myPrintSet(){
			 officeFormTemplate({template_code:"01010"});
		}
		//方案设置
		 function subjIntercalate(){
			 parent.$.ligerDialog.open({
					title : '方案设置',
					width : $(window).width()-500,
					height : $(window).height(),
					url : 'hrp/acc/accbooksch/accBookSchMainPage.do?isCheck=false',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : false,
					isResize : true,
					parentframename : window.name,
					buttons : [ {
						text : '保存',
						onclick : function(item, dialog) {
							dialog.frame.saveSch(0);
						},
						cls : 'l-dialog-btn-highlight'
					},{
						text : '查询',
						onclick : function(item, dialog) {
							dialog.frame.saveSch(1);
						},
						cls : 'l-dialog-btn-highlight'
					},{
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
		 };
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="flag" name="flag"/>
	<input type="hidden" id="query_code" name="query_code"/>
	<input type="hidden" id="subj_flag" name="subj_flag"/>
	<input type="hidden" id="subj_query_code" name="subj_query_code"/>
	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
	    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0" width="100%">
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font size="2" color="red">*</font>会计期间：</td>
	            <td align="left" class="l-table-edit-td" colspan="2">
	            	<input id="acc_year_month1" name="acc_year_month1" style="width:180px" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方&nbsp;&nbsp;案：</td> 
				<td align="left" class="l-table-edit-td">
					<table>
						<tr>
							<td>
								<input name="sch_id" type="text" id="sch_id" ltype="text" /></td>
							</td>
							<td align="left" class="l-table-edit-td">
								<input class="l-button l-button-test"  type="button" value="设置" onclick="subjIntercalate();"/>
							</td>
						</tr>
					</table>
				</td>
				
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;" >科&nbsp;&nbsp;目：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input id="subj_code" name="subj_code"  />
	            </td>
	        </tr> 
		 	<tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" >部&nbsp;&nbsp;门：</td>
	            <td align="left" class="l-table-edit-td" colspan="2" >
	            	<table>
						<tr>
							<td><input id="check_item_code1_b" name="check_item_code1_b"  /></td>
							<td align="left" class="l-table-edit-td">&nbsp;至：</td>
							<td><input id="check_item_code1_e" name="check_item_code1_e" /></td>
							<td align="right" class="l-table-edit-td"></td>
	            		</tr>
					</table>
	            </td>
		 	</tr>
	    </table>
	</form>
	<div id="maingrid"></div>
   	
</body>
</html>
