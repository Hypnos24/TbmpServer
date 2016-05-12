<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title></title>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
$('document').ready(function() {
	layer.alert("登录超时,请重新登录.", {closeBtn:false}, function(){
		var win=window;while(win != window.parent){win = window.parent;}win.location.href='${ctx}/web/admin/index';
	});
});
</script>
</head>
  <body>
</body>
</html>