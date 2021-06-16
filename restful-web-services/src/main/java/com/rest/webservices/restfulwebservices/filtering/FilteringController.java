package com.rest.webservices.restfulwebservices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retriveSomeBean() {
		SomeBean someBean= new SomeBean("value1","value2","value3");
		MappingJacksonValue mapping= new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter filters=SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filter=new SimpleFilterProvider().addFilter("SomeBeanFilter", filters);
		mapping.setFilters(filter);
		return mapping;
	}
	@GetMapping("/filtering-list")
	public MappingJacksonValue retriveListOfSomeBeans() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","value3"), new SomeBean("value11","value22","value33"));
		SimpleBeanPropertyFilter filters=SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filter=new SimpleFilterProvider().addFilter("SomeBeanFilter", filters);
		MappingJacksonValue mapping= new MappingJacksonValue(list);
		mapping.setFilters(filter);
	
		return mapping;
	}
}
