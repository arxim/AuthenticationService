package com.service;

import java.util.List;
import java.util.Map;

public class DropdownServices {
	public String addDropdown(List<Map<String, String>> role) {
		String strOption = "<option value=''>Select</option>";
		for (int i = 0; i < role.size(); i++) {
			strOption += "<option value='"+role.get(i).get("ROLECODE")+"'>"+role.get(i).get("ROLENAME")+"</option>";
		}
		return strOption;
		
	}
}
