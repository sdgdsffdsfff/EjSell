<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>销售出货数据导入</title>
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<script type="text/javascript" src="../js/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="p" class="easyui-panel" title="销售出货数据导入" data-options="fit:true," style="height: 500px; padding: 10px; background: #fafafa;">
		<p>
		<h1>销售出货数据导入说明:</h1>
		<h2>
			1.文件大小限制在5MB。 <br /> 2.请确保数据导出后没有进行改动。 <br />
		</h2>
		<br />
		<form id="frmUpload" action="importSellOut.do" method="POST" enctype="multipart/form-data">
			选择文件:<input type="file" name="upload" style="width: 300px" /> <a id="btn_upload" onclick="uploadCheck()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">导入数据</a>
		</form>
		</p>
	</div>
	<script type="text/javascript">
		//上传文件检测
		function uploadCheck() {
			$('#frmUpload').submit();
		}
	</script>
</body>
</html>