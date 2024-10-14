<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form action="${pageContext.request.contextPath }/admin/category/update" method="post"  enctype="multipart/form-data">
	<input
		type="text" id="categoryid" name="categoryid"
		value="${cate.categoryId}" hidden="hidden"><br>
	<label for="categoryname">Category name:</label><br> 
	<input
		type="text" id="categoryname" name="categoryname"
		value="${cate.categoryName}"><br> <label for="image">Image:</label>
	<br>
	<c:if test="${cate.image.substring(0,4) != 'http' }">
		<c:url value="/image?fname=${cate.image }" var="imgUrl"></c:url>
	</c:if>
	<c:if test="${cate.image.substring(0,4) == 'http' }">
		<c:url value="${cate.image }" var="imgUrl"></c:url>
	</c:if>
	<img id="images" height="150" width="200" src="${imgUrl }" />
	<br>
	<input type="file" onchange="chooseFile(this)"  id="image" name="image" value="${cate.image}">
	<br>
	<label for="status">Status:</label><br> 
	<input type="radio" id="ston" name="status" value="1" 
	<c:if test="${cate.active == 1}">checked</c:if>>
  <label for="html">Đang hoạt động</label><br>
  <input type="radio" id="stoff" name="status" value="0"
  <c:if test="${cate.active == 0}">checked</c:if>>
  <label for="html">Khóa</label><br>
  <input type="submit" value="Submit">
</form>
