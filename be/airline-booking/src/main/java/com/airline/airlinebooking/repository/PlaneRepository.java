package com.airline.airlinebooking.repository;

import com.airline.airlinebooking.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaneRepository extends JpaRepository<Plane , Long> {

}
