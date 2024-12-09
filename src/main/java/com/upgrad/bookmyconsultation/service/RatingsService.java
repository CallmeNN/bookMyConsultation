package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Doctor;
import com.upgrad.bookmyconsultation.entity.Rating;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.repository.DoctorRepository;
import com.upgrad.bookmyconsultation.repository.RatingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RatingsService {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private RatingsRepository ratingsRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	
	//create a method name submitRatings with void return type and parameter of type Rating
		//set a UUID for the rating
		//save the rating to the database
		//get the doctor id from the rating object
		//find that specific doctor with the using doctor id
		//modify the average rating for that specific doctor by including the new rating
		//save the doctor object to the database

	public void submitRatings(Rating rating){

		if (rating == null) {
			throw new NullPointerException("Rating object is null");
		}

		ratingsRepository.save(rating);
		String doctorId = rating.getDoctorId();

		if (doctorId == null) {
			throw new NullPointerException("Doctor ID is null");
		}

		Doctor doctor = doctorRepository.findById(doctorId)
			.orElseThrow(()-> new ResourceUnAvailableException("Doctor not found for ID: " + doctorId));

		if (doctor == null) {
			throw new NullPointerException("Doctor object is null");
		}

		double newRating = (doctor.getRating() * ratingsRepository.countByDoctorId(doctorId) + rating.getRating()) / (ratingsRepository.countByDoctorId(doctorId) + 1);

		doctor.setRating(newRating);

		doctorRepository.save(doctor);

	}
}
