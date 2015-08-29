<!DOCTYPE html>
<%@page import="com.dexpert.feecollection.main.users.LoginBean"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html lang="en">
<head>
<%
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

	if (cookie.getName().equals("user"))
		usercookie = cookie.getValue();
	if (cookie.getName().equals("JSESSIONID"))
		sessionID = cookie.getValue();
		}
	} else {
		sessionID = session.getId();
	}
%>
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

<body onload="getCollegeList()">
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



			<%
				String msg = (String) request.getAttribute("msg");
			%>

			<%
				if (msg != null)

																																													{
			%>

			<div
				style="color: red; text-align: center; font-weight: bold; font-size: medium;">


				<%=msg%>
			</div>
			<%
				}
			%>


			<div id="content" class="col-lg-10 col-sm-10">
				<!-- content starts -->
				<div></div>


				<div class="row">
					<form action="registerStudent">
						<div class="box col-md-12">
							<div class="box-inner">
								<div class="box-header well">
									<h2>
										<i class="glyphicon glyphicon-info-sign"></i> New Student Form
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

												<tr>
													<th>
														<%
															if (profile.contentEquals("Affiliated")){
																																																				
																																																				
																																																				System.out.print("Profile is ::"+profile);
														%> <input type="hidden" name="aplInstId"
														value="<%=loginUser.getAffBean().getInstId()%>"> <%
 	}
 %>
													</th>
													<th></th>
													<th></th>

												</tr>
											</thead>
											<tbody>
												<tr>

													<td>Student Enrollment Number</td>
													<td colspan="2"><div id="the-basics"
															class="has-success">
															<input required="required"
																name="appBean1.enrollmentNumber"
																value='<s:property value="appBean1.enrollmentNumber"/>'
																placeholder="Enrollment Number" type="text"
																class="form-control">
														</div></td>

												</tr>
												<tr>

													<td>Student Name</td>
													<td><div id="the-basics" class="has-success">
															<input required="required" id="District/Area"
																name="appBean1.aplFirstName"
																value='<s:property value="appBean1.aplFirstName"/>'
																placeholder="First Name" type="text"
																class="form-control">
														</div></td>

													<td><div id="the-basics" class="has-success">
															<input required="required" name="appBean1.aplLstName"
																value='<s:property value="appBean1.aplLstName"/>'
																placeholder="Last Name" type="text" class="form-control">
														</div></td>

												</tr>

												<tr>

													<td>Gender</td>
													<td><div id="the-basics" class="has-success">

															<s:set var="gender">
																<s:property value="appBean1.gender" />
															</s:set>
															<s:if test='%{#gender=="Male"}'>
																<label> <input type="radio" required="required"
																	name="appBean1.gender" checked="checked"
																	id="userPrefixMr" value="Male">Male
																</label>
															&nbsp;&nbsp;&nbsp;	<label><input type="radio"
																	required="required" name="appBean1.gender"
																	id="userPrefixMrs" value="Female">Female</label>

															</s:if>
															<s:elseif test='%{#gender=="Female"}'>
																<label> <input type="radio" required="required"
																	name="appBean1.gender" id="userPrefixMr" value="Male">Male
																</label>&nbsp;&nbsp;&nbsp;<label><input
																	checked="checked" type="radio" required="required"
																	name="appBean1.gender" id="userPrefixMrs"
																	value="Female">Female</label>

															</s:elseif>

															<s:else>
																<label><input type="radio" required="required"
																	name="appBean1.gender" id="userPrefixMr" value="Male">Male</label>
															&nbsp;&nbsp;&nbsp;<label><input type="radio"
																	required="required" name="appBean1.gender"
																	id="userPrefixMrs" value="Female">Female</label>

															</s:else>



														</div></td>

												</tr>
												<%
													if (!profile.contentEquals("Affiliated")){
												%>

												<tr>

													<td>College Name</td>
													<td colspan="2">
														<div class="box-content">
															<div class="control-group">
																<div class="controls">
																	<select data-rel="chosen" name="aplInstId"
																		style="width: 300px;">
																		<option value="">--Select College--</option>
																		<s:iterator value="affInstList">

																			<option value='<s:property value="instId" />'><s:property
																					value="instName" /></option>

																		</s:iterator>
																	</select>
																</div>
															</div>


														</div>

													</td>

												</tr>
												<%
													}
												%>

												<tr>

													<td>Student Address</td>
													<td colspan="2"><div id="the-basics"
															class="has-success">
															<textarea required="required" id="CollegeName"
																name="appBean1.aplAddress" placeholder="Address"
																class="form-control"><s:property
																	value="appBean1.aplAddress" /></textarea>

														</div></td>

												</tr>

												<tr>

													<td>Primary Mobile Number</td>
													<td colspan="2"><div id="the-basics"
															class="has-success">
															<input required="required" id="Contact"
																name="appBean1.aplMobilePri" maxlength="10"
																value='<s:property value="appBean1.aplMobilePri"/>'
																placeholder="Mobile Number" pattern="[789][0-9]{9}"
																type="text" class="form-control">

														</div></td>

												</tr>
												<tr>

													<td>Secondary Mobile Number</td>
													<td colspan="2"><div id="the-basics"
															class="has-success">
															<input required="required" id="Contact"
																name="appBean1.aplMobileSec" maxlength="10"
																value='<s:property value="appBean1.aplMobileSec"/>'
																placeholder="Mobile Number" pattern="[789][0-9]{9}"
																type="text" class="form-control">

														</div></td>

												</tr>
												<tr>

													<td>Email Id</td>
													<td colspan="2"><div id="the-basics"
															class="has-success">
															<input required="required" id="Contact"
																value='<s:property value="appBean1.aplEmail"/>'
																name="appBean1.aplEmail" placeholder="Email ID"
																type="email" class="form-control">

														</div></td>

												</tr>


											</tbody>
										</table>


									</div>


								</div>
							</div>
						</div>
						<div class="col-md-12">
							<button type="submit" onclick="OpenSummaryInParent()"
								class="btn btn-success">Add Student Detail</button>

							<button onclick="window.close()" class="btn btn-info">Close
							</button>

						</div>
					</form>
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
		/* function OpenSummaryInParent() {
			window.onunload = function() {
				window.opener.document.location.reload();
				setTimeout(window.close(), 100);
			}

		} */

		function getCollegeList() {

		}
	</script>


</body>
</html>
