<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function setFinYear(val)
{
	parent.setFinYear(val);
}

</script>
</head>
<body>
	<select onchange="setFinYear(this.value)">
	<option>Select Financial Year</option>
		<option>2014-2015</option>
		<option>2015-2016</option>
		<option>2016-2017</option>
		<option>2017-2018</option>
		<option>2018-2019</option>
		<option>2019-2020</option>
		<option>2020-2021</option>
		<option>2021-2022</option>
		<option>2022-2023</option>
		<option>2023-2024</option>
		<option>2024-2025</option>
	</select>
</body>
</html>