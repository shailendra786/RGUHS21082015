<!DOCTYPE html>
<%@page import="com.dexpert.feecollection.main.users.LoginBean"%>
<html lang="en">
<head>
<%
	//checking session
	LoginBean loginUser = new LoginBean();
	loginUser = (LoginBean) session.getAttribute("loginUserBean"); String profile=(String)session.getAttribute("sesProfile");
	boolean alertflag=false;
	boolean alertflag2=false;
	if (loginUser == null) {
		response.sendRedirect("Login.jsp");

		return;

	}
	String usercookie = null;
	String sessionID = null;
	String dispchar = "display:none";
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {

	if (cookie.getName().equals("user"))
		usercookie = cookie.getValue();
	if (cookie.getName().equals("JSESSIONID"))
		sessionID = cookie.getValue();
		}
	} else {
		sessionID = session.getId();
	}
	try{
		alertflag=(boolean)request.getAttribute("reqAlertFlag");
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	try{
	alertflag2=(boolean)request.getAttribute("reqAlertFlag2");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<meta charset="utf-8">
<title>FeeDesk</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description"
	content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
<meta name="author" content="Muhammad Usman">

<!-- The styles -->
<link id="bs-css" href="css/bootstrap-cerulean.min.css" rel="stylesheet">

<link href="css/charisma-app.css" rel="stylesheet">
<link href='bower_components/fullcalendar/dist/fullcalendar.css'
	rel='stylesheet'>
<link href='bower_components/fullcalendar/dist/fullcalendar.print.css'
	rel='stylesheet' media='print'>
<link href='bower_components/chosen/chosen.min.css' rel='stylesheet'>
<link href='bower_components/colorbox/example3/colorbox.css'
	rel='stylesheet'>
<link href='bower_components/responsive-tables/responsive-tables.css'
	rel='stylesheet'>
<link
	href='bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css'
	rel='stylesheet'>
<link href='css/jquery.noty.css' rel='stylesheet'>
<link href='css/noty_theme_default.css' rel='stylesheet'>
<link href='css/elfinder.min.css' rel='stylesheet'>
<link href='css/elfinder.theme.css' rel='stylesheet'>
<link href='css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='css/uploadify.css' rel='stylesheet'>
<link href='css/animate.min.css' rel='stylesheet'>
<link href="bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<!-- jQuery -->
<script src="bower_components/jquery/jquery.min.js"></script>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="img/favicon.ico">

</head>

<body onload="showAlert(<%=alertflag%>,<%=alertflag2%>)">
	<!-- topbar starts -->
	<%
		Integer headCount = 4;
			Integer colCount = 0;
	%>
	<!-- topbar ends -->
	<div class="ch-container">
		<div class="row">


			<noscript>
				<div class="alert alert-block col-md-12">
					<h4 class="alert-heading">Warning!</h4>

					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="col-lg-10 col-sm-10">
				<!-- content starts -->
				<div></div>


				<div class="row">
					<div class="box col-md-12">
						<div class="box-inner">
							<div class="box-header well">
								<h2>
									<i class="glyphicon glyphicon-info-sign"></i> New Fee Form
								</h2>

								<div class="box-icon">

									<a href="#" class="btn btn-minimize btn-round btn-default"><i
										class="glyphicon glyphicon-chevron-down"></i></a>

								</div>
							</div>
							<div class="box-content row">
								<form action="GenerateCombination">
									<div class="col-lg-12 col-md-12 animated fadeIn">
										<%-- <%int headcount=4;
Integer count=0;%>

										<table>
											<s:iterator value="ComboList" >
											<tr>
											<s:iterator status="incr">
											<td>
											<s:if test="%{#incr.index==0}">This is first</s:if>
											<s:property></s:property>
											</td>
											</s:iterator>
											
											</tr>
											</s:iterator>
										</table>
 --%>

										<table class="table table-condensed">
											<thead>

												<tr>
													
													<th></th>
													<th></th>

												</tr>
											</thead>
											<tbody>
												<tr>
													
													<td>Fee Payable By</td>
													<td><div class="control-group">


															<div class="controls">
																<select required="required" id="selectError1" class="form-control"
																	data-rel="chosen" name="feePayee">
																	<option value="1">College</option>
																	<option value="2">Student</option>
																</select>
															</div>
														</div></td>

												</tr>
												<tr>
													
													<td>Fee Name</td>
													<td><div id="the-basics" class="has-success">
															<input required="required" id="District/Area"
																placeholder="Give a Unique Name" type="text"
																class="form-control" name="feedetails.feeName">
														</div></td>

												</tr>


												<tr>
													
													<td>Select Course Parameters</td>
													<td><div class="control-group">


															<div class="controls">
																<s:select  theme="simple" list="CourseParamList"
																	listValue="lookupName" listKey="lookupId"
																	multiple="CourseParamList" data-rel="chosen"
																	cssStyle="width:60%" cssClass="form-control"
																	name="SelectedCourseParam"></s:select>

															</div>
														</div></td>

												</tr>
												<tr>
													
													<td>Select College Parameters</td>
													<td><div class="control-group">


															<div class="controls">
																<s:select theme="simple" list="InstituteParamList"
																	listValue="lookupName" listKey="lookupId"
																	multiple="InstituteParamList" data-rel="chosen"
																	cssStyle="width:60%" cssClass="form-control"
																	name="SelectedInstParam"></s:select>

															</div>
														</div></td>

												</tr>
												<tr>
													
													<td>Select Student Parameters</td>
													<td><div class="control-group">


															<div class="controls">
																<s:select theme="simple" list="ApplicantParamList"
																	listValue="lookupName" listKey="lookupId"
																	multiple="ApplicantParamList" data-rel="chosen"
																	cssStyle="width:60%" cssClass="form-control"
																	name="SelectedAppParam"></s:select>

															</div>
														</div></td>



												</tr>
												<tr>
													
													<td>Select Service Parameters</td>

													<td><div class="control-group">


															<div class="controls">
																<s:select theme="simple" list="ServiceParamList"
																	listValue="lookupName" listKey="lookupId"
																	multiple="ServiceParamList" data-rel="chosen"
																	cssStyle="width:60%" cssClass="form-control"
																	name="SelectedSerParam"></s:select>

															</div>
														</div></td>
												</tr>
												<tr>
													
													<td>Select Fee Calculation Parameter</td>
													<td><div class="control-group">
															<div class="controls">
																<select required="required" id="selectError1" class="form-control"
																	data-rel="chosen" name="cal_mode">
																	<option value="1">Per Student</option>
																	
																	<option value="0">Fixed</option>

																</select>
															</div>
														</div></td>

												</tr>

											</tbody>
										</table>

									</div>




									<div class="col-md-12">
										<button class="btn btn-success">Next</button>

										<button onclick="window.close()" class="btn btn-info">Close
										</button>

									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<!--/row-->

				<!--/row-->
				<!-- content ends -->
			</div>
			<!--/#content.col-md-0-->
		</div>
		<!--/fluid-row-->



		<hr>

		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">�</button>
						<h3>Settings</h3>
					</div>
					<div class="modal-body">
						<p>Here settings can be configured...</p>
					</div>
					<div class="modal-footer">
						<a href="#" class="btn btn-default" data-dismiss="modal">Close</a>
						<a href="#" class="btn btn-primary" data-dismiss="modal">Save
							changes</a>
					</div>
				</div>
			</div>
		</div>

		<!-- <footer class="row">
			<p class="col-md-9 col-sm-9 col-xs-12 copyright">
				&copy; <a href="http://dexpertsystems.com" target="_blank">Dexpert
					Systems Pvt. Ltd</a>
			</p>

			<p class="col-md-3 col-sm-3 col-xs-12 powered-by">
				Powered by: <a href="http://dexpertsystems.com">Dexpert</a>
			</p>
		</footer> -->

	</div>
	<!--/.fluid-container-->

	<!-- external javascript -->

	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- library for cookie management -->
	<script src="js/jquery.cookie.js"></script>
	<!-- calender plugin -->
	<script src='bower_components/moment/min/moment.min.js'></script>
	<script src='bower_components/fullcalendar/dist/fullcalendar.min.js'></script>

	<!-- data table plugin -->
	<script src='js/jquery.dataTables.min.js'></script>

	<!-- select or dropdown enhancer -->
	<script src="bower_components/chosen/chosen.jquery.min.js"></script>
	<!-- plugin for gallery image view -->
	<script src="bower_components/colorbox/jquery.colorbox-min.js"></script>
	<!-- notification plugin -->
	<script src="js/jquery.noty.js"></script>
	<!-- library for making tables responsive -->
	<script src="bower_components/responsive-tables/responsive-tables.js"></script>
	<!-- tour plugin -->
	<script
		src="bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
	<!-- star rating plugin -->
	<script src="js/jquery.raty.min.js"></script>
	<!-- for iOS style toggle switch -->
	<script src="js/jquery.iphone.toggle.js"></script>
	<!-- autogrowing textarea plugin -->
	<script src="js/jquery.autogrow-textarea.js"></script>
	<!-- multiple file upload plugin -->
	<script src="js/jquery.uploadify-3.1.min.js"></script>
	<!-- history.js for cross-browser state change on ajax -->
	<script src="js/jquery.history.js"></script>
	<!-- application script for Charisma demo -->
	<script src="js/charisma.js"></script>

	<script>
		function OpenSummaryInParent() {
			window.opener.location = "College-Payment-Summary.html";
			window.close();

		}

		function getValue() {
			alert(document.getElementById("CourseParamSelect").value);
		}
		function showAlert(x,y)
		{
			if(x)
				{
				alert("Please select atleast one Parameter");
				}
			if(y)
			{
			alert("Fee Name already taken Please use different name");
			}
		}
	</script>
</body>
</html>
