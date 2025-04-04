package com.fct.visitation.controllers.api;

import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.QRScanLogService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/qr")
public class QRCodeApiController {

    @Autowired
    private VisitorService visitorService;
    
    @Autowired
    private QRScanLogService qrScanLogService;

    @PostMapping("/scan/{qrCode}")
    public ResponseEntity<?> scanQRCode(@PathVariable String qrCode, @RequestParam Long checkpointId) {
        return visitorService.findByQrCode(qrCode)
                .map(visitor -> {
                    QRScanLog scanLog = qrScanLogService.recordScan(visitor, checkpointId);
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("visitor", visitor);
                    response.put("scanLog", scanLog);
                    
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.badRequest().body("Invalid QR Code"));
    }
}
