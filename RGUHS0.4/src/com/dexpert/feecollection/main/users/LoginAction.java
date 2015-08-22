package com.dexpert.feecollection.main.users;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dexpert.feecollection.main.users.affiliated.AffAction;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	// Declare Global Variables Here
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	static Logger log = Logger.getLogger(AffAction.class.getName());

	HttpSession httpSession = ServletActionContext.getRequest().getSession();
	LoginBean loginBean = new LoginBean();
	LoginDAO loginDAO = new LoginDAO();

	// End of Global Variables

	// ---------------------------------------------------
	// Action Methods Here

	public String userLogin() throws InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, IOException

	{

		log.info("user Name is ::" + loginBean.getUserName());
		log.info("Password is ::" + loginBean.getPassword());
		String encryptedPwd, decrypedText = null;
		LoginBean lgbean = new LoginBean();

		List<LoginBean> loginUserList = loginDAO.getLoginDetails(loginBean);

		Iterator<LoginBean> loginIterator = loginUserList.iterator();
		while (loginIterator.hasNext()) {
			log.info("1");
			lgbean = (LoginBean) loginIterator.next();

			encryptedPwd = lgbean.getPassword();
			log.info("2");
			PasswordEncryption.decrypt(encryptedPwd);
			decrypedText = PasswordEncryption.plainStr;
			log.info("3");
			log.info("password frm Database is ::" + decrypedText);
			log.info("4");
		}
		try {
			if (loginBean.getUserName().equals(loginBean.getUserName()) && loginBean.getPassword().equals(decrypedText)) {
				log.info("valid User name and Password");
				Cookie usercookie = new Cookie("userName", loginBean.getUserName());
				usercookie.setMaxAge(60 * 60);
				response.addCookie(usercookie);

				httpSession.setAttribute("loginUserBean", lgbean);

				if (lgbean.getAffBean() != null) {
					log.info("Valid College");
					httpSession.setAttribute("sesProfile", "Affiliated");
					httpSession.setAttribute("dashLink", "index-College.jsp");
					httpSession.setAttribute("sesId", lgbean.getAffBean().getInstId());
					return "college";
				} else if (lgbean.getParBean() != null) {
					log.info("Valid University");
					httpSession.setAttribute("sesProfile", "Parent");
					httpSession.setAttribute("dashLink", "index-University.jsp");
					return "university";
				} else if (lgbean.getSaBean() != null) {
					log.info("Valid Super Admin");
					httpSession.setAttribute("sesProfile", "SU");
					httpSession.setAttribute("dashLink", "index-Admin.jsp");
					return "superAdmin";
				} else {
					request.setAttribute("msg", "Invalid Username or Password");

					return INPUT;
				}

			} else {
				request.setAttribute("msg", "Invalid Username or Password");
				return INPUT;

			}
		} catch (java.lang.NullPointerException e) {
			return ERROR;
		}

	}

	public String logoutUser() throws IOException {

		loginBean = (LoginBean) httpSession.getAttribute("loginUserBean");

		response.setHeader("Cache-Control", "no-cache, no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Vary", "*");

		httpSession.removeAttribute("loginBean");
		httpSession.removeAttribute("sesProfile");
		httpSession.removeAttribute("dashLink");

		request.setAttribute("redirectLink", "Login.jsp");

		return SUCCESS;
	}

	// End of Action Methods

	// ---------------------------------------------------

	// Keep Getter Setter Methods Here

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
