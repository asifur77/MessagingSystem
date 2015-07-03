package com.spr.controller;

import java.util.Iterator;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

import javax.validation.Valid;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;








import com.spr.exception.ShopNotFound;
import com.spr.model.Shop;
import com.spr.service.ShopService;
import com.spr.validation.ShopValidator;

@Controller
@RequestMapping(value="/shop")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopValidator shopValidator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(shopValidator);
	}

	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newShopPage() {
		ModelAndView mav = new ModelAndView("shop-new", "shop", new Shop());
		return mav;
	}
	
	
	@RequestMapping("/sendDataXML")
    public ModelAndView sendDataXML(@RequestParam(value = "message" , defaultValue = "hello") String message , @RequestParam(value = "mobile") String mobile) {
    	
		//mobile = URLDecoder.decode(URLEncoder.encode(mobile));
		/*try {
			mobile = URLDecoder.decode(mobile.replace("+", "%2B"), "UTF-8").replace("%2B", "+");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(mobile.charAt(0) == ' ')
		mobile = mobile.replaceFirst(" ", "+");
		
		Shop shop = new Shop();
		shop.setName(message);
		shop.setEmplNumber(mobile);
		shop.setdtime();
		shop.setsend_time("");
		shop.setReply("Mr X.");
		shop.setStatus(false);
		shopService.create(shop);
		
    	System.out.println("Send data successfully !!" + message);
    	
    	//return "success rate : true;  message: " + message;
    	
    	ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		return mav;
	
	} 
	
	
	@RequestMapping("/recvDataXML")
    @Produces("application/json")
    public @ResponseBody
	String recvDataXML(@RequestParam(value = "mobile", defaultValue = "007") String mobile) throws ShopNotFound {
    	
		List<Shop> shopList = shopService.findAll();
		String temp = "Message: ";
		//mobile = URLDecoder.decode(URLEncoder.encode(mobile));
		/*try {
			mobile = URLDecoder.decode(mobile.replace("+", "%2B"), "UTF-8").replace("%2B", "+");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(mobile.charAt(0) == ' ')
			mobile = mobile.replaceFirst(" ", "+");
		mobile = mobile.replace("%20", " ");
		
		for (Shop shop : shopList) {
			if(shop.getEmplNumber().equals(mobile) && shop.getStatus() == false)
			{
				shopService.updat(shop);
				temp += shop.getReply();
				temp += "\n";
				System.out.println("Receive data successfully !!" + mobile);
			}
		}
			 	
    	return temp;
    } 

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView createNewShop(@ModelAttribute @Valid Shop shop,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return new ModelAndView("shop-new");
		
		ModelAndView mav = new ModelAndView();
		
		shop.setdtime();
		shop.setsend_time("");
		shop.setReply("Mr X.");
		shop.setStatus(false);
		
		String message = "New message "+shop.getName()+" was successfully created at"+shop.getdtime();
		
		shopService.create(shop);
		mav.setViewName("redirect:/index.html");
				
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;		
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView shopListPage() {
		ModelAndView mav = new ModelAndView("shop-list");
		List<Shop> shopList = shopService.findAll();
		mav.addObject("shopList", shopList);
		return mav;
	}
	
	@RequestMapping(value="/out", method=RequestMethod.GET)
	public ModelAndView shopOutPage() {
		ModelAndView mav = new ModelAndView("shop-out");
		List<Shop> shopList = shopService.findAll();
		mav.addObject("shopList", shopList);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editShopPage(@PathVariable Integer id) {
		ModelAndView mav = new ModelAndView("shop-edit");
		Shop shop = shopService.findById(id);
		mav.addObject("shop", shop);
		return mav;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editShop(@ModelAttribute @Valid Shop shop,
			BindingResult result,
			@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShopNotFound {
		
		if (result.hasErrors())
			return new ModelAndView("shop-edit");
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");
		String message = "Message was successfully updated.";

		shopService.update(shop);
		
		redirectAttributes.addFlashAttribute("message", message);	
		return mav;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteShop(@PathVariable Integer id,
			final RedirectAttributes redirectAttributes) throws ShopNotFound {
		
		ModelAndView mav = new ModelAndView("redirect:/index.html");		
		
		Shop shop = shopService.delete(id);
		String message = "The message "+shop.getName()+" was successfully deleted.";
		
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
}
