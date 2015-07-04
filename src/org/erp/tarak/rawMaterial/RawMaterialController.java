package org.erp.tarak.rawMaterial;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.erp.tarak.category.CategoryBean;
import org.erp.tarak.category.CategoryService;
import org.erp.tarak.category.CategoryUtilities;
import org.erp.tarak.library.ERPUtilities;
import org.erp.tarak.measurement.MeasurementService;
import org.erp.tarak.variant.VariantBean;
import org.erp.tarak.variantProperties.VariantPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RawMaterialController {

	@Autowired
	private RawMaterialService rawMaterialService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MeasurementService measurementService;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	   private ServletContext servletContext;

	@Autowired
	private VariantPropertiesService variantPropertiesService;

	@RequestMapping(value = "/addRawMaterialImage", method = RequestMethod.POST)
	public ModelAndView addImage(
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result) throws Exception {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		MultipartFile file = rawMaterialBean.getImage();
		if(null != file ) {
				String fileName = file.getOriginalFilename();
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				File dir = new File(servletContext.getContextPath() + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + fileName);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				model.put("image", dir.getAbsolutePath()+ File.separator + fileName);
				rawMaterialBean.setImagePath(dir.getAbsolutePath()+ File.separator + fileName);
		}
		
		model.put("rawMaterialBean",rawMaterialBean);
		return new ModelAndView("rawMaterial", model);
	}
	
	
	@RequestMapping(value = "/saveRawMaterial", method = RequestMethod.POST)
	public ModelAndView saveRawMaterial(
			@ModelAttribute("rawMaterialBean") @Valid RawMaterialBean rawMaterialBean,
			BindingResult result) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("rawMaterial", rawMaterialBean);
		String variantType=rawMaterialBean.getVariantType();
		model.put("vProperties", variantPropertiesService.getVariantPropertiesByType(variantType));
		boolean isRawMaterialExists=rawMaterialBean.getRawMaterialId()==0?RawMaterialUtilities.isRawMaterialExists(rawMaterialService,rawMaterialBean.getRawMaterialName()):false;
		if (result.hasErrors() || isRawMaterialExists) {
			if(isRawMaterialExists)
			{
				model.put("message", "RawMaterial with name "+rawMaterialBean.getRawMaterialName()+" already exists");
			}
			return new ModelAndView("rawMaterial", model);

		}
		int varQtyCount=0;
		if(rawMaterialBean.getVariantBeans()!=null && rawMaterialBean.getVariantBeans().size()>0)
		{
			for(VariantBean vb: rawMaterialBean.getVariantBeans())
			{
				varQtyCount+=vb.getQuantity();
			}
		}	
		if(varQtyCount>rawMaterialBean.getQuantity())
		{
			model.put("message", "Quantity entered in Variants doesnot match with total quantity");
			return new ModelAndView("rawMaterial", model);

		}
		RawMaterial rawMaterial = RawMaterialUtilities.prepareRawMaterialModel(rawMaterialBean,
				categoryService, measurementService,rawMaterialService);
		RawMaterialUtilities.saveRawMaterial(rawMaterial,rawMaterialService);
		try {
			MultipartFile multipartFile=rawMaterialBean.getImage();
			String fileExt=multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."),multipartFile.getOriginalFilename().length());
			File dest = new File(servletContext.getRealPath("resources/images")+ File.separator +rawMaterial.getRawMaterialCode()+ fileExt);
			rawMaterial.setImagePath("/ERPSoftware/resources/images"+ File.separator +rawMaterial.getRawMaterialCode()+ fileExt);
			multipartFile.transferTo(dest);
            RawMaterialUtilities.saveRawMaterial(rawMaterial, rawMaterialService);
        }
        catch(Exception e)
        {
        	
        }
		model.put("message", "RawMaterial details saved successfully");
		model.put("rawMaterials", RawMaterialUtilities
				.prepareListofRawMaterialBean(rawMaterialService.listRawMaterials()));
		return new ModelAndView("rawMaterialsList", model);
	}

	@RequestMapping(value = "/rawMaterials", method = RequestMethod.GET)
	public ModelAndView listRawMaterials() {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("rawMaterials", RawMaterialUtilities
				.prepareListofRawMaterialBean(rawMaterialService.listRawMaterials()));
		return new ModelAndView("rawMaterialsList", model);
	}

	@RequestMapping(value = "/addRawMaterial", method = RequestMethod.GET)
	public ModelAndView addRawMaterial(
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CategoryBean categoryBean = new CategoryBean();
		rawMaterialBean.setCategoryBean(categoryBean);
		VariantBean variantBean=new VariantBean();
		List<VariantBean> variantBeans=new LinkedList<VariantBean>();
		variantBeans.add(variantBean);
		rawMaterialBean.setVariantBeans(variantBeans);
		String variantType=rawMaterialBean.getVariantType();
		model.put("vProperties", variantPropertiesService.getVariantPropertiesByType(variantType));
		model.put("operation", "Add");
		return new ModelAndView("rawMaterial", model);
	}

	@RequestMapping(value = "/RawMaterialSelectionList/{type}", method = RequestMethod.GET)
	public ModelAndView selectRawMaterial(
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result,@PathVariable String type) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CategoryBean categoryBean = new CategoryBean();
		rawMaterialBean.setCategoryBean(categoryBean);
		model.put("type",type);
		model.put("categories", CategoryUtilities
				.prepareListofCategoryBean(categoryService.listCategories()));
		return new ModelAndView("RawMaterialSelectionList", model);
	}

	@RequestMapping(value = "/RawMaterialSelectionListType/{type}/{category}", method = RequestMethod.POST)
	public ModelAndView selectRawMaterialDisplay(@PathVariable long category,
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result,@PathVariable String type) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		CategoryBean categoryBean = new CategoryBean();
		rawMaterialBean.setCategoryBean(categoryBean);
		model.put("type",type);
		model.put("rawMaterials", RawMaterialUtilities
				.prepareListofRawMaterialBean(rawMaterialService
						.listRawMaterialsbyCategory(category)));
		model.put("categoryId", category);
		return new ModelAndView("RawMaterialSelectionList", model);
	}

	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome() {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", "rawMaterial");
		model.put("rawMaterials", RawMaterialUtilities
				.prepareListofRawMaterialBean(rawMaterialService.listRawMaterials()));
		return new ModelAndView("index", model);
	}
*/
	@RequestMapping(value = {"/rawMaterial/index"}, method = RequestMethod.GET)
	public ModelAndView welcome1() {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("category", "rawMaterial");
		model.put("rawMaterials", RawMaterialUtilities
				.prepareListofRawMaterialBean(rawMaterialService.listRawMaterials()));
		
		return new ModelAndView("index",model);

	}
	
	@RequestMapping(value = "/addRawMaterialVariant", method = RequestMethod.POST)
	public ModelAndView addVariant(@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		VariantBean variantBean=new VariantBean();
		List<VariantBean> variantBeans=new LinkedList<VariantBean>();
		if(rawMaterialBean.getVariantBeans()!=null)
		{
			variantBeans.addAll(rawMaterialBean.getVariantBeans());
		}
		variantBeans.add(variantBean);
		rawMaterialBean.setVariantBeans(variantBeans);
		model.put("rawMaterialBean",rawMaterialBean);
		String variantType=rawMaterialBean.getVariantType();
		model.put("vProperties", variantPropertiesService.getVariantPropertiesByType(variantType));
		return new ModelAndView("rawMaterial",model);
	}

	@RequestMapping(value = "/loadRawMaterialVariant/{variantType}", method = RequestMethod.POST)
	public ModelAndView loadVariant(@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result,@PathVariable String variantType) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		model.put("rawMaterialBean",rawMaterialBean);
		model.put("vProperties", variantPropertiesService.getVariantPropertiesByType(variantType));
		return new ModelAndView("rawMaterial",model);
	}

	
	@RequestMapping(value = "/deleteRawMaterial", method = RequestMethod.GET)
	public ModelAndView deleteRawMaterial(
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		rawMaterialService.deleteRawMaterial(RawMaterialUtilities.prepareRawMaterialModel(
				rawMaterialBean, categoryService, measurementService));
		model.put("rawMaterial", null);
		model.put("rawMaterials", RawMaterialUtilities
				.prepareListofRawMaterialBean(rawMaterialService.listRawMaterials()));
		return new ModelAndView("rawMaterialsList", model);
	}

	
	@RequestMapping(value = "/deleteRawVariant/{variantId}", method = RequestMethod.POST)
	public ModelAndView deleteVariant(
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result, @PathVariable long variantId) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		Iterator<VariantBean> vIterator=rawMaterialBean.getVariantBeans().iterator();
		while(vIterator.hasNext())
		{
			VariantBean variantBean=vIterator.next();
			if(variantId==variantBean.getVariantId())
			{
				vIterator.remove();
			}
		}
		String variantType=rawMaterialBean.getVariantType();
		model.put("vProperties", variantPropertiesService.getVariantPropertiesByType(variantType));
		return new ModelAndView("rawMaterial", model);
	}
	
	
	@RequestMapping(value = "/editRawMaterial", method = RequestMethod.GET)
	public ModelAndView editRawMaterial(
			@ModelAttribute("rawMaterialBean") RawMaterialBean rawMaterialBean,
			BindingResult result) {
		Map<String, Object> model = RawMaterialUtilities.getInputsInMap(rawMaterialService,categoryService,measurementService,variantPropertiesService);
		
		if (!ERPUtilities.isValidUser(session)) {
			model.put("message", "Invalid User Session! Please login again");
			return new ModelAndView("error", model);
		}
		RawMaterialBean pb=RawMaterialUtilities.prepareRawMaterialBean(rawMaterialService
				.getRawMaterial(rawMaterialBean.getRawMaterialId()));
		Iterator <VariantBean> vi=pb.getVariantBeans().iterator();
		while(vi.hasNext())
		{
			VariantBean vb=vi.next();
			if(vb.getVariantId()==0)
			{
				vi.remove();
			}
		}
		model.put("rawMaterialBean", pb);
		model.put("operation", "Edit");
		String variantType=pb.getVariantType();
		model.put("vProperties", variantPropertiesService.getVariantPropertiesByType(variantType));
		return new ModelAndView("rawMaterial", model);
	}
}
