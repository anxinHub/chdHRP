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
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>

<script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js" type="text/javascript"></script>
<style type="text/css">
.inp{
	border-left-width:0px;
	border-top-width:0px;
	border-right-width:0px;
	border-bottom-color:black;
	margin-top: 5px;
	padding-top: 2px
}

</style>
<script type="text/javascript">
	
    var sum_column;
    
	var jbgz_value;
	
	var jcxbt_value;
	
	var yf_value;
	
	var kf_value;
	
	var sf_value;
	
	var qt_value;
    
	var acc_times;
	
    $(function ()
    {
    	
    	loadDict(null);
    	
    	$("#emp_code").ligerTextBox({cancelable: false});
    	
    	$("#wage_code").ligerTextBox({cancelable: false});
    	
    });

   
    function addColumn(){
    	
    	var wage_name = liger.get("wage_code").getText();
   	 
   	 item_column="";
   	 
   	 sum_column="";
   	 
   	var yf_columns = "<tr style='color:blue'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>应发合计：</td><td  colspan='16'><input class='inp' name='YF_ITEM' type='text' id='YF_ITEM' ltype='text' disabled='disabled' style='width: 80px' /></td></tr>";
	
	var kk_columns = "<tr style='color:blue'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>扣发合计：</td><td colspan='16'><input class='inp' name='KF_ITEM' type='text' id='KF_ITEM' ltype='text' disabled='disabled' style='width: 80px' /></td></tr>";
	
	var sf_columns = "<tr style='color:blue'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>实发合计：</td><td colspan='16'><input class='inp' name='SF_ITEM' type='text' id='SF_ITEM' ltype='text' disabled='disabled' style='width: 80px' /></td></tr>";

	if(wage_name.indexOf("餐")<=0){
		
		var jbgz_columns = "<tr style='color:blue'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>基本工资：</td><td colspan='16'><input class='inp' name='JBGZ_ITEM' type='text' id='JBGZ_ITEM' ltype='text' disabled='disabled' style='width: 80px' /></td></tr>";
		
		var jcxbt_columns = "<tr style='color:blue'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>基础性补贴：</td><td colspan='16'><input class='inp' name='JCXBT_ITEM' type='text' id='JCXBT_ITEM' ltype='text' disabled='disabled' style='width: 80px' /></td></tr>";
		
		var qt_columns = "<tr style='color:blue'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>其他辅助：</td><td colspan='16'><input class='inp' name='QT_ITEM' type='text' id='QT_ITEM' ltype='text' disabled='disabled' style='width: 80px' /></td></tr>";
		
	}else{
		
		var jbgz_columns = "";
		
		var jcxbt_columns ="";
		
		var qt_columns = "";
	}
	
	var note_columns = "<tr style='color:red;font-size:24px;'><td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>备注：</td><td colspan='16'><input class='inp' name='NOTE' type='text' id='NOTE' ltype='text' disabled='disabled' style='width: 500px' /></td></tr>";
	
	var jbgz_index=0;
	
	var jcxbt_index=0;
	
	var kf_index=0;
	
	var sf_index=0;
	
	var qt_index=0;
	
    jbgz_value="";
    
    jcxbt_value="";
	
    yf_value="";
    
	kf_value="";
	
	sf_value="";
	
	qt_value="";
	
	var note = "备注:";
	
	ajaxJsonObjectByUrl("../accwagepay/queryAccWagePayGrid.do?isCheck=false",{wage_code:liger.get("wage_code").getValue(),acc_year:acc_times.getValue().split(".")[0]},function(responseData){
			
			$("#table_id").html("");
			
			$.each(responseData.Rows,function(index,value){
				
				sum_column+=","+value.COLUMN_ITEM;

// 				if(value.ITEM_NATURE=='01'){
				if(value.ITEM_NATURE=='06'){
    				// 基本工资
					if(value.ITEM_TYPE=='01'){
						// 拼接  工资类型：基本项
						if(jbgz_index%6==0){
							
							jbgz_columns=jbgz_columns+"<tr>";
							
						}else if(jbgz_index%6==0&&jbgz_index != 0){
							
							jbgz_columns=jbgz_columns+"</tr>";
						}
						
						jbgz_columns += "<td align='right' class='l-table-edit-td' style='padding-left:25px;padding-top:10px;'>"+value.ITEM_NAME+"：</td>"
									  + "<td align='left' class='l-table-edit-td'>"
									  +     "<input class='inp' name='"+value.COLUMN_ITEM+"' type='text' id='"+value.COLUMN_ITEM+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px' />"
									  + "</td>"
									  + "<td align='left'></td>";

						jbgz_value=jbgz_value+"+round("+value.COLUMN_ITEM+",2)";
						
						jbgz_index=jbgz_index+1;
	    				
					}else if(value.ITEM_TYPE=='05'){
						// 拼接 基本性补贴
						if(jcxbt_index%6==0){
							
							jcxbt_columns=jcxbt_columns+"<tr>";
							
						}else if(jcxbt_index%6==0&&jcxbt_index != 0){
							
							jcxbt_columns=jcxbt_columns+"</tr>";
						}
						
						jcxbt_columns = jcxbt_columns +"<td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>"+value.ITEM_NAME+"：</td>"
						+"<td align='left' class='l-table-edit-td'><input class='inp' name='"+value.COLUMN_ITEM+"' type='text' id='"+value.COLUMN_ITEM+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px' /></td>"
						+"<td align='left'></td>";

						jcxbt_value=jcxbt_value+"+round("+value.COLUMN_ITEM+",2)";
						
						jcxbt_index=jcxbt_index+1;
					}
					
					yf_value=yf_value+"+"+value.COLUMN_ITEM;
    				
    			 }else if(value.ITEM_NATURE=='07'){
    				// 扣发合计
					if(kf_index%6==0){
						
						kk_columns=kk_columns+"<tr>";
						
					}else if(kf_index%6==0&&kf_index != 0){
						
						kk_columns=kk_columns+"</tr>";
					}
    				
    				kk_columns = kk_columns +"<td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>"+value.ITEM_NAME+"：</td>"
					+"<td align='left' class='l-table-edit-td'><input class='inp' name='"+value.COLUMN_ITEM+"' type='text' id='"+value.COLUMN_ITEM+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px' /></td>"
					+"<td align='left'></td>";
					
					kf_value=kf_value+"+round("+value.COLUMN_ITEM+",2)";
    			
    				kf_index=kf_index+1;
    				
    			}else if(value.ITEM_NATURE=='08'){
    				// 实发合计
					if(sf_index%6==0){
						
						sf_columns=sf_columns+"<tr>";
						
					}else if(sf_index%6==0&&sf_index != 0){
						
						sf_columns=sf_columns+"</tr>";
					}
    				
    				sf_columns = sf_columns +"<td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>"+value.ITEM_NAME+"：</td>"
					+"<td align='left' class='l-table-edit-td'><input class='inp' name='"+value.COLUMN_ITEM+"' type='text' id='"+value.COLUMN_ITEM+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px' /></td>"
					+"<td align='left'></td>";
					
					sf_value=sf_value+"+round("+value.COLUMN_ITEM+",2)";
					
    				sf_index=sf_index+1;
    				
    			}else if(value.ITEM_NATURE=='09'){
    				// 其他辅助
					if(qt_index%6==0){
						
						qt_columns=qt_columns+"<tr>";
						
					}else if(qt_index%6==0&&qt_index != 0){
						
						qt_columns=qt_columns+"</tr>";
					}
    				
					qt_columns = qt_columns +"<td align='right' class='l-table-edit-td'  style='padding-left:25px;padding-top:10px;'>"+value.ITEM_NAME+"：</td>"
					+"<td align='left' class='l-table-edit-td'><input class='inp' name='"+value.COLUMN_ITEM+"' type='text' id='"+value.COLUMN_ITEM+"' ltype='text' validate='{required:true,maxlength:20}' disabled='disabled' style='width: 80px' /></td>"
					+"<td align='left'></td>";
					
					qt_value=qt_value+"+round("+value.COLUMN_ITEM+",2)";
					
					qt_index=qt_index+1;
    				
    			}  
					
				note =note+""+value.note;
				
				if(value.note!=""){
					$("#note").width(1000);
				}
	     	});

			$("#table_id").append(yf_columns);
			
			$("#table_id").append(jbgz_columns);	
			
			$("#table_id").append(jcxbt_columns);
			
			$("#table_id").append(kk_columns);
			
			$("#table_id").append(sf_columns);
			
			$("#table_id").append(qt_columns);
			
			$("#table_id").append(note_columns);
			
        });
   	 
    }
    
	function btn_query(){
    	
    	var emp_id=liger.get("emp_code").getValue();
    	
    	if(emp_id ==""){
    		$.ligerDialog.error('职工为必填项');
        	return;
    	}
    	if(emp_id.indexOf(".") != -1){
    		emp_id = emp_id.split(".")[0];
    	}
    	
    	var formPara={
        		
    			wage_code:liger.get("wage_code").getValue(),
        		
        		acc_year:acc_times.getValue(),
        		
        		emp_id:emp_id,
        		
        		emp_no:$("#emp_no").val(),
        		
        		column_item:sum_column,
        		
        		jbgz_value:jbgz_value==""?"":jbgz_value.substring(1,jbgz_value.length)+" as jbgz_value",
        				
        		jcxbt_value:jcxbt_value==""?"":jcxbt_value.substring(1,jcxbt_value.length)+" as jcxbt_value",
        		
        		yf_value:yf_value==""?"":yf_value.substring(1,yf_value.length)+" as yf_value",
        				
        		kf_value:kf_value==""?"":kf_value.substring(1,kf_value.length)+" as kf_value",
        				
        		qt_value:qt_value==""?"":qt_value.substring(1,qt_value.length)+" as qt_value",
        		
        		sf_value:sf_value==""?"":sf_value.substring(1,sf_value.length)+" as sf_value"
    	};
    	
		ajaxJsonObjectByUrl("../accwagepay/queryAccEmpDetail.do?isCheck=false",formPara,function(responseData){
            
        	data = responseData.Rows[0];
        	if(!data){
        		return ;
        	}
        	
   			$("#YF_ITEM").val(data['YF_VALUE']);
   			
   			$("#KF_ITEM").val(data['KF_VALUE']);
   			
   			$("#SF_ITEM").val(data['SF_VALUE']);
   			
   			$("#JBGZ_ITEM").val(data['JBGZ_VALUE']);
   			
   			$("#JCXBT_ITEM").val(data['JCXBT_VALUE']);
   			
   			$("#QT_ITEM").val(data['QT_VALUE']);
   			
   			$("#NOTE").val(data['NOTE']);
   			
        	if(data != null ){
        		
            	$.each(item,function(i,v){
            		
            		$("#"+v).val(data[v]);
            		
            	});
        		
        	}else{
        		
				$.each(item,function(i,v){
            		
            		$("#"+v).val("0");
            		
            	});
        		
        	}
    			
        });
    	
    } 
    
    function loadDict(){
        //字典下拉框
        
        $(':button').ligerButton({width:80});
 
       var fromData={
        		
        		wage_code:'0000'
        
        }

        autocomplete("#emp_code","../../sys/queryEmpDict.do?isCheck=false&user_id=${sessionScope.user_id}","id","text",true,true,'',true);
        
		var year_Month = '${wage_year_month}';
		
		if(year_Month.toString()=="000000"){
			
			var date=new Date;
			
			 var year=date.getFullYear();
			 
			 var month=date.getMonth()+1;
			 
			 month =(month<10 ? "0"+month:month); 
			 
			 year_Month = (year.toString()+month.toString());
			
		}
		
	       //期间
     	acc_times = $("#acc_time").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy.mm",
            defaultDate: true,
   		});
		
		autocompleteObj({
    		id:"#wage_code",
    		urlStr:"../queryAccWage.do?isCheck=false&is_read=1&wage_type=salary",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		highLight:true,
    		parmsStr:null,
    		defaultSelect:true,
    		initvalue:null,
    		initWidth:"160",initHeight:null,boxwidth:null,alwayShowInDown:null,selectEvent:function (value){
    			addColumn();
    			autocomplete("#emp_code","../accwagepay/queryEmpDictByWageCode.do?isCheck=false&wage_code="+value,"id","text",true,true,'',true);
			}
    	});
		
		acc_times.setValue(year_Month.substring(0,4)+"."+year_Month.substring(4,6).toString());
		 
     } 
    
    function showWord(){
		 
		var showPara={
			class_name: "com.chd.hrp.acc.service.wagedata.AccPeopleWageItemService",
			file_name: "新个税"
			};
	
		showFileWord(showPara); 
	
	}
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div id="layout1">
           
            <div position="center">
			    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			       <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;" ><b><font color="red">*</font></b>期间：</td>
			            <td align="left" class="l-table-edit-td"><input name="acc_time" type="text" id="acc_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资套：</td>
			            <td align="left" class="l-table-edit-td"><input name="wage_code" type="text" id="wage_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
			            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
			            <td align="left"></td>
			            <td align="left"><input type="button" value=" 查询" onclick="btn_query();" /> </td>
			            <td align="left"></td>
			            <td align="right">
			            <a href="javascript:showWord();">新个税文档</a>
						</td>
			            
			        </tr>  
			    </table>
			    
			</div>
			<div style="height:100%;overflow: auto;margin-right: 20px">
			    <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table_id"></table>
			</div>
			
	</div>
</body>
</html>
