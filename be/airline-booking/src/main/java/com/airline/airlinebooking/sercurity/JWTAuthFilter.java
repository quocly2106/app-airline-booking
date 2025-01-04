package com.airline.airlinebooking.sercurity;

import com.airline.airlinebooking.config.Admin.AdminDetailsService;
import com.airline.airlinebooking.config.Customer.CustomerDetailsService;
import com.airline.airlinebooking.config.Staff.StaffDetailsService;
import com.airline.airlinebooking.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthFilter.class);

    private final JWTUtils jwtUtils;
    private final AdminDetailsService adminDetailsService;
    private final StaffDetailsService staffDetailsService;
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public JWTAuthFilter(JWTUtils jwtUtils, AdminDetailsService adminDetailsService,
                         StaffDetailsService staffDetailsService,
                         CustomerDetailsService customerDetailsService) {
        this.jwtUtils = jwtUtils;
        this.adminDetailsService = adminDetailsService;
        this.staffDetailsService = staffDetailsService;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);

        try {
            userEmail = jwtUtils.extractUsername(jwtToken);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = loadUserDetails(userEmail);
                if (userDetails != null) {
                    if (jwtUtils.isTokenExpired(jwtToken)) {
                        logger.warn("Expired JWT token for user: {}", userEmail);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token expired");
                        return;
                    }

                    if (jwtUtils.isValidToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        logger.info("Authentication set for user: {}", userEmail);
                    } else {
                        logger.warn("Invalid JWT token for user: {}", userEmail);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("JWT processing error: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails loadUserDetails(String userEmail) {
        try {
            return adminDetailsService.loadUserByUsername(userEmail);
        } catch (UsernameNotFoundException exAdmin) {
            try {
                return staffDetailsService.loadUserByUsername(userEmail);
            } catch (UsernameNotFoundException exDoctor) {
                try {
                    return customerDetailsService.loadUserByUsername(userEmail);
                } catch (UsernameNotFoundException exReceptionist) {
                    logger.warn("User not found in any service for email: {}", userEmail);
                    return null;
                }
            }
        }
    }
}