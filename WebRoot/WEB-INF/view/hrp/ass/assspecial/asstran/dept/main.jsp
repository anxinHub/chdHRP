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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var show_detail ;
    $(function ()
    {
        loadDict();//加载下拉框-
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 query();
		 showDetail();
		 show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		
    });
    //查询
    function  query(){
    	  grid.options.parms=[];
    	  grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'in_dept_id',value:liger.get("in_dept_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'in_dept_no',value:liger.get("in_dept_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'out_dept_id',value:liger.get("out_dept_id").getValue().split("@")[0]}); 
    	  grid.options.parms.push({name:'out_dept_no',value:liger.get("out_dept_id").getValue().split("@")[1]}); 
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  grid.options.parms.push({name:'create_date_beg',value:$("#create_date_beg").val()}); 
    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()}); 
    	  grid.options.parms.push({name:'audit_date_beg',value:$("#audit_date_beg").val()}); 
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
    	  grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	//加载查询条件
    	  grid.loadData(grid.where);
     }

    function loadHead(){
    	if(show_detail == "1"){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '移库单号', name: 'tran_no', align: 'left',width : 120,
						render : function(rowdata, rowindex,
								value) {
							if(rowdata.in_dept_name == "合计"){
								return '';
							}
							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
							rowdata.hos_id   + "|" + 
							rowdata.copy_code   + "|" + 
							rowdata.tran_no +"')>"+rowdata.tran_no+"</a>";
						}
					 		}, 
					 {display : '摘要',name : 'note',align : 'left',width : 150
							},
                     { display: '移入科室', name: 'in_dept_name', align: 'left',width : 100,
					 		},
                     { display: '移出科室', name: 'out_dept_name', align: 'left',width : 100,
					 		},
					 { display : '卡片号', name : 'ass_card_no' , align : 'left',width : 100,
					 		},		
					 {display : '资产编码',name : 'ass_code',align : 'left',width : 100,
							},
					 {display : '资产名称',name : 'ass_name',align : 'left',width : 100,
							},
					 {display : '资产规格',name : 'ass_spec',align : 'left',width : 100,
							},
					 {display : '资产型号',name : 'ass_mondl',align : 'left',width : 100,
							}, 
					 {display : '资产品牌',name : 'ass_brand',align : 'left',width : 100,
							},
					 {display : '数量',name : 'ass_amount',align : 'left',width : 100,
							},
					 {display : '金额',name : 'price',align : 'left',width : 100,
								render: function(item)
					            {
					                    return formatNumber(item.price,'${ass_05005}',1);
					            }
							},
                     { display: '制单人', name: 'create_emp_name', align: 'left',width : 100,
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left',width : 100,
					 		},
                     { display: '确认人', name: 'audit_emp_name', align: 'left',width : 100,
					 		},
                     { display: '确认日期', name: 'audit_date', align: 'left',width : 100,
					 		},
                     { display: '状态', name: 'state_name', align: 'left',width : 100,
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssTranDeptSpecial.do?isCheck=false&show_detail=1',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						                {
											line : true
										},{
											text : '移库确认（<u>Q</u>）',
											id : 'updateConfirm',
											click : updateConfirm,
											icon : 'ok'
										},{
											line : true
										},
										{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
										{ line:true } ,
										{ text: '批量打印', id:'print', click: print, icon:'print' }

				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.tran_no 
							);
    				} 
                   });
    	}else{
    		grid = $("#maingrid").ligerGrid({
    	           columns: [ 
    	                     { display: '移库单号', name: 'tran_no', align: 'left',
    							render : function(rowdata, rowindex,
    									value) {
    								return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
    								rowdata.hos_id   + "|" + 
    								rowdata.copy_code   + "|" + 
    								rowdata.tran_no +"')>"+rowdata.tran_no+"</a>";
    							}
    						 		}, 
    	    				 {display : '摘要',name : 'note',align : 'left',width : 150
     								},
    	                     { display: '移入科室', name: 'in_dept_name', align: 'left'
    						 		},
    	                     { display: '移出科室', name: 'out_dept_name', align: 'left'
    						 		},
    	                     { display: '制单人', name: 'create_emp_name', align: 'left'
    						 		},
    	                     { display: '制单日期', name: 'create_date', align: 'left'
    						 		},
    	                     { display: '确认人', name: 'audit_emp_name', align: 'left'
    						 		},
    	                     { display: '确认日期', name: 'audit_date', align: 'left'
    						 		},
    	                     { display: '状态', name: 'state_name', align: 'left'
    						 		}
    	                     ],
    	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssTranDeptSpecial.do?isCheck=false&show_detail=0',
    	                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
    	                     selectRowButtonOnly:true,//heightDiff: -10,
    	                     toolbar: { items: [
    	                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
    	                     	{ line:true },
    					    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    					    	                { line:true },
    					    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
    							                {
    												line : true
    											},{
    												text : '移库确认（<u>Q</u>）',
    												id : 'updateConfirm',
    												click : updateConfirm,
    												icon : 'ok'
    											},{
    												line : true
    											},
    											{ text: '模板设置', id:'printSet', click: printSet, icon:'print' },
    											{ line:true } ,
    											{ text: '批量打印', id:'print', click: print, icon:'print' }

    					    				]},
    	    				onDblClickRow : function (rowdata, rowindex, value)
    	    				{
    							openUpdate(
    									rowdata.group_id   + "|" + 
    									rowdata.hos_id   + "|" + 
    									rowdata.copy_code   + "|" + 
    									rowdata.tran_no 
    								);
    	    				} 
    	                   });
    	}

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	parent.$.ligerDialog.open({
    		title: '科室移库添加',
    		height: $(window).height(),
    		width: $(window).width(),
    		url: 'hrp/ass/assspecial/asstran/dept/assTranDeptSpecialAddPage.do?isCheck=false&',
    		modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
    		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	});

    	}
	function updateConfirm(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.tran_no );
						
					});
			$.ligerDialog.confirm('确定移库?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateConfirmTranDeptSpecial.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.tran_no
							); });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssTranDeptSpecial.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3] || "undefined"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			" copy_code="+vo[2]   +"&"+ 
			"tran_no="+vo[3]; 
		
		parent.$.ligerDialog.open({
    		title: '科室移库修改',
    		height: $(window).height(),
    		width: $(window).width(),
    		url: 'hrp/ass/assspecial/asstran/dept/assTranDeptSpecialUpdatePage.do?isCheck=false&'+ parm,
    		modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
    		parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
    	});
    
    }
    function loadDict(){
    	
		var param = {
            	query_key:''
        }; 
		
    	autocomplete("#out_dept_id", "../../../queryDeptDict.do?isCheck=false","id", "text",true,true,param,true);
    	
    	autocomplete("#in_dept_id", "../../../queryDeptDict.do?isCheck=false","id", "text",true,true,param,true);
    	
    	autocomplete("#create_emp", "../../../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", false, false, false, false);
    	
		    $("#create_date_beg").ligerTextBox({width:90});
		    
	        $("#audit_date_beg").ligerTextBox({width:90});
	        
	        $("#create_date_end").ligerTextBox({width:90});
	        
	        $("#audit_date_end").ligerTextBox({width:90});
	        
	        $("#ass_name").ligerTextBox({width:230});
	        
	       /*  $("#state").ligerComboBox({width:160}); */
	        $('#state').ligerComboBox({
				data:[{id:0,text:'新建'},{id:2,text:'移库确认'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width:160
			});
	        autodate("#create_date_beg","YYYY-mm-dd","month_first");

	 		autodate("#create_date_end","YYYY-mm-dd","month_last");
         } 
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);
		
		hotkeys('Q', updateConfirm);

		hotkeys('P', print);
		

	 }
	//打印模板设置
	    function printSet(){
		  
	    	var useId=0;//统一打印
			if('${ass_05019}'==1){
				//按用户打印
				useId='${user_id }';
			}/* else if('${ass_05019}'==2){
				//按仓库打印
				if(liger.get("store_id").getValue()==""){
					$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
					return;
				}
				useId=liger.get("store_id").getValue().split(",")[0];
			} */
	    	
			officeFormTemplate({template_code:"0508802",use_id : useId});
	    }


	    //打印
	    function print(){
	    	var useId=0;//统一打印
	 		if('${ass_05019}'==1){
	 			//按用户打印
	 			useId='${user_id }';
	 		}else if('${ass_05019}'==2){
	 			//按仓库打印
	 			if(liger.get("store_id").getValue()==""){
	 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
	 				return;
	 			}
	 			useId=liger.get("store_id").getValue().split(",")[0];
	 		}

	 		var data = gridManager.getCheckedRows();
			if (data.length == 0){
				$.ligerDialog.error('请选择行');
			}else{
			
				var tran_no ="" ;
				$(data).each(function (){
					
					tran_no  += "'"+this.tran_no+"',"
						
				});
				
				 var para={
		    			paraId :tran_no.substring(0,tran_no.length-1) ,
		    			class_name:"com.chd.hrp.ass.serviceImpl.tran.dept.AssTranDeptSpecialServiceImpl",
		    			method_name:"assTranDeptSpecialByPrintTemlate",
		    			template_code:'0508802',
		    			isPrintCount:false,//更新打印次数
		    			isPreview:true,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:1
		    			//isSetPrint:flag
		    	 }; 
			 
	           	ajaxJsonObjectByUrl("queryState.do?isCheck=false",{paraId :tran_no.substring(0,tran_no.length-1) },function (responseData){
	           		if(responseData.state=="true"){
	           		   officeFormPrint(para);
	           		}
	           	});
		   }
	    }
	  //是否显示明细
	    function showDetail() {
			show_detail = $("#show_detail").is(":checked") ? 1 : 0;
			$("#batch_no").val();
			if (grid) {
				//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
				grid.unbind(); 
			}
			loadHead();
			//query();
		}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="create_date_beg" type="text" id="create_date_beg" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left" width="2%">至：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date_end" type="text" id="create_date_end" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td" >
            	<!-- <select id="state" name="state">
            		<option value="">全部</option>
            		<option value="0">新建</option>
            		<option value="2">移库确认</option>
            	</select> -->
            	<input  name="state" type="text" id="state"/>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">移出科室：</td>
            <td align="left" class="l-table-edit-td"><input name="out_dept_id" type="text" id="out_dept_id"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date_beg" type="text" id="audit_date_beg" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left">至：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date_end" type="text" id="audit_date_end" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_beg\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">移入科室：</td>
            <td align="left" class="l-table-edit-td"><input name="in_dept_id" type="text" id="in_dept_id"  /></td>
            <td align="left"></td>
        </tr> 
        <tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">资产名称：</td>
			 <td align="left"  class="l-table-edit-td" colspan = "3"><input name="ass_name" type="text" id="ass_name" /></td>
		</tr>
        <tr>
			<td align="right" class="l-table-edit-td" width="10%">
			</td>
			<td align="left" class="l-table-edit-td" >
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
             </td>
		</tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
