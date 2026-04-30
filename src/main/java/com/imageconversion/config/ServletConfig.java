package com.imageconversion.config;

import jakarta.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imageconversion.conversion.servlet.ChangeExtensionServlet;
import com.imageconversion.conversion.servlet.GetExtensionServlet;
import com.imageconversion.conversion.servlet.InputImageServlet;
import com.imageconversion.conversion.servlet.OpticalCharacterRecognitionServlet;
import com.imageconversion.conversion.servlet.ResizeServlet;
import com.imageconversion.conversion.servlet.SaveImageServlet;
import com.imageconversion.conversion.servlet.TrimmingServlet;
import com.imageconversion.sample.servlet.SampleCalcServlet;

/**
 * Servlet登録設定クラス
 */
@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<ResizeServlet> resizeServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<ResizeServlet> registration =
                new ServletRegistrationBean<>(new ResizeServlet(), "/function/resize");
    	
    	registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<TrimmingServlet> trimmingServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<TrimmingServlet> registration =
    			new ServletRegistrationBean<>(new TrimmingServlet(), "/function/trimming");
    	
    	registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<ChangeExtensionServlet> changeExtensionServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<ChangeExtensionServlet> registration =
    			new ServletRegistrationBean<>(new ChangeExtensionServlet(), "/function/changeExtension");
    	
    	registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<InputImageServlet> inputImageServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<InputImageServlet> registration = 
    			new ServletRegistrationBean<>(new InputImageServlet(), "/function/inputImage");
    	
    	registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<GetExtensionServlet> getExtensionServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<GetExtensionServlet> registration = 
    			new ServletRegistrationBean<>(new GetExtensionServlet(), "/function/getExtension");
        
        registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<OpticalCharacterRecognitionServlet> ocrServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<OpticalCharacterRecognitionServlet> registration = 
    			new ServletRegistrationBean<>(new OpticalCharacterRecognitionServlet(), "/function/opticalCharacterRecognition");
        
        registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<SaveImageServlet> saveImageServlet(MultipartConfigElement multipartConfigElement) {
    	ServletRegistrationBean<SaveImageServlet> registration = 
    			new ServletRegistrationBean<>(new SaveImageServlet(), "/function/saveImage");
    	
    	registration.setMultipartConfig(multipartConfigElement);
        return registration;
    }

    @Bean
    public ServletRegistrationBean<SampleCalcServlet> sampleCalcServlet() {
        return new ServletRegistrationBean<>(new SampleCalcServlet(), "/function/sampleCalc");
    }
}
