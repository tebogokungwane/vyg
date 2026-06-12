package com.vyg.controller;

import com.vyg.config.security.JwtFilter;
import com.vyg.entity.Branding;
import com.vyg.service.BrandingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BrandingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BrandingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BrandingService brandingService;

    @MockitoBean
    private JwtFilter jwtFilter;

    @Test
    void uploadFavicon_returnsCreated() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "favicon.ico", "image/x-icon", new byte[]{1, 2, 3});

        Branding branding = new Branding();
        branding.setId(1L);
        branding.setType("favicon");

        when(brandingService.upload(eq("favicon"), any())).thenReturn(branding);

        mockMvc.perform(multipart("/api/branding/favicon").file(file))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void uploadLogo_returnsCreated() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "logo.png", "image/png", new byte[]{4, 5, 6});

        Branding branding = new Branding();
        branding.setId(2L);
        branding.setType("logo");

        when(brandingService.upload(eq("logo"), any())).thenReturn(branding);

        mockMvc.perform(multipart("/api/branding/logo").file(file))
                .andExpect(status().isCreated())
                .andExpect(content().string("2"));
    }

    @Test
    void getFavicon_returnsImageBytes() throws Exception {
        byte[] imageBytes = {10, 20, 30};
        Branding branding = new Branding();
        branding.setType("favicon");
        branding.setImageData(imageBytes);
        branding.setImageType("image/x-icon");

        when(brandingService.getByType("favicon")).thenReturn(branding);

        mockMvc.perform(get("/api/branding/favicon"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/x-icon"))
                .andExpect(content().bytes(imageBytes));
    }

    @Test
    void getLogo_returnsImageBytes() throws Exception {
        byte[] imageBytes = {40, 50, 60};
        Branding branding = new Branding();
        branding.setType("logo");
        branding.setImageData(imageBytes);
        branding.setImageType("image/png");

        when(brandingService.getByType("logo")).thenReturn(branding);

        mockMvc.perform(get("/api/branding/logo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/png"))
                .andExpect(content().bytes(imageBytes));
    }

    @Test
    void getByType_whenNotFound_returns404() throws Exception {
        when(brandingService.getByType("favicon"))
                .thenThrow(new IllegalArgumentException("No favicon uploaded"));

        mockMvc.perform(get("/api/branding/favicon"))
                .andExpect(status().isNotFound());
    }
}
