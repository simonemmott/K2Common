package com.k2.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EntityTest {

	@Id public Long id;
	@Column public String name;
	@Column public String description;

}
