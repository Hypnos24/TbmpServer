	function addBlankSelect(selectId, defaultValue, value, text) {
		$("#"+selectId).prepend("<option value='"+value+"'>"+text+"</option>");
		if (defaultValue != null) {
			$("#"+selectId).val(defaultValue);
		}
	}
	
	function clearSelect(selectId) {
		$("#"+selectId).empty(); 
	}
	
	
	function removeSelect(selectId, value) {
		$("#"+selectId+" option[value='"+value+"']").remove();
	}
	
	function copySelect(sourceSelectId, destSelectId) {
		$("#"+sourceSelectId+" option").each(function(){ //遍历全部option
			var value = $(this).val();
	        var text = $(this).text(); //获取option的内容
	      	$("#"+destSelectId).append("<option value='"+value+"'>"+text+"</option>");
	    });
	}
	
	function myContain(array, value) {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return true;
			}
		}
		return false;
	}
	
	function myRemove(array, value) {
		for (var i = 0; i < array.length; i++) {
			if (array[i] == value) {
				array.splice(i,1); //删除从指定位置deletePos开始的指定数量deleteCount的元素，数组形式返回所移除的元素
				break;
			}
		}
	}
	
	function goback() {
		window.history.go(-1);
	}
	
	function tips(msg, id) {
		layer.tips(msg, id, {time:1000, shift: 6});
	}
	
	function msgs(msg) {
		layer.msg(msg, {shift: 6, time:1000});
	}
	
	function msg(msg) {
		layer.msg(msg, {time:1000});
	}
	
	function logout(url) {
		layer.confirm("确定注销本次登录?", {
		    btn: ["确定","取消"], //按钮
		    shade: 0.1 //显示遮罩
		}, function(){
			var win = window;
			while (win != window.parent) {
				win = window.parent;
			}
			win.location.href=url;
		});
	}

	function forceLogout(msg, url) {
		layer.msg(msg+",2秒后自动注销退出，请重新登录", {shift: 6, time:2000}, function() {
			var win = window;
			while (win != window.parent) {
				win = window.parent;
			}
			win.location.href=url;
		});
	}
