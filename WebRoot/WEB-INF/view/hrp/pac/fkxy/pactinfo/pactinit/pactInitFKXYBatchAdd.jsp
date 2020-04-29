<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script type="text/javascript">
var rowindex = '${rowindex}';//行号
//var proofData = parentFrameUse().grid.getRow(rowindex).detailData;//论证信息数据
var etTab,subject_type,sup_no,inv_category,mat_store,grid;
var  sup_no = '${sup_no}';
var query = function (){

	
	params = [
             
              { name: 'sup_no', value: $("#sup_no").val() },
              //新增条件
              { name: 'mat_store', value: $("#mat_store").val().split(",")[0] },
              { name: 'inv_category', value: $("#inv_category").val() }
           
          ];
          grid.loadData(params);
}
/**
 * 获取页面路径传递的参数
 * @param name
 * @returns
 */

 	
var initSelect=  function(){
	
  	sup_no = $("#sup_no").etSelect({url: '../../../basicset/select/queryHosSupDictSelect.do?isCheck=false',defaultValue: "${sup_no}"}); ///设置默认值
  
  	mat_store = $("#mat_store").etSelect({url: '../../../basicset/select/queryMatStoreAll.do?isCheck=false',defaultValue: "none"});
  	inv_category = $("#inv_category").etSelect({url: '../../../basicset/select/queryMatType.do?isCheck=false',defaultValue: "none"});
}
var initGrid = function () {
    var columns = [
               {display:'材料id', name:'subject_id',align:'center',hidden:true},
				  { display: '材料编码', name: 'subject_code', align: 'center', width: '8%'},
				 { display: '材料名称', name: 'subject_name', align: 'center', width: '20%'},
				{ display: '规格', name: 'item_spec', align: 'center', width: '8%'},
				{ display: '厂商id', name: 'fac_id', align: 'center', hidden: true},
				{ display: '厂商', name: 'fac_name', align: 'center', width: '12%'},
				{ display: '计量单位', name: 'unit_name', align: 'center', width: '8%'},
				{ display: '参考单价', name: 'price', align: 'center', width: '8%'}             
			 ];
    var paramObj = {
        	editable: false,
        	height: '97%',
        	width:'100%',
            dataModel: {
                url: 'queryPactFKXYMaterial.do?isCheck=false&sup_no='+sup_no ///查询方法
            },
            checkbox: true,
            columns: columns,
            toolbar: {
                items: [
                    { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    { type: 'button', label: '保存',  listeners: [{ click: add }],  icon: 'add' },
                ]
            }
        };     
    grid = $("#mainGrid").etGrid(paramObj);
    }
//保存方法
var add =function (){
	
	var data = grid.selectGetChecked();
	///var str=JSON.stringify(data)
	///alert(str)
	 var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
    var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	if(data.length>0 && data.length!=""){
		$(data).each(function(){
			this.rowData.item_name = this.rowData.subject_name;
			parentWindow.subGrid.addRow(this.rowData);
		})
		this_close();
	}else {
		
		 $.etDialog.error("请选择数据明细！") ;return
	}
			
}
function this_close(){
	 var curIndex = parent.$.etDialog.getFrameIndex(window.name);
     parent.$.etDialog.close(curIndex);

}
$(function () {
	//query();
    //initfrom();
    initGrid();
    initSelect();
    //query();
})

</script>
</head>
<body>
 <table class="table-layout">
  <tr>  <td class="label" style="width: 100px;">供应商：</td>
            <td class="ipt"> <select id="sup_no" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">仓库：</td>
            <td class="ipt"><select id="mat_store" style="width: 180px"></select> </td>
            <td class="label" style="width: 100px;">物资类别：</td>
            <td class="ipt"><select id="inv_category" style="width: 180px"></select> </td>
        </tr>
 </table>
  <div id="mainGrid"></div>
</body>
</html>