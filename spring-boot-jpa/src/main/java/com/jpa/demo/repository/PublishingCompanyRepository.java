package com.jpa.demo.repository;

import com.jpa.demo.entity.PublishingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublishingCompanyRepository extends JpaRepository<PublishingCompany, Integer> {
}
