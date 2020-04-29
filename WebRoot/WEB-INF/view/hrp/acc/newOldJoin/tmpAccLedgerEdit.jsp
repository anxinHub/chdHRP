<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<link href="<%=path%>/lib/ligerUI/skins/Tab/css/tab.css" rel="stylesheet" />
<link href="<%=path%>/lib/ligerUI/skins/Tab/css/grid.css" rel="stylesheet" />
<script>
    var grid, cell, onoff;
    var gridManager = null;
    var userUpdateStr;
    var titleData;
    var para="";
    var checkTypeName = "";
    var fdata=0;
    var caseStr = "${caseStr}";
    var dire = '${tmpAccSubj.subj_dire}';
    var show_sum=0;
    var toolbar_items;
    var column="";
    
	$(function () {
		loadDict();
		loadForm();
    	loadHead(null);	//加载数据
    	
    	<%--
    	if(dire == 1){
    		$("#dire0").hide();
    		$("#dire1").show();
    	}else{
    		$("#dire0").show();
    		$("#dire1").hide();
    	}
    	--%>
    	
    	$("#subj_code").ligerTextBox({ disabled: true});
    	
    	if('${tmpAccSubj.is_check}' == 0 || '${tmpAccSubj.is_check}' == "否"){
    		$("#bottom").hide();
    	}else{
    		fdata=1;
    		$("#bottom").show();
    		$("#bal_oc").ligerTextBox({ disabled: true});
    		$("#bal_od").ligerTextBox({ disabled: true});
    	}
   		$("#end_oc").ligerTextBox({ disabled: true});
   		$("#end_od").ligerTextBox({ disabled: true});
   		
   		if("${caseStr}" == "oldLedger"){
   			toolbar_items = [
                { text: '添加', id:'add', click: itemclick,icon:'add' },
                { line:true },
                { text: '删除', id:'delete', click:itemclick,icon:'delete' },
                { line:true },
                { text: '导入', id:'import', click:itemclick,icon:'up' },
                { line:true },
				{ text: '刷新', id:'query', click:query ,icon:'refresh' },
                { line:true }
           ];
   		}else{
   			toolbar_items = [
            	{ text: '添加', id:'add', click: itemclick,icon:'add' },
            	{ line:true },
            	{ text: '删除', id:'delete', click:itemclick,icon:'delete' },
            	{ line:true },
            	{ text: '导入', id:'import', click:itemclick,icon:'up' },
                { line:true },
            	{ text: '导出', id:'export', click:itemclick,icon:'down' },
            	{ line:true },
				{ text: '刷新', id:'query', click:query ,icon:'refresh' },
            	{ line:true }
			];
   		}
	});
	
	function getSelectName(check_code){
		var name;
		if (check_code == "check1"){
			name = "dept_name";
		}else if(check_code == "check2"){
			name = "emp_name";
		}else if(check_code == "check3"){
			name = "proj_name";
		}else if(check_code == "check4"){
			name = "store_name";
		}else if(check_code == "check5"){
			name = "cus_name";
		}else if(check_code == "check6"){
			name = "sup_name";
		}else if(check_code == "check7"){
			name = "source_name";
		}else if(check_code == "check8"){
			name = "hos_name";
		}else{
			name = "check_item_name";
		}
		return name;
	}
	
	function getSelectCode(check_code){
		var name;
		if (check_code == "check1"){
			name = "dept_code";
		}else if(check_code == "check2"){
			name = "emp_code";
		}else if(check_code == "check3"){
			name = "proj_code";
		}else if(check_code == "check4"){
			name = "store_code";
		}else if(check_code == "check5"){
			name = "cus_code";
		}else if(check_code == "check6"){
			name = "sup_code";
		}else if(check_code == "check7"){
			name = "source_code";
		}else if(check_code == "check8"){
			name = "hos_code";
		}else{
			name = "check_item_code";
		}
		return name;
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
  
    function loadHead(){
    	var subj_code = liger.get("subj_code").getValue();
    	var columns = "";
    	var gridUrl = 'queryLedgerCheckListNew.do?isCheck=false&caseStr='+caseStr+'&subj_code='+'${tmpAccLedger.subj_code}';
    	if(caseStr == "oldLedger"){
    		gridUrl = 'queryLedgerCheckListOld.do?isCheck=false&caseStr='+caseStr+'&subj_code='+'${tmpAccLedger.subj_code}';
    	}
    	<%--$.post("getSubjItemTitle.do?isCheck=false&subj_code="+subj_code,function(data){--%>
    	$.post("getSubjCheckTitle.do?isCheck=false&subj_code="+subj_code+"&caseStr="+caseStr,function(data){
	 		var url;
	 		data = eval(data)
	 		 	if( data != "" && data!=null){
	 	 			if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
	 	 				url = getRequestURL(data[0].column_check1.toLowerCase());
	 	 				columns = columns + "{ display: '"+data[0].check_type_name1+"', name: 'value1',align: 'left',valueField : '"+data[0].check1.toLowerCase()+"',textField: 'check1',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check1.toLowerCase()+"',textField: '"+data[0].column_check1.toLowerCase()+"' }},";
	 	 				para += data[0].column_item1.toLowerCase() + ";";
	 	 				checkTypeName += data[0].check_type_name1.toLowerCase() + ";";
	 	 			}
	 	 			
	 	 			if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
	 	 				url = getRequestURL(data[0].column_check2.toLowerCase());
	 	 				columns = columns + "{ display: '"+data[0].check_type_name2+"', name: 'value2', align: 'left',valueField : '"+data[0].check2.toLowerCase()+"',textField: 'check2',editor: { type: 'select',keySupport : true, autocomplete : true,url:'"+url+"',valueField : '"+data[0].check2.toLowerCase()+"',textField: '"+data[0].column_check2.toLowerCase()+"' }"
	 						+"},";
	 	 				para += data[0].column_item2.toLowerCase() + ";";
	 	 				checkTypeName += data[0].check_type_name2.toLowerCase() + ";";
	 	 			}
	 	 			
	 	 			if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
	 	 				url = getRequestURL(data[0].column_check3.toLowerCase());
	 	 				columns = columns + "{ display: '"+data[0].check_type_name3+"', name: 'value3', align: 'left',valueField : '"+data[0].check3.toLowerCase()+"',textField: 'check3',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check3.toLowerCase()+"',textField: '"+data[0].column_check3.toLowerCase()+"' }"
	 						+"},";
	 	 				para += data[0].column_item3.toLowerCase() + ";";
	 	 				checkTypeName += data[0].check_type_name3.toLowerCase() + ";";
	 	 			}
	 	 			
	 	 			if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
	 	 				url = getRequestURL(data[0].column_check4.toLowerCase());
	 	 				columns = columns + "{ display: '"+data[0].check_type_name4+"', name: 'value4',align: 'left',valueField : '"+data[0].check4.toLowerCase()+"',textField: 'check4',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check4.toLowerCase()+"',textField: '"+data[0].column_check4.toLowerCase()+"' }"
	 						+"},";
	 	 				para += data[0].column_item4.toLowerCase() + ";";
	 	 				checkTypeName += data[0].check_type_name4.toLowerCase();
	 	 			}
	 		 	}
	 			if(dire == '0' || dire == '借'){
	 				columns += "{ display: '期初借方', name: 'bal_od', align: 'right', editor: { type: 'text' }, totalSummary: {type: 'sum'}, render: function(rowdata){return formatNumber(rowdata.bal_od, 2, 1);}},"
	 						 + "{ display: '期初贷方', name: 'bal_oc', align: 'right', totalSummary: {type: 'sum'}, render: function(rowdata){return formatNumber(rowdata.bal_oc, 2, 1);}},";
	 			}else{
	 				columns += "{ display: '期初借方', name: 'bal_od', align: 'right', totalSummary: {type: 'sum'}, render: function(rowdata){return formatNumber(rowdata.bal_od, 2, 1);}},"
	 						 + "{ display: '期初贷方', name: 'bal_oc', align: 'right', editor: { type: 'text' }, totalSummary: {type: 'sum'}, render: function(rowdata){return formatNumber(rowdata.bal_oc, 2, 1);}},";
	 			}
				columns += "{ display: '期末借方', name: 'end_od', align: 'right',totalSummary:{type: 'sum'},render:function(rowdata){return formatNumber(rowdata.bal_od, 2, 1)}},"
	 					 + "{ display: '期末贷方', name: 'end_oc', align: 'right',totalSummary:{type: 'sum'},render:function(rowdata){return formatNumber(rowdata.bal_oc, 2, 1)}}";
	 				<%--
	 				+
                "if(dire==0){return formatNumber(Number(rowdata.bal_os)+Number(rowdata.sum_od)-Number(rowdata.sum_oc),2,1);}return formatNumber(Number(rowdata.bal_os)+Number(rowdata.sum_oc)-Number(rowdata.sum_od),2,1);}}";
                 --%>
	 			grid = $("#maingrid").ligerGrid({
	 	           columns: eval("["+columns+"]"),
	 	           dataAction: 'server',dataType: 'server',usePager:false,
	 	           url: gridUrl,
	 	           width: '100%', height: '100%',rownumbers:false,checkbox:true,onAfterShowData: f_onAfterEdit,
	 	           enabledEdit: true, selectRowButtonOnly:true,
	 	           onAfterEdit: f_onAfterEdit,
	 	           editingWhenAddingRow:true,
	 	           toolbar: { items: toolbar_items}
	 			});
	 			
	 			gridManager = $("#maingrid").ligerGetGridManager();
	 	 },"json"); 
    }
    
    var bal_os=0;
    
    var sum_od=0;
    
    var sum_oc=0;
    
    var end_os=0;
    
    var end_osValue=0;
    
    function f_onAfterEdit(e){
		var data = gridManager.getData();
        
        var bal_od_value = grid.getTotalInfoSum("bal_od")*1;
        var bal_oc_value = grid.getTotalInfoSum("bal_oc")*1;
        
        $("#bal_od").val(formatNumber(parseFloat(bal_od_value).toString(),2,1));
	    $("#bal_oc").val(formatNumber(parseFloat(bal_oc_value).toString(),2,1));
	    $("#end_od").val(formatNumber(parseFloat(bal_od_value).toString(),2,1));
	    $("#end_oc").val(formatNumber(parseFloat(bal_oc_value).toString(),2,1));
    	
    	<%--
        var data = gridManager.getData();
		console.log(data);
    	var bal_osValue=grid.getTotalInfoSum("bal_os")*1;
    	
    	var sum_odValue=grid.getTotalInfoSum("sum_od")*1;
    	
    	var sum_ocValue=grid.getTotalInfoSum("sum_oc")*1;
    	
		if(dire==0){
			
     		end_osValue=bal_osValue+sum_odValue-sum_ocValue;
     		
     	}else{
     		
     		end_osValue=bal_osValue-sum_odValue+sum_ocValue;
     		
     	}
		
    	$(".l-grid-totalsummary-cell:last").find('div div').text(end_osValue.toFixed(2));
    	
    	$("#bal_os").val(formatNumber(parseFloat(bal_osValue).toString(),2,1));
	      
	    $("#sum_od").val(formatNumber(parseFloat(sum_odValue).toString(),2,1));
	    
	    $("#sum_oc").val(formatNumber(parseFloat(sum_ocValue).toString(),2,1));

	    $("#end_os").val(formatNumber(parseFloat(end_osValue).toString(),2,1));
	    --%>
    	
    }
    
    function itemclick(item){ 
    	if(item.id){
    		switch (item.id){
    			case "add":
    				grid.addRow();
					if (grid.gridview2.height() <= grid.gridbody.find(".l-grid-body-inner:first").height()) { //当添加一行处于最底部时,滚动条滚动到最底部  
                        grid.gridbody.scrollTop(grid.gridbody.find(".l-grid-body-inner:first").height());
                    }
    				<%--
    				gridManager.addEditRow();
    		    	gridManager.endEdit();
    				--%>
    		    	return;
    			case "delete":
    				 var data = gridManager.getCheckedRows();
                     if (data.length == 0){
                     	$.ligerDialog.error('请选择行');
                     }else{
                    	gridManager.removeRange(data);
         				var data = gridManager.getData();
         		     	<%--
         		     	var bal_osValue=grid.getTotalInfoSum("bal_os")*1;
         		     	var sum_odValue=grid.getTotalInfoSum("sum_od")*1;
         		     	var sum_ocValue=grid.getTotalInfoSum("sum_oc")*1;
         		 		if(dire==0){
         		      		end_osValue=bal_osValue+sum_odValue-sum_ocValue;
         		      	}else{
         		      		end_osValue=bal_osValue+sum_ocValue-sum_odValue;
         		      	}
         		     	$(".l-grid-totalsummary-cell:last").find('div div').text(end_osValue.toFixed(2));
         		     	$("#bal_os").val(formatNumber(parseFloat(bal_osValue).toString(),2,1));
         		 	    $("#sum_od").val(formatNumber(parseFloat(sum_odValue).toString(),2,1));
         		 	    $("#sum_oc").val(formatNumber(parseFloat(sum_ocValue).toString(),2,1));
         		 	    $("#end_os").val(formatNumber(parseFloat(end_osValue).toString(),2,1));
         		 	    --%>
                    }
    				return;
    			case "import":
    				<%--var subj_code = liger.get("subj_code").getValue();
                	$.ligerDialog.open({url: 'accLederImportPage.do?isCheck=false&subj_code='+subj_code+'&group_id='+'${group_id}'+'&hos_id='+'${hos_id}'+'&copy_code='+'${copy_code}'+'&subj_dire='+'${subj_dire}'+'&acc_year='+'${acc_year}', height: 500,width: 1135, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
 --%>               <%--$.post("getSubjItemTitle.do?isCheck=false&subj_code="+liger.get("subj_code").getValue(),function(data){ --%>
					$.post("getSubjCheckTitle.do?isCheck=false&caseStr="+caseStr+"&subj_code="+liger.get("subj_code").getValue(),function(data){ 
						data = eval(data);
						var para = {}
						var column = []
						if( data != "" && data!=null){
							if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
								var a = {
									"name":data[0].check1,
									"display" : data[0].check_type_name1,
									"width" : "200",
									"require" : true,
									"colum_check":data[0].colum_check1,
									"column_item":data[0].column_item1,
									"check_type_id":data[0].check_type_id1
								};
								column.push(a)
							}
				 	 			
							if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
								var b = {
									"name":data[0].check2,
									"display": data[0].check_type_name2,
									"width" : "200",
									"require" : true,
									"colum_check":data[0].colum_check2,
									"column_item":data[0].column_item2,
									"check_type_id":data[0].check_type_id2
								};
								column.push(b);
							}
				 	 			
							if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
								var c = {
									"name":data[0].check3,
									"display": data[0].check_type_name3,
									"width" : "200",
									"require" : true,
									"colum_check":data[0].colum_check3,
									"column_item":data[0].column_item3,
									"check_type_id":data[0].check_type_id3
								};
								column.push(c);
							}
				 	 			
							if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
								var d = {
									"name":data[0].check4,
									"display": data[0].check_type_name4,
									"width" : "200",
									"require" : true,
									"colum_check":data[0].colum_check4,
									"column_item":data[0].column_item4,
									"check_type_id":data[0].check_type_id4
								};
								column.push(d);
							}
						}
						
						if(dire == '0' || dire == '借'){
							column.push(
								{ "name":"bal_od", "display":"期初借方", "width" : "200", "column_check":"", "column_item":"" }
							);
						}else{
							column.push(
								{ "name":"bal_oc", "display":"期初贷方", "width" : "200", "column_check":"", "column_item":"" }
							);
						}
						 
						para = {"column": column, "subj_code" :liger.get("subj_code").getValue() }
						console.log("para", para);
						if(caseStr == "oldLedger"){
							importSpreadView("/hrp/acc/join/impTmpAccLedgerCheckOldByExcel.do?isCheck=false", para);  
						}else{
							importSpreadView("/hrp/acc/join/impTmpAccLedgerCheckNewByExcel.do?isCheck=false", para);
						}
					},"json"); 
					return;
    			case "export":
    				var selPara = {};
    				$.each(grid.options.parms, function (i, obj) {
    					selPara[obj.name] = obj.value;
    				});
    				var printPara = {
    					headCount: 2,
    					type: 3,
    					columns: grid.getColumns(1)
    				};
    				ajaxJsonObjectByUrl('queryLedgerCheckListOld.do?isCheck=false&subj_code='+'${tmpAccLedger.subj_code}', selPara, function (resData) {
    					printGridView(resData, printPara);
    				});
    				return;
    			case "downTemplate":
    				var subj_code = liger.get("subj_code").getValue();
                	location.href = "downTemplate.do?isCheck=false&subj_code="+subj_code;
                	return;
    		}
    	}
    }
    
    function is_addRow() {
    	setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
    } 
    
    var formPara;
  
    function saveAccLeder(){
    	if($("#bal_od").val()=="" || $("#bal_oc").val()=="" || $("#end_od").val()=="" || $("#end_oc").val()==""){
    		$.ligerDialog.warn('数据为空！');
    		return;
    	}
    	
    	if (fdata == 0){// 不是辅助核算
			formPara={
				caseStr : caseStr,
				subj_code:liger.get("subj_code").getValue(),
			  	bal_od:$("#bal_od").val().replace(/\,/g, ""),
			  	bal_oc:$("#bal_oc").val().replace(/\,/g, ""),
			  	end_od:$("#end_od").val().replace(/\,/g, ""),
			  	end_oc:$("#end_oc").val().replace(/\,/g, ""),
			  	is_check:0
			};
		} else{
			var col=column.split(",");
			var paraVo=[];
			grid.endEdit(); 
			var data = gridManager.getData();
			$.each(data,function(i,v){
				if(v.check1 && v.check1 != ""){
					var bal_od=this.bal_od===undefined?0.00:formatNumber(this.bal_od,2,0);
					var bal_oc=this.bal_oc===undefined?0.00:formatNumber(this.bal_oc,2,0);
					var end_od=this.end_od===undefined?0.00:formatNumber(this.end_od,2,0);
					var end_oc=this.end_oc===undefined?0.00:formatNumber(this.end_oc,2,0);
					paraVo.push(
						this.check1+"@"+this.check2+"@"+this.check3+"@"+this.check4+"@"+bal_od+"@"+bal_oc+"@"+end_od+"@"+end_oc
					);
				}
			})
			formPara={
				caseStr : caseStr,
				subj_code:liger.get("subj_code").getValue(),
				check_data:paraVo.toString(),
// 				para:para,
				bal_od:$("#bal_od").val().replace(/\,/g, ""),
				bal_oc:$("#bal_oc").val().replace(/\,/g, ""),
				end_od:$("#end_od").val().replace(/\,/g, ""),
				end_oc:$("#end_oc").val().replace(/\,/g, ""),
				show_sum:show_sum,
				is_check:1,
				checkTypeName:checkTypeName
			};
		}
    	
	    ajaxJsonObjectByUrl("addTmpAccLedger.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
                query();
            }
        }); 
	}
    
    function getRequestURL(check_code){
		var url;
		if (check_code == "dept_name"){
			url = "../accleder/queryDeptDict.do?isCheck=false";
		}else if(check_code == "emp_name"){
			url = "../accleder/queryEmp.do?isCheck=false";
		}else if(check_code == "proj_name"){
			url = "../accleder/queryProjDictDict.do?isCheck=false";
		}else if(check_code == "store_name"){
			url = "../accleder/queryStoreDictDict.do?isCheck=false";
		}else if(check_code == "cus_name"){
			url = "../accleder/queryCusDict.do?isCheck=false";
		}else if(check_code == "sup_name"){
			url = "../accleder/querySupDictDict.do?isCheck=false";
		}else if(check_code == "source_name"){
			url = "../accleder/querySourceDict.do?isCheck=false";
		}else if(check_code == "hos_name"){
			url = "../accleder/queryAccHosInfo.do?isCheck=false";
		}else{
			url = "../accleder/queryCheckItem.do?isCheck=false";
		}
		return url;
	}
	
	function loadDict(){
    	
    	$("#subj_code").ligerComboBox({
	      	
    		url: '../querySubj.do?isCheck=false',
	      	
			valueField: 'id',
		 	
			textField: 'text', 
		 	
			selectBoxWidth: 180,
			
			autocomplete: true,
			
			width: 180
			
		 });
        	
         liger.get("subj_code").setValue('${tmpAccLedger.subj_code}');
           
         liger.get("subj_code").setText('${tmpAccLedger.subj_code}'+' '+'${tmpAccLedger.subj_name}');
         
         if(dire == 0 || dire == "借"){
        	 $("#subj_dire_text").text('借');	
        	 
        	 $("#bal_oc").ligerTextBox({ disabled: true});
         }else{
        	 $("#subj_dire_text").text('贷');	
           
     		 $("#bal_od").ligerTextBox({ disabled: true});
         }
		
		//
		$("#bal_od").val(formatNumber(parseFloat('${tmpAccLedger.bal_od}'),2,1));
		$("#bal_oc").val(formatNumber(parseFloat('${tmpAccLedger.bal_oc}'),2,1));
		$("#end_od").val(formatNumber(parseFloat('${tmpAccLedger.end_od}'),2,1));
		$("#end_oc").val(formatNumber(parseFloat('${tmpAccLedger.end_oc}'),2,1));
	}   
	
    function loadForm(){
    	$("form").ligerForm();
    }  
    
    // 金额改变
    function changeMoney(){
    	var bal_od = $("#bal_od").val();
    	var bal_oc = $("#bal_oc").val();
    	
    	$("#bal_od").val(formatNumber(bal_od,2,1));
    	$("#bal_oc").val(formatNumber(bal_oc,2,1));
    	$("#end_od").val(formatNumber(bal_od,2,1));
    	$("#end_oc").val(formatNumber(bal_oc,2,1));
    }
    
</script>
</head>
<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<h4>科目初始账（金额方向：<font id="subj_dire_text"></font>）</h4>
	<input type="hidden" id="subj_dire" name="subj_dire" value="${tmpAccSubj.subj_dire}"/>
	<form name="form1" method="post"  id="form1" >
		<center>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" >
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">科目名称：</td>
					<td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;"></td>
					<td align="left" class="l-table-edit-td"></td>
					<td align="left"></td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">期初借方：</td>
					<td align="left" class="l-table-edit-td"><input name="bal_od" type="text" id="bal_od" value="${tmpAccLedger.bal_od}" ltype="text" style="text-align:right" onchange="changeMoney();" /></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">期初贷方：</td>
					<td align="left" class="l-table-edit-td"><input name="bal_oc" type="text" id="bal_oc" value="${tmpAccLedger.bal_oc}" ltype="text" style="text-align:right" onchange="changeMoney();"/></td>
					<td align="left"></td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">期末借方：</td>
					<td align="left" class="l-table-edit-td"><input name="end_od" type="text" id="end_od" value="${tmpAccLedger.end_od}" ltype="text" style="text-align:right"/></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left:20px;">期末贷方：</td>
					<td align="left" class="l-table-edit-td"><input name="end_oc" type="text" id="end_oc" value="${tmpAccLedger.end_oc}" ltype="text" style="text-align:right"/></td>
					<td align="left"></td>
				</tr>
			</table>
		</center>
	</form>
	<%-- <center id="dire0">公式说明：年初余额+累计借方-累计贷方=期初余额</center> --%>
	<%-- <center id="dire1" style="display: none;">公式说明：年初余额+累计贷方-累计借方=期初余额</center> --%>
	<div id="toptoolbar"></div>
	<div id="bottom">
		<h4>辅助核算初始账</h4>
		<div id="maingrid"></div>
	</div>
</body>
</html>
