package com.imageconversion.config;

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
    public ServletRegistrationBean<ResizeServlet> resizeServlet() {
        return new ServletRegistrationBean<>(new ResizeServlet(), "/function/resize");
    }

    @Bean
    public ServletRegistrationBean<TrimmingServlet> trimmingServlet() {
        return new ServletRegistrationBean<>(new TrimmingServlet(), "/function/trimming");
    }

    @Bean
    public ServletRegistrationBean<ChangeExtensionServlet> changeExtensionServlet() {
        return new ServletRegistrationBean<>(new ChangeExtensionServlet(), "/function/changeExtension");
    }

    @Bean
    public ServletRegistrationBean<InputImageServlet> inputImageServlet() {
        return new ServletRegistrationBean<>(new InputImageServlet(), "/function/inputImage");
    }

    @Bean
    public ServletRegistrationBean<GetExtensionServlet> getExtensionServlet() {
        return new ServletRegistrationBean<>(new GetExtensionServlet(), "/function/getExtension");
    }

    @Bean
    public ServletRegistrationBean<OpticalCharacterRecognitionServlet> ocrServlet() {
        return new ServletRegistrationBean<>(new OpticalCharacterRecognitionServlet(), "/function/opticalCharacterRecognition");
    }

    @Bean
    public ServletRegistrationBean<SaveImageServlet> saveImageServlet() {
        return new ServletRegistrationBean<>(new SaveImageServlet(), "/function/saveImage");
    }

    @Bean
    public ServletRegistrationBean<SampleCalcServlet> sampleCalcServlet() {
        return new ServletRegistrationBean<>(new SampleCalcServlet(), "/function/sampleCalc");
    }
}
