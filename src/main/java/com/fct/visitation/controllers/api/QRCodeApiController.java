package com.fct.visitation.controllers.api;

import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.QRScanLogService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
public class QRCodeApiController {

    @Autowired
    private VisitorService visitorService;
    
    @Autowired
    private QRScanLogService qrScanLogService;

    @PostMapping("/scan/{qrCode}")
    public ResponseEntity<?> scanQRCode(@PathVariable String qrCode, @RequestParam Long checkpointId) {
        return visitorService.getVisitorByQrCode(qrCode)
                .map(visitor -> {
                    QRScanLog scanLog = qrScanLogService.recordScan(visitor, checkpointId);
                    return ResponseEntity.ok("Scan recorded successfully");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid QR Code"));
    }
}