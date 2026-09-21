package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepository;
    private final IArtistaRepository artistaRepository;

    public DiscoController(
            IDiscoRepository discoRepository,
            IArtistaRepository artistaRepository) {

        this.discoRepository = discoRepository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping(
            value = "/disco",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(
            @RequestBody Disco disco) {

        if (!artistaRepository.existsById(disco.idArtista)) {
            return ResponseEntity.notFound().build();
        }

        Disco discoGuardado = discoRepository.save(disco);

        return ResponseEntity.ok(discoGuardado);
    }

    @GetMapping(
            value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        return ResponseEntity.ok(discoRepository.findAll());
    }

    @GetMapping(
            value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(
            @PathVariable String id) {

        return discoRepository.findById(id)
                .map(disco -> ResponseEntity.ok((Object) disco))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(
            value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
            @PathVariable String id) {

        return ResponseEntity.ok(
                discoRepository.findDiscosByIdArtista(id)
        );
    }
}