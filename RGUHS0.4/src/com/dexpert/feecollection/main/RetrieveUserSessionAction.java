package com.dexpert.feecollection.main;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dexpert.feecollection.challan.ChallanAction;
import com.dexpert.feecollection.main.payment.studentPayment.ApplicantFeeCollectionDAO;
import com.opensymphony.xwork2.ActionSupport;

public class RetrieveUserSessionAction extends ActionSupport {

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	static Logger log = Logger.getLogger(RetrieveUserSessionAction.class.getName());
	ApplicantFeeCollectionDAO dao = new ApplicantFeeCollectionDAO();

	public String RetrieveSessionUpdateTrans() {

		String RPS = request.getParameter("RPS");
		String txnId = request.getParameter("txnID");
		String payMode = request.getParameter("payMode");
		log.info("Response Code is :: " + RPS);
		log.info("xtn id  is :: " + txnId);
		log.info("pay mode Code is :: " + payMode);

		HttpSession httpSession = (HttpSession) request.getServletContext().getAttribute(txnId);

		try {
			String dueStr = (String) httpSession.getAttribute("dueStr");
			HashMap hm = (HashMap) httpSession.getAttribute("hmap");
			String studentEnrollmentNo = (String) hm.get("enrollId");
			String sgiTxnId = hm.get("txnID").toString();
			log.info("student enrollment Number" + studentEnrollmentNo);

			if (RPS.equals("0"))

			{

				log.info("in IF :: " + RPS);

				if (payMode.equals("Cash") || payMode.equals("Cheque")) {
					log.info("Cash Payment Mode");
					dao.updateTransactionStatus(sgiTxnId, "Pending", payMode);

				}

				//dao.updateTransTable(sgiTxnId, RPS, dueStr, studentEnrollmentNo, payMode);

				return "home";
			}

			else {
				log.info("In Else :: " + RPS);
				dao.updateTransTable(sgiTxnId, RPS, "", studentEnrollmentNo, payMode);
				return "home";
			}

		} catch (java.lang.NullPointerException e) {
			return ERROR;
		}

	}

}
