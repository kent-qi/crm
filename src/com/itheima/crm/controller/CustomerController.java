package com.itheima.crm.controller;

import com.itheima.crm.pojo.BaseDict;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.service.BaseDictService;
import com.itheima.crm.service.CustomerService;
import com.itheima.crm.utils.Page;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
   * 客户信息请求
   */
@Controller
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	private BaseDictService dictService;
	@Autowired
	private CustomerService customerService;
	@Value("${customer_from_type}")
	private String customer_from_type;
	@Value("${customer_industry_type}")
	private String customer_industry_type;

	@Value("${customer_level_type}")
	private String customer_level_type;
	private static Logger logger = Logger.getLogger(CustomerController.class);

	/**
	 * 程序
	 * 
	 * @param model
	 *            參數
	 * @param vo
	 *            參數
	 * @return 路徑 規範
	 */

	@RequestMapping("list")
	public String list(Model model, QueryVo vo) {
		logger.debug("list request params:");
		logger.debug(vo.toString());
		// 查询来源
		List<BaseDict> fromType = dictService.getBaseDictByCode(customer_from_type);
		// 查询行业
		List<BaseDict> industryType = dictService.getBaseDictByCode(customer_industry_type);
		List<BaseDict> levelType = dictService.getBaseDictByCode(customer_level_type);
		// 设置数据模型的返回
		model.addAttribute("fromType", fromType);
		model.addAttribute("industryType", industryType);
		model.addAttribute("levelType", levelType);
		model.addAttribute("vo", vo);

		// 根据查询条件分页查询用户列表
		Page<Customer> page = customerService.getCustomerByQueryVo(vo);
		logger.debug("list search result: ");
		logger.debug(page);
		// 设置分页数据返回
		model.addAttribute("page", page);
		return "customer";
	}
	/*
	* 
	*/

	@RequestMapping("edit")
	@ResponseBody

	public JSONObject edit(Integer id) { // 入参校验,返回错误码修改
		Customer customer = null;
		String code = "1";
		String msg = "";
		if (id != null) {
			logger.debug("ssssssssssss");
			customer = customerService.getCustomerById(id);
			logger.debug(customer);
			code = "0";
			msg = "成功";
			logger.info(JSONObject.fromObject(customer));
			JSONObject obj = new JSONObject();// {'code':0, data: {}, msg:''}
			obj.put("code", code);
			obj.put("data", JSONObject.fromObject(customer));
			obj.put("msg", msg);
			return obj;
		} else {
			code = "1";
			msg = "失败";
			JSONObject obj = new JSONObject();// {'code':0, data: {}, msg:''}
			obj.put("code", code);
			obj.put("data", JSONObject.fromObject(customer));
			obj.put("msg", msg);
			return obj;
		}

	}

	@RequestMapping("update")
	@ResponseBody
	public synchronized  JSONObject update(Customer customer) {// 当名字为马云222时，强制赋值名字为马云edit
		String code = "0";
		String msg = "1";
		if (customer == null) {
			code = "1";
			msg = "参数为空";
			JSONObject obj = new JSONObject();
			obj.put("code", 1);
			obj.put("msg", msg);
			return obj;
		} else {
			try {

				if (StringUtils.equals("马云222", customer.getCust_name())) {
					customer.setCust_name("马云edit");
				}
				customerService.updateCustomer(customer);
				msg = "更新成功";
				JSONObject obj = new JSONObject();
				obj.put("code", 0);
				obj.put("msg", msg);
				return obj;
			} catch (Exception e) {

				code = "1";
				msg = "更新失败";
				JSONObject obj = new JSONObject();
				obj.put("code", 1);
				obj.put("msg", msg);
				return obj;
			}

		}

	}

	@RequestMapping("delete")
	@ResponseBody
	public JSONObject delete(Integer id) {
		String code = "1";
		String msg = "1";
		if (id != null) {
			try {
				customerService.deleteCustomer(id);
				code = "0";
				msg = "删除成功";
				JSONObject obj = new JSONObject();
				obj.put("code", code);
				obj.put("msg", msg);
				return obj;
			} catch (Exception e) {
				code = "1";
				msg = "删除失败";
				JSONObject obj = new JSONObject();
				obj.put("code", code);
				obj.put("msg", msg);
				return obj;
			}

		} else {
			code = "1";
			msg = "参数校验失败";
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			obj.put("msg", msg);
			return obj;
		}

	}

	@RequestMapping("add")
	@ResponseBody
	public JSONObject add(Customer customer) {
		String code = "1";
		String msg = "1";
		if (customer == null) {
			code = "1";
			msg = "参数校验失败";
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			obj.put("msg", msg);
			return obj;
		} else {

			try {
				customerService.add(customer);
				code = "0";
				msg = "增加成功";
				JSONObject obj = new JSONObject();
				obj.put("code", code);
				obj.put("msg", msg);
				return obj;

			} catch (Exception e) {
				code = "1";
				msg = "增加失败";
				JSONObject obj = new JSONObject();
				obj.put("code", code);
				obj.put("msg", msg);
				return obj;
			}
		}

	}

}
