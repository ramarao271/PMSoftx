package org.erp.tarak.shipper;

import java.util.HashMap;
import java.util.Map;

import org.erp.tarak.address.Address;
import org.erp.tarak.address.AddressBean;
import org.erp.tarak.address.AddressService;
import org.erp.tarak.bankaccount.BankAccountService;
import org.erp.tarak.contactperson.ContactPersonService;
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
public class ShipperController {

	@Autowired
	private ShipperService shipperService;

	/*@Autowired
	@Qualifier("shipperValidator")
	private Validator validator;*/

	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;

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

	@RequestMapping(value = "/bankAccountsshipper/{category}", method = RequestMethod.POST)
	public ModelAndView addressModules(
			@ModelAttribute("shipperBean") ShipperBean shipperBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("shipperBean", shipperBean);
		model.put("operation", "Add");
		return new ModelAndView("shipper", model);
	}

	@RequestMapping(value = "/contactpersonshipper/{category}", method = RequestMethod.POST)
	public ModelAndView contactPersonModules(
			@ModelAttribute("shipperBean") ShipperBean shipperBean,
			@PathVariable String category) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("shipperBean", shipperBean);
		model.put("operation", "Add");
		return new ModelAndView("shipper", model);
	}

	@RequestMapping(value = "/saveShipper", method = RequestMethod.POST)
	public ModelAndView saveShipper(
			@ModelAttribute("shipperBean") @Validated ShipperBean shipperBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (result.hasErrors()) {
			model.put("shipperBean", shipperBean);
			return new ModelAndView("shipper", model);
		}
		Shipper shipper = ShipperUtilities.prepareShipperModel(shipperBean);
		shipperService.addShipper(shipper);
		model.put("message", "Shipper details saved successfully!");
		model.put("shippers",
				ShipperUtilities.prepareListofShipperBeans(shipperService.listShippers()));
		return new ModelAndView("shipperList", model);
	}

	@RequestMapping(value = "/listShipper", method = RequestMethod.GET)
	public ModelAndView listShippers() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("shippers",
				ShipperUtilities.prepareListofShipperBeans(shipperService.listShippers()));
		return new ModelAndView("shipperList", model);
	}

	@RequestMapping(value = "/addShipper", method = RequestMethod.GET)
	public ModelAndView addShipper() {
		Map<String, Object> model = new HashMap<String, Object>();
		ShipperBean shipperBean = new ShipperBean();
		AddressBean address = new AddressBean();
		shipperBean.setCompanyAddressBean(address);
		model.put("shipperBean", shipperBean);
		return new ModelAndView("shipper", model);
	}

	@RequestMapping(value = "/shipper/", method = RequestMethod.GET)
	public ModelAndView welcome1() {
		ShipperBean shipperBean = new ShipperBean();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "shipper");
		model.put("shipperBean", shipperBean);
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/shipper/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("category", "shipper");
		return new ModelAndView("index",model);
	}

	@RequestMapping(value = "/deleteShipper", method = RequestMethod.GET)
	public ModelAndView deleteShipper(
			@ModelAttribute("command") ShipperBean shipperBean,
			BindingResult result) {
		Shipper shipper = ShipperUtilities.getShipperModel(shipperService,
				shipperBean.getShipperId());
		Address address = shipper.getCompanyAddress();
		shipperService.deleteShipper(shipper);
		addressService.deleteAddress(address);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("shipper", null);
		model.put("shippers",
				ShipperUtilities.prepareListofShipperBeans(shipperService.listShippers()));
		return new ModelAndView("shipperList", model);
	}

	@RequestMapping(value = "/editShipper", method = RequestMethod.GET)
	public ModelAndView editShipper(
			@ModelAttribute("command") ShipperBean shipperBean,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("shipperBean", ShipperUtilities.prepareShipperBean(shipperBean.getShipperId(), shipperService));
		return new ModelAndView("shipper", model);
	}

	
}
