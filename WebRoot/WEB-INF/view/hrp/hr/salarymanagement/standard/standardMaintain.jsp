<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,dialog,select,grid,datepicker" name="plugins" />
	</jsp:include>
	<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
	<script src="<%=path%>/lib/map.js"></script>
    <script>

	var tab_code,col_code,arrtitle,grid;
    
	var valsele1,valsele2,valsele3,valsele4,valsele5,valsele6,valsele7,valsele8;
	
        var initValidate = function () {
 			
        	  $("#save").click( function () {

				var arr = grid.getAllData();

				var recode = false;
				var codes = "";
				var arrparam =[];
				var keyMap = new HashMap();
				$.each(arr,function(){
					//动态拼接字段
					for(var a = 0; a < arrtitle.length ; a++){
							codes += "this.val"+(a+1)+"||"
					}
					codes += 'this.wage_stan'
					//表格验证
					if(eval(codes)){
						codes = codes.replace(/\|\|/g,'&&')
						if(eval(codes)){
							codes = codes.replace(/\&\&/g,'+').replace("+this.wage_stan","")
							if(keyMap.get(eval(codes))){
								$.etDialog.error("有整行重复的信息!");
								recode = true;
								return;
							}else{
								keyMap.put(eval(codes),true);
								arrparam.push(this)
							}
						}else{
							$.etDialog.error("有数据不完整的行,请检查!");
							recode = true;
							return;
						}
					}
					codes = "";
				})
				
				if(recode){
					return;
				}
				
				var arrform = "stan_id="+'${vo.id}'
				arrform += "&arr="+JSON.stringify(arrparam)
                  ajaxPostData({
                     url: 'addMaintain.do',
                      data:arrform,
                      success: function (responseData) {
                          		query()
                          		parentWindowQuery()
                      },
                   
                  })
              })
            $("#close").click(function () {
                          var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                          parent.$.etDialog.close(curIndex);
            })
        };
  
        var initGrid = function () {
        	
        	var columns2 = [];
        	var counts = 0;
        	$.each(arrtitle,function(index,row){
        		columns2.push({ display: this.COL_NAME, name: 'VALUE'+(++counts), width: 200,
			        			editor: {
			        				type: 'select',
			                        keyField:'val'+counts,
			                        //url:'queryStandardRankodeOption.do?isCheck=false&field_tab_code='+this.FIELD_TAB_CODE,
			                        source:eval('valsele'+counts),
			                    }
        					});
        	})
        	columns2.push({ display: '薪资', name: 'wage_stan', width: 200,
			        		editor: {
			    				type: 'number'
			                }
        					})
            var paramObj = {
                height: $(window).height()-$("#btn").height(),
                width:"99%",
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                columns: columns2,
                showBottom: false,
                pageModel: {type: 'remote',rPP:200,rPPOptions: [50,100,200,500]},
                toolbar: {
                    items: [
                       { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                       { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

	    var add = function () {
	        grid.addRow();
	    };

	    var query = function () {
            params = [];
	    	grid.loadData(params,'queryMaintain.do?stan_id='+'${vo.id}');
	    };
	    
	    
	    var remove = function (){

			var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            $.etDialog.confirm('确定删除?', function (index) {
            	grid.deleteRows(selectData);
            	$.etDialog.close(index)
    		});
		}
	    
	    var parentWindowQuery = function (){
	    	var parentFrameName = parent.$.etDialog.parentFrameName;
            var parentWindow = parent.window[parentFrameName];
            parentWindow.query();
		}
	    
	    var queryStandardMaintain = function(){
 			ajaxPostData({
                url: 'queryStandardMaintain.do?isCheck=false&id='+'${vo.id}',
                async: false,
                 success: function (responseData) {
               	  arrtitle = responseData;
                 },
             })
             
             if(arrtitle.length > 0){
            	 var queryint = 1;
            	 $.each(arrtitle,function(){
          			ajaxPostData({
                        url: 'queryStandardRankodeOption.do?isCheck=false&field_tab_code='+this.FIELD_TAB_CODE,
                        async: false,
                         success: function (responseData) {
                        	 window["valsele"+(queryint++)] = responseData;
                         },
                     })
            	 })
             }
             
	    }
	    
	    $(function () {
	    	queryStandardMaintain();
	        initValidate();
	        initGrid();
	        query();
	    })
        
    </script>
</head>

<body>
	<div  id="btn"  class="button-group" style="overflow-y: auto">
    	<div id="mainGrid"></div>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div>   
</body>

</html>