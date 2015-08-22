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



				<div class="row">
					<div class="box col-md-12">
						<div class="box-inner">
							<div class="box-header well">
								<h2>
									<i class="glyphicon glyphicon-info-sign"></i> Applicant Detail
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

												<td><strong>Enrollment NUmber</strong></td>
												<td><div id="the-basics" class="has-success">
														<s:property value="appBean1.enrollmentNumber" />

													</div></td>

											</tr>

										</tbody>
									</table>


								</div>


							</div>
						</div>
					</div>


				</div>
				<form action="submitingParameter" method="post">


					<input type="hidden" readonly="readonly"
						name="appBean1.enrollmentNumber" id="enrollId"
						value="<s:property value="appBean1.enrollmentNumber" />">

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

													<td><strong>Type of Service</strong></td>
													<td>

														<div class="box-content">
															<div class="control-group">
																<div class="controls">


																	<select data-rel="chosen" id="serviceType"
																		name="feeCollectionBean.service_type"
																		style="width: 400px;">
																		<option value="">--Select from Option--</option>
																		<option value="EC">ELIGIBILITY CERTIFICATE</option>
																		<option value="ECR">ELIGIBILITY
																			CERTIFICATE-RENEWAL</option>
																		<option value="CONAIE">CHANGE OF NAME &
																			INITIAL EXPANSION</option>
																		<option value="MC">MIGRATION CERTIFICATE</option>
																		<option value="MT">MIGRATION TRANSFER - From
																			1st to 2nd year</option>
																		<option value="NOCIT">NOC (for internship
																			transfer)</option>
																		<option value="CMCL">CONSOLIDATED MARKS CARD
																			(LAMINATED)</option>
																		<option value="NCIMC">NAME CORRECTION IN
																			MARKS CARD</option>
																		<option value="PDC">PDC</option>
																		<option value="RC">RANK CERTIFICATE</option>
																		<option value="RCD">RANK
																			CERTIFICATE-DUPLICATE</option>
																		<option value="MPC">MEDAL/PRIZE CERTIFICATE</option>
																		<option value="MPCD">MEDAL/PRIZE
																			CERTIFICATE-Duplicate</option>
																		<option value="DDC">DUPLICATE DEGREE
																			CERTIFICATE</option>
																		<option value="PGETS">PGET Superspeciality</option>
																		<option value="PGETF">PGET Fee</option>
																		<option value="PGETA">PGET Application</option>
																		<option value="VO">VERIFICATION ONLY</option>
																		<option value="VACOC">VERIFICATION AND
																			CERTIFICATION OF COPY'S</option>
																		<option value="CCOTSO">CERTIFIED COPY OF THE
																			SYLLABUS/ORINANCE</option>
																		<option value="DMC">DUPLICATE MARKS CARD</option>
																		<option value="PPC">PROVISIONAL PASS
																			CERTIFICATE</option>
																		<option value="DC">DEGREE CERTIFICATE</option>
																		<option value="CC">CERTIFICATE COURSE</option>
																		<option value="NCIDC">NAME CORRECTION IN
																			DEGREE CERTIFICATE</option>
																		<option value="COTOTP">CHANGE OF TITLE OF
																			THESIS-PHD</option>
																		<option value="RRP">RE-REGISTRATION-PH.D</option>
																		<option value="EOR">EXTENSION OF REGISTRATION</option>
																		<option value="PE">PRE EXAMINATION</option>
																		<option value="RF">REGISTRATION FEE</option>
																		<option value="FSSAFPE">FINAL SYNOPSIS
																			SUBMISSION AND FINAL PH.D. EXAMINATION</option>
																		<option value="DF">DISSERTATION FEE</option>
																		<option value="DFR">DISSERTATION
																			FEE-RESUBMISSION</option>
																		<option value="CVOTAOTDF">CREDENTIAL
																			VERIFICATION / OFFICIAL TRANSCRIPT/ ATTESTATION OF
																			THE DOCUMENTS FEE</option>
																		<option value="MOIEC">MEDIUM OF INSTRUCTION
																			IN ENGLISH CERTIFICATION</option>
																		<option value="MLCPFR2500">MALPRACTICE LAPSES
																			COMMITTEE - PENALTY FEE-RS. 2500</option>
																		<option value="MLCPFR5000">MALPRACTICE LAPSES
																			COMMITTEE - PENALTY FEE-RS. 5000</option>
																		<option value="MLCPFR7500">MALPRACTICE LAPSES
																			COMMITTEE - PENALTY FEE-RS. 7500</option>
																		<option value="MLCPFR10000">MALPRACTICE
																			LAPSES COMMITTEE - PENALTY FEE-RS. 10000</option>
																		<option value="MLCPFR12500">MALPRACTICE
																			LAPSES COMMITTEE - PENALTY FEE-RS. 12500</option>
																		<option value="MLCPFR15000">MALPRACTICE
																			LAPSES COMMITTEE - PENALTY FEE-RS. 15000</option>
																		<option value="MLCPFR17500">MALPRACTICE
																			LAPSES COMMITTEE - PENALTY FEE-RS. 17500</option>
																		<option value="MLCPFR20000">MALPRACTICE
																			LAPSES COMMITTEE - PENALTY FEE-RS. 20000</option>
																		<option value="MLCPFR25000">MALPRACTICE
																			LAPSES COMMITTEE - PENALTY FEE-RS. 25000</option>
																		<option value="RTFS1">RE-TOTALING FOR 1
																			SUBJECT</option>
																		<option value="RTFS2">RE-TOTALING FOR 2
																			SUBJECT</option>
																		<option value="RTFS3">RE-TOTALING FOR 3
																			SUBJECT</option>
																		<option value="RTFS4">RE-TOTALING FOR 4
																			SUBJECT</option>
																		<option value="RTFS5">RE-TOTALING FOR 5
																			SUBJECT</option>
																		<option value="PMCFFR5000">PROFESSIONAL
																			MISCONDUCT COMMITTEE FINE FEES-RS. 5000</option>
																		<option value="PMCFFR1000">PROFESSIONAL
																			MISCONDUCT COMMITTEE FINE FEES-RS. 10000</option>
																		<option value="PMCFFR20000">PROFESSIONAL
																			MISCONDUCT COMMITTEE FINE FEES-RS. 20000</option>
																		<option value="PMCFFR30000">PROFESSIONAL
																			MISCONDUCT COMMITTEE FINE FEES-RS. 30000</option>
																		<option value="PMCFFR50000">PROFESSIONAL
																			MISCONDUCT COMMITTEE FINE FEES-RS. 50000</option>
																		<option value="PMCFFR100000">PROFESSIONAL
																			MISCONDUCT COMMITTEE FINE FEES-RS. 100000</option>


																	</select>

																	<script type="text/javascript">
																		var value =
																	<%=request.getParameter("selectedValue")%>
																		;
																		if (value != null) {

																			document.f1.slvalue.selectedIndex = value;

																		}
																	</script>
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
																	<select data-rel="chosen" id="nationality"
																		name="feeCollectionBean.nationality"
																		style="width: 400px;">
																		<option value="">--Select Option--</option>

																		<option value="INDIAN">INDIAN</option>
																		<option value="NRISAARC">NRI/SAARC</option>
																		<option value="FOREIGN">FOREIGN</option>

																	</select>
																	<script type="text/javascript">
																		document
																				.getElementById('nationality').value = "feeCollectionBean.nationality";
																	</script>
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
																	<select data-rel="chosen" id="faculty"
																		name="feeCollectionBean.faculty" style="width: 400px;">
																		<option value="">--Select Option--</option>

																		<option value="MED">MEDICAL</option>
																		<option value="DENT">DENTAL</option>
																		<option value="NursPHYSIOPHARM">NURSING,PHYSIOTHERAPY,PHARMACY</option>
																		<option value="AYAVRDHOMEOUNANYOGA">AYUSH,AYURVEDA,HOMEOPATHY,UNANI,YOGA</option>
																		<option value="PMEDIAOTHERS">PARAMEDICAL AND
																			Others</option>

																	</select>


																	<script type="text/javascript">
																		document
																				.getElementById('faculty').value = "feeCollectionBean.faculty";
																	</script>

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
																	<select data-rel="chosen" id="course"
																		name="feeCollectionBean.course" style="width: 400px;">
																		<option value="">--Select Option--</option>

																		<option value="PG">PG</option>
																		<option value="UG">UG</option>
																		<option value="PGDIPLOMA">PG DIPLOMA</option>
																		<option value="PHD">PHD</option>

																	</select>
																	<script type="text/javascript">
																		document
																				.getElementById('course').value = "feeCollectionBean.course";
																	</script>
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

<button type="submit" class="btn btn-success">Calculate Fee</button>

									</div>


								</div>
							</div>
						</div>
						<div class="col-md-12">
							
							<button onclick="window.close()" class="btn btn-info">Close
							</button>

							<s:if test='%{#fee>"0.0"}'>


								<input type="button" class="btn btn-danger"
									onclick="openPaymentGateway(<s:property value="feeCollectionBean.fee" />,<s:property value="appBean1.enrollmentNumber" />)"
									value="Pay">

								<script type="text/javascript">
									function openPaymentGateway(fee,id) {
						
										window.location = "AccessingPaymentGateway?feeValue="+fee+"&enrollId="+id;

									}
								</script>



							</s:if>




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
