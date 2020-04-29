<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/stringbuffer.js"></script>
	<script>
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var titleData;
    
    var cusData;
    
    var deptData;
    
    var empData;
    
    var projData;
    
    var storeData;
    
    var sourceData;
    
    var supData;
    
    var checkItemData;
    
    var check_type;
    
    var dataAB;
    
    $(function (){
    	
		loadDict();
		
		loadForm();
		
    	loadHead(null);	//加载数据
    	
    });
    
	function loadColDictData(){
		$.post("../../sys/queryCusDict.do?isCheck=false",null,function(data){cusData = data;},"json");
		$.post("../../sys/queryDeptDict.do?isCheck=false",null,function(data){deptData = data;},"json");
		$.post("../../sys/queryEmp.do?isCheck=false",null,function(data){empData = data;},"json");
		$.post("../../sys/queryProjDictDict.do?isCheck=false",null,function(data){projData = data;},"json");
		$.post("../../sys/queryStoreDictDict.do?isCheck=false",null,function(data){storeData = data;},"json");
		$.post("../../sys/querySourceDict.do?isCheck=false",null,function(data){sourceData = data;},"json");
		$.post("../../sys/querySupDictDict.do?isCheck=false",null,function(data){supData = data;},"json");
		$.post("../queryCheckItem.do?isCheck=false",null,function(data){checkItemData = data;},"json");
		$.post("queryVHosDict.do?isCheck=false",null,function(data){checkItemData = data;},"json");
		
	} 
	
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    }
  
    var para="";
    
    function loadHead(){	
    	
    	var subj_code = liger.get("subj_code").getValue();    	
    	var columns = "{ display: '摘要', name: 'summary', align: 'left',editor: { type: 'text' }},";
    	
    	var checktype = new StringBuffer();
	 	
    	$.post("../accleder/getSubjItemTitle.do?isCheck=false",{'subj_code':subj_code},function(data){	 			
	 		
    		var url;	 		
 		 	
    		dataAB = data;
    		if( data != "" && data!=null){		 		
 	 			
    			if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){	 				
 	 				
    				url = getRequestURL(data[0].column_item1.toLowerCase());
 	 				
 	 				columns = columns + "{ display: '"+data[0].check_type_name1+"', name: 'check1',align: 'left',textField: '"+data[0].column_check1.toLowerCase()+"',editor: { type: 'select', url:'"+url+"',valueField : '"+data[0].check1.toLowerCase()+"',textField: '"+data[0].column_check1.toLowerCase()+"' }"+"},";
 	 				
 	 				para =data[0].column_item1.toLowerCase();
 	 				
 	 				if(data[0].column_item1!=undefined){
 	 				
 	 					checktype.append(data[0].column_item1).append('-1,');
 	 				
 	 				}
 	 				
 	 			}
 	 			
 	 			if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){ 	 				
 	 				url = getRequestURL(data[0].column_item2.toLowerCase());						
 	 				columns = columns + "{ display: '"+data[0].check_type_name2+"', name: 'check2', align: 'left',textField: '"+data[0].column_check2.toLowerCase()+"',editor: { type: 'select', url:'"+url+"',valueField : '"+data[0].check2.toLowerCase()+"',textField: '"+data[0].column_check2.toLowerCase()+"' }"+"},";
 	 				
 	 				para =para+";"+data[0].column_item2.toLowerCase();
 	 				
 	 				if(data[0].column_item2!=undefined){
 	 				
 	 					checktype.append(data[0].column_item2).append('-2,');
 	 				
 	 				}
 	 			
 	 			}
 	 			
 	 			if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){		
 	 				
 	 				url = getRequestURL(data[0].column_item3.toLowerCase());			
 	 				
 	 				columns = columns + "{ display: '"+data[0].check_type_name3+"', name: 'check3', align: 'left',textField: '"+data[0].column_check3.toLowerCase()+"',editor: { type: 'select', url:'"+url+"',valueField : '"+data[0].check3.toLowerCase()+"',textField: '"+data[0].column_check3.toLowerCase()+"' }"+"},";
 	 				
 	 				para =para+";"+data[0].column_item3.toLowerCase();
 	 				
 	 				if(data[0].column_item3!=undefined){
 	 					
 	 					checktype.append(data[0].column_item3).append('-3,');
 	 				
 	 				}
 	 			
 	 			}
 	 			
 	 			if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){	 			
 	 				
 	 				url = getRequestURL(data[0].column_item4.toLowerCase());	 				
 	 				
 	 				columns = columns + "{ display: '"+data[0].check_type_name4+"', name: 'check4',align: 'left',textField: '"+data[0].column_check4.toLowerCase()+"',editor: { type: 'select', url:'"+url+"',valueField : '"+data[0].check4.toLowerCase()+"',textField: '"+data[0].column_check4.toLowerCase()+"' }" +"},";
 	 				
 	 				para =para+";"+data[0].column_item4.toLowerCase();
 	 				
 	 				if(data[0].column_item4!=undefined){
 	 				
 	 					checktype.append(data[0].column_item4).append('-4,');
 	 				
 	 				}
 	 			
 	 			}
 		 		
 		 	}
 		 	columns = columns + "{ display: '发生日期', name: 'occur_date', align: 'left', type: 'date', format: 'yyyy-MM-dd', editor: { type: 'date'}},"
                 +"{ display: '到期日期', name: 'due_date', align: 'left', type: 'date', format: 'yyyy-MM-dd', editor: { type: 'date'}},"
                 +"{ display: '合同编号', name: 'con_no', align: 'left',editor: { type: 'text' }},"
                 +"{ display: '单据号', name: 'business_no', align: 'left',editor: { type: 'text' }},"
                 +"{ display: '金额', name: 'money', align: 'right',totalSummary:"
	                    +"{type: 'sum'},editor: { type: 'text' }"
				 +"}";
			//alert(columns);
	 			grid = $("#maingrid").ligerGrid({
	 	           columns: eval("["+columns+"]"),
	 	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccCurrentAccountInit.do?isCheck=false&subj_code='+liger.get("subj_code").getValue().split(".")[0]+'&checktype='+checktype,
	 	                     width: '100%', height: '100%', checkbox: true,rownumbers:false,
	 	                     enabledEdit: true,
	 	                     selectRowButtonOnly:true,
	 	                     toolbar: { items: [
	 	                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
	 	                     	{ line:true },
	 	                     	{ text: '删除', id:'delete', click:itemclick,icon:'delete' },
	 	                     	{ line:true },
								/* { text: '下载导入模板', id:'downTemplate', click:itemclick,icon:'down' },
	 	                     	{ line:true }, */
	 	                     	{ text: '导入', id:'import', click:itemclick,icon:'up' },
	 	                     	{ line:true }
	 	    				]}
	 	                   });
	 			gridManager = $("#maingrid").ligerGetGridManager();
	 			gridManager.endEdit();
	 	},"json");
	 	
    }
    
    function itemclick(item){ 
    	if(item.id){
    		switch (item.id){
    			case "add":
    				gridManager.addEditRow();
    		    	gridManager.endEdit();
    		    	return;
    			case "delete":
    				var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }
                    gridManager.deleteSelectedRow();
    				return;
				case "import":
    				
    				//var subj_code = liger.get("subj_code").getValue();
    				
                	//$.ligerDialog.open({url: 'accCurrentAccountImportPage.do?isCheck=false&subj_code='+subj_code+'&group_id='+'${group_id}'+'&hos_id='+'${hos_id}'+'&copy_code='+'${copy_code}'+'&subj_dire='+'${subj_dire}', height: 500,width: 800, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
                
                	
                	importSpreadViewAB("hrp/acc/accnostro/accCurrentAccountImportLJ.do?isCheck=false");
    			case "export":
                
    				return;
               
    			case "downTemplate":
                	
    				var subj_code = liger.get("subj_code").getValue();
    		    	
                	location.href = "downTemplate.do?isCheck=false&subj_code="+subj_code;
                	
                	return;
    		}
    	}
    }
    
    function importSpreadViewAB(url) {
    	 var para = {
				 "column" : [
				    {
					"name" : "summary",
					"display" : "摘要",
					"width" : "200"
				}],'subj_code':liger.get("subj_code").getValue(),'subj_dire':'${subj_dire}'
			};
		if( dataAB != "" && dataAB!=null){
 	 			
  			if(dataAB[0].check_type_name1 != null && dataAB[0].check_type_name1 != ""){
  				//CHECK1_NAME
  				para.column.push({name:'column_code_value1',display:dataAB[0].check_type_name1 + '编码',width:'200',require:true});
  				para.column.push({name:'column_name_value1',display:dataAB[0].check_type_name1 + '名称',width:'200',require:true});
 			}
 			
 			if(dataAB[0].check_type_name2 != null && dataAB[0].check_type_name2 != ""){
 				para.column.push({name:'column_code_value2',display:dataAB[0].check_type_name2 + '编码',width:'200',require:true});
  				para.column.push({name:'column_name_value2',display:dataAB[0].check_type_name2 + '名称',width:'200',require:true});
 			}
 			
 			if(dataAB[0].check_type_name3 != null && dataAB[0].check_type_name3 != ""){
 				para.column.push({name:'column_code_value3',display:dataAB[0].check_type_name3 + '编码',width:'200',require:true});
  				para.column.push({name:'column_name_value3',display:dataAB[0].check_type_name3 + '名称',width:'200',require:true});
 			}
 			
 			if(dataAB[0].check_type_name4 != null && dataAB[0].check_type_name4 != ""){
 				para.column.push({name:'column_code_value4',display:dataAB[0].check_type_name4 + '编码',width:'200',require:true});
  				para.column.push({name:'column_name_value4',display:dataAB[0].check_type_name4 + '名称',width:'200',require:true});
 			}
	 		
	 	}
		para.column.push({name:'year',display:'发生日期',width:'200',require:true});
		para.column.push({name:'month',display:'到期日期',width:'200'});
		para.column.push({name:'sup_name',display:'合同编号',width:'200'});
		para.column.push({name:'store_name',display:'单据号',width:'200'});
		para.column.push({name:'stocker_name',display:'金额',width:'200',require:true});

		//alert(JSON.stringify(dataAB));
		//alert(JSON.stringify(para));
    	parent.parent.openDialog({ url: "print/import.jsp", data: { url: url, para: para }, title: "导入", width: 0, height: 0 });

    }
    function addNewRow(){
    	gridManager.addEditRow();
    	gridManager.endEdit();
    } 
    
  	function saveAccVouchCheck(){
	  	var data = gridManager.getCheckedRows();
	  	var gridRowData = grid.getData();
		//删除空行
		for (var i = 0; i < gridRowData.length; i++) {
			//判断  如果当前行中摘要、发生日期、到期日期、合同编号、单据号、金额都为空则删除
			if ((gridRowData[i]) && (!gridRowData[i].summary  && !gridRowData[i].occur_date && !gridRowData[i].due_date  
					&& !gridRowData[i].con_no && !gridRowData[i].business_no && !gridRowData[i].money) ) {
				grid.deleteRow(i);
				gridRowData = grid.getData();
				i--;
			}
		}
		
		if(gridRowData.length == 0 ){
		  	$.ligerDialog.error('请添加明细');
		  	return ;
		}

	    $(gridRowData).each(function(){	
		  	if(this.occur_date == undefined || this.occur_date ==''){	
			  	$.ligerDialog.error('发生日期为必填项');			  
			  	return ;
		  	}	
		  
		  	if(this.money == undefined || this.money ==''){	
			  	$.ligerDialog.error('金额为必填项');			  
			  	return ;
		  	}
		  
	  	});     
	  
	  	var paraVo=[];
	  	$.each(gridRowData,function(i,v){
			paraVo.push(
				this.summary+"@"+this.check1+"@"+this.check2+"@"+this.check3+"@"+this.check4+"@"+this.occur_date
				+"@"+this.due_date+"@"+this.con_no+"@"+this.business_no+"@"+this.money	
			)
			
	 	})
		
	  	var formPara={
			subj_code : liger.get("subj_code").getValue(),
			check_type : check_type,
			subj_dire : '${subj_dire}',
			check_data : paraVo.toString(),
			para:para
	  	};
  /* alert(JSON.stringify(formPara));alert(liger.get("subj_code").getValue()); */
  		ajaxJsonObjectByUrl("../accvouchcheck/getIsAccFlag.do?isCheck=false",formPara,
		function(responseData){
  				if(responseData.state == "true"){
  					ajaxJsonObjectByUrl("addAccVouchVerCheckInit.do?isCheck=false",formPara,
							function(responseData){
								if(responseData.state=="true"){
									 query();
								}
							});
  				}else{
  					$.ligerDialog.error('本期间已结账');			  
  				  	return ;
  				}
			}
		);
  	}
  
  	function getRequestURL(check_code){
		var url;
		if (check_code == "check1"){
			url = "../accleder/queryDeptDict.do?isCheck=false";
		}else if(check_code == "check2"){
			url = "../accleder/queryEmp.do?isCheck=false";
		}else if(check_code == "check3"){
			url = "../accleder/queryProjDictDict.do?isCheck=false";
		}else if(check_code == "check4"){
			url = "../accleder/queryStoreDictDict.do?isCheck=false";
		}else if(check_code == "check5"){
			url = "../accleder/queryCusDict.do?isCheck=false";
		}else if(check_code == "check6"){
			url = "../accleder/querySupDictDict.do?isCheck=false";
		}else if(check_code == "check7"){
			url = "../accleder/querySourceDict.do?isCheck=false";
		}else {
			url = "../accleder/queryCheckItem.do?isCheck=false";
		}
		return url;
  	}
	
	  function FormatDate (strTime) {  
		  var date = new Date(strTime); 
		  return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	  }
   
  		function loadDict(){
	    	var param = {
	        	SUBJ_NATURE_CODE1:'04',
	        	SUBJ_NATURE_CODE2:'05'
	        };
	        
			$("#subj_code").ligerComboBox({
				parms : param,
		    	url: '../querySubj.do?isCheck=false',
				valueField: 'id',
				textField: 'text', 
				selectBoxWidth: 180,
				autocomplete: true,
				width: 180,
				disabled:true
			});
		 
            liger.get("subj_code").setValue('${subj_code}');
            liger.get("subj_code").setText('${subj_code}'+' '+'${subj_name}');
            
            if('${subj_dire}' == 0){
            	$("#subj_dire_text").text('借');	
            }else{
            	$("#subj_dire_text").text('贷');	
            }
         }   
    function loadForm(){
      	 $("form").ligerForm();
       }  
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<center>
	 		<table cellpadding="0" cellspacing="0" class="l-table-edit" >
        		<tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称：</td>
		            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}"  disabled="disabled"/></td>
		            <td align="left"></td>
        		</tr> 
		       	<%--  <tr>
		       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">年初余额：</td>
		            <td align="left" class="l-table-edit-td"><input name="bal_os" type="text" id="bal_os" value="${accLederCheck.bal_os }" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计借方：</td>
		            <td align="left" class="l-table-edit-td"><input name="sum_od" type="text" id="sum_od" value="${accLederCheck.sum_od }" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计贷方：</td>
		            <td align="left" class="l-table-edit-td"><input name="sum_oc" type="text" id="sum_oc" value="${accLederCheck.sum_oc }" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初余额：</td>
		            <td align="left" class="l-table-edit-td"><input name="end_os" type="text" id="end_os" value="${accLederCheck.end_os }" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
		            <td align="left" class="l-table-edit-td"></td>
		            <td align="left"></td>
		        </tr>  --%>
	 
    		</table>
    	</center>
    </form>
    <!-- <center>公式说明：年初余额+累计借方-累计贷方=期初余额</center> -->
	<div id="toptoolbar" ></div>
	<h4>（金额方向：<font id="subj_dire_text"><input type="hidden" id="subj_dire" name="subj_dire" value="${subj_dire}"/></font>）</h4>
	<div id="maingrid"></div>
</body>
</html>
