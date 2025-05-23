package com.fct.visitation.utils;

import com.fct.visitation.models.entity.Visitor;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class QRCodeGenerator implements QRCodeGeneratorInterface {
    @Override
    public String generateQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(pngData);
        } catch (WriterException | IOException e) {
            throw new RuntimeException("Error generating QR Code", e);
        }
    }
    
    @Override
    public String generateQRCode(Visitor visitor) {
        // Create a QR code with visitor information
        String visitorInfo = "ID:" + visitor.getId() + 
                            ",Name:" + visitor.getFirstName() + " " + visitor.getLastName() + 
                            ",Email:" + visitor.getEmail();
        
        // Generate a QR code image with standard dimensions
        return generateQRCodeImage(visitorInfo, 250, 250);
    }
}