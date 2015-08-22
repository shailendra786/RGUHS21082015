<!DOCTYPE html>
<%@page import="com.dexpert.feecollection.main.users.LoginBean"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<%-- <%
	//checking session
	LoginBean loginUser = new LoginBean();
	loginUser = (LoginBean) session.getAttribute("loginUserBean"); String profile=(String)session.getAttribute("sesProfile");

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

	if (cookie.getName().equals("loginUser"))
		usercookie = cookie.getValue();
	if (cookie.getName().equals("JSESSIONID"))
		sessionID = cookie.getValue();
		}
	} else {
		sessionID = session.getId();
	}
%> --%>
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

<body>
	<!-- topbar starts -->

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


				<%
					String msg = (String) request.getAttribute("msg");
				%>

				<%
					if (msg != null) {
				%>

				<div
					style="color: red; text-align: center; font-size: medium; font-weight: bold; margin-top: 10px;">
					<%=msg%>

				</div>
				<%
					}
				%>
				<form action="submitingParameterNoValidate" method="post">

					<input type="hidden" name="noValidate" value="1">


					<div class="row">
						<div class="box col-md-12">
							<div class="box-inner">
								<div class="box-header well">
									<h2>
										<i class="glyphicon glyphicon-info-sign"></i>Applicant Fees
										Collection
									</h2>

									<div class="box-icon">

										<a href="#" class="btn btn-minimize btn-round btn-default"><i
											class="glyphicon glyphicon-chevron-down"></i></a>

									</div>
								</div>
								<div class="box-content row">
									<div class="col-lg-12 col-md-12 animated fadeIn">

										<table class="table table-condensed">
											<thead>


											</thead>
											<tbody>

												<tr>
													<td style="font-weight: bold;">Enrollment Number</td>



													<td><input type="text" value="0" name="validateFlag"
														hidden="hidden"> <input style="width: 400px;" 
														type="text" name="appBean1.enrollmentNumber" pattern="[a-zA-Z0-9]*"
														class="form-control" required="required" id="enrollId"
														value='<s:property value="appBean1.enrollmentNumber" />'></td>

												</tr>
												<tr>
													<td style="font-weight: bold;">First Name</td>



													<td><input style="width: 400px;" type="text"
														name="appBean1.aplFirstName" class="form-control"
														required="required" id="firstNameId"
														value='<s:property value="appBean1.aplFirstName" />'></td>

												</tr>
												<tr>
													<td style="font-weight: bold;">Last Name</td>



													<td><input style="width: 400px;" type="text"
														name="appBean1.aplLstName" class="form-control"
														required="required" id="lstNameId"
														value='<s:property value="appBean1.aplLstName" />'></td>

												</tr>
												<tr>
													<td style="font-weight: bold;">Mobile No.</td>



													<td><input style="width: 400px;" type="text"
														name="appBean1.aplMobilePri" class="form-control"
														required="required" id="contactId" pattern="[789][0-9]{9}"
														value='<s:property value="appBean1.aplMobilePri" />'></td>

												</tr>
												<tr>
													<td style="font-weight: bold;">Email</td>



													<td><input style="width: 400px;" type="email"
														id="emailId" name="appBean1.aplEmail" class="form-control"
														required="required"
														value='<s:property value="appBean1.aplEmail" />'></td>

												</tr>
												<tr>

													<td><strong>Type of Service</strong></td>
													<td>

														<div class="box-content">
															<div class="control-group">
																<div class="controls">
																	<s:select theme="simple" id="service" headerKey=""
																		headerValue="Select an Option" requiredLabel="true"
																		data-rel="chosen" cssStyle="width:400px"
																		list="serviceList"
																		name="feeCollectionBean.service_type">



																	</s:select>




																</div>
															</div>



														</div>
													</td>

												</tr>

												<tr>
													<td><strong>Nationality/Origin</strong></td>
													<td>

														<div class="box-content">
															<div class="control-group">
																<div class="controls">



																	<s:select theme="simple" headerKey="" id="nationality"
																		headerValue="Select an Option" requiredLabel="true"
																		cssClass="form-control" cssStyle="width:400px;"
																		data-rel="chosen" list="nationalityList"
																		name="feeCollectionBean.nationality">



																	</s:select>

																</div>
															</div>
														</div>
													</td>
												</tr>
												<tr>
													<td><strong>Faculty</strong></td>
													<td>

														<div class="box-content">
															<div class="control-group">
																<div class="controls">


																	<s:select theme="simple" headerKey="" id="faculty"
																		requiredLabel="true" headerValue="Select an Option"
																		cssClass="form-control" cssStyle="width:400px;"
																		data-rel="chosen" list="facultyList"
																		name="feeCollectionBean.faculty">

																	</s:select>

																</div>
															</div>
														</div>
													</td>
												</tr>

												<tr>
													<td><strong>Course</strong></td>
													<td>

														<div class="box-content">
															<div class="control-group">
																<div class="controls">

																	<s:select theme="simple" headerKey="" id="course"
																		headerValue="Select an Option" cssClass="form-control"
																		cssStyle="width:400px;" data-rel="chosen"
																		list="courseList" name="feeCollectionBean.course">

																	</s:select>
																</div>
															</div>
														</div>
													</td>
												</tr>


												<tr>
													<td><span style="font-size: large; font-weight: bold;">Total
															Fees</span></td>
													<td>
														<div class="box-content">
															<div class="control-group">
																<div class="controls">

																	<div>

																		<input type="text" readonly="readonly" id="feeId"
																			name="feeCollectionBean.fee"
																			style="width: 400px; font-size: large; font-weight: bold; padding: 5px;"
																			value='<s:property value="feeCollectionBean.fee" />'>

																		<s:set var="fee">
																			<s:property value="feeCollectionBean.fee" />
																		</s:set>





																	</div>

																</div>
															</div>
														</div>
													</td>
												</tr>

											</tbody>
										</table>


										<table class="table table-condensed">
											<tr>
												<td>
													<button type="submit" class="btn btn-success">Calculate
														Fee</button>

												</td>




												<td><s:if test='%{#fee>"0.0"}'>


														<input type="button" class="btn btn-danger"
															onclick="openPaymentGateway()"
															value="Proceed For Payment">

														<script type="text/javascript">
															function openPaymentGateway() {

																var fee = document
																		.getElementById("feeId").value;
																var id = document
																		.getElementById("enrollId").value
																		.trim();

																var fName = document
																		.getElementById("firstNameId").value
																		.trim();
																var lName = document
																		.getElementById("lstNameId").value
																		.trim();

																var con = document
																		.getElementById("contactId").value
																		.trim();
																var emailId = document
																		.getElementById("emailId").value
																		.trim();

																window.location = "AccessingPaymentGateway?feeValue="
																		+ fee
																		+ "&enrollId="
																		+ id
																		+ "&firstName="
																		+ fName
																		+ "&lstName="
																		+ lName
																		+ "&contact="
																		+ con
																		+ "&email="
																		+ emailId;

															}
														</script>



													</s:if></td>
												<td><input type="button" value="Back"
													class="btn btn-default" onclick="window.history.back()">
												</td>
											</tr>

										</table>



									</div>


								</div>
							</div>
						</div>
						<div class="col-md-12">

							<!-- <input type="button" class="btn btn-info" value="CLose"
								onclick="window.close()"> -->



						</div>

					</div>
				</form>
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
						<button type="button" class="close" data-dismiss="modal">×</button>
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

	<script>
		function OpenSummaryInParent() {
			window.opener.location.reload(true);
			window.close();

		}
	</script>


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


</body>
</html>
