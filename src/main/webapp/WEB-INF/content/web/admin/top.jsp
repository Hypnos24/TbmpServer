<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>

<head>
<title></title>
<link type="text/css" href="${ctx}/style/style.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/js/jquery.js"></script>
<script type="text/javascript">
	var serverTime = parseInt("${serverTime}");
	$('document').ready(function() {
		var now = new Date();
		var offset = parseInt("${offset}");
		offset = now.getTimezoneOffset() / 60 + offset;
		serverTime = serverTime + offset * 3600;
		refreshTime();
		setInterval(refreshTime,1000);
	});
	function refreshTime() {
		serverTime++;
		var now = new Date(serverTime * 1000);
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		month = fullZero(month);
		var day=now.getDate();
		day = fullZero(day);
		var hours=now.getHours();
		hours = fullZero(hours);
		var minutes=now.getMinutes();
		minutes = fullZero(minutes);
		var seconds=now.getSeconds();
		seconds = fullZero(seconds);
		$("#severTime").html("服务器当前时间："+year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds);
	}

	function fullZero(value) {
		if (value < 10) {
			value = "0" + value;
		}
		return value;
	}
</script>

</head>
<body style="background:url(${ctx}/images/topbg.gif) repeat-x;">

	<div class="topleft">
		<a href="${ctx}/web/admin/main" target="_parent"><img
			src="${ctx}/images/logo.png" title="系统首页" /> </a>
	</div>

	<div class="topright">
		<div class="user" style="width: 100px;">
			<i><a href="javascript:window.parent.logout();" style="padding-left: 35px;">注销</a></i>
		</div>
	</div>
	
	<div class="topright">
		<div class="user" style="width: 100px;">
			<i><a href="${ctx}/web/admin/main" style="padding-left: 35px;"
				target="_parent">首页</a></i>
		</div>
	</div>

	<div class="topright">
		<div class="user" style="width: 260px;padding-left:10px;">
			<i id="severTime">服务器当前时间：</i>
		</div>
	</div>
	<div class="topright">
		<div class="user" style="width: 180px;padding-left:10px;">
			<i id="versionName">当前版本：${versionName}</i>
		</div>
	</div>
</body>
</html>