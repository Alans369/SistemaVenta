package com.SistemaVenta.demo.Utils;



import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
@Service
public class PdfGeneratorService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ResourceLoader resourceLoader;

    public byte[] generatePdfFromHtml(String templateName, Map<String, Object> data) throws IOException {
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
            return outputStream.toByteArray();
        }
    }

    public byte[] generatePdfFromHtml(String templateName, Map<String, Object> ... modelMaps)
            throws IOException {
        // Crea un nuevo contexto para pasar los datos a la plantilla
        Context context = new Context();
        
        // Agrega todas las variables del modelo al contexto
        for (Map<String, Object> modelMap : modelMaps) {
            modelMap.forEach((key, value) -> context.setVariable(key, value));
        }

        // Procesa la plantilla HTML con los datos
        String htmlContent = templateEngine.process(templateName, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream, false);
            renderer.finishPDF();
            return outputStream.toByteArray();
        }
    }

}