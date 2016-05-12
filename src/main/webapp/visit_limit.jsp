<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title></title>
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript">
$('document').ready(function() {
	layer.alert("访问受限", {closeBtn:false}, function(){
		window.location.href='${ctx}/web/admin/welcome';
	});
});
</script>
</head>
  <body>
</body>
</html>