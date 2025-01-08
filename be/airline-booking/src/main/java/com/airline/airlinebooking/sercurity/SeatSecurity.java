package com.airline.airlinebooking.sercurity;

import com.airline.airlinebooking.model.Booking;
import com.airline.airlinebooking.model.Flight;
import com.airline.airlinebooking.model.Seat;
import com.airline.airlinebooking.repository.BookingRepository;
import com.airline.airlinebooking.repository.SeatRepository;
import com.airline.airlinebooking.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SeatSecurity {

    @Autowired
    private BookingRepository bookingRepository;  // Repository để truy vấn thông tin đặt ghế

    @Autowired
    private SeatRepository seatRepository;  // Repository để truy vấn thông tin ghế

    // Kiểm tra nếu người dùng là chủ sở hữu của ghế
    public boolean isOwner(Authentication authentication, Long seatId) {
        // Lấy customer_id từ Authentication (thông qua thông tin người dùng hiện tại)
        Long customerId = ((Customer) authentication.getPrincipal()).getId();  // Giả sử 'Customer' có phương thức getId()

        // Lấy Seat từ SeatRepository
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with ID: " + seatId));

        Flight flight = seat.getFlight();  // Lấy chuyến bay mà ghế thuộc về

        // Kiểm tra xem khách hàng có đặt vé trên chuyến bay này không
        Booking booking = bookingRepository.findByFlightAndCustomerId(flight, customerId);

        // Nếu tìm thấy booking, trả về true (người dùng có quyền huỷ ghế này)
        return booking != null;
    }
}
