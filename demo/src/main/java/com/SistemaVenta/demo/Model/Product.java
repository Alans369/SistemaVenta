package com.SistemaVenta.demo.Model;


import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "productos")
@Getter @Setter @ToString

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Brand marca;   
    
    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombre;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen;

    private double precioCompra;

    private double precioVenta;

    private Integer stock;
    
    @Column(nullable = false)
    private boolean estado = true;

    @Transient // Este campo no se guardará en la base de datos
    private String imagenbase64;


    @Transient
    public String getImagenDataUri() {
        if (imagen != null && imagen.length > 0) {
            String mimeType = detectarTipoImagen(imagen);
            String base64 = Base64.getEncoder().encodeToString(imagen);
            return "data:" + mimeType + ";base64," + base64;
        }
        return null;
    }

    @Transient
    private String detectarTipoImagen(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length < 4) {
            return "image/jpeg"; // Por defecto
        }
        
        // Verificar PNG (89 50 4E 47)
        if (imageBytes[0] == (byte) 0x89 && imageBytes[1] == 0x50 && 
            imageBytes[2] == 0x4E && imageBytes[3] == 0x47) {
            return "image/png";
        }
        
        // Verificar JPEG (FF D8)
        if (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) {
            return "image/jpeg";
        }
        
        // Verificar GIF (47 49 46)
        if (imageBytes[0] == 0x47 && imageBytes[1] == 0x49 && imageBytes[2] == 0x46) {
            return "image/gif";
        }
        
        // Verificar WebP (52 49 46 46 ... 57 45 42 50)
        if (imageBytes.length >= 12 &&
            imageBytes[0] == 0x52 && imageBytes[1] == 0x49 && 
            imageBytes[2] == 0x46 && imageBytes[3] == 0x46 &&
            imageBytes[8] == 0x57 && imageBytes[9] == 0x45 && 
            imageBytes[10] == 0x42 && imageBytes[11] == 0x50) {
            return "image/webp";
        }
        
        // Verificar BMP (42 4D)
        if (imageBytes[0] == 0x42 && imageBytes[1] == 0x4D) {
            return "image/bmp";
        }
        
        // Por defecto retornar JPEG
        return "image/jpeg";
    }
}
