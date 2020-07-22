package com.example.demo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DataRepository extends CrudRepository<Data,Long>{
	Data findByName(String name);
	List<Data> findBysubmittedOnBetween(Date date1,Date date2);
}
