package com.fct.visitation.utils;

import com.fct.visitation.models.entity.Visitor;

public interface QRCodeGeneratorInterface {
    /**
     * Generates a QR code image with the specified dimensions
     * @param text the text to encode in the QR code
     * @param width the width of the QR code
     * @param height the height of the QR code
     * @return the generated QR code as a base64 encoded string
     */
    String generateQRCodeImage(String text, int width, int height);
    
    /**
     * Generates a QR code for a visitor
     * @param visitor the visitor for whom to generate a QR code
     * @return the generated QR code as a string
     */
    String generateQRCode(Visitor visitor);
}