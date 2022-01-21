package com.example.MercRestSample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MercRestSample.webmodel.EmployeeWebModel;

@Repository
public interface EmployeeWebRepository extends JpaRepository<EmployeeWebModel, Long> {

}
