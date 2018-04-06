package com.farelcigar.api.api;

import com.farelcigar.api.domain.Brand;
import com.farelcigar.api.domain.Event;
import com.farelcigar.api.domain.User;
import com.farelcigar.api.domain.dto.EventDto;
import com.farelcigar.api.domain.dto.EventPicturesDto;
import com.farelcigar.api.domain.dto.PromotionDto;
import com.farelcigar.api.service.BrandService;
import com.farelcigar.api.service.EventPictureService;
import com.farelcigar.api.service.EventService;
import com.farelcigar.api.service.PromotionService;
import com.farelcigar.api.service.impl.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/public")
public class PublicApiController {

    private final BrandService brandService;
    private final EventService eventService;
    private final PromotionService promotionService;
    private final EventPictureService eventPictureService;
    private final UserDetailsServiceImpl userDetailsService;

    public PublicApiController(
            BrandService brandService,
            EventService eventService,
            PromotionService promotionService,
            EventPictureService eventPictureService,
            UserDetailsServiceImpl userDetailsService) {
        this.brandService = brandService;
        this.eventService = eventService;
        this.promotionService = promotionService;
        this.eventPictureService = eventPictureService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/test")
    public String test(@RequestParam("file") MultipartFile file,
                       @RequestParam("name") String name) {
        return "Name " + name + " Filename: " + file.getOriginalFilename();
    }

    @GetMapping(value = "/user")
    public boolean isAuthenticated(Authentication authentication) {
        return userDetailsService.isAuthenticated(authentication);
    }

    /*@GetMapping(value = "/user")
    public User getAuthUser(Authentication authentication) {
        return userDetailsService.getAuthUser(authentication);
    }*/

    @PostMapping(value = "/user")
    public User createUser(@RequestBody User user) {
        return userDetailsService.createUser(user);
    }

    @GetMapping(value = "/brand/all")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping(value = "/brand/{id}/document")
    public ResponseEntity<byte[]> getBrandDocument(
            @PathVariable Long id) throws IOException {
        return brandService.getDocument(id);
    }

    @GetMapping(value = "/brand/{id}/picture")
    public void getBrandPicture(
            @PathVariable Long id,
            HttpServletResponse httpServletResponse) throws IOException {
        brandService.getPicture(id, httpServletResponse);
    }

    @GetMapping(value = "/event/all")
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping(value = "/event/{id}")
    public EventPicturesDto getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @GetMapping(value = "/event/{id}/picture")
    public void getEventPicture(
            @PathVariable Long id,
            HttpServletResponse httpServletResponse) throws IOException {
        eventService.getPicture(id, httpServletResponse);
    }

    @GetMapping(value = "/event/pictures/{id}")
    public void getEventPictures(
            @PathVariable Long id,
            HttpServletResponse httpServletResponse) throws IOException {
        eventPictureService.getPicture(id, httpServletResponse);
    }

    @GetMapping(value = "/promotion/all")
    public List<PromotionDto> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    @GetMapping(value = "/promotion/{id}/picture")
    public void getPromotionPicture(
            @PathVariable Long id,
            HttpServletResponse httpServletResponse) throws IOException {
        promotionService.getPicture(id, httpServletResponse);
    }
}
