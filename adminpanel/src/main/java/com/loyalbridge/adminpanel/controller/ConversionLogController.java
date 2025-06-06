package com.loyalbridge.adminpanel.controller;

import com.loyalbridge.adminpanel.dto.ConversionLogResponse;
import com.loyalbridge.adminpanel.service.ConversionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/conversion-logs")
@RequiredArgsConstructor
public class ConversionLogController {
    private final ConversionLogService conversionLogService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<Page<ConversionLogResponse>> getConversionLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long partnerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable) {
        log.info("Getting conversion logs with filters: userId={}, partnerId={}, startDate={}, endDate={}",
                userId, partnerId, startDate, endDate);
        return ResponseEntity.ok(conversionLogService.getConversionLogs(
                userId, partnerId, startDate, endDate, pageable));
    }

    @GetMapping("/export")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('PARTNER_ADMIN')")
    public ResponseEntity<String> exportConversionLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long partnerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Exporting conversion logs with filters: userId={}, partnerId={}, startDate={}, endDate={}",
                userId, partnerId, startDate, endDate);

        List<ConversionLogResponse> logs = conversionLogService.exportConversionLogs(
                userId, partnerId, startDate, endDate);

        String csv = convertToCsv(logs);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "conversion-logs.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .body(csv);
    }

    private String convertToCsv(List<ConversionLogResponse> logs) {
        StringBuilder csv = new StringBuilder();
        
        // Add header
        csv.append("ID,Partner Name,User Name,Amount,Points,Status,Type,Timestamp,Description,Reference ID,Error Message\n");
        
        // Add data rows
        for (ConversionLogResponse log : logs) {
            csv.append(String.format("%d,%s,%s,%.2f,%d,%s,%s,%s,%s,%s,%s\n",
                    log.getId(),
                    escapeCsv(log.getPartnerName()),
                    escapeCsv(log.getUserName()),
                    log.getAmount(),
                    log.getPoints(),
                    escapeCsv(log.getStatus()),
                    escapeCsv(log.getType()),
                    log.getTimestamp(),
                    escapeCsv(log.getDescription()),
                    escapeCsv(log.getReferenceId()),
                    escapeCsv(log.getErrorMessage())));
        }
        
        return csv.toString();
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
} 