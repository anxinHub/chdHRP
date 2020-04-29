<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	 <jsp:param value="select,datepicker,grid,dialog,ligerUI" name="plugins"/>
</jsp:include>
<script type="text/javascript">
    var grid;
    $(function (){
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		init();
    });
    
	var year_input,dept_name_select,emp_type_select,awards_item_select

	function init(){
		getData("../../queryBudgYear.do?isCheck=false",function(data){
			year_input = $("#year_input").etDatepicker({
				defaultDate: data[0].text,
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				maxDate:data[0].text,
				minDate:  data[data.length - 1].text,
				todayButton: false,
				onChanged: queryNew
			});
		});

		dept_name_select =$("#dept_name_select").etSelect({
			url:'../../queryBudgDeptDictAll.do?isCheck=false',
			defaultValue:"none",
			onChange: queryNew
		});

		emp_type_select = $("#emp_type_select").etSelect({
			url:'../../queryEmpTypeAll.do?isCheck=false',
			defaultValue:"none",
			onChange: queryNew
		});
		
		awards_item_select = $("#awards_item_select").etSelect({
			url:'../../queryBudgAwardsItemAll.do?isCheck=false',
			defaultValue:"none",
			onChange: queryNew
		});
	}

	//ajax获取数据
	function getData(url,callback){
		$.ajax({
			url:url,
			dataType:"JSON",
			type:"post",
			success:function(res){
				if(typeof callback ==="function"){
					callback(res);
				}
			}
		})
	}

	function queryNew(){
		var parms = [
			{name:'year',value:year_input.getValue()},
			{name:'dept_id',value:dept_name_select.getValue()},
			{name:'emp_type_code',value:emp_type_select.getValue()},
			{name:'awards_item_code',value:awards_item_select.getValue()}
		]
		//加载查询条件
		grid.loadData(parms,"queryBudgEmpTypeAwards.do");
	}

    //获取查询条件的数值
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [ 
              { display: '年度', name: 'year', align: 'center',width:"10%",
			  },
              { display: '部门', name: 'dept_name', align: 'left',width:"20%" ,
			  },
              { display: '职工类别', name: 'type_name', align: 'left',width:"20%"
			  },
              { display: '奖金项目', name: 'awards_item_name', align: 'left',width:"30%"
			  },
              { display: '金额', name: 'amount', align: 'right',minWidth:"16%",
				   render:function(ui){
        		   	  return formatNumber(ui.rowData.amount,2,1);
          	  	   }
			  }
          ],
          dataModel:{
           	 method:'POST',
           	 location:'remote',
           	 url:'',
           	 recIndx: 'year' //必填 且该列不能为空  
          },
          usePager:true,width: '100%', height: '100%',checkbox: true,
		  toolbar: {
              items: [
              { type: "button", label: '查询',icon:'search',listeners: [{ click: queryNew}] },
              { type: "button", label: '计算',icon:'calculator',listeners: [{ click: collect}] },
			  { type: "button", label: '删除',icon:'minus',listeners: [{ click: remove}] },
		      //{ type: "button", label: '导入',icon:'arrowthick-1-n',listeners: [{ click: imp}] },
              ]
          }
       });
    }
   
   	function collect(){
		var year =year_input.getValue();
		
   		if(year===''){
   			$.ligerDialog.warn('请选择预算年度');
   			return;
   		}
   		ajaxJsonObjectByUrl("collectBudgYearEmpTypeAwards.do?isCheck=false&year="+year,'',function (responseData){
       		if(responseData.state=="true"){
       			queryNew();
       		}
       	});
   	}
    function remove(){
    	
    	var data = grid.selectGet();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
				ParamVo.push(
					this.rowData.group_id   +"@"+ 
					this.rowData.hos_id   +"@"+ 
					this.rowData.copy_code   +"@"+ 
					this.rowData.year   +"@"+ 
					this.rowData.dept_id   +"@"+ 
					this.rowData.emp_type_code   +"@"+ 
					this.rowData.awards_item_code 
				) 
			});
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteBudgEmpTypeAwards.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			queryNew();
                		}
                	});
            	}
            }); 
        }
   	}
    function imp(){
   		var index = layer.open({
				type : 2,
				title : '导入',
				shadeClose : false,
				shade : false,
				maxmin : true, //开启最大化最小化按钮
				area : [ '893px', '500px' ],
				content : 'budgEmpTypeAwardsImportPage.do?isCheck=false'
			});
		layer.full(index);
   	}	
    function downTemplate(){
    	location.href = "downTemplate.do?isCheck=false";
    }	
   
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', queryNew);
		hotkeys('D', remove);
	 }
  </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
  
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">年度：</td>
				<td class="ipt">
					<input type="text" id="year_input" />
				</td>
				<td class="label">部门名称：</td>
				<td class="ipt">
					<select name="" id="dept_name_select" style="width:180px;"></select>
				</td>
				<td class="label">职工类别：</td>
				<td class="ipt">
					<select name="" id="emp_type_select" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">项目编码：</td>
				<td class="ipt">
					<select name="" id="awards_item_select" style="width:180px;"></select>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="maingrid"></div>
	
</body>
</html>
