package org.erp.tarak.worker;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.erp.tarak.address.AddressBean;
import org.erp.tarak.address.AddressService;
import org.erp.tarak.bankaccount.BankAccountService;
import org.erp.tarak.contactperson.ContactPersonService;
import org.erp.tarak.user.UserBean;
import org.erp.tarak.worker.openingbalance.WorkerOpeningBalance;
import org.erp.tarak.worker.openingbalance.WorkerOpeningBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WorkerController {

	@Autowired
	private WorkerService workerService;

	/*@Autowired
	@Qualifier("workerValidator")
	private Validator validator;*/

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	@Autowired
	private WorkerOpeningBalanceService wkrService;
	
	/*@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}*/

	@Autowired
	private AddressService addressService;
	@Autowired
	private BankAccountService bankAccountService;
	@Autowired
	private ContactPersonService contactPersonService;

	@RequestMapping(value = "/bankAccountsworker/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("workerBean") WorkerBean workerBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workerBean", workerBean);
		model.put("operation", "Add");
		return new ModelAndView("worker", model);
	}

	@RequestMapping(value = "/contactpersonworker/{category}", method = RequestMethod.POST)
	public ModelAndView contactPersonModules(
			@ModelAttribute("workerBean") WorkerBean workerBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workerBean", workerBean);
		model.put("operation", "Add");
		return new ModelAndView("worker", model);
	}

	@RequestMapping(value = "/saveWorker", method = RequestMethod.POST)
	public ModelAndView saveWorker(
			@ModelAttribute("workerBean") @Validated WorkerBean workerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (result.hasErrors()) {
			model.put("workerBean", workerBean);
			return new ModelAndView("worker", model);
		}
		if (session.getAttribute("user") != null) {
			UserBean user = (UserBean) session.getAttribute("user");

		
		Worker worker = WorkerUtilities.prepareWorkerModel(workerBean);
		WorkerOpeningBalance workerOpeningBalance=null;
		if(worker.getWorkerId()==0)
		{
			workerOpeningBalance=new WorkerOpeningBalance();
			workerOpeningBalance.setFinancialYear(user.getFinYear());
		}
		else
		{
			workerOpeningBalance=wkrService.getWorkerOpeningBalance(user.getFinYear(), workerBean.getWorkerId());
		}
		workerService.addWorker(worker);
		workerOpeningBalance.setWorkerId(worker.getWorkerId());
		workerOpeningBalance.setOpeningBalance(worker.getOpeningBalance());
		wkrService.addWorkerOpeningBalance(workerOpeningBalance);
		model.put("message", "Worker details saved successfully!");
		model.put("workers",
				WorkerUtilities.prepareListofWorkerBeans(workerService.listWorkers()));
		}
		return new ModelAndView("workerList", model);
	}

	@RequestMapping(value = "/listWorker", method = RequestMethod.GET)
	public ModelAndView listWorkers() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workers",
				WorkerUtilities.prepareListofWorkerBeans(workerService.listWorkers()));
		return new ModelAndView("workerList", model);
	}

	@RequestMapping(value = "/addWorker", method = RequestMethod.GET)
	public ModelAndView addWorker() {
		Map<String, Object> model = new HashMap<String, Object>();
		WorkerBean workerBean = new WorkerBean();
		AddressBean address = new AddressBean();
		model.put("workerBean", workerBean);
		return new ModelAndView("worker", model);
	}

	@RequestMapping(value = "/worker/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		WorkerBean workerBean = new WorkerBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "worker");
		model.put("workerBean", workerBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/worker/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "worker");
		return new ModelAndView("index",model);
	}

	@RequestMapping(value = "/deleteWorker", method = RequestMethod.GET)
	public ModelAndView deleteWorker(
			@ModelAttribute("command") WorkerBean workerBean,
			BindingResult result) {
		Worker worker = WorkerUtilities.getWorkerModel(workerService,
				workerBean.getWorkerId());
		workerService.deleteWorker(worker);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("worker", null);
		model.put("workers",
				WorkerUtilities.prepareListofWorkerBeans(workerService.listWorkers()));
		return new ModelAndView("workerList", model);
	}

	@RequestMapping(value = "/editWorker", method = RequestMethod.GET)
	public ModelAndView editWorker(
			@ModelAttribute("command") WorkerBean workerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("workerBean", WorkerUtilities.prepareWorkerBean(workerBean.getWorkerId(), workerService));
		return new ModelAndView("worker", model);
	}
	
	@RequestMapping(value = "/viewWorkerTransactions", method = RequestMethod.GET)
	public ModelAndView viewWorkerTransactions(
			@ModelAttribute("command") WorkerBean workerBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (session.getAttribute("user") != null) 
		{
			UserBean user = (UserBean) session.getAttribute("user");
			List<Object[]> objects=workerService.getWorkerTransactions(workerBean.getWorkerId(),user.getFinYear());
			List<WorkerTransaction> transactions=new LinkedList<WorkerTransaction>();
			double orderCost=0;
			double invoiceCost=0;
			for(Object[] x: objects)
			{
				WorkerTransaction transaction=new WorkerTransaction();
				transaction.setWorkerId(workerBean.getWorkerId());
				transaction.setTransactionId(((BigInteger)x[0]).longValue());
				transaction.setDate((Date)x[1]);
				transaction.setOrderCost((double)x[2]);
				transaction.setInvoiceCost((double)x[3]);
				transactions.add(transaction);
				orderCost+=(double)x[2];
				invoiceCost+=(double)x[3];
			}
			WorkerBean workerBean1=WorkerUtilities.prepareWorkerBean(workerBean.getWorkerId(), workerService);
			workerBean1.setBalance(orderCost-invoiceCost);
			model.put("workerBean", workerBean1);
			model.put("transactions", transactions);
		}
		return new ModelAndView("workerTransactions", model);
	}	
}
