package com.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class UserResource {
	
	@Autowired
	private userDaoService service;
	
	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		return service.findAll();
		
	}
	
	@GetMapping("/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable int id) {
		User user= service.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("id-"+id);
		}
		EntityModel<User> model= EntityModel.of(user);	
		WebMvcLinkBuilder linkToUser= linkTo(methodOn(this.getClass()).retriveAllUsers());
		model.add(linkToUser.withRel("all-users"));
		
				return model;
	}
	
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser=service.save(user);
		
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
		
		
	}
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user= service.deleteById(id);
		
		if(user==null) {
			throw new UserNotFoundException("id-"+id);
		}
		
	}
	
	

}
