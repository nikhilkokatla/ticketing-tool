
package com.ticketingtool.dashboard;

import com.ticketingtool.user.User;
import com.ticketingtool.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private UserRepository userRepository;

    // Agent Dashboard
    @GetMapping("/statistics")
    public DashboardResponse getDashboardStatistics() {
        return dashboardService.getDashboardStatistics();
    }

    // Customer Dashboard
    @GetMapping("/customer")
    public DashboardResponse getCustomerDashboard(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        return dashboardService.getCustomerDashboardStatistics(user.getId());
    }
}
