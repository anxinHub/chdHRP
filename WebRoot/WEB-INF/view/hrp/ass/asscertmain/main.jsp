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
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
        
		loadHotkeys();
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件 
          grid.options.parms.push({name:'cert_date_begin',value:$("#cert_date_begin").val()}); 
        
    	  grid.options.parms.push({name:'cert_date_end',value:$("#cert_date_end").val()});
    	   
    	  grid.options.parms.push({name:'link_mobile',value:$("#link_mobile").val()}); 
    	  
    	  grid.options.parms.push({name:'cert_inv_name',value:$("#cert_inv_name").val()}); 
    	  
    	  grid.options.parms.push({name:'cert_inv_name',value:$("#cert_inv_name").val()}); 
    	  
    	  grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue().split("@")[0]}); 
    	  
    	  grid.options.parms.push({name:'link_person',value:$("#link_person").val()}); 
    	  
    	  grid.options.parms.push({name:'cert_code',value:$("#cert_code").val()}); 
    	  
    	  grid.options.parms.push({name:'create_user',value:liger.get("create_user").getValue()}); 
    	    
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where); 
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                    
                     { display: '证件编号', name: 'cert_code', align: 'left',width:100,
					 			render : function(rowdata, rowindex,
										value) {
										return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|"  + rowdata.cert_code +"')>"+rowdata.cert_code+"</a>";
								   }
					 		},
					 { display: '证件产品名称', name: 'cert_inv_name', align: 'left',width:150
					 		},		
                     { display: '生产厂商', name: 'fac_name', align: 'left',width:190
					 		},
                     { display: '发证日期', name: 'cert_date', align: 'left',width:120
					 		},
                     { display: '发证机关', name: 'issuing_authority', align: 'left',width:130
					 		},
                     { display: '起始日期', name: 'start_date', align: 'left',width:190
					 		},
                     { display: '截止日期', name: 'end_date', align: 'left',width:190
					 		},
					 { display: '制单人', name: 'create_user_name', align: 'right',width:120
					 		},
					 { display: '联系人', name: 'link_person', align: 'right',width:120
					 		},
                     { display: '联系电话', name: 'link_tel', align: 'left',width:100
					 		},
                     { display: '手机', name: 'link_mobile', align: 'left',width:100
					 		},
                     { display: '证件描述', name: 'cert_memo', align: 'left',width:130
					 		},
                     { display: '存档位置', name: 'file_address', align: 'left',width:100
					 		},
                     { display: '使用状态', name: 'cert_state_name', align: 'left',width:130
					 		},
                     { display: '状态', name: 'state_name', align: 'left',width:100
					 		}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:true,
                     url:'queryAssCertMain.do',
                     width: '100%', 
                     height: '100%', 
                     checkbox: true,
                     rownumbers:true,
                     delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items : [ {
							text : '查询（<u>Q</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						}, {
							line : true
						},
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
											{ line:true }, 
						                {
											text : '审核（<u>S</u>）',
											id : 'toExamine',
											click : toExamine,
											icon : 'ok'
										},{
											line : true
										}, {
											text : '销审（<u>X</u>）',
											id : 'notToExamine',
											click :notToExamine ,
											icon : 'right'
										}
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.cert_code
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    function add_open(){
				parent.$.ligerDialog.open({
					title: '注册证号添加',
					height: $(window).height(),
					width: $(window).width(),
					url: 'hrp/ass/asscertmain/assCertMainAddPage.do?isCheck=false&',
					modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
					parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
				});		
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
							this.cert_code  
							); });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssCertMain.do",
                            			{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function toExamine(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.cert_code  );
						
					});
			$.ligerDialog.confirm('确定审核?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateToExamine.do?isCheck=false", {
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
    function notToExamine(){
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(
					function() {
							ParamVo.push(this.group_id + "@" + this.hos_id + "@"
									+ this.copy_code + "@" + this.cert_code );
						
					});
			$.ligerDialog.confirm('确定销审?', function(yes) {

				if (yes) {
					ajaxJsonObjectByUrl("updateNotToExamine.do?isCheck=false", {
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
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "&group_id="+ 
			 vo[0] +"&hos_id="+ 
			 vo[1] +"& copy_code="+ 
			 vo[2] +"&cert_code="+
			 vo[3];
		parent.$.ligerDialog.open({
			title: '注册证号修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscertmain/assCertUpdatePage.do?isCheck=false&'+ parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
    }
    function loadDict(){
    	
		var param = {
            query_key:''
        }; 
		
		autocomplete("#fac_id", "../queryHosFacDict.do?isCheck=false&is_read=2","id", "text",true,true,param,true,null,"160");
            
		autocomplete("#create_user", "../queryUserDict.do?isCheck=false","id", "text",true,true,param,true,null,"160");
		
        $("#link_mobile").ligerTextBox({width:160});
        
        $("#link_person").ligerTextBox({width:160});
        
        $("#cert_code").ligerTextBox({width:160});
        
        $("#cert_inv_name").ligerTextBox({width:320});
        
        $("#cert_date_begin").ligerTextBox({width:120});
        
        $("#cert_date_end").ligerTextBox({width:120});
        
        $('#state').ligerComboBox({
			data:[{id:0,text:'新建'},{id:1,text:'审核'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});
        
      }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('D', remove);
		hotkeys('S', toExamine);
		hotkeys('X', notToExamine);
	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%;" border="0">
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发证日期：</td>
            <td align="left" class="l-table-edit-td" width="5%"><input name="cert_date_begin" type="text" id="cert_date_begin" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'cert_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td>&nbsp;至：</td>
			<td><input name="cert_date_end" type="text" id="cert_date_end" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'cert_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发证编码：</td>
            <td align="left" class="l-table-edit-td"><input name="cert_code" type="text" id="cert_code" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" /></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发证名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="cert_inv_name" type="text" id="cert_inv_name" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">联系人：</td>
            <td align="left" class="l-table-edit-td"><input name="link_person" type="text" id="link_person" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手机：</td>
            <td align="left" class="l-table-edit-td"><input name="link_mobile" type="text" id="link_mobile" /></td>
         </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
