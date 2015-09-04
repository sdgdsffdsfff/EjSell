<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>初始化安装</title>
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<script type="text/javascript" src="../js/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="初始化安装" data-options="fit:true," style="height: 500px; padding: 10px; background: #fafafa;">
		<form action="install.do" method="post">
		<table>
			<tr>
				<td>尺码名称(用逗号分隔):</td>
				<td><input type="text" name="sizeNames" /></td>
			</tr>
			<tr>
				<td>品牌选择:</td>
				<td>
					<select name="sizeJumpLine">
						<option value="1" >恩爵</option>
						<option value="0" >璐嘉儿</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="安装"></td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>