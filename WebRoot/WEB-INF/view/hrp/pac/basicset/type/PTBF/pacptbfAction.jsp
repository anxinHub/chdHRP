<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
	</jsp:include>
	<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script>
var grid;
var gridManager=null;
var BB_Button;
var BF_Type1,BF_Type2;
var str1= "";
var str2= "";
var query = function () {
    params = [
        { name: 'BB_ButtonFunction', value: $("#BB_Button").val()  }
    ];
    grid.loadData(params);
	
};

var initSelect=  function(){
	
	//$("#BB_ButtonFunction").etSelect({url: '../../../basicset/select/queryBtenDictSelect.do?isCheck=false',defaultValue: "none"});
	var natureSelect = $("#BB_Button").etSelect({url: "../../select/queryBtenDictSelect.do?isCheck=false" ,defaultValue: "none"});
		//alert(33)
		
	/*var str1 = '<td colspan="2" style="text-align:left; align:left" style="width: 100px;">函数体：</td>';
	var str2 = '<td style="width: 735px;" colspan="2"><textarea id="BF_Body"  style="width: 500px;height: 150px"></textarea></td>';
	$('#headerTitle1').html(str1);
	$('#headerBody1').html(str2); 
	*/
	
}
  var initGrid = function () {
      var columns = [
           { display: 'bf_rowid', name: 'bf_rowid', width: '80px',hidden:true},
           { display: 'bb_rowid', name: 'bb_rowid', width: '80px',hidden:true},
      	   { display: '按钮编码', name: 'bb_buttoncode', width: '200px'},
           { display: '按钮名称', name: 'bb_button', width: '200px'}
      ];
      var paramObj = {
      	editable: true,
      	height: '99%',
      	width:'100%',
      	checkbox: true,
      	selectionModel:{
      		type: "cell", 
      		mode:"single"
      	},
          dataModel: {
             url: 'queryPactPtbf.do?isCheck=false'
          },
          rowClick:  rowClick_f,
          rowSelect:  rowClick_f, 
          columns: columns,
          toolbar: {
              items: [
                  { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                  { type: 'button', label: '新增', listeners: [{ click: add }], icon: 'add' },
                  { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                  { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'del' },

              ]
          }
      };
      grid = $("#maingrid").etGrid(paramObj);
  };
  
 
 function add(){
	 grid.addRow();
	 initfrom(); 
	 
 }
 
 
 ///表格行点击时间，加在右边内容
 var rowClick_f=function(event,ui){
	               grid.setSelection(ui.rowIndx,true,true);
				   var BF_Rowid=ui.rowData.bf_rowid;
				   if(!BF_Rowid){BF_Rowid=0;}
				   //alert(con_id);
				   $("#BF_Rowid").val(BF_Rowid);
				   //alert(BF_Rowid);
				    if(BF_Rowid!=0){
				   ajaxPostData({
			         url: 'queryPactPtbfId.do?isCheck=false',
			         data: {
			        	 BF_Rowid:BF_Rowid
			                         
			         },
			         success: function (response) {
			
			      	 if(response.BF_Class){
			      		
				     	  str1 ="";
			      		  str2 ="";
				     	  $("input[name='con_radio'][value="+response.BF_Type+"]").prop("checked",true); 
			      		  if (response.BF_Type=='1'){
			      			str1 = '<td colspan="2" style="text-align:left align="left" style="width: 100px;">SQL：</td>';
							str2 = '<td style="width: 735px;" colspan="2"><textarea id="BF_SQL"  style="width: 500px;height: 150px"></textarea></td>';
							$('#headerTitle').html(str1);
							$('#headerBody').html(str2);
							$("#BF_SQL").val(response.BF_SQL);
			      		  }else{
			      			str1 = '<td colspan="2" style="text-align:left; align:left" style="width: 100px;">函数体：</td>';
							str2 = '<td style="width: 735px;" colspan="2"><textarea id="BF_Body"  style="width: 500px;height: 150px"></textarea></td>';
							$('#headerTitle').html(str1);
							$('#headerBody').html(str2);
							$("#BF_Body").val(response.BF_Body);
				     	   }
						$("#BF_Class").val(response.BF_Class);
						$("#BF_Method").val(response.BF_Method);
						$("#BF_Input").val(response.BF_Input);
						$("#BF_Output").val(response.BF_Output);
					
					} else {
						$("#BF_Class").val('');
						$("#BF_Method").val('');
						$("#BF_Input").val('');
						$("#BF_Body").val('');
						$("#BF_Output").val('');
						$("#BF_SQL").val('');
					}

				},
				error : function() {
					//alert("shibai");
					$("#BF_Class").val('');
					$("#BF_Method").val('');
					$("#BF_Input").val('');
					$("#BF_Body").val('');
					$("#BF_Output").val('');
					$("# BF_SQL").val('');
				}
			});

		} else {
			$("#BF_Class").val('');
			$("#BF_Method").val('');
			$("#BF_Input").val('');
			$("#BF_Body").val('');
			$("#BF_Output").val('');
			$("#BF_SQL").val('');

		}

	};
	//跳转保存页面
	var save = function(data) {
		var BF_Method = $("#BF_Method").val();
		var BF_Type = $("input[name='con_radio']:checked").val();
		var BF_Body = "";
		var BF_SQL = "";
		if (BF_Type == '1') {
			BF_SQL = $("#BF_SQL").val();
			BF_Body = "0";
		} else {
			BF_Body = $("#BF_Body").val();
			BF_SQL = "0";
		}
		
		if (BF_Method.length > 500) {
			$.etDialog.error('函数内容控制在500字内');
			return;
		}

		var param = [];
		var data = grid.getAllData();
		if (data.length == 0) {
			$.etDialog.error('请添加数据');
		}
		formValidate = $.etValidate({
			items : [ {
				el : $("#BF_Class"),
				required : true
			}, {
				el : $("#BF_Method"),
				required : true
			}, {
				el : $("#BF_Input"),
				required : true
			},
			//{el : $("#BF_Body"),required : true},
			{
				el : $("#BF_Output"),
				required : true
			} ]
		});
		var row_data = grid.pqGrid("selection", {
			method : 'getSelection',
			type : 'row'
		}); //获取编辑行的数据
		var BF_Rowid = row_data[0].rowData.bf_rowid;
		var BB_Button = row_data[0].rowData.bb_button;
		var BB_ButtonCode = row_data[0].rowData.bb_buttoncode;
		var BB_Rowid = row_data[0].rowData.bb_rowid;
		if (!formValidate.test()) {
			return;
		}
		;
		var urlstr = "";
		if ((BF_Rowid > 0) && (BB_Rowid > 0)) {
			urlstr = 'updatePacPtbfAction.do?isCheck=false';
		} else {
			urlstr = 'savePacPtbfAction.do';
		}
		ajaxPostData({
			url : urlstr,
			data : {
				BF_Class : $("#BF_Class").val(),
				BF_Method : $("#BF_Method").val(),
				BF_Input : $("#BF_Input").val(),
				BF_Body : BF_Body,
				BF_Output : $("#BF_Output").val(),
				BF_Rowid : $("#BF_Rowid").val(),
				BF_SQL : BF_SQL,
				BF_Type : BF_Type,
				BB_ButtonCode : BB_ButtonCode,
				BB_Button : BB_Button,
				BB_Rowid : BB_Rowid,
				BF_Rowid : BF_Rowid
			},
			success : function() {
			
				query();
				//personelids=[];
				//personelidns=[];

			}
		})
	};

	//删除
	var del = function() {
		var data = grid.selectGet();
		if (data.length == 0) {
			$.etDialog.error('请选择行');
		} else {

			if (!data[0].rowData.bf_rowid) {
				// $.etDialog.error('暂无数据可删除');
				grid.deleteRows(data);
				return;
			}
			$.etDialog.confirm('确定删除?', function() {
				ajaxPostData({
					url : 'deletePacPtbfAction.do',
					data : {
						group_id : data[0].rowData.group_id,
						hos_id : data[0].rowData.hos_id,
						copy_code : data[0].rowData.copy_code,
						BF_Rowid : data[0].rowData.bf_rowid,
						BB_Rowid : data[0].rowData.bb_rowid
					},
					success : function() {
						grid.deleteRows(data);

						$("#BF_Class").val('');
						$("#BF_Method").val('');
						$("#BF_Input").val('');
						$("#BF_Body").val('');
						$("#BF_Output").val('');
						$("#BF_Rowid").val('0');
						$("#BB_Rowid").val('0');
						$("#BF_Type1").val('');
						$("#BF_Type2").val('');
						$("#BF_SQL").val('');
						BB_ButtonCode.setValue("");
						BB_Button.setValue("");
						BB_Button.setValue("");
						query();
					}
				})
			});
		}
	};

	$(function() {
		initSelect();
		initfrom();
		initGrid();
		
	})

	var initfrom = function() {
   	        $("input[name='con_radio'][value='1']").prop("checked",true); 
			str1 = '<td colspan="2" style="text-align:left align="left" style="width: 100px;">SQL：</td>';
			str2 = '<td style="width: 735px;" colspan="2"><textarea id="BF_SQL"  style="width: 500px;height: 150px"></textarea></td>';
			$('#headerTitle').html(str1);
			$('#headerBody').html(str2);
			$("input[name='con_radio']").change(function(){
		
				var val = $(this).val();
				  $("input[name='con_radio'][value="+val+"]").prop("checked",true); 
				 if (val=='1'){
		   			    str1 = '<td colspan="2" s  tyle="text-align:left align="left" style="width: 100px;">SQL：</td>';
						str2 = '<td style="width: 735px;" colspan="2"><textarea id="BF_SQL"  style="width: 500px;height: 150px"></textarea></td>';
						$('#headerTitle').html(str1);
						$('#headerBody').html(str2);
		   		  }else if(val=='2'){
		   			    str1 = '<td colspan="2" style="text-align:left; align:left" style="width: 100px;">函数体：</td>';
						str2 = '<td style="width: 735px;" colspan="2"><textarea id="BF_Body"  style="width: 500px;height: 150px"></textarea></td>';
						$('#headerTitle').html(str1);
						$('#headerBody').html(str2);
			     }
			});
	}
	//raido点击事件

</script>
</head>

<body  style="overflow: scroll; ">
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
    
       <tr>
            <td class="label" style="width: 100px;">数据源：</td>
            <td class="ipt">
                <select id="BB_Button" style="width: 180px"></select>
            </td>
        </tr>
    
    </table>
     
         <div class="container" style="width: 100%;" > 
	     <div align="left" style="width: 40%" >
            <div id="maingrid"></div>
        </div>
        
       <div align="left" style="width: 60%;border: 1px solid #a3c0e8;
    margin-left: 8px;" id="con" >  
       <table  class="table-layout">
        <tr >
       
        <td style="width:100px;"><input type="radio" id="BF_Type1"  name="con_radio" value="1" />查询类</td>
        <td style="width:100px;"><input type="radio" id="BF_Type2"  name="con_radio" value="2"/>生成类</td>
         </tr>
       <tr>
       <td  style="text-align:left"  align="left" style="width: 100px;">类名：
       <input id="BF_Class" type="text" style="width: 200px;"/></td>
       <td  style="text-align:left" align="left" style="width: 100px;">方法名：
       <input id="BF_Method" type="text" style="width: 100px;"/></td>
       </tr>
       <tr >
       <td colspan="2" style="text-align:left" align="left"   style="width: 500px;">数据源入参：</td>
       </tr>
       <tr>
       <td colspan="2" ><input id="BF_Input" type="text" style="width: 500px;"/></td>
       </tr>
        <tr id="headerTitle">
       <!--  <td colspan="2" style="text-align:left; align:left" style="width: 100px;">函数体：</td> -->
       </tr>
       <tr id="headerBody">
       <!--  <td style="width: 735px;" colspan="2"><textarea id="BF_Body"  style="width: 500px;height: 150px"></textarea></td>       -->
       </tr>
     <!--   <tr id="headerTitle2">
         <td colspan="2" style="text-align:left align="left" style="width: 100px;">SQL：</td>
       </tr>
         <tr id="headerBody2">
         <td style="width: 735px;" colspan="2"><textarea id="BF_SQL"  style="width: 500px;height: 150px"></textarea></td>     
       </tr> -->
       <tr >
       <td colspan="2" style="text-align:left align="left" style="width: 100px;">数据源返回值参数：</td>
       </tr>
       <tr>
       <td colspan="2" ><input id="BF_Output" type="text" style="width: 500px;;height: 50px"/></td>
       </tr> 
       </table>
       </div>
       
     </div>
    
</body>
</html>