package com.starLife.in.Controllers.AuthorizedControllers.CustomerControllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import java.time.DayOfWeek;
import java.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.starLife.in.Entity.Customer;
import com.starLife.in.Entity.Transaction_History;
import com.starLife.in.ExceptionHandler.OtpException;
import com.starLife.in.helper.AtmPinHelper;
import com.starLife.in.helper.Message;
import com.starLife.in.helper.MoneyTransferHelper;
import com.starLife.in.helper.SigninHelper;
import com.starLife.in.repository.CustomerRepository;
import com.starLife.in.repository.Trans_historyRepository;
import com.starLife.in.service.AccountVerified;
import com.starLife.in.service.AtmService;
import com.starLife.in.service.EmailService;
import com.starLife.in.service.OtpService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository cstmrRepo;

	@Autowired
	private Trans_historyRepository transactionRepo;

	@Autowired
	private AtmService atmService;

	@Autowired
	private EmailService EmailService;

	@Autowired
	private OtpService otpService;
	@Autowired private AccountVerified accountVerified;

	@GetMapping("/customerDashboard/homepage")
	public String openCustomerDashboard(Model m, Principal p, HttpSession session) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		m.addAttribute("title", " Customer-Dashboard ");

		m.addAttribute("customer", customer);

		session.setAttribute("username", customer);

		// check customer is verified or not 

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		return "customerService/customerHomepage";
	}

	@GetMapping("/customerDashboard/checkbalance")
	public String CHECKBALANCE(Model m, Principal p, HttpSession session) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		if (customer.getAtmPin() == null) {
			session.setAttribute("notAtm", "notAtm");
			session.removeAttribute("yesAtm");
			return "redirect:/customer/customerDashboard/debit_card";
		}

		m.addAttribute("title", " Check balance ");
		m.addAttribute("customer", customer);



		return "customerService/balanceCheck";
	}

	// profile update ................

	@GetMapping("/customerDashboard/updateProfile")
	public String updateProfile(Model m, Principal p) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		m.addAttribute("title", " Update Details ");
		m.addAttribute("customer", customer);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		return "customerService/updateProfile";
	}

	// profile update ................

	@GetMapping("/customerDashboard/money_transfer")
	public String moneyTransfer(Model m, Principal p) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		m.addAttribute("title", " Money Transfer ");
		m.addAttribute("customer", customer);
    
		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}
		return "customerService/moneytranfer";
	}

	// money transfer process

	@GetMapping("/customerDashboard/moneyTransfer")
	public String getMethodName(@ModelAttribute MoneyTransferHelper recipant, Model m, HttpSession session,
			Principal p) {

		String accountNo = recipant.getRecipientAcc();
		String recipient_name = recipant.getRecipientName();
		String amount = recipant.getAmount();
		String atmPin = recipant.getAtmPin();

		Customer currentRecipient = this.cstmrRepo.findByAccountNo(accountNo);

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		int transactionNum;

		Random rd = new Random();
		int max = 999999;
		int min = 100000;

		transactionNum = rd.nextInt(max - min + 1) + min;
		Transaction_History trans2 = new Transaction_History();
		

		if (currentRecipient.getName().equals(recipient_name)) {

			Transaction_History trans1 = new Transaction_History();

			if (customer.getAtmPin().equals(atmPin)) {

				// for sender

				String balance1 = String.valueOf(Integer.parseInt(customer.getBalance()) - Integer.parseInt(amount));

				trans1.setBalance(balance1);
				trans1.setCust(customer);
				trans1.setAmount(amount);
				trans1.setStatus("Debit");
				trans1.setTrans_date(LocalDate.now());
				trans1.setTrans_id("txn_slb_" + transactionNum);

				EmailService.sendmaill(customer.getcEmail(), "Dear Customer \n An amount of INR " + amount +
						" has been Debited to your account  xxxx" + customer.getAccountNo().substring(13) + " on "
						+ new Date().getTime() + ".", "Transaction summery ");
				customer.setBalance(balance1);
				this.cstmrRepo.save(customer);

				this.transactionRepo.save(trans1);

				// for receiver

				String balance2 = String
						.valueOf(Integer.parseInt(currentRecipient.getBalance()) + Integer.parseInt(amount));

				LocalDate date = LocalDate.now();

				trans2.setBalance(balance2);
				trans2.setCust(currentRecipient);
				trans2.setAmount(amount);
				trans2.setStatus("Credit");
				trans2.setTrans_date(LocalDate.now());
				trans2.setTrans_id("txn_slb_" + transactionNum);
				EmailService.sendmaill(currentRecipient.getcEmail(), "Dear Customer \n An amount of INR " + amount +
						" has been Credited to your account  xxxx" + currentRecipient.getAccountNo().substring(13)
						+ " on " + date + ".", "Transaction summery ");

				this.transactionRepo.save(trans2);

				currentRecipient.setBalance(balance2);
				this.cstmrRepo.save(currentRecipient);

				Message message = new Message("alert-success", "Fund transfered !!!");
				session.setAttribute("moneyTranfer", message);
				m.addAttribute("customer", customer);

				return "customerService/moneytranfer";

			} else {

				trans1.setBalance(customer.getBalance());
				trans1.setCust(customer);
				trans1.setAmount(amount);
				trans1.setStatus("Failed");
				trans1.setTrans_date(LocalDate.now());
				trans1.setTrans_id("txn_slb_" + transactionNum);

				this.transactionRepo.save(trans1);
				Message message = new Message("alert-danger", "You entered wrong atm pin ...");
				session.setAttribute("moneyTranfer", message);
				m.addAttribute("customer", customer);
				return "customerService/moneytranfer";
			}

		}

		trans2.setBalance(currentRecipient.getBalance());
		trans2.setCust(currentRecipient);
		trans2.setAmount(amount);
		trans2.setStatus("Failed");
		trans2.setTrans_date(LocalDate.now());
		trans2.setTrans_id("txn_slb_" + transactionNum);

		this.transactionRepo.save(trans2);

		m.addAttribute("title", " Money Transfer ");

		m.addAttribute("customer", customer);

		return "customerService/moneytranfer";
	}

	// atm pin update................

	@GetMapping("/customerDashboard/atm_pin_update")
	public String atmpinUpdate(Model m, Principal p) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		m.addAttribute("title", "Update Atm Pin");
		m.addAttribute("customer", customer);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		return "customerService/updateAtmPin";
	}

	// debit card process.......................................

	@GetMapping("/customerDashboard/debit_card")
	public String debitcard(Model m, Principal p, HttpSession session) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		m.addAttribute("title", "Debit card");
		m.addAttribute("customer", customer);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		if (customer.getAtmNumber() == null) {
			session.setAttribute("notAtm", "notAtm");
			session.removeAttribute("yesAtm");
		} else {

			String atmNum = customer.getAtmNumber();
			String first = atmNum.substring(0, 4);
			String second = atmNum.substring(4, 8);
			String third = atmNum.substring(8, 12);
			String fourth = atmNum.substring(12);

			m.addAttribute("atmnum1", first);
			m.addAttribute("atmnum2", second);
			m.addAttribute("atmnum3", third);
			m.addAttribute("atmnum4", fourth);

			session.setAttribute("yesAtm", "yesAtm");
			session.removeAttribute("notAtm");
		}

		return "customerService/debitcard";
	}

	// view statement ....................................

	@GetMapping("/customerDashboard/viewStatement")
	public String statementpage(Model m, Principal p) {

		String username = p.getName();

		Customer customer = this.cstmrRepo.getUserByUserName(username);

		m.addAttribute("title", "Statement : View Your statement");
		m.addAttribute("customer", customer);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		return "customerService/viewStatement";
	}

	// process check balance

	@PostMapping("/customerDashboard/checkBalanceProcess")
	public String processBalance(@ModelAttribute Customer custo, Model m, Principal p, HttpSession session) {

		Customer cust = this.cstmrRepo.getUserByUserName(p.getName());

		String savedAtmNum = cust.getAtmPin();
		String currAtm = custo.getAtmPin();
    

		Boolean isVerified =accountVerified.isVerified(cust);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}
		


		if (custo.getAccountNo() != null) {

			if (custo.getAccountNo().equals(cust.getAccountNo()) && savedAtmNum.equals(currAtm)) {
				String balance = cust.getBalance();
				m.addAttribute("balance", balance);
				return "customerService/fetchedbalance";
			}

		}
		if (custo.getAtmNumber() != null) {
			if (custo.getAtmNumber().equals(cust.getAtmNumber()) && savedAtmNum.equals(currAtm)) {
				String balance = cust.getBalance();
				m.addAttribute("balance", balance);
				return "customerService/fetchedbalance";
			}
		}
		if (custo.getUserId() != null) {
			if (custo.getUserId().equals(cust.getUserId()) && savedAtmNum.equals(currAtm)) {
				String balance = cust.getBalance();
				m.addAttribute("balance", balance);
				m.addAttribute("customer", cust);
				return "customerService/fetchedbalance";
			}
		}

		session.setAttribute("wrongAtmPin",
				new Message("alert-danger", "You entered wrong atm pin ! Please try again "));

		return "customerService/balanceCheck";
	}

	// UPDATE DETAILS PROCESS ......................................

	@PostMapping("/customerDashboard/updatedetails")
	public String createAcc(@ModelAttribute Customer customer, Model m, HttpSession session, Principal p,
			@RequestParam("filename") MultipartFile file) throws IOException {

		String username = p.getName();
		Customer cust = this.cstmrRepo.getUserByUserName(username);
    
		Boolean isVerified =accountVerified.isVerified(cust);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		try {

			if (file.isEmpty()) {

			} else {

				String fileurr = file.getOriginalFilename();

				cust.setcImage(fileurr);

				File saveFile = new ClassPathResource("static/image").getFile();

				Path path = (Path) Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}

			cust.setName(customer.getName());
			cust.setcEmail(customer.getcEmail());
			cust.setcPhone(customer.getcPhone());
			cust.setcFathername(customer.getcFathername());
			cust.setcMothername(customer.getcMothername());
			cust.setcNomineename(customer.getcNomineename());

			this.cstmrRepo.save(cust);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(customer);
		System.out.println(cust);
		Customer custome = this.cstmrRepo.getUserByUserName(cust.getUserId());
		m.addAttribute("customer", custome);

		session.setAttribute("message", "successfully updated");

		return "customerService/updateProfile";
	}

	// genareate debit card .....................

	@PostMapping("/customerDashboard/generateDebitcard")
	public String generationDebit(Principal p, Model m, HttpSession session) throws OtpException {

		String username = p.getName();

		Customer cust = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(cust);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		Boolean atmGenerateSuccess = this.atmService.generateAtm(cust);

		if (atmGenerateSuccess) {

			m.addAttribute("customer", cust);

			session.setAttribute("messageatm", "atm generated successfully");

			String atmNum = cust.getAtmNumber();
			String first = atmNum.substring(0, 4);
			String second = atmNum.substring(4, 8);
			String third = atmNum.substring(8, 12);
			String fourth = atmNum.substring(12);

			m.addAttribute("atmnum1", first);
			m.addAttribute("atmnum2", second);
			m.addAttribute("atmnum3", third);
			m.addAttribute("atmnum4", fourth);

			String atmpin = this.atmService.generatePin();

			String message = "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your atm pin , don't disclose</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"
					+ atmpin + "</h1></div>";

			if (atmpin != null) {
				cust.setAtmPin(atmpin);
				this.cstmrRepo.save(cust);
				EmailService.sendmaill(cust.getcEmail(), message, "ATM PIN SEND RELATED");
			}

			session.setAttribute("notAtm", "yes");

			return "redirect:/customer/customerDashboard/debit_card";

		}

		session.setAttribute("yesAtm", "yes");
		session.setAttribute("messageatmNot", "ATM generated successfully");

		return "redirect:/customer/customerDashboard/debit_card";
	}

	// UPDATE ATM PIN HANDLER ....sent otp ....................................

	@GetMapping("/customerDashboard/atmpinchangeGetOTP")
	public String putMethodName(Principal p, Model m, HttpSession session, @ModelAttribute Customer cust)
			throws OtpException {

		String username = p.getName();
		Customer existCust = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(existCust);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		String atmnumber = cust.getAtmNumber();

		session.setAttribute("atmnumber", atmnumber);

		String existAtmNum = existCust.getAtmNumber();
		String correctOtp = existAtmNum.substring(10);

		if (atmnumber.equals(correctOtp)) {

			// get otp .....

			String otp = this.otpService.generateOtp();

			existCust.setSavedOtp(otp);
			this.cstmrRepo.save(existCust);

			// send image for otp ..........

			String message = "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>Your OTP </h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"
					+ otp + "</h1></div>";

			EmailService.sendmaill(existCust.getcEmail(), message, "OTP FOR ATM PIN UPDATATION !!");

			session.setAttribute("message1", new Message("alert-success", "OTP has been sent successfully !!! "));
			session.setAttribute("showOtpTakeForm", "showOtpTakeForm");
			m.addAttribute("lastSixDigit", atmnumber);
		} else {
			session.setAttribute("message1", new Message("alert-danger", "Wrong atm number ! Please tra again.."));
		}

		return "customerService/updateAtmPin";
	}

	// atm pin updatation pin verify handler .................................

	@PostMapping("/customerDashboard/UpdateAtnPinOtpVerify")
	public String verifyAtmOtp(Principal p, Model m, HttpSession session, @ModelAttribute Customer cust,
			@ModelAttribute SigninHelper signinHelper) {

		// showUpdateform

		String username = p.getName();

		Customer existCust = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(existCust);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		String savedOTP = existCust.getSavedOtp();

		String currOtp = signinHelper.getNewOtp();

		m.addAttribute("currOtp", currOtp);

		String atmNum = (String) session.getAttribute("atmnumber");

		if (currOtp.equals(savedOTP)) {

			session.setAttribute("message1", new Message("alert-success", "OTP verified successfully !!!! "));
			session.setAttribute("showUpdateform", "showUpdateform");
			m.addAttribute("lastSixDigit", atmNum);

		} else {
			session.setAttribute("message1", new Message("alert-danger", "You entered wrong otp !! Please try again "));
			m.addAttribute("lastSixDigit", atmNum);

		}

		return "customerService/updateAtmPin";

	}

	// update pin handler goes here .....................

	@PostMapping("/customerDashboard/updateAtmPin")
	public String UpdateAtmPin(Principal p, Model m, HttpSession session, @ModelAttribute Customer cust,
			@ModelAttribute AtmPinHelper atmPinHelper) {

		String username = p.getName();
		Customer existCust = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(existCust);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		String savedOTP = (String) session.getAttribute("savedotp");
		m.addAttribute("savedOTP", savedOTP);

		String newPin = atmPinHelper.getNewPin();
		String CnfmPin = atmPinHelper.getConfirmPin();

		if (newPin.equals(CnfmPin)) {

			existCust.setAtmPin(newPin);
			this.cstmrRepo.save(existCust);
			session.setAttribute("message1", new Message("alert-success", "Atm Pin has been updated successfully !!"));

			String message = "<div style='width:100vw; height:fit-content;  border: 0.1vw solid red; padding:2px; align-items: center; text-align:center; justify-content: center ; padding-bottom:5vw;'><div  style=' width:100%; height:25vw; background:red; padding-top:4vw; text-align:center; '><h1 style='  font-size: 10vw; color: white;'>Star Life Bank</h1></div><h3 style='color: red; font-size: 3vw;'>WELCOME TO STAR LIFE BANK</h3><h3 style='color: black; font-size: 3vw; font-family: Verdana;'>-: Your new atm pin :-</h3><h1 style='color: red; font-size: 6vw; text-align:center;'>"
					+ newPin + "</h1></div>";

			EmailService.sendmaill(existCust.getcEmail(), message, "New ATM Pin alert !!");

		} else {
			session.setAttribute("message1", new Message("alert-danger", "New pin and confirm are not matched !!!!  "));
		}
		return "customerService/updateAtmPin";
	}

	// fetch transaction history

	@PostMapping("/customerDashboard/fetch-transactions")
	public String getTransactionHistory(Model m, Principal p,   @RequestParam(value = "filter", required = false) String filter,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		// get cutomer details

		String username = p.getName();
		Customer customer = this.cstmrRepo.getUserByUserName(username);

		Boolean isVerified =accountVerified.isVerified(customer);

		if(!isVerified){
			return "redirect:/home/logoutsuccess";
		}

		m.addAttribute("title", "Statement : View Your statement");
		m.addAttribute("customer", customer);

		LocalDate today = LocalDate.now();

		if (filter != null) {
        switch (filter) {
            case "today":
                startDate = today;
                endDate = today;
                break;
            case "week":
                startDate = today.with(DayOfWeek.MONDAY);
                endDate = today;
                break;
            case "month":
                startDate = today.withDayOfMonth(1);
                endDate = today;
                break;
            case "6months":
                startDate = today.minusMonths(6).withDayOfMonth(1);
                endDate = today;
                break;
        }
    }
      
   // Default range: show recent if no filter or date selected

    if (startDate == null || endDate == null) {
        startDate = LocalDate.of(1970, 1, 1);
        endDate = today;
    }


		// find transaction history

		List<Transaction_History> transaction_HistorybyuserId = this.transactionRepo
				.findByCustomerCidAndTransDateBetween(customer.getCid(), startDate, endDate);
		List<Transaction_History> finalList = transaction_HistorybyuserId.subList(0,
				Math.min(3, transaction_HistorybyuserId.size()));

		m.addAttribute("transactions", finalList);

		return "customerService/viewStatement";
	}

}
