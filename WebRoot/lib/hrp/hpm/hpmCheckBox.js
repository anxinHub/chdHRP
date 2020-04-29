 //是否类型的模拟复选框的渲染函数
    function checkboxRender(rowdata, rowindex, value, column)
    {
        var iconHtml = '<div class="chk-icon';
        if (value) iconHtml += " chk-icon-selected";
        iconHtml += '"';
        iconHtml += ' rowid = "' + rowdata['__id'] + '"';
        iconHtml += ' gridid = "' + this.id + '"';
        iconHtml += ' columnname = "' + column.name + '"';
        iconHtml += ' column_id = "' + column.id + '"';
        iconHtml += ' style = "display: inline-block;vertical-align: middle;"';
        iconHtml += '></div>';
        
        return iconHtml;
    }
    
    //是否类型的模拟复选框的点击事件
    $("div.chk-icon").live('click', function (e){
    	
    	var grid = $.ligerui.get($(this).attr("gridid")),
    	checked;
    	var columnname = $(this).attr("columnname");
    	v_columnn = columnname;
    	
    	if($(this).hasClass("total")){//判断当前点击行是否是列头
    		
    		/*if(!$(this).hasClass("chk-icon-selected")){//判断是否全选，若是则把所有数据都全选
    			$(this).addClass("chk-icon-selected");
    			checked = true;
    		}else{//若反选则数据全反选
    			$(this).removeClass("chk-icon-selected");
    			 checked = false;
    		}
    		
    		$("div.chk-icon").each(function(){
    			if($(this).hasClass("total")) return true;
            	var grid2 = $.ligerui.get($(this).attr("gridid"));
                var rowdata2 = grid2.getRow($(this).attr("rowid"));
            	var columnname2 = $(this).attr("columnname");
            	if(columnname == columnname2){
            		grid2.updateCell(columnname2, checked, rowdata2);
            	}
            });*/
    	}else{//非列头行点击
    		var rowdata = grid.getRow($(this).attr("rowid"));//获取当前行对象
    		if(rowdata[columnname] == undefined || rowdata[columnname] == ''){//为空点击时
    			checked = true;//选中
    		}else{//不为空点击时
    			checked = false;//不选中
    		}
    		grid.updateCell(columnname, checked, rowdata);//修改当前行当前列选中状态
    		save(rowdata);
    	}
   		
    });
   
    