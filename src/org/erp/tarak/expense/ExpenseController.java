package org.erp.tarak.expense;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.erp.tarak.library.ERPUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private HttpSession session;


	@RequestMapping(value = "/saveExpense", method = RequestMethod.POST)
	public ModelAndView saveExpense(
			@ModelAttribute("command") @Valid ExpenseBean expenseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		if(result.hasErrors())
		{
			model.put("expenses", ExpenseUtilities
					.prepareListofExpenseBean(expenseService.listExpenses()));
			return new ModelAndView("expense", model);
		}
		if(expenseBean.getExpenseId()==0)
		{
			Expense expense=expenseService.getExpenseByName(expenseBean.getExpenseName());
			if(expense!=null && expense.getExpenseId()>0)
			{
				model.put("message", "Expense with name "+expenseBean.getExpenseName()+" already exists!");
				model.put("expenses", ExpenseUtilities
						.prepareListofExpenseBean(expenseService.listExpenses()));
				return new ModelAndView("expense", model);
			}
		}
		model.put("message", "Expense saved successfully");
		Expense expense = ExpenseUtilities.prepareExpenseModel(expenseBean,expenseService);
		expenseService.addExpense(expense);
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		return new ModelAndView("expense", model);
	}

	@RequestMapping(value = "/expense", method = RequestMethod.GET)
	public ModelAndView addExpense(
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		model.put("operation", "Add");
		return new ModelAndView("expense", model);
	}

	@RequestMapping(value = "/deleteExpense", method = RequestMethod.GET)
	public ModelAndView deleteExpense(
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		expenseService.deleteExpense(ExpenseUtilities
				.prepareExpenseModel(expenseBean));
		model.put("expense", null);
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		model.put("message", "Expense deleted");
		return new ModelAndView("expense", model);
	}

	@RequestMapping(value = "/editExpense", method = RequestMethod.GET)
	public ModelAndView editExpense(
			@ModelAttribute("command") ExpenseBean expenseBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("expense", ExpenseUtilities.prepareExpenseBean(expenseService
				.getExpense(expenseBean.getExpenseId())));
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		model.put("operation", "Edit");
		return new ModelAndView("expense", model);
	}
	@RequestMapping(value = "/ExpenseSelectionList/{type}", method = RequestMethod.GET)
	public ModelAndView selectExpense(
			@ModelAttribute("expenseBean") ExpenseBean expenseBean,
			BindingResult result, @PathVariable String type) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("type", type);
		model.put("expenses", ExpenseUtilities
				.prepareListofExpenseBean(expenseService.listExpenses()));
		return new ModelAndView("ExpenseSelectionList", model);
	}

}
