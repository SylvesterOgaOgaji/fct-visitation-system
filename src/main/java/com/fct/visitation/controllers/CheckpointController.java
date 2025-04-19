package com.fct.visitation.controllers;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.repositories.CheckpointRepository;
import com.fct.visitation.services.interfaces.QRScanLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/checkpoint")
public class CheckpointController {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Autowired
    private QRScanLogService qrScanLogService;

    @GetMapping
    public String listCheckpoints(Model model) {
        model.addAttribute("checkpoints", checkpointRepository.findAll());
        return "checkpoint/list";
    }

    @GetMapping("/{id}")
    public String viewCheckpoint(@PathVariable Long id, Model model) {
        Checkpoint checkpoint = checkpointRepository.findById(id).orElse(null);
        
        if (checkpoint == null) {
            return "redirect:/error";
        }
        
        model.addAttribute("checkpoint", checkpoint);
        return "checkpoint/view";
    }

    @GetMapping("/scan-history/{id}")
    public String viewScanHistory(@PathVariable Long id, Model model) {
        List<QRScanLog> scanLogs = qrScanLogService.findByCheckpointId(id);
        model.addAttribute("scanLogs", scanLogs);
        return "checkpoint/scan-history";
    }

    // Fix: String to Long conversion issue
    @GetMapping("/process/{stringId}")
    public String processCheckpoint(@PathVariable String stringId, Model model) {
        try {
            // Fix: Convert String to Long
            Long checkpointId = Long.parseLong(stringId);
            
            Checkpoint checkpoint = checkpointRepository.findById(checkpointId).orElse(null);
            
            if (checkpoint == null) {
                return "redirect:/error";
            }
            
            model.addAttribute("checkpoint", checkpoint);
            return "checkpoint/process";
        } catch (NumberFormatException e) {
            return "redirect:/error";
        }
    }

    // Add any other methods that need fixing
}