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
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
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
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_select").getValue().split("@")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_select").getValue().split("@")[1]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#kind_code").val()!=""){
                		return rowdata.kind_code.indexOf($("#kind_code").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科室名称', name: 'dept_id_name', align: 'left',width:100
					 },
		             { display: '岗位名称', name: 'station_name', align: 'left',width:100
					 },
                     { display: '定岗人数', name: 'station_num', align: 'left',width:100
					 },
                     { display: '实际人数', name: 'act_num', align: 'left',width:100
					 },
                     { display: '岗位差额', name: 'diff_num', align: 'left',width:100
					 },
                     { display: '预计退休人数', name: 'note', align: 'left',width:100
					 },
                     { display: '预计招聘人数', name: 'note', align: 'left',width:100
					 },
                     { display: '招聘状态', name: 'demand_state', align: 'left',width:100,
						 render : function(rowdata, rowindex,
									value) {
								if(rowdata.demand_state == '01'){
									return "审核中";
								}
								if(rowdata.demand_state == '02'){
									return '通过'
								}
								if(rowdata.demand_state == '03'){
									return '拒绝'
								}
								if(rowdata.demand_state == '04'){
									return '删除'
								}
								if(rowdata.demand_state == '05'){
									return '存档'
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryStationInfo.do?isCheck=false&tab_code=HR_STATION_DESCRIPTION',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
						{ text: '查询', id:'search', click: query,icon:'search' },
						{ line:true },
    	                { text: '生成招聘岗位', id:'genPosit', click: itemclick,icon:'add' }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "genPosit":
                	var data = gridManager.getCheckedRows();
                	var err ="";
            		if (data.length == 0) {
            			$.ligerDialog.error('请选择行');
            		} else {
            			var paramVo = [];
            			$(data).each(function() {
//             						if(this.demand_state == '01'){
            								
            								paramVo.push({
            	        						demand_id: this.demand_id,
            	        						group_id: this.group_id,
            	        						hos_id: this.hos_id,
            	        						dept_id :  this.dept_id,
            	        						dept_no : this.dept_no,
            	        						station_code :  this.station_code,
            	        						station_name :  this.station_name,
            	        						form_code : this.kind_code,
            	        						demand_major :  this.discipline,
            	        						
            	        						demand_age :  this.age,
            	        						demand_edu :  this.education,
            	        						demand_deg : this.academic,
            	        						
//             	        						demand_cet :this.,
//             	        						demand_sex : this.,
            	        						demand_qualify : this.technique,
            	        						demand_require : this.interpersonal,
            	        						demand_state: '01'
            	        					});
//             						}else{
//             							if(err == ""){
//             								err = this.row_id;
//             							}else{
//             								err = err+ "、"+this.row_id;
//             							}
//             						}
            					});
            			if (err != "") {
            				$.ligerDialog.warn("第["+err+"]行招聘岗位已经生成不是审核中状态，生成招聘岗位失败！");
            				return;
            			}
            			$.ligerDialog.confirm(
            							'确定生成招聘岗位?',
            							function(yes) {
            								if (yes) {
            									ajaxJsonObjectByUrl(
            											"addRecruitDemand03Batch.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND",
            											{
            												ParamVo : JSON.stringify(paramVo)
            											},
            											function(responseData) {
            												if (responseData.state == "true") {
            													parent.query();
            													query();
            												}
            											});
            								}
            							});
            		}
                	
                    return;  
            }   
        }
        
    }
	
    function openUpdate(obj){
    	
		var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&kind_code="+vo[2];
		
    	$.ligerDialog.open({ url : 'demandUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 500, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveDeptKind(); },cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#dept_select",
				"../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT", "id", "text",
				true, true, "", false, null, "180");
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室名称：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_select" type="text" id="dept_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
