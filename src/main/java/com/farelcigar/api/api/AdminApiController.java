package com.farelcigar.api.api;

import com.farelcigar.api.domain.Brand;
import com.farelcigar.api.domain.BrandFile;
import com.farelcigar.api.domain.Event;
import com.farelcigar.api.domain.Promotion;
import com.farelcigar.api.domain.dto.EventDto;
import com.farelcigar.api.domain.dto.PasswordsDto;
import com.farelcigar.api.domain.dto.PromotionDto;
import com.farelcigar.api.domain.enums.FileType;
import com.farelcigar.api.service.BrandService;
import com.farelcigar.api.service.EventPictureService;
import com.farelcigar.api.service.EventService;
import com.farelcigar.api.service.PromotionService;
import com.farelcigar.api.service.impl.UserDetailsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/public")
public class AdminApiController {

    private final BrandService brandService;
    private final EventService eventService;
    private final PromotionService promotionService;
    private final EventPictureService eventPictureService;
    private final UserDetailsServiceImpl userDetailsService;

    public AdminApiController(
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

    @PostMapping(value = "/user/password")
    public boolean changePassword(
            @RequestBody PasswordsDto passwordsDto,
            Authentication authentication) {
        return userDetailsService.updatePassword(passwordsDto, authentication);
    }

    @PostMapping(value = "/brand")
    public Brand createBrand(@RequestBody String name) {
        return brandService.createBrand(name);
    }

    @PutMapping(value = "/brand/{id}")
    public Brand updateBrand(
            @PathVariable Long id,
            @RequestBody String name) {
        return brandService.updateBrand(id, name);
    }

    @DeleteMapping(value = "/brand/{id}")
    public void deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }

    @PostMapping(value = "/brand/{id}/document")
    public BrandFile addBrandDocument(
            @PathVariable Long id,
            @RequestParam("document") MultipartFile document) throws IOException {
        return brandService.addFile(id, FileType.DOCUMENT, document);
    }

    @PostMapping(value = "/brand/{id}/picture")
    public BrandFile addBrandPicture(
            @PathVariable Long id,
            @RequestParam("picture") MultipartFile picture) throws IOException {
        return brandService.addFile(id, FileType.PICTURE, picture);
    }

    @PutMapping(value = "/brand/{id}/document")
    public BrandFile updateBrandDocument(
            @PathVariable Long id,
            @RequestParam("document") MultipartFile document) throws IOException {
        return brandService.updateFile(id, FileType.DOCUMENT, document);
    }

    @PutMapping(value = "/brand/{id}/picture")
    public BrandFile updateBrandPicture(
            @PathVariable Long id,
            @RequestParam("picture") MultipartFile picture) throws IOException {
        return brandService.updateFile(id, FileType.PICTURE, picture);
    }

    @DeleteMapping(value = "/brand/file/{id}")
    public void deleteBrandFile(@PathVariable Long id) {
        brandService.deleteFile(id);
    }

    @PostMapping(value = "/event")
    public Event createEvent(
            @RequestBody EventDto eventDto) {
        return eventService.createEvent(eventDto);
    }

    @PostMapping(value = "/event/{id}")
    public Event addEventPicture(
            @PathVariable Long id,
            @RequestParam("picture") MultipartFile picture) throws IOException {
        return eventService.addPicture(id, picture);
    }

    @PutMapping(value = "/event/{id}")
    public Event updateEvent(
            @PathVariable("id") Long id,
            @RequestBody EventDto eventDto) {
        return eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping(value = "/event/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @PostMapping(value = "/event/{id}/pictures")
    public void addEventPictures(
            @PathVariable Long id,
            @RequestParam("pictures") MultipartFile[] pictures) throws IOException {
        for (MultipartFile picture : pictures) {
            eventPictureService.addPicture(id, picture);
        }
    }

    @DeleteMapping(value = "/event/{id}/picture")
    public void deleteEventPicture(@PathVariable Long id) {
        eventPictureService.deletePicture(id);
    }

    @DeleteMapping(value = "/event/{id}/pictures")
    public void deleteEventPictures(@PathVariable Long id) {
        eventPictureService.deleteAllPicturesForEvent(id);
    }

    @PostMapping(value = "/promotion")
    public Promotion createPromotion(@RequestBody PromotionDto promotionDto) {
        return promotionService.createPromotion(promotionDto);
    }

    @PostMapping(value = "/promotion/{id}")
    public Promotion addPromotionPicture(
            @PathVariable Long id,
            @RequestParam("picture") MultipartFile picture) throws IOException {
        return promotionService.addPicture(id, picture);
    }

    @PutMapping(value = "/promotion/{id}")
    public Promotion updatePromotion(
            @PathVariable Long id,
            @RequestBody PromotionDto promotionDto) throws IOException {
        return promotionService.updatePromotion(id, promotionDto);
    }

    @DeleteMapping(value = "/promotion/{id}")
    public void deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
    }
}

