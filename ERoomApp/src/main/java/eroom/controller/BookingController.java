package eroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eroom.Links;

@Controller
public class BookingController {

    @RequestMapping(Links.BOOKING)
    public String bookingHome(Model model){
        return "booking";
    }
    
}
