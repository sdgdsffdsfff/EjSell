<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>代销分析系统</title>
<link rel="stylesheet" type="text/css" href="./js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="./js/easyui/themes/icon.css">
<script type="text/javascript" src="./js/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="./js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="./js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="代销分析系统" data-options="fit:true," style="height: 500px; padding: 10px; background: #fafafa;">
		<p>
		<h2>
			<br />
			<br /> <a class="easyui-linkbutton" data-options="iconCls:'icon-add'" href="SellOutAction/importSellOutPage.do">销售出货导入</a>
			<br />
			<br /> <a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" href="SellReturnAction/importSellReturnPage.do" >销售退货导入</a>
			<br />
			<br /> <a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="SellReturnAction/downSellReturn.do?isExists=1">导出回货数据</a>
			<br />
			<br /> <a class="easyui-linkbutton" data-options="iconCls:'icon-save'" href="SellReturnAction/downSellReturn.do?isExists=0">导出代卖数据</a>
			<br />
			<br /> <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="SellOutAction/clearData.do">清空销售数据</a>
		</h2>
		</p>
	</div>
</body>
</html>