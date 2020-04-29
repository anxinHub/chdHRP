<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<link href="<%=path%>/lib/ligerUI/skins/Tab/css/tab.css" rel="stylesheet" />
<link href="<%=path%>/lib/ligerUI/skins/Tab/css/grid.css" rel="stylesheet" />

	<script>
    var grid,
	cell,onoff;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    var titleData;
    
    var para="";
    
    var fdata=0;
    
    var dire='${subj_dire}';
    
    var show_sum=0;
    
    var column="";
    
    $(function ()
    {
    	
		loadDict();
		
		loadForm();
		
    	loadHead(null);	//加载数据
    	if(dire == 1){
    		$("#dire0").hide();
    		$("#dire1").show();
    	}else{
    		$("#dire0").show();
    		$("#dire1").hide();
    	}
    	
    	$("#subj_code").ligerTextBox({ disabled: true});
    	
    	if('${is_check}'==0){
    		
    		$("#bottom").hide();
    		
    		$("#end_os").ligerTextBox({ disabled: true});
    		
    	}else{
    		
    		fdata=1;
    		
    		$("#bottom").show();
    		
    		$("#bal_os").ligerTextBox({ disabled: true});	
    		
    		$("#sum_od").ligerTextBox({ disabled: true});
    		
    		$("#sum_oc").ligerTextBox({ disabled: true});
    		
    		$("#end_os").ligerTextBox({ disabled: true});
    		
    	}
    	
    	  /* ajaxJsonObjectByUrl("queryModByModCode.do?isCheck=false",{},function(responseData){
    		   
            if(responseData!=null){
           	 
           	 if(responseData[0].start_month=="01"||responseData[0].start_month=="1"){
    				 
                	show_sum=1;
                	 
                 	grid.toggleCol('sum_od', false);
                 	
                 	grid.toggleCol('sum_oc', false);
                 	
                 }
           	 
            }
            
        });  */	
    	
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
    	$.post("getSubjItemTitle.do?isCheck=false&subj_code="+subj_code,function(data){
	 		var url;
	 		data = eval(data)
	 		 	if( data != "" && data!=null){
	 	 			if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
	 	 				
	 	 				url = getRequestURL(data[0].column_check1.toLowerCase());
	 	 				
	 	 				columns = columns + "{ display: '"+data[0].check_type_name1+"', name: 'value1',align: 'left',valueField : '"+data[0].check1.toLowerCase()+"',textField: 'check1',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check1.toLowerCase()+"',textField: '"+data[0].column_check1.toLowerCase()+"' }"
	 						
	 	 					+"},";
	 	 					
	 	 					para = data[0].column_item1.toLowerCase();
	 	 			}
	 	 			
	 	 			if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
	 	 				
	 	 				url = getRequestURL(data[0].column_check2.toLowerCase());
	 	 				
	 	 				columns = columns + "{ display: '"+data[0].check_type_name2+"', name: 'value2', align: 'left',valueField : '"+data[0].check2.toLowerCase()+"',textField: 'check2',editor: { type: 'select',keySupport : true, autocomplete : true,url:'"+url+"',valueField : '"+data[0].check2.toLowerCase()+"',textField: '"+data[0].column_check2.toLowerCase()+"' }"
	 					
	 						+"},";
	 	 				para =para+";"+data[0].column_item2.toLowerCase();
	 	 			}
	 	 			
	 	 			if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
	 	 				
	 	 				url = getRequestURL(data[0].column_check3.toLowerCase());
	 					
	 	 				columns = columns + "{ display: '"+data[0].check_type_name3+"', name: 'value3', align: 'left',valueField : '"+data[0].check3.toLowerCase()+"',textField: 'check3',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check3.toLowerCase()+"',textField: '"+data[0].column_check3.toLowerCase()+"' }"
	 					
	 						+"},";
	 	 				para =para+";"+data[0].column_item3.toLowerCase();
	 	 			}
	 	 			
	 	 			if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
	 	 			
	 	 				url = getRequestURL(data[0].column_check4.toLowerCase());
	 	 				
	 	 				columns = columns + "{ display: '"+data[0].check_type_name4+"', name: 'value4',align: 'left',valueField : '"+data[0].check4.toLowerCase()+"',textField: 'check4',editor: { type: 'select',keySupport : true, autocomplete : true, url:'"+url+"',valueField : '"+data[0].check4.toLowerCase()+"',textField: '"+data[0].column_check4.toLowerCase()+"' }"
	 					
	 						+"},";
	 					
	 	 				para =para+";"+data[0].column_item4.toLowerCase();
	 	 			}
	 		 		
	 		 	}
	 			
				columns = columns + "{ display: '年初余额', name: 'bal_os', align: 'right',editor: { type: 'text' },totalSummary:{type: 'sum'},render:function(rowdata){return formatNumber(rowdata.bal_os, 2, 1)}},"
                
	 			+"{ display: '累计借方', name: 'sum_od', align: 'right', editor: { type: 'text' },totalSummary:{type: 'sum'},render:function(rowdata){return formatNumber(rowdata.sum_od, 2, 1)}},"
                
	 			+"{ display: '累计贷方', name: 'sum_oc', align: 'right',editor: { type: 'text' },totalSummary:{type: 'sum'},render:function(rowdata){return formatNumber(rowdata.sum_oc, 2, 1)}},"
                 
	 			+"{ display: '期初余额', name: 'end_os', align: 'right',totalSummary:{type: 'sum'},render:function(rowdata){"+
                
                "if(dire==0){return formatNumber(Number(rowdata.bal_os)+Number(rowdata.sum_od)-Number(rowdata.sum_oc),2,1);}return formatNumber(Number(rowdata.bal_os)+Number(rowdata.sum_oc)-Number(rowdata.sum_od),2,1);}}";
                 
	 			grid = $("#maingrid").ligerGrid({
	 				
	 	           columns: eval("["+columns+"]"),
	 	           
	 	                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccLederCheckList.do?isCheck=false&subj_code='+'${subj_code}',
	 	                     
	 	                     width: '100%', height: '100%',rownumbers:false,checkbox:true,onAfterShowData: f_onAfterEdit,
	 	                     
	 	                     enabledEdit: true, selectRowButtonOnly:true,onAfterEdit: f_onAfterEdit,editingWhenAddingRow:true,
							/*onGridClick:function (src){
								if(src.row) {
									onoff = true;	
								}else {
									onoff = false;
								}

							},  
							onAfterAddRow:function (){
								if(cell && onoff){
									onoff = false;
									setTimeout(function (){
										grid._applyEditor(cell)
									},0)

								}
								
							},	*/	                     
	 	                     toolbar: { items: [
	 	                     	{ text: '添加', id:'add', click: itemclick,icon:'add' },
	 	                     	{ line:true },
	 	                     	
	 	                     	{ text: '删除', id:'delete', click:itemclick,icon:'delete' },
	 	                     	{ line:true },
	 	                     	
	 	                     	{ text: '导入', id:'import', click:itemclick,icon:'up' },
	 	                     	{ line:true },
	 	                     	
								{ text: '刷新', id:'query', click:query ,icon:'refresh' },
	 	                     	{ line:true },
	 	                     	
	 	                     	{ text: '打印', id:'print', click: print ,icon:'print' },
	 	                     	{ line:true }
	 	                     ]}
	 	                  
	 			});
	 			
	 			gridManager = $("#maingrid").ligerGetGridManager();
				/* $("#maingrid").keydown(function(){
					 
					 onoff = false;
				 })*/
	 			
	 			/*gridManager.endEdit();*/
	 	
	 	 },"json"); 
	 	
    }
    
    var bal_os=0;
    
    var sum_od=0;
    
    var sum_oc=0;
    
    var end_os=0;
    
    var end_osValue=0;
    
    function f_onAfterEdit(e){
    	
		/*cell = grid.getCellObj(e.record, e.column);*/
        var data = gridManager.getData();
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
    	
    }
    
	// 打印
    function print(){
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据！");
    		return;
    	}
    	
    	var heads={
	      	//"isAuto": true/false 默认true，页眉右上角默认显示页码
			"rows": []
		};
	   		
		var printPara={
			rowCount:1,
			title:'辅助核算明细',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.acc.service.AccLederService",
			method_name: "queryAccLederCheckListPrint",
			bean_name: "accLederService",
			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
			subj_code: "${subj_code}"
		};
	
		//执行方法的查询条件
		$.each(grid.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
		});
		
		officeGridPrint(printPara); 
    }
    
    function itemclick(item){ 
    	
    	if(item.id){
    		
    		switch (item.id){
    		
    			case "add":
    				grid.addRow();
					if (grid.gridview2.height() <= grid.gridbody.find(".l-grid-body-inner:first").height()) { //当添加一行处于最底部时,滚动条滚动到最底部  
                        grid.gridbody.scrollTop(grid.gridbody.find(".l-grid-body-inner:first").height());
                    }
    				/*gridManager.addEditRow();
    				
    		    	gridManager.endEdit();*/
    		    	
    		    	return;
    		    	
    			case "delete":
    				
    				 var data = gridManager.getCheckedRows();
               	  
                     if (data.length == 0){
                    	 
                     	$.ligerDialog.error('请选择行');
                     	
                     }else{
                    	 
                    	gridManager.removeRange(data);
         				
         				
         				var data = gridManager.getData();
         				
         		     	
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
                     }
    				
                    
    				return;
    				
    			case "import":
    				
    			/* 	var subj_code = liger.get("subj_code").getValue();
    				
                	$.ligerDialog.open({url: 'accLederImportPage.do?isCheck=false&subj_code='+subj_code+'&group_id='+'${group_id}'+'&hos_id='+'${hos_id}'+'&copy_code='+'${copy_code}'+'&subj_dire='+'${subj_dire}'+'&acc_year='+'${acc_year}', height: 500,width: 1135, title:'导入',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true });
 */               $.post("getSubjItemTitle.do?isCheck=false&subj_code="+liger.get("subj_code").getValue(),function(data){ 
						 data = eval(data)
						 console.log(data)
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
					 	 					}
				 	 				
				 	 				
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
				 	 				}
				 	 				column.push(b)
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
				 	 				}
				 	 				
				 	 				column.push(c)
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
				 	 				}
				 	 				column.push(d)
				 	 			}
				 		 	}
						 column.push(
								 {
					 				"name":"bal_os",
			 	 					"display":"年初余额",
			 	 					"width" : "200",
			 	 					"column_check":"",
			 	 					"column_item":""
								},
								{
					 				"name":"sum_od",
			 	 					"display":"累计借方",
			 	 					"width" : "200",
			 	 					"column_check":"",
			 	 					"column_item":""
								},
								{
					 				"name":"sum_oc",
			 	 					"display":"累计贷方",
			 	 					"width" : "200",
			 	 					"column_check":"",
			 	 					"column_item":""
								})
						 
						 para = {"column": column,
						 		 "subj_code" :liger.get("subj_code").getValue() 
						 }
						 importSpreadView("/hrp/acc/accleder/readAccLederRelaFiles.do?isCheck=false", para);  
 					},"json"); 
				
    			case "export":
                
    				return;
               
    			case "downTemplate":
                	
    				var subj_code = liger.get("subj_code").getValue();
    		    	
                	location.href = "downTemplate.do?isCheck=false&subj_code="+subj_code;
                	
                	return;
    		}
    	}
    }
    
    function is_addRow()
    {
    	
    	setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
    	
    } 
    
    var formPara;
  
    function saveAccLeder(){
    	
    	if($("#bal_os").val()=="" || $("#sum_od").val()=="" || $("#sum_oc").val()=="" || $("#end_os").val()==""){
    		$.ligerDialog.error('数据为空！');
    		return;
    	}
    	
    	if (fdata == 0){
			  
			  formPara={
					  
					  subj_code:liger.get("subj_code").getValue(),
			  	      
			  	      bal_os:$("#bal_os").val().replace(/\,/g, ""),
			  	      
			  	      sum_od:$("#sum_od").val().replace(/\,/g, ""),
			  	    
			  	      sum_oc:$("#sum_oc").val().replace(/\,/g, ""),
			  	  
			  	      end_os:$("#end_os").val().replace(/\,/g, ""),
			  	      
			  	      is_check:0
			  	      
			  };
		      	
		     } else{
		    	 
		    	  var col=column.split(",");
		     	
		     	  var paraVo=[];
		     	
		    	  grid.endEdit(); 
		    	  
		    	  var data = gridManager.getData();
				  $.each(data,function(i,v){
					  console.log(v)
						if(v.check1 && v.check1 != " "){
							var bal_os=this.bal_os===undefined?0.00:formatNumber(this.bal_os,2,0);
							var sum_od=this.sum_od===undefined?0.00:formatNumber(this.sum_od,2,0);
							var sum_oc=this.sum_oc===undefined?0.00:formatNumber(this.sum_oc,2,0);
							
							if(dire==0){
								paraVo.push(
										
										this.value1+"@"+this.value2+"@"+this.value3+"@"+this.value4+"@"+bal_os+"@"+sum_od
										+"@"+sum_oc+"@"+formatNumber(parseFloat(bal_os)+parseFloat(sum_od)-parseFloat(sum_oc),2,0)
												
										)
								
							}else{
								paraVo.push(
										this.value1+"@"+this.value2+"@"+this.value3+"@"+this.value4+"@"+bal_os+"@"+sum_od
										+"@"+sum_oc+"@"+formatNumber(parseFloat(bal_os)-parseFloat(sum_od)+parseFloat(sum_oc),2,0)
										)
							}
						} 
						  
				 })
				   formPara={
						  
						  subj_code:liger.get("subj_code").getValue(),
						  
				  	      check_data:paraVo.toString(),
				  	      
				  	      para:para,
				  	      
				  	      bal_os:$("#bal_os").val().replace(/\,/g, ""),
				  	      
				  	      sum_od:$("#sum_od").val().replace(/\,/g, ""),
				  	    
				  	      sum_oc:$("#sum_oc").val().replace(/\,/g, ""),
				  	  
				  	      end_os:$("#end_os").val().replace(/\,/g, ""),
				  	      
				  	      show_sum:show_sum,
				  	      
				  	      is_check:1
				  	      
				  };
				  console.log("para", para);
				  
		     }
    	
    	
	    ajaxJsonObjectByUrl("addAccLeder.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	
                query();
                
               // is_addRow();
                
            }
        }); 
	     
	  
  }
    function getRequestURL(check_code){
		
		var url;

		if (check_code == "dept_name"){
			
			url = "queryDeptDict.do?isCheck=false";
			
		}else if(check_code == "emp_name"){
			
			url = "queryEmp.do?isCheck=false";
			
		}else if(check_code == "proj_name"){
			
			url = "queryProjDictDict.do?isCheck=false";
			
		}else if(check_code == "store_name"){
			
			url = "queryStoreDictDict.do?isCheck=false";
			
		}else if(check_code == "cus_name"){
			
			url = "queryCusDict.do?isCheck=false";
			
		}else if(check_code == "sup_name"){
			
			url = "querySupDictDict.do?isCheck=false";
			
		}else if(check_code == "source_name"){
			
			url = "querySourceDict.do?isCheck=false";
			
		}else if(check_code == "hos_name"){
			
			url = "queryAccHosInfo.do?isCheck=false";
		}else{
			
			url = "queryCheckItem.do?isCheck=false";
			
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
        	
         liger.get("subj_code").setValue('${subj_code}');
           
         liger.get("subj_code").setText('${subj_code}'+' '+'${subj_name}');
         
         if(dire == 0){
        	 $("#subj_dire_text").text('借');	
           
         }else{
        	 $("#subj_dire_text").text('贷');	
           
         }
        	 
		$("#bal_os").val(formatNumber(parseFloat('${bal_os}'),2,1));
       	 
       	 $("#sum_od").val(formatNumber(parseFloat('${sum_od}'),2,1));
       	 
       	 $("#sum_oc").val(formatNumber(parseFloat('${sum_oc}'),2,1));
       	 
       	 $("#end_os").val(formatNumber(parseFloat('${end_os}'),2,1));
         
	}   
    function loadForm(){
      	
    	$("form").ligerForm();
      
    }  
    
    function changeMoney(){
    	
    	var bal_os = $("#bal_os").val();
    	
    	var sum_od = $("#sum_od").val();
    	
    	var sum_oc = $("#sum_oc").val();

		if(dire==0){
    		
    		$("#end_os").val(formatNumber(parseFloat(Number(bal_os.replace(/\,/g, ""))+Number(sum_od.replace(/\,/g, ""))-Number(sum_oc.replace(/\,/g, ""))),2,1));
    		
    	}else{
    		
    		$("#end_os").val(formatNumber(parseFloat(Number(bal_os.replace(/\,/g, ""))+Number(sum_oc.replace(/\,/g, ""))-Number(sum_od.replace(/\,/g, ""))),2,1));
    	}
    	
    	$("#bal_os").val(formatNumber(bal_os,2,1));
    	
    	$("#sum_od").val(formatNumber(sum_od,2,1));
    	
    	$("#sum_oc").val(formatNumber(sum_oc,2,1));
    	
    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;" >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<h4>科目初始账（金额方向：<font id="subj_dire_text"></font>）</h4>
	<input type="hidden" id="subj_dire" name="subj_dire" value="${subj_dire}"/>
	<form name="form1" method="post"  id="form1" >
	<center>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目名称：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年初余额：</td>
            <td align="left" class="l-table-edit-td"><input name="bal_os" type="text" id="bal_os" ltype="text" style="text-align:right" onchange="changeMoney();" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计借方：</td>
            <td align="left" class="l-table-edit-td"><input name="sum_od" type="text" id="sum_od" ltype="text" style="text-align:right" onchange="changeMoney();"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">累计贷方：</td>
            <td align="left" class="l-table-edit-td"><input name="sum_oc" type="text" id="sum_oc" ltype="text" style="text-align:right" onchange="changeMoney();" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初余额：</td>
            <td align="left" class="l-table-edit-td"><input name="end_os" type="text" id="end_os"  ltype="text" style="text-align:right" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"></td>
            <td align="left" class="l-table-edit-td"></td>
            <td align="left"></td>
        </tr> 
	 
    </table>
    </center>
    </form>
     <center id="dire0">公式说明：年初余额+累计借方-累计贷方=期初余额</center>
     <center id="dire1" style="display: none;">公式说明：年初余额+累计贷方-累计借方=期初余额</center>
	<div id="toptoolbar" ></div>
   
   
	<div id="bottom">
	<h4>辅助核算初始账</h4>
	<div id="maingrid"></div>
	</div>

</body>
</html>
