<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:forEach items="${suppliers}" var="cust">
<c:out value="${cust}" />	
</c:forEach>