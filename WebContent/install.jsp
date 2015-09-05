<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>安装信息</title>
<link rel="stylesheet" type="text/css" href="./js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="./js/easyui/themes/icon.css">
<script type="text/javascript" src="./js/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="./js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="安装信息" data-options="fit:true," style="height: 500px; padding: 10px; background: #fafafa;">
		<h1>${applicationScope.projectName}(版本号:${applicationScope.version})</h1>
		<p>
		<h2>
			<br /> <br /> <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="SysConfigAction/installPage.do">初始化安装</a>
		</h2>
		</p>
	</div>
</body>
</html>