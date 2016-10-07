<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">	
<title>Vinilla</title>
<link type="text/css" rel="stylesheet" href="/css/base.css"/>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
<script>

$(function(){
	$("#day").focus();
});

function addLog() {
	$("#moneyForm").submit();
}

function deleteLog(seq) {
	$("#moneyForm").attr("action","/deleteMoneyLog.do?seq=" + seq);
	$("#moneyForm").submit();
}

function cash(amount) {
	$("#amount").val(Number($("#amount").val()) + Number(amount.replace(/,/g,"")));
}

function changeMonth(month) {
	$("#month").val(month);
	$("#moneyForm").submit();
}
</script>
</head>
<body>

<div id="wrap">

	<header><div id="headerTitle">${year}.${month}</div></header>
	
	<div id="contents">
		
		<form id="moneyForm" method="post" action="/moneyLog.do">
			<input type="text" name="year" id="year" value="${year}" size="4" maxlength="4" tabindex="-1" >
			<br><br>
			<input type="text" name="month" id="month" value="${month}" size="4" readonly="readonly" tabindex="-1" >
			<span class="button"><input type="button" value="01" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="02" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="03" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="04" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="05" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="06" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="07" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="08" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="09" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="10" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="11" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="12" onclick="changeMonth(this.value)" tabindex="-1" /></span>
			<br><br>
			<input type="text" name="day" id="day" value="${day}" size="2" maxlength="2" tabindex="1" onfocus="this.select()" >
			<input type="text" name="title" id="title" placeholder="적요" tabindex="2" >
			<input type="radio" name="plusminus" id="plusminus_M" value="M" checked="checked" tabindex="-1" > <label for="plusminus_M">MINUS</label>
			<input type="radio" name="plusminus" id="plusminus_P" value="P" tabindex="-1" > <label for="plusminus_P">PLUS</label>
			<input type="text" name="amount" id="amount" tabindex="3" placeholder="금액" onkeydown="if(event.keyCode == 13) {addLog();return false;}">
			
			<br>
			<br>
			<span class="button"><input type="button" value="10" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="50" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="100" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="500" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="1,000" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="5,000" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="10,000" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="50,000" onclick="cash(this.value)" tabindex="-1" /></span>
			<span class="button"><input type="button" value="100,000" onclick="cash(this.value)" tabindex="-1" /></span>
			
			<br>
			<br>
			<input type="radio" name="isregular" id="isregular_N" value="N" checked="checked" tabindex="-1" > <label for="isregular_N">IRREGULAR</label>
			<input type="radio" name="isregular" id="isregular_Y" value="Y" tabindex="-1" > <label for="isregular_Y">REGULAR</label>
			
			<br>
			<input type="radio" name="intervals" id="intervals_NONE" value="" checked="checked" tabindex="-1" > <label for="intervals_NONE">NONE</label>
			<input type="radio" name="intervals" id="intervals_1" value="D" tabindex="-1" > <label for="intervals_1">EVERYDAY</label>
			<input type="radio" name="intervals" id="intervals_7" value="W" tabindex="-1" > <label for="intervals_7">WEEK</label>
			<input type="radio" name="intervals" id="intervals_30" value="M" tabindex="-1" > <label for="intervals_30">MONTH</label>
			<input type="radio" name="intervals" id="intervals_365" value="Y" tabindex="-1" > <label for="intervals_365">YEAR</label>
			
			<br>
			<br>
			<span class="button"><input type="button" value="ADD" onclick="addLog()" tabindex="4" /></span>
			
		</form>
		
		<br>
		<br>
		
		<table style="width:500px;border:1px solid lightgray;" summary="LOG">
			<tr>
				<th width="50">DAY</th>
				<th>TITLE</th>
				<th width="100" align="right">AMOUNT</th>
				<th width="70">REGULAR</th>
				<th width="100">FN</th>
			</tr>
			
			<c:set var="plusSum" value="0"/>
			<c:set var="minusSum" value="0"/>
			<c:set var="preDay" value=""/>
		
			<c:forEach var="vo" items="${list}">
			<c:if test="${vo.plusminus == '+'}">
			<c:set var="plusSum" value="${plusSum + vo.amount}"/>
			</c:if>
			<c:if test="${vo.plusminus == '-'}">
			<c:set var="minusSum" value="${minusSum + vo.amount}"/>
			</c:if>
			
			<tr>
				<td align="center">
					<c:if test="${preDay != vo.day}">
						${vo.day}
					</c:if>
				</td>
				<td align="left">${vo.title}</td>
				<td align="right">${vo.plusminus}<fmt:formatNumber value="${vo.amount}" pattern="#,###"/></td>
				<td align="center">${vo.isregular} / ${vo.intervals}</td>
				<td align="center"><span class="button"><input type="button" value="X" onclick="deleteLog(${vo.seq})" tabindex="-1" /></span></td>
			</tr>
			<c:set var="preDay" value="${vo.day}"/>
			</c:forEach>
			<tr height="24">
				<td></td>
				<td></td>
				<td align="right" style="color:blue">+&nbsp;<fmt:formatNumber value="${plusSum}" pattern="#,###"/></td>
				<td></td>
				<td></td>
			</tr>
			<tr height="24">
				<td></td>
				<td></td>
				<td align="right" style="color:red">-&nbsp;<fmt:formatNumber value="${minusSum}" pattern="#,###"/></td>
				<td></td>
				<td></td>
			</tr>
		</table>
		
		<br>
		<br>
		
		<table style="width:500px;border:1px solid lightgray;" summary="TITLE SUM">
			<tr>
				<th>TITLE</th>
				<th width="100" align="right">AMOUNT</th>
			</tr>
			
			<c:forEach var="vo" items="${titleSum}">
			<tr>
				<td align="left">${vo.title}</td>
				<td align="right">${vo.plusminus}<fmt:formatNumber value="${vo.amount}" pattern="#,###"/></td>
			</tr>
			</c:forEach>
		</table>
		
		<br>
		<br>
		
		<table style="width:500px;border:1px solid lightgray;" summary="YEARLY PLUS LIST">
			<tr>
				<th width="50">YEAR</th>
				<th width="50">MONTH</th>
				<th width="50">DAY</th>
				<th>TITLE</th>
				<th width="100" align="right">AMOUNT</th>
				<th width="70">REGULAR</th>
			</tr>
			
			<c:set var="plusSumYearly" value="0"/>
			
			<c:forEach var="vo" items="${plusList}">
			<c:set var="plusSumYearly" value="${plusSumYearly + vo.amount}"/>
			
			<tr height="24">
				<td align="center">${vo.year}</td>
				<td align="center">${vo.month}</td>
				<td align="center">
					<c:if test="${preDay != vo.day}">
						${vo.day}
					</c:if>
				</td>
				<td align="left">${vo.title}</td>
				<td align="right">${vo.plusminus}<fmt:formatNumber value="${vo.amount}" pattern="#,###"/></td>
				<td align="center">${vo.isregular} / ${vo.intervals}</td>
			</tr>
			</c:forEach>
			<tr height="24">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td align="right" style="color:blue">+&nbsp;<fmt:formatNumber value="${plusSumYearly}" pattern="#,###"/></td>
				<td></td>
			</tr>
		</table>
	</div>
		
	<footer>footer</footer>
	
</div>
</body>
</html>