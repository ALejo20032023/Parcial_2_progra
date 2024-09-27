package com.example.tipo_cambio.Controller;

import com.example.tipo_cambio.Model.TipoCambio;
import com.example.tipo_cambio.Service.TipoCambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TipoCambioController {
    @Autowired
    private TipoCambioService TipoCambioService;

    @GetMapping("/tipo-cambio")
    public ResponseEntity<TipoCambio> getTipoCambio() {
        try {
            TipoCambio tipoCambio = TipoCambioService.obtenerTipoCambio();
            return ResponseEntity.ok(tipoCambio);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
