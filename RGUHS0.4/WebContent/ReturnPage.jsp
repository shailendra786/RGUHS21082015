<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RGUHS</title>
</head>
<body onload="waitSubmit()">
	<h2></h2>
	<%
		String msg = request.getParameter("RPS");
		String transId = request.getParameter("txnId");
		String paymentMode = request.getParameter("PayMode");
	%>
	<%
		if (msg.equals("0")) {
	%>

	<div align="center">Congratulations, your payment has been
		successful, please click 'Back' button to go to your home page...</div>
	<%
		}

		else {

			if (msg.equals("-99")) {
	%>
	<div align="center">You have cancelled the transaction, please
		click 'Back' button to go to your home page...</div>

	<%
		} else {
	%>
	<div align="center">Sorry, your transaction has been declined due
		to some reason, please click 'Back' button to go to your home page...</div>

	<%
		}
		}
	%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">

		<%-- <center>
			<div style="text-align: center;" class="btn btn-default">

				<a
					href="RetrieveUserSession?RPS=<%=msg%>&txnID=<%=transId%>&payMode=<%=paymentMode%>">Back
				</a>

			</div>
		</center> --%>

		<script type="text/javascript">
		function waitSubmit() {
			
			
			setTimeout(function(){ 
				alert("hello");
				window.location="RetrieveUserSession?RPS=<%=msg%>&txnID=<%=transId%>&payMode=<%=paymentMode%>"
				
				
			}, 3000);
		}
	</script>
</body>
</html>